package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_template_file DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhTemplateFileDTO extends CommonDTO {

    private String id;                      // 템플릿 첨부파일 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String templateContentId;       // 템플릿 일련번호
    private String templateFileId;          // 통합 파일 관리 일련번호

}
