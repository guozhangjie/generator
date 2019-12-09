package com.max.framework.util.ftp;

import java.io.Serializable;

/**
 * ftp链接常量
 */
public class FtpBean implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 路径
     */
    private String path;

    /**
     * ip地址
     * @return ip地址
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * ip地址
     * @param ipAddr ip地址
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    /**
     * 端口号
     * @return 端口号
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 端口号
     * @param port 端口号
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 用户名
     * @return 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 密码
     * @return 密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 密码
     * @param pwd 密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * 路径
     * @return 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 路径
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path;
    }
}
