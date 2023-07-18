package com.fw.core.dto.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * board_content DTO
 *
 * @author YJW
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBoardContentDTO extends CommonDTO {

	private String id;         	    // (게시글 일련번호 / 파일 일련번호)
    private String boardId; 	    // 게시판 일련번호
    private String memberId;        // 회원 일련번호
    private String subject;         // 제목
    private String content;         // 내용
    private String isNotice;        // 공지여부
    private String hitCnt;          // 조회수
    private String recommendCnt;    // 추천수
    private String attachFileId;    // 첨부파일ID
    private String delFlag;         // 삭제여부
    private String createSeq;       // 생성자번호
    private String createdAt;       // 생성일시
    private String updateSeq;       // 수정자번호
    private String updatedAt;       // 수정일시
    private String isSecret;        // 비밀글 여부 (기본값 N)

    /* Request Parameter */
    private String boardContentId;  // 게시글 일련번호

    /* file_mgr */
    private String name;            // 파일명
    private String path;            // 파일경로
    private String url;             // 파일 URL
    private String fileId;          // 파일 일련번호
}
