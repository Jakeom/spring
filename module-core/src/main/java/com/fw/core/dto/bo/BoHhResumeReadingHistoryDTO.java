package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_resume_reading_history DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhResumeReadingHistoryDTO extends CommonDTO {

    private String id;                  // 열람내역 일련번호	
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    private String expireAt;            // 열람만료일
    private String registerPathCd;      // 등록방식코드참조(열람권, 인재등록)
    private String memberId;            // HH회원 일련번호
    private String resumeId;            // 이력서 일련번호
    private String memo;                // 내인재 메모
    private String groupId;             // 그룹 일련번호

    //member table
    private String name;       // 관리자 일련번호

    //resume 테이블 member_id
    private String resumeMemberId;

    // 검색조건
    private String startDate;
    private String endDate;

}
