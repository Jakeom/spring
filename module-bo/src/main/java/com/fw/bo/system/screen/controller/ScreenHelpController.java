package com.fw.bo.system.screen.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.bo.system.screen.service.ScreenHelpService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoHelpDTO;
import com.fw.core.vo.ResponseVO;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;

/**
 * 에디터 Controller
 * 
 * @author
 *
 */

@Controller
@RequiredArgsConstructor
public class ScreenHelpController {
	private final ScreenHelpService helpService;

	/**
	 * 정보 조회
	 */
	@GetMapping("/bo/system/screen/help")
	public String helpcd(BoHelpDTO boHelpDTO, ModelMap model) {
		model.addAttribute("helpList", helpService.selectHelpList(boHelpDTO));
		return "/bo/system/screen/help";
	}
	
	/**
	 * 글 등록 페이지
	 */
	@GetMapping("/bo/system/screen/help/form")
	public String helpForm(ModelMap model) {
		return "/bo/system/screen/help/form";
	}

	@GetMapping("/bo/system/screen/help/detail")
	public String helpetail(ModelMap model, BoHelpDTO boHelpDTO) {
		model.addAttribute("menuCd", boHelpDTO.getMenuCd());
		return "/bo/system/screen/help/detail";
	}
	

	/**
	 * 리스트 조회
	 */
	@GetMapping("/bo/system/screen/help/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectHelpList(BoHelpDTO boHelpDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
		.body(ResponseVO.builder(code).data(helpService.selectHelpList(boHelpDTO)).build());
	}

	/**
	 * 글 등록
	 * 
	 * @param boHelpDTO
	 * @return
	 */
	@PostMapping("/bo/system/screen/help/insert")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertHelpInfo(BoHelpDTO boHelpDTO) {
		try {
			helpService.insertHelpInfo(boHelpDTO);
			ResponseCode code = ResponseCode.SUCCESS;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
		} catch (Exception e) {
			throw e;
		}
	}
	
	

	
}
