package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * position_ban_word DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoPositionBanWordDTO extends CommonDTO {

    private String id;        // 포지션 금칙어 일련번호
    private String word;      // 금칙어
    private String delFlag;   // 삭제여부

}
