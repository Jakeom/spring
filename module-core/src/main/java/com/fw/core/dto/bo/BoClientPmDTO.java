package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Data;

@Data
public class BoClientPmDTO extends CommonDTO {

    private String id;            //관리자 아이디
    private String memberId;      // PM memberId
    private String licenseNo;     // 사업자번호
    private String companyName;   // 기업명
    private String createdAt;     // 생성일시
    private String expiredAt;     // 만료일시
    private String updatedAt;     // 수정일시
    private String certifiedAt;   // 인증일시
    private String registerAt;    // 등록일
    private String delFlag;       // 삭제여부
    private String wefirmId;      // 위펌 아이디
    private String wefirmName;    // 위펌 이름
    private String name;          // member_name pm 이름 확인할때 사용

    //검색조건
    private String startDate;
    private String endDate;
    private String nameOption;
    private String licenseNoOption;
    private String companyNameOption;

}
