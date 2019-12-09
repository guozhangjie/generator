package com.max.framework.core.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重复注解支持
 * @author 马青松
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)  
public @interface Regexs {  
    /**
     * 重复注解支持
     * @return 重复注解支持
     */
    Regex[] value();  
}  