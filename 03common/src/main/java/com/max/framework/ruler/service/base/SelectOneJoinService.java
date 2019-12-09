package com.max.framework.ruler.service.base;

/**
 * 单条显示查询规则
 * @param <J> 多表连接entity类型
 * @param <P> 主键类型
 * @author maqs
 */
public interface SelectOneJoinService<J, P> {
    /**
     * 根据主键检索多表连接单条结果
     * @param id pk
     * @return E
     */
    public J selectOneJoinByPk(P id);

}
