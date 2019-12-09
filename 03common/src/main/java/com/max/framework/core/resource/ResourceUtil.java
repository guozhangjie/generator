package com.max.framework.core.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.max.framework.core.DataLoadGetTemplate;

/**
 * 资源处理工具类
 * @author 马青松
 */
public class ResourceUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(ResourceUtil.class);
    
    /**
     * 数据加载模板
     */
    private static DataLoadGetTemplate<String> RESOURCES_DATALOAD;

    /**
     * 获得资源内容
     * @param key key
     * @return 值
     */
    public static String getResourceInfo(String key) {
        return getByKey(key);
    }

    /**
     * 加载资源
     * @param target 目标对象
     * @param path 路径
     * @return 返回结果
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, String> loadResource(Object target, String path) {
        Properties props = new Properties();
        InputStream in = null;

        try {
            in = target.getClass().getResourceAsStream(path);
            props.load(in);
            in.close();
            return new HashMap<String, String>((Map) props);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
    
    /**
     * 通过路径加载配置文件
     * @param path 路径
     * @return map
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, String> loadResource(String path) {
        Properties props = new Properties();
        InputStream in = null;

        try {
            in = new FileInputStream(new File(path));
            props.load(in);
            in.close();
            return new HashMap<String, String>((Map) props);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    /**
     * getByKey
     * @param key key
     * @return data
     */
    public static String getByKey(String key) {
        return RESOURCES_DATALOAD.getDataByKey(key);
    }

    /**
     * 加载数据
     * @return 加载数据
     */
    public static DataLoadGetTemplate<String> getResourcesDataload() {
        return RESOURCES_DATALOAD;
    }

    /**
     * 加载数据
     * @param resourcesDataload 加载数据
     */
    public static void setResourcesDataload(DataLoadGetTemplate<String> resourcesDataload) {
        RESOURCES_DATALOAD = resourcesDataload;
    }
}
