package com.kakao.codingtest.backup.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kakao.codingtest.config.vo.TaskInfoVO;
import com.kakao.codingtest.jdbc.DatabaseManager;

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
		for (RequestJDBCQueryVO queryVO: super.getQueries()) {
			Thread jdbcWorkerThread = new JDBCWorkerThread(
					super.getTask(), queryVO, this.dbManager);
			threadPool.execute(jdbcWorkerThread);
		}
		threadPool.shutdown();
	}
}
