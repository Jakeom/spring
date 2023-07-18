package com.fw.m.company.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.m.MCompanyDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.m.company.service.MCompanyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MCompanyController {

	private final MCompanyService mCompanyService;
	private final CommonFileService commonFileService;

	/**
	 * 포지션 제안 리스트
	 */
	@GetMapping("/m/company/list")
	public String suggetList(ModelMap model, MCompanyDTO mCompanyDTO) {
		model.addAttribute("suggetList", mCompanyService.selectSuggetList(mCompanyDTO));
		model.addAttribute("searchInfo", mCompanyDTO);
		model.addAttribute("positionDetail", mCompanyService.selectPositionDetail(mCompanyDTO));
		return "/m/company/list";
	}

	/**
	 * 결과 수정
	 */
	@PostMapping("/m/company/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateSuggestStatus(@RequestBody MCompanyDTO mCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			mCompanyService.updateSuggestStatus(mCompanyDTO);
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 비밀번호 확인
	 */
	@PostMapping("/m/company/checkPwd")
	@ResponseBody
	public ResponseEntity<ResponseVO> checkPwd(@RequestBody MCompanyDTO mCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(String.valueOf(mCompanyService.selectCheckPassword(mCompanyDTO))).build());
	}

}
