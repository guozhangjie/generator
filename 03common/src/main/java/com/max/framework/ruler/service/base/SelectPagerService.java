package com.max.framework.ruler.service.base;

import java.util.List;
import java.util.Map;

import com.max.framework.bean.PageResult;
import com.max.framework.bean.PagerBean;

/**
 * 分页service规则
 * @param <J> e
 * @author maqs
 */
public interface SelectPagerService<J> {
    /**
     * 根据条件分页查询
     * @param pager pager
     * @param condition condition
     * @param sort sort
     * @return Result
     */
    public PageResult<List<J>> selectPager(PagerBean pager, Map<String, Object> condition,String sort);
}
