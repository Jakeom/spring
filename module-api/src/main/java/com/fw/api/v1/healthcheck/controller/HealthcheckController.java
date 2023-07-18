package com.fw.api.v1.healthcheck.controller;

import com.fw.api.system.exception.ApiBizException;
import com.fw.core.code.ResponseCode;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api("Health Check API")
public class HealthcheckController {

    @ApiOperation(value = "Health Check", notes = "Health Check")
    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<ResponseVO> healthCheck() {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}