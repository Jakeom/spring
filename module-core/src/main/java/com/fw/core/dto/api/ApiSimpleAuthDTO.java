package com.fw.core.dto.api;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * ApiSimpleAuthDTO
 *
 * @author WSH
 */
@Getter
@Setter
public class ApiSimpleAuthDTO extends CommonDTO {
		
	private String simple_auth_seq;				// 간편 인증 순서
	private String member_id;					// 회원 순서
	private String simple_auth_cd;				// 간편 인증 코드
	private String simple_auth_val;				// 간편 인증 값
	private String del_flag;					// 삭제 여부
	private String reg_flag;					// 생성 번호
	private String reg_date;					// 생성 일시

	// api parameter
	private String phoneVal;					// 자동인증용 기기 고유값
	private String memberId;					// 회원 순서
	private String simpleAuthCd;				// 간편 인증 코드(KAKAO,FACEBOOK,GOOGLE,NAVER,APPLE)
	private String simpleAuthVal;				// 간편 인증 값
}
