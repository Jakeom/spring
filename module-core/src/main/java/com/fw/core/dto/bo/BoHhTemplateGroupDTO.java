package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * hh_template_group DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoHhTemplateGroupDTO extends CommonDTO {

	private String id;         	  	 // 그룹 일련번호
	private String createdAt;     	 // 생성일시
    private String updatedAt;     	 // 수정일시
    private String isDefault;		 // 기본그룹 여부
    private String mediaTypeCd;		 // 매체구분(email/sms)
    private String name;		 	 // 그룹명
    private String memberId;		 // 회원 테이블 PK
}
