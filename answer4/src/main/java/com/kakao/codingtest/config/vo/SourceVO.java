package com.kakao.codingtest.config.vo;

import lombok.Data;

/**
 * @author yuganji
 *
 */
@Data
public class SourceVO {
	private String type;
	private String url;
	private String driverClassName;
	private String username;
	private String password;
	private String tableName;
	private String timefield;
	private String timeFormat;
}
