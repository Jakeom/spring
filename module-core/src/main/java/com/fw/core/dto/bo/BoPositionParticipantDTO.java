package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * position_participant DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoPositionParticipantDTO extends CommonDTO {

    private String id;               // 포지션참여자 일련번호
    private String position_id;	     // 포지션 일련번호
    private String member_id;        // 회원 테이블 PK
    private String stoped;           // cowork 종료여부

}
