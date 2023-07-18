package com.fw.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonDTO extends InternalDateDTO {

    private BoAdminSessionDTO adminSession;
    private FoSessionDTO frontSession;

    private String orgId;
    private String orgNm;
    private String cd;
    private String cdNm;
    private String order;
    private String groupCd;
    private String fileSeq;
    private int page = 1;           // 현재 페이지
    private Integer startRow;       // 시작 Row
    private Integer endRow;         // 종료 Row
    private int rowSize = 10;       // 페이지당 Row Size
    private int totalCount;         // 총 개수
    private int rowNum;             // Row Number
    public int getStartRow(){
        return (this.rowSize * this.page) - this.rowSize;
    }
    public int getEndRow() {
        return this.rowSize;
    }
    public int getPageCnt(){
        return this.rowSize;
    }
    public int getPageCount(){
        return this.rowSize;
    }
    public int getCurrentPage() {
        return this.page;
    }

    /** 페이지 블록 : 페이지 블록 사이즈 */
    public int getLastNo(){
        return 10;
    }
    private String orderType;    // 정렬기준
    private String sortColumn;
    private List<FileDTO> commonFileList;
    private String searchValue;
    private String searchType;
    private String searchType2;

    private String code;
    private String address;
    private String link;
    private String name;
    private String region;
    private String schoolType;
    private String delFlag;
    private String campusName;
    private String degreeType;
    private String estType;
    private String infoUrl;
    private String id;
    private String createdAt;
    private String updatedAt;
    private String ceo;
    private String closed;
    private String companyName;
    private String companyScale;
    private String companyStatus;
    private String deleted;
    private String establishDate;
    private String industry;
    private String licenseNumber;
    private String location;
    private String marketListing;
    private String phone;
    private String memberId;
    private String mediaType;
    private String mediaTypeCd;
    private String content;
    private String isDefault;
    private String subject;
    private String mode;

    private MultipartFile[] files;

    private String groupNm;
    private String upperCd;


}