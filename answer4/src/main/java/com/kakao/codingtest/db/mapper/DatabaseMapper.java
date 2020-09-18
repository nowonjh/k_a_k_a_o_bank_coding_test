package com.kakao.codingtest.db.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kakao.codingtest.config.vo.SourceTableVO;

@Repository
public interface DatabaseMapper {
	List<Map<String, Object>> getData(SourceTableVO vo);
}
