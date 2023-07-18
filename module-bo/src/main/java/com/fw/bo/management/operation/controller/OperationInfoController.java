package com.fw.bo.management.operation.controller;

import com.fw.bo.management.operation.service.OperationInfoService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoInfoDTO;
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
 * 정보관리 Controller
 *
 * @author Ghazal
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationInfoController {

	private final OperationInfoService infoService;

	/**
	 * 정보관리 페이지
	 */
	@GetMapping("/bo/management/operation/info")
	public String info(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/bo/management/operation/info";

	}
	/**
	 * 정보 등록 페이지
	 */
	@GetMapping("/bo/management/operation/info/form")
	public String infoForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/bo/management/operation/info/form";
	}

	/**
	 * 정보 상세 페이지
	 */
	@GetMapping("/bo/management/operation/info/detail")
	public String infoDetail(BoInfoDTO boInfoDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("detail", infoService.selectInfoDetail(boInfoDTO));
		return "/bo/management/operation/info/detail";
	}

	/**
	 * 정보 상세 수정페이지
	 */
	@GetMapping("/bo/management/operation/info/modify")
	public String InfoModify(BoInfoDTO boInfoDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("modify", infoService.selectInfoModify(boInfoDTO));
		return "/bo/management/operation/info/modify";
	}

	/**
	 * 정보 리스트
	 */
	@GetMapping("/bo/management/operation/info/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectInfoList(BoInfoDTO boInfoDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(infoService.selectInfoList(boInfoDTO)).build());
	}

	/**
	 * 정보 검색
	 */
	@PostMapping("/bo/management/operation/info/search")
	@ResponseBody
	public ResponseEntity<ResponseVO> searchInfoList(BoInfoDTO boInfoDTO, HttpServletRequest request, HttpServletResponse response) {
		boInfoDTO.setSearchStart(boInfoDTO.getSearchStart());
		boInfoDTO.setSearchEnd(boInfoDTO.getSearchEnd());
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(infoService.saerchInfoList(boInfoDTO)).build());
	}

	/**
	 * 정보 등록
	 */
	@PostMapping("/bo/management/operation/info/insert")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertInfo(@RequestBody BoInfoDTO boInfoDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			infoService.insertInfo(boInfoDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 정보 수정
	 */
	@PostMapping("/bo/management/operation/info/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateInfo(BoInfoDTO boInfoDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			infoService.updateInfo(boInfoDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 정보 삭제
	 */
	@PostMapping("/bo/management/operation/info/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteInfo(BoInfoDTO boInfoDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			infoService.deleteInfo(boInfoDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}