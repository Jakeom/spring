package com.fw.bo.management.operation.controller;

import com.fw.bo.management.operation.service.OperationTicketService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoTicketUseHistoryDTO;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationTicketController {

    private final OperationTicketService ticketService;
    /**
     * 티켓 관리 페이지
     */
    @GetMapping("bo/management/operation/ticket")
    public String Ticket(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        return "bo/management/operation/ticket";
    }

    /**
     * 티켓 관리 리스트
     */
    @GetMapping("/bo/management/operation/ticket/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectTicketList(BoTicketUseHistoryDTO boTicketUseHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(ticketService.selectTicketList(boTicketUseHistoryDTO)).build());
    }
    /**
     * 티켓 검색
     */
    @PostMapping("/bo/management/operation/ticket/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchTicketList(BoTicketUseHistoryDTO boTicketUseHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(ticketService.saerchTicketList(boTicketUseHistoryDTO)).build());
    }
    /**
     * 티켓 타입 확인
     * */
//    @PostMapping("/bo/management/operation/ticket/search/status")
//    @ResponseBody
//    public ResponseEntity<ResponseVO> searchStatus(BoTicketUseHistoryDTO boTicketUseHistoryDTO) {
////        System.out.println("hereeeee it isssssssssssss   " + boTicketUseHistoryDTO.getIsCowork());
////        boTicketUseHistoryDTO.setStatus(boTicketUseHistoryDTO.getStatus());
//        ResponseCode code = ResponseCode.SUCCESS;
//        return ResponseEntity.status(code.getHttpStatus())
//                .body(ResponseVO.builder(code).data(ticketService.saerchStatus(boTicketUseHistoryDTO)).build());
//    }

}
