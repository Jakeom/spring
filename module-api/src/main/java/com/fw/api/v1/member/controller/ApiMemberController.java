package com.fw.api.v1.member.controller;

import com.fw.api.system.security.JwtTokenProvider;
import com.fw.api.system.security.UserAuthentication;
import com.fw.api.v1.cert.service.ApiCertService;
import com.fw.api.v1.member.service.ApiMemberService;
import com.fw.api.v1.resume.service.ApiResumeService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.config.annotation.Paging;
import com.fw.core.dto.api.ApiMemberDTO;
import com.fw.core.dto.api.ApiScrapPositionDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * member Controller
 * 
 * @author wsh
 */

@Api("Member API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiMemberController {

	private final ApiMemberService memberService;
	private final BCryptPasswordEncoder pwEncoder;
	private final ApiResumeService resumeService;
	private final ApiCertService certService;
	private final CommonFileService fileService;

	@ApiOperation(value = "로그인", notes = "")
	@PostMapping("/member/login")
	@ResponseBody
	public ResponseEntity<ResponseVO> login(@RequestBody ApiMemberDTO apiMember) {
		ApiMemberDTO userBean = new ApiMemberDTO();
		String token;
		String serviceCode = "";
		ResponseCode code = ResponseCode.SUCCESS;

		String simpleAuthCd = apiMember.getSimpleAuthCd();
		String simpleAuthVal = apiMember.getSimpleAuthVal();
		String autoLogin = apiMember.getAutoLogin();
		String loginId = apiMember.getLoginId();
		String password = apiMember.getPassword();

		if (StringUtils.isNotEmpty(autoLogin)) { // 자동 로그인일경우

			List<ApiMemberDTO> sAuthList = memberService.selectMemeberSimpleAuth(apiMember);
			if (sAuthList == null || sAuthList.size() == 0) {// validate 자동 로그인
				code = ResponseCode.TOKEN_NON_EXISTS;
				return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
			} else {
				String memberId = sAuthList.get(0).getMemberId();
				apiMember.setMemberId(memberId);
			}
		} else if (StringUtils.isNotEmpty(simpleAuthCd)) {// validate 간편로그인
			if (StringUtils.isBlank(simpleAuthVal)) {
				code = ResponseCode.LOGIN_FAIL;
				return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
			}
		} else if (StringUtils.isNotEmpty(loginId)) {// validate 일반 로그인
			if (StringUtils.isBlank(password)) {
				code = ResponseCode.LOGIN_FAIL;
				return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
			}
		} else if (StringUtils.isBlank(loginId)) {
			code = ResponseCode.LOGIN_FAIL;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
		}

		// 회원 데이터 취득
		userBean = memberService.selectLoginInfo(apiMember);
		// NOTI password를 업뎃하지 않겠다. 라는 의미임.
		apiMember.setPassword(null);

		try {
			if (userBean == null) {
				return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.LOGIN_FAIL).build());
			}

			// 일반로그인일 경우 패스워드 확인
			if (StringUtils.isNotEmpty(loginId)) {
				if (!pwEncoder.matches(password, userBean.getPassword())) {
					throw new BadCredentialsException("");
				} else {
					if (userBean.isTemp()) { // 임시회원
						serviceCode = "TEMP_ACCOUNT";
						Map<String, Object> map = new HashMap<>();
						map.put("email",apiMember.getLoginId());
						return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.LOGIN_FAIL).data(map).serviceCode(serviceCode).build());
					}
					if (StringUtils.equals(userBean.getIsStop(),"1")) { // 이용정지
						serviceCode = "STOP_ACCOUNT";
						return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.LOGIN_FAIL).serviceCode(serviceCode).build());
					}
					if (StringUtils.equals(userBean.getApproved(),"0")) { // 헤드헌터 미승인
						serviceCode = "NOT_APPROVE_ACCOUNT";
						return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.LOGIN_FAIL).serviceCode(serviceCode).build());
					}
				}
			}

			// NOTI token 파싱후 기간지난상태라면
			if (StringUtils.isNotEmpty(autoLogin)) {
				LocalDateTime expireTime = certService.getExpirationTime(autoLogin);
				if (LocalDateTime.now().compareTo(expireTime) > 0) {
					// 토큰 만료일경우 토큰 삭제
					apiMember.setSimpleAuthCd("AUTO");
					memberService.updateMemberSimpleAuthDel(apiMember);
					serviceCode = "TOKEN_EXPIRED";
					return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.TOKEN_EXPIRED).serviceCode(serviceCode).build());
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.LOGIN_FAIL).build());
		}

		Authentication authentication = new UserAuthentication(userBean.getId(), null, null);
		token = JwtTokenProvider.generateAccessToken(authentication);

		try {
			// 로그인 실패횟수 초기화
			apiMember.setMemberId(userBean.getId());
			apiMember.setPasswordFailCount("0");
			memberService.updateMember(apiMember);
		} catch (Exception e) {
			log.error("로그인 실패횟수 초기화 실패::", e);
		}

		// 자동로그인 토큰 발급/조회
		ApiMemberDTO autologin = new ApiMemberDTO();
		apiMember.setMemberId(userBean.getId());
		autologin = memberService.selectAutoLoginToken(apiMember);

		if (autologin == null) { // 자동로그인 토큰 존재X 토큰 생성
			userBean.setAutoLogin(certService.requestToken(apiMember));
		} else { // 자동로그인 토큰 존재O 기존 토큰 조회
			userBean.setAutoLogin(autologin.getSimpleAuthVal());
		}

		if (StringUtils.isBlank(apiMember.getPhoneType()) || StringUtils.isBlank(apiMember.getPushToken())) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		} else {
			memberService.updateMember(apiMember);
		}

		// userBean.setPassword(null);
		// 웹토큰 생성 + 회원 데이터 보내기
		return ResponseEntity.status(code.getHttpStatus()).header("Authorization", "Bearer " + token)
				.body(ResponseVO.builder(code, token).data(userBean).build());

	}

	// 로그아웃
	@ApiOperation(value = "로그아웃", notes = "")
	@PostMapping("/member/logout")
	@ResponseBody
	public ResponseEntity<ResponseVO> logout(ApiMemberDTO apiMember) {

		ResponseCode code = ResponseCode.SUCCESS;

		// 웹토큰 삭제
		return ResponseEntity.status(code.getHttpStatus()).header("Authorization", "").body(ResponseVO.builder(code).data(code).build());
	}

	@ApiOperation(value = "회원정보 (수정)", notes = "")
	@PostMapping("/member/update")
	@ResponseBody
	@Paging
	public ResponseEntity<ResponseVO> updateInfo(@RequestBody ApiMemberDTO apiMember) {
		ResponseCode code = ResponseCode.SUCCESS;

		// 회원정보 등록
		memberService.updateMember(apiMember);

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	@ApiOperation(value = "회원정보 (인재검색)", notes = "")
	@PostMapping("/member/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectMemberByResume(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		List<ApiMemberDTO> list = memberService.selectMemberByResume(apiMemberDTO);

		for (ApiMemberDTO amd : list) {
			String fileSeq = amd.getPictureFileId();
			amd.setCommonFileList(fileService.selectFileDetailList(fileSeq));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", memberService.selectMemberByResumeCnt(apiMemberDTO));
		map.put("list", list);

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
	}

	/**
	 * 회원정보등록 (AP 회원가입)
	 */
	@ApiOperation(value = "회원가입", notes = "")
	@PostMapping("/member/signup")
	@ResponseBody
	@Paging
	public ResponseEntity<ResponseVO> signup(@RequestBody ApiMemberDTO apiMember) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "SUCCESS";

		boolean isExistLoginId = memberService.selectDuplicateLoginId(apiMember);

		if (isExistLoginId) {
			serviceCode = "DUPLICATE_ID";
		} else {
			memberService.insertMember(apiMember);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).serviceCode(serviceCode).build());
	}

	/**
	 * 로그인설정(등록)
	 */
	@ApiOperation(value = "로그인설정(등록)", notes = "")
	@PostMapping("/member/insertSimpleAuth")
	@ResponseBody
	@Paging
	public ResponseEntity<ResponseVO> insertSimpleAuth(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		/* 필수값 체크 */
		String memberId = apiMemberDTO.getMemberId(); // 회원 일련번호
		String simpleAuthCd = apiMemberDTO.getSimpleAuthCd(); // 간편 인증 코드
		String simpleAuthVal = apiMemberDTO.getSimpleAuthVal(); // 간편 인증 값

		if (StringUtils.isBlank(memberId) || StringUtils.isBlank(simpleAuthCd) || StringUtils.isBlank(simpleAuthVal)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		memberService.insertMemberSimpleAuth(apiMemberDTO);

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	/**
	 * 로그인설정(조회)
	 */
	@ApiOperation(value = "간편인증 내역 조회", notes = "")
	@PostMapping("/member/simpleAuth")
	@ResponseBody
	@Paging
	public ResponseEntity<ResponseVO> selectMemeberSimpleAuth(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(memberService.selectMemeberSimpleAuth(apiMemberDTO)).build());
	}

	/**
	 * 스크랩된 공고 조회
	 */
	@ApiOperation(value = "스크랩된 공고 조회", notes = "스크랩된 공고 조회")
	@PostMapping("/member/my-scrap")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectMyScrapPosition(@RequestBody ApiScrapPositionDTO apiScrapPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		/* 필수값 체크 */
		String memberId = apiScrapPositionDTO.getMemberId(); // 회원 일련번호

		if (StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", memberService.selectMyScrapPositionCnt(apiScrapPositionDTO));
		map.put("list", memberService.selectMyScrapPosition(apiScrapPositionDTO));

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
	}

	/**
	 * 회원찾기
	 */
	@ApiOperation(value = "회원찾기", notes = "")
	@PostMapping("/member/search")
	@ResponseBody
	@Paging
	public ResponseEntity<ResponseVO> selectSearchMember(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		List<ApiMemberDTO> list = memberService.selectSearchMember(apiMemberDTO);

		// fileList
		for (ApiMemberDTO amd : list) {
			String fileSeq = amd.getProfilePictureFileId();
			amd.setCommonFileList(fileService.selectFileDetailList(fileSeq));
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(list).build());
	}

	/**
	 * 비밀번호 재설정
	 */
	@ApiOperation(value = "비밀번호 재설정", notes = "")
	@PostMapping("/member/pw-reset")
	@ResponseBody
	@Paging
	public ResponseEntity<ResponseVO> updatePwReset(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "SUCCESS";

		// 필수값 체크
		String memberId = apiMemberDTO.getMemberId();
		String recentPassword = apiMemberDTO.getRecentPassword(); // 기존 비밀번호
		String password = apiMemberDTO.getPassword(); // 새 비밀번호

		if (StringUtils.isBlank(memberId) || StringUtils.isBlank(recentPassword) || StringUtils.isBlank(password)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		try {
			ApiMemberDTO member = new ApiMemberDTO();
			member = memberService.selectLoginInfo(apiMemberDTO);

			if (!pwEncoder.matches(apiMemberDTO.getRecentPassword(), member.getPassword())) { // 기존비밀번호 불일치
				serviceCode = "PASSWORD_NOT_MATCH";
			} else { // 기존비밀번호 일치
				memberService.updateMember(apiMemberDTO);
			}
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).serviceCode(serviceCode).build());
	}

	/**
	 * 헤드헌터 정보조회
	 */
	@ApiOperation(value = "헤드헌터 정보조회", notes = "")
	@PostMapping("/member/hh-info")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectHhInfo(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "SUCCESS";

		ApiMemberDTO hhInfo = memberService.selectHhInfo(apiMemberDTO);

		if (hhInfo == null) { // 결과값 null -> AP memberId 조회 시 serviceCode = "NOT_HH"
			serviceCode = "NOT_HH";
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(null).serviceCode(serviceCode).build());
		}

		String fieldString = hhInfo.getFieldCd(); // 전문분야 String

		if (StringUtils.isNotBlank(fieldString)) { // 전문분야 String to Array
			String[] fieldArray = fieldString.split(",");
			hhInfo.setSpecialField(fieldArray);
		}

		// set CommonFileList
		String fileSeq = hhInfo.getProfilePictureFileId();
		hhInfo.setCommonFileList(fileService.selectFileDetailList(fileSeq));

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhInfo).serviceCode(serviceCode).build());
	}

	/**
	 * 헤드헌터 정보수정
	 */
	@ApiOperation(value = "헤드헌터 정보수정", notes = "")
	@PostMapping("/member/update/hh")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateHhInfo(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "SUCCESS";

		ApiMemberDTO hhInfo = memberService.selectHhInfo(apiMemberDTO);

		if (hhInfo == null) { // 결과값 null -> AP memberId 조회 시 serviceCode = "NOT_HH"
			serviceCode = "NOT_HH";
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(null).serviceCode(serviceCode).build());
		}

		// di없이 이름,전화번호 수정불가
		if (StringUtils.isBlank(apiMemberDTO.getDi())
				&& (StringUtils.isNotBlank(apiMemberDTO.getName()) || StringUtils.isNotBlank(apiMemberDTO.getPhone()))) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		try {
			memberService.updateHhInfo(apiMemberDTO);
		} catch (Exception e) {
			serviceCode = "INTERNAL_SERVER_ERROR";
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).serviceCode(serviceCode).build());
	}

	/**
	 * pushToken 갱신
	 */
	@ApiOperation(value = "푸쉬토큰 갱신", notes = "")
	@PostMapping("/member/update/push-token")
	@ResponseBody
	public ResponseEntity<ResponseVO> updatePushToken(@RequestBody ApiMemberDTO apiMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		if(StringUtils.isBlank(apiMemberDTO.getMemberId())) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		try {
			memberService.updatePushInfo(apiMemberDTO);
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}
}