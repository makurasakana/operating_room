package com.wy.operating_room.util;

import static org.apache.commons.lang3.StringUtils.isBlank;


public class StringUtil {

    public static boolean isNotBlank(Object cs) {
        return cs != null && !isBlank(cs.toString());
    }
}
