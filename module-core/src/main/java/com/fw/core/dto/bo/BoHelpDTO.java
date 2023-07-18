package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * 조직 DTO
 * @author jhmoon
 */
@Getter
@Setter
public class BoHelpDTO extends CommonDTO {

    private String menuCd;
    private String content;
    private String createSeq;
    private String createDt;
    private String updateSeq;
    private String updateDt;

}
