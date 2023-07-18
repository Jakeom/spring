package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hh_custom_template DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHhCustomTemplateDTO extends CommonDTO {

    private String id;                  // 템플릿 일련번호
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    private String content;             // 커스텀 템플릿 내용 (HTML)
    private String isDefault;           // 기본여부
    private String baseTemplateId;      // 템플릿 일련번호
    private String memberId;            // HH 회원 일련번호

}
