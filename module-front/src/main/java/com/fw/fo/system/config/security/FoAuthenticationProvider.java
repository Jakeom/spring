package com.fw.fo.system.config.security;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import com.fw.core.mapper.db1.fo.FoSimpleLoginMapper;
import com.fw.fo.login.service.FoSimpleLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class FoAuthenticationProvider implements AuthenticationProvider {

    private final FoLoginService foLoginService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private CommonFileService commonFileService;
    private HttpServletRequest request;
    private final AES256Provider aes256Provider;
    private final FoSimpleLoginService foSimpleLoginService;

    @Autowired
    public void setCommonFileService(CommonFileService commonFileService) {
        this.commonFileService = commonFileService;
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String dType = request.getParameter("dType");
        String autoLogin = request.getParameter("autoLogin");
        String autoLoginToken = request.getParameter("autoLoginToken");


        FoSessionDTO foSessionDTO = (FoSessionDTO) foLoginService.loadUserByUsername(userId);
        foSessionDTO.setAutoLogin(autoLogin);
        foSessionDTO.setAutoLoginToken(autoLoginToken);

        //이용정지 계정인 경우
        if(StringUtils.equals("1", foSessionDTO.getIsStop())) {
            throw new DisabledException(userId);
        }

        //블랙리스트멤버인 경우
        if(foLoginService.selectBlack(foSessionDTO.getId()) > 0) {
            throw new AuthenticationCredentialsNotFoundException(userId);
        }

        //로컬스토리지 토큰값과 디비 토큰값이 다른경우
        if(!StringUtils.isEmpty(autoLoginToken)) {
            String memberId = request.getParameter("id");
            if(foLoginService.selectAutoLogin(memberId) == null){
                throw new BadCredentialsException(userId);
            }else {
                String simpleAutoLoginToken = foLoginService.selectAutoLogin(memberId).getSimpleAuthVal();
                if(!StringUtils.equals(autoLoginToken, simpleAutoLoginToken)){
                    throw new BadCredentialsException(userId);
                }
            }


        } else {
            if(Objects.isNull(foSessionDTO) || !StringUtils.equals(dType, foSessionDTO.getDType()) || !bCryptPasswordEncoder.matches(password, foSessionDTO.getPassword())){
                throw new BadCredentialsException(userId);          // 아이디 혹은 비밀번호가 틀린 경우
            } else if(!foSessionDTO.isAccountNonLocked()){
                throw new LockedException(userId);                  // 계정이 잠긴경우
            } else if(!foSessionDTO.isEnabled()){
                throw new DisabledException(userId);                // 계정이 비활성화 된 경우
            } else if(!foSessionDTO.isAccountNonExpired()){
                throw new AccountExpiredException(userId);          // 계정이 만료된 경우
            } else if(!foSessionDTO.isCredentialsNonExpired()){
                throw new CredentialsExpiredException(userId);      // 비밀번호가 만료된 경우
            }
        }

        List<FileDTO> fileList = commonFileService.selectFileDetailList(foSessionDTO.getProfilePictureFileId());
        if(fileList != null && !fileList.isEmpty()) {
            foSessionDTO.setProfileUrl(fileList.get(0).getUrl());
        }

        if(StringUtils.equals("Y",foSessionDTO.getAutoLogin())){
            String result = "";
            // NOTI 토큰발행(30일짜리)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            final String targetStr = LocalDateTime.now().plusDays(90).format(formatter) + "|" + foSessionDTO.getId();
            result = aes256Provider.encryptStr(targetStr);
            FoSimpleLoginDTO foSimpleLoginDTO = new FoSimpleLoginDTO();
            foSimpleLoginDTO.setMemberId(foSessionDTO.getId());
            foSimpleLoginDTO.setSimpleAuthCd("AUTO_WEB");
            foSimpleLoginDTO.setSimpleAuthVal(result);
            foSimpleLoginDTO.setRegSeq(foSessionDTO.getId());
            foSimpleLoginService.insertMemberSimpleAuth(foSimpleLoginDTO);
           foSessionDTO.setAutoLoginToken(result);
        }

        return new UsernamePasswordAuthenticationToken(foSessionDTO, null, foSessionDTO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
