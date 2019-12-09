package com.max.framework.core.codename;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.max.framework.bean.CodeName;
import com.max.framework.core.DataLoadGetTemplate;
import com.max.framework.core.exception.SystemException;
import com.max.framework.core.ognl.OgnlCache;
import com.max.framework.util.string.StringUtil;

import ognl.OgnlException;

/**
 * CODENAME工具类
 * @author 马青松
 */
public class CodeNameUtil {
    /**
     * 数据加载模板
     */
    private static DataLoadGetTemplate<List<CodeName>> CODENAME_DATALOAD;
    
    /**
     * 空codename
     */
    private static final CodeName EMPTY_CODENAME = new CodeName(); 

    /**
     * 根据CodeId获得该组CodeName
     * @param codeId codeId
     * @return 该组CodeName
     */
    public static List<CodeName> getCodeNamesByCodeId(String codeId) {
        return getByKey(codeId);
    }

    /**
     * 根据CodeId和codeValue获得一个CodeName
     * @param codeId codeId
     * @param codeValue codeValue
     * @return 一个CodeName
     */
    public static CodeName getCodeNamesByCodeIdAndCodeValue(String codeId, String codeValue) {
        List<CodeName> masterCodeNames = getByKey(codeId);
        for (CodeName codeName : masterCodeNames) {
            if (codeValue.equals(codeName.getCodeValue())) {
                return codeName;
            }
        }
        return EMPTY_CODENAME;
    }

    /**
     * 根据一个对象内部的code值获得对应的CodeName
     * @param entity 源对象
     * @param srcExpress 源对象对应字段
     * @param codeId CodeId
     * @return CodeName CodeName
     */
    public static CodeName getEntityCodeNameValue(Object entity, String srcExpress, String codeId) {
        Object srcValue = null;
        try {
            srcValue = OgnlCache.getValue(srcExpress, entity);
        } catch (OgnlException ex) {
            throw new SystemException(ex.getMessage());
        }
        if (StringUtil.isNullOrEmpty(srcValue)) {
            return EMPTY_CODENAME;
        }
        String codeValue = srcValue.toString();
        CodeName codeName = getCodeNamesByCodeIdAndCodeValue(codeId, codeValue);
        return codeName;
    }

    /**
     * CodeName向本对象内信息设置
     * @param entity 源对象
     * @param srcExpress 源对象对应字段
     * @param codeId CodeId
     * @param descExpress 设置目标对象的ognl
     */
    public static void setEntityCodeNameValue(Object entity, String srcExpress, String codeId, String descExpress) {
        CodeName codeName = getEntityCodeNameValue(entity, srcExpress, codeId);
        if (codeName != null) {
            try {
                OgnlCache.setValue(descExpress, entity, codeName);
            } catch (OgnlException ex) {
                throw new SystemException(ex.getMessage());
            }
        }
    }

    /**
     * 依靠注解批量注入其关联的codeName对象内容
     * @param entity 对象或者对象List
     * @param includeFileds 进行处理的字段
     */
    @SuppressWarnings("unchecked")
    public static void setEntityCodeNameByAnotation(Object entity, String[] includeFileds) {
        if (entity == null) {
            return;
        }
        // 排序以进行二分检索
        Arrays.sort(includeFileds);
        Class<?> targetClass = null;

        if (entity instanceof List) {
            List<Object> entitys = (List<Object>) entity;

            for (Object tagert : entitys) {
                // 反射获得要检证的注解
                if (targetClass == null) {
                    targetClass = tagert.getClass();
                }

                Field[] fields = targetClass.getDeclaredFields();
                for (Field field : fields) {
                    if (Arrays.binarySearch(includeFileds, field.getName()) < 0) {
                        continue;
                    }
                    Annotation annotation = field.getAnnotation(CodeNameRelation.class);
                    if (annotation != null) {
                        CodeNameRelation codeNameRelation = (CodeNameRelation) annotation;
                        setEntityCodeNameValue(tagert, codeNameRelation.srcExpress(), codeNameRelation.CodeId(), field.getName());
                    }
                }
            }
        } else {
            targetClass = entity.getClass();
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (Arrays.binarySearch(includeFileds, field.getName()) < 0) {
                    continue;
                }
                Annotation annotation = field.getAnnotation(CodeNameRelation.class);
                if (annotation != null) {
                    CodeNameRelation codeNameRelation = (CodeNameRelation) annotation;
                    setEntityCodeNameValue(entity, codeNameRelation.srcExpress(), codeNameRelation.CodeId(), field.getName());
                }
            }
        }
    }

    /**
     * getByKey
     * @param key key
     * @return data
     */
    public static List<CodeName> getByKey(String key) {
        return CODENAME_DATALOAD.getDataByKey(key);
    }
    
    /**
     * 加载数据
     * @return 加载数据
     */
    public static DataLoadGetTemplate<List<CodeName>> getCodenameDataload() {
        return CODENAME_DATALOAD;
    }

    /**
     * 加载数据
     * @param codenameDataload 加载数据
     */
    public static void setCodenameDataload(DataLoadGetTemplate<List<CodeName>> codenameDataload) {
        CODENAME_DATALOAD = codenameDataload;
    }
}
