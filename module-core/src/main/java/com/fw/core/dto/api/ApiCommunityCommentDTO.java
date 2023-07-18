package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * community DTO
 * @author WSH
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCommunityCommentDTO {

    private String communityCommentSeq;       	// 커뮤니티 종류
    private String communitySeq;        	  	// 커뮤니티 순서
    private String content;       				// 내용
    private String depth;       				// 깊이
    private String parentCommunityCommentSeq;	// 기업 순서
    private String recommend;       			// 추천수
    private String delFlag;       				// 삭제 여부
    private String secretFlag;       			// 삭제 여부
    private String createSeq;       			// 생성 번호
    private String createDt;       				// 생성 일시
    private String updateSeq;       			// 수정 번호
    private String updateDt;       				// 수정 일시
    private String attachFileId;
    private String memberId;
    private String exType;

}
