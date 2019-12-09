package com.max.framework.core.codename;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CODENAME关联注解
 * @author 马青松
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CodeNameRelation {
    /**
     * 关联字段的OGNL表达式
     * @return 关联字段的OGNL表达式
     */
    String srcExpress();

    /**
     * CodeId
     * @return CodeId
     */
    String CodeId();
}
