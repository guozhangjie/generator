package com.max.framework.util.tcp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.max.framework.core.message.Message.MessageType;
import com.max.framework.core.message.MessageUtil;
import com.max.framework.util.tcp.TcpResultBean.TcpResultType;

import net.sf.json.JSONObject;

/**
 * 发送TCP工具类
 * @author 马青松
 */
public class TcpClientRequestUtil {
    /**
     * 编码格式
     */
    public static final String CODE_FORMAT = "utf-8";

    /**
     * 命令KEY
     */
    public static final String COMMAND_KEY = "command";

    /**
     * 密码KEY
     */
    public static final String PASSWORD_KEY = "password";
    
    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(TcpClientRequestUtil.class);
    
    /**
     * 发送请求
     * @param requestIp 请求IP
     * @param port 端口
     * @param command 指定命令
     * @param params 参数
     * @param codeFormat 编码格式
     * @return 结果
     */
    public static TcpResultBean sendClientRequest(String requestIp, int port, String command, JSONObject params, String codeFormat) {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            socket = new Socket(requestIp, port);

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, codeFormat)), true);
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            params.put(PASSWORD_KEY, ConstAuth.password);
            params.put(COMMAND_KEY, command);
            String json = params.toString();
            printWriter.println(json);

            logger.info("client send params:" + json);
            TcpResultBean tcpResultBean = (TcpResultBean) ois.readObject();
            logger.info("client accept reslut");

            return tcpResultBean;
        } catch (UnknownHostException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        TcpResultBean tcpResultBean = new TcpResultBean();
        logger.info(MessageUtil.createMessage("", "M9020", MessageType.ERROR, requestIp));
        tcpResultBean.makeResultMessages(MessageUtil.createMessage("", "M9020", MessageType.ERROR, requestIp));
        tcpResultBean.setTcpResultType(TcpResultType.ERROR);
        return tcpResultBean;
    }
    
    /**
     * 发送请求
     * @param requestIp 请求IP(默认编码格式是utf-8)
     * @param port 端口
     * @param command 指定命令
     * @param params 参数
     * @return 结果
     */
    public static TcpResultBean sendClientRequest(String requestIp, int port, String command, JSONObject params) {
        return sendClientRequest(requestIp, port, command, params, CODE_FORMAT);
    }
}
