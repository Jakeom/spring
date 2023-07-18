package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiNoticeDTO extends CommonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String noticeSeq;		// 공지사항 일련번호
	private String noticeType; 		// 공지종류
	private String noticeTypeCd;	// 공지 종류 코드(BOARD_TYPE)
	private String title; 			// 공지 제목
	private String content;			// 공지 내용
	private String fileSeq; 		// 공지 첨부파일 seq
	private String hit;				// 조회수
	private String delFlag;			// 삭제여부
	private String createSeq;		// 등록자 일련번호
	private String createDt;		// 등록일
	private String updateSeq;		// 수정자 일련번호
	private String updateDt;		// 수정일
	private String displayOrder;	// 메인노출순서

	// 검색 조건
	private String startDate;
	private String endDate;

	// notice DTO WSH
	// private String id; 							// 공지사항 일련번호
	// private String createdAt; 					// 생성일시
	// private String updatedAt; 					// 수정일시
	// private String kindCd; 						// 공지종류 코드참조
	// private String subject; 					// 제목
	// private String hits; 						// 조회수
	// private String modifiedAt; 					// 수정날짜
	// private String backofficeAdminId; 			// 생성한 관리자 일련번호
	// private String modifiedBackofficeAdminId; 	// 수정한 관리자 일련번호
}
