package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class BoAdminMenuDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menuCd;
    private String upperMenuCd;
    private String menuNm;
    private String menuIconCd;
    private String menuUrl;
    private String menuOpenTypeCd;
    private String menuOrder;
    private String menuLevel;
    private String useFlag;
    private String accessAbleStartDt;
    private String accessAbleEndDt;
    private String blockStartDt;
    private String blockEndDt;
    private String createSeq;
    private String createDt;
    private String updateSeq;
    private String updateDt;
    private String sql;

    private List<BoAdminMenuDTO> menuMasterList;
    private List<BoAdminMenuDTO> deleteMenuMasterList;


}
