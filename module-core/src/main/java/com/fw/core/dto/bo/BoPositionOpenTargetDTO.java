package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * position_open_target DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoPositionOpenTargetDTO extends CommonDTO {

    private String id;              // 공개대상 일련번호
    private String positionId;	    // 포지션 일련번호
    private String memberId;       // 회원 테이블 PK
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시

}
