package com.max.framework.util.random;

import java.util.Random;

import com.max.framework.util.date.SystemDateUtil;

/**
 * 随机数类
 * @author gao
 */
public class RandomUtil {
    /**
     * 默认长度
     */
    private static final int DEFAULT_LENGTH = 3;
    
    /**
     * 生成默认三位长度随机数 
     * @return 生成String类型随机数
     */
    public static String getRandomNumber() {
        return getRandomNumber(DEFAULT_LENGTH);
    }
    
    /**
     * 生成指定长度随机数
     * @param length 指定长度
     * @return 生成String类型随机数
     */
    public static String getRandomNumber(int length) {
        int index;
        // 生成的长度
        int count = 0;
        char[] str = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer buffer = new StringBuffer("");
        Random random = new Random();
        // 生成随机数,取绝对值,防止生成负数,随机生成10以内的数字,从str中获取然后拼接
        while (count < length) {
            index = Math.abs(random.nextInt(9));
            buffer.append(str[index]);
            count++;
        }
        return buffer.toString();
    }
    
    /**
     * 获得唯一code值(yyyyMMddHHmmSSSXXXXX)
     * @param length 随机数长度
     * @return 生成String类型唯一码
     */
    public static String getUniqueCode(int length) {
        return SystemDateUtil.getSystemDateTimeMillisecondString() + getRandomNumber(length);
    }
    
    /**
     * 获得唯一code值默认随机数3位(yyyyMMddHHmmSSSXXX)
     * @return 生成String类型唯一码
     */
    public static String getUniqueCode() {
        return SystemDateUtil.getSystemDateTimeMillisecondString() + getRandomNumber(DEFAULT_LENGTH); 
    }
}
