package com.fw.core.dto.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * board_reply DTO
 *
 * @author YJW
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBoardReplyDTO extends CommonDTO {

    private String replyId;             // 댓글 일련번호
    private String parentReplyId;       // 부모 댓글 일련번호
    private String boardContentId;      // 게시글 일련번호
    private String memberId;            // 회원 일련번호
    private String exType;              // 경험 구분
    private String content;             // 내용
    private String attachFileId;        // 첨부파일 list
    private String delFlag;             // 삭제여부
    private String secretFlag;          // 비밀글 여부
}
