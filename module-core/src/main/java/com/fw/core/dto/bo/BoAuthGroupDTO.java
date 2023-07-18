package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 관리자 권한 DTO
 *
 * @author YJW
 */
@Getter
@Setter
public class BoAuthGroupDTO extends CommonDTO {

    private String groupId;                      // 그룹 seq
    private String menuCd;                       // 메뉴 seq
    private String programCd;                    // 메뉴프로그램 seq
    private String createSeq;                    // 생성자
    private String createDt;                     // 생성일시
    private List<BoAuthGroupDTO> groupAuthList;  // 그룹별 권한 리스트
}
