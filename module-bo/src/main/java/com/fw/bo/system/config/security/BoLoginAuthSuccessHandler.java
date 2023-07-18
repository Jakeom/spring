package com.fw.bo.system.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fw.core.persistence.db1.domain.TbAdminLoginLog;
import com.fw.core.persistence.db1.dto.TbAdminLoginLogRequestDto;
import com.fw.core.persistence.db1.repository.TbAdminLoginLogRepository;
import com.fw.core.util.IpUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 성공 Handler
 * @author dongbeom
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BoLoginAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final BoLoginService boLoginService;
    private final TbAdminLoginLogRepository adminLoginLogRepository;

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        BoAdminDTO boAdminDTO = (BoAdminDTO) authentication.getPrincipal();
        BoAdminSessionDTO boAdminSessionDTO = BoAdminSessionDTO.builder().adminSeq(boAdminDTO.getAdminSeq())
                                                                         .orgId(boAdminDTO.getOrgId())
                                                                         .adminId(boAdminDTO.getAdminId())
                                                                         .adminNm(boAdminDTO.getAdminNm())
                                                                         .email(boAdminDTO.getEmail())
                                                                         .build();

        request.getSession().setAttribute(BO_SESSION_KEY, boAdminSessionDTO);
        request.getSession().setAttribute("adminId", boAdminDTO.getAdminId());
        request.getSession().setAttribute("adminNm", boAdminDTO.getAdminNm());

        // 비밀번호 실패 횟수 초기화
        boLoginService.updateResetPasswordFailCount(boAdminDTO);

        TbAdminLoginLog adminLoginLog = TbAdminLoginLogRequestDto.builder()
                                                                 .adminId(boAdminDTO.getAdminId())
                                                                 .resultFlag("Y")
                                                                 .accessIp(IpUtil.getClientIp())
                                                                 .createDt(LocalDateTime.now())
                                                                 .build().toEntity();

        adminLoginLogRepository.save(adminLoginLog);
        response.sendRedirect("/bo/");
    }

}
