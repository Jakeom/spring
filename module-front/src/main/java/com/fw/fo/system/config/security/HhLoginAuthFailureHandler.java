package com.fw.fo.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fw.core.code.ResponseCode;
import com.fw.core.persistence.db1.repository.TbAdminLoginLogRepository;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Component
@RequiredArgsConstructor
public class HhLoginAuthFailureHandler implements AuthenticationFailureHandler {

    private final TbAdminLoginLogRepository adminLoginLogRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String errorMessage = "아이디 혹은 비밀번호를 확인해주세요.";
        String serviceCode = "login_fail";
        if(exception instanceof AuthenticationCredentialsNotFoundException){
            errorMessage ="관리자에게 문의하시기 바랍니다. 오류코드 - (99)";
        }else if(exception instanceof DisabledException){
            errorMessage ="이용정지된 계정이니 관리자에게 문의바랍니다.";
            serviceCode = "stop_account";
        }
        // AJAX 요청시 제외
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.getWriter().print(mapper.registerModule(new JavaTimeModule()).writeValueAsString(ResponseVO.builder(ResponseCode.LOGIN_FAIL).data(errorMessage).serviceCode(serviceCode).build()));
            response.getWriter().flush();
        }else{
            errorMessage = URLEncoder.encode(errorMessage,"UTF-8");
            response.sendRedirect("/?errorMessage="+errorMessage);
        }
    }

}
