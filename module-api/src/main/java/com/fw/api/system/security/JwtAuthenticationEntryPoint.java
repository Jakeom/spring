package com.fw.api.system.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fw.core.code.ResponseCode;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT 비 인가 접근시 처리
 * @author sjpaik
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("JWT 인증 실패 ---\nStatus : {}, Message : {}", response.getStatus(), authException.getMessage());

        ResponseCode responseCode = (ResponseCode) request.getAttribute("responseCode");
        ResponseVO responseVO = ResponseVO.builder(responseCode).build();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(responseVO));
        response.sendError(responseCode.getHttpStatus().value());
    }

}
