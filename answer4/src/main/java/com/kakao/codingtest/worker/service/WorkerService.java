package com.kakao.codingtest.worker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.codingtest.jdbc.JdbcManager;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.util.Constants;
import com.kakao.codingtest.worker.AWorker;
import com.kakao.codingtest.worker.JDBCWorker;
import com.kakao.codingtest.worker.SqoopWorker;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuganji
 * 백업 작업을 실행하는 클래스
 */
@Slf4j
@Service
public class WorkerService {

    @Autowired
    private JdbcManager jdbcManager;

    public void run(long now, TaskInfoVO task) {
        AWorker worker = null;
        if (task.getConnector().equals(Constants.ConnectorType.SQOOP.getValue())) {
            worker = new SqoopWorker(task, now);
        } else if (task.getConnector().equals(Constants.ConnectorType.JDBC.getValue())) {
            worker = new JDBCWorker(task, now, jdbcManager);
        } else if (task.getConnector().equals(Constants.ConnectorType.SPARK.getValue())) {
            worker = new SparkWorker(task, now);
        }
        if (worker != null) {
            worker.start();
        } else {
            log.error("Job task could not be completed. "
                    + "please check connector type. "
                    + "[" + task.getName() + "]");
        }
    }

    /**
     * perioid_hour    -    task가 동작하는 주기
     * delay_main    -    source 데이터의 유입지연을 고려한 텀을 주기위한 시간
     * 2 가지의 값을 가지고 동작을 할지 말지 판단함.
     * @param now  현재시각 timestamp
     * @param task task infomation
     * @return
     */
    public boolean isTime(long now, TaskInfoVO task) {
        now += Constants.MILLIS_1HOUR * 9;
        now -= Constants.MILLIS_1MIN * task.getDelayMin();
        if (now % (Constants.MILLIS_1HOUR * task.getPeriodHour())
                <= Constants.MILLIS_1MIN * 5) {
            return true;
        }
        return false;
    }
}
