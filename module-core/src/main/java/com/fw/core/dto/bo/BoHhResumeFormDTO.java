package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_resume_form DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhResumeFormDTO extends CommonDTO {

    private String id;                      // 그룹 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String completedAt;             // 처리 일시
    private String denyReason;              // 거절 사유
    private String formUrl;                 // 양식 주소
    private String name;                    // 이력서 양식 이름	
    private String status;                  // 상태
    private String statusCode;                  // 상태 commonCd
    private String backofficeAdminId;       // 관리자 일련번호
    private String formFileId;              // 파일 일련번호
    private String memberId;                // 회원 테이블 PK

    //backoffice_admin 테이블
    private String backofficeAdminName;     // backofficeAdmin 이름
    
    //member 테이블
    private String email;                   // 이메일

    //검색조건
    private String startDate;
    private String endDate;

    private String nameOption;
    private String phoneOption;
    private String emailOption;


}
