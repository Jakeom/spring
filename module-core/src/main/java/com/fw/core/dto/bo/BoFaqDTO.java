package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.Data;

import java.util.List;

/**
 * @author dongbeom
 */
@Data
public class BoFaqDTO extends CommonDTO {

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
	private String memberTypeCd; // 멤버 타입
	private String categoryNm; // 카테고리 이름
	private String displayFlag; // 카테고리 이름
	private String displayCnt; // 메인노출 개수

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

	private String startDate;
	private String endDate;

	/* file_mgr */
	private String originName;	// 원래 파일명
	private String url;			// S3 URL

	/* custom column */
	private String writer;
	private String editor;
	private String faqType; 	// FAQ 구분(AP : 후보자 / HH : 헤드헌터 / 값이 없으면 모든 리스트)
	private String boardId; 	// faq 일련번호 param
	private List<String> checkSeq;
	private List<String> displayFlagCheck;

}
