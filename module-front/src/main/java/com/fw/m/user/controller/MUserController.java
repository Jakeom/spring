package com.fw.m.user.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.m.user.service.MUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class MUserController {

	private final MUserService mUserService;

	/**
	 * 동의서 및 이력서 등록하기
	 */
	@GetMapping("/m/user/position")
	public String position(ModelMap model, FoPositionDTO foPositionDTO) {
		model.addAttribute("searchInfo", foPositionDTO);
		return "/m/user/position";
	}

	/**
	 * 동의서 및 이력서 업로드
	 */
	@PostMapping(value = "/m/user/position", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public ResponseEntity<ResponseVO> updatePosition(@RequestPart(value = "jsonData") FoPositionDTO foPositionDTO,
													 @RequestPart(value = "agreeFiles", required = false) MultipartFile[] agreeFiles,
													 @RequestPart(value = "resumeFiles", required = false) MultipartFile[] resumeFiles) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			foPositionDTO.setAgreeFiles(agreeFiles);
			foPositionDTO.setResumeFiles(resumeFiles);
			mUserService.updateResume(foPositionDTO);
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

}
