package com.kakao.codingtest.config.vo;

import lombok.Data;

/**
 * @author yuganji
 *
 */
@Data
public class SourceTableVO {
	private String tableName;
	private String timefield;
	private String timeFormat;
}
