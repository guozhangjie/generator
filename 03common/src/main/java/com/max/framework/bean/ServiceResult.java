package com.max.framework.bean;

import java.io.Serializable;

/**
 * service返回值
 * @param <T> 类型
 * @author maqs
 */
public class ServiceResult<T> implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 返回状态
     */
    private int res;
    
    /**
     * 返回结果
     */
    private T data;

    /**
     * 返回状态
     * @return 返回状态
     */
    public int getRes() {
        return res;
    }

    /**
     * 返回状态
     * @param res 返回状态
     */
    public void setRes(int res) {
        this.res = res;
    }

    /**
     * 返回结果
     * @return 返回结果
     */
    public T getData() {
        return data;
    }

    /**
     * 返回结果
     * @param data 返回结果
     */
    public void setData(T data) {
        this.data = data;
    }
}
