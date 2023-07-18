package com.fw.bo.system.setting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.bo.system.setting.service.BoCommonCdService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoCommonCdDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoCommonCdController {

	private final BoCommonCdService boCommonCdService;

	/**
	 * 공통코드 리스트 조회
	 */
	@GetMapping("/bo/system/setting/common-cd")
	public String commonCd(BoCommonCdDTO boCommonCdDTO, ModelMap model) {
		boCommonCdDTO.setCdLevel("1");
		model.addAttribute("commonCdList", boCommonCdService.selectCommonCodeList(boCommonCdDTO));
		return "/bo/system/setting/common-cd";
	}

	/**
	 * 공통코드 리스트
	 */
	@PostMapping("/bo/system/setting/common-cd/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectCommonCodeList(BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		log.info("{}", boCommonCdDTO);
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(boCommonCdService.selectCommonCodeList(boCommonCdDTO)).build());
	}

	/**
	 * 공통코드 리스트 상세조회
	 */
	@PostMapping("/bo/system/setting/common-cd/info")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectCommonCodeInfo(BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		log.info("{}", boCommonCdDTO);
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(boCommonCdService.selectCommonCodeInfo(boCommonCdDTO)).build());
	}

	/**
	 * 공통코드 리스트 상세조회
	 */
	@PostMapping("/bo/system/setting/common-cd/detail/info")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectCommonCodeDetailInfo(BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		log.info("{}", boCommonCdDTO);
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(boCommonCdService.selectCommonCodeDetailInfo(boCommonCdDTO)).build());
	}

	/**
	 * 그룹코드 중복체크
	 */
	@ResponseBody
	@GetMapping("/bo/system/setting/common-cd/check")
	public ResponseEntity<ResponseVO> Check(String groupCd) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boCommonCdService.selectGroupCdCheck(groupCd)).build());
	}

	/**
	 * 코드 중복체크
	 */
	@ResponseBody
	@GetMapping("/bo/system/setting/common-cd/cd/check")
	public ResponseEntity<ResponseVO> cdCheck(String cd) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boCommonCdService.selectCdCheck(cd)).build());
	}

	/**
	 * 공통코드(cd_level=1) 등록
	 */
	@PostMapping("/bo/system/setting/common-cd/insert")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertCommonCd(@RequestBody BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boCommonCdService.insertCommonCd(boCommonCdDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 공통코드(cd_level=2) 등록
	 */
	@PostMapping("/bo/system/setting/common-cd/detail/insert")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertDetailCommonCd(@RequestBody BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boCommonCdService.insertDetailCommonCd(boCommonCdDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 공통코드(cd_level=1) 수정
	 */
	@PostMapping("/bo/system/setting/common-cd/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateCommonCd(@RequestBody BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boCommonCdService.updateCommonCd(boCommonCdDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 공통코드(cd_level=2) 수정
	 */
	@PostMapping("/bo/system/setting/common-cd/detail/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateDetailCommonCd(@RequestBody BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boCommonCdService.updateDetailCommonCd(boCommonCdDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 공통코드(cd_level=1) 삭제
	 */
	@PostMapping("/bo/system/setting/common-cd/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteCommonCd(@RequestBody BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boCommonCdService.deleteCommonCd(boCommonCdDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 공통코드(cd_level=2) 삭제
	 */
	@PostMapping("/bo/system/setting/common-cd/detail/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteDetailCommonCd(@RequestBody BoCommonCdDTO boCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boCommonCdService.deleteDetailCommonCd(boCommonCdDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}
