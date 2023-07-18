package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * reward_history DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoRewardHistoryDTO extends CommonDTO {

    private String id;                      // 스크랩 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String balance;                 // 리워드 총량
    private String goodsCd;                 // 리워드 교환 상품
    private String increase;                // 리워드 증감
    private String reasonCd;                // 증감 코드
    private String trId;                    // tr_id
    private String applicantId;             // 회원 테이블 PK
    private String headhunterId;            // 열람한 HH의 PK
    private String rewardId;                // 리워드 PK
    private String adminReason;             // 관리자 사유
    private String backofficeAdminId;       // 관리자 일련번호
    private String delFlag;
    private String rewardGoodsId;
    private String sendFlag;
    private String content;
    private String loginId;

    //reward_goods table
    private String goodNm;
    private String point;
    private String goodComp;

    //검색조건
    private String searchStart;
    private String searchEnd;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private String endDate;
    private String startDate;

}
