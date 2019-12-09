package com.max.framework.util.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * 二进制数组和流进行相互转化的工具类
 * @author 马青松
 */
public class ByteArrayUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(ByteArrayUtil.class);
    
    /**
     * 将输入流转化为二进制数组
     * @param in 输入流
     * @return 二进制数组
     */
    public static byte[] toByteArray(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int index = 0;
        try {
            while ((index = in.read(buffer)) != -1) {
                out.write(buffer, 0, index);
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return null;
        }
        return out.toByteArray();
    }
    
    /**
     * 将二进制数组转化为InputStream
     * @param datas 二进制数组
     * @return 流
     */
    public static InputStream toInputStream(byte[] datas) {
        ByteArrayInputStream bais = new ByteArrayInputStream(datas);
        return bais;
    }
}
