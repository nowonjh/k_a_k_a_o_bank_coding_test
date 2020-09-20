package com.kakao.codingtest.exception;

public class TaskInfoValidateException extends Exception {
    private static final long serialVersionUID = 5809268548220440151L;

    public TaskInfoValidateException() {
        super();
    }

    public TaskInfoValidateException(String msg) {
        super(msg);
    }
}
