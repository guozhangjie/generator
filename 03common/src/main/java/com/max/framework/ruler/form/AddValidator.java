package com.max.framework.ruler.form;

import java.util.List;

import com.max.framework.core.message.Message;

/**
 * 增加validate规范
 * @author maqs
 */
public interface AddValidator {
    /**
     * 增加validate规范
     * @return 错误消息
     */
    public List<Message> validateAdd();
}
