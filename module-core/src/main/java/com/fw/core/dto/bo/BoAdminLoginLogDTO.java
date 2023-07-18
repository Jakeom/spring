package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 관리자 로그인로그 DTO
 *
 * @author YJW
 */
@Getter
@Setter
public class BoAdminLoginLogDTO extends CommonDTO {

    private String adminLoginLogSeq;  // 로그인로그seq
    private String adminId;           // 관리자 아이디
    private String accessIp;          // ip
    private String resultFlag;        // 로그인 성공여부
    private String createDt;          // 로그인 시간
    private int count;                // 로그 성공/실패 카운트
}
