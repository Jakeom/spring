package com.fw.core.dto.fo;
import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * point DTO
 *
 * @author YJW
 */
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FoPointDTO extends CommonDTO {

	private String id;         	  	// 포인트 일련번호
	private String createdAt;     	// 생성일시
    private String updatedAt;     	// 수정일시
    private String memberId;		// HH 회원 일련번호
    private Integer paidPoint;		// 잔여 결제 포인트 수량
    private Integer freePoint;		// 잔여 무상지급 포인트 수량
    private Integer balance;	    // 잔여 포인트(총량)
    private String resumeId;        // 이력서 일련번호
    private Integer paidIncrease;   // 결제포인트 증감
    private Integer freeIncrease;   // 무상지급 포인트 증감
    private String type;
    private int amount;
    private String registerPathCd;

    private String paymentId;

}
