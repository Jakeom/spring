package com.fw.bo.system.config.security;

import com.fw.core.persistence.db1.domain.TbAdminLoginLog;
import com.fw.core.persistence.db1.dto.TbAdminLoginLogRequestDto;
import com.fw.core.persistence.db1.repository.TbAdminLoginLogRepository;
import com.fw.core.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.time.LocalDateTime;

/**
 * 로그인 실패 Handler
 * @author dongbeom
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BoLoginAuthFailureHandler implements AuthenticationFailureHandler {

    private final TbAdminLoginLogRepository adminLoginLogRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String userId = request.getParameter("adminId");
        TbAdminLoginLog adminLoginLog = TbAdminLoginLogRequestDto.builder()
                                                                 .adminId(userId)
                                                                 .resultFlag("N")
                                                                 .accessIp(IpUtil.getClientIp())
                                                                 .createDt(LocalDateTime.now())
                                                                 .build().toEntity();
        adminLoginLogRepository.save(adminLoginLog);

        if (exception != null) {
            final FlashMap flashMap = new FlashMap();
            flashMap.put("message", exception.getMessage());

            final FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
        }

        response.sendRedirect("/bo/login-fail");
    }

}
