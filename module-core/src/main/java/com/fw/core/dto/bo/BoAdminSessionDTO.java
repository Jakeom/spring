package com.fw.core.dto.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 관리자 Session DTO
 * @author sjpaik
 */
@Getter
@Setter
@Builder
public class BoAdminSessionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String adminSeq;            // 사용자 순번
    private String orgId;               // 조직 id
    private String adminId;             // 사용자 id
    private String adminNm;             // 사용자이름
    private String email;               // 이메일

}
