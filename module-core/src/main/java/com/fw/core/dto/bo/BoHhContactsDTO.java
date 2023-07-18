package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * hh_contacts DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoHhContactsDTO extends CommonDTO {

	private String id;         	  	 // 일련번호
	private String createdAt;     	 // 생성일시
    private String updatedAt;     	 // 수정일시
    private String email;		 	 // 이메일
    private String memo;		 	 // 메모
    private String name;		 	 // 이름
    private String phone;		 	 // 휴대폰번호
    private String position;		 // 직급
    private String groupId;		 	 // group fk
}
