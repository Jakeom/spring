package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * backoffice_admin_log DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoBackofficeAdminLogDTO extends CommonDTO {

    private String backofficeAdminId;           // 관리자 일련번호
    private String id;                          // 일련번호
    private String createdAt;                   // 요청일
    private String params;                      // 파라미터
    private String requestMethod;               // 요청방식
    private String requestUrl;                  // 요청 URL
    private String responseStatus;              // 응답코드

}
