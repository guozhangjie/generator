package com.max.framework.ruler.form;

import java.util.List;

import com.max.framework.core.message.Message;

/**
 * 检索validate规范
 * @author maqs
 */
public interface SearchValidator {
    /**
     * 检索validate规范
     * @return 错误消息
     */
    public List<Message> validateSearch();
}
