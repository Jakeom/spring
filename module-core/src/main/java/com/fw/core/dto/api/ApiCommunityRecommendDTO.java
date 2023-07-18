package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * community recommend DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCommunityRecommendDTO {

    private String communityRecommendSeq;   // 커뮤니티 추천 순서
    private String communityCommentSeq;     // 커뮤니티 댓글 순서
    private String communitySeq;            // 커뮤니티 순서
    private String createSeq;               // 생성 번호
    private String createDt;                // 생성 일시
    private String memberId;                // 회원 일련번호
    private String updateType;              // P-추천수증가 / M-추천수감소
}
