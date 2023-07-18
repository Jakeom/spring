package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhClientDTO extends CommonDTO {

    // 고객사 관리자 client_pm
    private String id;              // client_pm - 관리자 아이디 & client_pass - 합격자 아이디
    private String memberId;        // PM
    private String licenseNo;       // 사업자번호
    private String companyName;     // 기업명
    private String createdAt;       // 생성일시
    private String expiredAt;       // 만료일시
    private String updatedAt;       // 수정일시
    private String certifiedAt;     // 인증일시
    private String registerAt;      // 등록일
    private String delFlag;         // 삭제여부

    // 합격자 client_pass
    private String pmId;            // 관리자 아이디
    private String passName;        // 합격자명
    private String apprFlag;        // 합격자 승인상태
    private String taxFileId;       // 세금계산서 파일 아이디
    private String rejectMemo;      // 반려사유

}
