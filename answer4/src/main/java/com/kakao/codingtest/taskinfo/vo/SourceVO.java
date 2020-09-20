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
public class SourceVO {
    private String type;
    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private String tableName;
    private String timeField;
    private String timeFormat;
}
