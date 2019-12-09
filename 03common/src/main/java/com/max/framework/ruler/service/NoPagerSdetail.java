package com.max.framework.ruler.service;

import com.max.framework.ruler.service.base.SelectNoPagerService;
import com.max.framework.ruler.service.base.SelectOneJoinService;

/**
 * 多表连接不分页主从查询视图
 * 此规则要求:
 * 1. 查询页面分页多表连接
 * 2. 详细页面显示时使用selectOneJoinByPk
 * @param <J> 多表 entity
 * @param <P> 主键类型
 * @author maqs
 */
public interface NoPagerSdetail<J, P> extends SelectNoPagerService<J>, SelectOneJoinService<J, P> {

}
