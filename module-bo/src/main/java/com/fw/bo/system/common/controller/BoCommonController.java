package com.fw.bo.system.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fw.bo.system.common.service.BoCommonService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.CommonDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoCommonController {

	private final BoCommonService boCommonService;

	/**
	 * 공통코드 리스트 조회
	 */
	@GetMapping("/bo/cd/{groupCd}")
	public ResponseEntity<ResponseVO> getCommonCodeList(CommonDTO commonDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(boCommonService.selectCommonCd(commonDTO)).build());
	}

	/**
	 * 기관 리스트 조회
	 */
	@GetMapping("/bo/org")
	public ResponseEntity<ResponseVO> getOrgList(CommonDTO commonDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(boCommonService.selectOrgList(commonDTO)).build());
	}

}
