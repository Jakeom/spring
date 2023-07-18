package com.fw.bo.management.operation.service;

import com.fw.core.dto.MailDTO;
import com.fw.core.dto.bo.BoMemberDTO;
import com.fw.core.dto.fo.MsgDTO;
import com.fw.core.mapper.db1.bo.BoMemberMapper;
import com.fw.core.util.MailUtil;
import com.fw.core.util.SmsUtil;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationSmsService {
    private final BoMemberMapper boMemberMapper;
    /**
     * SMS 전송
     */
    public void sendSms(BoMemberDTO boMemberDTO, MsgDTO msgDTO) {
        List<String> list  = boMemberDTO.getIdList();

        String msgTypeCheck;
        if(msgDTO.getMessage().length() > 60) {
            msgTypeCheck = "LMS";
        } else {
            msgTypeCheck = "SMS";
        }

        if(list != null) {
            for(int i =0 ; i < list.size() ; i++ ){
                boMemberDTO.setId(list.get(i));
                BoMemberDTO newData = boMemberMapper.selectMemberInfo(boMemberDTO);
                SmsUtil.sendMessage(MsgDTO.builder()
                        .subject(msgDTO.getSubject())
                        .type(msgTypeCheck)
                        .to(newData.getPhone())
                        .message(msgDTO.getMessage())
                        .build());
            }
        }

        if(boMemberDTO.getNonMemberList() != null) {
            for(int i =0 ; i < boMemberDTO.getNonMemberList().size() ; i++ ){
                SmsUtil.sendMessage(MsgDTO.builder()
                        .subject(msgDTO.getSubject())
                        .type(msgTypeCheck)
                        .to(boMemberDTO.getNonMemberList().get(i))
                        .message(msgDTO.getMessage())
                        .build());
            }
        }
    }
    /**
     * 이메일 전송
     */
    public void sendEmail(BoMemberDTO boMemberDTO, MailDTO mailDTO) {
        List<String> list  = boMemberDTO.getIdList();
        if(list != null) {
            for (int i = 0; i < list.size(); i++) {
                boMemberDTO.setId(list.get(i));
                BoMemberDTO newData = boMemberMapper.selectMemberInfo(boMemberDTO);
                MailUtil.sendEmail(MailDTO.builder()
                        .fromEmail("resume9@resume9.co.kr")
                        .receiveEmail(Collections.singletonList((newData.getEmail())))
                        .subject(mailDTO.getSubject())
                        .content(mailDTO.getContent())
                        .template("certCode")
                        .build());
            }
        }
        System.out.println(boMemberDTO.getNonMemberList());
        if(boMemberDTO.getNonMemberList() != null) {
            for (int i = 0; i < boMemberDTO.getNonMemberList().size(); i++) {
                MailUtil.sendEmail(MailDTO.builder()
                        .fromEmail("resume9@resume9.co.kr")
                        .receiveEmail(Collections.singletonList(boMemberDTO.getNonMemberList().get(i)))
                        .subject(mailDTO.getSubject())
                        .content(mailDTO.getContent())
                        .template("certCode")
                        .build());
            }
        }
    }
}
