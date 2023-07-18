package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * point_product DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoPointProductDTO extends CommonDTO {

    private String id;              // 포인트 상품 일련번호
    private String name;            // 포인트 상품명
    private String price;           // 포인트 가격
    private String paidQty;         // 포인트 지급수량
    private String freeQty;         // 무상지급 포인트 지급수량

}
