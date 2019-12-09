package com.max.framework.ruler.form;

import java.util.List;

import com.max.framework.core.message.Message;

/**
 * 删除validate规范
 * @author maqs
 */
public interface DeleteValidator {
    /**
     * 删除validate规范
     * @return 错误消息
     */
    public List<Message> validateDelete();
}
