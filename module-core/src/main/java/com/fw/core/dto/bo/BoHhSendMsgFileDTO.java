package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_send_msg_file DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhSendMsgFileDTO extends CommonDTO {

    private String id;              // 발송메일 파일 일련번호
    private String fileId;          // 파일 일련번호
    private String msgId;           // 메시지 일련번호

}
