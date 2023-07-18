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
public class BoOrgDTO extends CommonDTO {

    private String orgId;
    private String orgNm;
    private String useFlag;
    private String createSeq;
    private String createDt;
    private String updateSeq;
    private String updateDt;
    private String delFlag;
    private String writer; 
    private String corrector;
    

}
