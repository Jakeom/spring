package com.fw.core.dto.api;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * ApiPositionAlertDTO
 * @author WSH
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiPositionAlertDTO extends CommonDTO {

    private String position_alert_seq;      // 맞춤 채용 공고 순서
    private String industry;               	// 직무
    private String memberId;                // 회원 일련번호
    private String delFlag;               	// 삭제여부
    private String regSeq;               	// 생성번호
    private String regDate;               	// 생성일시

}
