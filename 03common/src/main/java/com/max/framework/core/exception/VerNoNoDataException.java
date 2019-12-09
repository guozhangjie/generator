package com.max.framework.core.exception;

/**
 * 开放式并发异常
 * @author 马青松
 */
public class VerNoNoDataException extends SystemException {
    private static final long serialVersionUID = 1L;

    /**
     * 构造器
     * @param message 消息
     */
    public VerNoNoDataException(String message) {
        super(message);
    }
}
