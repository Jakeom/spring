package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhNoticeSettingDTO extends CommonDTO {

    // 맞춤인재알림 -- tb_ap_alert
	private String apAlertSeq;		// 맞춤인재 알림순서
	private String industry;		// 핵심역량
	private String careerSt;		// 경력시작
	private String careerEn;		// 경력종료
	private String ageSt;			// 연령시작
	private String ageEn;			// 연령종료
	private String salarySt;		// 연봉시작
	private String salaryEn;		// 연봉종료
	private String hp1;				// 학력1
	private String company;			// 출신회사
	private String foreignLang;		// 외국어
	private String certificate;		// 자격증
	private String memberId;		// 회원일련번호
	private String delFlag;			// 삭제여부
    private String regSeq;			// 생성번호
    private String regDate;			// 생성일시

    // 맞춤인재학력 -- tb_ap_hp
    private String apHpSeq;			// 맞춤인재 학력순서
    private String hp2;				// 학력2
	private String[] hp2List;

    // 맞춤인재 희망근무지 -- tb_ap_loc
    private String apLocSeq;		// 맞춤인재 희망근무지순서
    private String loc;				// 희망근무지
	private String[] locList;
    // 회원 -- member
	private String hhApFlag;		// 맞춤인재알림여부

}
