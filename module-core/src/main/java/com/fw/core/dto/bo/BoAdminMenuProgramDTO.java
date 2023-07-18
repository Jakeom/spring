package com.fw.core.dto.bo;

import java.io.Serializable;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BoAdminMenuProgramDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menuCd;
    private String programCd;
    private String programNm;
    private String programUrl;
    private String programAttrCd;
    private String createSeq;
    private String createDt;
    private String updateSeq;
    private String updateDt;
    private String sql;


}
