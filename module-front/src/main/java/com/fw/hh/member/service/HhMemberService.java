package com.fw.hh.member.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoApplicantDTO;
import com.fw.core.dto.fo.FoHeadhunterDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoMemberRoleDTO;
import com.fw.core.dto.hh.HhSfChangeRequestDTO;
import com.fw.core.mapper.db1.fo.FoApplicantMapper;
import com.fw.core.mapper.db1.fo.FoHeadhunterMapper;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import com.fw.core.mapper.db1.fo.FoMemberRoleMapper;
import com.fw.core.mapper.db1.hh.HhMemberMapper;
import com.fw.core.mapper.db1.hh.HhResumeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhMemberService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final HhMemberMapper hhMemberMapper;
	private final HhResumeMapper hhResumeMapper;
	private final FoMemberMapper foMemberMapper;
	private final FoApplicantMapper foApplicantMapper;
	private final FoHeadhunterMapper foHeadhunterMapper;
	private final FoMemberRoleMapper foMemberRoleMapper;
	private final CommonFileService commonFileService;
	private final CommonService commonService;

	@Transactional
	public Map<String, Object> insertTempApplicant(FoMemberDTO foMemberDTO){
		Map<String, Object> resultMap = new HashMap<>();

		foMemberDTO.setPhone(foMemberDTO.getPhone().replaceAll("[^\\d.]", ""));	// 휴대폰 숫자 이외 문자 제거
		foMemberDTO.setEmail(foMemberDTO.getEmail().toLowerCase());
		foMemberDTO.setLoginId(foMemberDTO.getEmail().toLowerCase());
		foMemberDTO.setAgreeMarketing("0");
		foMemberDTO.setDtype("AP");

		// 휴대폰 중복 체크
		FoMemberDTO phone = hhMemberMapper.selectExistMember(FoMemberDTO.builder().phone(foMemberDTO.getPhone()).build());
		if(phone != null){
			resultMap.put("RESULT", "PHONE_DUPLICATION");
			return resultMap;
		}

		// 이메일 중복 체크
		FoMemberDTO email = hhMemberMapper.selectExistMember(FoMemberDTO.builder().email(foMemberDTO.getEmail()).build());
		if(email != null){
			resultMap.put("RESULT", "EMAIL_DUPLICATION");
			return resultMap;
		}

		FoMemberDTO hh = hhMemberMapper.selectExistMember(foMemberDTO);

		if(hh != null){
			// 탈퇴 회원 체크
			if(StringUtils.equals("1", hh.getDeleted())) {
				resultMap.put("RESULT", "DELETED_MEMBER");
				return resultMap;
			} else {
				resultMap.put("RESULT", "MEMBER_DUPLICATION");
				return resultMap;
			}
		}

		foMemberDTO.setPassword(bCryptPasswordEncoder.encode(foMemberDTO.getPhone()));	// 임시비밀번호는 회원 핸드폰으로 설정
		foMemberDTO.setTemp(true);

		// 회원 등록
		foMemberMapper.insertMember(foMemberDTO);

		// 후보자 등록
		FoApplicantDTO foApplicantDTO = new FoApplicantDTO();
		foApplicantDTO.setMemberId(foMemberDTO.getId());

		// 추천 코드 조회
		FoHeadhunterDTO foHeadhunterDTO = new FoHeadhunterDTO();
		foHeadhunterDTO.setMemberId(foMemberDTO.getFrontSession().getId());
		String referralCd = foHeadhunterMapper.selectHeadhunterByMemberId(foHeadhunterDTO);
		foApplicantDTO.setReferralCode(referralCd);
		foApplicantMapper.insertApplicant(foApplicantDTO);

		// 권한 등록
		FoMemberRoleDTO foMemberRoleDTO = new FoMemberRoleDTO();
		foMemberRoleDTO.setMemberId(foMemberDTO.getId());
		foMemberRoleDTO.setRoleId("1");
		foMemberRoleMapper.insertMemberRole(foMemberRoleDTO);

		// 기본 이력서 생성
		/*
		HhResumeDTO hhResumeDTO = HhResumeDTO.builder()
				.name(foMemberDTO.getName())
				.bylaws("0")
				.disability("0")
				.isVeterans("0")
				.existTemp("0")
				.opened("0")
				.representation("1")
				.firstOpenChanged("0")
				.createMemberId(foMemberDTO.getFrontSession().getId())
				.memberId(foMemberDTO.getId())
				.build();
		hhResumeMapper.insertTempResume(hhResumeDTO);
		*/
		resultMap.put("RESULT", "SUCCESS");
		resultMap.put("memberId", foMemberDTO.getId());
		//resultMap.put("resumeId", hhResumeDTO.getId());
		return resultMap;
	}


	@Transactional
	public Map<String, Object> insertTempApplicantNoResume(FoMemberDTO foMemberDTO){
		Map<String, Object> resultMap = new HashMap<>();

		foMemberDTO.setPhone(foMemberDTO.getPhone().replaceAll("[^\\d.]", ""));	// 휴대폰 숫자 이외 문자 제거
		foMemberDTO.setEmail(foMemberDTO.getEmail().toLowerCase());
		foMemberDTO.setLoginId(foMemberDTO.getEmail().toLowerCase());
		foMemberDTO.setAgreeMarketing("0");
		foMemberDTO.setDtype("AP");

		// 휴대폰 중복 체크
		FoMemberDTO phone = hhMemberMapper.selectExistMember(FoMemberDTO.builder().phone(foMemberDTO.getPhone()).build());
		if(phone != null){
			resultMap.put("RESULT", "PHONE_DUPLICATION");
			return resultMap;
		}

		// 이메일 중복 체크
		FoMemberDTO email = hhMemberMapper.selectExistMember(FoMemberDTO.builder().email(foMemberDTO.getEmail()).build());
		if(email != null){
			resultMap.put("RESULT", "EMAIL_DUPLICATION");
			return resultMap;
		}

		FoMemberDTO hh = hhMemberMapper.selectExistMember(foMemberDTO);

		if(hh != null){
			// 탈퇴 회원 체크
			if(StringUtils.equals("1", hh.getDeleted())) {
				resultMap.put("RESULT", "DELETED_MEMBER");
				return resultMap;
			} else {
				resultMap.put("RESULT", "MEMBER_DUPLICATION");
				return resultMap;
			}
		}

		foMemberDTO.setPassword(bCryptPasswordEncoder.encode(foMemberDTO.getPhone()));	// 임시비밀번호는 회원 핸드폰으로 설정
		foMemberDTO.setTemp(true);

		// 회원 등록
		foMemberMapper.insertMember(foMemberDTO);

		// 후보자 등록
		FoApplicantDTO foApplicantDTO = new FoApplicantDTO();
		foApplicantDTO.setMemberId(foMemberDTO.getId());

		// 추천 코드 조회
		FoHeadhunterDTO foHeadhunterDTO = new FoHeadhunterDTO();
		foHeadhunterDTO.setMemberId(foMemberDTO.getFrontSession().getId());
		String referralCd = foHeadhunterMapper.selectHeadhunterByMemberId(foHeadhunterDTO);
		foApplicantDTO.setReferralCode(referralCd);
		foApplicantMapper.insertApplicant(foApplicantDTO);

		// 권한 등록
		FoMemberRoleDTO foMemberRoleDTO = new FoMemberRoleDTO();
		foMemberRoleDTO.setMemberId(foMemberDTO.getId());
		foMemberRoleDTO.setRoleId("1");
		foMemberRoleMapper.insertMemberRole(foMemberRoleDTO);

		resultMap.put("RESULT", "SUCCESS");
		resultMap.put("memberId", foMemberDTO.getId());

		return resultMap;
	}

	public boolean selectSfChangeHistory(FoMemberDTO foMemberDTO) {
		return hhMemberMapper.selectSfChangeHistory(foMemberDTO);
	}

	@Transactional
	public void insertSfChange(HhSfChangeRequestDTO hhSfChangeRequestDTO) throws Exception {
		Integer sfWorkerListFileId = commonFileService.fileUpload(hhSfChangeRequestDTO.getSfWorkerFile(), "SF");
		hhSfChangeRequestDTO.setSfWorkerListFileId(String.valueOf(sfWorkerListFileId));
		hhSfChangeRequestDTO.setStatus("ACCEPT");
		hhMemberMapper.insertSfChange(hhSfChangeRequestDTO);

		FoMemberDTO foMemberDTO = new FoMemberDTO();
		foMemberDTO.setLoginId(hhSfChangeRequestDTO.getLoginId());
		FoMemberDTO memberInfo = foMemberMapper.selectMemberEmailDuplicateCheck(foMemberDTO);
		if (memberInfo != null) {
			// 서치펌 변경신청 관리자 PUSH 알림
			List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
			for (FoMemberDTO admin : adminList) {
				PushHistDTO push = PushHistDTO.builder()
						.dispType("SF_CHANGE_REQUEST")
						.memberId(admin.getApprovalSeq())
						.title("서치펌 변경신청")
						.content(memberInfo.getName()+"("+memberInfo.getLoginId()+") 님이 서치펌 변경신청 하셨습니다.")
						.createId(memberInfo.getId())
						.build();
				commonService.insertPushHist(push);
			}
		}
	}
}
