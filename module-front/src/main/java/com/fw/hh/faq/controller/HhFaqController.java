package com.fw.hh.faq.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.hh.HhFaqDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.mypage.service.FoMyPageProfileInfoService;
import com.fw.hh.faq.service.HhFaqService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * faq Controller
 * 
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HhFaqController {

	private final HhFaqService hhFaqService;
	private final FoMyPageProfileInfoService foMyPageProfileInfoService;
	@Value("${cert.server}")
	private String CERT_SERVER;

	@Value("${cert.callback-url}")
	private String CERT_CALLBACK_URL;

	/**
	 * 자주 묻는 질문 (회원)
	 */
	@GetMapping("/hh/service/faq-individual")
	public String faqIndividual(HhFaqDTO hhFaqDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("faqCategorySeq", hhFaqDTO.getFaqCategorySeq());
		model.addAttribute("faqSeq", hhFaqDTO.getFaqSeq());
		hhFaqDTO.setMemberTypeCd("AP");
		List<HhFaqDTO> cateList = hhFaqService.selectFaqCateList(hhFaqDTO);
		model.addAttribute("cateList", cateList);
		hhFaqDTO.setFaqCategorySeq(cateList.get(0).getFaqCategorySeq());
		model.addAttribute("faqList", hhFaqService.selectFaqList(hhFaqDTO));
		return "hh/service/faq-individual";
	}

	/**
	 * 자주 묻는 질문 카테고리별
	 */
	@GetMapping("/hh/service/faqList")
	@ResponseBody
	public ResponseEntity<ResponseVO> faqList(HhFaqDTO hhFaqDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("faqList", hhFaqService.selectFaqList(hhFaqDTO));
		ResponseCode code = ResponseCode.SUCCESS;
		List<HhFaqDTO> list = new ArrayList<>();
		try {
			list = hhFaqService.selectFaqList(hhFaqDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(list).build());
	}

	/**
	 * 자주 묻는 질문 (헤트한터)
	 */
	@GetMapping("/hh/service/faq-headhunter")
	public String faqHeadhunter(HhFaqDTO hhFaqDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		hhFaqDTO.setMemberTypeCd("HH");
		List<HhFaqDTO> cateList = hhFaqService.selectFaqCateList(hhFaqDTO);
		model.addAttribute("cateList", cateList);
		hhFaqDTO.setFaqCategorySeq(cateList.get(0).getFaqCategorySeq());
		model.addAttribute("faqList", hhFaqService.selectFaqList(hhFaqDTO));
		return "hh/service/faq-headhunter";
	}

	/**
	 * 회원탈퇴
	 */
	@GetMapping("/hh/service/withdrawal")
	public String withdrawal(ModelMap model, FoMemberDTO foMemberDTO, HttpServletRequest request, HttpServletResponse response) {
		// model.addAttribute("certServer", CERT_SERVER); // 본인인증 URL
		// model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL); // 본인인증 CALLBACK URL
		model.addAttribute("profileInfo", foMyPageProfileInfoService.selectProfileInfo(foMemberDTO));
		return "hh/service/withdrawal";
	}
}