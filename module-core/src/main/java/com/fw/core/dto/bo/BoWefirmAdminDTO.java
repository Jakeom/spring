package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * wefirm_admin DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoWefirmAdminDTO extends CommonDTO {

    private String id;              // 운영자 일련번호	
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시
    private String wefirmId;        // we펌 일련번호
    private String memberId;        // HH 회원 일련번호

}
