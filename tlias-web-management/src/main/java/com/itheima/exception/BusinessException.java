package com.itheima.exception;

public class BusinessException extends RuntimeException {
    /**
     * 构造方法,传入错误信息,并将其作为异常的原因
     * @param message
     */
    public BusinessException(String message) {
        super(message);
    }
}
