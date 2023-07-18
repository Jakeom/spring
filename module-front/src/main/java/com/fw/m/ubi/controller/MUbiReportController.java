package com.fw.m.ubi.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.m.UbiDataDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.m.ubi.service.UbiService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MUbiReportController {

    private final UbiService ubiService;

    /**
     * 이력서 조회(Ubireport)
     */
    @GetMapping("/m/ubi-report/resume")
    @ResponseBody
    public ResponseEntity<ResponseVO> resume(UbiDataDTO ubiDataDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        ubiDataDTO.setResumeId(ubiDataDTO.getId());
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(ubiService.selectResumeInfo(ubiDataDTO)).build());
    }

    /**
     * 취업활동증명서 발급 (Ubireport) TODO : 형식 제대로 알고 고쳐야 함
     */
    @PostMapping("/m/ubi-report/employment-certificate")
    @ResponseBody
    public ResponseEntity<ResponseVO> employmentCertificate(@RequestBody UbiDataDTO ubiDataDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        List<UbiDataDTO> list = ubiService.selectEmploymentCertificate(ubiDataDTO);
        Map<String, Object> map = new HashMap<>();
        int idx = 0;
        for(UbiDataDTO u : list){
            if(idx == 0){
                Map<String, Object> m = new HashMap<>();
                map.put("applicant", u);
                map.put("resume", u);
                m.put("company", list);
                map.put("position", m);
            }
            idx++;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
    }

}
