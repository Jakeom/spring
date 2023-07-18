package com.fw.bo.system.auth.controller;

import com.fw.bo.system.auth.service.AuthAdminService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthAdminDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 관리자별 권한 관리 Controller
 *
 * @YJW
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthAdminController {

    private final AuthAdminService authAdminService;

    /**
     * 관리자별 권한 관리
     */
    @GetMapping("/bo/system/auth/admin")
    public String authAdmin(ModelMap model, BoAdminMenuProgramDTO boAdminMenuProgramDTO, BoAdminDTO boAdminDTO) {
        /* 메뉴프로그램 리스트 */
        model.addAttribute("menuProgram", authAdminService.selectMenuProgram(boAdminMenuProgramDTO));
        /* 관리자 리스트 */
        model.addAttribute("admin", authAdminService.selectAdminList(boAdminDTO));
        return "/bo/system/auth/admin";
    }

    /**
     * 관리자별 권한 정보
     */
    @GetMapping("/bo/system/auth/admin/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectAdminAuth(BoAuthAdminDTO boAuthAdminDTO, ModelMap model) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(authAdminService.selectAdminAuth(boAuthAdminDTO)).build());
    }

    /**
     * 관리자별 권한 부여
     */
    @RequestMapping("/bo/system/auth/admin/insert")
    public ResponseEntity<ResponseVO> insert(@RequestBody BoAuthAdminDTO boAuthAdminDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            authAdminService.insertAdminAuth(boAuthAdminDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}

