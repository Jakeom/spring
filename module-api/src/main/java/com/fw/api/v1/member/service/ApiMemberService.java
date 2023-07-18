package com.fw.api.v1.member.service;

import com.fw.core.dto.api.ApiMemberDTO;
import com.fw.core.dto.api.ApiScrapPositionDTO;
import com.fw.core.mapper.db1.api.ApiMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author wsh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMemberService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final ApiMemberMapper apiMemberMapper;

	public void updateMember(ApiMemberDTO apiMemberDTO) {

		String pw = apiMemberDTO.getPassword();

		// 비밀번호 암호화
		if (pw != null && pw.length() > 0) {
			apiMemberDTO.setPassword(bCryptPasswordEncoder.encode(apiMemberDTO.getPassword()));
		}

		if (apiMemberDTO.getResumeRestricted() != null || apiMemberDTO.getFindingJob() != null || apiMemberDTO.getIsPrivateAgreement() != null
				|| apiMemberDTO.getContactExceptHoliday() != null || apiMemberDTO.getContactableTime() != null) {
			apiMemberMapper.updateApplicant(apiMemberDTO);
		}

		// member update
		String agreeMarketing = apiMemberDTO.getAgreeMarketing();
		if (Objects.equals(agreeMarketing,"Y")) {
			apiMemberDTO.setAgreeMarketing("1");
		} else if (Objects.equals(agreeMarketing, "N")) {
			apiMemberDTO.setAgreeMarketing("0");
		}

		String agreeAd = apiMemberDTO.getAgreeAd();
		if (Objects.equals(agreeAd,"Y")) {
			apiMemberDTO.setAgreeAd("1");
		} else if (Objects.equals(agreeAd, "N")) {
			apiMemberDTO.setAgreeAd("0");
		}


		apiMemberMapper.updateMember(apiMemberDTO);
	}

	/** 후보자 회원가입 */
	public ApiMemberDTO insertMember(ApiMemberDTO apiMemberDTO) {

		apiMemberDTO.setPassword(bCryptPasswordEncoder.encode(apiMemberDTO.getPassword()));
		apiMemberDTO.setTemp(false);
		apiMemberDTO.setDTYPE("AP");

		// agreeMarketing String to int
		if (Objects.equals(apiMemberDTO.getAgreeMarketing(),"Y")) {
			apiMemberDTO.setAgreeMarketing("1");
		} else if (Objects.equals(apiMemberDTO.getAgreeMarketing(), "N")) {
			apiMemberDTO.setAgreeMarketing("0");
		}

		// agreeAd String to int
		if (Objects.equals(apiMemberDTO.getAgreeAd(),"Y")) {
			apiMemberDTO.setAgreeAd("1");
		} else if (Objects.equals(apiMemberDTO.getAgreeAd(), "N")) {
			apiMemberDTO.setAgreeAd("0");
		}

		// 회원 등록
		apiMemberMapper.insertMember(apiMemberDTO);

		// 추천 코드 조회
		ApiMemberDTO headhunter = new ApiMemberDTO();
		headhunter.setReferralCode(apiMemberDTO.getHhReferralCode());
		boolean isExistHeadhunter = apiMemberMapper.selectHeadhunterByRefferalCd(headhunter);

		// 후보자 등록
		ApiMemberDTO applicant = new ApiMemberDTO();
		applicant.setMemberId(apiMemberDTO.getId());
		if(isExistHeadhunter) {
			applicant.setReferralCode(apiMemberDTO.getHhReferralCode());
		}
		apiMemberMapper.insertApplicant(applicant);

		// 간편 인증 등록
		if(StringUtils.isNotBlank(apiMemberDTO.getSimpleAuthCd()) && StringUtils.isNotBlank(apiMemberDTO.getSimpleAuthVal())){
			ApiMemberDTO simpleLogin = new ApiMemberDTO();
			simpleLogin.setMemberId(apiMemberDTO.getId());
			simpleLogin.setSimpleAuthCd(apiMemberDTO.getSimpleAuthCd());
			simpleLogin.setSimpleAuthVal(apiMemberDTO.getSimpleAuthVal());
			simpleLogin.setRegSeq(apiMemberDTO.getId());
			apiMemberMapper.insertMemberSimpleAuth(simpleLogin);
		}

		// 권한 등록
		ApiMemberDTO memberRole = new ApiMemberDTO();
		memberRole.setMemberId(apiMemberDTO.getId());
		memberRole.setRoleId("1");
		apiMemberMapper.insertMemberRole(memberRole);

		return apiMemberDTO;
	}

	/** 회원 간편정보등록 */
	public void insertMemberSimpleAuth(ApiMemberDTO apiMemberDTO) {
		apiMemberMapper.insertMemberSimpleAuth(apiMemberDTO);
	}

	/** 회원 간편정보삭제 */
	public int updateMemberSimpleAuthDel(ApiMemberDTO apiMemberDTO) {
		return apiMemberMapper.updateMemberSimpleAuthDel(apiMemberDTO);
	}

	/** 로그인 회원정보 검색 */
	public ApiMemberDTO selectLoginInfo(ApiMemberDTO apiMemberDTO) {
		return apiMemberMapper.selectLoginInfo(apiMemberDTO);
	}

	/** 회원 간편로그인 정보 검색 */
	public List<ApiMemberDTO> selectMemeberSimpleAuth(ApiMemberDTO apiMemberDTO) {
		return apiMemberMapper.selectMemeberSimpleAuth(apiMemberDTO);
	}

	/** 스크랩된 공고 조회 */
	public List<ApiScrapPositionDTO> selectMyScrapPosition(ApiScrapPositionDTO apiScrapPositionDTO) {
		return apiMemberMapper.selectMyScrapPosition(apiScrapPositionDTO);
	}

	/** 스크랩된 공고 조회 COUNT */
	public int selectMyScrapPositionCnt(ApiScrapPositionDTO apiScrapPositionDTO) {
		return apiMemberMapper.selectMyScrapPositionCnt(apiScrapPositionDTO);
	}

	/** 회원정보 (인재검색 by resume) */
	public List<ApiMemberDTO> selectMemberByResume(ApiMemberDTO apiMemberDTO) {
		return apiMemberMapper.selectMemberByResume(apiMemberDTO);
	}

	/** 회원정보 (인재검색 by resume) count */
	public int selectMemberByResumeCnt(ApiMemberDTO apiMemberDTO) {
		return apiMemberMapper.selectMemberByResumeCnt(apiMemberDTO);
	}

	/** 회원찾기 */
	public List<ApiMemberDTO> selectSearchMember(ApiMemberDTO apiMemberDTO){
		return apiMemberMapper.selectSearchMember(apiMemberDTO);
	}

	/** 자동로그인 토큰 조회 */
	public ApiMemberDTO selectAutoLoginToken(ApiMemberDTO apiMemberDTO){
		return apiMemberMapper.selectAutoLoginToken(apiMemberDTO);
	}

	/** 아이디(이메일) 중복확인 */
	public boolean selectDuplicateLoginId(ApiMemberDTO apiMemberDTO){
		return apiMemberMapper.selectDuplicateLoginId(apiMemberDTO);
	}

	/** 헤드헌터 정보조회 */
	public ApiMemberDTO selectHhInfo(ApiMemberDTO apiMemberDTO){
		return apiMemberMapper.selectHhInfo(apiMemberDTO);
	}

	/** 헤드헌터 정보수정 */
	public void updateHhInfo(ApiMemberDTO apiMemberDTO) {

		apiMemberMapper.updateMemberHh(apiMemberDTO); // member table update

		if(StringUtils.isNotBlank(apiMemberDTO.getGreeting())) { // headhunter table update
			apiMemberMapper.updateHeadhunter(apiMemberDTO);
		}

		if(apiMemberDTO.getSpecialField() != null && apiMemberDTO.getSpecialField().length > 0) { // hh_position_field update
			apiMemberMapper.deleteHhPositionField(apiMemberDTO);
			apiMemberMapper.insertHhPositionField(apiMemberDTO);
		}
	}

	public void updatePushInfo(ApiMemberDTO apiMemberDTO) {
		apiMemberMapper.updatePushInfo(apiMemberDTO);
	}
}
