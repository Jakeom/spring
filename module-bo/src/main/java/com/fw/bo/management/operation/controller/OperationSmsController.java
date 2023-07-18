package com.fw.bo.management.operation.controller;

import com.fw.bo.management.operation.service.OperationSmsService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.MailDTO;
import com.fw.core.dto.bo.BoMemberDTO;
import com.fw.core.dto.fo.MsgDTO;
import com.fw.core.mapper.db1.bo.BoMemberMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.fw.bo.system.homepage.service.HomepageQnaService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoQnaDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.List;

/**
 *SMS Controller
 *
 * @author Ghazal
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationSmsController {

	private final OperationSmsService smsService;
	private final BoMemberMapper boMemberMapper;

	/**
	 * SMS 관리 페이지
	 */
	@GetMapping("/bo/management/operation/sms")
	public String qna(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/bo/management/operation/sms";
	}

	/**
	 * SMS 전송
	 */
	@GetMapping("/bo/management/operation/sms/send")
	public void sendSms(BoMemberDTO boMemberDTO , MsgDTO msgDTO, HttpServletRequest request, HttpServletResponse response) {
		boMemberDTO.setIdList(boMemberDTO.getIdList());
		msgDTO.setSubject(msgDTO.getSubject());
		msgDTO.setMessage(msgDTO.getMessage());
		smsService.sendSms(boMemberDTO, msgDTO);
	}
	/**
	 * 이메일 전송
	 */
	@GetMapping("/bo/management/operation/email/send")
	public void sendEmail(BoMemberDTO boMemberDTO, MailDTO mailDTO, HttpServletRequest request, HttpServletResponse response) {
		boMemberDTO.setIdList(boMemberDTO.getIdList());
		mailDTO.setSubject(mailDTO.getSubject());
		mailDTO.setContent(mailDTO.getContent());
		smsService.sendEmail(boMemberDTO, mailDTO);
	}

}
