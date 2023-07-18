package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.InternalDateDTO;

import lombok.*;

/**
 * PositionStatus DTO
 * 
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPositionStatusDTO extends InternalDateDTO {

    /* request body */
    private String memberId;       // 회원 일련번호 (헤드헌터)

    /* response body */
    private String progressCnt;     // 진행중인 공고 수
    private String closedCnt;       // 마감된 공고 수
    private String applyCnt;        // 오늘 신규 지원자 수
    private String uncollectedCnt;  // 미열람 이력서 갯수

}
