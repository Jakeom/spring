package com.fw.bo.system.log.controller;

import com.fw.bo.system.log.service.LogApiService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoApiLogDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * API 로그 확인 Controller
 *
 * @YJW
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LogApiController {

    private final LogApiService logApiService;

    /**
     * API 로그 확인
     */
    @GetMapping("/bo/system/log/api")
    public String api(BoApiLogDTO boApiLogDTO, ModelMap model) {
        model.addAttribute("apiLog", logApiService.selectApiLog(boApiLogDTO));
        return "/bo/system/log/api";
    }

    /**
     * API 로그 GROUP BY COUNT
     */
    @GetMapping("/bo/system/log/api/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectApiLog(BoApiLogDTO boApiLogDTO, ModelMap model) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(logApiService.selectApiLog(boApiLogDTO)).build());
    }

}