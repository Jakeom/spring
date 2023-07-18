package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * board_content DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoBoardContentDTO extends CommonDTO {

	private String id;         	  // 게시글 일련번호
	private String createdAt;     // 생성일시
    private String updatedAt;     // 수정일시
    private String attachFileId;  // 첨부파일 id
    private String content;    	  // 내용
    private String hitCnt;        // 조회수
    private String isNotice;      // 공지여부
    private String subject; 	  // 제목
    private String boardId; 	  // 게시판 일련번호
    private String memberId; 	  // 회원 일련번호

    private String memberName;
}
