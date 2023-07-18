package com.fw.core.dto.bo;

import java.io.Serializable;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoRecruitDTO extends CommonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	/** 생성일시 */
	private String createdAt;
	/** 수정일시*/
	private String updatedAt;
	/** 삭제여부(종료공고 삭제기능 요건-미사용) */
	private String deleted;
	/** 마감일 */
	private String endDate;
	/** 기타사항 */
	private String etc;
	/** 계약 수수료율 */
	private String feeInfo;
	/** 직무명 */
	private String industry;
	/** cowork 여부 */
	private String isCowork;
	/** 선택공개여부 */
	private String isPrivate;
	/** AP용 JD */
	private String jopDescription;
	/** PM코멘트 */
	private String pmComment;
	/** 포지션상태코드 참조(1.진행2,마감3.중단) */
	private String status;
	/** 포지션종료사유코드 참조(1.채용완료2.채용사의 계획 변경3.기타) */
	private String stopReason;
	/** 제목 */
	private String title;
	/** 내 고객사 일련번호 */
	private String hr_company_id;
	/** 회원 일련번호 */
	private String member_id;
	/** 보증기간 */
	private String warrantyTerm;
	/** 기업키워드 */
	private String keywords;
	/** 삭제 여부 */
	private String delFlag;

	/** 종료중 상태 체크 */
	private String closeCheck;

	//hr_company
	private String address;             // 주소
	private String ceo;                 // 대표자 이름
	private String companyName;         // 기업명
	private String companyStatus;       // 기업상태코드참조
	private String establishDate;       // 설립일
	private String marketListing;       // 상장상태코드참조

	//포지션 참가자
	private String joinDate;
	private String participantName;
	private String count;

	//member 검색 조건
	private String searchStart;
	private String searchEnd;
	private String word;
	private String phone;
	private String email;
	private String name;
	private String inputSelect;
	private String inlineRadioOptions;
	private String personalInfo;
	private String recruitStartDt;
	private String recruitEndDt;

}
