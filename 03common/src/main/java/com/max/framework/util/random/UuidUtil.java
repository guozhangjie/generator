package com.max.framework.util.random;

import java.util.UUID;

/**
 * uuid类
 * @author 边城
 */
public class UuidUtil {

    /**
     * 生成uuid
     * @return UUid
     */
    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);

    }
}
