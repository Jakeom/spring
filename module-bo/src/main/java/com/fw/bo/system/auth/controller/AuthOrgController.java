package com.fw.bo.system.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.bo.system.auth.service.AuthOrgService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthOrgDTO;
import com.fw.core.dto.bo.BoOrgDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthOrgController {

	private final AuthOrgService orgAuthService;

	/**
	 * 조직 권한 관리
	 */
	@GetMapping("/bo/system/auth/org")
	public String org(BoAdminMenuProgramDTO boAdminMenuProgramDTO, ModelMap model, BoOrgDTO boOrgDTO) {
		model.addAttribute("menuProgramList", orgAuthService.selectMenuProgramList(boAdminMenuProgramDTO));
		model.addAttribute("orgList", orgAuthService.selectOrgList(boOrgDTO));
		return "/bo/system/auth/org";
	}

	/**
	 * 조직 권한 리스트 취득
	 */
	@GetMapping("/bo/system/auth/org/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectAuthList(BoAuthOrgDTO boAuthOrgDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(orgAuthService.selectAuthList(boAuthOrgDTO)).build());
	}

	/**
	 * 조직 권한 적용
	 */
	@PostMapping("/bo/system/auth/org/insert")
	@ResponseBody
	public ResponseEntity<ResponseVO> insert(@RequestBody BoAuthOrgDTO boAuthOrgDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			orgAuthService.insertAuthOrg(boAuthOrgDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

}
