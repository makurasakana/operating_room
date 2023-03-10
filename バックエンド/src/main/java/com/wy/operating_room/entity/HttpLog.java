package com.wy.operating_room.entity;

import com.wy.operating_room.util.GsonUtil;
import com.wy.operating_room.util.RequestUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_http_log")
public class HttpLog extends Base {

    private String personid;
    private String token;
    private String url;
    private String method;
    private String ipaddress;
    // 经过时间
    private double elapsedtime;
    private String param;
    private String origin;
    private String agent;
    private String language;


    public static HttpLog of(String personid, HttpServletRequest request) {

        String url = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");

        HttpLog obj = new HttpLog();
        obj.setPersonid(personid);
        obj.setToken(RequestUtil.getToken(request));
        obj.setUrl(url);
        obj.setMethod(request.getMethod());
        obj.setIpaddress(request.getRemoteAddr());
        obj.setOrigin(request.getHeader("Origin"));
        obj.setAgent(request.getHeader("User-Agent"));
        obj.setLanguage(request.getHeader("Accept-Language"));

        Map parameterMap = request.getParameterMap();
        if (parameterMap != null) {
            obj.setParam(GsonUtil.toJson(parameterMap));
        }

        return obj;
    }
}
