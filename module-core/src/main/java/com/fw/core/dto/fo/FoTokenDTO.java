package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FoTokenDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String createdAt;
    private String updatedAt;
    private String auth;
    private String expiredAt;
    private String isConfirmed;
    private String isUsed;
    private String mediaType;
    private String token;
    private String useType;
    private String delFlag;
    private String serviceCode;
    private String serviceMessage;

}
