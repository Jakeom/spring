package com.fw.api.v1.resume.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fw.api.v1.resume.service.ApiResumeService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.api.ApiResumeDTO;
import com.fw.core.vo.ResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * resume Controller
 * 
 * @author wsh
 */

@Api("Resume API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiResumeController {

	private final ApiResumeService resumeService;

	/**
	 * 이력서 리스트 조회
	 */
	@ApiOperation(value = "이력서 리스트 조회", notes = "이력서 리스트 조회")
	@PostMapping("/resume/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectResumeList(@RequestBody ApiResumeDTO apiResumeDTO) {

		ResponseCode code = ResponseCode.SUCCESS;

		String memberId = apiResumeDTO.getMemberId();
		if(StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(resumeService.selectResumeList(apiResumeDTO)).build());
	}
}