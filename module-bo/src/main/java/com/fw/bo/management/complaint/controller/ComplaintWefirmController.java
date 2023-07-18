package com.fw.bo.management.complaint.controller;

import com.fw.bo.management.complaint.service.ComplaintWefirmService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoWefirmDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 위펌 조회 및 관리 Controller
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ComplaintWefirmController {

    private final ComplaintWefirmService complaintWefirmService;
    /**
     * 위펌 리스트 페이지
     */
    @GetMapping("bo/management/complaint/wefirm")
    public String Wefirm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/complaint/wefirm";
    }

    /**
     * 위펌 위펌등록 페이지
     */
    @GetMapping("bo/management/complaint/wefirm/form")
    public String wefirmForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/complaint/wefirm/form";
    }
    /**
     * 위펌상세 수정페이지
     */
    @GetMapping("bo/management/complaint/wefirm/modify")
    public String wefirmModify(BoWefirmDTO boWefirmDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("modify", complaintWefirmService.selectWefirmModify(boWefirmDTO));
        return "bo/management/complaint/wefirm/modify";
    }
    /**
     * 위펌상세 페이지
     * 위펌 상세 Tab
     */
    @GetMapping("bo/management/complaint/wefirm/detail")
    public String wefirmDetail(BoWefirmDTO boWefirmDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", complaintWefirmService.selectWefirmDetail(boWefirmDTO));
        return "bo/management/complaint/wefirm/detail";
    }

    /**
     * 위펌상세 페이지
     * 채용 상세 Tab
     */
    @GetMapping("bo/management/complaint/wefirm/recruit/detail")
    public ResponseEntity recruitDetail(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(complaintWefirmService.selectRecruitDetail(boWefirmDTO)).build());
    }
    /**
     * 위펌상세 페이지
     * Notice Tab
     */
    @GetMapping("bo/management/complaint/wefirm/notice/detail")
    public ResponseEntity<ResponseVO> wefirmNotice(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(complaintWefirmService.selectWefirmNotice(boWefirmDTO)).build());
    }

    /**
     * 위펌상세 페이지
     * headhunter 상세  리스트
     */
    @GetMapping("bo/management/complaint/wefirm/headhunter/detail")
    public ResponseEntity headhunterDetail(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(complaintWefirmService.selectHeadhunterDetail(boWefirmDTO)).build());
    }

    /**
     * 위펌관리 리스트
     */
    @GetMapping("bo/management/complaint/wefirm/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectWefirmList(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(complaintWefirmService.selectWefirmList(boWefirmDTO)).build());
    }

    /**
     * 위펌관리 검색
     */
    @PostMapping("/bo/management/complaint/wefirm/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchWefirmList(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(complaintWefirmService.saerchWefirmList(boWefirmDTO)).build());
    }

    /**
     * 위펌 상태 확인
     * */
    @PostMapping("bo/management/complaint/wefirm/status/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchWefirmStatus(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        boWefirmDTO.setClosed(boWefirmDTO.getClosed());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(complaintWefirmService.saerchWefirmStatus(boWefirmDTO)).build());
    }

    /**
     * 위펌등록
     */
    @PostMapping("bo/management/complaint/wefirm/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertWefirm(@RequestBody BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintWefirmService.insertWefirm(boWefirmDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 위펌 상태 수정
     */
    @PostMapping("bo/management/complaint/wefirm/status/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateWefirmStatus(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        boWefirmDTO.setId(boWefirmDTO.getId());
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintWefirmService.updateWefirmStatus(boWefirmDTO);
        } catch (Exception e) {
            log.error("error", e.getMessage());
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 위펌수정
     */
    @PostMapping("bo/management/complaint/wefirm/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateWefirm(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("**************************************"+ boWefirmDTO.getId() + boWefirmDTO.getSfName()+ boWefirmDTO.getSfPhone() + boWefirmDTO.getSfRegNumber());
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintWefirmService.updateWefirm(boWefirmDTO);
        } catch (Exception e) {
            log.error("error", e.getMessage());
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 위펌삭제
     */
    @PostMapping("bo/management/complaint/wefirm/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteWefirm(BoWefirmDTO boWefirmDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintWefirmService.deleteWefirm(boWefirmDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 헤드헌터 부분 엑셀 다운로드
     */
    @GetMapping(value="/bo/management/complaint/wefirm/headhunter/excelDownload")
    public void download(HttpServletRequest request, HttpServletResponse response, BoWefirmDTO boWefirmDTO) {
        try {
            complaintWefirmService.ExcelFileDownload(boWefirmDTO, response);
        } catch (Exception e) {
            log.error("error", e);
        }
    }
}
