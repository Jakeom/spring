package com.fw.hh.mypage.controller;

import com.fw.core.dto.hh.HhPickApDTO;
import com.fw.hh.mypage.service.HhPickApService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhPickApController {

	private final HhPickApService hhPickApService;

	/**
	 * front HH 마이페이지-나를 pick한 후보자
	 */
	@GetMapping("/hh/mypage/pick-ap")
	public String MypagePickAp(ModelMap model, HhPickApDTO hhPickApDTO) {
		model.addAttribute("pickApList", hhPickApService.selectPickApList(hhPickApDTO));
		model.addAttribute("searchInfo", hhPickApDTO);
		return "hh/mypage/pick-ap";
	}

}