package com.max.framework.core.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.max.framework.bean.CodeName;
import com.max.framework.core.codename.CodeNameChecker;
import com.max.framework.core.codename.CodeNameUtil;
import com.max.framework.core.exception.SystemException;
import com.max.framework.core.message.Message;
import com.max.framework.core.message.MessageUtil;
import com.max.framework.core.ognl.OgnlCache;
import com.max.framework.util.string.StringUtil;

import ognl.OgnlException;

/**
 * 验证工具类
 * @author 马青松
 */
public class ValidateUtil {

    /**
     * 针对属性进行验证不通过则直接抛出System异常
     * @param <T> 类型
     * @param targetObj 目标对象
     * @param properties 验证属性
     */
    public static <T> void validatePropertiesException(T targetObj, String[] properties) {
        List<Message> list = validateProperties(targetObj, properties);
        if (!list.isEmpty()) {
            throw new SystemException(list);
        }
    }

    /**
     * 针对属性进行验证
     * @param <T> 类型
     * @param targetObj 目标对象
     * @param properties 验证属性
     * @return 返回消息
     */
    public static <T> List<Message> validateProperties(T targetObj, String[] properties) {
        List<Message> list = new ArrayList<Message>();
        // 反射获得要检证的注解
        Class<?> targetClass = targetObj.getClass();
        for (String proname : properties) {
            Field fields = null;
            while (true) {
                try {
                    fields = targetClass.getDeclaredField(proname);
                } catch (NoSuchFieldException | SecurityException exx) {
                    targetClass = targetClass.getSuperclass();
                    if (targetClass == null) {
                        throw new SystemException(exx.getMessage());
                    }
                    continue;
                }
                break;
            }

            try {
                Annotation[] annotations = fields.getAnnotations();
                for (Annotation anno : annotations) {
                    if ("com.max.framework.core.validate.Requied".equals(anno.annotationType().getName())) {
                        Requied requied = (Requied) anno;
                        Object value;
                        value = OgnlCache.getValue(proname, targetObj);
                        checkRequiedValidate(value, proname, requied, list);
                    } else if ("com.max.framework.core.validate.Regex".equals(anno.annotationType().getName())) {
                        Regex regex = (Regex) anno;
                        Object value = OgnlCache.getValue(proname, targetObj);
                        checkRegexValidate(value, proname, regex, list);
                    } else if ("com.max.framework.core.codename.CodeNameChecker".equals(anno.annotationType().getName())) {
                        // 进行codeName验证
                        CodeNameChecker codeNameChecker = (CodeNameChecker) anno;
                        Object value = OgnlCache.getValue(proname, targetObj);
                        checkCodeNameValidate(value, proname, codeNameChecker, list);
                    } else if ("com.max.framework.core.validate.Regexs".equals(anno.annotationType().getName())) {
                        Regexs regexs = (Regexs) anno;
                        checkRegexsValidate(targetObj, proname, regexs, list);
                    }
                }
            } catch (OgnlException ex) {
                throw new SystemException(ex.getMessage());
            }
        }
        return list;
    }

    /**
     * 添加message
     * @param proname 属性名
     * @param messageId 消息内容
     * @param messageParams 消息参数
     * @param list 消息数组
     * @param isArrayMessage 是否是数组消息
     * @param errorIndex 数组时的角标
     */
    private static void addValidateMessage(String proname, String messageId, Object[] messageParams, List<Message> list, boolean isArrayMessage,
            String errorIndex) {
        if (isArrayMessage) {
            Message message = MessageUtil.createMessage(proname, messageId, null, messageParams);
            if (StringUtil.isNullOrEmpty(errorIndex)) {
                errorIndex = "1";
            }
            message.setMessageInfo(message.getMessageInfo() + "(" + errorIndex + ")");
            list.add(message);
        } else {
            Message message = MessageUtil.createMessage(proname, messageId, null, messageParams);
            list.add(message);
        }
    }

    /**
     * 必须输入验证内容
     * @param value 验证内容
     * @param proname 验证属性
     * @param requied 必须输入注解
     * @param list 消息内容
     */
    private static void checkRequiedValidate(Object value, String proname, Requied requied, List<Message> list) {
        if (value instanceof String[]) {
            // 如果是数组的情况下单独进行验证
            String[] values = (String[]) value;
            if (values.length == 0) {
                addValidateMessage(proname, requied.messageId(), requied.messageParams(), list, true, null);
            }
            int errorIndex = 0;
            for (String vl : values) {
                errorIndex++;
                if (StringUtil.isNullOrEmpty(vl)) {
                    addValidateMessage(proname, requied.messageId(), requied.messageParams(), list, true, Integer.toString(errorIndex));
                }
            }
        } else {
            if (StringUtil.isNullOrEmpty(value)) {
                addValidateMessage(proname, requied.messageId(), requied.messageParams(), list, false, null);
            }
        }
    }

