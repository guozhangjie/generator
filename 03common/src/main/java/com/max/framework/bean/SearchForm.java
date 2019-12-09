package com.max.framework.bean;

import java.io.Serializable;

import com.max.framework.core.codename.CodeNameChecker;
import com.max.framework.core.codename.CodeNameUtil;
import com.max.framework.core.validate.Requied;
import com.max.framework.core.validate.ValidateUtil;
import com.max.framework.ruler.form.SearchValidator;

/**
 * 基础查询form(不分页)
 * @author maqs
 */
public abstract class SearchForm implements SearchValidator, Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    @Requied(messageParams = "排序字段")
    @CodeNameChecker(CodeId = "C999", messageParam = "排序字段")
    private String orderSortName;

    /**
     * 排序方向
     */
    @Requied(messageParams = "排列顺序")
    @CodeNameChecker(CodeId = "C024", messageParam = "排列顺序")
    private String orderSortDirect;

    /**
     * 获得实际排序表达式
     * @return 实际排序表达式
     */
    public String getRealOrderSortExp() {
        validateSortInfo();
        CodeName orderSortCodeName = CodeNameUtil.getCodeNamesByCodeIdAndCodeValue("C999", orderSortName);
        String sortName = orderSortCodeName.getOption1();
        CodeName orderSortDirectCodeName = CodeNameUtil.getCodeNamesByCodeIdAndCodeValue("C024", orderSortDirect);
        String sortDirect = orderSortDirectCodeName.getCodeName();
        return sortName + " " + sortDirect;
    }

    /**
     * 验证排序信息
     */
    protected void validateSortInfo() {
        ValidateUtil.validatePropertiesException(this, new String[] { "orderSortName", "orderSortDirect" });
    }

    /**
     * 排序字段
     * @return 排序字段
     */
    public String getOrderSortName() {
        return orderSortName;
    }

    /**
     * 排序字段
     * @param orderSortName 排序字段
     */
    public void setOrderSortName(String orderSortName) {
        this.orderSortName = orderSortName;
    }

    /**
     * 排序方向
     * @return 排序方向
     */
    public String getOrderSortDirect() {
        return orderSortDirect;
    }

    /**
     * 排序方向
     * @param orderSortDirect 排序方向
     */
    public void setOrderSortDirect(String orderSortDirect) {
        this.orderSortDirect = orderSortDirect;
    }
}
