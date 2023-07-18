package com.fw.hh.apsearch.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.MailDTO;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.dto.hh.*;
import com.fw.core.mapper.db1.fo.resume.FoResumeMapper;
import com.fw.core.mapper.db1.hh.HhApSearchsMapper;
import com.fw.core.mapper.db1.hh.HhMemberMapper;
import com.fw.core.mapper.db1.hh.HhMyProfileMapper;
import com.fw.core.mapper.db1.hh.HhSendMsgMapper;
import com.fw.core.util.IpUtil;
import com.fw.core.util.MailUtil;
import com.fw.fo.resume.service.FoFilterService;
import com.fw.hh.member.service.HhMemberService;
import com.fw.hh.myap.service.HhMyApService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhApSearchService {
	@Value("${service.web_domain}")
	private String WEB_DOMAIN;
	private final HhApSearchsMapper hhApSearchsMapper;
	private final CommonFileService commonFileService;
	private final HhMemberService hhMemberService;
	private final FoResumeMapper foResumeMapper;
	private final FoFilterService foFilterService;
	private final HhMyApService hhMyApService;
	private final HhSendMsgMapper hhSendMsgMapper;
	private final HhMemberMapper hhMemberMapper;
	private final HhMyProfileMapper hhMyprofileMapper;
	private final CommonService commonService;

	public int selectHhApListCount(HhMyapListDTO hhMyapListDTO) {
		return hhApSearchsMapper.selectHhApListCount(hhMyapListDTO);
	}

	public List<HhMyapListDTO> selectHhApList(HhMyapListDTO hhMyapListDTO) {
		return hhApSearchsMapper.selectHhApList(hhMyapListDTO);
	}

	public HhSearchApDTO selectHhSearchApCondition(FoSessionDTO foSessionDTO) {
		return hhApSearchsMapper.selectHhSearchApCondition(foSessionDTO);
	}

	public int insertHhSearchApCondition(HhMyapListDTO hhMyapListDTO) {
		return hhApSearchsMapper.insertHhSearchApCondition(hhMyapListDTO);
	}

	public int insertHhSearchApConditionHp(HhMyapListDTO hhMyapListDTO) {
		return hhApSearchsMapper.insertHhSearchApConditionHp(hhMyapListDTO);
	}

	public int insertHhSearchApConditionLoc(HhMyapListDTO hhMyapListDTO) {
		return hhApSearchsMapper.insertHhSearchApConditionLoc(hhMyapListDTO);
	}

	public int deleteHhSearchApCondition(HhMyapListDTO hhMyapListDTO) {
		return hhApSearchsMapper.deleteHhSearchApCondition(hhMyapListDTO);
	}

	public int selectHhResumeFormCount(HhResumeFormDTO hhResumeFormDTO) {
		return hhApSearchsMapper.selectHhResumeFormCount(hhResumeFormDTO);
	}

	public List<HhResumeFormDTO> selectHhResumeFormList(HhResumeFormDTO hhResumeFormDTO) {
		List<HhResumeFormDTO> list = hhApSearchsMapper.selectHhResumeFormList(hhResumeFormDTO);
		for(int i=0; i<list.size();i++){
			String fileSeq = list.get(i).getResumeFileId();
			if(!Objects.isNull(fileSeq)){
				if(StringUtils.isNotBlank(fileSeq)){
					list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
				}
			}
			String fileSeq2 = list.get(i).getAgreeFileId();
			if(!Objects.isNull(fileSeq2)){
				if(StringUtils.isNotBlank(fileSeq2)){
					list.get(i).setAgreeFileList(commonFileService.selectFileDetailList(fileSeq2));
				}
			}
		}
		return list;
	}

	@Transactional
	public String insertHhRegistrationApply(HhResumeFormDTO hhResumeFormDTO) throws Exception {

		if (hhResumeFormDTO.getFile1() == null && hhResumeFormDTO.getFile2() == null) {
			return "FAIL";
		}

		// 파일 부분 질문해야함
		// Integer fileId = commonFileService.fileUpload(hhResumeFormDTO.getFile1(), "RESUME_FORM");
		// hhResumeFormDTO.setFormFileId(""+fileId);
/*
		String name = "";
		if (hhResumeFormDTO.getFile1() == null) {
			name += hhResumeFormDTO.getFile1()[0].getOriginalFilename();
		}
		if (hhResumeFormDTO.getFile2() == null) {
			if (name.length() != 0) {
				name += ",";
			}
			name += hhResumeFormDTO.getFile1()[0].getOriginalFilename();
		}
		hhResumeFormDTO.setName(name);
		hhResumeFormDTO.setStatus("WAITING_CONFIRM");
		hhResumeFormDTO.setFormFileId("1");
*/
		Integer profileFileId = commonFileService.fileUpload(hhResumeFormDTO.getFile1(), "RESUME_PROFILE");
		if(profileFileId != null){
			hhResumeFormDTO.setAgreeFileId(String.valueOf(profileFileId));
		}
		Integer profileFileId2 = commonFileService.fileUpload(hhResumeFormDTO.getFile2(), "RESUME_PROFILE");
		if(profileFileId != null){
			hhResumeFormDTO.setResumeFileId(String.valueOf(profileFileId2));
		}
		hhApSearchsMapper.insertHhResumeForm(hhResumeFormDTO);

		// 이력서 등록신청 관리자 PUSH 알림
		List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
		for (FoMemberDTO admin : adminList) {
			PushHistDTO push = PushHistDTO.builder()
					.dispType("RESUME_REGISTRATION")
					.memberId(admin.getApprovalSeq())
					.title("이력서 등록신청")
					.content("이력서 등록신청이 있습니다.")
					.createId(hhResumeFormDTO.getFrontSession().getId())
					.build();
			commonService.insertPushHist(push);
		}
		return "SUCCESS";
	}

	@Transactional
	public Map<String, Object> insertResume(FoResumeDTO foResumeDTO) throws Exception {
		Map<String, Object> resultMap = null;
		if (StringUtils.isBlank(foResumeDTO.getMemberId())) {
			FoMemberDTO tmpfm = new FoMemberDTO();
			tmpfm.setLoginId(foResumeDTO.getEmail());
			tmpfm.setPhone(foResumeDTO.getPhone());
			tmpfm.setEmail(foResumeDTO.getEmail());
			tmpfm.setName(foResumeDTO.getName());
			tmpfm.setFrontSession(foResumeDTO.getFrontSession());
			resultMap = hhMemberService.insertTempApplicantNoResume(tmpfm);
			if (StringUtils.equals("SUCCESS", (String) resultMap.get("RESULT"))) {
				foResumeDTO.setMemberId((String) resultMap.get("memberId"));
			} else {
				return resultMap;
			}
		}

		// 이력서 프로필 사진 업로드
		Integer profileFileId = commonFileService.fileUpload(foResumeDTO.getResumeImageFiles(), "RESUME_PROFILE");
		if (profileFileId != null) {
			foResumeDTO.setPictureFileId(String.valueOf(profileFileId));
		}

        // 이력서 등록
        foResumeMapper.insertResume(foResumeDTO);
        String resumeId = foResumeDTO.getId();

		// 핵심역량 등록
		for (FoResumeDTO f : foResumeDTO.getSpecList()) {
			f.setResumeId(resumeId);
			foResumeMapper.insertResumeSpec(f);
		}

		// 경력 저장
		for (FoResumeDTO f : foResumeDTO.getCareerList()) {
			f.setResumeId(resumeId);
			foResumeMapper.insertResumeCareer(f);
		}

		// 학력 저장
		String filterCd = "";
		for (FoResumeDTO f : foResumeDTO.getAcademicBackgroundList()) {
			f.setResumeId(resumeId);
			foResumeMapper.insertResumeAcademicBackground(f);

			// 전공 저장
			String academicBackgroundId = f.getId();
			for (FoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
				if (StringUtils.isNotBlank(fm.getMajorName())) {
					fm.setAcademicBackgroundId(academicBackgroundId);
					foResumeMapper.insertResumeAcademicBackgroundMajor(fm);
				}
			}

			String resultFilterCd = foFilterService.getUnivFilter(f);
			if (StringUtils.isNotBlank(resultFilterCd)) {
				filterCd = resultFilterCd;
			}
		}

		// 필터링 저장
		foResumeMapper.insertResumeFiltering(FoResumeDTO.builder().filterCd(filterCd).resumeId(resumeId).build());

		// 어학 저장
		if (StringUtils.isNotBlank(foResumeDTO.getLanguageInput())) {
			foResumeMapper.insertResumeLanguage(FoResumeDTO.builder().conversationCd(foResumeDTO.getConversationCd())
					.languageCd(foResumeDTO.getLanguageCd()).languageCertificationCd(foResumeDTO.getLanguageCertificationCd())
					.languageInput(foResumeDTO.getLanguageInput()).obtainYm(foResumeDTO.getObtainYm()).testScore(foResumeDTO.getTestScore())
					.testTypeCd(foResumeDTO.getTestTypeCd()).resumeId(resumeId).build());
		}

		// 자격증 저장
		if (StringUtils.isNotBlank(foResumeDTO.getLicenseName())) {
			foResumeMapper.insertResumeLicense(FoResumeDTO.builder().agency(foResumeDTO.getAgency()).name(foResumeDTO.getLicenseName())
					.obtainYmLicense(foResumeDTO.getObtainYmLicense()).resumeId(resumeId).build());
		}

		// 수상 저장
		if (StringUtils.isNotBlank(foResumeDTO.getAwardName())) {
			foResumeMapper.insertResumeAward(FoResumeDTO.builder().agency(foResumeDTO.getAgency()).awardYm(foResumeDTO.getAwardYm())
					.name(foResumeDTO.getAwardName()).resumeId(resumeId).build());
		}

		// 외부활동 저장
		if (StringUtils.isNotBlank(foResumeDTO.getContent())) {
			foResumeMapper.insertResumeExternalActivity(FoResumeDTO.builder().content(foResumeDTO.getContent()).resumeId(resumeId).build());
		}

		// 포트폴리오 저장
		int i = 0;
		for (FoResumeDTO f : foResumeDTO.getPortfolioList()) {
			f.setResumeId(resumeId);
			if (StringUtils.equals("FILE", f.getAttachType())) {
				Integer portFolioId = commonFileService.fileUpload(new MultipartFile[] { foResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
				f.setAttachFileId(String.valueOf(portFolioId));
				i++;
			}
			foResumeMapper.insertResumePortfolio(f);
		}

		// 영문이력서 저장
		Integer enResumeId = commonFileService.fileUpload(foResumeDTO.getEnResumeFile(), "EN_RESUME");
		if (enResumeId != null) {
			foResumeMapper.insertResumeEnglish(
					FoResumeDTO.builder().attachType("FILE").url(null).attachFileId(String.valueOf(enResumeId)).resumeId(resumeId).build());
		}

		// 지역 저장
		foResumeMapper.insertResumeLocation(FoResumeDTO.builder().desiredLocationCd(foResumeDTO.getDesiredLocationCd()).resumeId(resumeId).build());

		if ("1".equals(foResumeDTO.getOpened())) {
			// 공개하는 경우 20000포인트 지급함
			HhMyapListDTO tmp = new HhMyapListDTO();
			tmp.setFrontSession(foResumeDTO.getFrontSession());
			hhMyApService.addExpiredPoint(tmp);
		}

		if (foResumeDTO.getApplicantId() != null && foResumeDTO.getApplicantId().length() > 0) {
			// 지원서가 이미 있는경우에는 해당 이력서를 업데이트 해줌
			foResumeDTO.setResumeId(foResumeDTO.getId());
			foResumeMapper.updateApplicantForResumeId(foResumeDTO);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("logoImagePath", "https://www.resume9.co.kr/nuxtfiles/img/n_logo_03.f09a7d2.png");
		map.put("mailImage1", WEB_DOMAIN + "/static/images/mail-img-1.png");
		map.put("mailImage2", WEB_DOMAIN + "/static/images/mail-img-2.png");
		map.put("mailImage3", WEB_DOMAIN + "/static/images/mail-img-3.png");
		map.put("mailImage4", WEB_DOMAIN + "/static/images/mail-img-4.png");
		map.put("mailImage5", WEB_DOMAIN + "/static/images/mail-img-5.png");
		map.put("mailImage6", WEB_DOMAIN + "/static/images/mail-img-6.png");
		map.put("apName", foResumeDTO.getName());
		map.put("apEmail", foResumeDTO.getEmail());
		map.put("mailRejectUrl", WEB_DOMAIN + "/"); // 수신 거부 URL
		map.put("mailSupportUrl", WEB_DOMAIN + "/"); // 도움 URL`
		map.put("hhName", foResumeDTO.getFrontSession().getName());
		map.put("homeUrl", WEB_DOMAIN + "/");
		map.put("signupUrl", WEB_DOMAIN + "/fo/auth/signup-temp?memberId=" + foResumeDTO.getMemberId()); // 임시회원가입 URL
		map.put("subject", "이력서 등록");
		MailUtil.sendTemplateEmail(MailDTO.builder().fromEmail("resume9@resume9.co.kr")
				.receiveEmail(Collections.singletonList(foResumeDTO.getEmail())).template("TEMP_SIGNUP").templateData(map).build());

		// 발송이력 저장
		HhSendMsgDTO hh = new HhSendMsgDTO();
		hh.setKindCd("TEMP_SIGNUP");
		hh.setMedia("MAIL");
		hh.setSubject("이력서 등록");
		hh.setMemberId(foResumeDTO.getFrontSession().getId());
		hhSendMsgMapper.insertHhSendMsg(hh);

		// 발송대상 저장
		hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder().msgId(hh.getId()).isRead("0").name(foResumeDTO.getName()).success("WAITING")
				.target(foResumeDTO.getEmail()).val(WEB_DOMAIN + "/fo/auth/signup-temp?memberId=" + foResumeDTO.getMemberId()) // 임시회원 회원가입 URL
				.build());

		// 내 인재 등록
		FoMemberDTO foMemberDTO = new FoMemberDTO();
		foMemberDTO.setMemberId(foResumeDTO.getMemberId());
		foMemberDTO.setResumeId(foResumeDTO.getId());
		foMemberDTO.setFrontSession(foResumeDTO.getFrontSession());
		hhMemberMapper.insertHhResumeReadingHistory(foMemberDTO);

		// 이력서 등록 시 이용내역 insert
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		HhMyProfileDTO hhMyprofileDTO = new HhMyProfileDTO();
		hhMyprofileDTO.setAccessIp(IpUtil.getClientIp());
		hhMyprofileDTO.setUserAgent(request.getHeader("user-agent"));
		hhMyprofileDTO.setMemberId(foResumeDTO.getFrontSession().getId());
		hhMyprofileDTO.setUseType("RESUME_REGISTER");
		hhMyprofileMapper.insertHist(hhMyprofileDTO);

		return resultMap;
	}

}
