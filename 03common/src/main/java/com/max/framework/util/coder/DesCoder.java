package com.max.framework.util.coder;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

/**
 * DES加密介绍 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 * 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 * 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现 。
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DesCoder {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(DesCoder.class);
    /**
     * 编码格式ASCII
     */
    private static final String CODER_ASCII = "ASCII";
    /**
     * 编码格式UTF-8
     */
    public static final String CODER_UTF8 = "UTF-8";
    /**
     * DES模式
     */
    private static final String DES_MODE = "DES";
    /**
     * 加密方式选择
     */
    private static final String CIPHER_MODE = "DES/CBC/PKCS5Padding";
    /**
     * 16进制数字列表
     */
    private static final String HEXSTRING_LIST = "0123456789ABCDEF";
    /**
     * 默认密钥
     */
    public static final String DEFAULT_PASSWORD_KEY = "11001100";

    /**
     * 加密
     * @param datasource 加密数据
     * @param password String 密钥
     * @return String 字符串字节
     */
    public static String encrypt(String datasource, String password) {
        try {
            DESKeySpec desKey;
            desKey = new DESKeySpec(password.getBytes(CODER_ASCII));
            IvParameterSpec ivSpec = new IvParameterSpec(password.getBytes(CODER_ASCII));
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_MODE);
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, ivSpec);
            // 现在，获取数据并加密
            // 正式执行加密操作
            byte[] enByteParams = cipher.doFinal(datasource.getBytes(CODER_UTF8));

            String hexString = convertByteToHexString(enByteParams);
            return hexString.toUpperCase();
        } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 解密
     * @param datasource 解密数字
     * @param password 密码
     * @return byte[] 二进制
     */
    public static String decrypt(String datasource, String password) {
        byte[] src = convertHexStringToByte(datasource);
        DESKeySpec desKey;
        try {
            desKey = new DESKeySpec(password.getBytes(CODER_ASCII));
            IvParameterSpec ivSpec = new IvParameterSpec(password.getBytes(CODER_ASCII));
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_MODE);
            SecretKey securekey = keyFactory.generateSecret(desKey);

            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, ivSpec);
            // 真正开始解密操作
            return new String(cipher.doFinal(src), CODER_UTF8);
        } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 二进制转16进制
     * @param data 二进制
     * @return 16进制
     */
    public static String convertByteToHexString(byte[] data) {
        StringBuilder returnVal = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            returnVal.append(hex);
        }
        return returnVal.toString();
    }

    /**
     * 16进制转二进制
     * @param hexString 二进制
     * @return 二进制
     */
    public static byte[] convertHexStringToByte(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] src = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            src[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return src;
    }

    /**
     * char转化为byte
     * @param charDot 字符
     * @return byte 字节
     */
    private static byte charToByte(char charDot) {
        return (byte) HEXSTRING_LIST.indexOf(charDot);
    }
}