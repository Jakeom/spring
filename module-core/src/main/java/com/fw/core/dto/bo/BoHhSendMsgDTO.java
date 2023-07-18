package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_send_msg DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhSendMsgDTO extends CommonDTO {

    private String id;              // 메시지 일련번호
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시	
    private String content;         // 내용
    private String kindCd;          // 메일형식타입참조
    private String media;           // 미디어타입참조
    private String subject;         // 제목
    private String memberId;        // HH 회원 일련번호

}
