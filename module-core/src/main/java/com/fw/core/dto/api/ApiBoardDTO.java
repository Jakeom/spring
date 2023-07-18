package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * board DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBoardDTO {

    private String id;              // 게시판 일련번호
    private String name;            // 게시판명
    private String wefirmId;        // we펌 일련번호
    private String delFlag;         // 삭제여부
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시
    private String createSeq;       // 생성자 번호
    private String updateSeq;       // 수정자 번호

}
