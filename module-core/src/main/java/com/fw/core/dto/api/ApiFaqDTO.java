package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dongbeom
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiFaqDTO extends CommonDTO {

	private String faqSeq; // FAQ 순번
	private String faqCategorySeq; // FAQ 카테고리 순번
	private String title; // 제목
	private String content; // 내용
	private String fileSeq; // 파일 순번
	private String hit; // 조회수
	private String delFlag; // 삭제여부
	private String createSeq; // 생성 순번
	private String createDt; // 생성 일시
	private String updateSeq; // 업데이트 순번
	private String updateDt; // 업데이트 일시
	private String memberType;	// 멤버타입(코드변환)
	private String memberTypeCd; // 멤버 타입
	private String categoryNm; // 카테고리 이름

	/* RESUME9 faq DTO */
	private String id; // 자주묻는질문 일련번호
	private String createdAt; // 생성일시
	private String updatedAt; // 수정일시
	private String categoryId; // FAQ 카테고리 일련번호
	private String subject; // 제목
	private String isMemberOnly; // 로그인회원노출여부
	private String hits; // 조회수
	private String backofficeAdminId; // 생성한 관리자 일련번호
	private String modifiedBackofficeAdminId; // 수정한 관리자 일련번호
	private String displayFlag; // 메인노출순서

	private String startDate;
	private String endDate;

}
