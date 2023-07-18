package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HhFaqDTO extends CommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String faqSeq;    /** seq */
    private String faqCategorySeq;
    private String title;        /**  제목 */
    private String content;      /**  내용 */
    private String fileSeq;      /** 첨부파일 seq */
    private String hit;          /** 조회수 */
    private String displayFlag;
    private String delFlag;      /** 삭제여부 */
    private String createSeq;    /** 만든 seq */
    private String createDt;     /** 만든날  */
    private String updateSeq;    /** 수정 seq */
    private String updateDt;     /** 수정날 */

   //FaqCategory table
    private String categoryNm;
    private String memberTypeCd;

    private  String searchVal;
}
