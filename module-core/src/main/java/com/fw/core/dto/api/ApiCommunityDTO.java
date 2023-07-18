package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

import java.util.List;

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
public class ApiCommunityDTO extends CommonDTO {

    private String communitySeq;        // 커뮤니티 순서
    private String communityType;       // 커뮤니티 종류
    private String communityTypeNm;     // 커뮤니티 코드 변환값
    private String companyName;         // 회사명
    private String commentCnt;          // 댓글 수
    private String dtype;        		// AP,HH 구분
    private String title;         		// 제목
    private String content;       		// 내용
    private String secretFlag;       	// 비밀글 여부
    private String companySeq;       	// 기업 순서
    private String hit;       			// 조회수
    private String recommend;       	// 추천수
    private String fileSeq;       		// 파일 순서
    private String delFlag;       		// 삭제 여부
    private String createSeq;       	// 생성 번호
    private String createDt;       		// 생성 일시
    private String updateSeq;       	// 수정 번호
    private String updateDt;       		// 수정 일시
    private String memberId;       		// 멤바 아이디
    private String myBoardFlag;         // 내 글 여부
    
    // file_mgr
    private String fileId;       		// 
    private String name;       			// 
    private String path;       			// 
    private String url;       			// 
    
    private List<ApiFileDTO> fileList;
    

}
