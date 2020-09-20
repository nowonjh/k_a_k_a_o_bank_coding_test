package com.kakao.codingtest.taskinfo.vo;

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
    private int delayMin;
    private int periodHour;
    private int concurrency;
    private boolean delete;
    private boolean useSqoop;
    private SourceVO source;
    private TargetVO target;
}
