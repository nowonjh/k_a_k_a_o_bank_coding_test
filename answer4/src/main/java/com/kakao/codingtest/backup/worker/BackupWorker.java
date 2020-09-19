package com.kakao.codingtest.backup.worker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.codingtest.config.vo.TaskInfoVO;
import com.kakao.codingtest.jdbc.DatabaseManager;
import com.kakao.codingtest.parquet.ConvertParquetUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuganji
 * 백업 작업을 실행하는 클래스
 * 
 */
@Slf4j
@Service
public class BackupWorker {

	@Autowired
	private DatabaseManager dbManager;
	public boolean backup(long now, TaskInfoVO task) {
		long endTime = now / (60 * 60 * 1000) * (60 * 60 / 1000);
		long startTime = endTime - task.getHourOfDay() * 1000;
		List<RequestJDBCQueryVO> queryList = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(task.getSource().getTimeFormat());
		while (startTime <= endTime) {
			RequestJDBCQueryVO queryVO = RequestJDBCQueryVO.builder()
					.tableName(task.getSource().getTableName())
					.timeField(task.getSource().getTimeField())
					.startTime(dateFormat.format(new Date(startTime)))
					.endTime(dateFormat.format(new Date(endTime)))
					.build();
			queryList.add(queryVO);
			startTime += 60 * 1000 * 30;
		}

		for (RequestJDBCQueryVO queryVO: queryList) {
			try {
				FileSystem fs = FileSystem.get(new URI(task.getTarget().getUrl()), new Configuration());
				List<Map<String, Object>> dataList = dbManager.query(task.getSource(), queryVO);
				ConvertParquetUtil.mapToParquet(fs, task.getTarget().getPath(), dataList);
			} catch (ClassNotFoundException | SQLException e) {
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			} catch (URISyntaxException e) {
				log.error(e.getMessage(), e);
			}
		}
		return true;
	}

	/**
	 * hour_of_day	-	task를 실행시키는 시각
	 * delay_main	-	source 데이터의 유입지연을 고려한 텀을 주기위한 시간
	 * 2 가지의 값을 가지고 동작을 할지 말지 판단함.
	 * 
	 * @param now  현재시각 timestamp
	 * @param task task infomation
	 * @return
	 */
	public boolean isTime(long now, TaskInfoVO task) {
		if ((now - task.getHourOfDay() * 60 * 60 * 1000L) % (86400 * 1000L
				+ task.getDelayMin() * 60 * 1000L) < 1000 * 60 * 10L) {
			return true;
		}
		return false;
	}
}
