package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_send_msg_target DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhSendMsgTargetDTO extends CommonDTO {

    private String id;              // 발송메일 수신자 일련번호
    private String isRead;          // 읽음여부
    private String name;            // 수신자 이름
    private String success;         // 전송상태 타입형식참조
    private String target;          // 수신자 정보(email / phoneNumber)
    private String msgId;           // 메시지 일련번호

}
