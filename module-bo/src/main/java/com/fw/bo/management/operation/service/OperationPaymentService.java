package com.fw.bo.management.operation.service;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoPaymentDTO;
import com.fw.core.mapper.db1.bo.BoPaymentMapper;
import com.fw.core.vo.ResponseVO;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Cancel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationPaymentService {

	/** restAPIKEY */
	@Value("${app-config.bootpay.rest-application-id}")
	private String REST_API_ID;

	/** private-key */
	@Value("${app-config.bootpay.private-key}")
	private String PRIVATE_KEY;

	private final BoPaymentMapper boPaymentMapper;
	/**
	 * 결제관리 리스트 취득
	 */
	public List<BoPaymentDTO> selectPaymentList(BoPaymentDTO boPaymentDTO) {
		return boPaymentMapper.selectPaymentList(boPaymentDTO);
	}
	/**
	 * 결제 검색
	 */
	public List<BoPaymentDTO> saerchPaymentList(BoPaymentDTO boPaymentDTO) {
		return boPaymentMapper.searchPaymentList(boPaymentDTO);
	}
	/**
	 * 결제 상태 확인
	 */
	public List<BoPaymentDTO> saerchStatus(BoPaymentDTO boPaymentDTO) {
		return boPaymentMapper.searchStatus(boPaymentDTO);
	}

	@Transactional
	public ResponseEntity<ResponseVO> receiptCancel(BoPaymentDTO boPaymentDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			Bootpay bootpay = new Bootpay(REST_API_ID, PRIVATE_KEY);
			HashMap<String, Object> token = bootpay.getAccessToken();
			if(token.get("error_code") != null) { //failed
				code = ResponseCode.INTERNAL_SERVER_ERROR;
			}
			Cancel cancel = new Cancel();
			cancel.receiptId = boPaymentDTO.getReceiptId();
			cancel.cancelUsername = boPaymentDTO.getAdminSession().getAdminNm(); // adminNm
			cancel.cancelMessage = "관리자 취소";

			HashMap<String, Object> res = bootpay.receiptCancel(cancel);
			if(res.get("error_code") == null) { //success
				System.out.println("receiptCancel success: " + res);
				boPaymentMapper.updatePayment(BoPaymentDTO.builder()
						.cancelledPrice(boPaymentDTO.getCancelledPrice())
						.remainPrice("0")
						.status("CANCELED")
						.id(boPaymentDTO.getPaymentId()).build());//payment update

				BoPaymentDTO h = boPaymentMapper.selectPurchasePointInfo(boPaymentDTO);

				boPaymentMapper.updatePoint(BoPaymentDTO.builder()
						.freeIncrease(h.getFreeIncrease() * -1)
						.paidIncrease(h.getPaidIncrease() * -1)
						.memberId(boPaymentDTO.getMemberId()) // memberId 찾는법 변경
						.build()); // 포인트 업데이트

				boPaymentMapper.insertPointUseHistory(BoPaymentDTO.builder()
						.paymentId(boPaymentDTO.getPaymentId())
						.type(boPaymentDTO.getType()) // REFUND로 js로 보낼거임
						.memberId(boPaymentDTO.getMemberId()) // memberId 찾는법 변경
						.freeIncrease(h.getFreeIncrease() * -1)
						.paidIncrease(h.getPaidIncrease() * -1)
						.build()); // 포인트 사용내역 추가
			} else {
				System.out.println("receiptCancel false: " + res);
			}
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

}
