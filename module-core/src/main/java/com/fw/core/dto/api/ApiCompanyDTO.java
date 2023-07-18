package com.fw.core.dto.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCompanyDTO extends CommonDTO {

    private String id;                  // 내 고객사 일련번호
    private String createdAt;           // 등록일시
    private String updatedAt;           // 수정일시
    private String address;             // 주소
    private String ceo;                 // 대표자 이름
    private String closed;              // 휴폐업여부
    private String companyName;         // 기업명
    private String companyScale;        // 기업규모코드참조
    private String companyStatus;       // 기업상태코드참조
    private String deleted;             // 삭제여부
    private String establishDate;       // 설립일
    private String industry;            // 업종명
    private String licenseNumber;       // 사업자등록번호
    private String location;            // 소재지
    private String marketListing;       // 상장상태코드참조
    private String phone;               // 연락처

    //api parameter
    private String companyId;              
}