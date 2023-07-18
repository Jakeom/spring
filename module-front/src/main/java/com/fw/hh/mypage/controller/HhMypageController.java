package com.fw.hh.mypage.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.hh.HhNoticeSettingDTO;
import com.fw.core.dto.hh.HhPaymentDTO;
import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhPointUseHistoryDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.system.config.security.FoLoginService;
import com.fw.hh.mypage.service.HhMypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 마이페이지 Controller
 *
 * @author wsh
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HhMypageController {

	private final HhMypageService hhMypageService;
	private final CommonService commonService;
	private final FoLoginService foLoginService;
	private final CommonFileService commonFileService;

	/**
	 * front HH 메인 마이페이지
	 */
	@GetMapping("/hh/mypage/notice-setting")
	public String MypageNoticeSetting(ModelMap model, HhNoticeSettingDTO hhNoticeSettingDTO,
									  HttpServletRequest request, HttpServletResponse response) {
		boolean existence = hhMypageService.selectApAlertExists(hhNoticeSettingDTO);
		model.addAttribute("noticeSetting", hhMypageService.selectApAlertInfo(hhNoticeSettingDTO));
		model.addAttribute("existence", existence);
		return "hh/mypage/notice-setting";
	}

	/**
	 * front HH 마이페이지-포인트 충전
	 */
	@GetMapping("/hh/mypage/point-charge")
	public String MypagePointCharge(ModelMap model, HhPointDTO hhPointDTO, HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		model.addAttribute("pointInfo", hhMypageService.selectMyPoint(hhPointDTO));
		hhPointUseHistoryDTO.setReasonCd("ADD_PURCHASE");
		model.addAttribute("purchaseList", hhMypageService.selectPurchasePoint(hhPointUseHistoryDTO));
		hhPointUseHistoryDTO.setReasonCd("REFUND");
		model.addAttribute("doneRefundList", hhMypageService.selectPurchasePoint(hhPointUseHistoryDTO));
		return "hh/mypage/point-charge";
	}

	/**
	 * 포인트내역/입금정보 리스트
	 */
	@GetMapping("/hh/mypage/point-use-history")
	public String selectPointUseHistory(ModelMap model, HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		log.info(hhPointUseHistoryDTO.getSelectPeriod());
		log.info(hhPointUseHistoryDTO.getUseType());
		CommonDTO commonDTO = new CommonDTO();
		commonDTO.setGroupCd("POINT_USE");
		model.addAttribute("pointUseType", commonService.selectCommonCodeList(commonDTO));
		hhPointUseHistoryDTO.setTotalCount(hhMypageService.selectPointUseHistoryCnt(hhPointUseHistoryDTO));
		model.addAttribute("pointUseHistory", hhMypageService.selectPointUseHistory(hhPointUseHistoryDTO));
		model.addAttribute("searchInfo", hhPointUseHistoryDTO);
		return "hh/mypage/point-use-history";
	}

	/**
	 * HH 맞춤인재알림설정
	 */
	@PostMapping("/hh/mypage/update-notice-setting")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateNoticeSetting(@RequestBody HhNoticeSettingDTO hhNoticeSettingDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMypageService.updateNoticeSetting(hhNoticeSettingDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 학력2 조회
	 */
	@GetMapping("/hh/mypage/hp2")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectHp2(HhNoticeSettingDTO hhNoticeSettingDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhMypageService.selectHp2List(hhNoticeSettingDTO)).build());
	}

	/**
	 * 희망근무지 조회
	 */
	@GetMapping("/hh/mypage/loc")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectLoc(HhNoticeSettingDTO hhNoticeSettingDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhMypageService.selectLocList(hhNoticeSettingDTO)).build());
	}

	/**
	 * 포인트 구매시 포인트 업데이트
	 */
	@PostMapping("/hh/mypage/purchase-point")
	@ResponseBody
	public ResponseEntity<ResponseVO> updatePoint(HhPaymentDTO hhPaymentDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "false";
		try {
			hhPaymentDTO.setMemberId(hhPaymentDTO.getFrontSession().getId());
			hhMypageService.updatePurchasePoint(hhPaymentDTO);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, foSessionDTO.getUsername()));
			serviceCode = "true";
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 결제취소
	 */
	@PostMapping("/hh/mypage/purchase-cancel")
	@ResponseBody
	public ResponseEntity<ResponseVO> purchaseCancel( HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		ResponseEntity<ResponseVO> r = hhMypageService.receiptCancel(hhPointUseHistoryDTO);
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, foSessionDTO.getUsername()));
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return r;
	}

	/**
	 * @description 새로운 인증 생성
	 * @param currentAuth 현재 auth 정보
	 * @param username	현재 사용자 Id
	 * @return Authentication
	 * @author Armton
	 */
	protected Authentication createNewAuthentication(Authentication currentAuth, String userId) {
		FoSessionDTO foSessionDTO = (FoSessionDTO) foLoginService.loadUserByUsername(userId);
		List<FileDTO> fileList = commonFileService.selectFileDetailList(foSessionDTO.getProfilePictureFileId());
		if(fileList != null) {
			foSessionDTO.setProfileUrl(fileList.get(0).getUrl());
		}
		UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(foSessionDTO, currentAuth.getCredentials(), foSessionDTO.getAuthorities());
		newAuth.setDetails(currentAuth.getDetails());
		return newAuth;
	}
}

