package com.imooc.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XuMingyue
 * @create 2020-06-24 11:32
 * Cookit工具类
 */

public class CookieUtil {
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static  void set(HttpServletResponse response,
                            String name,
                            String value,
                            int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        //过期时间
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 查询cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        //判断cookieMap是否有登出的账号cookie
        if(cookieMap.containsKey(name)){
            //如果有，返回cookie
            return cookieMap.get(name);
        }else{
            //没有
            return null;
        }

    }

    /**
     * 将cookielist-->cookiemap  将cookie封装成map
     * @param request
     * @return
     */
    private  static Map<String ,Cookie > readCookieMap(HttpServletRequest request){
        HashMap<String, Cookie> cookieMap = new HashMap<>(1000);
        //获取cookie数组
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
