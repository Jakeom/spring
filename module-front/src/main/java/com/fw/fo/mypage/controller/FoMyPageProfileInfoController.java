package com.fw.fo.mypage.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.mypage.service.FoMyPageProfileInfoService;
import com.fw.fo.system.config.security.FoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 마이페이지 블랙리스트 Controller
 *
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoMyPageProfileInfoController {

    private final FoMyPageProfileInfoService foMyPageProfileInfoService;
    private final FoLoginService foLoginService;

    private final CommonFileService commonFileService;
    @Value("${cert.server}")
    private String CERT_SERVER;

    @Value("${cert.callback-url}")
    private String CERT_CALLBACK_URL;

    /**
     * front 정보수정 페이지
     */
    @GetMapping("/fo/mypage/profile-info")
    public String profileInfo(ModelMap model, FoMemberDTO foMemberDTO, HttpServletRequest request) {
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL
        model.addAttribute("profileInfo", foMyPageProfileInfoService.selectProfileInfo(foMemberDTO));
        return "fo/mypage/profile-info";
    }

    /**
     * front 정보수정 페이지
     */
    @PostMapping("/fo/mypage/profile-info")
    @ResponseBody
    public ResponseEntity<ResponseVO> profileInfoProcess(FoMemberDTO foMemberDTO, HttpServletRequest request) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "false";
        try {
            foMyPageProfileInfoService.updateProfile(foMemberDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, foSessionDTO.getUsername()));
            serviceCode = "true";
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
    }

    /**
     * @description 새로운 인증 생성
     * @param currentAuth 현재 auth 정보
     * @param username	현재 사용자 Id
     * @return Authentication
     * @author Armton
     */
    protected Authentication createNewAuthentication(Authentication currentAuth, String userId) {
        FoSessionDTO foSessionDTO = (FoSessionDTO) foLoginService.loadUserByUsername(userId);
        List<FileDTO> fileList = commonFileService.selectFileDetailList(foSessionDTO.getProfilePictureFileId());
        if(fileList != null) {
            foSessionDTO.setProfileUrl(fileList.get(0).getUrl());
        }
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(foSessionDTO, currentAuth.getCredentials(), foSessionDTO.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }
}