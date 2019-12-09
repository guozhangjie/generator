package com.max.framework.core.validate;

/**
 * 注解内置类型
 * @author max
 */
public enum RegType {
    /**
     * 类型
     */
    custom("", ""), interger("SYS.INT", "S0002"), bigdecimal("SYS.DECIMAL", "S0003"),
    /**
     * 类型
     */
    date("SYS.DATA", "S0004"), ip("SYS.IP", "S0005"), email("SYS.EMAIL", "S0006"),
    /**
     * 类型
     */
    idnumber("SYS.ID_NUMBER", "S0007"), phone("SYS.PHONE", "S0008"),
    /**
     * 类型
     */
    car_number("SYS.CAR_NUMBER", "S0009"), letter("SYS.LETTER", "S0010"),
    /**
     * 类型
     */
    letter_number("SYS.LETTER_NUMBER", "S0011"), any_char("SYS.ANY_CHAR", "S0012"),
    /**
     * 类型
     */
    uuid("SYS.UUID", "S0013"), username("SYS.USER_NAME", "S0014"),
    /**
     * 类型
     */
    password_strong("SYS.PASSWORD_STRONG", "S0015"), letter_chinese("SYS.LETTER_CHINESE", "S0029"),
    /**
     * 邮编
     */
    post_code("SYS.POST.CODE", "M0002");

    /**
     * regexId
     */
    private String regexId;

    /**
     * messageId
     */
    private String messageId;

    /**
     * 构造器
     * @param regexId regexId
     * @param messageId messageId
     */
    private RegType(String regexId, String messageId) {
        this.regexId = regexId;
        this.messageId = messageId;
    }

    /**
     * regexId
     * @return regexId
     */
    public String getRegexId() {
        return regexId;
    }

    /**
     * regexId
     * @param regexId regexId
     */
    public void setRegexId(String regexId) {
        this.regexId = regexId;
    }

    /**
     * messageId
     * @return messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * messageId
     * @param messageId messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
