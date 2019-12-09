package com.max.framework.util.coder;

/**
 * 旧系统密码工具类
 * @author guozhangjie
 */
public class PasswordUtil {

    /**
     * 获取加密后字段
     * @param inputString 需要加密字符串
     * @return 加密后字符串
     */
    public static String getEncryptPassword(String inputString) {
        String endPassword = "";
        String middleString;
        long maxSize = 33;
        long powNum = 3;
        long temp = 0;
        StringBuilder sb = new StringBuilder();
        // 获取字符串ASCII码
        for (int i = 0; i < inputString.length(); i++) {
            sb.append((int) (inputString.charAt(i)));
        }
        middleString = sb.toString();
        while (!middleString.equals("")) {
            String start = middleString.substring(0, 1);
            if (start.equals("0")) {
                temp = new Long(middleString.substring(0, 1));
            } else {
                temp = new Long(middleString.substring(0, (middleString.length() > 2 ? 2 : middleString.length())));
            }
            // 将明文分组，且每组均小于N（N=33）
            if (temp >= maxSize) {
                temp = new Long(middleString.substring(0, 1));
                middleString = middleString.substring(1);
            } else {
                middleString = middleString.substring(middleString.length() > 2 ? 2 : middleString.length());
            }
            // 加密密文
            long encryption = (long) Math.pow((double) temp, (double) powNum);
            // 根据加密公式计算密文
            encryption %= maxSize;
            endPassword = endPassword + encryption;
        }
        return endPassword;
    }
}
