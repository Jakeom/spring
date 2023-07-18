package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * 팝업 DTO
 *
 * @author YJW
 */
@Getter
@Setter
public class BoPopupDTO extends CommonDTO {

    private String popupSeq;      // 팝업SEQ
    private String title;         // 제목
    private String content;       // 내용
    private String popupOrder;    // 팝업순서
    private String useFlag;       // 사용여부
    private String popupStartDt;  // 팝업시작일시
    private String popupEndDt;    // 팝업종료일시
    private String linkUrl;       // 링크URL
    private String offsetX;       // 오프셋X
    private String offsetY;       // 오프셋Y
    private String createSeq;     // 생성자번호
    private String createDt;      // 생성일시
    private String updateSeq;     // 수정자번호
    private String updateDt;      // 수정일시
    private String delFlag;       // 삭제여부
}
