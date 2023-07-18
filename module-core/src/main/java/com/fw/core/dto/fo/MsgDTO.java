package com.fw.core.dto.fo;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgDTO {

    private String type;    // SMS, LMS
    private String from;    // 발신자 번호
    private String to;      // 수신자 번호
    private String subject;      // 제목
    private String message;      // 내용

}
