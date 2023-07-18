package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class BoNoticeDTO extends CommonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String noticeSeq;
	/** 공지 seq */
	private String noticeTypeCd;
	/** 공지 종류 코드(BOARD_TYPE) */
	private String title;
	/** 공지 제목 */
	private String content;
	/** 공지 내용 */
	private String fileSeq;
	/** 공지 첨부파일 seq */
	private String hit;
	/** 조회수 */
	private String delFlag;
	/** 삭제여부 */
	private String createSeq;
	/** 만든 seq */
	private String createDt;
	/** 만든날 */
	private String updateSeq;
	/** 수정 seq */
	private String updateDt;
	/** 수정날 */
	private String noticeCd;
	/** 공지사항 종류 코드 */


	/** 검색 조건 */
	private String searchStart;
	private String searchEnd;
	private String editor;
	private String writer;

	/** notice DTO WSH */
	private String id;							// 공지사항 일련번호
	private String createdAt;					// 생성일시
	private String updatedAt;					// 수정일시
	private String kindCd;						// 공지종류 코드참조
	private String subject;						// 제목
	private String hits;						// 조회수
	private String modifiedAt;					// 수정날짜
	private String backofficeAdminId;			// 생성한 관리자 일련번호
	private String modifiedBackofficeAdminId;	// 수정한 관리자 일련번호

	/** file_mgr */
	private String originName;					// 원래 파일명
	private String url;							// S3 URL

	/** param */
	private String boardId;	// 공지사항 일련번호

	/** 파일 리스트*/
	private MultipartFile[] noticeFiles;

	private List<String> noticeIds;
	private List<String> displayPositions;
	private String displayOrder;


}
