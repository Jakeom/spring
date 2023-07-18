package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.FileDTO;
import lombok.*;

import java.util.List;

/**
 * hh_approval_request DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhApprovalRequestDTO extends CommonDTO {

    private String id;                      // 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String completedAt;             // 승인날짜
    private String denyReason;              // 거절사유
    private String status;                  // 신청상태
    private String backofficeAdminId;       // 백오피스 어드민 PK
    private String memberId;                // 회원 테이블 PK
    private String delFlag;

    private String memberName;
    private String adminName;
    private String email;
    private String phone;
    private String sfCeoName;
    private String sfHomepageUrl;
    private String sfName;
    private String sfPhone;
    private String sfWorkerListFileId;
    private String originName; //파일 이름
    private String url;  //파일 url


    //search
    private String searchStart;
    private String searchEnd;
    private String memberEmail;
    private String memberPhone;

}
