package com.max.framework.util.tcp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.max.framework.core.message.Message;

/**
 * Tcp返回结果Bean
 * @author 马青松
 */
public class TcpResultBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回结果类型
     */
    private TcpResultType tcpResultType = TcpResultType.NORMAL;

    /**
     * 返回的messages
     */
    private List<Message> messages;

    /**
     * 返回字符数据
     */
    private String resultDataString;

    /**
     * 返回二进制
     */
    private byte[] resultByteData;

    /**
     * 扩展信息
     */
    private Map<String, Serializable> extendsInfo;

    /**
     * 添加返回页面的message
     * @param msg 返回页面的message
     */
    public void addMessage(Message msg) {
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        messages.add(msg);
    }

    /**
     * 添加返回页面的message
     * @param msgs 返回页面的message
     */
    public void addMessage(List<Message> msgs) {
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        messages.addAll(msgs);
    }

    /**
     * 设定消息返回结果
     * @param msg 消息
     */
    public void makeResultMessages(Message msg) {
        tcpResultType = TcpResultType.MESSAGE;
        addMessage(msg);
    }

    /**
     * 设定消息返回结果
     * @param msgs 消息
     */
    public void makeResultMessages(List<Message> msgs) {
        tcpResultType = TcpResultType.MESSAGE;
        addMessage(msgs);
    }

    /**
     * 返回结果类型
     * @return 返回结果类型
     */
    public TcpResultType getTcpResultType() {
        return tcpResultType;
    }

    /**
     * 返回结果类型
     * @param tcpResultType 返回结果类型
     */
    public void setTcpResultType(TcpResultType tcpResultType) {
        this.tcpResultType = tcpResultType;
    }

    /**
     * 返回的messages
     * @return 返回的messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * 返回的messages
     * @param messages 返回的messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * 返回字符数据
     * @return 返回字符数据
     */
    public String getResultDataString() {
        return resultDataString;
    }

    /**
     * 返回字符数据
     * @param resultDataString 返回字符数据
     */
    public void setResultDataString(String resultDataString) {
        this.resultDataString = resultDataString;
    }

    /**
     * 返回二进制
     * @return 返回二进制
     */
    public byte[] getResultByteData() {
        return resultByteData;
    }

    /**
     * 返回二进制
     * @param resultByteData 返回二进制
     */
    public void setResultByteData(byte[] resultByteData) {
        this.resultByteData = resultByteData;
    }

    /**
     * 返回类型枚举
     * @author 马青松
     */
    public enum TcpResultType {
        /**
         * 正常返回
         */
        NORMAL,
        /**
         * 消息返回
         */
        MESSAGE,
        /**
         * 异常内容返回
         */
        ERROR
    }

    /**
     * 扩展信息
     * @return 扩展信息
     */
    public Map<String, Serializable> getExtendsInfo() {
        return extendsInfo;
    }

    /**
     * 扩展信息
     * @param extendsInfo 扩展信息
     */
    public void setExtendsInfo(Map<String, Serializable> extendsInfo) {
        this.extendsInfo = extendsInfo;
    }

}
