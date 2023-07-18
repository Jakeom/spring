package com.fw.fo.mypage.controller;

import com.fw.core.dto.fo.FoInterestHhDTO;
import com.fw.fo.mypage.service.FoAttentionHeadHunterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 관심헤드헌터 Controller
 * 
 * @author wsh
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoAttentionHeadHunterController {

	private final FoAttentionHeadHunterService foAttentionHeadHunterService;

	/**
	 * front 관심 헤드헌터
	 */
	@GetMapping("/fo/mypage/attention-headhunter")
	public String attentionHeadhunter(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			FoInterestHhDTO foInterestHhDTO) {
		foInterestHhDTO.setRowSize(3);

		model.put("interestHhList", foAttentionHeadHunterService.selectInterestHhList(foInterestHhDTO));
		return "fo/mypage/attention-headhunter";
	}

	@GetMapping("/fo/user/mypage/attention-headhunter/detail")
	public String attentionHeadhunterDetail(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			FoInterestHhDTO foInterestHhDTO) {

		model.put("interestHhDetail", foAttentionHeadHunterService.attentionHeadhunterDetail(foInterestHhDTO));
		return "fo/user/mypage/attention_headhunter";
	}

	@PostMapping("/fo/user/mypage/attention-headhunter/delete")
	public String attentionHeadhunterDelete(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			FoInterestHhDTO foInterestHhDTO) {

		foAttentionHeadHunterService.attentionHeadhunterDelete(foInterestHhDTO);
		return "fo/mypage/attention-headhunter";
	}
}