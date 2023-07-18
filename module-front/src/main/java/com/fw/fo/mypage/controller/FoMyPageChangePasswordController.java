package com.fw.fo.mypage.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.mypage.service.FoMyPageChangePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 마이페이지 Controller
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoMyPageChangePasswordController {

    private final FoMyPageChangePasswordService foMyPageChangePasswordService;

    private final BCryptPasswordEncoder pwEncoder;

    /**
     * front 메인 비밀번호 변경
     */
    @GetMapping("/fo/mypage/password")
    public String password(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "fo/mypage/password";
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/fo/user/mypage/changePassword")
    @ResponseBody
    public ResponseEntity<ResponseVO> changePassword(@RequestBody FoMemberDTO foMemberDTO, HttpServletRequest request) {
        ResponseCode code = ResponseCode.SUCCESS;

        String password = foMemberDTO.getPassword(); // 기존 비밀번호

        try {
            FoMemberDTO member = (FoMemberDTO) foMyPageChangePasswordService.selectMemberPassword(foMemberDTO);
            if(!pwEncoder.matches(password, member.getPassword())) {
                throw new BadCredentialsException("");
            }
            foMyPageChangePasswordService.updateChangePassword(foMemberDTO);
        } catch (Exception e){
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            log.error("error", e);
        }

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
    }

}