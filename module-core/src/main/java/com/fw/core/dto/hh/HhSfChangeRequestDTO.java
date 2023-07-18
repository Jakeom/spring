package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * sf_change_request DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HhSfChangeRequestDTO extends CommonDTO {

    private String id;                      // 신청 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String sfCeoName;               // 서치펌 대표자명
    private String sfHomepageUrl;           // 서치펌 홈페이지 URL
    private String sfName;                  // 서치펌명
    private String sfPhone;                 // 서치펌 연락처
    private String sfRegNumber;             // 서치펌 사업자등록번호
    private String sfWorkerListFileId;      // 서치펌 종사자 명부 file id
    private String status;                  // 신청상태코드
    private String memberId;                // HH 회원 일련번호
    private String completedAt;             // 처리날짜
    private String denyReason;              // 거절사유
    private String backofficeAdminId;       // 일련번호
    private MultipartFile[] sfWorkerFile;
    private String loginId;

    private String originName; //파일 이름
    private String url;  //파일 url

    //search
    private String adminName;
    private String memberName;
    private String searchStart;
    private String searchEnd;
    private String memberEmail;
    private String memberPhone;
}
