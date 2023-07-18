package com.fw.hh.position.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.MailDTO;
import com.fw.core.dto.fo.*;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.dto.hh.*;
import com.fw.core.mapper.db1.fo.FoPositionMapper;
import com.fw.core.mapper.db1.fo.resume.FoResumeMapper;
import com.fw.core.mapper.db1.hh.*;
import com.fw.core.util.IpUtil;
import com.fw.core.util.MailUtil;
import com.fw.core.util.SmsUtil;
import com.fw.hh.member.service.HhMemberService;
import com.fw.m.resume.service.MResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class HhPositionService {

	@Value("${service.web_domain}")
	private String WEB_DOMAIN;
	private final HhPositionMapper hhPositionMapper;
	private final HhMemberService hhMemberService;
	private final HhResumeMapper hhResumeMapper;
	private final FoPositionMapper foPositionMapper;
	private final HhCompanyMapper hhCompanyMapper;
	private final HhSendMsgMapper hhSendMsgMapper;
	private final CommonFileService commonFileService;
	private final HhMyApMapper hhMyApMapper;
	private final MResumeService mResumeService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final FoResumeMapper foResumeMapper;
	private final HhMyProfileMapper hhMyprofileMapper;

	public List<HhPositionDTO> selectRegPositionList(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectRegPositionList(hhPositionDTO);
	}

	public List<HhCompanyDTO> selectHrCompanyList(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrCompanyList(hhCompanyDTO);
	}

	public int selectHhPositionCount(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectHhPositionCount(hhPositionDTO);
	}

	public List<HhPositionDTO> selectHhPositionList(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectHhPositionList(hhPositionDTO);
	}

	@Transactional
	public void insertPosition(HhPositionDTO hhPositionDTO) throws Exception {
		HhCompanyDTO hhCompanyDTO = HhCompanyDTO.builder().address(hhPositionDTO.getAddress()).ceo(hhPositionDTO.getCeo())
				.companyName(hhPositionDTO.getCompanyName()).companyScale(hhPositionDTO.getCompanyScale())
				.companyStatus(hhPositionDTO.getCompanyStatus()).establishDate(hhPositionDTO.getEstablishDate())
				.industry(hhPositionDTO.getCompanyIndustry()).licenseNumber(hhPositionDTO.getLicenseNumber()).location(hhPositionDTO.getLocation())
				.marketListing(hhPositionDTO.getMarketListing()).phone(hhPositionDTO.getPhone()).build();
		hhCompanyDTO.setFrontSession(hhPositionDTO.getFrontSession());

		// 공고제목,직무,채용공고JD 금칙어 체크
		List<HhPositionDTO> banWordList = hhPositionMapper.selectPositionBanWordList(hhPositionDTO);
		for (HhPositionDTO ban : banWordList) {
			String banTitle = hhPositionDTO.getTitle().replaceAll("[^\uAC00-\uD7A30-9a-zA-Z]", "").replaceAll(" ", "");
			if (StringUtils.contains(banTitle, ban.getWord())) { // 제목 금칙어
				hhPositionDTO.setBanMsg("TITLE_BAN_WORDS");
				return;
			}
			String banIndustry = hhPositionDTO.getIndustry().replaceAll("[^\uAC00-\uD7A30-9a-zA-Z]", "").replaceAll(" ", "");
			if (StringUtils.contains(banIndustry, ban.getWord())) { // 직무 금칙어
				hhPositionDTO.setBanMsg("INDUSTRY_BAN_WORDS");
				return;
			}
			String banJd = hhPositionDTO.getJobDescription().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
					.replaceAll("[^\uAC00-\uD7A30-9a-zA-Z]", "").replaceAll(" ", "");
			if (StringUtils.contains(banJd, ban.getWord())) { // JD 금칙어
				hhPositionDTO.setBanMsg("JD_BAN_WORDS");
				return;
			}
		}

		// 기등록 기업정보 검색
		List<HhCompanyDTO> list = hhCompanyMapper.selectHrCompanyList(hhCompanyDTO);
		String hrCompanyId = "";
		if (Objects.isNull(list) || list.size() == 0) {
			// 기업정보 등록
			hhCompanyMapper.insertHrCompany(hhCompanyDTO);
			hrCompanyId = hhCompanyDTO.getId();
		} else {
			log.info("{}", list.get(0));
			hrCompanyId = list.get(0).getId();
		}

		// 고객사 인사담당 등록
		for (HhPositionDTO h : hhPositionDTO.getContactInfoList()) {
			hhCompanyMapper.insertHrManager(HhCompanyDTO.builder().email(h.getEmail()).name(h.getName()).phone(h.getPhone()).id(hrCompanyId).build());
		}

		// 포지션 등록
		hhPositionDTO.setHrCompanyId(hrCompanyId);
		hhPositionMapper.insertPosition(hhPositionDTO);
		String positionId = hhPositionDTO.getId();

		// 기업 소개 등록
		if (hhPositionDTO.getCompanyInfoFiles() != null) {
			for (MultipartFile m : hhPositionDTO.getCompanyInfoFiles()) {
				Integer companyInfo = commonFileService.fileUpload(new MultipartFile[] { m }, "COMPANY_INFO");
				hhPositionMapper.insertPositionCompanyInfo(
						HhPositionDTO.builder().introUrl(null).introFileId(String.valueOf(companyInfo)).positionId(positionId).build());
			}
		}

		// 포지션 참가자 등록
		for (HhPositionDTO h : hhPositionDTO.getCoworkerList()) {
			h.setPositionId(positionId);
			hhPositionMapper.insertPositionParticipant(h);
		}
		// 포지션 등록 시 이용내역 insert
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		HhMyProfileDTO hhMyprofileDTO = new HhMyProfileDTO();
		hhMyprofileDTO.setAccessIp(IpUtil.getClientIp());
		hhMyprofileDTO.setUserAgent(request.getHeader("user-agent"));
		hhMyprofileDTO.setMemberId(hhPositionDTO.getFrontSession().getId());
		hhMyprofileDTO.setUseType("POSITION_REGISTER");

		hhMyprofileMapper.insertHist(hhMyprofileDTO);

	}

	public List<HhPositionDTO> selectWeFirmCoworkerList(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectWeFirmCoworkerList(hhPositionDTO);
	}

	public HhPositionDTO selectPosition(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectPosition(hhPositionDTO);
	}

	public List<HhPositionDTO> selectPositionApplicant(HhPositionDTO hhPositionDTO) {
		List<HhPositionDTO> list = hhPositionMapper.selectPositionApplicant(hhPositionDTO);
		for (HhPositionDTO h : list) {
			if (StringUtils.isNotBlank(h.getAgreeFileId())) {
				h.setAgreeFileList(commonFileService.selectFileDetailList(h.getAgreeFileId()));
			}

			if (StringUtils.isNotBlank(h.getResumeFileId())) {
				h.setResumeFileList(commonFileService.selectFileDetailList(h.getResumeFileId()));
			}
		}
		return list;
	}

	@Transactional
	public void updatePositionDeadline(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updatePositionDeadline(hhPositionDTO);
	}

	public List<HhPositionDTO> selectPositionCoworkerList(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectPositionCoworkerList(hhPositionDTO);
	}

	public HhPositionDTO selectPositionStatus(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectPositionStatus(hhPositionDTO);
	}

	@Transactional
	public String insertContactList(FoMemberDTO foMemberDTO) {
		Map<String, Object> resultMap = new HashMap<>();

		if (hhResumeMapper.selectResumeRejectList(
				HhResumeDTO.builder().memberId(foMemberDTO.getFrontSession().getId()).email(foMemberDTO.getEmail()).build()) > 0) {
			return "REJECT";
		}

		resultMap = hhMemberService.insertTempApplicant(foMemberDTO); // 임시회원 생성
		String result = (String) resultMap.get("RESULT");

		if (StringUtils.equals("SUCCESS", result)) {
			FoPositionApplicantDTO foPositionApplicantDTO = new FoPositionApplicantDTO();
			foPositionApplicantDTO.setPhoneOpened("0");
			foPositionApplicantDTO.setPmSubmitted("0");
			foPositionApplicantDTO.setProgressStep("CONTACT");
			foPositionApplicantDTO.setProposedEamil("0");
			foPositionApplicantDTO.setProposedSms("0");
			foPositionApplicantDTO.setRegPath("3");
			foPositionApplicantDTO.setUpdateRequest("0");
			foPositionApplicantDTO.setApMemberId((String) resultMap.get("memberId"));
			// foPositionApplicantDTO.setResumeId((String) resultMap.get("resumeId"));
			foPositionApplicantDTO.setHhMemberId(foMemberDTO.getFrontSession().getId());
			foPositionApplicantDTO.setPositionId(foMemberDTO.getPositionId());
			foPositionMapper.insertPositionApplicant(foPositionApplicantDTO); // 포지션 지원 등록
		}
		return result;
	}

	@Transactional
	public void updatePositionEnd(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updatePositionEnd(hhPositionDTO);
	}

	@Transactional
	public void updateCoworkerEnd(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updateCoworkerEnd(hhPositionDTO);
	}

	@Transactional
	public void deleteContactList(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.deleteContactList(hhPositionDTO);
	}

	@Transactional
	public void updateProposalStatus(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updateProposalStatus(hhPositionDTO);

		// 탈락일시 문자 발송
		if (StringUtils.equals("FAIL", hhPositionDTO.getProposalStatus())) {
			SmsUtil.sendMessage(MsgDTO.builder().type("SMS").to(hhPositionDTO.getPhone()).message("[RESUME9] 지원하신 포지션에 아쉽게도 불합격 처리되었습니다.").build());
		}

	}

	@Transactional
	public void updateProgressStep(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updateProgressStep(hhPositionDTO);
	}

	@Transactional
	public String updateSendSms(HhPositionDTO hhPositionDTO) {
		List<HhPositionDTO> list = hhPositionMapper.selectPositionApplicantBySpecial(hhPositionDTO); // 문자 발송 리스트

		HhPointDTO reqHhPointDTO = new HhPointDTO();
		reqHhPointDTO.setFrontSession(hhPositionDTO.getFrontSession());
		HhPointDTO pointInfo = hhMyApMapper.selectPoint(reqHhPointDTO);
		if (pointInfo == null) {
			return "NO_POINT";
		}

		int decreasePoint = 0;
		String type = ""; // 포인트 사용내역 SMS/LMS 구분
		// SMS/LMS 종류에 따른 차감포인트
		if (StringUtils.equals(hhPositionDTO.getType(), "SMS")) {
			decreasePoint = 20 * list.size();
			type = "USE_SEND_SMS";
		} else if (StringUtils.equals(hhPositionDTO.getType(), "LMS")) {
			decreasePoint = 60 * list.size();
			type = "USE_SEND_LMS";
		}

		// 보유포인트보다 차감포인트가 작으면 문자 발송하지 않음
		if (Integer.parseInt(pointInfo.getBalance()) < decreasePoint) {
			return "NO_POINT";
		}

		for (HhPositionDTO h : list) {
			String message = hhPositionDTO.getContent();
			message = message.replace("{$포지션제목$}", h.getTitle());
			message = message.replace("{$수신자이름$}", h.getName());
			SmsUtil.sendMessage(MsgDTO.builder().type(hhPositionDTO.getType()).from(hhPositionDTO.getFrontSession().getPhone()).to(h.getPhone())
					.subject(hhPositionDTO.getSubject()).message(message).build());
			// 발송이력
			HhSendMsgDTO hh = new HhSendMsgDTO();
			hh.setContent(message);
			hh.setKindCd("GENERAL");
			hh.setMedia(hhPositionDTO.getType());
			hh.setSubject(hhPositionDTO.getSubject());
			hh.setMemberId(hhPositionDTO.getFrontSession().getId());
			hhSendMsgMapper.insertHhSendMsg(hh);

			// 발송대상 저장
			hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder().msgId(hh.getId()).isRead("0").name(h.getName())
					.success("WAITING").target(h.getPhone()).build());
		}

		// 포인트 차감
		mResumeService.updatePoint(
				FoPointDTO.builder().type(type).amount(decreasePoint).memberId(hhPositionDTO.getFrontSession().getId()).build());
		return "SUCCESS";
	}

	@Transactional
	public void updateSendFailSms(HhPositionDTO hhPositionDTO) {
		List<HhPositionDTO> list = hhPositionMapper.selectPositionApplicantBySpecial(hhPositionDTO); // 문자 발송 리스트
		for (HhPositionDTO h : list) {
			String message = hhPositionDTO.getContent();
			message = message.replace("{$포지션제목$}", h.getTitle());
			message = message.replace("{$수신자이름$}", h.getName());
			SmsUtil.sendMessage(MsgDTO.builder().type(hhPositionDTO.getType()).from(hhPositionDTO.getFrontSession().getPhone()).to(h.getPhone())
					.subject(hhPositionDTO.getSubject()).message(message).build());
			// 발송 이력
			HhSendMsgDTO hh = new HhSendMsgDTO();
			hh.setContent(message);
			hh.setKindCd("GENERAL");
			hh.setMedia("SMS");
			hh.setSubject(hhPositionDTO.getSubject());
			hh.setMemberId(hhPositionDTO.getFrontSession().getId());
			hhSendMsgMapper.insertHhSendMsg(hh);

			// 발송대상 저장
			hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder().msgId(hh.getId()).isRead("0").name(hhPositionDTO.getFrontSession().getName())
					.success("WAITING").target(h.getPhone()).build());

			// 탈락 처리
			h.setProcessStatus("FAIL"); // 진행상태
			h.setProgressStep("FAIL"); // 제안상태
			hhPositionMapper.updateProgressStep(h);
			hhPositionMapper.updateProgressStatus(h);
		}
	}

	@Transactional
	public void updateSendMail(HhPositionDTO hhPositionDTO) {
		List<HhPositionDTO> list = hhPositionMapper.selectPositionApplicantBySpecial(hhPositionDTO); // 문자 발송 리스트
		for (HhPositionDTO h : list) {
			String message = hhPositionDTO.getContent();
			message = message.replace("{$포지션제목$}", h.getTitle());
			message = message.replace("{$수신자이름$}", h.getName());

			String proposalUrl = "/fo/user/application/job-position";
			String template = "PROPOSAL";

			// 수동입력 제안 메일의 경우 다른 템플릿의 이메일이 전송된다.
			if (StringUtils.equals("3", h.getRegPath())) {
				proposalUrl = "/m/user/position?memberId=" + h.getMemberId();
				template = "PROPOSAL_TEMP";
			}

			Map<String, Object> map = new HashMap<>();
			map.put("logoImagePath", "https://www.resume9.co.kr/nuxtfiles/img/n_logo_03.f09a7d2.png");
			map.put("content", message);
			map.put("proposalUrl", WEB_DOMAIN + proposalUrl); // 제안 URL
			map.put("mailRejectUrl", WEB_DOMAIN + "/"); // 수신 거부 URL
			map.put("mailSupportUrl", WEB_DOMAIN + "/"); // 도움 URL
			map.put("subject", hhPositionDTO.getSubject());

			MailUtil.sendTemplateEmail(MailDTO.builder().content(message).fromEmail("resume9@resume9.co.kr").receiveEmail(Arrays.asList(h.getEmail()))
					.subject(hhPositionDTO.getSubject()).template(template).templateData(map).build());

			// 발송이력 저장
			HhSendMsgDTO hh = new HhSendMsgDTO();
			hh.setContent(message);
			hh.setKindCd(template);
			hh.setMedia("MAIL");
			hh.setSubject(hhPositionDTO.getSubject());
			hh.setMemberId(hhPositionDTO.getFrontSession().getId());
			hhSendMsgMapper.insertHhSendMsg(hh);

			// 발송대상 저장
			hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder().msgId(hh.getId()).isRead("0").name(h.getName())
					.success("WAITING").target(h.getEmail()).val(WEB_DOMAIN + "/fo/user/application/job-position").build());

			// 포지션 제안상태 업데이트
			h.setProposedEamil("1"); // 이메일 제안
			h.setProposalStatus("PROPOSAL"); // 제안상태
			h.setProgressStep("PROPOSAL");
			hhPositionMapper.updateProposalStatus(h);
			hhPositionMapper.updateProgressStep(h);
		}
	}

	@Transactional
	public void updateOpenInfo(HhPositionDTO hhPositionDTO) {
		hhPositionDTO.setPhoneOpened("1");
		hhPositionMapper.updatePositionApplicant(hhPositionDTO);
	}

	@Transactional
	public void updateCompanyRecommend(HhPositionDTO hhPositionDTO) {
		// 고객 담당자 이메일 조회
		List<HhPositionDTO> companyManagerList = hhPositionMapper.selectCompanyHrManagerList(hhPositionDTO);

		String encodedPassword = bCryptPasswordEncoder.encode(hhPositionDTO.getPassword());
		hhPositionDTO.setPassword(encodedPassword);
		hhPositionMapper.insertRecommendEmail(hhPositionDTO);

		for (HhPositionDTO h : companyManagerList) {
			h.setPositionId(hhPositionDTO.getPositionId());

			Map<String, Object> map = new HashMap<>();
			map.put("logoImagePath", "https://www.resume9.co.kr/nuxtfiles/img/n_logo_03.f09a7d2.png");
			map.put("content", hhPositionDTO.getContent());
			map.put("companyUrl",
					WEB_DOMAIN + "/m/company/list?positionId=" + hhPositionDTO.getPositionId() + "&recommendId=" + hhPositionDTO.getRecommendId()); // 추천
																																					// URL
			map.put("mailRejectUrl", WEB_DOMAIN + "/"); // 수신 거부 URL
			map.put("mailSupportUrl", WEB_DOMAIN + "/"); // 도움 URL
			map.put("subject", hhPositionDTO.getSubject());

			// 메일 발송
			MailUtil.sendTemplateEmail(MailDTO.builder().content(hhPositionDTO.getContent()).fromEmail("resume9@resume9.co.kr")
					.receiveEmail(Arrays.asList(h.getEmail())).subject(hhPositionDTO.getSubject()).template("COMPANY").templateData(map).build());

			// 발송이력 저장
			HhSendMsgDTO hh = new HhSendMsgDTO();
			hh.setContent(hhPositionDTO.getContent());
			hh.setKindCd("COMPANY");
			hh.setMedia("MAIL");
			hh.setSubject(hhPositionDTO.getSubject());
			hh.setMemberId(hhPositionDTO.getFrontSession().getId());
			hhSendMsgMapper.insertHhSendMsg(hh);

			// 발송대상 저장
			hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder().msgId(hh.getId()).isRead("0").name(h.getName())
					.success("WAITING").target(h.getEmail()).val(WEB_DOMAIN + "/m/company/list?positionId=" + hhPositionDTO.getPositionId()
							+ "&recommendId=" + hhPositionDTO.getRecommendId())
					.build());

		}

		// 기업추천 상태 업데이트
		for (HhPositionDTO h : hhPositionDTO.getIdList()) {
			h.setReceiptStatus("RECOMMENDED");
			h.setProgressStep("PROCESS");
			hhPositionMapper.updatePositionApplicant(h);
		}
	}

	@Transactional
	public void updatePmSubmit(HhPositionDTO hhPositionDTO) {
		for (HhPositionDTO h : hhPositionDTO.getIdList()) {
			h.setPmSubmitted("1");
			h.setReceiptStatus("UNOPENED");
			h.setReceiptPath("CO");
			hhPositionMapper.updatePositionApplicant(h);
		}
	}

	@Transactional
	public void insertPositionRecommend(HhPositionDTO hhPositionDTO) {

		// 이력서 조회
		FoSessionDTO session = new FoSessionDTO();
		session.setId(hhPositionDTO.getApMemberId());
		FoResumeDTO foResumeDTO = new FoResumeDTO();
		foResumeDTO.setFrontSession(session);
		List<FoResumeDTO> resumeList = foResumeMapper.selectResumeList(foResumeDTO);

		// 포지션 추천
		FoPositionApplicantDTO foPositionApplicantDTO = new FoPositionApplicantDTO();
		foPositionApplicantDTO.setPhoneOpened("0");
		foPositionApplicantDTO.setPmSubmitted("0");
		foPositionApplicantDTO.setProgressStep("CONTACT");
		foPositionApplicantDTO.setProposalStatus("UNDER_REVIEW");
		foPositionApplicantDTO.setProposedEamil("0");
		foPositionApplicantDTO.setProposedSms("0");
		foPositionApplicantDTO.setRegPath("1");
		foPositionApplicantDTO.setUpdateRequest("0");
		foPositionApplicantDTO.setApMemberId(hhPositionDTO.getApMemberId());
		foPositionApplicantDTO.setResumeId(resumeList.get(0).getId());
		foPositionApplicantDTO.setHhMemberId(hhPositionDTO.getHhMemberId());
		foPositionApplicantDTO.setPositionId(hhPositionDTO.getPositionId());

		foPositionMapper.insertPositionApplicant(foPositionApplicantDTO);
	}

	public boolean selectRecommendDuplicate(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectRecommendDuplicate(hhPositionDTO);
	}

	public void uploadFiles(HhPositionDTO hhPositionDTO) throws Exception {
		Integer fileSeq = commonFileService.fileUpload(hhPositionDTO.getFiles(), "RECOMMEND");
		hhPositionDTO.setSubmitResumeFileId(String.valueOf(fileSeq));
		hhPositionMapper.updatePositionApplicant(hhPositionDTO);
	}

	public void updateFinalPass(HhPositionDTO hhPositionDTO) {
		hhPositionDTO.setProgressStep("PASS");
		hhPositionDTO.setProcessStatus("PASS");
		hhPositionDTO.setPassedStatus("PASS");
		hhPositionMapper.updatePositionApplicant(hhPositionDTO);
	}

	public void updatePositionApplicant(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updatePositionApplicant(hhPositionDTO);
	}

	public void updatePositionDelete(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updatePositionDelete(hhPositionDTO);
	}

	public void insertCoworkJoin(HhPositionDTO hhPositionDTO) {
		hhPositionDTO.setMemberId(hhPositionDTO.getFrontSession().getId());
		hhPositionMapper.insertPositionParticipant(hhPositionDTO);
	}

	public void updateShowHist(HhPositionDTO hhPositionDTO) {
		hhPositionMapper.updateShowHist(hhPositionDTO);
	}

	public HhPositionDTO selectPositionCompanyInfo(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectPositionCompanyInfo(hhPositionDTO);
	}

	public List<HhPositionDTO> selectRemainNotFail(HhPositionDTO hhPositionDTO) {
		return hhPositionMapper.selectRemainNotFail(hhPositionDTO);
	}

	public void updateRemainFail(HhPositionDTO hhPositionDTO) {
		List<HhPositionDTO> remainList = hhPositionMapper.selectRemainNotFail(hhPositionDTO);
		for(HhPositionDTO h : remainList) {
			HhPositionDTO remainDTO = new HhPositionDTO();
			remainDTO.setId(h.getId());
			remainDTO.setProgressStep("FAIL");
			hhPositionMapper.updateRemainFail(remainDTO);
		}
	}
}
