package com.kakao.codingtest.backup.worker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.kakao.codingtest.config.vo.TaskInfoVO;
import com.kakao.codingtest.jdbc.DatabaseManager;
import com.kakao.codingtest.target.Convert2Parquet;
import com.kakao.codingtest.target.IConvertData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCWorker implements IWorker {

	private DatabaseManager dbManager;
	private TaskInfoVO task;

	public JDBCWorker(DatabaseManager dbManager, TaskInfoVO task) {
		this.dbManager = dbManager;
		this.task = task;
	}

	@Override
	public void work(List<RequestJDBCQueryVO> queryList) {
		IConvertData convertData = null;
		if (task.getTarget().getFormat().toLowerCase().equals("parquet")) {
			convertData = new Convert2Parquet(task);
		} else if (task.getTarget().getFormat().toLowerCase().equals("csv")) {
			// TODO 파일 포맷이 parquet가 아닐때...
		}

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
	}

}
