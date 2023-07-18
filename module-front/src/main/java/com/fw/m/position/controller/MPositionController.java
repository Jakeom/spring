package com.fw.m.position.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.position.service.FoPositionService;
import com.fw.hh.position.service.HhPositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MPositionController {

    private final FoPositionService foPositionService;
    private final HhPositionService hhPositionService;

    /**
     * 포지션 상세 WebView
     */
    @GetMapping("/m/position/detail")
    public String positionDetail(ModelMap model, FoPositionDTO foPositionDTO) {
        model.addAttribute("positionDetail",foPositionService.selectPositionInfo(foPositionDTO));
        return "m/position/detail";
    }

    /**
     * 포지션 존재유무 by 회사
     */
    @PostMapping("/m/position/existByCompany")
    public ResponseEntity<ResponseVO> positionExistByCompany(@RequestBody FoPositionDTO foPositionDTO) {
            ResponseCode code = ResponseCode.SUCCESS;
            String serviceCode = "";
            try {
                log.info("companySeq >>> {}",foPositionDTO.getCompanySeq());
                boolean isExist = foPositionService.selectPositionExistByCompany(foPositionDTO);
                if(isExist) { // 존재O
                    serviceCode = "EXISTS";
                } else { // 존재X
                    serviceCode = "NOT_EXISTS";
                }
            } catch (Exception e) {
                log.error("error", e);
                code = ResponseCode.INTERNAL_SERVER_ERROR;
            }

            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
    }
}
