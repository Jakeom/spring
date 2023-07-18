package com.fw.api.v1.cert.controller;

import com.fw.api.v1.cert.service.ApiCertService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.api.ApiMemberDTO;
import com.fw.core.dto.api.ApiTokenDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Cert Controller
 * 
 * @author j.s.ko
 */

@Api("Cert API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiCertController {

	private final ApiCertService certService;

    @ApiOperation(value = "자동로긴용 토큰 발급", notes = "")
	@PostMapping("/cert/requestToken")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectMyResume(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(certService.requestToken(apiMemberDTO)).build());
	}

	/**
	 * 인증번호 생성
	 * useType - 사용처 (공통코드 참조)
	 * mediaType - 인증 방식 (공통코드 참조)
	 */
	@PostMapping("/cert/Identify")
	@ResponseBody
	public ResponseEntity<ResponseVO> sendToken(@RequestBody ApiTokenDTO apiTokenDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			certService.insertToken(apiTokenDTO);
		} catch (Exception e){
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(apiTokenDTO).build());
	}

	/**
	 * 인증번호 검증 및 사용 처리
	 */
	@PostMapping("/cert/checkIdentify")
	@ResponseBody
	public ResponseEntity<ResponseVO> checkToken(@RequestBody ApiTokenDTO apiTokenDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		apiTokenDTO = certService.selectToken(apiTokenDTO);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("serviceCode", apiTokenDTO.getServiceCode());
		resultMap.put("serviceMessage", apiTokenDTO.getServiceMessage());

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(resultMap).build());
	}
}
