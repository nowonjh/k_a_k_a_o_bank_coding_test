package com.kakao.codingtest.backup.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kakao.codingtest.config.vo.TaskInfoVO;
import com.kakao.codingtest.task.TaskInfoManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class BackupWorkerServiceTest {

	@Autowired
	private BackupWorkerService backupWorker;

	@Autowired
	private TaskInfoManager taskInfoManager;

	@Test
	void isTimeTest() throws ParseException {
		TaskInfoVO taskInfoVO = new TaskInfoVO();
		taskInfoVO.setDelayMin(20);
		taskInfoVO.setHourOfDay(3);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		assertEquals(true, backupWorker.isTime(format.parse("2020-09-19 03:20:11").getTime(), taskInfoVO));
		assertEquals(true, backupWorker.isTime(format.parse("2020-09-18 03:20:59").getTime(), taskInfoVO));
		assertEquals(false, backupWorker.isTime(format.parse("2020-09-19 03:19:11").getTime(), taskInfoVO));
		assertEquals(false, backupWorker.isTime(format.parse("2020-09-19 15:20:11").getTime(), taskInfoVO));
		assertEquals(false, backupWorker.isTime(format.parse("2020-09-19 03:21:11").getTime(), taskInfoVO));
	}

	@Test
	void getHour() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time = format.parse("2020-09-19 03:20:11").getTime();
		long hour = new JDBCWorker(null, time, null).getHour(time);
		assertEquals(3L, hour);
	}

	@Test
	void queryList() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
		for (TaskInfoVO taskInfoVO: taskInfoManager.getTaskList()) {
			long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-09-19 03:20:00").getTime();
			List<RequestJDBCQueryVO> queries =
					new JDBCWorker(taskInfoVO, time, null).listQueryVO();
			log.debug(queries.toString());

			SimpleDateFormat dateFormat = new SimpleDateFormat(taskInfoVO.getSource().getTimeFormat());
			for (RequestJDBCQueryVO queryVO: queries) {
				assertNotNull(queryVO);
				assertNotNull(queryVO.getTimeField());
				assertNotNull(queryVO.getTableName());
				assertNotNull(queryVO.getStartTime());
				assertNotNull(queryVO.getEndTime());
				Calendar startCal = Calendar.getInstance();
				Calendar endCal = Calendar.getInstance();
				startCal.setTimeInMillis(dateFormat.parse(queryVO.getStartTime()).getTime());
				endCal.setTimeInMillis(dateFormat.parse(queryVO.getEndTime()).getTime());
				if (startCal.get(Calendar.HOUR_OF_DAY) <= 6) {
					assertEquals(1000 * 60 * 60 * 3L, endCal.getTimeInMillis() - startCal.getTimeInMillis());
				} else {
					assertEquals(1000 * 60 * 20L, endCal.getTimeInMillis() - startCal.getTimeInMillis());
				}
			}
		}
	}
}
