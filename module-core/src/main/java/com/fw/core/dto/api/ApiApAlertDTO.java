package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fw.core.dto.CommonDTO;
import lombok.*;

import java.util.List;

/**
 * ApiApAlertDTO
 * 
 * @author WSH
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiApAlertDTO extends CommonDTO {

	private String ap_alert_seq; // 맞춤 인재 알림 순서
	private String apAlertSeq; // 맞춤 인재 알림 순서
	private String industry; // 핵심역량
	private String careerSt; // 경력시작
	private String careerEn; // 경력종료
	private String ageSt; // 연령시작
	private String ageEn; // 연령종료
	private String salarySt; // 연봉시작
	private String salaryEn; // 연봉종료
	private String hp1; // 학력1
	@JsonIgnore
	private String hp2String;
	private String[] hp2; // 학력2
	private String company; // 출신회사
	private String foreignLang; // 외국어
	private String certificate; // 자격증
	private String[] loc; // 희망근무지
	@JsonIgnore
	private String locString;
	private String memberId; // 회원 일련번호
	private String member_id; // 회원 일련번호
	private String delFlag; // 삭제여부
	private String regSeq; // 생성번호
	private String regDate; // 생성일시
	private String searchVal; // 검색
	// ApiPositionAlertDTO

	private String positionAlertSeq; // 맞춤 채용 공고 순서

	// parameter
	// private String memberId; // 회원 일련번호
	// private String careerSt; // 경력시작
	// private String careerEn; // 경력종료
	// private String ageSt; // 연령시작
	// private String ageEn; // 연령종료
	// private String salarySt; // 연봉시작
	// private String salaryEn; // 연봉종료
	// private String foreignLang; // 외국어

	// member
	private String hhResuFlag; // 헤드헌터 이력서 접수 알림여부
	private String hhMsgFlag; // 헤드헌터 메시지 알림여부
	private String hhApMsgFlag; // 헤드헌터 후보자 메시지 알림여부
	private String hhCommFlag; // 헤드헌터 커뮤니티 알림여부
	private String hhApFlag; // 헤드헌터 맞춤인재 알림여부
	private String apAlarmFlag; // 후보자 메시지 알림여부
	private String apCommFlag; // 후보자 커뮤니티 알림여부
	private String apPosiFlag; // 후보자 맞춤채용공고 알림여부
	private String agreeMarketing; // 마케팅정보수신제공동의
	private String agreeMarketingAt; // 마케팅정보수신제공여부 변경날짜

	private List<ApiApAlertDTO> industryList;		// 맞춤 채용공고 리스트

}
