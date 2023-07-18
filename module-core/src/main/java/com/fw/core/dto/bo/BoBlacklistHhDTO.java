package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * blacklist_hh DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoBlacklistHhDTO extends CommonDTO {

    private String id;                   // 블랙리스트 일련번호
    private String applicantId;          // AP회원 일련번호
    private String headhunterId;         // HH회원 일련번호
    private String createdAt;            // 생성일시
    private String updatedAt;            // 수정일시

}
