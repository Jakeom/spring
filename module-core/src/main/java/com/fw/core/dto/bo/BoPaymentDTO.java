package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * payment DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoPaymentDTO extends CommonDTO {

    private String id;                      // 결제내역 일련번호
    private String paymentId;
    private String type;
    private String cancelledPrice;          // 총 취소금액
    private String createdAt;
    private String method;                  // 결제 수단 alias
    private String methodName;              // 결제 수단 명칭
    private String orderId;                 // 주문번호
    private String paymentData;             // PG사 결제 raw 데이터
    private String pg;                      // 걸제 PG alias
    private String pgName;                  // 결제 PG 명칭
    private String price;                   // 결제가격
    private String productName;             // 상품명
    private String purchasedAt;             // 결제 승인시간
    private String remainPrice;             // 총 금액 - 최소 금액
    private String receiptId;               // 영수증ID
    private String status;                  // 결제상태
    private String unit;                    // 결제 단위(krw, usd)
    private String memberId;                // HH 회원 일련번호
    private String pointProductId;          // 포인트 상품 일련번호
    private String requestedAt;             // 결제 요청시간
    private String bankCode;                // 은행코드
    private String bankName;                // 은행명
    private String virtualAccount;          // 가상계좌
    private String depositor;               // 입금자명
    private String expireDate;              // 입금기한
    private String revokedAt;               // 결제 취소시간
    private String delFlag;
    private String pointUseHistoryId; // 히스토리 아이디
    private String statusCd;

    private Integer paidIncrease;   // 결제포인트 증감
    private Integer freeIncrease;   // 무상지급 포인트 증감

//검색
    private String name;
    private String email;
    private String phone;
    private String searchStart;
    private String searchEnd;


}
