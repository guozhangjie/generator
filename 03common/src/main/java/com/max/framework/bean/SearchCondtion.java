package com.max.framework.bean;

import java.io.Serializable;

/**
 * 查询条件对象
 * @param <T> 类型
 * @author 马青松
 */
public class SearchCondtion<T> implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * 分页信息
     */
    private PagerBean pager;

    /**
     * 查询条件
     */
    private T condition;

    /**
     * 排序内容
     */
    private String sort;

    /**
     * 构造器
     * @param pager pager
     * @param condition condition
     * @param sort sort
     */
    public SearchCondtion(PagerBean pager, T condition, String sort) {
        super();
        this.pager = pager;
        this.condition = condition;
        this.sort = sort;
    }

    /**
     * 获得分页
     * @return the pager
     */
    public PagerBean getPager() {
        return pager;
    }

    /**
     * 设置 分页
     * @param pager the pager to set
     */
    public void setPager(PagerBean pager) {
        this.pager = pager;
    }

    /**
     * 获得条件
     * @return the condition
     */
    public T getCondition() {
        return condition;
    }

    /**
     * 设置条件
     * @param condition the condition to set
     */
    public void setCondition(T condition) {
        this.condition = condition;
    }

    /**
     * 获得排序
     * @return the sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * 设置排序
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }
}
