package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * point DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoPointDTO extends CommonDTO {

	private String id;         	  	// 포인트 일련번호
	private String createdAt;     	// 생성일시
    private String updatedAt;     	// 수정일시
    private String memberId;		// HH 회원 일련번호
    private String paidPoint;		// 잔여 결제 포인트 수량
    private String freePoint;		// 잔여 무상지급 포인트 수량
    private String balance;			// 잔여 포인트(총량)
}
