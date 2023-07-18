package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_resume_reject DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhResumeRejectDTO extends CommonDTO {

    private String id;                  // 이력서 등록 거절내역 일련번호
    private String createdAt;           // 생성일시		
    private String datedAt;             // 수정일시
    private String email;               // 이메일
    private String reasonType;          // 거절이유 코드참조
    private String memberId;            // 회원 테이블 PK

}
