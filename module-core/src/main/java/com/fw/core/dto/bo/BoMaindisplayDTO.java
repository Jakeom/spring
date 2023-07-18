package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoMaindisplayDTO extends CommonDTO {

    private String mainDisplaySeq; //메인노출 순서
    private String displayType; // 노출종류
    private String url; // url
    private String fileSeq; // 파일순서
    private String sort; // 순서
    private String useFlag; // 사용 여부
    private String delFlag; // 삭제 여부
    private String regSeq; // 생성 번호
    private String regDate; // 생성 일시
    private String companyName; // 회사명

    //검색조건
    private String startDate;
    private String endDate;
    private String commoncdType;

    private MultipartFile[] maindisplayFiles;

}
