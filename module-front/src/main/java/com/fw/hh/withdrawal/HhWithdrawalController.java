package com.fw.hh.withdrawal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.login.service.FoMemberService;
import com.fw.fo.mypage.service.FoMyPageChangePasswordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원탈퇴
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HhWithdrawalController {

	private final FoMyPageChangePasswordService foMyPageChangePasswordService;
	private final FoMemberService foMemberService;

	private final BCryptPasswordEncoder pwEncoder;

	/**
	 * 탈퇴
	 */
	@PostMapping("/hh/withdrawal")
	@ResponseBody
	public ResponseEntity<ResponseVO> changePassword(@RequestBody FoMemberDTO foMemberDTO, HttpServletRequest request) {
		ResponseCode code = ResponseCode.SUCCESS;

		String password = foMemberDTO.getPassword(); // 기존 비밀번호

		try {
			FoMemberDTO member = foMyPageChangePasswordService.selectMemberPassword(foMemberDTO);
			if (!pwEncoder.matches(password, member.getPassword())) {
				throw new BadCredentialsException("");
			}
			foMemberService.updateDeleteMember(foMemberDTO);
		} catch (Exception e) {
			code = ResponseCode.INTERNAL_SERVER_ERROR;
			log.error("error", e);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

}