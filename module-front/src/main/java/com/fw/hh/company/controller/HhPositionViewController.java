package com.fw.hh.company.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.fw.core.dto.hh.HhCompanyDTO;
import com.fw.hh.company.service.HhPositionViewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhPositionViewController {

	private final HhPositionViewService hhPositionViewService;

	/**
	 * 포지션 상세
	 */
	@GetMapping("/hh/company/headhunter-view")
	public String companyDetail(ModelMap model, HhCompanyDTO hhCompanyDTO) {
		HhCompanyDTO positionDto = null;
		HhCompanyDTO companyDto = null;
		if(StringUtils.isNotBlank(hhCompanyDTO.getPositionId())){
			positionDto = hhPositionViewService.selectPositionDetailByPositionId(hhCompanyDTO);
			companyDto = hhPositionViewService.selectCompanyDetailByPositionId(hhCompanyDTO);
		} else {
			positionDto = hhPositionViewService.selectPositionDetail(hhCompanyDTO);
			companyDto = hhPositionViewService.selectCompanyDetail(hhCompanyDTO);
		}
		model.addAttribute("companyDetail", companyDto);
		model.addAttribute("positionDetail", positionDto);
		return "hh/company/headhunter-view";
	}
}
