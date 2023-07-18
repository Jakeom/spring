package com.fw.bo.system.homepage.controller;

import com.fw.bo.system.homepage.service.HomepageMainDisplayService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoMaindisplayDTO;
import com.fw.core.dto.bo.BoNoticeDTO;
import com.fw.core.dto.bo.BoTbBlacklistDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 메인 노출 조회 및 관리 Controller
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomepageMainDisplayController {

    private final HomepageMainDisplayService homepageMainDisplayService;

    /**
     * 메인 노출 조회 화면
     */
    @GetMapping("/bo/system/homepage/maindisplay")
    public String mainDisplay(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/system/homepage/maindisplay";
    }

    /**
     * 메인 노출 추가 화면
     */
    @GetMapping("/bo/system/homepage/maindisplay/form")
    public String create(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/system/homepage/maindisplay/form";
    }

    /**
     * 메인 노출 상세보기 화면
     */
    @GetMapping("/bo/system/homepage/maindisplay/detail")
    public String detail(ModelMap model, BoMaindisplayDTO boMaindisplayDTO, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", homepageMainDisplayService.selectMaindisplay(boMaindisplayDTO));
        model.addAttribute("file", homepageMainDisplayService.selectMaindisplayFile(boMaindisplayDTO));
        return "/bo/system/homepage/maindisplay/detail";
    }

    /**
     * 메인 노출 수정하기 화면
     */
    @GetMapping("/bo/system/homepage/maindisplay/modify")
    public String modify(ModelMap model, BoMaindisplayDTO boMaindisplayDTO, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", homepageMainDisplayService.selectMaindisplay(boMaindisplayDTO));
        model.addAttribute("file", homepageMainDisplayService.selectMaindisplayFile(boMaindisplayDTO));
        return "/bo/system/homepage/maindisplay/modify";
    }

    /**
     * 메인노출 리스트 조회
     */
    @GetMapping("/bo/system/homepage/maindisplay/list")
    public ResponseEntity<ResponseVO> mainDisplayList(BoMaindisplayDTO boMaindisplayDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(homepageMainDisplayService.selectMaindisplayList(boMaindisplayDTO)).build());
    }

    /**
     * 메인노출 리스트 추가
     */
    @PostMapping("/bo/system/homepage/maindisplay/form/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertMaindisplay(
            @RequestPart(value = "jsonData") BoMaindisplayDTO boMaindisplayDTO,
            @RequestPart(value = "maindisplayFiles", required = false) MultipartFile[] maindisplayFiles, HttpServletRequest request, HttpServletResponse response
    ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boMaindisplayDTO.setMaindisplayFiles(maindisplayFiles);
            homepageMainDisplayService.insertMaindisplay(boMaindisplayDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 메인노출 리스트 삭제
     */
    @PostMapping("/bo/system/homepage/maindisplay/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteMaindisplay(@RequestBody BoMaindisplayDTO boMaindisplayDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageMainDisplayService.deleteMaindisplay(boMaindisplayDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 메인노출 리스트 업데이트
     */
    @PostMapping("/bo/system/homepage/maindisplay/modify/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateMaindisplay(
            @RequestPart(value = "jsonData") BoMaindisplayDTO boMaindisplayDTO,
            @RequestPart(value = "maindisplayFiles", required = false) MultipartFile[] maindisplayFiles, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boMaindisplayDTO.setMaindisplayFiles(maindisplayFiles);
            homepageMainDisplayService.updateMaindisplay(boMaindisplayDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}
