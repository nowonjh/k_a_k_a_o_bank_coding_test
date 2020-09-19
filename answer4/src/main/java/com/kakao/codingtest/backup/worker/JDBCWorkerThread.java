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
public class JDBCWorkerThread extends Thread {
	private TaskInfoVO taskInfoVO;
	private List<RequestJDBCQueryVO> queryList;
	private DatabaseManager dbManager;

	public JDBCWorkerThread(
			TaskInfoVO taskInfoVO,
			List<RequestJDBCQueryVO> queryList,
			DatabaseManager dbManager) {
		this.taskInfoVO = taskInfoVO;
		this.queryList = queryList;
		this.dbManager = dbManager;
	}

	@Override
	public void run() {
		IConvertData convertData = null;
		if (this.taskInfoVO.getTarget().getFormat().toLowerCase().equals("parquet")) {
			convertData = new Convert2Parquet(this.taskInfoVO);
		} else if (this.taskInfoVO.getTarget().getFormat().toLowerCase().equals("csv")) {
			// TODO 파일 포맷이 parquet가 아닐때...
		}
		for (RequestJDBCQueryVO queryVO: this.queryList) {
			try {
				FileSystem fs = FileSystem.get(new URI(this.taskInfoVO.getTarget().getUrl()), new Configuration());
				List<Map<String, Object>> dataList = dbManager.query(
						this.taskInfoVO.getSource(), queryVO);
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
