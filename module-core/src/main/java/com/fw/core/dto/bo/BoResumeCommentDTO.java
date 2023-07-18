package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * resume_comment DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoResumeCommentDTO extends CommonDTO {

	private String id;         	  	// 게시글 일련번호
	private String createdAt;     	// 생성일시
    private String updatedAt;     	// 수정일시
    private String contents;  	  	// 수정요청내용
    private String directModified;	// 헤드헌터 직접수정여부
    private String modified;      	// 수정완료여부
    private String resumeCategoryCd;// 이력서 항목코드참조(경력기술, 자기소개서 등)
    private String resumeId; 	  	// 이력서 일련번호
}
