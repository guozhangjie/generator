package com.max.framework.util.coder;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * base64编码格式
 * @author 马青松
 */
public class Base64Coder {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(Base64Coder.class);

    /**
     * 图片二进制数组转化为base64
     * @param src 图片数组
     * @return base64字符串
     */
    public static String encryptBase64(byte[] src) {
        if (src == null || src.length == 0) {
            return null;
        }
        // 返回Base64编码过的字节数组字符串
        return Base64.encodeBase64String(src);
    }

    /**
     * 字符串加密为base64
     * @param src 字符串
     * @return 字符串加密
     */
    public static String encryptBase64(String src) {
        try {
            byte[] data = src.getBytes(DesCoder.CODER_UTF8);
            return Base64.encodeBase64String(data);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * Base64解码并生成图片
     * @param base64Src Base64内容
     * @return 图片内容
     */
    public byte[] decryptBase64ToByte(String base64Src) {
        byte[] bytes = Base64.decodeBase64(base64Src);
        return bytes;
    }

    /**
     * base64解密为字符串
     * @param base64Src 加密字符串
     * @return 字符串
     */
    public static String decryptBase64(String base64Src) {
        byte[] bytes = Base64.decodeBase64(base64Src);
        try {
            return new String(bytes, DesCoder.CODER_UTF8);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }
}
