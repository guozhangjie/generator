package com.max.framework.ruler.form;

import java.util.List;

import com.max.framework.core.message.Message;

/**
 * 修改validate规范
 * @author maqs
 */
public interface UpdateValidator {
    /**
     * 修改validate规范
     * @return 错误消息
     */
    public List<Message> validateUpdate();
}
