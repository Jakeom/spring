package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoLoginLogDTO extends CommonDTO {
    
	private String apiLoginLogSeq;		    // 순서
    private String id;                	    // 아이디
    private String resultFlag;              // 결과여부
    private String accessIp;                // 접속 아이피
    private String createDt;          		// 생성일시
    
}
