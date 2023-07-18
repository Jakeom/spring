package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * resume_keyword DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoResumeKeywordDTO extends CommonDTO {

	private String resumeId;	// 이력서 일련번호
	private String id;     	
    private String keyword;		// 키워드
}
