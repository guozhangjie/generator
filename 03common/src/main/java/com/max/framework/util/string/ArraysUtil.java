package com.max.framework.util.string;

import java.util.List;

/**
 * 数组和集合工具类工具
 */
public class ArraysUtil {
    /**
     * 数组和集合工具类是否为空
     * @param arrays 数组和集合
     * @return true:空, false:非空
     */
    public static boolean isNullOrEmpty(Object[] arrays) {
        if (arrays != null && arrays.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 数组和集合工具类是否为空
     * @param arrays 数组和集合
     * @return true:空, false:非空
     */
    public static boolean isNullOrEmpty(List<?> arrays) {
        if (arrays != null && arrays.size() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
