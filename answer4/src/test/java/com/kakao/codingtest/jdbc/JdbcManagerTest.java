package com.kakao.codingtest.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kakao.codingtest.taskinfo.TaskInfoManager;
import com.kakao.codingtest.taskinfo.vo.SourceVO;
import com.kakao.codingtest.worker.vo.RequestJDBCQueryVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class JdbcManagerTest {
	@Autowired
	private JdbcManager databaseManager;

	@Autowired
	private TaskInfoManager taskInfoManager;

	@Test
	void query() {
		SourceVO sourceVO = taskInfoManager.getTaskList().get(0).getSource();
		RequestJDBCQueryVO requestJDBCQueryVO =
				RequestJDBCQueryVO.builder()
					.tableName("menu_log")
					.timeField("log_tktm")
					.startTime("20190301000000")
					.endTime("20190329000000")
					.build();
		try {
			assertEquals(635, databaseManager.query(sourceVO, requestJDBCQueryVO).size());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
}
