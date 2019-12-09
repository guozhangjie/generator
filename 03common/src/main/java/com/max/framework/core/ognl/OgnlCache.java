package com.max.framework.core.ognl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * Ognl表达式工具类
 * @author 马青松
 */
public final class OgnlCache {
    /**
     * expressionCache
     */
    private static final Map<String, Object> expressionCache = new ConcurrentHashMap<String, Object>();

    /**
     * OgnlCache()
     */
    private OgnlCache() {
    }

    /**
     * 根据表达式获得值
     * @param expression 表达式
     * @param root 根对象
     * @return 值
     * @throws OgnlException 异常
     */
    @SuppressWarnings("unchecked")
    public static Object getValue(String expression, Object root) throws OgnlException {
        Map<Object, OgnlClassResolver> context = Ognl.createDefaultContext(root, new OgnlClassResolver());
        return Ognl.getValue(parseExpression(expression), context, root);
    }

    /**
     * 根据表达式设置值
     * @param expression 表达式
     * @param root 根对象
     * @param value 值
     * @throws OgnlException 异常
     */
    @SuppressWarnings("unchecked")
    public static void setValue(String expression, Object root, Object value) throws OgnlException {
        try {
            Map<Object, OgnlClassResolver> context = Ognl.createDefaultContext(root, new OgnlClassResolver());
            Ognl.setValue(parseExpression(expression), context, root, value);
        } catch (OgnlException ex) {
            throw new OgnlException("Error evaluating expression '" + expression + "'. Cause: " + ex, ex);
        }
    }

    /**
     * 解析表达式
     * @param expression 表达式
     * @return 对象
     * @throws OgnlException 异常
     */
    private static Object parseExpression(String expression) throws OgnlException {
        Object node = expressionCache.get(expression);
        if (node == null) {
            node = Ognl.parseExpression(expression);
            expressionCache.put(expression, node);
        }
        return node;
    }
}
