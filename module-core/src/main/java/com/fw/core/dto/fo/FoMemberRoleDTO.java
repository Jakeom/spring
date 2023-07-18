package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoMemberRoleDTO extends CommonDTO {
    
	private String memberId;
    private String roleId;

}
