package com.fw.bo.system.auth.controller;

import com.fw.bo.system.auth.service.AuthGroupService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthGroupDTO;
import com.fw.core.dto.bo.BoGroupDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 그룹별 권한 관리 Controller
 *
 * @YJW
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthGroupController {

	private final AuthGroupService authGroupService;

	/**
	 * 그룹별 권한 관리
	 */
	@GetMapping("/bo/system/auth/group")
	public String authGroup(ModelMap model, BoAdminMenuProgramDTO boAdminMenuProgramDTO, BoGroupDTO boGroupDTO) {
		model.addAttribute("menuProgram", authGroupService.selectMenuProgram(boAdminMenuProgramDTO));
		model.addAttribute("group", authGroupService.selectGroupList(boGroupDTO));
		return "/bo/system/auth/group";
	}

	/**
	 * 그룹별 권한 정보
	 */
	@GetMapping("/bo/system/auth/group/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectGroupAuth(BoAuthGroupDTO boAuthGroupDTO, ModelMap model) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(authGroupService.selectGroupAuth(boAuthGroupDTO)).build());
	}

	/**
	 * 그룹별 권한 부여
	 */
	@RequestMapping("/bo/system/auth/group/insert")
	public ResponseEntity<ResponseVO> insert(@RequestBody BoAuthGroupDTO boAuthGroupDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			authGroupService.insertGroupAuth(boAuthGroupDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

}
