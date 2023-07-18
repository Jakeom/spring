package com.fw.core.dto.hh;

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
public class HhSendMsgDTO extends CommonDTO {

    private String id;              // 메시지 일련번호
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시	
    private String content;         // 내용
    private String kindCd;          // 메일형식타입참조
    private String media;           // 미디어타입참조
    private String subject;         // 제목
    private String memberId;        // HH 회원 일련번호
    private String recipientName;

    // hh_send_msg_target 테이블
    private String isRead;
    private String name;
    private String success;
    private String msgId;
    private String target;
    private String val;

    // hh_send_msg_file 테이블
    private String fileId;
}
