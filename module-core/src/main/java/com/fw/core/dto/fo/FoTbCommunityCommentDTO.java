package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoTbCommunityCommentDTO extends CommonDTO {

    private String communityCommentSeq;         // 커뮤니티 댓글 순서 
    private String communitySeq;                // 커뮤니티 순서
    private String content;                     // 내용
    private String depth;                       // 깊이 ( 모댓글이면 0, 답글이면 1 )
    private String parentCommunityCommentSeq;   // 상위 댓글
    private String recommend;                   // 추천수
    private String delFlag;                     // 삭제 여부
    private String createSeq;                   // 생성 번호
    private String createDt;                    // 생성 일시
    private String updateSeq;                   // 수정 번호
    private String updateDt;                    // 수정 일시
    private String maskingName;

    private String name;
    private String node;
    private String commentCnt;
    private String dateDiff;
    private String maxDepth;
    private List<FoTbCommunityCommentDTO> subList;

    public int getDepthInteger(){
        return Integer.parseInt(depth);
    }

}
