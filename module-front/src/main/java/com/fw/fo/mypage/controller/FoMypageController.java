package com.fw.fo.mypage.controller;

import com.fw.core.dto.fo.FoLoginLogDTO;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.fo.mypage.service.FoMypageService;
import com.fw.fo.position.service.FoPositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 마이페이지 Controller
 *
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoMypageController {

	private final FoMypageService foMypageService;
	private final FoPositionService foPositionService;

	/**
	 * front 메인 마이페이지
	 */
	@GetMapping("/fo/user/mypage/profile-info")
	public String mypage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "fo/user/mypage/profile_info";
	}

    /**
     * front 메인 로그인 기록
     */
    @GetMapping("/fo/mypage/login-log")
    public String loginLog(ModelMap model, HttpServletRequest request, HttpServletResponse response, FoLoginLogDTO foLoginLogDTO) {
		foLoginLogDTO.setTotalCount(foMypageService.selectLoginLogListCntPaging(foLoginLogDTO));
    	model.put("loginLogList", foMypageService.selectLoginLogListPaging(foLoginLogDTO));
		model.addAttribute("searchInfo", foLoginLogDTO);
    	return "fo/mypage/login-log";
    }

	/**
	 * front 관심 헤드헌터
	 */
    @GetMapping("/fo/user/mypage/attention-headhunter")
    public String attentionHeadhunter(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "fo/mypage/attention-headhunter";
    }

	/**
	 * 스크랩 공고
	 */
	@GetMapping("/fo/mypage/resume-scrap")
	public String resumeScrap(ModelMap model, FoPositionDTO foPositionDTO, HttpServletRequest request, HttpServletResponse response) {
		foPositionDTO.setPositionType("scrap");
		foPositionDTO.setTotalCount(foPositionService.selectScrapPositionListCntForPaging(foPositionDTO));
		model.addAttribute("positionList",foPositionService.selectScrapPositionListPaging(foPositionDTO));
		model.addAttribute("searchInfo", foPositionDTO);
		return "fo/mypage/resume-scrap";
	}

	/**
	 * 최근열람 공고
	 */
	@GetMapping("/fo/mypage/resume-reading-notice")
	public String resumeReadingNotice(ModelMap model, FoPositionDTO foPositionDTO, HttpServletRequest request, HttpServletResponse response) {
		foPositionDTO.setPositionType("positionIdList");
		if(foPositionDTO.getPositionIdList().size() == 0){
			foPositionDTO.setPositionType("positionIdListEmpty");
		}
		foPositionDTO.setTotalCount(foPositionService.selectScrapPositionListCntForPaging(foPositionDTO));
		model.addAttribute("positionList",foPositionService.selectScrapPositionListPaging(foPositionDTO));
		model.addAttribute("searchInfo", foPositionDTO);
		return "fo/mypage/resume-reading-notice";
	}
}