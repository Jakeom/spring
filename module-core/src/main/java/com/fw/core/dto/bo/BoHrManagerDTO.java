package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hr_manager DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHrManagerDTO extends CommonDTO {

    private String id;                  // 인사담당자 일련번호
    private String createdAt;           // 생성일시	
    private String updatedAt;           // 수정일시
    private String email;               // 인사담당자 이메일
    private String name;                // 인사담당자 이름
    private String phone;               // 유선 연락처
    private String workAddress;         // 근무지
    private String hrCompanyId;         // 내 고객사 일련번호

}
