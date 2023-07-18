package com.fw.hh.mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.hh.HhMyProfileDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.mypage.service.FoMyPageChangePasswordService;
import com.fw.hh.mypage.service.HhMyProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 Controller
 *
 * @author wsh
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HhMyProfileController {

	private final HhMyProfileService hhMyProfileService;
	private final FoMyPageChangePasswordService foMyPageChangePasswordService;
	private final BCryptPasswordEncoder pwEncoder;
	@Value("${cert.server}")
	private String CERT_SERVER;

	@Value("${cert.callback-url}")
	private String CERT_CALLBACK_URL;

	/**
	 * #내 정보 관리 - 프로필설정
	 */
	@GetMapping("/hh/mypage/profile")
	public String MypageProfile(ModelMap model, HhMyProfileDTO hhMyProfileDTO) {
		hhMyProfileDTO.setMemberId(hhMyProfileDTO.getFrontSession().getId());
		model.addAttribute("info", hhMyProfileDTO);
		model.addAttribute("profileInfo", hhMyProfileService.selectMyProfileInfo(hhMyProfileDTO));
		model.addAttribute("fieldList", hhMyProfileService.selectHhPositionFieldList(hhMyProfileDTO));
		return "hh/mypage/profile";
	}

	/**
	 * 헤드헌터 정보 조회
	 */
	@GetMapping("/hh/mypage/profile-info")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectProfileInfo(HhMyProfileDTO hhMyProfileDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("profileInfo", hhMyProfileService.selectMyProfileInfo(hhMyProfileDTO));
		dataMap.put("fieldInfo", hhMyProfileService.selectHhPositionFieldList(hhMyProfileDTO));
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(dataMap).build());
	}

	/**
	 * #내 정보 관리 - 프로필설정 - 전문분야 조회
	 */
	@GetMapping("/hh/mypage/profile/hh-position-field")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectHhPositionFieldList(HhMyProfileDTO hhMyProfileDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhMyProfileService.selectHhPositionFieldList(hhMyProfileDTO)).build());
	}

	/**
	 * #내 정보 관리 - 프로필설정 - 수정
	 */
	@PostMapping("/hh/mypage/profile-setting")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateProfileSetting(HhMyProfileDTO hhMyProfileDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyProfileService.updateProfileSetting(hhMyProfileDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	/**
	 * #내 정보 관리 - 프로필설정 - 기본정보/이메일서명
	 */
	@GetMapping("/hh/mypage/basic-information")
	public String MypageBasicInformation(ModelMap model, HhMyProfileDTO hhMyProfileDTO) {
		model.addAttribute("certServer", CERT_SERVER); // 본인인증 URL
		model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL); // 본인인증 CALLBACK URL
		model.addAttribute("basicInfo", hhMyProfileService.selectMyProfileInfo(hhMyProfileDTO));
		return "hh/mypage/basic-information";
	}

	/**
	 * #내 정보 관리 - 기본정보/이메일서명 - 비밀번호 변경
	 */
	@PostMapping("/hh/mypage/change-password")
	@ResponseBody
	public ResponseEntity<ResponseVO> changePassword(@RequestBody FoMemberDTO foMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		String password = foMemberDTO.getPassword(); // 기존 비밀번호

		try {
			FoMemberDTO member = foMyPageChangePasswordService.selectMemberPassword(foMemberDTO);
			if (!pwEncoder.matches(password, member.getPassword())) {
				throw new BadCredentialsException("");
			}
			foMyPageChangePasswordService.updateChangePassword(foMemberDTO);
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	/**
	 * #내 정보 관리 - 기본정보/이메일서명 - 이메일서명 수정
	 */
	@PostMapping("/hh/mypage/update-signature")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateSignature(@RequestBody HhMyProfileDTO hhMyProfileDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		try {
			hhMyProfileService.updateSignature(hhMyProfileDTO);
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	/**
	 * 핸드폰번호 변경
	 */
	@PostMapping("/hh/mypage/change-phone")
	@ResponseBody
	public ResponseEntity<ResponseVO> changePhone(@RequestBody HhMyProfileDTO hhMyProfileDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		try {
			hhMyProfileService.updateMember(hhMyProfileDTO);
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	/**
	 * front HH 마이페이지-이용 내역
	 */
	@GetMapping("/hh/mypage/usage-history")
	public String MypageUsageHistory(ModelMap model, HhMyProfileDTO hhMyProfileDTO) {
		model.addAttribute("searchInfo", hhMyProfileDTO);
		model.addAttribute("logList", hhMyProfileService.selectMyLogList(hhMyProfileDTO));
		hhMyProfileDTO.setTotalCount(hhMyProfileService.selectMyLogListCnt(hhMyProfileDTO));
		return "hh/mypage/usage-history";
	}
}