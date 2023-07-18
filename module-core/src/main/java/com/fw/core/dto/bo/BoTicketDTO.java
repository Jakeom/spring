package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * ticket DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoTicketDTO extends CommonDTO {

    private String id;                  // 열람권 일련번호
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    private String balance;             // 보유수량
    private String memberId;            // HH 회원 일련번호
    private String wefirmId;            // we펌 일련번호
    private String delFlag;


}
