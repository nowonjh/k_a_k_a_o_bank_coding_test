package com.kakao.codingtest.worker.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.codingtest.jdbc.JdbcManager;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.worker.AWorker;
import com.kakao.codingtest.worker.JDBCWorker;
import com.kakao.codingtest.worker.SqoopWorker;

/**
 * @author yuganji
 * 백업 작업을 실행하는 클래스
 */
@Service
public class WorkerService {

	@Autowired
	private JdbcManager jdbcManager;

	public void run(long now, TaskInfoVO task) {
		AWorker worker = null;
		if (task.isUseSqoop()) {
			worker = new SqoopWorker(task, now);
		} else {
			worker = new JDBCWorker(task, now, jdbcManager);
		}
		worker.start();
	}

	/**
	 * hour_of_day	-	task를 실행시키는 시각
	 * delay_main	-	source 데이터의 유입지연을 고려한 텀을 주기위한 시간
	 * 2 가지의 값을 가지고 동작을 할지 말지 판단함.
	 * @param now  현재시각 timestamp
	 * @param task task infomation
	 * @return
	 */
	public boolean isTime(long now, TaskInfoVO task) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now);
		if (cal.get(Calendar.HOUR_OF_DAY) == task.getHourOfDay()
				&& cal.get(Calendar.MINUTE) == task.getDelayMin()) {
			return true;
		}
		return false;
	}
}
