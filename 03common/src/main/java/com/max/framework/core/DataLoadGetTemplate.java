package com.max.framework.core;

/**
 * 数据加载获得模板
 * @param <T> T
 * @author max
 */
public interface DataLoadGetTemplate<T> {

    /**
     * 加载全部数据
     */
    public void setAllDatas();

    /**
     * 根据key获得指定数据
     * @param key key
     * @return 指定数据
     */
    public T getDataByKey(String key);
}
