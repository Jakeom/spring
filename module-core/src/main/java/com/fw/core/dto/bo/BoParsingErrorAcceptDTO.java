package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.FileDTO;
import lombok.*;

import java.util.List;

/**
 * parsing_error_accept DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoParsingErrorAcceptDTO extends CommonDTO {

    private String id;                      // 이력서 판독오류 신고 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String directInput;             // 직접입력시 필드
    private String errorType;               // 오류현상 코드
    private String pageUrl;                 // 등록 페이지정보
    private String memberId;                // 회원 일련번호
    private String resumeFileId;            // 파일 일련번호
    private String acceptStatus;            // 진행상태
    private String acceptStatusCode;        // 진행상태 commonCd
    private String etc;                     // 비고
    private String agreeFileid;            // 동의 파일 일련번호
    private String completedAt;             // 처리일자
    private String positionId;              // 등록포지션 ID
    private String backofficeAdminId;       // 일련번호
    private List<FileDTO> agreeFileList;    // 동의서 파일 리스트

    //member 테이블
    private String name;                    // 이름
    private String email;                   // 이메일
    
    //backoffice_admin 테이블
    private String backofficeAdminName;     // backofficeAdmin 이름

    //검색조건
    private String startDate;
    private String endDate;
    private String nameOption;
    private String phoneOption;
    private String emailOption;
    private String inputNameOption;
    private String backofficeAdminNameOption;

    // Datatable 대응
    private int draw;
    private int start;
    private int length;
    private int recordsTotal;

}
