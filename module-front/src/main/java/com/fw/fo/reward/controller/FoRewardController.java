package com.fw.fo.reward.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.*;
import com.fw.core.dto.fo.headhunter.FoSearchHhDTO;
import com.fw.core.dto.fo.resume.FoHhReadingHistoryDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.headhunter.service.FoHeadhunterService;
import com.fw.fo.position.service.FoPositionService;
import com.fw.fo.resume.service.FoResumeService;
import com.fw.fo.reward.service.FoRewardService;
import com.fw.fo.system.config.security.FoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 로그인 Controller
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoRewardController {

    private final FoRewardService foRewardService;
    private final FoLoginService foLoginService;
    private final CommonFileService commonFileService;
    /**
     * 리워드몰
     */
    @GetMapping("/fo/mypage/reward")
    public String reward(FoRewardDTO foRewardDTO , ModelMap model, HttpServletRequest request) {
        foRewardDTO.setTotalCount(foRewardService.selectRewardListCntForPaging(foRewardDTO));
        model.addAttribute("rewardList",foRewardService.selectRewardList(foRewardDTO));
        model.addAttribute("searchInfo",foRewardDTO);
        return "fo/mypage/reward";
    }

    /**
     * 리워드몰 상품상세
     */
    @GetMapping("/fo/reward/detail")
    public String rewardInfo(FoRewardDTO foRewardDTO , ModelMap model, HttpServletRequest request) {
        model.addAttribute("rewardInfo",foRewardService.selectRewardDetail(foRewardDTO));
        return "fo/mypage/reward-detail";
    }

    /**
     * 리워드 적립/상용 내역
     */
    @GetMapping("/fo/mypage/reward-point")
    public String rewardPoint(FoRewardDTO foRewardDTO , ModelMap model, HttpServletRequest request) {
        if(StringUtils.equals("all",foRewardDTO.getSelectPeriod())){
            foRewardDTO.setSelectPeriod(null);
        }
        foRewardDTO.setTotalCount(foRewardService.selectRewardHistoryListCntForPaging(foRewardDTO));
        model.addAttribute("rewardHistoryList",foRewardService.selectRewardHistoryListPaging(foRewardDTO));
        model.addAttribute("searchInfo",foRewardDTO);
        return "fo/mypage/reward-point";
    }

    /**
     * 상품구매
     */
    @PostMapping("/fo/mypage/reward-purchase")
    @ResponseBody
    public ResponseEntity<ResponseVO> rewardPurchase(FoRewardDTO foRewardDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foRewardDTO.setGoodsCd("PURCHASE_GIFT");
            foRewardDTO.setReasonCd("REWARD");
            foRewardService.updateReward(foRewardDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, foSessionDTO.getUsername()));
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * @description 새로운 인증 생성
     * @param currentAuth 현재 auth 정보
     * @param username	현재 사용자 Id
     * @return Authentication
     * @author Armton
     */
    protected Authentication createNewAuthentication(Authentication currentAuth, String userId) {
        FoSessionDTO foSessionDTO = (FoSessionDTO) foLoginService.loadUserByUsername(userId);
        List<FileDTO> fileList = commonFileService.selectFileDetailList(foSessionDTO.getProfilePictureFileId());
        if(fileList != null) {
            foSessionDTO.setProfileUrl(fileList.get(0).getUrl());
        }
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(foSessionDTO, currentAuth.getCredentials(), foSessionDTO.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }
}