package com.fw.bo.system.screen.controller;

import com.fw.bo.system.screen.service.ScreenPopupService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoPopupDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScreenPopupController {

    private final ScreenPopupService popupService;

    /**
     * 팝업관리 페이지
     */
    @GetMapping("/bo/system/screen/popup")
    public String popup(ModelMap model) {
        return "/bo/system/screen/popup";
    }

    /**
     * 팝업등록 페이지
     */
    @GetMapping("/bo/system/screen/popup/form")
    public String popupForm(ModelMap model) {
        return "/bo/system/screen/popup/form";
    }

    /**
     * 팝업상세 페이지
     */
    @GetMapping("/bo/system/screen/popup/detail")
    public String popupDetail(BoPopupDTO boPopupDTO, ModelMap model) {
        model.addAttribute("detail", popupService.selectPopupDetail(boPopupDTO));
        return "/bo/system/screen/popup/detail";
    }

    /**
     * 팝업수정 페이지
     */
    @GetMapping("/bo/system/screen/popup/modify")
    public String popupModify(BoPopupDTO boPopupDTO, ModelMap model) {
        model.addAttribute("modify", popupService.selectPopupDetail(boPopupDTO));
        return "/bo/system/screen/popup/modify";
    }

    /**
     * 팝업관리 리스트 취득
     */
    @GetMapping("/bo/system/screen/popup/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectPopupList(BoPopupDTO boPopupDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(popupService.selectPopupList(boPopupDTO)).build());
    }

    /**
     * 팝업등록
     */
    @PostMapping("/bo/system/screen/popup/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertPopup(BoPopupDTO boPopupDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            popupService.insertPopup(boPopupDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 팝업수정
     */
    @PostMapping("/bo/system/screen/popup/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> popupUpdate(BoPopupDTO boPopupDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            popupService.updatePopup(boPopupDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 팝업삭제
     */
    @PostMapping("/bo/system/screen/popup/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> popupDelete(BoPopupDTO boPopupDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            popupService.deletePopup(boPopupDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}