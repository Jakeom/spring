package com.fw.bo.system.homepage.controller;

import com.fw.core.dto.FileDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.bo.system.homepage.service.HomepageQnaService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoQnaDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1:1 문의 Controller
 *
 * @author Ghazal
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomepageQnaController {

    private final HomepageQnaService qnaService;

    /**
     * 1:1 문의관리 페이지
     */
    @GetMapping("/bo/system/homepage/qna")
    public String qna(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/system/homepage/qna";

    }

    /**
     * 1:1 문의등록 페이지
     */
    @GetMapping("/bo/system/homepage/qna/form")
    public String qnaForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/system/homepage/qna/form";
    }

    /**
     * 1:1 문의상세 페이지
     */
    @GetMapping("/bo/system/homepage/qna/detail")
    public String qnaDetail(BoQnaDTO boQnaDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", qnaService.selectQnaDetail(boQnaDTO));
        return "/bo/system/homepage/qna/detail";
    }

    /**
     * 1:1 문의상세 수정페이지
     */
    @GetMapping("/bo/system/homepage/qna/modify")
    public String qnaModify(BoQnaDTO boQnaDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("modify", qnaService.selectQnaModify(boQnaDTO));
        return "/bo/system/homepage/qna/modify";
    }

    /**
     * 1:1 문의관리 리스트
     */
    @GetMapping("/bo/system/homepage/qna/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectQnaList(BoQnaDTO boQnaDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(qnaService.selectQnaList(boQnaDTO)).build());
    }

    /**
     * 1:1 문의관리 검색
     */
    @PostMapping("/bo/system/homepage/qna/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchQnaList(BoQnaDTO boQnaDTO, HttpServletRequest request, HttpServletResponse response) {
        boQnaDTO.setSearchStart(boQnaDTO.getSearchStart());
        boQnaDTO.setSearchEnd(boQnaDTO.getSearchEnd());
        boQnaDTO.setAdminName(boQnaDTO.getAdminName());
        boQnaDTO.setMemberName(boQnaDTO.getMemberName());
        boQnaDTO.setMemberEmail(boQnaDTO.getMemberEmail());
        boQnaDTO.setMemberPhone(boQnaDTO.getMemberPhone());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(qnaService.selectQnaList(boQnaDTO)).build());
    }

    /**
     * 1:1 문의관리 수정
     */
    @PostMapping(value = "/bo/system/homepage/qna/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> updateQna(

            @RequestPart(value = "jsonData") BoQnaDTO boQnaDTO,
            @RequestPart(value = "qnaFiles", required = false) MultipartFile[] qnaFiles, HttpServletRequest request, HttpServletResponse response) {

        boQnaDTO.setAnswer(boQnaDTO.getAnswer());
        boQnaDTO.setId(boQnaDTO.getId());
        boQnaDTO.setAttachFileId(boQnaDTO.getAttachFileId());
		boQnaDTO.setQnaFiles(qnaFiles);

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            qnaService.updateQna(boQnaDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 파일 삭제
     */
    @PostMapping("/bo/system/homepage/qna/deleteFile")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteFile(FileDTO fileDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            qnaService.updateFile(fileDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}
