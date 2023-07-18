package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * wefirm_join_request DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoWefirmJoinRequestDTO extends CommonDTO {
    //sf_change_request
    private String id;              // 가입신청 일련번호
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시
    private String memberId;        // HH 회원 일련번호
    private String delFlag;
    private String completedAt;
    private String denyReason;
    private String backoffice_admin_id;
    private String status;





}
