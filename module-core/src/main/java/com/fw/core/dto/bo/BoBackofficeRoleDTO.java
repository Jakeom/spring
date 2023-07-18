package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * backoffice_role DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoBackofficeRoleDTO extends CommonDTO {

    private String id;                  // 권한 일련번호
    private String code;                // 권한코드
    private String categoryCode;        // 권한분류
    private String description;         // 권한설명
    private String priority;            // 중요도

}
