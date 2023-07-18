package com.fw.api.v1.headhunter.controller;

import com.fw.api.v1.headhunter.service.ApiHeadHunterService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.api.ApiMemberDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * headhunter Controller
 * 
 * @author wsh
 */

@Api("HeadHunter API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiHeadHunterController {

	private final ApiHeadHunterService headHunterService;

	private final CommonFileService fileService;

	/**
	 * 내 인재 리스트 조회
	 */
	@ApiOperation(value = "내 인재 리스트 조회", notes = "")
	@PostMapping("/headhunter/ap-list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectApList(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		/** 필수값 체크 */
		String memberId = apiMemberDTO.getMemberId();
		if (StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("totalCount",headHunterService.selectApListCnt(apiMemberDTO));
		map.put("list",headHunterService.selectApList(apiMemberDTO));

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
	}
}