package com.max.framework.core.codename;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CodeName表单验证
 * @author 马青松
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CodeNameChecker {
    /**
     * CodeId
     * @return CodeId
     */
    String CodeId();

    /**
     * 指定验证范围
     * @return 指定验证范围
     */
    String[] checkRanger() default {};

    /**
     * 消息ID
     * @return 消息ID
     */
    String messageId() default "S0022";

    /**
     * 消息参数
     * @return 消息参数
     */
    String messageParam();
}
