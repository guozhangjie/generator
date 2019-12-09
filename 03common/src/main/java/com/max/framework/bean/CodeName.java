package com.max.framework.bean;

import java.io.Serializable;

/**
 * CODENAME码值表 master_code_name
 */
public class CodeName extends CodeNameKey implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * CODE内容 code_name
     */
    private String codeName;

    /**
     * 备选显示 option1
     */
    private String option1;

    /**
     * 备选显示 option2
     */
    private String option2;

    /**
     * 备选显示 option3
     */
    private String option3;

    /**
     * 备选显示 option4
     */
    private String option4;

    /**
     * 获得CODE内容 code_name
     * @return CODE内容
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * 设置CODE内容 code_name
     * @param codeName CODE内容
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * 获得备选显示 option1
     * @return 备选显示
     */
    public String getOption1() {
        return option1;
    }

    /**
     * 设置备选显示 option1
     * @param option1 备选显示
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     * 获得备选显示 option2
     * @return 备选显示
     */
    public String getOption2() {
        return option2;
    }

    /**
     * 设置备选显示 option2
     * @param option2 备选显示
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }

    /**
     * 获得备选显示 option3
     * @return 备选显示
     */
    public String getOption3() {
        return option3;
    }

    /**
     * 设置备选显示 option3
     * @param option3 备选显示
     */
    public void setOption3(String option3) {
        this.option3 = option3;
    }

    /**
     * 获得备选显示 option4
     * @return 备选显示
     */
    public String getOption4() {
        return option4;
    }

    /**
     * 设置备选显示 option4
     * @param option4 备选显示
     */
    public void setOption4(String option4) {
        this.option4 = option4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + "CodeName [codeName=" + codeName + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3
                + ", option4=" + option4 + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((codeName == null) ? 0 : codeName.hashCode());
        result = prime * result + ((option1 == null) ? 0 : option1.hashCode());
        result = prime * result + ((option2 == null) ? 0 : option2.hashCode());
        result = prime * result + ((option3 == null) ? 0 : option3.hashCode());
        result = prime * result + ((option4 == null) ? 0 : option4.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CodeName other = (CodeName) obj;
        if (codeName == null) {
            if (other.codeName != null)
                return false;
        } else if (!codeName.equals(other.codeName))
            return false;
        if (option1 == null) {
            if (other.option1 != null)
                return false;
        } else if (!option1.equals(other.option1))
            return false;
        if (option2 == null) {
            if (other.option2 != null)
                return false;
        } else if (!option2.equals(other.option2))
            return false;
        if (option3 == null) {
            if (other.option3 != null)
                return false;
        } else if (!option3.equals(other.option3))
            return false;
        if (option4 == null) {
            if (other.option4 != null)
                return false;
        } else if (!option4.equals(other.option4))
            return false;
        return true;
    }
}