package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoTbBlacklistDTO extends CommonDTO {
    private String blacklistSeq;   // 블랙리스트 순서

    private String memberId;       // 회원 일련번호

    private String blacklistType;  // 블랙리스트 종류

    private String name;            // 성명

    private String phone;           // 연락처

    private String email;           // 이메일

    private String useFlag;        // 사용 여부

    private String delFlag;        // 삭제 여부

    private String regSeq;         // 생성 번호

    private String regDate;        // 생성 일시

    private String updateDt;        // 업데이트 일시

    //검색옵션
    private String nameOption;
    private String emailOption;
    private String idOption;
    private String phoneOption;
    private String startDate;
    private String endDate;

    private List<String> checkSeq; // 체크된 회원아이디 - > 블랙리스트 등록에 활용


}

