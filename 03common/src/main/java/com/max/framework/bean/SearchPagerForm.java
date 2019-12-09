package com.max.framework.bean;

import java.io.Serializable;

import com.max.framework.core.validate.RegType;
import com.max.framework.core.validate.Regex;
import com.max.framework.core.validate.Requied;
import com.max.framework.core.validate.ValidateUtil;
import com.max.framework.util.string.StringUtil;

/**
 * 基础查询form(分页)
 * @author maqs
 */
public abstract class SearchPagerForm extends SearchForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    @Requied(messageParams = "页码")
    @Regex(regType = RegType.interger, regexParams = { "10" }, messageParams = { "10" })
    private String pageNum;

    /**
     * 一页记录数
     */
    @Requied(messageParams = "一页记录数")
    @Regex(regType = RegType.interger, regexParams = { "10" }, messageParams = { "10" })
    private String pageSize;

    /**
     * 分页信息
     * @return 分页信息
     */
    public PagerBean getPager() {
        validatePagerInfo();
        return new PagerBean(StringUtil.convertStringtoInt(pageNum), StringUtil.convertStringtoInt(pageSize));
    }

    /**
     * 验证分页信息
     */
    protected void validatePagerInfo() {
        ValidateUtil.validatePropertiesException(this, new String[] { "pageNum", "pageSize" });
    }

    /**
     * 页码
     * @return 页码
     */
    public String getPageNum() {
        return pageNum;
    }

    /**
     * 页码
     * @param pageNum 页码
     */
    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 一页记录数
     * @return 一页记录数
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * 一页记录数
     * @param pageSize 一页记录数
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
