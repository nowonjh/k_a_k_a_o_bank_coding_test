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
	private String name;
	private int dealyMin;
	private int period;
	private int hourOfDay;
	private int concurrency;
	private boolean delete;
	private boolean useSqoop;
	private SourceVO sourceTable;
	private TargetVO targetTable;
}
