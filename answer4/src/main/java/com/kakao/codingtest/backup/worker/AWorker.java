package com.kakao.codingtest.backup.worker;

import java.util.List;

public interface IWorker {
	void work(List<RequestJDBCQueryVO> queryList);
}
