package com.fw.core.dto.m;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MServiceDTO extends CommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // faq & notice
    private String faqSeq;
    private String faqCategorySeq;
    private String title;
    private String content;
    private String fileSeq;
    private String hit;
    private String displayFlag;
    private String delFlag;
    private String createSeq;
    private String createDt;
    private String updateSeq;
    private String updateDt;
    private String noticeSeq;
    private String noticeTypeCd;
}
