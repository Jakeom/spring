package com.fw.api.v1.common.controller;

import com.fw.api.v1.common.service.ApiCommonService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.api.ApiCommonCdDTO;
import com.fw.core.dto.api.ApiInfoDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@Api("Common API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiCommonController {

	private final ApiCommonService apiCommonService;
	private final CommonFileService fileService;
	/**
	 * 약관 조회
	 */
	@ApiOperation(value = "약관 조회", notes = "")
	@PostMapping("/common/info")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectCommonInfo(@RequestBody ApiInfoDTO apiInfoDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		List<ApiInfoDTO> list = apiCommonService.selectCommonInfo(apiInfoDTO);

		for(ApiInfoDTO aid : list) {
			String fileSeq = aid.getFileSeq();
			aid.setCommonFileList(fileService.selectFileDetailList(fileSeq));
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(list).build());
	}

	/**
	 * 공통코드 리스트 조회
	 */
	@ApiOperation(value = "공통코드 리스트 조회", notes = "")
	@PostMapping("/common/common-cd")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectCommonCd(@RequestBody ApiCommonCdDTO apiCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(apiCommonService.selectCommonCd(apiCommonCdDTO)).build());
	}
}
