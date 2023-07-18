package com.fw.hh.mypage.service;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.hh.HhNoticeSettingDTO;
import com.fw.core.dto.hh.HhPaymentDTO;
import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhPointUseHistoryDTO;
import com.fw.core.mapper.db1.hh.HhMypageMapper;
import com.fw.core.mapper.db1.hh.HhPointMapper;
import com.fw.core.vo.ResponseVO;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Cancel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhMypageService {
	/** restAPIKEY */
	@Value("${app-config.bootpay.rest-application-id}")
	private String REST_API_ID;

	/** private-key */
	@Value("${app-config.bootpay.private-key}")
	private String PRIVATE_KEY;
	private final HhMypageMapper hhMypageMapper;
	private final HhPointMapper hhPointMapper;


	public void updateNoticeSetting(HhNoticeSettingDTO hhNoticeSettingDTO) {

		hhMypageMapper.updateHhApFlag(hhNoticeSettingDTO); // 맞춤인재 알림여부 수정
		if(StringUtils.isBlank(hhNoticeSettingDTO.getCareerSt())) {
			hhNoticeSettingDTO.setCareerSt(null);
		}
		if(StringUtils.isBlank(hhNoticeSettingDTO.getCareerEn())) {
			hhNoticeSettingDTO.setCareerEn(null);
		}
		if(StringUtils.isBlank(hhNoticeSettingDTO.getAgeSt())) {
			hhNoticeSettingDTO.setAgeSt(null);
		}
		if(StringUtils.isBlank(hhNoticeSettingDTO.getAgeEn())) {
			hhNoticeSettingDTO.setAgeEn(null);
		}
		if(StringUtils.isBlank(hhNoticeSettingDTO.getSalarySt())) {
			hhNoticeSettingDTO.setSalarySt(null);
		}
		if(StringUtils.isBlank(hhNoticeSettingDTO.getSalaryEn())) {
			hhNoticeSettingDTO.setSalaryEn(null);
		}

		if (StringUtils.isBlank(hhNoticeSettingDTO.getApAlertSeq())) { // 등록
			hhMypageMapper.insertApAlert(hhNoticeSettingDTO);
			if (hhNoticeSettingDTO.getHp2List() != null && hhNoticeSettingDTO.getHp2List().length > 0) {
				hhMypageMapper.insertHp2(hhNoticeSettingDTO);
			}
			if (hhNoticeSettingDTO.getLocList() != null && hhNoticeSettingDTO.getLocList().length > 0) {
				hhMypageMapper.insertLoc(hhNoticeSettingDTO);
			}
		} else { // 수정
			hhMypageMapper.updateApAlert(hhNoticeSettingDTO);
			hhMypageMapper.deleteHp2(hhNoticeSettingDTO);
			hhMypageMapper.deleteLoc(hhNoticeSettingDTO);
			if (hhNoticeSettingDTO.getHp2List() != null && hhNoticeSettingDTO.getHp2List().length > 0) {
				hhMypageMapper.insertHp2(hhNoticeSettingDTO);
			}
			if (hhNoticeSettingDTO.getLocList() != null && hhNoticeSettingDTO.getLocList().length > 0) {
				hhMypageMapper.insertLoc(hhNoticeSettingDTO);
			}
		}
	}

	public HhNoticeSettingDTO selectApAlertInfo(HhNoticeSettingDTO hhNoticeSettingDTO) {
		return hhMypageMapper.selectApAlertInfo(hhNoticeSettingDTO);
	}

	public List<HhNoticeSettingDTO> selectHp2List(HhNoticeSettingDTO hhNoticeSettingDTO) {
		return hhMypageMapper.selectHp2List(hhNoticeSettingDTO);
	}

	public List<HhNoticeSettingDTO> selectLocList(HhNoticeSettingDTO hhNoticeSettingDTO) {
		return hhMypageMapper.selectLocList(hhNoticeSettingDTO);
	}

	public boolean selectApAlertExists(HhNoticeSettingDTO hhNoticeSettingDTO) {
		return hhMypageMapper.selectApAlertExists(hhNoticeSettingDTO);
	}

	public HhPointDTO selectMyPoint(HhPointDTO hhPointDTO) {
		return hhMypageMapper.selectMyPoint(hhPointDTO);
	}

	public List<HhPointUseHistoryDTO> selectPointUseHistory(HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		String startDt = hhPointUseHistoryDTO.getStartDt();
		String endDt = hhPointUseHistoryDTO.getEndDt();
		if(StringUtils.isBlank(startDt) && StringUtils.isNotBlank(endDt)) { // 이전날짜 ~ endDt 검색조건
			hhPointUseHistoryDTO.setDateSearchType("endDt");
		}
		if(StringUtils.isBlank(endDt) && StringUtils.isNotBlank(startDt)) { // startDt ~ 이후날짜 검색조건
			hhPointUseHistoryDTO.setDateSearchType("startDt");
		}
		return hhMypageMapper.selectPointUseHistory(hhPointUseHistoryDTO);
	}

	public int selectPointUseHistoryCnt(HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		return hhMypageMapper.selectPointUseHistoryCnt(hhPointUseHistoryDTO);
	}

	// 결제취소 가능 포인트 조회
	public List<HhPointUseHistoryDTO> selectPurchasePoint(HhPointUseHistoryDTO hhPointUseHistoryDTO){
		return hhPointMapper.selectPurchasePoint(hhPointUseHistoryDTO);
	}

	@Transactional
	public void updatePurchasePoint(HhPaymentDTO hhPaymentDTO){
		FoPointDTO hhPoint = hhPointMapper.selectHhPoint(FoPointDTO.builder()
							.memberId(hhPaymentDTO.getMemberId())
							.build());
		if(hhPoint.getId() == null){
			hhPointMapper.insertPointHh(FoPointDTO.builder()
					.memberId(hhPaymentDTO.getMemberId())
					.build()); // 포인트 생성
		}
		hhPaymentDTO.setRemainPrice(hhPaymentDTO.getPrice());
		hhPointMapper.insertPayment(hhPaymentDTO);//payment insert

		hhPointMapper.updatePoint(FoPointDTO.builder()
				.freeIncrease(hhPaymentDTO.getFreeIncrease())
				.paidIncrease(hhPaymentDTO.getPaidIncrease())
				.memberId(hhPaymentDTO.getMemberId())
				.build()); // 포인트 업데이트

		hhPointMapper.insertPointUseHistory(FoPointDTO.builder()
				.paymentId(hhPaymentDTO.getId())
				.type(hhPaymentDTO.getType())
				.memberId(hhPaymentDTO.getMemberId())
				.freeIncrease(hhPaymentDTO.getFreeIncrease())
				.paidIncrease(hhPaymentDTO.getPaidIncrease())
				.build()); // 포인트 사용내역 추가
	}

	@Transactional
	public ResponseEntity<ResponseVO> receiptCancel(HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			Bootpay bootpay = new Bootpay(REST_API_ID, PRIVATE_KEY);
			HashMap<String, Object> token = bootpay.getAccessToken();
			if(token.get("error_code") != null) { //failed
				code = ResponseCode.INTERNAL_SERVER_ERROR;
			}
			Cancel cancel = new Cancel();
			cancel.receiptId = hhPointUseHistoryDTO.getReceiptId();
			cancel.cancelUsername = hhPointUseHistoryDTO.getFrontSession().getUsername();
			cancel.cancelMessage = "사용자 취소";

			HashMap<String, Object> res = bootpay.receiptCancel(cancel);
			if(res.get("error_code") == null) { //success
				System.out.println("receiptCancel success: " + res);

				hhPointMapper.updatePayment(HhPaymentDTO.builder()
						.cancelledPrice(hhPointUseHistoryDTO.getCancelledPrice())
						.remainPrice("0")
						.status("CANCELED")
						.id(hhPointUseHistoryDTO.getPaymentId()).build());//payment update

				HhPointUseHistoryDTO h = hhPointMapper.selectPurchasePointInfo(hhPointUseHistoryDTO);

				hhPointMapper.updatePoint(FoPointDTO.builder()
						.freeIncrease(-Integer.parseInt(h.getFreeIncrease()))
						.paidIncrease(-Integer.parseInt(h.getPaidIncrease()))
						.memberId(hhPointUseHistoryDTO.getFrontSession().getId())
						.build()); // 포인트 업데이트

				hhPointMapper.insertPointUseHistory(FoPointDTO.builder()
						.paymentId(hhPointUseHistoryDTO.getPaymentId())
						.type(hhPointUseHistoryDTO.getType())
						.memberId(hhPointUseHistoryDTO.getFrontSession().getId())
						.freeIncrease(-Integer.parseInt(h.getFreeIncrease()))
						.paidIncrease(-Integer.parseInt(h.getPaidIncrease()))
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