package com.kakao.codingtest.worker;

import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;

/**
 * @author yuganji
 */
public class SqoopWorker extends AWorker {
    public SqoopWorker(TaskInfoVO task, long now) {
        super(task, now);
    }

    @Override
    public void run() {
        // TODO sqoop 사용일 경우에 대한 처리
    }
}
