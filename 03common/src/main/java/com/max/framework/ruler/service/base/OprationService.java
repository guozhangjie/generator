package com.max.framework.ruler.service.base;

/**
 * 操作规则
 * @param <E> 单表entity类型
 * @param <P> 主键类型
 * @author maqs
 */
public interface OprationService<E, P> {
    /**
     * 根据主键检索单表单条结果
     * @param id pk
     * @return E
     */
    public E selectOneByPk(P id);

    /**
     * 根据主键删除
     * @param id pk
     * @return 结果 1 成功  小于1失败 
     */
    public int deleteByPk(P id);

    /**
     * 增加(可选)
     * @param entity entity
     * @return 结果 (1 成功)(小于1失败)
     */
    public int insertSelective(E entity);

    /**
     * 修改(全量)[禁止使用updateselective]
     * @param entity entity
     * @return 结果 (1 成功)(小于1失败)
     */
    public int updateByPk(E entity);
}