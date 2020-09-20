package com.kakao.codingtest.util;

import lombok.Getter;

public final class Constants {
    private Constants() {
        throw new IllegalStateException("Constants is utility class");
    }
    public static final long MILLIS_1SEC = 1000;
    public static final long MILLIS_1MIN = MILLIS_1SEC * 60;
    public static final long MILLIS_1HOUR = MILLIS_1MIN * 60;
    public static final long MILLIS_1DAY = MILLIS_1HOUR * 24;

    public enum TargetType {
        HDFS("mem"),
        LOCAL("count"),
        AWS_S3("s3"),
        SAMBA("samba");

        @Getter
        private final String value;
        TargetType(String val) {
            this.value = val;
        }
        @Override
        public String toString() {
            return this.getValue();
        }
    }

    public enum ConnectorType {
        JDBC("jdbc"),
        SQOOP("sqoop"),
        SPARK("spark");
        @Getter
        private final String value;
        ConnectorType(String val) {
            this.value = val;
        }
        @Override
        public String toString() {
            return this.getValue();
        }
    }
}
