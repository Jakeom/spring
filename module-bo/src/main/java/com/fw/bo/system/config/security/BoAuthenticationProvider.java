package com.fw.bo.system.config.security;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminIpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Bo Authentication Provider
 * @author dongbeom
 */
@Slf4j
public class BoAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BoLoginService boLoginService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String adminId = (String) authentication.getPrincipal();
        String adminPassword = (String) authentication.getCredentials();

        BoAdminDTO boAdminDTO = BoAdminDTO.builder()
                                          .adminId(adminId)
                                          .adminPassword(adminPassword)
                                          .build();

        // TODO :: 계정에 대한 조건 점검

        BoAdminDTO adminInfo = boLoginService.selectAdminForAdminId(boAdminDTO);
        if (adminInfo == null) {
            throw new UsernameNotFoundException("존재 하지 않습니다.");     // TODO :: MESSAGE 처리
        }

        if (adminInfo.getPasswordFailCount() > 4) {
            throw new LockedException("비밀번호를 틀린 횟수가 5회로 계정이 잠겼습니다.");     // TODO :: MESSAGE 처리
        }

        if (!bCryptPasswordEncoder.matches(boAdminDTO.getAdminPassword(), adminInfo.getAdminPassword())) {
            boLoginService.updatePasswordFailCount(adminInfo);
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");     // TODO :: MESSAGE 처리
        }

        BoAdminIpDTO boAdminIpDTO = new BoAdminIpDTO();
        boAdminIpDTO.setAdminSeq(adminInfo.getAdminSeq());
        if (!boLoginService.isAccessIp(boAdminIpDTO)) {
            throw new BadCredentialsException("접근이 허용되지 않은 IP입니다.");     // TODO :: MESSAGE 처리
        }
        return new UsernamePasswordAuthenticationToken(adminInfo, authentication.getCredentials(), adminInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
