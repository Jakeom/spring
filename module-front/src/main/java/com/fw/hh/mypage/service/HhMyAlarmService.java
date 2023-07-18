package com.fw.hh.mypage.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.MailDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.MsgDTO;
import com.fw.core.dto.hh.HhMyAlarmDTO;
import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhSendMsgDTO;
import com.fw.core.mapper.db1.hh.HhMyAlarmMapper;
import com.fw.core.mapper.db1.hh.HhMyApMapper;
import com.fw.core.mapper.db1.hh.HhSendMsgMapper;
import com.fw.core.util.MailUtil;
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
public class HhMyAlarmService {
    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    private final HhMyAlarmMapper hhMyAlarmMapper;
    private final CommonFileService commonFileService;
    private final HhSendMsgMapper hhSendMsgMapper;
    private final HhMyApMapper hhMyApMapper;
    private final MResumeService mResumeService;
    private final CommonService commonService;

    public int selectSendMediaListCount(HhMyAlarmDTO hhMyAlarmDTO) {
        return hhMyAlarmMapper.selectSendMediaListCount(hhMyAlarmDTO);
    }

    public HhMyAlarmDTO selectMailInfo(HhMyAlarmDTO hhMyAlarmDTO) {
        return hhMyAlarmMapper.selectSendMediaInfo(hhMyAlarmDTO);
    }

    public void deleteSendMail(HhMyAlarmDTO hhMyAlarmDTO) {
        for(int i = 0; i<hhMyAlarmDTO.getMailArr().length; i++) {
            hhMyAlarmDTO.setId(hhMyAlarmDTO.getMailArr()[i]);
            hhMyAlarmMapper.deleteSendMail(hhMyAlarmDTO);
        }
    }

    public void deleteSendMessage(HhMyAlarmDTO hhMyAlarmDTO) {
        for(int i = 0; i<hhMyAlarmDTO.getMsgArr().length; i++) {
            hhMyAlarmDTO.setId(hhMyAlarmDTO.getMsgArr()[i]);
            hhMyAlarmMapper.deleteSendMail(hhMyAlarmDTO);
        }
    }

