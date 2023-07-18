package com.fw.fo.login.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoApplicantDTO;
import com.fw.core.dto.fo.FoHeadhunterDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoMemberRoleDTO;
import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import com.fw.core.mapper.db1.fo.FoApplicantMapper;
import com.fw.core.mapper.db1.fo.FoHeadhunterMapper;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import com.fw.core.mapper.db1.fo.FoMemberRoleMapper;
import com.fw.core.mapper.db1.fo.FoSimpleLoginMapper;
import com.fw.core.util.RandomUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoMemberService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final FoMemberMapper foMemberMapper;
	private final FoApplicantMapper foApplicantMapper;
	private final FoSimpleLoginMapper foSimpleLoginMapper;
	private final FoHeadhunterMapper foHeadhunterMapper;
	private final FoMemberRoleMapper foMemberRoleMapper;
	private final CommonFileService commonFileService;

	@Transactional
	public FoMemberDTO insertMember(FoMemberDTO foMemberDTO) throws Exception {
		foMemberDTO.setPassword(bCryptPasswordEncoder.encode(foMemberDTO.getPassword()));
		foMemberDTO.setTemp(false);

		if (StringUtils.equals("AP", foMemberDTO.getDtype())) {
			// 회원 등록
			foMemberMapper.insertMember(foMemberDTO);

			// 추천 코드 조회
			FoHeadhunterDTO foHeadhunterDTO = new FoHeadhunterDTO();
			foHeadhunterDTO.setReferralCode(foMemberDTO.getHhReferralCode());
			boolean isExistHeadhunter = foHeadhunterMapper.selectHeadhunterByRefferalCd(foHeadhunterDTO);

			// 후보자 등록
			FoApplicantDTO foApplicantDTO = new FoApplicantDTO();
			foApplicantDTO.setMemberId(foMemberDTO.getId());
			if (isExistHeadhunter) {
				foApplicantDTO.setReferralCode(foMemberDTO.getHhReferralCode());
			}
			foApplicantMapper.insertApplicant(foApplicantDTO);

			// 간편 인증 등록
			if (StringUtils.isNotBlank(foMemberDTO.getSimpleAuthCd()) && StringUtils.isNotBlank(foMemberDTO.getSimpleAuthVal())) {
				FoSimpleLoginDTO foSimpleLoginDTO = new FoSimpleLoginDTO();
				foSimpleLoginDTO.setMemberId(foMemberDTO.getId());
				foSimpleLoginDTO.setSimpleAuthCd(foMemberDTO.getSimpleAuthCd());
				foSimpleLoginDTO.setSimpleAuthVal(foMemberDTO.getSimpleAuthVal());
				foSimpleLoginDTO.setRegSeq(foMemberDTO.getId());
				foSimpleLoginMapper.insertMemberSimpleAuth(foSimpleLoginDTO);
			}

			// 권한 등록
			FoMemberRoleDTO foMemberRoleDTO = new FoMemberRoleDTO();
			foMemberRoleDTO.setMemberId(foMemberDTO.getId());
			foMemberRoleDTO.setRoleId("1");
			foMemberRoleMapper.insertMemberRole(foMemberRoleDTO);
		} else if (StringUtils.equals("HH", foMemberDTO.getDtype())) {
			// 회원 등록
			foMemberMapper.insertMember(foMemberDTO);

			// 헤드헌터 추천 코드 생성
			String referralCode = null;
			FoHeadhunterDTO foHeadhunterDTO = new FoHeadhunterDTO();
			while (true) {
				referralCode = RandomUtil.createRandomStr(6);
				foHeadhunterDTO.setReferralCode(referralCode);
				if (!foHeadhunterMapper.selectHeadhunterByRefferalCd(foHeadhunterDTO)) {
					break;
				}
			}

			// 명부 파일 등록
			Integer fileSeq = commonFileService.fileUpload(foMemberDTO.getFiles(), "HEADHUNTER");

			// 헤드헌터 등록
			foHeadhunterDTO.setSfCeoName(foMemberDTO.getSfCeoName());
			foHeadhunterDTO.setSfName(foMemberDTO.getSfName());
			foHeadhunterDTO.setSfPhone(foMemberDTO.getSfPhone());
			foHeadhunterDTO.setSfRegNumber(foMemberDTO.getSfRegNumber());
			foHeadhunterDTO.setSfWorkerListFileId(String.valueOf(fileSeq));
			foHeadhunterDTO.setMemberId(foMemberDTO.getId());
			foHeadhunterDTO.setReferralCode(referralCode);
			foHeadhunterDTO.setStatus("RECEIPT");
			foHeadhunterMapper.insertHeadhunter(foHeadhunterDTO);

			// 권한 등록
			FoMemberRoleDTO foMemberRoleDTO = new FoMemberRoleDTO();
			foMemberRoleDTO.setMemberId(foMemberDTO.getId());
			foMemberRoleDTO.setRoleId("2");
			foMemberRoleMapper.insertMemberRole(foMemberRoleDTO);

			// TODO :: 승인 요청
			foHeadhunterMapper.insertHeadhunterRequest(foHeadhunterDTO);

		}
		return foMemberDTO;
	}

	public boolean selectMemberNameDuplicateCheck(FoMemberDTO foMemberDTO) {
		return foMemberMapper.selectMemberNameDuplicateCheck(foMemberDTO);
	}

	public FoMemberDTO selectMemberEmailDuplicateCheck(FoMemberDTO foMemberDTO) {
		return foMemberMapper.selectMemberEmailDuplicateCheck(foMemberDTO);
	}

	public FoMemberDTO selectMember(FoMemberDTO foMemberDTO) {
		return foMemberMapper.selectMember(foMemberDTO);
	}

	public void updatePassword(FoMemberDTO foMemberDTO) {
		foMemberDTO.setPassword(bCryptPasswordEncoder.encode(foMemberDTO.getPassword()));
		foMemberMapper.updatePassword(foMemberDTO);
	}

	public boolean selectMemberBlackList(FoMemberDTO foMemberDTO) {
		return foMemberMapper.selectMemberBlackList(foMemberDTO);
	}

	public FoMemberDTO selectLoginId(FoMemberDTO foMemberDTO) {
		return foMemberMapper.selectLoginId(foMemberDTO);
	}

	public void updateDeleteMember(FoMemberDTO foMemberDTO) {
		foMemberMapper.updateDeleteMember(foMemberDTO);
	}

	public void updateMember(FoMemberDTO foMemberDTO) {
		foMemberMapper.updateMember(foMemberDTO);
	}

	public void updateTempMember(FoMemberDTO foMemberDTO) {
		if (StringUtils.isNotBlank(foMemberDTO.getPassword())) {
			foMemberDTO.setPassword(bCryptPasswordEncoder.encode(foMemberDTO.getPassword()));
		}
		foMemberMapper.updateTempMember(foMemberDTO);
	}
}
