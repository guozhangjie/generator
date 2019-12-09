package com.max.framework.bean;

import java.io.Serializable;

/**
 * CODENAME码值表 master_code_name
*/
public class CodeNameKey implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * CODE编号 code_id
    */
    private String codeId;

    /**
     * CODE值 code_value
    */
    private String codeValue;

    /**
     * 获得CODE编号 code_id
     * @return CODE编号
     */
    public String getCodeId() {
        return codeId;
    }

    /**
     * 设置CODE编号 code_id
     * @param codeId CODE编号
     */
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    /**
     * 获得CODE值 code_value
     * @return CODE值
     */
    public String getCodeValue() {
        return codeValue;
    }

    /**
     * 设置CODE值 code_value
     * @param codeValue CODE值
     */
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", codeId=").append(codeId);
        sb.append(", codeValue=").append(codeValue);
        sb.append("]");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CodeNameKey other = (CodeNameKey) that;
        return (this.getCodeId() == null ? other.getCodeId() == null : this.getCodeId().equals(other.getCodeId()))
            && (this.getCodeValue() == null ? other.getCodeValue() == null : this.getCodeValue().equals(other.getCodeValue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCodeId() == null) ? 0 : getCodeId().hashCode());
        result = prime * result + ((getCodeValue() == null) ? 0 : getCodeValue().hashCode());
        return result;
    }
}