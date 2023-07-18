package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * hr_memo DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHrMemoDTO extends CommonDTO {

    private String id;             // 고객사 메모 일련번호
    private String createdAt;      // 생성일시
    private String updatedAt;      // 수정일시
    private String content;        // 메모 내용
    private String hrCompanyId;    // 내 고객사 일련번호

}
