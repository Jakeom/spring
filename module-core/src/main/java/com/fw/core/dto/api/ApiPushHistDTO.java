package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * tb_push_hist DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPushHistDTO extends CommonDTO {

    private String pushHistSeq;     // 푸시 알림 일련번호
    private String dispType;        // 푸시 알림 공통코드 - 'NOTIFICATION_TYPE'
    private String dispTypeNm;      // 푸시 알림 공통코드명 - 'NOTIFICATION_TYPE'
    private String memberId;        // 수신자 일련번호
    private String title;           // 제목
    private String imgNm;           // 이미지 명
    private String imgPath;         // 이미지 경로
    private String imgSize;         // 이미지 사이즈
    private String url;             // URL
    private String phoneType;       // OS 종류
    private String phoneToken;      // 토큰
    private String sendTime;        // 보낸시간
    private String setTime;         // 예약시간
    private String sendYn;          // 발송여부
    private String reserveYn;       // 예약발송여부
    private String createId;        // 등록자 일련번호
    private String createTime;      // 등록시간
    private String updateId;        // 수정자 일련번호
    private String updateTime;      // 수정시간
    private String receiveYn;       // 수신여부
    private String receiveTime;     // 수신시간

}
