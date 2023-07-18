package com.fw.core.dto.api;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * active_history DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiActiveHistoryDTO extends CommonDTO {

    private String id;                      // 로그인 이력 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String activeHistoryCd;         // 활동코드참조(인재등록, 로그인등, 포지션등록 등)
    private String comment;                 // 비고
    private String ip;                      // 접속 IP
    private String location;                // 접속 국가코드
    private String ua;                      // 접속 기기
    private String member_id;               // 회원 일련번호

}
