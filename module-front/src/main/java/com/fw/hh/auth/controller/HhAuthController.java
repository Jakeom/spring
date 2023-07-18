package com.fw.hh.auth.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoCommonDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.hh.HhSfChangeRequestDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.common.service.FoCommonService;
import com.fw.fo.login.service.FoMemberService;
import com.fw.fo.mypage.service.FoMyPageProfileInfoService;
import com.fw.fo.system.config.security.FoLoginService;
import com.fw.hh.member.service.HhMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhAuthController {

    private final FoLoginService foLoginService;
    private final FoMemberService foMemberService;
    private final FoCommonService foCommonService;
    private final FoMyPageProfileInfoService foMyPageProfileInfoService;
    private final BCryptPasswordEncoder pwEncoder;
    private final HhMemberService hhMemberService;

    @Value("${cert.server}")
    private String CERT_SERVER;

    @Value("${cert.callback-url}")
    private String CERT_CALLBACK_URL;

    /**
     * HH 로그인 페이지
     */
    @GetMapping("/hh/auth/login")
    public String hhLogin(ModelMap model){
        return "hh/auth/login";
    }

    /**
     * HH 회원 가입
     */
    @GetMapping("/hh/auth/signup")
    public String hhSignup(ModelMap model){
        // 회원가입절차 전 필독 공지사항
        FoCommonDTO foCommonDTO = new FoCommonDTO();
        foCommonDTO.setInfoType("HH_JOIN");
        model.addAttribute("noticeInfo", foCommonService.selectInfo(foCommonDTO));

        // 서비스 이용약관
        foCommonDTO.setInfoType("POLICY");
        model.addAttribute("policyInfo", foCommonService.selectInfo(foCommonDTO));

        // 개인정보처리방침 및 제3자정보제공동의
        foCommonDTO.setInfoType("PRIVACY");
        model.addAttribute("privacyInfo", foCommonService.selectInfo(foCommonDTO));

        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL

        return "hh/auth/signup";
    }

    /**
     * HH 회원가입 수행
     */
    @PostMapping("/hh/auth/signup")
    @ResponseBody
    public ResponseEntity<ResponseVO> signup(FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "false";
        try {
            foMemberService.insertMember(foMemberDTO);
            serviceCode = "true";
        } catch (Exception e){
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            log.error("error", e);
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
    }

    /**
     * HH 현재 비밀번호 일치여부 확인
     */
    @PostMapping("/hh/auth/current-password-check")
    @ResponseBody
    public ResponseEntity<ResponseVO> currentPasswordCheck(FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "";
        try {
            // password 조회
            FoMemberDTO f = foMyPageProfileInfoService.selectProfileInfo(foMemberDTO);
            if(!pwEncoder.matches(foMemberDTO.getPassword(), f.getPassword())) {
                serviceCode = "PASSWORD_NOT_MATCH";
            }else {
                serviceCode = "SUCCESS";
            }
        } catch (Exception e){
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            log.error("error", e);
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
    }

    /**
     * HH 이용정지회원 서치펌 변경이력여부 확인
     */
    @PostMapping("/hh/auth/sf-change-history")
    @ResponseBody
    public ResponseEntity<ResponseVO> sfChangeHistory(FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhMemberService.selectSfChangeHistory(foMemberDTO)).build());
    }

    /**
     * HH 이용정지회원 서치펌 변경신청
     */
    @PostMapping("/hh/auth/request-sf-change")
    @ResponseBody
    public ResponseEntity<ResponseVO> registerPasser(@RequestPart(value = "jsonData") HhSfChangeRequestDTO hhSfChangeRequestDTO,
                                                     @RequestPart(value = "sfWorkerFile", required = false) MultipartFile[] sfWorkerFile) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhSfChangeRequestDTO.setSfWorkerFile(sfWorkerFile);
            hhMemberService.insertSfChange(hhSfChangeRequestDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}