package com.fw.hh.mypage.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.hh.HhMyAlarmDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.mypage.service.HhMyAlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

/**
 * 마이페이지 Controller
 *
 * @author wsh
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HhMyAlarmController {

	private final HhMyAlarmService hhMyAlarmService;
	private final CommonService commonService;

	/**
	 * front HH 마이페이지-자주쓰는 메일/SMS
	 */
	@GetMapping("/hh/mypage/frequently-used-message")
	public String MypageFrequentlyUsedMessage(ModelMap model, HhMyAlarmDTO hhMyAlarmDTO, CommonDTO commonDTO, HttpServletRequest request, HttpServletResponse response) {
		commonDTO.setMemberId(commonDTO.getFrontSession().getId());
		commonDTO.setMediaType("SMS");
		model.addAttribute("smsTemplateList", commonService.selectTemplateList(commonDTO));
		commonDTO.setMediaType("EMAIL");
		model.addAttribute("emailTemplateList", commonService.selectTemplateList(commonDTO));
		return "hh/mypage/frequently-used-message";
	}

	/**
	 * front HH 마이페이지-발송한 메일/SMS내역
	 */
	@GetMapping("/hh/mypage/mail-history")
	public String MypageMailHistory(ModelMap model, HhMyAlarmDTO hhMyAlarmDTO) {
		// Mail
		hhMyAlarmDTO.setMediaList(Collections.singletonList("MAIL"));
		model.addAttribute("mailList", hhMyAlarmService.selectSendMediaList(hhMyAlarmDTO));

		// Message
		hhMyAlarmDTO.setMediaList(Arrays.asList("SMS", "MMS", "LMS"));
		model.addAttribute("msgList", hhMyAlarmService.selectSendMediaList(hhMyAlarmDTO));

		hhMyAlarmDTO.setTotalCount(hhMyAlarmService.selectSendMediaListCount(hhMyAlarmDTO));
		model.addAttribute("searchInfo", hhMyAlarmDTO);
		return "hh/mypage/mail-history";
	}

	/**
	 * front HH 마이페이지-발송한 메일
	 */
	@GetMapping("/hh/mypage/history/mail")
	public String MyPageHistoryMail(ModelMap model, HhMyAlarmDTO hhMyAlarmDTO) {
		// Mail
		hhMyAlarmDTO.setMediaList(Collections.singletonList("MAIL"));
		hhMyAlarmDTO.setTotalCount(hhMyAlarmService.selectSendMediaListCount(hhMyAlarmDTO));
		model.addAttribute("mailList", hhMyAlarmService.selectSendMediaList(hhMyAlarmDTO));
		model.addAttribute("searchInfo", hhMyAlarmDTO);
		return "hh/mypage/history/mail";
	}

	/**
	 * front HH SMS내역
	 */
	@GetMapping("/hh/mypage/history/sms")
	public String MyPageHistorySms(ModelMap model, HhMyAlarmDTO hhMyAlarmDTO) {
		// Message
		hhMyAlarmDTO.setMediaList(Arrays.asList("SMS", "MMS", "LMS"));
		hhMyAlarmDTO.setTotalCount(hhMyAlarmService.selectSendMediaListCount(hhMyAlarmDTO));
		model.addAttribute("msgList", hhMyAlarmService.selectSendMediaList(hhMyAlarmDTO));
		model.addAttribute("searchInfo", hhMyAlarmDTO);
		return "hh/mypage/history/sms";
	}

	/**
	 * 메일상세
	 */
	@GetMapping("/hh/mypage/history/mail-info")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectMailInfo(HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		hhMyAlarmDTO.setMediaList(Collections.singletonList("MAIL"));
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhMyAlarmService.selectMailInfo(hhMyAlarmDTO)).build());
	}

	/**
	 * 메일
	 */
	@PostMapping("/hh/mypage/frequently-used-message/delete/template")
	public ResponseEntity<ResponseVO> deleteTemplate(HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			log.info("{}", hhMyAlarmDTO.getDeleteTemplateList());
			hhMyAlarmService.deleteSmsTemplate(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 발송한 메일 삭제
	 */
	@PostMapping("/hh/mypage/delete-send-mail")
	public ResponseEntity<ResponseVO> deleteSendMail(@RequestBody HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmService.deleteSendMail(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 발송한 메세지 삭제
	 */
	@PostMapping("/hh/mypage/delete-send-message")
	public ResponseEntity<ResponseVO> deleteSendMessage(@RequestBody HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmService.deleteSendMessage(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 메일 재발송
	 */
	@PostMapping("/hh/mypage/resend-mail")
	public ResponseEntity<ResponseVO> resendMail(@RequestBody HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmService.insertResendMail(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 메세지 재발송
	 */
	@PostMapping("/hh/mypage/resend-message")
	public ResponseEntity<ResponseVO> resendMessage(@RequestBody HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmService.insertResendMessage(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 그룹관리 정보 삭제
	 */
	@PostMapping("/hh/mypage/frequently-used-message/modify")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertEmail(@RequestPart(value = "jsonData")
														  HhMyAlarmDTO hhMyAlarmDTO,
															@RequestPart(value = "emailFiles", required = false) MultipartFile[] emailFiles
															) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmDTO.setEmailFiles(emailFiles);
			hhMyAlarmService.updateSmsTemplate(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	@PostMapping("/hh/mypage/frequently-used-message/modify/sms")
	@ResponseBody
	public ResponseEntity<ResponseVO> insertSms(HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmService.updateSmsTemplateSms(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}



	@PostMapping("/hh/mypage/frequently-used-message/file/detail")
	public ResponseEntity<ResponseVO> selectDetailFile(HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhMyAlarmService.selectTemplateFileList(hhMyAlarmDTO)).build());
	}

	@PostMapping("/hh/mypage/frequently-used-message/create/template/sms")
	public ResponseEntity<ResponseVO> insertSmsTemplate(HhMyAlarmDTO hhMyAlarmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmDTO.setMemberId(hhMyAlarmDTO.getFrontSession().getId());
			hhMyAlarmService.insertTemplate(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	@PostMapping("/hh/mypage/frequently-used-message/create/template/email")
	public ResponseEntity<ResponseVO> insertEmailTemplate(@RequestPart(value = "jsonData")
															  HhMyAlarmDTO hhMyAlarmDTO,
														  @RequestPart(value = "emailFiles", required = false) MultipartFile[] emailFiles
	) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyAlarmDTO.setEmailFiles(emailFiles);
			hhMyAlarmDTO.setMemberId(hhMyAlarmDTO.getFrontSession().getId());
			hhMyAlarmService.insertEmailTemplate(hhMyAlarmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

}