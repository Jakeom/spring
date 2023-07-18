package com.fw.hh.myaddress.service;


import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.MailDTO;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.MsgDTO;
import com.fw.core.dto.hh.HhContactsDTO;
import com.fw.core.dto.hh.HhContactsGroupDTO;
import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhSendMsgDTO;
import com.fw.core.mapper.db1.hh.HhAddressMapper;
import com.fw.core.mapper.db1.hh.HhMyApMapper;
import com.fw.core.mapper.db1.hh.HhSendMsgMapper;
import com.fw.core.util.SmsUtil;
import com.fw.core.util.SmtpMailUtil;
import com.fw.m.resume.service.MResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhAddressService {

    @Value("${service.web_domain}")
	private String WEB_DOMAIN;
	@Value("${service.mail_path}")
	private String FILE_MAIL_PATH;
	private final HhMyApMapper hhMyApMapper;
    private final HhAddressMapper  hhAddressMapper; 
    private final HhSendMsgMapper hhSendMsgMapper;
    private final MResumeService mResumeService;
	private final CommonFileService commonFileService;

    public int selectHhContactsListCount(HhContactsDTO hhContactsDTO){
        return hhAddressMapper.selectHhContactsListCount(hhContactsDTO);
    }
    public List<HhContactsDTO> selectHhContactsList(HhContactsDTO hhContactsDTO){
        return hhAddressMapper.selectHhContactsList(hhContactsDTO);
    }

    public int insertContactgroup(HhContactsGroupDTO hhContactsGroupDTO){
        return hhAddressMapper.insertContactgroup(hhContactsGroupDTO);
    }

    public int insertContact(HhContactsDTO hhContactsDTO){
        return hhAddressMapper.insertContact(hhContactsDTO);
    }

    public int updateContact(HhContactsDTO hhContactsDTO){
        return hhAddressMapper.updateContact(hhContactsDTO);
    }

    public void updateContactgroup(HhContactsGroupDTO hhContactsGroupDTO){
        hhAddressMapper.updateContactgroup(hhContactsGroupDTO);
    }

    public List<HhContactsGroupDTO> selectContractGroup(HhContactsGroupDTO hhContactsGroupDTO){
        return hhAddressMapper.selectContractGroup(hhContactsGroupDTO);
    }

    @Transactional
    public void updateSendMail(HhContactsDTO hhContactsDTO) {

		for(HhContactsDTO h : hhContactsDTO.getTargetList()) {
			String message = hhContactsDTO.getContent();

			Map<String, Object> map = new HashMap<>();
			map.put("logoImagePath", "https://www.resume9.co.kr/nuxtfiles/img/n_logo_03.f09a7d2.png");
			map.put("content", message);
			map.put("proposalUrl", WEB_DOMAIN + "/fo/user/application/job-position");    // 제안 URL
			map.put("mailRejectUrl", WEB_DOMAIN + "/");                // 수신 거부 URL
			map.put("mailSupportUrl", WEB_DOMAIN + "/");            // 도움 URL
			map.put("subject", hhContactsDTO.getSubject());

			SmtpMailUtil.sendMail(MailDTO.builder()
						.content(message)
						.toEmailList(Collections.singletonList(h.getEmail()))
						.receiveEmail(Collections.singletonList("recruiter@resume9.co.kr"))
						.subject(hhContactsDTO.getSubject())
					    .replyToEmail(hhContactsDTO.getFrontSession().getEmail())
						.useHtmlYn(true)
						.build());

			// 발송이력 저장
			HhSendMsgDTO hh = new HhSendMsgDTO();
			hh.setContent(message);
			hh.setKindCd("ADDRESS");
			hh.setMedia("MAIL");
			hh.setSubject(hhContactsDTO.getSubject());
			hh.setMemberId(hhContactsDTO.getFrontSession().getId());
			hhSendMsgMapper.insertHhSendMsg(hh);

			//발송대상 저장
			hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder()
					.msgId(hh.getId())
					.isRead("0")
					.name(h.getName())
					.success("WAITING")
					.target(h.getEmail())
					.val(WEB_DOMAIN + "/fo/user/application/job-position")
					.build());
		}
    }

    @Transactional
    public String updateSendSms(HhContactsDTO hhContactsDTO){
        HhPointDTO reqHhPointDTO = new HhPointDTO();
        reqHhPointDTO.setFrontSession(hhContactsDTO.getFrontSession());
        HhPointDTO pointInfo = hhMyApMapper.selectPoint(reqHhPointDTO);
		if(pointInfo == null){
			return "NO_POINT";
		}

		int decreasePoint = 0;
		int num = hhContactsDTO.getTargetList().size();

		// SMS/LMS 종류에 따른 차감포인트
		String type = "";
		if(StringUtils.equals(hhContactsDTO.getType(), "SMS")) {
			decreasePoint = 20 * num;
			type = "USE_SEND_SMS";
		} else if (StringUtils.equals(hhContactsDTO.getType(), "LMS")) {
			decreasePoint = 60 * num;
			type = "USE_SEND_LMS";
		}

		// 보유포인트보다 차감포인트가 작으면 문자 발송하지 않음
		if(Integer.parseInt(pointInfo.getBalance()) < decreasePoint) {
			return "NO_POINT";
		}

		for(HhContactsDTO h : hhContactsDTO.getTargetList()) {
			MsgDTO msgDTO = new MsgDTO();
			msgDTO.setTo(h.getPhone());
            msgDTO.setType(hhContactsDTO.getType());
            msgDTO.setMessage(hhContactsDTO.getContent());
            msgDTO.setSubject(hhContactsDTO.getSubject());

			SmsUtil.sendMessage(msgDTO);

			// 발송이력
			HhSendMsgDTO hh = new HhSendMsgDTO();
			hh.setContent(msgDTO.getMessage());
			hh.setKindCd("GENERAL");
			hh.setMedia(hhContactsDTO.getType());
			hh.setSubject(msgDTO.getSubject());
			hh.setMemberId(hhContactsDTO.getFrontSession().getId());
			hhSendMsgMapper.insertHhSendMsg(hh);

			//발송대상 저장
			hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder()
					.msgId(hh.getId())
					.isRead("0")
					.name(h.getName())
					.success("WAITING")
					.target(h.getPhone())
					.build());
		}

		// 포인트 차감
		mResumeService.updatePoint(FoPointDTO.builder()
				.type(type)
				.amount(decreasePoint)
				.memberId(hhContactsDTO.getFrontSession().getId()).build());
		return "SUCCESS";
    }
     
    
}
