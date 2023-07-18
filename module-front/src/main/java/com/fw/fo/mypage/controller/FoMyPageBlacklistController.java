package com.fw.fo.mypage.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoTbBlacklistDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.mypage.service.FoMyPageBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 마이페이지 블랙리스트 Controller
 *
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoMyPageBlacklistController {

    private final FoMyPageBlacklistService foMyPageBlacklistService;

    /**
     * front 메인 블랙리스트
     */
    @GetMapping("/fo/mypage/blacklist")
    public String blacklist(ModelMap model,FoTbBlacklistDTO foTbBlacklistDTO, HttpServletRequest request) {
        foTbBlacklistDTO.setTotalCount(foMyPageBlacklistService.selectBlacklistCntForPaging(foTbBlacklistDTO));
        model.addAttribute("blacklist", foMyPageBlacklistService.selectBlacklist(foTbBlacklistDTO));
        model.addAttribute("searchInfo", foTbBlacklistDTO);
        return "fo/mypage/blacklist";
    }

    /**
     * 블랙리스트 확인/해제
     */
    @PostMapping("/fo/user/mypage/updateBlacklist")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateBlacklist(@RequestBody FoTbBlacklistDTO foTbBlacklistDTO) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            foMyPageBlacklistService.updateBlacklist(foTbBlacklistDTO);
        } catch (Exception e){
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            log.error("error", e);
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
    }
}