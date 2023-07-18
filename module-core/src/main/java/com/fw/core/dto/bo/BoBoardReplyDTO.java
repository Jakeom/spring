package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * board_reply DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoBoardReplyDTO extends CommonDTO {

	private String id;         	  // 댓글 일련번호
	private String createdAt;     // 생성일시
    private String updatedAt;     // 수정일시
    private String content;       // 내용
    private String isReReply;     // 답글여부
    private String boardContentId;// 게시글 일련번호
    private String memberId;      // 회원 일련번호
    private String parentReplyId; // 부모 댓글 일련번호(답글의 경우)
}
