package com.kakao.codingtest.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kakao.codingtest.backup.worker.BackupWorker;
import com.kakao.codingtest.config.vo.TaskInfoVO;
import com.kakao.codingtest.task.TaskInfoManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuganji
 * 10분마다 스케줄링을 하며 테스크를 동작시킨다 
 */
@Slf4j
@Service
public class BackupScheduler {
	@Autowired
	private TaskInfoManager taskInfoManager;

	@Autowired
	private BackupWorker backupWorker;

	@Scheduled(fixedDelay = 1000 * 60 * 10)
	public void schedule() {
		long now = System.currentTimeMillis();
		log.debug(taskInfoManager.getTaskList().toString());
		for (TaskInfoVO task: taskInfoManager.getTaskList()) {
			if (backupWorker.isTime(now, task)) {
				backupWorker.backup(now, task);
			}
		}
	}
}
