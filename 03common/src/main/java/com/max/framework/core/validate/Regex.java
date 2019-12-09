package com.max.framework.core.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 正则表达式注解
 * @author 马青松
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(value = Regexs.class)
public @interface Regex {
    /**
     * 正则表达式ID
     * @return 正则表达式ID
     */
    String regexId() default "";

    /**
     * 正则表达式参数
     * @return 正则表达式参数
     */
    String[] regexParams() default {};

    /**
     * 消息ID
     * @return 消息ID
     */
    String messageId() default "";

    /**
     * 消息参数
     * @return 消息参数
     */
    String[] messageParams() default {};
    
    /**
     * 是否是全部匹配
     * @return 是否是全部匹配
     */
    boolean isAllMatch() default true;
    
    /**
     * 正则表达式类型
     * @return 正则表达式类型
     */
    RegType regType() default RegType.custom ;
}