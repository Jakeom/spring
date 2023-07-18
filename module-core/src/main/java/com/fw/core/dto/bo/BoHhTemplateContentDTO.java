package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * hh_template_content DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoHhTemplateContentDTO extends CommonDTO {

	private String id;         	  	 // 템플릿 일련번호
	private String createdAt;     	 // 생성일시
    private String updatedAt;     	 // 수정일시
    private String content;		 	 // 커스텀 템플릿 내용 (HTML)
    private String isDefault;		 // 기본 메세지 여부
    private String name;		 	 // 템플릿 이름
    private String subject;		 	 // 템플릿 제목
    private String templateGroupId;	 // 템플릿 그룹 일련번호
}
