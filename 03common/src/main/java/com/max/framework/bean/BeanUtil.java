package com.max.framework.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.max.framework.core.exception.SystemException;
import com.max.framework.core.ognl.OgnlCache;
import com.max.framework.core.validate.RegType;
import com.max.framework.core.validate.Regex;
import com.max.framework.util.date.SystemDateUtil;
import com.max.framework.util.string.StringUtil;

import ognl.OgnlException;

/**
 * 对象处理工具类
 * @author 马青松
 */
public class BeanUtil {
    /**
     * 两个对象复制同名属性,根据注解进行类型转换
     * @param src 源对象
     * @param desc 目标对象
     * @param propertis 属性列表
     */
    private static void copyRule(Object src, Object desc, String[] propertis, BeanCopyImp imp) {
        for (String pro : propertis) {
            Object value;
            try {
                value = OgnlCache.getValue(pro, src);
                
                Class<?> targetClass = src.getClass();
                Field field = null;
                Regex regex = null;

                while (true) {
                    try {
                        field = targetClass.getDeclaredField(pro);
                        regex = field.getAnnotation(Regex.class);
                    } catch (NoSuchFieldException | SecurityException exx) {
                        targetClass = targetClass.getSuperclass();
                        if (targetClass == null) {
                            throw new SystemException(exx.getMessage());
                        }
                        continue;
                    }
                    break;
                }
                
                if (regex == null) {
                    imp.execNoRegex(pro, desc, value);
                    continue;
                }
                
                if (value != null) {
                    imp.execNotNull(pro, desc, value, regex);
                } else {
                    imp.execNull(pro, desc, value);
                }
            } catch (OgnlException ex) {
                throw new SystemException(ex.getMessage());
            } 
        }
    }

    /**
     * 两个对象复制同名属性,根据注解进行类型转换
     * @param src 源对象
     * @param desc 目标对象
     * @param propertis 属性列表
     */
    public static void copy(Object src, Object desc, String[] propertis) {
        copyRule(src, desc, propertis, new BeanCopyImp() {
            /**
             * {@inheritDoc}
             */
            public void execNull(String pro, Object desc, Object value) throws OgnlException {
                OgnlCache.setValue(pro, desc, null);
            }

            /**
             * {@inheritDoc}
             */
            public void execNotNull(String pro, Object desc, Object value, Regex regex) throws OgnlException {
                if (regex.regType() == RegType.bigdecimal) {
                    if (StringUtil.isNullOrEmpty(value)) {
                        OgnlCache.setValue(pro, desc ,null);
                    } else {
                        OgnlCache.setValue(pro, desc, StringUtil.convertStringtoDecimal(value));
                    }
                } else if (regex.regType() == RegType.date) {
                    if (StringUtil.isNullOrEmpty(value)) {
                        OgnlCache.setValue(pro, desc ,null);
                    } else {
                        OgnlCache.setValue(pro, desc, SystemDateUtil.getDateFormString(value.toString()));
                    }
                } else if (regex.regType() == RegType.interger) {
                    if (StringUtil.isNullOrEmpty(value)) {
                        OgnlCache.setValue(pro, desc, null);
                    } else {
                        OgnlCache.setValue(pro, desc, StringUtil.convertStringtoInt(value));
                    }
                } else {
                    OgnlCache.setValue(pro, desc, value);
                }
            }

            /**
             * {@inheritDoc}
             */
            public void execNoRegex(String proName, Object desc, Object value) throws OgnlException {
                OgnlCache.setValue(proName, desc, value);
            }

        });
    }

    /**
     * 转化bean的内容为Map
     * @param src 对象
     * @param propertis 转化属性
     * @return Map对象
     */
    public static Map<String, Object> copyToMap(Object src, String[] propertis) {
        Map<String, Object> map = new HashMap<>();
        copyRule(src, map, propertis, new BeanCopyImp() {
            /**
             * {@inheritDoc}
             */
            public void execNull(String pro, Object desc, Object value) throws OgnlException {
            }

            /**
             * {@inheritDoc}
             */
            public void execNotNull(String pro, Object desc, Object value, Regex regex) throws OgnlException {

                if (regex.regType() == RegType.bigdecimal) {
                    map.put(pro, StringUtil.convertStringtoDecimal(value));
                } else if (regex.regType() == RegType.date) {
                    map.put(pro, SystemDateUtil.getDateFormString(value.toString()));
                } else if (regex.regType() == RegType.interger) {
                    map.put(pro, StringUtil.convertStringtoInt(value));
                } else {
                    map.put(pro, value);
                }
            }

            /**
             * {@inheritDoc}
             */
            public void execNoRegex(String proName, Object desc, Object value) throws OgnlException {
                map.put(proName, value);
            }

        });

        return map;
    }
}

/**
 * copy接口
 * @author max
 */
interface BeanCopyImp {
    /**
     * 没有注解
     * @param proName 属性名
     * @param desc 目标对象
     * @param value 值
     * @throws OgnlException OgnlException
     */
    void execNoRegex(String proName, Object desc, Object value) throws OgnlException;
    
    /**
     * copy如果值为null
     * @param proName 属性名
     * @param desc 目标对象
     * @param value 值
     * @throws OgnlException OgnlException
     */
    void execNull(String proName, Object desc, Object value) throws OgnlException;

    /**
     * copy如果值不为null
     * @param proName 属性名
     * @param desc 目标对象
     * @param value 值
     * @param regex 规则注解
     * @throws OgnlException OgnlException
     */
    void execNotNull(String proName, Object desc, Object value, Regex regex) throws OgnlException;
}
