package com.fw.fo.community.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.*;
import com.fw.core.util.CookieUtil;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.community.service.FoCommunityService;
import com.fw.fo.position.service.FoPositionService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoCommunityController {

    private final FoCommunityService foCommunityService;
    private final FoPositionService foPositionService;
    private final CommonService commonService;
    private final FoRewardService foRewardService;
    private final FoLoginService foLoginService;
    private final CommonFileService commonFileService;

    /**
     * AP 커뮤니티 메인
     */
    @GetMapping("/fo/community/community-list")
    public String community(ModelMap model, FoTbCommunityDTO foTbCommunityDTO) throws ParseException {

        model.addAttribute("searchInfo", foTbCommunityDTO);
        foTbCommunityDTO.setSortColumn("recommend");
        model.addAttribute("bestList", foCommunityService.selectCommunityList(foTbCommunityDTO));
        foTbCommunityDTO.setSortColumn("create_dt");
        model.addAttribute("recentList", foCommunityService.selectCommunityList(foTbCommunityDTO));

        return "fo/community/community-list";
    }

    /**
     * AP 커뮤니티
     */
    @GetMapping("/fo/community/board")
    public String board(ModelMap model, FoTbCommunityDTO foTbCommunityDTO, FoPositionDTO foPositionDTO) throws ParseException {
        CommonDTO commonDTO = new CommonDTO();
        commonDTO.setGroupCd("COMMUNITY_TYPE");
        commonDTO.setCd(foTbCommunityDTO.getCommunityType());
        commonDTO.setOrder("1");
        foTbCommunityDTO.setCommunityTypeNm(commonService.selectCommonCodeNm(commonDTO));
        foPositionDTO.setMemberId(foTbCommunityDTO.getFrontSession().getId());

        model.addAttribute("searchInfo", foTbCommunityDTO);
        model.addAttribute("communityList", foCommunityService.selectCommunityList(foTbCommunityDTO));
        model.addAttribute("position", foPositionService.selectMyPassPosition(foPositionDTO));
        return "fo/community/board";
    }

    /**
     * AP 커뮤니티 상세
     */
    @GetMapping("/fo/community/board/detail")
    public String boardDetail(ModelMap model, FoTbCommunityDTO foTbCommunityDTO, HttpServletRequest request, HttpServletResponse response, FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        if(!CookieUtil.mergeCookie(request, response, "community", foTbCommunityDTO.getCommunitySeq(), "/", 60 * 60 * 24)) {
            foCommunityService.updateCommunityHit(foTbCommunityDTO);
        }

        FoTbCommunityDTO communityInfo = foCommunityService.selectCommunityDetail(foTbCommunityDTO);
        if(StringUtils.equals(foTbCommunityDTO.getCommunityType(),"COMPANY_CURIOUS")) {
            if(StringUtils.equals(foTbCommunityDTO.getFrontSession().getId(),communityInfo.getCreateSeq())) {
                communityInfo.setMyBoardFlag("Y");
            } else {
                communityInfo.setMyBoardFlag("N");
            }
        }

        model.addAttribute("searchInfo", foTbCommunityDTO);
        model.addAttribute("detail", communityInfo);
        model.addAttribute("detailComment", foCommunityService.selectCommunityCommentList(foTbCommunityCommentDTO));
        return "fo/community/board-detail";
    }

    /**
     * 자유게시판 글 쓰기
     * @author dongbeom
     */
    @PostMapping("/fo/community/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertCommunityWorry(FoTbCommunityDTO foTbCommunityDTO, FoRewardDTO foRewardDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        if(StringUtils.isBlank(foTbCommunityDTO.getCompanySeq())){
            foTbCommunityDTO.setCompanySeq(null);
        }
        try {
            foCommunityService.insertCommunity(foTbCommunityDTO);
            if(foTbCommunityDTO.getCommunityType().equals("PASS_REVIEW")){
                if(foRewardService.selectReward(foRewardDTO)==0){
                    foRewardService.insertReward(foRewardDTO);
                }
                foRewardDTO.setIncrease("3000");
                foRewardDTO.setGoodsCd("REVIEW_GIVE");
                foRewardDTO.setReasonCd("REWARD");
                foRewardService.updateReward(foRewardDTO);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
                SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, foSessionDTO.getUsername()));
            }
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }

    /**
     * 자유게시판 댓글 쓰기
     * @author dongbeom
     */
    @PostMapping("/fo/community/insertComment")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertCommunityComment(@RequestBody FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foCommunityService.insertCommunityComment(foTbCommunityCommentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }

    /**
     * 글 좋아요
     * @author dongbeom
     */
    @PostMapping("/front/user/community/recommendupdate")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateRecommend(@RequestBody FoTbCommunityDTO foTbCommunityDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foCommunityService.updateRecommend(foTbCommunityDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }

    /**
     * 글 좋아요
     * @author dongbeom
     */
    @PostMapping("/fo/community/insertRecommendCommunity")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertRecommendCommunity(@RequestBody FoTbCommunityDTO foTbCommunityDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foCommunityService.insertCommunityRecommend(foTbCommunityDTO);
        } catch (Exception e) {
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