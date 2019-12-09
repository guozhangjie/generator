package com.max.framework.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.max.framework.core.message.Message;

/**
 * 检索结果集合（用于向页面返回值）
 * @param <T> 类型
 * @author 马青松
 * @since 1.0
 */
public class PageResult<T> implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * 返回类型
     */
    private ResultType resultType = ResultType.NORMAL;

    /**
     * 返回页面的message
     */
    private List<Message> messages;

    /**
     * 如果出现异常则需要页面的跳转
     */
    private String errorUrl;

    /**
     * 分页信息
     */
    private PagerBean pager;

    /**
     * 查询结果
     */
    private T result;

    /**
     * token
     */
    private String token;

    /**
     * 附加信息
     */
    private Map<String, String> extendsInfo;

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
     * 创建正常返回结果
     * @param result result
     * @param pager pager
     * @param extendsInfo extendsInfo
     */
    public void createNormalResult(T result, PagerBean pager, Map<String, String> extendsInfo) {
        this.pager = pager;
        this.result = result;
    }

    /**
     * 返回消息提示结果(如成功等)
     * @param msgs msgs
     * @param pager pager
     * @param result result
     */
    public void createMessageResult(List<Message> msgs, T result, PagerBean pager) {
        resultType = ResultType.MESSAGE;
        addMessage(msgs);
        this.pager = pager;
        this.result = result;
    }

    /**
     * 返回消息提示结果(如成功等)
     * @param msg msg
     * @param pager pager
     * @param result result
     */
    public void createMessageResult(Message msg, T result, PagerBean pager) {
        resultType = ResultType.MESSAGE;
        addMessage(msg);
        this.pager = pager;
        this.result = result;
    }

    /**
     * 返回消息提示结果(如成功等)
     * @param msgs msgs
     * @param pager pager
     */
    public void createMessageResult(List<Message> msgs, PagerBean pager) {
        resultType = ResultType.MESSAGE;
        addMessage(msgs);
        this.pager = pager;
    }

    /**
     * 返回消息提示结果(如成功等)
     * @param msg msg
     * @param pager pager
     */
    public void createMessageResult(Message msg, PagerBean pager) {
        resultType = ResultType.MESSAGE;
        addMessage(msg);
        this.pager = pager;
    }

    /**
     * 返回消息错误结果(如有重复等)
     * @param msg msg
     * @param pager pager
     */
    public void createWarningResult(Message msg, PagerBean pager) {
        resultType = ResultType.WARNING;
        addMessage(msg);
        this.pager = pager;
    }

    /**
     * 返回消息错误结果(如有重复等)
     * @param msgs msgs
     * @param pager pager
     */
    public void createWarningResult(List<Message> msgs, PagerBean pager) {
        resultType = ResultType.WARNING;
        addMessage(msgs);
        this.pager = pager;
    }
    
    /**
     * 返回消息错误结果(如有重复等)
     * @param msg msg
     * @param pager pager
     * @param result result
     */
    public void createWarningResult(Message msg, T result, PagerBean pager) {
        resultType = ResultType.WARNING;
        addMessage(msg);
        this.pager = pager;
        this.result = result;
    }

    /**
     * 返回消息错误结果(如有重复等)
     * @param msgs msgs
     * @param pager pager
     * @param result result
     */
    public void createWarningResult(List<Message> msgs, T result, PagerBean pager) {
        resultType = ResultType.WARNING;
        addMessage(msgs);
        this.pager = pager;
        this.result = result;
    }

    /**
     * 获得分页
     * @return 分页
     */
    public PagerBean getPager() {
        return pager;
    }

    /**
     * 设置分页
     * @param pager 分页
     */
    public void setPager(PagerBean pager) {
        this.pager = pager;
    }

    /**
     * 获得结果
     * @return 结果
     */
    public T getResult() {
        return result;
    }

    /**
     * 设置 结果
     * @param result 结果
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 结果类型
     * @return 结果类型
     */
    public ResultType getResultType() {
        return resultType;
    }

    /**
     * 结果类型
     * @param resultType 结果类型
     */
    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    /**
     * 用户消息
     * @return 用户消息
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * 用户消息
     * @param messages 用户消息
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * 错误跳转页面
     * @return 错误跳转页面
     */
    public String getErrorUrl() {
        return errorUrl;
    }

    /**
     * 设置错误跳转页面
     * @param errorUrl 页面
     */
    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    /**
     * token
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * token
     * @param token token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 附加信息
     * @return 附加信息
     */
    public Map<String, String> getExtendsInfo() {
        return extendsInfo;
    }

    /**
     * 附加信息
     * @param extendsInfo 附加信息
     */
    public void setExtendsInfo(Map<String, String> extendsInfo) {
        this.extendsInfo = extendsInfo;
    }

    /**
     * 返回类型枚举
     * @author 马青松
     */
    public enum ResultType {
        /**
         * 正常返回
         */
        NORMAL,
        /**
         * 消息返回
         */
        MESSAGE,
        /**
         * 警告返回
         */
        WARNING,
        /**
         * 异常内容返回
         */
        ERROR
    }

}
