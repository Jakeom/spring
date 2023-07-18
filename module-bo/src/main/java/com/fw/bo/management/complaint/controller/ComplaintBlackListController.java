package com.fw.bo.management.complaint.controller;


import com.fw.bo.management.complaint.service.ComplaintBlacklistService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoMemberDTO;
import com.fw.core.dto.bo.BoTbBlacklistDTO;
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
 * 블랙리스트 조회 및 관리 Controller
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ComplaintBlackListController {
    private final ComplaintBlacklistService complaintBlacklistService;

    /**
     * 블랙리스트 조회 화면
     */
    @GetMapping("/bo/management/complaint/blacklist")
    public String blackList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/management/complaint/blacklist";
    }

    /**
     * 블랙리스트 추가 화면
     */
    @GetMapping("/bo/management/complaint/blacklist/form")
    public String blackListForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/management/complaint/blacklist/form";
    }

    /**
     * 블랙리스트 상세 조회 화면
     */
    @GetMapping("/bo/management/complaint/blacklist/detail")
    public String detailBlacklist(BoTbBlacklistDTO boTbBlacklistDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", complaintBlacklistService.selectBlacklistDetail(boTbBlacklistDTO));
        return "/bo/management/complaint/blacklist/detail";
    }


    /**
     * 블랙리스트 검색 회원 수 CNT
     */
    @PostMapping("/bo/management/complaint/blacklist/form/member/cnt")
    public ResponseEntity<ResponseVO> selectMemberCnt(@RequestBody BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintBlacklistService.selectMemberCnt(boMemberDTO)).build());
    }



    /**
     * 블랙리스트 리스트 출력
     */
    @GetMapping("/bo/management/complaint/blacklist/list")
    public ResponseEntity<ResponseVO> selectBlacklistList(BoTbBlacklistDTO boTbBlacklistDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintBlacklistService.selectBlacklistList(boTbBlacklistDTO)).build());
    }

    /**
     * 블랙리스트 검색
     */
    @GetMapping("/bo/management/complaint/blacklist/form/search")
    public ResponseEntity<ResponseVO> blackListFormSearch(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintBlacklistService.selectMemberList(boMemberDTO)).build());
    }

    /**
     * 블랙리스트 추가
     */
    @PostMapping("/bo/management/complaint/blacklist/form/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertBlacklist(@RequestBody BoTbBlacklistDTO boTbBlacklistDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintBlacklistService.insertBlacklist(boTbBlacklistDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 블랙리스트 업데이트
     */
    @PostMapping("/bo/management/complaint/blacklist/detail/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateBlacklist(@RequestBody BoTbBlacklistDTO boTbBlacklistDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintBlacklistService.updateBlacklist(boTbBlacklistDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 블랙리스트 삭제
     */
    @PostMapping("/bo/management/complaint/blacklist/detail/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteBlacklist(@RequestBody BoTbBlacklistDTO boTbBlacklistDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintBlacklistService.deleteBlacklist(boTbBlacklistDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}

