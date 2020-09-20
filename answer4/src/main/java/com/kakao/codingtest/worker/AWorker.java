package com.kakao.codingtest.worker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.util.Constants;
import com.kakao.codingtest.worker.vo.RequestJDBCQueryVO;

import lombok.Getter;

public abstract class AWorker extends Thread {
	@Getter
	private TaskInfoVO task;
	private long now;

	@Getter
	private List<RequestJDBCQueryVO> queries;

	public AWorker(TaskInfoVO task, long now) {
		this.task = task;
		this.now = now;
		this.queries = this.listQueryVO();
	}

	public List<RequestJDBCQueryVO> listQueryVO() {
		if (task == null) {
			return null;
		}
		List<RequestJDBCQueryVO> queryList =  new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(task.getSource().getTimeFormat());
		long endTime = now - task.getDelayMin() * Constants.MILLIS_1MIN;
		long startTime = endTime - (task.getPeriodHour() * Constants.MILLIS_1HOUR);

		if (task.isUseSqoop()) {
			RequestJDBCQueryVO queryVO = RequestJDBCQueryVO.builder()
					.tableName(task.getSource().getTableName())
					.timeField(task.getSource().getTimeField())
					.startTime(dateFormat.format(new Date(startTime)))
					.endTime(dateFormat.format(new Date(endTime)))
					.build();
			queryList.add(queryVO);
			return queryList;
		}
		while (startTime < endTime) {
			long tmpStartTime = startTime;
			if (this.getHour(startTime) <= 6) {
				startTime += Constants.MILLIS_1HOUR * 3;
			} else {
				startTime += Constants.MILLIS_1MIN * 20;
			}
			RequestJDBCQueryVO queryVO = RequestJDBCQueryVO.builder()
					.tableName(task.getSource().getTableName())
					.timeField(task.getSource().getTimeField())
					.startTime(dateFormat.format(new Date(tmpStartTime)))
					.endTime(dateFormat.format(new Date(startTime)))
					.build();
			queryList.add(queryVO);
		}
		return queryList;
	}

	public int getHour(long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
}
