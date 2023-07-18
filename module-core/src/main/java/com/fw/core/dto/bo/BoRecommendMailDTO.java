package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * recommend_mail DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoRecommendMailDTO extends CommonDTO {

    private String id;                  // 추천메일 일련번호	
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    private String content;             // 메일내용
    private String password;            // 열람비밀번호
    private String subject;             // 메일제목
    private String positionId;          // 포지션 일련번호

}
