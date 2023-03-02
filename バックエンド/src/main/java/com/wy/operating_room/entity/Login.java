package com.wy.operating_room.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_login")
public class Login extends Base {

    private String personid;
    private String token;
    private String ipaddress;
    private String origin;
    private String agent;
    private String language;
    private String logouttime;

    public static Login of(String personid, String token, HttpServletRequest request) {
        Login login = new Login();
        login.setPersonid(personid);
        login.setToken(token);
        login.setIpaddress(request.getRemoteAddr());
        login.setOrigin(request.getHeader("Origin"));
        login.setAgent(request.getHeader("User-Agent"));
        login.setLanguage(request.getHeader("Accept-Language"));
        return login;
    }
}
