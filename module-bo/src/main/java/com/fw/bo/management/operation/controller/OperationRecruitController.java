package com.fw.bo.management.operation.controller;


import com.fw.core.dto.bo.BoPositionBanWordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.fw.bo.management.operation.service.OperationRecruitService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoRecruitDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationRecruitController {

    private final OperationRecruitService recruitService;

    /**
     * 채용관리 페이지
     */
    @RequestMapping("/bo/management/operation/recruit")
    public String recruit(ModelMap model,BoRecruitDTO boRecruitDTO ,BoPositionBanWordDTO boPositionBanWordDTO, HttpServletRequest request, HttpServletResponse response) {
        int totalCnt = recruitService.searchRecruitListCnt(boRecruitDTO);
        boRecruitDTO.setTotalCount(totalCnt);
        model.addAttribute("recruitList", recruitService.saerchRecruitList(boRecruitDTO));
        model.addAttribute("searchInfo", boRecruitDTO);
        model.addAttribute("banWord", recruitService.selectPositionBanWord(boPositionBanWordDTO));
        return "/bo/management/operation/recruit";

    }

    /**
     * 채용상세 페이지
     */
    @GetMapping("/bo/management/operation/recruit/detail")
    public String recruitDetail(BoRecruitDTO boRecruitDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", recruitService.selectRecruitDetail(boRecruitDTO));
        model.addAttribute("participant", recruitService.selectParticipantName(boRecruitDTO));
        return "/bo/management/operation/recruit/detail";
    }

    /**
     * 채용관리 리스트
     */
    @GetMapping("/bo/management/operation/recruit/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectRecruitList(BoRecruitDTO boRecruitDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(recruitService.selectRecruitList(boRecruitDTO)).build());
    }

    /**
     * 채용관리 검색
     */
    @PostMapping("/bo/management/operation/recruit/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchRecruitList(BoRecruitDTO boRecruitDTO, HttpServletRequest request, HttpServletResponse response) {

        boRecruitDTO.setStatus(boRecruitDTO.getStatus());
        boRecruitDTO.setDelFlag(boRecruitDTO.getDelFlag());
        boRecruitDTO.setPhone(boRecruitDTO.getPhone());
        boRecruitDTO.setName(boRecruitDTO.getName());
        boRecruitDTO.setEmail(boRecruitDTO.getEmail());
        boRecruitDTO.setKeywords(boRecruitDTO.getKeywords());
        boRecruitDTO.setCompanyName(boRecruitDTO.getCompanyName());
        boRecruitDTO.setWord(boRecruitDTO.getWord());
        boRecruitDTO.setSearchStart(boRecruitDTO.getSearchStart());
        boRecruitDTO.setSearchEnd(boRecruitDTO.getSearchEnd());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(recruitService.saerchRecruitList(boRecruitDTO)).build());
    }

    /**
     * 채용 상태 확인
     */
    @PostMapping("/bo/management/operation/recruit/search/isCoWork")
    @ResponseBody
    public ResponseEntity<ResponseVO> coWorkRecruit(BoRecruitDTO boRecruitDTO, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("hereeeee it isssssssssssss   " + boRecruitDTO.getIsCowork());
        boRecruitDTO.setIsCowork(boRecruitDTO.getIsCowork());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(recruitService.saerchCoworkRecruitList(boRecruitDTO)).build());
    }

    /**
     * 채용 수정
     */
    @PostMapping("/bo/management/operation/recruit/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateRecruit(BoRecruitDTO boRecruitDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            recruitService.updateRecruit(boRecruitDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    @PostMapping("/bo/management/operation/recruit/ban/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertPositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(recruitService.insertPositionBanWord(boPositionBanWordDTO)).build());
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
        }
    }

    @PostMapping("/bo/management/operation/recruit/ban/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> updatePositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            recruitService.updatePositionBanWord(boPositionBanWordDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}