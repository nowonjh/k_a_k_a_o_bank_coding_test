package com.kakao.codingtest.worker.service;

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

import com.kakao.codingtest.taskinfo.vo.SourceVO;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;
import com.kakao.codingtest.util.Constants;
import com.kakao.codingtest.worker.JDBCWorker;
import com.kakao.codingtest.worker.vo.RequestJDBCQueryVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class WorkerServiceTest {

    @Autowired
    private WorkerService workerService;

    @Test
    void isTimeTest() throws ParseException {
        TaskInfoVO taskInfoVO = new TaskInfoVO();
        taskInfoVO.setDelayMin(200);
        taskInfoVO.setPeriodHour(24);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertEquals(true, workerService.isTime(format.parse("2020-09-19 03:20:11").getTime(), taskInfoVO));
        assertEquals(true, workerService.isTime(format.parse("2020-09-18 03:20:59").getTime(), taskInfoVO));
        assertEquals(false, workerService.isTime(format.parse("2020-09-19 03:19:11").getTime(), taskInfoVO));
        assertEquals(false, workerService.isTime(format.parse("2020-09-19 15:20:11").getTime(), taskInfoVO));
        assertEquals(true, workerService.isTime(format.parse("2020-09-19 03:21:11").getTime(), taskInfoVO));
    }

    @Test
    void isTimeTest2() throws ParseException {
        TaskInfoVO taskInfoVO = new TaskInfoVO();
        taskInfoVO.setDelayMin(20);
        taskInfoVO.setPeriodHour(24);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = format.parse("2020-09-19 00:20:00").getTime();
        time += Constants.MILLIS_1HOUR * 9;
        time -= taskInfoVO.getDelayMin() * Constants.MILLIS_1MIN;
        assertEquals(0, time % (taskInfoVO.getPeriodHour() * Constants.MILLIS_1HOUR));
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
        TaskInfoVO taskInfoVO = new TaskInfoVO();
        taskInfoVO.setDelayMin(200);
        taskInfoVO.setPeriodHour(24);
        taskInfoVO.setConnector("jdbc");
        SourceVO sourceVO = new SourceVO();

        sourceVO.setTableName("menu_log");
        sourceVO.setTimeField("log_tktm");
        sourceVO.setTimeFormat("yyyyMMddHHmmss");
        sourceVO.setBeginLoadHour(6);
        sourceVO.setEndLoadHour(24);
        taskInfoVO.setSource(sourceVO);
        long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-09-19 03:20:00").getTime();

        List<RequestJDBCQueryVO> queries =
                new JDBCWorker(taskInfoVO, time, null).listQueryVO();
        log.debug(queries.toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat(taskInfoVO.getSource().getTimeFormat());
        assertEquals("20200918000000", queries.get(0).getStartTime());
        assertEquals("20200918030000", queries.get(0).getEndTime());

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
            if (startCal.get(Calendar.HOUR_OF_DAY) >= taskInfoVO.getSource().getBeginLoadHour()
                    && startCal.get(Calendar.HOUR_OF_DAY) < taskInfoVO.getSource().getEndLoadHour()) {
                assertEquals(Constants.MILLIS_1MIN * 20L, endCal.getTimeInMillis() - startCal.getTimeInMillis());
            } else {
                assertEquals(Constants.MILLIS_1HOUR * 3L, endCal.getTimeInMillis() - startCal.getTimeInMillis());
            }
        }
    }
}
