package com.fw.core.dto.api;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * ApiApHpDTO
 * @author WSH
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiApHpDTO extends CommonDTO {

    private String ap_hp_seq;                  	// 맞춤 인재 학력 순서
    private String ap_alert_seq;               	// 맞춤 인재 알림 순서
    private String hp2;               			// 학력2
    private String del_flag;         			// 삭제여부
    private String reg_seq;                 	// 생성번호
    private String reg_date;                    // 생성일시

}
