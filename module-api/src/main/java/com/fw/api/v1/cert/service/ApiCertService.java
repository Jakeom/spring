package com.fw.api.v1.cert.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.fw.api.system.security.AES256Provider;
import com.fw.api.v1.member.service.ApiMemberService;
import com.fw.core.dto.MailDTO;
import com.fw.core.dto.api.ApiMemberDTO;
import com.fw.core.dto.api.ApiTokenDTO;
import com.fw.core.mapper.db1.api.ApiCertMapper;
import com.fw.core.util.DateUtil;
import com.fw.core.util.MailUtil;
import com.fw.core.util.RandomUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author j.s.ko
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiCertService {

	private final ApiMemberService memberService;
	private final AES256Provider aes256Provider;

	private final ApiCertMapper apiCertMapper;

	public String requestToken(ApiMemberDTO apiMemberDTO) {
		String result = "";

		ApiMemberDTO apiMember = new ApiMemberDTO();
		apiMember.setMemberId(apiMemberDTO.getMemberId());
		apiMember.setSimpleAuthCd("AUTO");
		List<ApiMemberDTO> sAuthList = memberService.selectMemeberSimpleAuth(apiMember);
		if (sAuthList != null && sAuthList.size() > 0) {
			// NOTI 이전토큰 삭제처리
			memberService.updateMemberSimpleAuthDel(apiMember);
		}
		// NOTI 토큰발행(30일짜리)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		final String targetStr = LocalDateTime.now().plusDays(30).format(formatter) + "|" + apiMemberDTO.getPhoneVal();
		result = aes256Provider.encryptStr(targetStr);
		apiMember.setSimpleAuthVal(result);
		apiMember.setDelFlag("N");
		memberService.insertMemberSimpleAuth(apiMember);

		return result;
	}

	public LocalDateTime getExpirationTime(String token) {
		LocalDateTime result = null;

		final String decrypted = aes256Provider.decryptStr(token);
		if (decrypted.length() == 0)
			return null;

		String[] tokens = decrypted.split("\\|");
		if (tokens.length > 0) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			result = LocalDateTime.parse(tokens[0], formatter);
		}

		return result;
	}

	public void insertToken(ApiTokenDTO apiTokenDTO) {
		String token = RandomUtil.createRandomText(6); // 난수생성
		apiTokenDTO.setToken(token);
		apiCertMapper.insertToken(apiTokenDTO);

		if (StringUtils.equals("SMS", apiTokenDTO.getMediaType())) {

		} else if (StringUtils.equals("EMAIL", apiTokenDTO.getMediaType())) {
			Map<String, Object> map = new HashMap<>();
			map.put("certCode", token);
			MailUtil.sendTemplateEmail(MailDTO.builder().fromEmail("resume9@resume9.co.kr").receiveEmail(Arrays.asList(apiTokenDTO.getAuth()))
					.template("certCode").templateData(map).build());
		}

		log.info("Verify Code : {}", token);
	}

	public ApiTokenDTO selectToken(ApiTokenDTO apiTokenDTO) {
		ApiTokenDTO resDTO = apiCertMapper.selectToken(apiTokenDTO);

		String serviceCode = "N";
		String serviceMessage = null;

		// 인증코드 검증은 4가지 경우가 있음 - 정상, 잘못된 인증코드, 인증 시간 만료, 이미 사용한 코드
		if (!Objects.isNull(resDTO)) {
			if (DateUtil.isDtAfter(resDTO.getExpiredAt(), DateUtil.getDtStrNow("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")) {
				if (StringUtils.equals("1", resDTO.getIsUsed())) {
					serviceMessage = "이미 사용한 코드입니다.";
				} else {
					this.updateToken(apiTokenDTO);
					serviceCode = "Y";
					serviceMessage = "인증이 완료되었습니다.";
				}
			} else {
				serviceMessage = "인증시간이 만료되었습니다.";
			}
		} else {
			resDTO = new ApiTokenDTO();
			serviceMessage = "인증코드가 잘못 되었습니다.";
		}

		resDTO.setServiceCode(serviceCode);
		resDTO.setServiceMessage(serviceMessage);

		return resDTO;
	}

	public void updateToken(ApiTokenDTO apiTokenDTO) {
		apiCertMapper.updateToken(apiTokenDTO);
	}
}
