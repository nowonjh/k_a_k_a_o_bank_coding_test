package com.kakao.codingtest.backup.worker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.codingtest.config.vo.TaskInfoVO;
import com.kakao.codingtest.jdbc.DatabaseManager;
import com.kakao.codingtest.target.Convert2Parquet;
import com.kakao.codingtest.target.IConverData;

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
		IConverData convertData = null;
		if (task.getTarget().getFormat().toLowerCase().equals("parquet")) {
			convertData = new Convert2Parquet(task);
		}
		List<RequestJDBCQueryVO> queryList = this.listQueryVO(now, task);

		for (RequestJDBCQueryVO queryVO: queryList) {
			try {
				FileSystem fs = FileSystem.get(new URI(task.getTarget().getUrl()), new Configuration());
				List<Map<String, Object>> dataList = dbManager.query(task.getSource(), queryVO);
				convertData.convertAndPushHDFS(fs, dataList);
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
	
	private List<RequestJDBCQueryVO> listQueryVO(long now, TaskInfoVO task) {
		List<RequestJDBCQueryVO> queryList =  new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(task.getSource().getTimeFormat());
		now -= (task.getHourOfDay() * 1000 * 60 * 60) - (task.getDelayMin() * 1000 * 60);
		long endTime = now / (60 * 60 * 1000L) * (60 * 60 * 1000L);
		long startTime = endTime - (86400 * 1000L);
		while (startTime < endTime) {
			long tmpStartTime = startTime;
			startTime += 1000 * 60 * 30;
			RequestJDBCQueryVO queryVO = RequestJDBCQueryVO.builder()
					.tableName(task.getSource().getTableName())
					.timeField(task.getSource().getTimeField())
					.startTime(dateFormat.format(new Date(tmpStartTime)))
					.endTime(dateFormat.format(new Date(startTime)))
					.build();
			queryList.add(queryVO);
			
		}
		return queryList;
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
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now);
		if (cal.get(Calendar.HOUR_OF_DAY) == task.getHourOfDay()
				&& cal.get(Calendar.MINUTE) == task.getDelayMin()) {
			return true;
		}
		return false;
	}
}
