package com.fw.api.v1.file.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.CommonDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author jsko
 */
@Api("File API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiFileController {

	private final CommonFileService commonFileService;

	@ApiOperation(value = "파일 업로드", notes = "")
	@PostMapping(value = "/file/upload")
	@ResponseBody
	public ResponseEntity<ResponseVO> upload(CommonDTO commonDTO) throws Exception {
		ResponseCode code = ResponseCode.SUCCESS;
		Integer fileSeq = commonFileService.fileUpload(commonDTO.getFiles(), "MOBILE");
		HashMap<String, Object> map = new HashMap<>();
		map.put("fileSeq", fileSeq);
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
	}

	@ApiOperation(value = "파일 다운로드", notes = "")
	@GetMapping("/file/download/{fileSeq}")
	@ResponseBody
	public ResponseEntity<ResponseVO> download(@PathVariable String fileSeq) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonFileService.selectFileDetailList(fileSeq)).build());
	}

}