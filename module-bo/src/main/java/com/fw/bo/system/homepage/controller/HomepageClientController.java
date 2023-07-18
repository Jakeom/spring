package com.fw.bo.system.homepage.controller;

import com.fw.bo.system.homepage.service.HomepageClientService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoClientPmDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 고객사 관리 컨트롤러
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomepageClientController {

    private final HomepageClientService homepageClientService;

    /**
     * 고객사 관리 화면
     */
    @GetMapping("/bo/system/homepage/client")
    public String client(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/system/homepage/client";
    }

    /**
     * 고객사 리스트 화면
     */
    @GetMapping("/bo/system/homepage/client/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectClientPmList(BoClientPmDTO boClientPmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(homepageClientService.selectClientPmList(boClientPmDTO)).build());
    }

    /**
     * 고객사 소속 위펌 리스트
     */
    @GetMapping("/bo/system/homepage/client/wefirm/list")
    public ResponseEntity<ResponseVO> selectWefirmList(BoClientPmDTO boClientPmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(homepageClientService.selectWefirmList(boClientPmDTO)).build());
    }
}
