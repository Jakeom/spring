package com.fw.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class CookieUtil {

    public static String getCookie(HttpServletRequest request, String cookieName){
        String cookieValue = null;
        if(request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (StringUtils.equals(c.getName(), cookieName)) {
                    cookieValue = c.getValue();
                }
            }
        }
        return cookieValue;
    }

    public static boolean mergeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, String separator, int maxAge){
        String currentCookieValueStr = CookieUtil.getCookie(request, cookieName);
        log.info(">>> {}", currentCookieValueStr);

        if(StringUtils.isNotBlank(currentCookieValueStr)){
            // 쿠키가 이미 있음
            String[] currentCookieValue = currentCookieValueStr.split(separator);
            for(String str : currentCookieValue){
                if(StringUtils.equals(str, cookieValue)){
                    // 이미 쿠키 값이 존재
                    return true;
                }
            }
            currentCookieValueStr = currentCookieValueStr + separator + cookieValue;
        } else {
            // 쿠키가 없음
            currentCookieValueStr = cookieValue;
        }
        Cookie c = new Cookie(cookieName, currentCookieValueStr);
        c.setPath("/");
        c.setMaxAge(maxAge);
        response.addCookie(c);
        return false;
    }

}
