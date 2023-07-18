package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * advertisement DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoAdvertisementDTO extends CommonDTO {

    private String id;                                    // 서치펌 광고 일련번호
    private String createdAt;                             // 생성일시
    private String updatedAt;                             // 수정일시
    /*private String page;*/                              // 화면구분
    private String position;                              // 영역번호
    private String subject;                               // 제목
    private String bannerFileId;                          // 배너 이미지 일련번호
    private String bannerText;                            // 배너문구
    private String linkType;                              // 링크타입
    private String linkUrl;                               // URL
    private String linkFileId;                            // 파일 일련번호
    private String opened;                                // 노출여부
    private String backofficeAdminId;                     // 관리자 일련번호
    private String modifiedBackofficeAdminId;             // 수정관리자 일련번호
    private String modifiedAt;                            // 수정일시

}
