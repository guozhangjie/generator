package com.max.framework.core.message;

import java.text.MessageFormat;

import com.max.framework.core.DataLoadGetTemplate;
import com.max.framework.core.message.Message.MessageType;

/**
 * 消息工具类
 * @author 马青松
 */
public class MessageUtil {
    /**
     * 数据加载模板
     */
    private static DataLoadGetTemplate<String> MESSAGE_DATALOAD;

    /**
     * 创建消息工具方法
     * @param propertyName 属性名
     * @param messageId 消息ID
     * @param messageType 消息类型
     * @param messageParams 消息参数
     * @return 消息对象
     */
    public static Message createMessage(String propertyName, String messageId, MessageType messageType, Object... messageParams) {
        Message msg = new Message();
        msg.setPropertyName(propertyName);
        msg.setMessageId(messageId);
        if (messageType != null) {
            msg.setMessageType(messageType);
        } else {
            msg.setMessageType(MessageType.ERROR);
        }
        String msgInfo = getByKey(messageId);
        msgInfo = MessageFormat.format(msgInfo, messageParams);
        msg.setMessageInfo(msgInfo);

        return msg;
    }
    
    /**
     * 创建消息工具方法
     * @param messageId 消息ID
     * @param messageParams 消息参数
     * @return 消息对象
     */
    public static Message createMessage(String messageId, Object... messageParams) {
        return createMessage("",messageId,MessageType.INFO, messageParams);
    }

    /**
     * getByKey
     * @param key key
     * @return data
     */
    public static String getByKey(String key) {
        return MESSAGE_DATALOAD.getDataByKey(key);
    }

    /**
     * 加载数据
     * @return 加载数据
     */
    public static DataLoadGetTemplate<String> getMessageDataLoad() {
        return MESSAGE_DATALOAD;
    }

    /**
     * 加载数据
     * @param messageDataLoad 加载数据
     */
    public static void setMessageDataLoad(DataLoadGetTemplate<String> messageDataLoad) {
        MESSAGE_DATALOAD = messageDataLoad;
    }
}
