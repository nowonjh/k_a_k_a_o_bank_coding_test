package com.kakao.codingtest.config.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

/**
 * @author yuganji
 *
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TaskInfoVO {
	private int dealyMin;
	private int schedule;
	private SourceTableVO sourceTable;
	private TargetTableVO targetTale;
}
