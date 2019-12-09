package com.max.framework.core.ognl;

import java.util.HashMap;
import java.util.Map;

import ognl.ClassResolver;

/**
 * OgnlClassResolver
 * @author Daniel Guggi
 */
public class OgnlClassResolver implements ClassResolver {
    /**
     * classes
     */
    private Map<String, Class<?>> classes = new HashMap<String, Class<?>>(101);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public Class classForName(String className, Map context) throws ClassNotFoundException {
        Class<?> result = null;
        if ((result = classes.get(className)) == null) {
            try {
                result = Resources.classForName(className);
            } catch (ClassNotFoundException e1) {
                if (className.indexOf('.') == -1) {
                    result = Resources.classForName("java.lang." + className);
                    classes.put("java.lang." + className, result);
                }
            }
            classes.put(className, result);
        }
        return result;
    }
}
