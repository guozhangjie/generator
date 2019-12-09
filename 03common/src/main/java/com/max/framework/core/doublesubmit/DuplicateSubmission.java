package com.max.framework.core.doublesubmit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交
 * @author 马青松
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DuplicateSubmission {
    
    /**
     * 消息ID
     * @return 消息ID
     */
    String messageId() default "M9013";
    
    /**
     * 跳转错误页面
     * @return 跳转错误页面
     */
    String errorUri() default "/error/doublesubmit";
}
