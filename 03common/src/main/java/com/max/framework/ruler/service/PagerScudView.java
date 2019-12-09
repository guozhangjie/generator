package com.max.framework.ruler.service;

import com.max.framework.ruler.service.base.OprationService;
import com.max.framework.ruler.service.base.SelectOneJoinService;
import com.max.framework.ruler.service.base.SelectPagerService;

/**
 * 多表连接查询,单表增删改视图
 * 此规则要求:
 * 1. 查询页面分页多表连接
 * 2. 修改页面显示时使用selectOneJoinByPk
 *    修改前使用selectOneByPk前检测
 *    修改必须使用updateByPk全量修改
 * @param <J> 多表 entity
 * @param <E> 单表entity类型
 * @param <P> 主键类型
 * @author maqs
 */
public interface PagerScudView<J, E, P> extends SelectPagerService<J>, SelectOneJoinService<J, P>, OprationService<E, P> {

}
