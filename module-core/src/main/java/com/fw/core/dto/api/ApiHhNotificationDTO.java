package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_notification DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiHhNotificationDTO extends CommonDTO {

    private String id;                  // 알림 일련번호
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시	
    private String kindCd;              // 알림분류코드참조
    private String subject;             // 제목
    private String url;                 // 상세보기 URL
    private String isRead;              // 읽음/안읽음 표시
    private String delFlag;             // 삭제 유무
    private String userType;
    private String kindNm;              // 알림 분류 코드명

    //parameter
    private String memberId;           	// HH 회원 일련번호
    
    private String content; 		   	// 콘텐트
}
