package com.fw.bo.management.operation.controller;

import com.fw.bo.management.operation.service.OperationPaymentService;
import com.fw.bo.management.operation.service.OperationRecruitService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoPaymentDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 결제 관리 Controller
 * @author dongl
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationPaymentController {

    private final OperationPaymentService paymentService;
    /**
     * 결제 관리 페이지
     */
    @GetMapping("bo/management/operation/payment")
    public String payment(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/operation/payment";
    }
    /**
     * 결제 관리 리스트
     */
    @GetMapping("/bo/management/operation/payment/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectPaymentList(BoPaymentDTO boPaymentDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(paymentService.selectPaymentList(boPaymentDTO)).build());
    }
    /**
     * 결제  검색
     */
    @PostMapping("/bo/management/operation/payment/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchPaymentList(BoPaymentDTO boPaymentDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(paymentService.saerchPaymentList(boPaymentDTO)).build());
    }
    /**
     * 결제 상태 확인
     */
    @PostMapping("/bo/management/operation/payment/search/status")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchStatus(BoPaymentDTO boPaymentDTO, HttpServletRequest request, HttpServletResponse response) {
        boPaymentDTO.setStatus(boPaymentDTO.getStatus());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(paymentService.saerchStatus(boPaymentDTO)).build());
    }

    /**
     * 결제 취소
     */
    @PostMapping("/bo/management/operation/payment/purchase-cancel")
    @ResponseBody
    public ResponseEntity<ResponseVO> purchaseCancel(BoPaymentDTO boPaymentDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<ResponseVO> r = paymentService.receiptCancel(boPaymentDTO);
        return r;
    }
}
