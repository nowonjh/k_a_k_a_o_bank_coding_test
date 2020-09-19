package com.kakao.codingtest.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.kakao.codingtest.config.vo.TaskInfoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
}
