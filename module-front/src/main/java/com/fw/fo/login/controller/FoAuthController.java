package com.fw.fo.login.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.FoCommonDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.common.service.FoCommonService;
import com.fw.fo.login.service.FoMemberService;
import com.fw.fo.login.service.FoSimpleLoginService;
import com.fw.fo.system.config.security.FoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoAuthController {

    private final FoLoginService foLoginService;
    private final FoSimpleLoginService foSimpleLoginService;
    private final FoMemberService foMemberService;
    private final FoCommonService foCommonService;
    private final CommonFileService commonFileService;
    private final CommonService commonService;
    @Value("${cert.server}")
    private String CERT_SERVER;

    @Value("${cert.callback-url}")
    private String CERT_CALLBACK_URL;

    @Value("${service.web_domain}")
    private String WEB_DOMAIN;

    /**
     * FO 메인 로그인 페이지
     */
    @GetMapping("/fo/auth/login")
    public String login(ModelMap model) {
        model.addAttribute("webDomain", WEB_DOMAIN);
        return "fo/auth/login";
    }

    /**
     * FO 간편로그인
     */
    @GetMapping("/fo/auth/naverLoginCallback")
    public String loginNaver(ModelMap model, HttpServletRequest request) {
        request.getSession().getAttribute("naver_id_login");
        return "fo/naverLogin";
    }

    /**
     * 간편로그인
     */
    @PostMapping("/fo/auth/simpleLoginCheck")
    @ResponseBody
    public ResponseEntity<ResponseVO> simpleLoginCheck(ModelMap model, HttpServletRequest request, FoSimpleLoginDTO foSimpleLoginDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        FoSimpleLoginDTO simpleLoginInfo = foSimpleLoginService.selectSimpleLoginInfo(foSimpleLoginDTO);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(simpleLoginInfo).build());
    }

    /**
     * 간편로그인
     */
    @PostMapping("/fo/auth/doSimpleLogin")
    @ResponseBody
    public ResponseEntity<ResponseVO> doSimpleLogin(FoSessionDTO foSessionDTO, HttpServletRequest request) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "false";
        //String prevPage = (String) request.getSession().getAttribute("prevPage");

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            FoSessionDTO adminBean = foLoginService.selectDoSimpleLoginInfo(foSessionDTO);
            SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, adminBean.getUsername()));
            serviceCode = "true";
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
    }

    /**
     * AP 회원가입 수행
     */
    @PostMapping("/fo/auth/signup")
    @ResponseBody
    public ResponseEntity<ResponseVO> signup(@RequestBody FoMemberDTO foMemberDTO) {
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
     * AP 회원가입
     */
    @GetMapping("/fo/auth/signup")
    public String signup(ModelMap model, FoSimpleLoginDTO foSimpleLoginDTO) {
        // 회원가입절차 전 필독 공지사항
        FoCommonDTO foCommonDTO = new FoCommonDTO();
        foCommonDTO.setInfoType("AP_JOIN");
        model.addAttribute("noticeInfo", foCommonService.selectInfo(foCommonDTO));

        // 서비스 이용약관
        foCommonDTO.setInfoType("POLICY");
        model.addAttribute("policyInfo", foCommonService.selectInfo(foCommonDTO));

        // 개인정보처리방침 및 제3자정보제공동의
        foCommonDTO.setInfoType("PRIVACY");
        model.addAttribute("privacyInfo", foCommonService.selectInfo(foCommonDTO));

        model.addAttribute("simpleLoginInfo", foSimpleLoginDTO);             // 간편인증 로그인 정보
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL

        return "fo/auth/signup";
    }

    /**
     * 임시회원 회원가입 페이지
     */
    @RequestMapping("/fo/auth/signup-temp")
    public String signTemp(ModelMap model, FoMemberDTO foMemberDTO) {
        model.addAttribute("memberInfo", commonService.selectMemberInfoByMemberId(foMemberDTO));
        model.addAttribute("memberId", foMemberDTO.getMemberId());
        // 회원가입절차 전 필독 공지사항
        FoCommonDTO foCommonDTO = new FoCommonDTO();
        foCommonDTO.setInfoType("AP_JOIN");
        model.addAttribute("noticeInfo", foCommonService.selectInfo(foCommonDTO));

        // 서비스 이용약관
        foCommonDTO.setInfoType("POLICY");
        model.addAttribute("policyInfo", foCommonService.selectInfo(foCommonDTO));

        // 개인정보처리방침 및 제3자정보제공동의
        foCommonDTO.setInfoType("PRIVACY");
        model.addAttribute("privacyInfo", foCommonService.selectInfo(foCommonDTO));

        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL

        return "fo/auth/signup-temp";
    }

    /**
     * 임시회원 회원가입
     */
    @PostMapping("/fo/auth/signup-temp-update")
    @ResponseBody
    public ResponseEntity<ResponseVO> signupTempUpdate(@RequestBody FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foMemberDTO.setTemp(false); // 임시회원 해제
            foMemberService.updateTempMember(foMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 이메일 중복 검증
     */
    @PostMapping("/fo/auth/duplicate-email-check")
    @ResponseBody
    public ResponseEntity<ResponseVO> duplicateCheck(@RequestBody FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foMemberService.selectMemberEmailDuplicateCheck(foMemberDTO)).build());
    }

    /**
     * 본인인증 중복 검증
     */
    @PostMapping("/fo/auth/duplicate-name-check")
    @ResponseBody
    public ResponseEntity<ResponseVO> duplicateNameCheck(@RequestBody FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foMemberService.selectMemberNameDuplicateCheck(foMemberDTO)).build());
    }

    /**
     * 아이디 찾기
     */
    @GetMapping("/fo/auth/find-id")
    public String findId(ModelMap model) {
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL
        return "fo/auth/find-id";
    }

    /**
     * 아이디 찾기 (조회)
     */
    @PostMapping("/fo/auth/find-id")
    @ResponseBody
    public ResponseEntity<ResponseVO> findId(@RequestBody FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foMemberService.selectLoginId(foMemberDTO)).build());
    }

    /**
     * 비밀번호 재설정
     */
    @GetMapping("/fo/auth/pw-reset")
    public String pwreset(ModelMap model) {
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL
        return "fo/auth/pw-reset";
    }

    /**
     * 비밀번호 재설정 (update)
     */
    @PostMapping("/fo/auth/pw-reset")
    @ResponseBody
    public ResponseEntity<ResponseVO> pwreset(@RequestBody FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "false";
        try {
            foMemberService.updatePassword(foMemberDTO);
            serviceCode = "true";
        } catch (Exception e){
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            log.error("error", e);
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foMemberService.selectLoginId(foMemberDTO)).serviceCode(serviceCode).build());
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

    /**
     * 블랙리스트 조회 조회
     */
    @PostMapping("/fo/auth/blacklist-check")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectMemberBlackList(@RequestBody FoMemberDTO foMemberDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foMemberService.selectMemberBlackList(foMemberDTO)).build());
    }
}