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
public class BoResumeFilteringDTO extends CommonDTO {

	private String id;         	  	
    private String filterCd;		// 이력서 필터코드참조(sky, 서울, 지방국공립 등)
    private String resumeId; 	  	// 이력서 일련번호
}
