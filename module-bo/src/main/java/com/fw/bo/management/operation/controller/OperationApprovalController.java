package com.fw.bo.management.operation.controller;

import com.fw.bo.management.operation.service.OperationApprovalService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.*;
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
 * 승인 관리 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationApprovalController {
    private final OperationApprovalService approvalService;

    /**
     * 헤드한터 페이지
     */
    @GetMapping("bo/management/operation/approval/headhunter")
    public String headhunter(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        return "bo/management/operation/approval/headhunter";
    }
    
    /**
     * 서치펌 페이지
     */
    @GetMapping("bo/management/operation/approval/searchfirm")
    public String searchfrim(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        return "bo/management/operation/approval/searchfirm";
    }

    /**
     * 위펌 페이지
     */
    @GetMapping("bo/management/operation/approval/wefirm")
    public String wefirm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        return "bo/management/operation/approval/wefirm";
    }

    /**
     * 헤드한터 리스트
     */
    @GetMapping("/bo/management/operation/approval/headhunter/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectHeadhunterRequestList(BoHhApprovalRequestDTO boHhApprovalRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.selectHeadhunterRequestList(boHhApprovalRequestDTO)).build());
    }

    /**
     * hh Request 상세 (모달)
     */
    @GetMapping("/bo/management/operation/approval/headhunter/hhRequestDetail")
    @ResponseBody
    public ResponseEntity<ResponseVO> hhRequestDetail(BoHhApprovalRequestDTO boHhApprovalRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.selecthhRequestDetail(boHhApprovalRequestDTO)).build());
    }

    /**
     * 헤드한터 검색
     */
    @PostMapping("/bo/management/operation/approval/headhunter/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchHeadhunterRequestList(BoHhApprovalRequestDTO boHhApprovalRequestDTO, HttpServletRequest request, HttpServletResponse response) {

        boHhApprovalRequestDTO.setAdminName(boHhApprovalRequestDTO.getAdminName());
        boHhApprovalRequestDTO.setMemberName(boHhApprovalRequestDTO.getMemberName());
        boHhApprovalRequestDTO.setMemberEmail(boHhApprovalRequestDTO.getMemberEmail());
        boHhApprovalRequestDTO.setMemberPhone(boHhApprovalRequestDTO.getMemberPhone());
        boHhApprovalRequestDTO.setSearchStart(boHhApprovalRequestDTO.getSearchStart());
        boHhApprovalRequestDTO.setSearchEnd(boHhApprovalRequestDTO.getSearchEnd());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.saerchHeadhunterRequestList(boHhApprovalRequestDTO)).build());
    }

    /**
     * 헤드한터 상태 확인
     * */
    @PostMapping("/bo/management/operation/approval/headhunter/search/status")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchStatus(BoHhApprovalRequestDTO boHhApprovalRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        boHhApprovalRequestDTO.setStatus(boHhApprovalRequestDTO.getStatus());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.saerchHeadhunterStatus(boHhApprovalRequestDTO)).build());
    }

    /**
     * 헤드한터 상태 거절
     */
    @PostMapping("/bo/management/operation/approval/headhunter/reject")
    @ResponseBody
    public ResponseEntity<ResponseVO> hhReject(@RequestBody BoHhApprovalRequestDTO boHhApprovalRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            approvalService.updateRejectRequest(boHhApprovalRequestDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

    /**
     * 헤드한터 상태 승인
     */
    @PostMapping("/bo/management/operation/approval/headhunter/accept")
    @ResponseBody
    public ResponseEntity<ResponseVO> hhAccept(@RequestBody BoHhApprovalRequestDTO boHhApprovalRequestDTO, HttpServletRequest request, HttpServletResponse response) {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            approvalService.updateAcceptRequest(boHhApprovalRequestDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

    /**
     * 서치펌 리스트
     */
    @GetMapping("bo/management/operation/approval/searchfirm/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectSearchfirmList(BoSfChangeRequestDTO boSfChangeRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.selectSFRequestList(boSfChangeRequestDTO)).build());
    }

    /**
     * 서치펌 상세 (모달)
     */
    @GetMapping("/bo/management/operation/approval/searchFirm/sfDetail")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchFirmDetail(BoSfChangeRequestDTO boSfChangeRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.selectSearchFirmDetail(boSfChangeRequestDTO)).build());
    }

    /**
     * 서치펌 검색
     */
    @PostMapping("bo/management/operation/approval/searchfirm/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchApprovalList(BoSfChangeRequestDTO boSfChangeRequestDTO, HttpServletRequest request, HttpServletResponse response) {

      boSfChangeRequestDTO.setAdminName(boSfChangeRequestDTO.getAdminName());
      boSfChangeRequestDTO.setMemberName(boSfChangeRequestDTO.getMemberName());
      boSfChangeRequestDTO.setMemberEmail(boSfChangeRequestDTO.getMemberEmail());
      boSfChangeRequestDTO.setMemberPhone(boSfChangeRequestDTO.getMemberPhone());
      boSfChangeRequestDTO.setSearchStart(boSfChangeRequestDTO.getSearchStart());
      boSfChangeRequestDTO.setSearchEnd(boSfChangeRequestDTO.getSearchEnd());

        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.saerchSFRequestList(boSfChangeRequestDTO)).build());
    }
    /**
     * 서치펌 상태 확인
     * */
    @PostMapping("bo/management/operation/approval/searchfirm/search/status")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchStatus(BoSfChangeRequestDTO boSfChangeRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        boSfChangeRequestDTO.setStatus(boSfChangeRequestDTO.getStatus());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.saerchSFStatus(boSfChangeRequestDTO)).build());
    }
    /**
     * 서치펌 상태 거절
     */
    @PostMapping("/bo/management/operation/approval/searchfirm/reject")
    @ResponseBody
    public ResponseEntity<ResponseVO> sFReject(@RequestBody BoSfChangeRequestDTO boSfChangeRequestDTO, HttpServletRequest request, HttpServletResponse response) {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            approvalService.updateSFRejectRequest(boSfChangeRequestDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }
    /**
     * 서치펌 상태 승인
     */
    @PostMapping("/bo/management/operation/approval/searchfirm/accept")
    @ResponseBody
    public ResponseEntity<ResponseVO> sFAccept(@RequestBody BoSfChangeRequestDTO boSfChangeRequestDTO, HttpServletRequest request, HttpServletResponse response) {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            approvalService.updateSFAcceptRequest(boSfChangeRequestDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

    /**
     * 위펌 리스트
     */
    @GetMapping("bo/management/operation/approval/wefirm/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectWefirmRequestList(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.selectWefirmRequestList(boWefirmRequestHistoryDTO)).build());
    }

    /**
     * 위펌 상세 (모달)
     */
    @GetMapping("/bo/management/operation/approval/wefirm/wefirmDetail")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectWefirmDetail(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.selectWefirmDetail(boWefirmRequestHistoryDTO)).build());
    }

    /**
     * 위펌 검색
     */
    @PostMapping("/bo/management/operation/approval/wefirm/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchWefirmRequestList(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.saerchWefirmRequestList(boWefirmRequestHistoryDTO)).build());
    }
    /**
     * 위펌 상태 거절
     */
    @PostMapping("/bo/management/operation/approval/wefirm/reject")
    @ResponseBody
    public ResponseEntity<ResponseVO> wefirmReject(@RequestBody BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO, HttpServletRequest request, HttpServletResponse response) {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            approvalService.updateWefirmRejectRequest(boWefirmRequestHistoryDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }
    /**
     * 위펌 상태 승인
     */
    @PostMapping("/bo/management/operation/approval/wefirm/accept")
    @ResponseBody
    public ResponseEntity<ResponseVO> wefirmAccept(@RequestBody BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO, HttpServletRequest request, HttpServletResponse response) {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            approvalService.updateWefirmAcceptRequest(boWefirmRequestHistoryDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }
    /**
     * 위펌 상태 확인
     * */
    @PostMapping("bo/management/operation/approval/wefirm/search/status")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchStatus(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        boWefirmRequestHistoryDTO.setStatus(boWefirmRequestHistoryDTO.getStatus());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(approvalService.saerchWefirmStatus(boWefirmRequestHistoryDTO)).build());
    }

}