    @Transactional
    public void insertResendMail(HhMyAlarmDTO hhMyAlarmDTO) {
        for(HhMyAlarmDTO h : hhMyAlarmDTO.getIdList()) {
            h.setMediaList(Collections.singletonList("MAIL"));
            h.setFrontSession(hhMyAlarmDTO.getFrontSession());
            HhMyAlarmDTO mailInfo = hhMyAlarmMapper.selectSendMediaInfo(h); // 메일 상세 조회

            Map<String, Object> map = new HashMap<>();
            map.put("logoImagePath", "https://www.resume9.co.kr/nuxtfiles/img/n_logo_03.f09a7d2.png");
            map.put("content", mailInfo.getContent());
            map.put("mailRejectUrl", WEB_DOMAIN + "/");
            map.put("mailSupportUrl", WEB_DOMAIN + "/");
            map.put("subject", mailInfo.getSubject());

            // 메일 발송 종류 PROPOSAL / ADDRESS / COMPANY
            if(StringUtils.equals(mailInfo.getKindCd(), "PROPOSAL")) { // CASE1 PROPOSAL
                map.put("proposalUrl", mailInfo.getVal());
            } else if(StringUtils.equals(mailInfo.getKindCd(),"TEMP_SIGNUP")) {
                FoMemberDTO foMemberDTO = new FoMemberDTO();
                foMemberDTO.setMemberId(mailInfo.getMemberId());
                FoMemberDTO memberInfo = commonService.selectMemberInfoByMemberId(foMemberDTO);

                map.put("mailImage1", WEB_DOMAIN + "/static/images/mail-img-1.png");
                map.put("mailImage2", WEB_DOMAIN + "/static/images/mail-img-2.png");
                map.put("mailImage3", WEB_DOMAIN + "/static/images/mail-img-3.png");
                map.put("mailImage4", WEB_DOMAIN + "/static/images/mail-img-4.png");
                map.put("mailImage5", WEB_DOMAIN + "/static/images/mail-img-5.png");
                map.put("mailImage6", WEB_DOMAIN + "/static/images/mail-img-6.png");
                map.put("apName", mailInfo.getName());
                map.put("apEmail", mailInfo.getTarget());
                map.put("mailRejectUrl", WEB_DOMAIN + "/"); // 수신 거부 URL
                map.put("mailSupportUrl", WEB_DOMAIN + "/"); // 도움 URL`
                map.put("hhName", memberInfo.getName());
                map.put("homeUrl", WEB_DOMAIN + "/");
                map.put("signupUrl", mailInfo.getVal()); // 임시회원가입 URL
            } else { // CASE2 NOT PROPOSAL
                map.put("companyUrl", mailInfo.getVal());
            }

            if(StringUtils.equals(mailInfo.getKindCd(),"ADDRESS")) {
                SmtpMailUtil.sendMail(MailDTO.builder()
                        .content(mailInfo.getContent())
                        .toEmailList(Collections.singletonList(mailInfo.getTarget()))
                        .receiveEmail(Collections.singletonList("recruiter@resume9.co.kr"))
                        .subject(mailInfo.getSubject())
                        .replyToEmail(hhMyAlarmDTO.getFrontSession().getEmail())
                        .useHtmlYn(true)
                        .build());
            } else {
                MailUtil.sendTemplateEmail(MailDTO.builder()
                        .content(mailInfo.getContent())
                        .fromEmail("resume9@resume9.co.kr")
                        .receiveEmail(Collections.singletonList(mailInfo.getTarget()))
                        .subject(mailInfo.getSubject())
                        .template(mailInfo.getKindCd())
                        .templateData(map)
                        .build());
            }
            // 발송이력 저장
            HhSendMsgDTO hh = new HhSendMsgDTO();
            hh.setContent(mailInfo.getContent());
            hh.setKindCd(mailInfo.getKindCd());
            hh.setMedia("MAIL");
            hh.setSubject(mailInfo.getSubject());
            hh.setMemberId(h.getFrontSession().getId());
            hhSendMsgMapper.insertHhSendMsg(hh);

            //발송대상 저장
            hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder()
                    .msgId(hh.getId())
                    .isRead("0")
                    .name(mailInfo.getName())
                    .success("WAITING")
                    .target(mailInfo.getTarget())
                    .val(mailInfo.getVal())
                    .build());
        }
    }

    @Transactional
    public String insertResendMessage(HhMyAlarmDTO hhMyAlarmDTO) {
        HhPointDTO hhPointDTO = new HhPointDTO();
        hhPointDTO.setFrontSession(hhMyAlarmDTO.getFrontSession());
        HhPointDTO pointInfo = hhMyApMapper.selectPoint(hhPointDTO);

        if(pointInfo == null) { // 포인트 정보 없을 경우 'NO_POINT'
            return "NO_POINT";
        }

        // script 총금액 계산값 decreasePoint & 내 포인트 총합 비교
        if(Integer.parseInt(pointInfo.getBalance()) < Integer.parseInt(hhMyAlarmDTO.getDecreasePoint())) {
            return "NO_POINT";
        }

        for(HhMyAlarmDTO h : hhMyAlarmDTO.getIdList()) {
            h.setMediaList(Arrays.asList("SMS","LMS"));
            h.setFrontSession(hhMyAlarmDTO.getFrontSession());
            HhMyAlarmDTO msgInfo = hhMyAlarmMapper.selectSendMediaInfo(h); // 메세지 상세 조회

            if(StringUtils.equals(msgInfo.getMedia(),"SMS")) { // 포인트 사용내역 SMS/LMS 구분
                hhMyAlarmDTO.setPointUseType("USE_SEND_SMS");
            } else {
                hhMyAlarmDTO.setPointUseType("USE_SEND_LMS");
            }

            SmsUtil.sendMessage(MsgDTO.builder() // SMS 재발송
                    .type(msgInfo.getMedia())
                    .from(h.getFrontSession().getEmail())
                    .to(msgInfo.getTarget())
                    .subject(msgInfo.getSubject())
                    .message(msgInfo.getContent())
                    .build());

            HhSendMsgDTO hh = new HhSendMsgDTO(); // 발송이력 저장
            hh.setContent(msgInfo.getContent());
            hh.setKindCd(msgInfo.getKindCd());
            hh.setMedia(msgInfo.getMedia());
            hh.setSubject(msgInfo.getSubject());
            hh.setMemberId(h.getFrontSession().getId());
            hhSendMsgMapper.insertHhSendMsg(hh);

            hhSendMsgMapper.insertHhSendMsgTarget(HhSendMsgDTO.builder() //발송대상 저장
                    .msgId(hh.getId())
                    .isRead("0")
                    .name(msgInfo.getName())
                    .success("WAITING")
                    .target(msgInfo.getTarget())
                    .build());
        }

        int decreasePoint = Integer.parseInt(hhMyAlarmDTO.getDecreasePoint());
        mResumeService.updatePoint(FoPointDTO.builder()
                .type(hhMyAlarmDTO.getPointUseType())
                .amount(decreasePoint)
                .memberId(hhMyAlarmDTO.getFrontSession().getId()).build());

        return "SUCCESS";
    }

    public List<FileDTO> selectTemplateFileList(HhMyAlarmDTO hhMyAlarmDTO) {
        hhMyAlarmDTO = hhMyAlarmMapper.selectEmailTemplateFileSeq(hhMyAlarmDTO);
        if(hhMyAlarmDTO != null) {
            List<FileDTO> list = commonFileService.selectFileDetailList(hhMyAlarmDTO.getTemplateFileId());
            return list;
        }
        return null;
    }

    public void deleteSmsTemplate(HhMyAlarmDTO hhMyAlarmDTO) {
    	log.info(hhMyAlarmDTO.getMediaTypeCd());
        if(hhMyAlarmDTO.getMediaTypeCd().equals("SMS")) {
            int size = hhMyAlarmDTO.getDeleteTemplateList().size();
            for(int i = 0; i < size; i++) {
                hhMyAlarmDTO.setId(hhMyAlarmDTO.getDeleteTemplateList().get(i));
                hhMyAlarmMapper.deleteSmsTemplate(hhMyAlarmDTO);
            }
        } else {
            HhMyAlarmDTO fileDTO;
            int size = hhMyAlarmDTO.getDeleteTemplateList().size();
            for(int i = 0; i < size; i++) {
                hhMyAlarmDTO.setId(hhMyAlarmDTO.getDeleteTemplateList().get(i));
                fileDTO = hhMyAlarmMapper.selectEmailTemplateFileSeq(hhMyAlarmDTO);
                if(fileDTO != null) {
                    commonFileService.updateFileDetail(fileDTO.getTemplateFileId());
                    hhMyAlarmMapper.updateTemplateFile(hhMyAlarmDTO);
                }
                hhMyAlarmMapper.deleteSmsTemplate(hhMyAlarmDTO);
            }
        }
    }

    public void insertTemplate(HhMyAlarmDTO hhMyAlarmDTO) {
        List<HhMyAlarmDTO> list = hhMyAlarmMapper.selectCountTemplateGroup(hhMyAlarmDTO);
        if(list.size() == 0) {
            hhMyAlarmMapper.insertTemplateGroup(hhMyAlarmDTO);
            hhMyAlarmDTO.setTemplateGroupId(hhMyAlarmDTO.getId());
            hhMyAlarmMapper.insertTemplateContent(hhMyAlarmDTO);
        } else {
            hhMyAlarmDTO.setTemplateGroupId(list.get(0).getId());
            hhMyAlarmMapper.insertTemplateContent(hhMyAlarmDTO);
        }
    }

    @Transactional
    public void insertEmailTemplate(HhMyAlarmDTO hhMyAlarmDTO) throws Exception {
        List<HhMyAlarmDTO> list = hhMyAlarmMapper.selectCountTemplateGroup(hhMyAlarmDTO);
        if(list.size() == 0) {
            hhMyAlarmMapper.insertTemplateGroup(hhMyAlarmDTO);
            hhMyAlarmDTO.setTemplateGroupId(hhMyAlarmDTO.getId());
            hhMyAlarmMapper.insertTemplateContent(hhMyAlarmDTO);
        } else {
            hhMyAlarmDTO.setTemplateGroupId(list.get(0).getId());
            hhMyAlarmMapper.insertTemplateContent(hhMyAlarmDTO);
        }
        hhMyAlarmDTO.setId(hhMyAlarmDTO.getTemplateContentId());
        Integer profileFileId = commonFileService.fileUpload(hhMyAlarmDTO.getEmailFiles(), "RESUME_PROFILE");
        if(profileFileId != null){
            hhMyAlarmDTO.setFileSeq(String.valueOf(profileFileId));
            hhMyAlarmMapper.insertTemplateEmailFile(hhMyAlarmDTO);
        }
    }

    @Transactional
    public void updateSmsTemplate(HhMyAlarmDTO hhMyAlarmDTO) throws Exception {
        if(hhMyAlarmDTO.getFileChangeCheck().equals("0")) {
            hhMyAlarmMapper.updateSmsTemplate(hhMyAlarmDTO);
        } else {
            if(hhMyAlarmMapper.selectEmailTemplateFileSeq(hhMyAlarmDTO) != null) {
                hhMyAlarmDTO.setTemplateFileId(hhMyAlarmMapper.selectEmailTemplateFileSeq(hhMyAlarmDTO).getTemplateFileId());
                commonFileService.updateFileDetail(hhMyAlarmDTO.getTemplateFileId());
            }
            if(hhMyAlarmDTO.getEmailFiles() == null) {
                hhMyAlarmMapper.updateSmsTemplate(hhMyAlarmDTO);
            } else {
                commonFileService.fileUpdate(hhMyAlarmDTO.getEmailFiles(), "RESUME_PROFILE", hhMyAlarmDTO.getTemplateFileId());
                hhMyAlarmMapper.updateSmsTemplate(hhMyAlarmDTO);
            }
        }
    }

    public void updateSmsTemplateSms(HhMyAlarmDTO hhMyAlarmDTO) {
        hhMyAlarmMapper.updateSmsTemplate(hhMyAlarmDTO);
    }

    public List<HhMyAlarmDTO> selectSendMediaList(HhMyAlarmDTO hhMyAlarmDTO) {
        List<HhMyAlarmDTO> list = hhMyAlarmMapper.selectSendMediaList(hhMyAlarmDTO);
        if(!Objects.isNull(list)) {
            for (HhMyAlarmDTO h : list) {
                String fileSeq = h.getFileId();
                if(StringUtils.isNotBlank(fileSeq)) {
                    h.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    public List<HhMyAlarmDTO> selectMailMsgHistory(HhMyAlarmDTO hhMyAlarmDTO) {
        List<HhMyAlarmDTO> list = hhMyAlarmMapper.selectMailMsgHistory(hhMyAlarmDTO);
        if(!Objects.isNull(list)) {
            for (HhMyAlarmDTO h : list) {
                String fileSeq = h.getFileId();
                if(StringUtils.isNotBlank(fileSeq)) {
                    h.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

}