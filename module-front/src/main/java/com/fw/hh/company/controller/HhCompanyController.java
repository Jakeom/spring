package com.fw.hh.company.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.hh.HhCompanyDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.company.service.HhcompanyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhCompanyController {

	private final HhcompanyService hhcompanyService;

	/**
	 * 고객사 관리
	 */
	@GetMapping("/hh/company/company-list")
	public String companyList(ModelMap model, HhCompanyDTO hhCompanyDTO) {

		model.addAttribute("companyList", hhcompanyService.selectHrCompanyList(hhCompanyDTO));
		return "hh/company/company-list";
	}

	/**
	 * 고객사 등록
	 */
	@PostMapping("/hh/company/insertHrCompany")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertHrCompany(@RequestBody HhCompanyDTO hhCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			log.info("insert >>>> {}", hhCompanyDTO.getCompanyName());
			hhcompanyService.insertHrCompany(hhCompanyDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 고객사 삭제
	 */
	@PostMapping("/hh/company/deleteHrCompany")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteHrCompany(@RequestBody HhCompanyDTO hhCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhcompanyService.deleteHrCompany(hhCompanyDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 고객사 상세
	 */
	@GetMapping("/hh/company/headhunter-customer-detail")
	public String companyDetail(ModelMap model, HhCompanyDTO hhCompanyDTO) {
		model.addAttribute("searchInfo", hhCompanyDTO);
		model.addAttribute("companyDetail", hhcompanyService.selectHrCompanyDetail(hhCompanyDTO));
		model.addAttribute("detailCompany", hhcompanyService.selectHrDetailCompany(hhCompanyDTO));
		model.addAttribute("memoList", hhcompanyService.selectHrMemoList(hhCompanyDTO));
		model.addAttribute("positionList", hhcompanyService.selectHrPositionList(hhCompanyDTO));
		model.addAttribute("managerList", hhcompanyService.selectHrManagerList(hhCompanyDTO));
		return "hh/company/headhunter-customer-detail";
	}

	/**
	 * 고객사 수정
	 */
	@PostMapping("/hh/company/updateHrCompany")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateHrCompany(@RequestBody HhCompanyDTO hhCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhcompanyService.updateHrCompany(hhCompanyDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 고객사 메모 등록
	 */
	@PostMapping("/hh/company/insertHrMemo")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertHrMemo(@RequestBody HhCompanyDTO hhCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhcompanyService.insertHrMemo(hhCompanyDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 메모 수정
	 */
	@PostMapping("/hh/company/deleteHrMemo")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteHrMemo(@RequestBody HhCompanyDTO hhCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhcompanyService.deleteHrMemo(hhCompanyDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}
