package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
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
public class BoBoardDTO extends CommonDTO {

    private String id;              // 게시판 일련번호
    private String createdAt;       // 생성일시
    private String updatedAt;       // 수정일시
    private String name;            // 게시판명
    private String wefirmId;        // we펌 일련번호

}
