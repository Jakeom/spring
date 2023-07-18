package com.fw.api.v1.community.controller;

import com.fw.api.v1.community.service.ApiCommunityService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.api.ApiCommonCdDTO;
import com.fw.core.dto.api.ApiCommunityDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * community Controller
 * 
 * @author WSH
 * @since 2022.11
 */

@Api("Community API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiCommunityController {

	private final ApiCommunityService apiCommunityService;

	private final CommonFileService fileService;

	/**
	 * 커뮤니티 카테고리 목록 조회
	 */
	@ApiOperation(value = "커뮤니티 카테고리 목록 조회", notes = "커뮤니티 목록 조회")
	@PostMapping("/community/category/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectCommunityCategoryList(@RequestBody ApiCommonCdDTO apiCommonCdDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(apiCommunityService.selectCommunityCategoryList(apiCommonCdDTO)).build());
	}

	/**
	 * 게시글 리스트 조회
	 */
	@ApiOperation(value = "게시글 리스트 조회", notes = "게시글 리스트 조회")
	@PostMapping("/community/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectCommunityContentList(@RequestBody ApiCommunityDTO apiCommunityDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		List<ApiCommunityDTO> list = apiCommunityService.selectCommunityContentList(apiCommunityDTO);

		// fileList
		for(ApiCommunityDTO acd : list) {
			String fileSeq = acd.getFileSeq();
			acd.setCommonFileList(fileService.selectFileDetailList(fileSeq));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", apiCommunityService.selectCommunityContentCnt(apiCommunityDTO));
		map.put("list", list);

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
	}
}