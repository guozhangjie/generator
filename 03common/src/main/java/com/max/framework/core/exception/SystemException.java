package com.max.framework.core.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.max.framework.core.message.Message;

/**
 * 系统异常，如果出现此类异常那么就是向专有页面跳转
 * @author 马青松
 */
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 打印日志
     */
    private static final Logger logger = Logger.getLogger(SystemException.class);

    /**
     * 消息列表
     */
    private List<Message> messages;

    /**
     * 消息
     */
    private String message;

    /**
     * 构造器
     * @param message 消息列表
     */
    public SystemException(String message) {
        super(message);
        this.message = message;
        logger.error(this.message);
    }

    /**
     * 构造器
     * @param messages 消息列表
     */
    public SystemException(List<Message> messages) {
        super(messages.toString());
        this.messages = messages;
        logger.error(this.messages);
    }

    /**
     * 构造器
     * @param message 消息列表
     */
    public SystemException(Message message) {
        super(message.toString());
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        logger.error(this.messages);
    }

    /**
     * 消息列表
     * @return 消息列表
     */
    public List<Message> getMessages() {
        return messages;
    }
}
