package com.kakao.codingtest.taskinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.gson.Gson;
import com.kakao.codingtest.exception.TaskInfoValidateException;
import com.kakao.codingtest.taskinfo.vo.TaskInfoVO;

@SpringBootTest
public class TaskInfoManagerTest {

    @Autowired
    private TaskInfoManager taskInfoManager;


    @Test()
    void reloadWrongFile() {
        List<TaskInfoVO> origin = taskInfoManager.getTaskList();
        ReflectionTestUtils.setField(taskInfoManager, "taskInfoPath", "./conf/xxx.conf");
        taskInfoManager.reload();
        assertEquals(origin, taskInfoManager.getTaskList());
    }

    @Test()
    void reloadWrongFile2() throws IOException {
        List<TaskInfoVO> origin = taskInfoManager.getTaskList();
        Gson gson = new Gson();
        String data = gson.toJson(origin);
        data = data.replaceAll("3", "true");
        String testConfPath = "./conf/test_conf.json";
        FileUtils.write(new File(testConfPath), data, Charset.defaultCharset());
        ReflectionTestUtils.setField(taskInfoManager, "taskInfoPath", testConfPath);
        taskInfoManager.reload();
        assertEquals(origin, taskInfoManager.getTaskList());
    }

    @Test()
    void validateCheck() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        TaskInfoVO taskInfoVO = new TaskInfoVO();
        taskInfoVO.setDelayMin(10);
        taskInfoVO.setName("Test");
        taskInfoVO.setPeriodHour(24);
        taskInfoVO.setConcurrency(1);
        // Normal Until here

        taskInfoVO.setConcurrency(8);
        assertThrows(
                TaskInfoValidateException.class,
                () -> {
                    taskInfoManager.validCheck(taskInfoVO); });
        taskInfoVO.setConcurrency(1);

        taskInfoVO.setPeriodHour(-1);
        assertThrows(
                TaskInfoValidateException.class,
                () -> {
                    taskInfoManager.validCheck(taskInfoVO); });

        taskInfoVO.setPeriodHour(24 * 8);
        assertThrows(
                TaskInfoValidateException.class,
                () -> {
                    taskInfoManager.validCheck(taskInfoVO); });
        taskInfoVO.setPeriodHour(24);

        taskInfoVO.setName(null);
        assertThrows(
                TaskInfoValidateException.class,
                () -> {
                    taskInfoManager.validCheck(taskInfoVO); });
        taskInfoVO.setName("Test");

        taskInfoVO.setDelayMin(1441);
        assertThrows(
                TaskInfoValidateException.class,
                () -> {
                    taskInfoManager.validCheck(taskInfoVO); });
    }
}
