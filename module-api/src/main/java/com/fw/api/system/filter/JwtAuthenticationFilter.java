package com.fw.api.system.filter;

import com.fw.api.system.security.JwtTokenProvider;
import com.fw.api.system.security.UserAuthentication;
import com.fw.core.code.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 인증 필터
 * @author sjpaik
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            if(StringUtils.isNotBlank(token) && token.startsWith("Bearer ")){
                token = token.substring("Bearer".length());
                if(JwtTokenProvider.validateToken(token)){
                    String userId = JwtTokenProvider.getUserIdFromJWT(token);
                    UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
                    userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(userAuthentication);
                } else {
                    request.setAttribute("responseCode", ResponseCode.TOKEN_EXPIRED);
                }
            } else {
                request.setAttribute("responseCode", ResponseCode.TOKEN_NON_EXISTS);
            }
        } catch (Exception e){
            request.setAttribute("responseCode", ResponseCode.INTERNAL_SERVER_ERROR);
        }
        filterChain.doFilter(request, response);
    }

}