    /**
     * 正则表达式验证内容
     * @param value 验证内容
     * @param proname 验证属性
     * @param Regex 正则表达式注解
     * @param list 消息内容
     */
    private static void checkRegexValidate(Object value, String proname, Regex regex, List<Message> list) {
        if (value instanceof String[]) {
            // 如果是数组的情况下单独进行验证
            String[] values = (String[]) value;
            if (values.length == 0) {
                return;
            }
            int errorIndex = 0;
            for (String vl : values) {
                errorIndex++;
                checkRegIsAllMatch(vl, proname, regex, list, true, Integer.toString(errorIndex));
            }
        } else {
            checkRegIsAllMatch(value, proname, regex, list, false, null);
        }
    }

    /**
     * codeNameCheck
     * @param value 验证内容
     * @param proname 属性
     * @param codeNameChecker codeName注解对象
     * @param list 消息
     */
    private static void checkCodeNameValidate(Object value, String proname, CodeNameChecker codeNameChecker, List<Message> list) {
        if (StringUtil.isNullOrEmpty(value)) {
            // 没有数据的时候不进行code处理
            return;
        }
        boolean isFind = false;
        if (codeNameChecker.checkRanger() == null || codeNameChecker.checkRanger().length == 0) {
            // 使用全部code时
            List<CodeName> codes = CodeNameUtil.getCodeNamesByCodeId(codeNameChecker.CodeId());
            for (CodeName code : codes) {
                if (code.getCodeValue().toString().equals(value.toString())) {
                    isFind = true;
                }
            }
        } else {
            // 指定code范围时
            for (String codeValueId : codeNameChecker.checkRanger()) {
                if (codeValueId.equals(value)) {
                    isFind = true;
                }
            }
        }
        if (!isFind) {
            // 没有检索到对应code的情况下
            Message message = MessageUtil.createMessage(proname, codeNameChecker.messageId(), null, codeNameChecker.messageParam(), value);
            list.add(message);
        }
    }

    /**
     * 组合正则表达式验证
     * @param targetObj 验证目标
     * @param proname 属性名称
     * @param regexs 组合正则表达式
     * @param list 消息内容
     * @throws OgnlException OgnlException
     */
    private static <T> void checkRegexsValidate(T targetObj, String proname, Regexs regexs, List<Message> list) throws OgnlException {
        for (Regex regex : regexs.value()) {
            Object value = OgnlCache.getValue(proname, targetObj);
            if (value instanceof String[]) {
                // 如果是数组的情况下单独进行验证
                String[] values = (String[]) value;
                if (values.length == 0) {
                    continue;
                }
                int errorIndex = 0;
                boolean isExit = false;
                for (String vl : values) {
                    errorIndex++;
                    int res = checkRegIsAllMatch(vl, proname, regex, list, true, Integer.toString(errorIndex));
                    if (res == -1) {
                        isExit = true;
                    }
                }
                if (isExit) {
                    break;
                }
            } else {
                int res = checkRegIsAllMatch(value, proname, regex, list, false, null);
                if (res == -2) {
                    break;
                }
            }
        }
    }

    /**
     * 根据配置检查正则使用部分匹配还是全部匹配
     * @param value 验证内容
     * @param proname 属性名称
     * @param regex 正则表达式对象
     * @param list 消息内容
     * @param isArrayMessage 是否是数组消息
     * @param errorIndex 数组角标
     * @return -1 不需要进行正则验证 -2 正则验证没有通过 1通过正则验证
     */
    private static int checkRegIsAllMatch(Object value, String proname, Regex regex, List<Message> list, boolean isArrayMessage, String errorIndex) {
        if (StringUtil.isNullOrEmpty(value)) {
            // 没有数据的时候不进行正则表达式处理
            return -1;
        }
        // 判断类型进行正则表达式处理
        String regexId = regex.regexId();
        String messageId = regex.messageId();
        RegType regType = regex.regType();
        if (!regType.equals(RegType.custom)) {
            regexId = regType.getRegexId();
            messageId = regType.getMessageId();
        }

        Object[] regParams = regex.regexParams();
        if (regex.isAllMatch()) {
            if (!value.toString().matches(RegexUtil.getRegex(regexId, regParams))) {
                addValidateMessage(proname, messageId, regex.messageParams(), list, isArrayMessage, errorIndex);
                return -2;
            }
        } else {
            Pattern pattern = Pattern.compile(RegexUtil.getRegex(regexId, regParams));
            Matcher matcher = pattern.matcher(value.toString());
            if (!matcher.find()) {
                addValidateMessage(proname, messageId, regex.messageParams(), list, isArrayMessage, errorIndex);
                return -2;
            }
        }
        return 1;
    }
}
