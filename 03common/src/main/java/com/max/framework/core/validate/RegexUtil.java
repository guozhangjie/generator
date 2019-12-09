package com.max.framework.core.validate;

import java.text.MessageFormat;

import com.max.framework.core.DataLoadGetTemplate;

/**
 * 正则验证工具类
 * @author 马青松
 */
public class RegexUtil {
    /**
     * 数据加载模板
     */
    private static DataLoadGetTemplate<String> REGEX_DATALOAD;
    

    /**
     * 获得相关正则表达式内容
     * @param key key
     * @param regexParams 参数
     * @return 关正则表达式内容
     */
    public static String getRegex(String key, Object... regexParams) {
        String regStr = getByKey(key);
        regStr = MessageFormat.format(regStr, regexParams);
        regStr = regStr.replaceAll("&lpat&", "{");
        regStr = regStr.replaceAll("&rpat&", "}");
        return regStr;
    }

    /**
     * getByKey
     * @param key key
     * @return data
     */
    public static String getByKey(String key) {
        return REGEX_DATALOAD.getDataByKey(key);
    }

    /**
     * 数据加载
     * @return 数据加载
     */
    public static DataLoadGetTemplate<String> getRegexDataload() {
        return REGEX_DATALOAD;
    }

    /**
     * 数据加载
     * @param regexDataload 数据加载
     */
    public static void setRegexDataload(DataLoadGetTemplate<String> regexDataload) {
        REGEX_DATALOAD = regexDataload;
    }
}
