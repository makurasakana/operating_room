package com.wy.operating_room.util;

import javax.servlet.http.HttpServletRequest;


public class RequestUtil {

    public static String getToken(HttpServletRequest servletRequest) {
        return servletRequest.getHeader("token");
    }
}
