package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhMemberRoleDTO extends CommonDTO {
    
	private String memberId;
    private String roleId;

}
