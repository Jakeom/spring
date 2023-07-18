package com.fw.hh.wefirm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.hh.HhWefirmCustomerDTO;
import com.fw.core.dto.hh.HhWefirmHeadhunterDTO;
import com.fw.core.dto.hh.HhWefirmPositionDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.wefirm.service.HhMyWefirmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhMyWefirmController {

	private final HhMyWefirmService hhMyWefirmService;

	/**
	 * Wefrim 포지션 공고 리스트
	 */
	@GetMapping("/hh/wefirm/view-position")
	public String viewPositionList(ModelMap model, HhWefirmPositionDTO hhWefirmPositionDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmPositionDTO.setTotalCount(hhMyWefirmService.selectViewPositionListCnt(hhWefirmPositionDTO));
		model.addAttribute("searchInfo", hhWefirmPositionDTO);
		model.addAttribute("viewPositionList", hhMyWefirmService.selectViewPositionList(hhWefirmPositionDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/view-position";
	}

	/**
	 * 소속 헤드헌터
	 */
	@GetMapping("/hh/wefirm/affiliated-headhunter")
	public String affiliatedHeadhunter(ModelMap model, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmHeadhunterDTO.setRowSize(9);
		hhWefirmHeadhunterDTO.setMemberId(hhWefirmHeadhunterDTO.getFrontSession().getId());
		hhWefirmHeadhunterDTO.setTotalCount(hhMyWefirmService.selectHeadhunterListCnt(hhWefirmHeadhunterDTO));
		model.addAttribute("searchInfo", hhWefirmHeadhunterDTO);
		model.addAttribute("wefirmHeadhunterList", hhMyWefirmService.selectHeadhunterList(hhWefirmHeadhunterDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/affiliated-headhunter";
	}

	/**
	 * 헤드헌터 상세
	 */
	@GetMapping("/hh/wefirm/headhunter-info")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectHhInfo(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("hhInfo", hhMyWefirmService.selectHhInfo(hhWefirmHeadhunterDTO));
		dataMap.put("hhFieldInfo", hhMyWefirmService.selectHhFieldInfo(hhWefirmHeadhunterDTO));
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(dataMap).build());
	}

	/**
	 * 고객사 관리
	 */
	@GetMapping("/hh/wefirm/customer")
	public String customer(ModelMap model, HhWefirmCustomerDTO hhWefirmCustomerDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmHeadhunterDTO.setMemberId(hhWefirmHeadhunterDTO.getFrontSession().getId());
		hhWefirmCustomerDTO.setMemberId(hhWefirmCustomerDTO.getFrontSession().getId());
		hhWefirmCustomerDTO.setTotalCount(hhMyWefirmService.selectWefirmCustomerListCnt(hhWefirmCustomerDTO));
		model.addAttribute("searchInfo", hhWefirmCustomerDTO);
		model.addAttribute("customerList", hhMyWefirmService.selectWefirmCustomerList(hhWefirmCustomerDTO));
		model.addAttribute("headhunterList", hhMyWefirmService.selectWefirmHeadhunterList(hhWefirmHeadhunterDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/customer";
	}

	/**
	 * 고객사 상세
	 */
	@GetMapping("/hh/wefirm/customer-info")
	public ResponseEntity<ResponseVO> customerInfo(HhWefirmCustomerDTO hhWefirmCustomerDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhMyWefirmService.selectCustomerInfo(hhWefirmCustomerDTO)).build());
	}

	/**
	 * 고객사 등록
	 */
	@PostMapping("/hh/wefirm/register-customer")
	@ResponseBody
	public ResponseEntity<ResponseVO> registerCustomer(@RequestBody HhWefirmCustomerDTO hhWefirmCustomerDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyWefirmService.insertWefirmCustomer(hhWefirmCustomerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhWefirmCustomerDTO.getResultCode()).build());
	}

	/**
	 * 고객사 삭제
	 */
	@PostMapping("/hh/wefirm/delete-customer")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteCustomer(@RequestBody HhWefirmCustomerDTO hhWefirmCustomerDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyWefirmService.deleteWefirmCustomer(hhWefirmCustomerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 고객사 수정
	 */
	@PostMapping("/hh/wefirm/modify-customer")
	@ResponseBody
	public ResponseEntity<ResponseVO> modifyCustomer(@RequestBody HhWefirmCustomerDTO hhWefirmCustomerDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyWefirmService.updateWefirmCustomer(hhWefirmCustomerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 합격자 상세
	 */
	@GetMapping("/hh/wefirm/passer-info")
	public ResponseEntity<ResponseVO> passerInfo(HhWefirmCustomerDTO hhWefirmCustomerDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhMyWefirmService.selectPasserInfo(hhWefirmCustomerDTO)).build());
	}

	/**
	 * 합격자 등록
	 */
	@PostMapping("/hh/wefirm/register-passer")
	@ResponseBody
	public ResponseEntity<ResponseVO> registerPasser(@RequestPart(value = "jsonData") HhWefirmCustomerDTO hhWefirmCustomerDTO,
			@RequestPart(value = "taxFiles", required = false) MultipartFile[] taxFiles) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhWefirmCustomerDTO.setTaxFiles(taxFiles);
			hhMyWefirmService.insertPasser(hhWefirmCustomerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 합격자 수정
	 */
	@PostMapping("/hh/wefirm/modify-passer")
	@ResponseBody
	public ResponseEntity<ResponseVO> modifyPasser(@RequestPart(value = "jsonData") HhWefirmCustomerDTO hhWefirmCustomerDTO,
			@RequestPart(value = "taxFiles", required = false) MultipartFile[] taxFiles) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhWefirmCustomerDTO.setTaxFiles(taxFiles);
			hhMyWefirmService.updatePasser(hhWefirmCustomerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 합격자 승인
	 */
	@PostMapping("/hh/wefirm/approve-passer")
	@ResponseBody
	public ResponseEntity<ResponseVO> approvePasser(@RequestBody HhWefirmCustomerDTO hhWefirmCustomerDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyWefirmService.updateApprovePasser(hhWefirmCustomerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * Wefrim 포지션 리스트
	 */
	@GetMapping("/hh/wefirm/position-list")
	public String selectPmPositionList(ModelMap model, HhWefirmPositionDTO hhWefirmPositionDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		model.addAttribute("positionList", hhMyWefirmService.selectPmPositionList(hhWefirmHeadhunterDTO));
		model.addAttribute("AllPositionList", hhMyWefirmService.selectPmPositionList(hhWefirmHeadhunterDTO));
		return "hh/wefirm/position-list";
	}

}
