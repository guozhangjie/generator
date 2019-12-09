package com.max.framework.core.exception;

import java.util.List;

import com.max.framework.core.message.Message;

/**
 * 用户异常，出现该类异常需要向用户表示页面的消息
 * @author 马青松
 */
public class ApplicationExcetion extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 消息列表
     */
    private List<Message> messages;

    /**
     * 构造器
     * @param messages 相关消息
     */
    public ApplicationExcetion(List<Message> messages) {
        super(messages.toString());
        this.messages = messages;
    }

    /**
     * 消息列表
     * @return 消息列表
     */
    public List<Message> getMessages() {
        return messages;
    }
}
