package com.kakao.codingtest.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kakao.codingtest.jdbc.JdbcManager;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.worker.thread.JDBCWorkerThread;
import com.kakao.codingtest.worker.vo.RequestJDBCQueryVO;

public class JDBCWorker extends AWorker {
    private JdbcManager jdbcManager;

    public JDBCWorker(TaskInfoVO task, long now, JdbcManager jdbcManager) {
        super(task, now);
        this.jdbcManager = jdbcManager;
    }

    @Override
    public void run() {
        ExecutorService threadPool = Executors.newFixedThreadPool(super.getTask().getConcurrency());
        for (RequestJDBCQueryVO queryVO: super.getQueries()) {
            Thread jdbcWorkerThread = new JDBCWorkerThread(
                    super.getTask(), queryVO, this.jdbcManager);
            threadPool.execute(jdbcWorkerThread);
        }
        threadPool.shutdown();
    }
}
