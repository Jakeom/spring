package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhApAlertDTO extends CommonDTO implements Serializable {
    
	private String apAlertSeq; // 맞춤 인재 알림 일련번호
	private String industry; // 핵심역량
	private Integer careerSt; // 경력시작
	private Integer careerEn; // 경력종료
	private Integer ageSt; // 연령시작
	private Integer ageEn; // 연령종료
	private Integer salarySt; // 연봉시작
	private Integer salaryEn; // 연봉종료
	private String hp1; // 학력1
	private String hp2; // 학력2
	private String company; // 출신회사
	private String foreignLang; // 외국어
	private String certificate; // 자격증
	private String workplace; // 희망근무지
	private String memberId; // 회원 일련번호
}
