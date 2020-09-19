package com.kakao.codingtest.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kakao.codingtest.taskinfo.TaskInfoManager;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.worker.service.WorkerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuganji
 * 10분마다 스케줄링을 하며 테스크를 동작시킨다
 */
@Slf4j
@Service
public class TaskScheduler {
	@Autowired
	private TaskInfoManager taskInfoManager;

	@Autowired
	private WorkerService workerSerivce;

	@Scheduled(fixedDelay = 1000 * 60 * 10)
	public void schedule() {
		long now = System.currentTimeMillis();
		log.debug(taskInfoManager.getTaskList().toString());
		for (TaskInfoVO task: taskInfoManager.getTaskList()) {
			if (workerSerivce.isTime(now, task)) {
				workerSerivce.backup(now, task);
			}
		}
	}
}
