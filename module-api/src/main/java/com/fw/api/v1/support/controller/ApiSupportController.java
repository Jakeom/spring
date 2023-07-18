package com.fw.api.v1.support.controller;

import com.fw.api.v1.support.service.ApiSupportService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.api.ApiBannerDTO;
import com.fw.core.dto.api.ApiCompanyDTO;
import com.fw.core.dto.api.ApiFaqDTO;
import com.fw.core.dto.api.ApiNoticeDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * support Controller
 * 
 * @author wsh
 * @since 2022.11
 */

@Api("Support API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiSupportController {

	private final ApiSupportService apiSupportService;

	private final CommonFileService fileService;

	/**
	 * 공지사항 목록조회
	 */
	@ApiOperation(value = "공지사항 목록조회", notes = "")
	@PostMapping("/support/notice/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectNoticeList(@RequestBody ApiNoticeDTO apiNoticeDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		List<ApiNoticeDTO> list = apiSupportService.selectNoticeList(apiNoticeDTO);

		// fileList
		for(ApiNoticeDTO and : list) {
			String fileSeq = and.getFileSeq();
			and.setCommonFileList(fileService.selectFileDetailList(fileSeq));
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(list).build());
	}

	/**
	 * FAQ 목록조회
	 */
	@ApiOperation(value = "FAQ 목록조회", notes = "")
	@PostMapping("/support/faq/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectFaqList(@RequestBody ApiFaqDTO apiFaqDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		List<ApiFaqDTO> list = apiSupportService.selectFaqList(apiFaqDTO);

		// fileList
		for(ApiFaqDTO afd : list) {
			String fileSeq = afd.getFileSeq();
			afd.setCommonFileList(fileService.selectFileDetailList(fileSeq));
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(list).build());
	}

	/**
	 * 배너 리스트 조회
	 */
	@ApiOperation(value = "배너 리스트 조회", notes = "배너 리스트 조회")
	@PostMapping("support/banner/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectBannerList(@RequestBody ApiBannerDTO apiBannerDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		List<ApiBannerDTO> list = apiSupportService.selectBannerList(apiBannerDTO);

		// fileList
		for(ApiBannerDTO abd : list) {
			String fileSeq = abd.getFileSeq();
			abd.setCommonFileList(fileService.selectFileDetailList(fileSeq));
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(list).build());
	}

	@ApiOperation(value = "회사정보 조회", notes = "회사정보 조회")
	@PostMapping("/company/detail")
	@ResponseBody
	public ResponseEntity<ResponseVO> companyInfo(@RequestBody ApiCompanyDTO apiCompanyDTO) {

		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(apiSupportService.selectCompanyDetail(apiCompanyDTO)).build());
	}
}