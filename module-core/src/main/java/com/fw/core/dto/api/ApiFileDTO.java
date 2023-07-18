package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author j.s.ko
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiFileDTO extends CommonDTO{
    private String fileDetailSeq;
    private String fileSeq;
    private String orgFileNm;
    private String saveFileNm;
    private String saveFileSrc;
    private String fileUrl;
    private String fileExt;
    private String fileSize;
    private String delFlag;
    private String createSeq;
    private String createDt;
    private String memberId;
}
