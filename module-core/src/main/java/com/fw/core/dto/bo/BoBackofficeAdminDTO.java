package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * backoffice_admin DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoBackofficeAdminDTO extends CommonDTO {

    private String id;                  // 관리자 일련번호
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    private String deleted;             // 삭제여부
    private String email;               // 이메일	
    private String loginId;             // 로그인ID
    private String name;                // 관리자명
    private String password;            // 패스워드
    private String phone;               // 휴대폰번호

}
