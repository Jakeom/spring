package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * backoffice_admin_role DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoBackofficeAdminRoleDTO extends CommonDTO {

    private String backofficeAdminId;       // 관리자 일련번호
    private String backofficeRoleId;        // 권한 일련번호

}
