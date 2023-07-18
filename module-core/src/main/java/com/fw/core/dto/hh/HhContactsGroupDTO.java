package com.fw.core.dto.hh;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * hh_contacts_group DTO
 *
 * @author 
 */
@Getter
@Setter
public class HhContactsGroupDTO extends CommonDTO {

	private String id;         	  	 // 일련번호
	private String createdAt;     	 // 생성일시
    private String updatedAt;     	 // 수정일시
    private String name;		 	 // 그룹명
    private String memberId;		 // 회원 테이블 PK
    private String delFlag; // 삭제 플레그
    private String groupId;
}
