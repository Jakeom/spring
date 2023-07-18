package com.fw.bo.system.org.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.bo.system.org.service.OrgOrgService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoOrgDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 조직 관리 Controller
 * 
 * @author 문종현
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrgOrgController {

	private final OrgOrgService orgOrgService;

	/**
	 * 조직관리 페이지
	 */
	@GetMapping("/bo/system/org/org")
	public String org(ModelMap model) {
		return "bo/system/org/org";
	}
	/**
	 * 조직 관리 form
	 */
	@GetMapping("/bo/system/org/org/form")
	public String orgFrom(ModelMap model) {
		return "bo/system/org/org/form";
	}
	/**
	 * 조직아이디 중복체크
	 */
	@ResponseBody
	@GetMapping("/bo/system/org/org/check")
	public ResponseEntity<ResponseVO> Check(String orgId) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(orgOrgService.selectOrgOverLap(orgId)).build());
	}
	/**
	 * 조직관리 detail
	 */
	@GetMapping("/bo/system/org/org/detail")
	public String detailMember(BoOrgDTO boOrgDTO, ModelMap model) {
		model.addAttribute("detail", orgOrgService.selectOrg(boOrgDTO));
		return "/bo/system/org/org/detail";
	}
	/**
	 * 조직 수정 페이지
	 */
	@GetMapping("/bo/system/org/org/modify")
	public String orgModify(BoOrgDTO boOrgDTO, ModelMap model) {
		model.addAttribute("modify", orgOrgService.selectOrg(boOrgDTO));
		return "/bo/system/org/org/modify";
	}
	/**
	 * 조직 리스트 취득
	 */
	@GetMapping("/bo/system/org/org/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectOrgList(BoOrgDTO boOrgDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(orgOrgService.selectOrgList(boOrgDTO)).build());
	}
	/**
	 * 조직 관리 정보 조회
	 */
	@GetMapping("/bo/system/org/org/info")
	@ResponseBody
	public BoOrgDTO selectOrg(BoOrgDTO boOrgDTO) {
		return orgOrgService.selectOrg(boOrgDTO);
	}
	/**
	 * 조직 관리 정보 등록
	 */
	@PostMapping("/bo/system/org/org/insert")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertOrgInfo(@RequestBody BoOrgDTO boOrgDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			orgOrgService.insertOrgInfo(boOrgDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
	/**
	 * 조직 관리 정보 수정
	 */
	@PostMapping("/bo/system/org/org/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateOrgInfo(@RequestBody BoOrgDTO boOrgDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			orgOrgService.updateOrgInfo(boOrgDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
	/**
	 * 조직 관리 정보 삭제
	 */
	@PostMapping("/bo/system/org/org/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteOrgInfo(@RequestBody BoOrgDTO boOrgDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			orgOrgService.deleteOrgInfo(boOrgDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}
