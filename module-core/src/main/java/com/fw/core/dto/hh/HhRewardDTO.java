package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HhRewardDTO extends CommonDTO {

    private String id;                  // 스크랩 일련번호
    private String applicantId;         // 회원 테이블 PK
    private String balance;             // 리워드 총량
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시

    //리워드 내역
    private String reasonCd;           // 증감 코드


}
