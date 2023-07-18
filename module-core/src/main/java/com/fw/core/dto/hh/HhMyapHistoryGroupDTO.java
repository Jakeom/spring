package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhMyapHistoryGroupDTO extends CommonDTO {
    
	private String id;                	    // 그룹 일련번호
    private String name; // 그룹명
    private String createdAt;// 생성일
    private String updatedAt;// 수정일
    private String memberId;// 생성자
    private String delFlag;// 삭제유무
    private String isDefault; // 기본값


}
