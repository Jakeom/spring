package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * reward DTO
 *
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoRewardMallDTO extends CommonDTO {

    private String id;                  // 일련번호
    private String point;               // 포인트
    private String goodNm;              // 상품명
    private String goodComp;            // 사용처
    private String goodDetail;          // 상세설명
    private String delFlag;             // 삭제 여부
    private String createSeq;          // 생성 번호
    private String createDt;           // 생성 일시
    private String updateSeq;          // 수정 번호
    private String updateDt;           // 수정 일시
    private String count;              //신청 현황
    private String fileCheck;          // 파일 체크

    /** reward_history Table */
    private String createdAt;            // 생성일시
    private String applicantId;          // 회원 테이블 PK
    private String memberName;
    /** 검색 */
    private String searchStart;
    private String searchEnd;

    private MultipartFile[] imageFile;
    private String fileSeq;

}
