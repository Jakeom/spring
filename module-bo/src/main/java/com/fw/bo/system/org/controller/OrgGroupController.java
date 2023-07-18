package com.fw.bo.system.org.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.bo.system.org.service.OrgGroupService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoGroupDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 그룹 관리 Controller
 * 
 * @author
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrgGroupController {

	private final OrgGroupService orgGroupService;

	/**
	 * 그룹관리 페이지
	 */
	@GetMapping("/bo/system/org/group")
	public String Group(ModelMap model) {
		return "bo/system/org/group";
	}

	/**
	 * 그룹관리 등록페이지
	 */
	@GetMapping("/bo/system/org/group/form")
	public String formGroup(ModelMap model) {
		return "bo/system/org/group/form";
	}

	/**
	 * 그룹아이디 중복체크
	 */
	@ResponseBody
	@GetMapping("/bo/system/org/group/check")
	public ResponseEntity<ResponseVO> Check(String groupId) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(orgGroupService.selectGroupIdCheck(groupId)).build());
	}

	/**
	 * 그룹관리 상세페이지
	 */
	@GetMapping("/bo/system/org/group/detail")
	public String detailGroup(BoGroupDTO boGroupDTO, ModelMap model) {
		model.addAttribute("detail", orgGroupService.selectGroupInfo(boGroupDTO));
		return "/bo/system/org/group/detail";
	}

	/**
	 * 그룹상세 수정페이지
	 */
	@GetMapping("/bo/system/org/group/modify")
	public String modifyGroup(BoGroupDTO BoGroupDTO, ModelMap model) {
		model.addAttribute("groupModify", orgGroupService.selectGroupModify(BoGroupDTO));
		return "/bo/system/org/group/modify";
	}

	/**
	 * 그룹관리 리스트 취득
	 */
	@GetMapping("/bo/system/group/group/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectGroupList(BoGroupDTO boGroupDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(orgGroupService.selectGroupList(boGroupDTO)).build());
	}

	/**
	 * 그룹관리 정보 조회
	 */
	@GetMapping("/bo/system/group/group/info")
	@ResponseBody
	public BoGroupDTO selectGroupInfo(BoGroupDTO boGroupDTO) {
		return orgGroupService.selectGroupInfo(boGroupDTO);
	}

	/**
	 * 그룹관리 정보 등록
	 */
	@PostMapping("/bo/system/group/group/insert")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertGroupInfo(@RequestBody BoGroupDTO boGroupDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			orgGroupService.insertGroupInfo(boGroupDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 그룹관리 정보 수정
	 */
	@PostMapping("/bo/system/group/group/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateGroupInfo(@RequestBody BoGroupDTO boGroupDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			orgGroupService.updateGroupInfo(boGroupDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 그룹관리 정보 삭제
	 */
	@PostMapping("/bo/system/group/group/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteGroupInfo(@RequestBody BoGroupDTO boGroupDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			orgGroupService.deleteGroupInfo(boGroupDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}