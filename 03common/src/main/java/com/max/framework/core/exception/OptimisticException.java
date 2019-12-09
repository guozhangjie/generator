package com.max.framework.core.exception;

/**
 * 乐观排他异常，出现该异常是向排他专有页面跳转
 * @author 马青松
 *
 */
public class OptimisticException extends SystemException {
    private static final long serialVersionUID = 1L;

    /**
     * 构造器
     * @param message 相关消息
     */
    public OptimisticException(String message) {
        super(message);
    }
}
