package com.fw.m.community.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.dto.fo.FoTbCommunityCommentDTO;
import com.fw.core.dto.fo.FoTbCommunityDTO;
import com.fw.core.util.CookieUtil;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.position.service.FoPositionService;
import com.fw.m.community.service.MCommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MCommunityController {

    private final MCommunityService mCommunityService;
    private final FoPositionService foPositionService;
    private final CommonService commonService;
    /**
     * 커뮤니티 상세
     */
    @GetMapping("/m/community/detail")
    public String communityDetail(ModelMap model, FoTbCommunityDTO foTbCommunityDTO, FoTbCommunityCommentDTO foTbCommunityCommentDTO, HttpServletRequest request, HttpServletResponse response) {
        if(!CookieUtil.mergeCookie(request, response, "community", foTbCommunityDTO.getCommunitySeq(), "/", 60 * 60 * 24)) {
            mCommunityService.updateCommunityHit(foTbCommunityDTO); // 조회수 증가
        }

        FoMemberDTO foMemberDTO = new FoMemberDTO();
        foMemberDTO.setMemberId(foTbCommunityDTO.getMemberId());
        FoMemberDTO memberInfo = commonService.selectMemberInfoByMemberId(foMemberDTO);

        FoTbCommunityDTO communityInfo = mCommunityService.selectCommunityDetail(foTbCommunityDTO); // 커뮤니티 상세 조회

        if(StringUtils.equals(communityInfo.getCommunityType(),"COMPANY_CURIOUS")) {
            if (StringUtils.equals(communityInfo.getCreateSeq(), foTbCommunityDTO.getMemberId())) {
                communityInfo.setMyBoardFlag("Y");
            } else {
                communityInfo.setMyBoardFlag("N");
            }
        }

        log.info("myBoardFlag >>>> {}",communityInfo.getMyBoardFlag());
        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("memberId", foTbCommunityDTO.getMemberId());
        model.addAttribute("detail", communityInfo);
        model.addAttribute("detailComment", mCommunityService.selectCommunityCommentList(foTbCommunityCommentDTO));

        return "m/community/detail";
    }

    /**
     * 커뮤니티 등록
     */
    @GetMapping("/m/community/registration")
    String communityRegistration(ModelMap model, FoTbCommunityDTO foTbCommunityDTO, FoPositionDTO foPositionDTO) {

        /* 입사지원현황 -> 합격후기 탭 들어온 경우 */
        if(StringUtils.isNotBlank(foPositionDTO.getPositionId())) { // 합격한 채용공고 company정보 조회
            foPositionDTO.setId(foPositionDTO.getPositionId());
            FoPositionDTO company = foPositionService.selectPositionInfo(foPositionDTO);
            if(company != null) {
                foTbCommunityDTO.setCompanySeq(company.getCompanySeq());
            }
        }
        model.addAttribute("parameter", foTbCommunityDTO);
        model.addAttribute("category", mCommunityService.selectCommunityCategory(foTbCommunityDTO));
        model.addAttribute("position", foPositionService.selectMyPassPositionNotReview(foPositionDTO));

        return "m/community/registration";
    }

    /**
     * 커뮤니티 글 등록
     */
    @PostMapping("/m/community/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertCommunity(FoTbCommunityDTO foTbCommunityDTO) {
        ResponseCode code = ResponseCode.SUCCESS;

        if(StringUtils.isBlank(foTbCommunityDTO.getCompanySeq())){
            foTbCommunityDTO.setCompanySeq(null);
        }

        try {
;            mCommunityService.insertCommunity(foTbCommunityDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }

    /**
     * 커뮤니티 댓글
     */
    @PostMapping("/m/community/insertComment")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertCommunityComment(@RequestBody FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            mCommunityService.insertCommunityComment(foTbCommunityCommentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }

    /**
     * 글 좋아요
     */
    @PostMapping("/m/user/community/recommendupdate")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateRecommend(@RequestBody FoTbCommunityDTO foTbCommunityDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            mCommunityService.updateRecommend(foTbCommunityDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }

    /**
     * 글 좋아요
     */
    @PostMapping("/m/community/insertRecommendCommunity")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertRecommendCommunity(@RequestBody FoTbCommunityDTO foTbCommunityDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            mCommunityService.insertCommunityRecommend(foTbCommunityDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }
}
