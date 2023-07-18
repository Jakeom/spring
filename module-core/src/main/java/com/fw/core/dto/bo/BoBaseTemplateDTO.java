package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * base_template DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoBaseTemplateDTO extends CommonDTO {

	private String id;         	  	// 템플릿 일련번호
	private String createdAt;     	// 생성일시
    private String updatedAt;     	// 수정일시
    private String content;  	  	// 템플릿 (HTML)
    private String mediaTypeCd;		// 매체구분 (email, sms 등)
    private String subject;			// 제목
    private String templateTypeCd;	// 템플릿 양식 구분 (포지션 제안, 학격통보, 탈락통보 등)
}
