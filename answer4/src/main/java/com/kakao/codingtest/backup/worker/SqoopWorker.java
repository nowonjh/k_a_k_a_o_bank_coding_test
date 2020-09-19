package com.kakao.codingtest.backup.worker;

import java.util.List;

import com.kakao.codingtest.config.vo.TaskInfoVO;

/**
 * @author yuganji
 */
public class SqoopWorker implements IWorker {
	private TaskInfoVO task;
	public SqoopWorker(TaskInfoVO task) {
		this.task = task;
	}

	@Override
	public void work(List<RequestJDBCQueryVO> queryList) {
		// TODO sqoop 사용일 경우에 대한 처리
	}

}
