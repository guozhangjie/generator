package com.max.framework.ruler.service.base;

import java.util.List;
import java.util.Map;

/**
 * 不分页service规则
 * @param <J> join entity
 * @author maqs
 */
public interface SelectNoPagerService<J> {
    /**
     * 根据条件进行连接查询不分页
     * @param condition condition
     * @param sort sort
     * @return Result
     */
    public List<J> selectJoin(Map<String, Object> condition, String sort);
}
