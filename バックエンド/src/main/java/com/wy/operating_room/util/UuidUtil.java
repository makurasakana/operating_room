package com.wy.operating_room.util;

import java.util.UUID;


public class UuidUtil {

    /**
     * @return 获取 32 位 UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
