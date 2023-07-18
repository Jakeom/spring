package com.fw.hh.community.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.fo.FoTbCommunityCommentDTO;
import com.fw.core.dto.fo.FoTbCommunityDTO;
import com.fw.core.util.CookieUtil;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.community.service.HhCommunityService;
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
import java.text.ParseException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhCommunityController {

    private final HhCommunityService hhCommunityService;
    private final CommonService commonService;

    /**
     * headhunter 커뮤니티 메인
     */
    @GetMapping("/hh/community/community-list")
    public String community(ModelMap model, FoTbCommunityDTO foTbCommunityDTO) throws ParseException {

            CommonDTO commonDTO = new CommonDTO();
            commonDTO.setGroupCd("COMMUNITY_TYPE");
            commonDTO.setCd(foTbCommunityDTO.getCommunityType());
            commonDTO.setOrder("1");
            foTbCommunityDTO.setCommunityTypeNm(commonService.selectCommonCodeNm(commonDTO));

            model.addAttribute("searchInfo", foTbCommunityDTO);
            foTbCommunityDTO.setSortColumn("recommend");
            model.addAttribute("bestList", hhCommunityService.selectCommunityList(foTbCommunityDTO));
            foTbCommunityDTO.setSortColumn("create_dt");
            model.addAttribute("recentList", hhCommunityService.selectCommunityList(foTbCommunityDTO));

        return "hh/community/community-list";
    }

    /**
     * headhunter 커뮤니티
     */
    @GetMapping("/hh/community/board")
    public String board(ModelMap model, FoTbCommunityDTO foTbCommunityDTO) throws ParseException {

        CommonDTO commonDTO = new CommonDTO();
        commonDTO.setGroupCd("COMMUNITY_TYPE");
        commonDTO.setCd(foTbCommunityDTO.getCommunityType());
        commonDTO.setOrder("1");
        foTbCommunityDTO.setCommunityTypeNm(commonService.selectCommonCodeNm(commonDTO));

        model.addAttribute("searchInfo", foTbCommunityDTO);
        model.addAttribute("communityList", hhCommunityService.selectCommunityList(foTbCommunityDTO));
        return "hh/community/board";
    }

    /**
     * headhunter 커뮤니티 상세
     */
    @GetMapping("/hh/community/board/detail")
    public String boardDetail(ModelMap model, FoTbCommunityDTO foTbCommunityDTO, HttpServletRequest request, HttpServletResponse response, FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        if(!CookieUtil.mergeCookie(request, response, "community", foTbCommunityDTO.getCommunitySeq(), "/", 60 * 60 * 24)) {
            hhCommunityService.updateCommunityHit(foTbCommunityDTO);
        }

        model.addAttribute("searchInfo", foTbCommunityDTO);
        model.addAttribute("detail", hhCommunityService.selectCommunityDetail(foTbCommunityDTO));
        model.addAttribute("detailComment", hhCommunityService.selectCommunityCommentList(foTbCommunityCommentDTO));
        return "hh/community/board-detail";
    }

    /**
     * 커뮤니티 글 등록
     */
    @PostMapping("/hh/community/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertCommunityWorry(FoTbCommunityDTO foTbCommunityDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        if(StringUtils.isBlank(foTbCommunityDTO.getCompanySeq())){
            foTbCommunityDTO.setCompanySeq(null);
        }
        try {
            hhCommunityService.insertCommunity(foTbCommunityDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());

    }

    /**
     * 커뮤니티 댓글등록
     */
    @PostMapping("/hh/community/insertComment")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertCommunityComment(@RequestBody FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhCommunityService.insertCommunityComment(foTbCommunityCommentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 커뮤니티 좋아요
     */
    @PostMapping("/hh/community/insertRecommendCommunity")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertRecommendCommunity(@RequestBody FoTbCommunityDTO foTbCommunityDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhCommunityService.insertCommunityRecommend(foTbCommunityDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}
