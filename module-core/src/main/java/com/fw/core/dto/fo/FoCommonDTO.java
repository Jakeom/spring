package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FoCommonDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mainDisplaySeq;
    private String infoType;
    private String displayType;
    private String content;
    private String fileSeq;
    private String useFlag;
    private String delFlag;
    private String regSeq;
    private String regDate;
    private String bannerSeq;
    private String bannerTypeCd;
    private String bannerNm;
    private String bannerOrder;
    private String bannerStartDt;
    private String bannerEndDt;
    private String linkUrl;
    private String createSeq;
    private String createDt;
    private String updateSeq;
    private String updateDt;

}
