package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * position_reply DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoPositionReplyDTO extends CommonDTO {

	private String id;         	  	// 게시글 일련번호
	private String createdAt;     	// 생성일시
    private String updatedAt;     	// 수정일시
    private String content;  	  	// 내용
    private String isReReply;		// 답글여부
    private String memberId;      	// 작성자
    private String parentReplyId;	// 부모 댓글 일련번호(답글의 경우)
    private String positionId; 	  	// 포지션 일련번호
}
