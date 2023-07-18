package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * resume_parsed_result DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoResumeParsedResultDTO extends CommonDTO {

	private String id;         	  	// 파싱결과 일련번호
	private String createdAt;     	// 생성일시
    private String updatedAt;     	// 수정일시
    private String content;  	  	// 파싱결과 내용
    private String memberId;		// 회원 일련번호
    private String memberType;		// 회원구분(유저,관리자)
}
