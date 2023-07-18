package com.fw.core.dto.batch;

import com.fw.core.dto.CommonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * TABLE tb_push_hist
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PushHistBean extends CommonDTO implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    private String pushHistSeq;
    private String dispType;
    private String memberId;
    private String title;
    private String content;
    private String imgNm;
    private String imgPath;
    private String imgSize;
    private String url;
    private String phoneType;
    private String phoneToken;
    private String sendTime;
    private String setTime;
    private String sendYn;
    private String reserveYn;
    private String createId;
    private String createTime;
    private String updateId;
    private String updateTime;
    private String webDomain;
}