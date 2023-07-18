package com.fw.fo.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fw.core.code.ResponseCode;
import com.fw.core.persistence.db1.domain.TbAdminLoginLog;
import com.fw.core.persistence.db1.dto.TbAdminLoginLogRequestDto;
import com.fw.core.persistence.db1.repository.TbAdminLoginLogRepository;
import com.fw.core.util.IpUtil;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class FoLoginAuthFailureHandler implements AuthenticationFailureHandler {

    private final TbAdminLoginLogRepository adminLoginLogRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String errorMessage = "아이디 혹은 비밀번호를 확인해주세요.";
        if(exception instanceof AuthenticationCredentialsNotFoundException){
            errorMessage ="관리자에게 문의하시기 바랍니다. 오류코드 - (99)";
        }else if(exception instanceof DisabledException){
            errorMessage ="이용정지된 계정이니 관리자에게 문의바랍니다.";
        }
        // AJAX 요청시 제외
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.getWriter().print(mapper.registerModule(new JavaTimeModule()).writeValueAsString(ResponseVO.builder(ResponseCode.LOGIN_FAIL).data(errorMessage).build()));
            response.getWriter().flush();
        }else{
            errorMessage = URLEncoder.encode(errorMessage,"UTF-8");
            response.sendRedirect("/?errorMessage="+errorMessage);
        }
    }

}
