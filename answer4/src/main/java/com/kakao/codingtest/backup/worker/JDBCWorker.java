package com.kakao.codingtest.backup.worker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.kakao.codingtest.config.vo.TaskInfoVO;
import com.kakao.codingtest.jdbc.DatabaseManager;
import com.kakao.codingtest.target.Convert2Parquet;
import com.kakao.codingtest.target.IConvertData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCWorker extends AWorker {
	private DatabaseManager dbManager;

	public JDBCWorker(TaskInfoVO task, long now, DatabaseManager dbManager) {
		super(task, now);
		this.dbManager = dbManager;
	}

	@Override
	public void run() {
		ExecutorService threadPool = Executors.newFixedThreadPool(super.getTask().getConcurrency());
		Thread jdbcWorkerThread = new JDBCWorkerThread(
				super.getTask(), super.getQueries(), this.dbManager);
		threadPool.execute(jdbcWorkerThread);
	}
}
