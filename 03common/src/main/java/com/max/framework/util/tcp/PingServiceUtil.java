package com.max.framework.util.tcp;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.log4j.Logger;

/**
 * ping服务通不通
 */
public class PingServiceUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(PingServiceUtil.class);
    /**
     * 超时时间
     */
    private static final int TIMEOUT_TIME = 3000;

    /**
     * ping 服务器通不通
     * @param serviceIp 服务器IP
     * @return true:通, false:不通
     */
    public static boolean pingService(String serviceIp) {
        // ping 服务器IP 超时在3钞以上 视为ping不通
        try {
            return InetAddress.getByName(serviceIp).isReachable(TIMEOUT_TIME);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(pingService("asdadas"));;
    }
}
