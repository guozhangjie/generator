package com.max.framework.util.string;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.max.framework.util.coder.DesCoder;

/**
 * 字符串工具类
 * @author 马青松
 */
public class StringUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(StringUtil.class);

    /**
     * 编码格式iso-8859-1
     */
    public static final String CODER_ISO_8859 = "iso-8859-1";

    /**
     * 判定字符串是否为null或者""
     * @param data 字符串
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(String data) {
        if (data == null || "".equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * 判定字符串是否为null或者""
     * @param data 字符串
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Object data) {
        if (data == null || "".equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * 任何类型转字符串,屏蔽掉null的情况
     * @param data 转化数据
     * @return 结果
     */
    public static String toString(Object data) {
        if (data == null) {
            return "";
        } else {
            return data.toString();
        }
    }

    /**
     * 字符串转int,屏蔽null
     * @param data 转化数据
     * @return 整数结果
     */
    public static int convertStringtoInt(Object data) {
        if (data != null && !isNullOrEmpty(data.toString())) {
            return Integer.parseInt(data.toString());
        } else {
            return 0;
        }
    }

    /**
     * 字符串转BigDecaimal,屏蔽null
     * @param data 转化数据
     * @return 整数结果
     */
    public static BigDecimal convertStringtoDecimal(Object data) {
        if (data != null && !isNullOrEmpty(data.toString())) {
            return new BigDecimal(data.toString());
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 字符串数组转型为整数数组
     * @param datas 转化数据
     * @return 整数数组结果
     */
    public static Integer[] convertStringArrayToIntArray(String[] datas) {
        if (datas != null && datas.length != 0) {
            Integer[] intArray = new Integer[datas.length];
            for (int i = 0; i < datas.length; i++) {
                intArray[i] = convertStringtoInt(datas[i]);
            }

            return intArray;
        } else {
            return null;
        }
    }

    /**
     * utf-8字符串转iso-8859-1解决文字乱码问题
     * @param utf8Data utf8Data
     * @param isIe true是IE浏览器, False非IE浏览器
     * @return iso-8859-1字符串
     */
    public static String convertUtf8ToIso(String utf8Data, boolean isIe) {
        if (isNullOrEmpty(utf8Data)) {
            return "";
        }
        try {
            if (isIe) {
                return java.net.URLEncoder.encode(utf8Data, DesCoder.CODER_UTF8);
            } else {
                return new String(utf8Data.replace(" ", "_").getBytes(DesCoder.CODER_UTF8), CODER_ISO_8859);
            }
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
            return "";
        }
    }

    /**
     * 将大写字母前加入_，变成小写
     * @param param 元字符
     * @return 目标字符
     */
    public static String getUpperCharToUnderLine(String param) {
        Pattern pattern = Pattern.compile("[A-Z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = pattern.matcher(param);
        int index = 0;
        while (mc.find()) {
            builder.replace(mc.start() + index, mc.end() + index, "_" + mc.group().toLowerCase());
            index++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 格式化数字
     * @param src 元字符串
     * @param strLength 长度
     * @param perfix 前缀
     * @return 结果
     */
    public static String formatNumber(String src, int strLength, String perfix) {
        if (isNullOrEmpty(src)) {
            return "";
        }
        if (isNullOrEmpty(perfix)) {
            perfix = "0";
        }
        int strLen = src.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append(perfix).append(src);// 左补0
                src = sb.toString();
                strLen = src.length();
            }
        }
        return src;
    }
    
    /**
     * 格式化数字
     * @param src 元字符串
     * @param strLength 长度
     * @param perfix 前缀
     * @return 结果
     */
    public static String formatNumber(int src, int strLength, String perfix) {
        String strSrc = Integer.toString(src);
        if (isNullOrEmpty(perfix)) {
            perfix = "0";
        }
        int strLen = strSrc.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append(perfix).append(strSrc);// 左补0
                strSrc = sb.toString();
                strLen = strSrc.length();
            }
        }
        return strSrc;
    }
}
