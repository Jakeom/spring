package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_resume_reading_history_group DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhResumeReadingHistoryGroupDTO extends CommonDTO {

    private String id;              // 일련번호
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시
    private String isDefault;       // 기본그룹유무
    private String name;            // 그룹명
    private String memberId;        // 회원 테이블 PK

}
