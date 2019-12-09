package com.max.framework.core.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 必须输入注解
 * @author 马青松
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Requied {
    /**
     * 消息ID
     * @return 消息ID
     */
    String messageId() default "S0001";

    /**
     * 消息参数
     * @return 消息参数
     */
    String[] messageParams() default {};
}
