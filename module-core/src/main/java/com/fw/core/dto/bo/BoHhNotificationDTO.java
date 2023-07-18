package com.fw.core.dto.bo;

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
public class BoHhNotificationDTO extends CommonDTO {

    private String id;                  // 알림 일련번호
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시	
    private String kindCd;              // 알림분류코드참조
    private String subject;             // 제목
    private String url;                 // 상세보기 URL
    private String member_id;            // HH 회원 일련번호
    private String isRead;              // 읽음/안읽음 표시

}
