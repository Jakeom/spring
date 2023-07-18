package com.fw.api.v1.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fw.api.v1.university.service.ApiUniversityService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.api.ApiResumeAcademicBackgroundDTO;
import com.fw.core.vo.ResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * University Controller
 * 
 * @author wsh
 */

@Api("University API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiUniversityController {

	private final ApiUniversityService universityService;

	/**
	 * 학력 셀렉터 리스트 조회
	 */
	@ApiOperation(value = "학력 셀렉터 리스트 조회", notes = "")
	@PostMapping("/university/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectUniversityList(@RequestBody ApiResumeAcademicBackgroundDTO apiResumeAcademicBackgroundDTO) {
		apiResumeAcademicBackgroundDTO.setTotalCount(200);
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(universityService.selectUniversityList(apiResumeAcademicBackgroundDTO)).build());
	}
}