package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_position_field DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhPositionFieldDTO extends CommonDTO {

    private String id;                  // 분야 일련번호
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    private String fieldCd;             // 분야 코드 참조
    private String type;                // 분야 구분(전문, 진행)
    private String memberId;            // HH 회원 일련번호

}
