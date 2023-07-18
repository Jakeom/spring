package com.fw.fo.token.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoTokenDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.token.service.FoTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoTokenController {

    private final FoTokenService foTokenService;

    /**
     * 인증번호 생성
     * useType - 사용처 (공통코드 참조)
     * mediaType - 인증 방식 (공통코드 참조)
     */
    @PostMapping("/fo/token/send/{useType}/{mediaType}")
    @ResponseBody
    public ResponseEntity<ResponseVO> sendToken(FoTokenDTO foTokenDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foTokenService.insertToken(foTokenDTO);
        } catch (Exception e){
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            log.error("error", e);
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foTokenDTO).build());
    }

    /**
     * 인증번호 검증 및 사용 처리
     */
    @GetMapping("/fo/token/check")
    @ResponseBody
    public ResponseEntity<ResponseVO> checkToken(FoTokenDTO foTokenDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        foTokenDTO = foTokenService.selectToken(foTokenDTO);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("serviceCode", foTokenDTO.getServiceCode());
        resultMap.put("serviceMessage", foTokenDTO.getServiceMessage());

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(resultMap).build());
    }

}