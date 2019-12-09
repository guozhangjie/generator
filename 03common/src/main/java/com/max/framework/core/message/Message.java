package com.max.framework.core.message;

import java.io.Serializable;

/**
 * 向页面返回消息的父基类
 * @author 马青松
 */
public class Message implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 属性名
     */
    private String propertyName;
    /**
     * 消息编号
     */
    private String messageId;
    /**
     * 消息内容
     */
    private String messageInfo;
    /**
     * 消息类型
     */
    private MessageType messageType;
    /**
     * 数组情况下记录索引
     */
    private Integer errorIndex;

    /**
     * 属性名
     * @return 属性名
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 属性名
     * @param propertyName 属性名
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 消息编号
     * @return 消息编号
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 消息编号
     * @param messageId 消息编号
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 消息内容
     * @return 消息内容
     */
    public String getMessageInfo() {
        return messageInfo;
    }

    /**
     * 消息内容
     * @param messageInfo 消息内容
     */
    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    /**
     * 消息类型
     * @return 消息类型
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * 消息类型
     * @param messageType 消息类型
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * 数组情况下记录索引
     * @return 数组情况下记录索引
     */
    public Integer getErrorIndex() {
        return errorIndex;
    }

    /**
     * 数组情况下记录索引
     * @param errorIndex 数组情况下记录索引
     */
    public void setErrorIndex(Integer errorIndex) {
        this.errorIndex = errorIndex;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Message) {
            Message msg = (Message) obj;
            if (this.propertyName.equals(msg.propertyName) && this.messageId.equals(msg.messageId)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Message [propertyName=" + propertyName + ", messageId=" + messageId + ", messageInfo=" + messageInfo + "]";
    }

    /**
     * 消息类型枚举
     * @author 马青松
     */
    public enum MessageType {
        /**
         * 消息
         */
        INFO, 
        /**
         * 警告
         */
        WARNING,
        /**
         * 异常
         */
        ERROR
    }
}
