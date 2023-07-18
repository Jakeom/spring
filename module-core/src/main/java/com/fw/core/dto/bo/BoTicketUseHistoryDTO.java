package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * ticket_use_history DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoTicketUseHistoryDTO extends CommonDTO {

    private String id;                      // 사용내역 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String balance;                 // 잔여수량
    private String increase;                // 증감
    private String memberId;                // 회원 테이블 PK
    private String ticketId;                // 열람권 일련번호
    private String reason;                  // 사유코드
    private String backofficeAdminId;       // 관리자 일련번호
    private String adminReason;             // 관리자 지급/차감 사유

    // 검색조건
    private String adminName;               //관리자 이름
    private String option;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String searchStart;
    private String searchEnd;
    private String startDate;
    private String endDate;

}
