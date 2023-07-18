package com.fw.bo.system.log.controller;

import com.fw.bo.system.log.service.LogAdminService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminLogDTO;
import com.fw.core.dto.bo.BoAdminLoginLogDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 관리자 로그 확인 Controller
 *
 * @YJW
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LogAdminController {

    private final LogAdminService logAdminService;

    /**
     * 관리자 로그 확인
     */
    @GetMapping("/bo/system/log/admin")
    public String logAdmin(BoAdminLogDTO boAdminLogDTO, BoAdminLoginLogDTO boAdminLoginLogDTO, ModelMap model) {
        model.addAttribute("adminLog", logAdminService.selectAdminLog(boAdminLogDTO));
        return "/bo/system/log/admin";
    }

    /**
     * 관리자 로그
     */
    @GetMapping("/bo/system/log/admin/logList")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectAdminLog(BoAdminLogDTO boAdminLogDTO, ModelMap model) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(logAdminService.selectAdminLog(boAdminLogDTO)).build());
    }

    /**
     * 관리자 아이디 조회
     */
    @GetMapping("/bo/system/log/admin/search")
    public String selectAdminId(BoAdminDTO boAdminDTO, ModelMap model) {
        model.addAttribute("adminList", logAdminService.selectAdminId(boAdminDTO));
        return "/bo/system/log/admin/search";
    }

    /**
     * 관리자 로그인 로그 조회
     */
    @GetMapping("/bo/system/log/admin/loginLog")
    public String selectAdminId(BoAdminLoginLogDTO boAdminLoginLogDTO, ModelMap model) {
        model.addAttribute("loginLog", logAdminService.selectAdminLoginLog(boAdminLoginLogDTO));
        return "/bo/system/log/admin/loginLog";
    }

}