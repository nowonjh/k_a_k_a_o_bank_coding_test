package com.kakao.codingtest.worker;

import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.util.Constants;
import com.kakao.codingtest.worker.service.SparkWorker;

public final class WorkerFactory {
    private WorkerFactory() { }
    public static AWorker create(String type, TaskInfoVO task, long now) {
        if (type.equals(Constants.ConnectorType.JDBC.getValue())) {
            return new JDBCWorker(task, now);
        } else if (type.equals(Constants.ConnectorType.SPARK.getValue())) {
            return new SparkWorker(task, now);
        } else if (type.equals(Constants.ConnectorType.SQOOP.getValue())) {
            return new SqoopWorker(task, now);
        }
        return null;
    }
}
