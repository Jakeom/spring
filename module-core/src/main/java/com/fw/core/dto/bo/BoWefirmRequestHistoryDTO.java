package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * wefirm_request_history DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoWefirmRequestHistoryDTO extends CommonDTO {

    private String id;                      // 운영자 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String wefirmId;                // we펌 일련번호
    private String backofficeAdminId;       // 처리자 ID
    private String requestType;             // 접수내용구분
    private String status;                  // 접수상태
    private String completedAt;             // 처리시간
    private String denyReason;              // 거절사유
    private String ceoMemberId;             // 접수자 id
    private String memberId;                // 위펌 관리자 일련번호

    private String fileId;            //파일 일련번호
    private String originName;              //파일 이름
    private String url;                     //파일 url
    private String websiteUrl;
    private String ceoName;

    //search
    private String adminName;
    private String memberName;              //접수자 이름
    private String searchStart;
    private String searchEnd;
    private String memberEmail;
    private String memberPhone;
}
