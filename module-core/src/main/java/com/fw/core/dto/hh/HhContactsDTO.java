package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * hh_contacts DTO
 *
 * @author 
 */
@Getter
@Setter
public class HhContactsDTO extends CommonDTO {

	private String id;         	  	 // 일련번호
	private String createdAt;     	 // 생성일시
    private String updatedAt;     	 // 수정일시
    private String email;		 	 // 이메일
    private String memo;		 	 // 메모
    private String name;		 	 // 이름
    private String phone;		 	 // 휴대폰번호
    private String position;		 // 직급
    private String groupId;		 	 // 그룹 일련번호
    private String groupNm;	// 그룹명
    private String lastSendAt;       // 최종메일 발송일

    private String to;
    private String receiveEmail;
    private String subject;
    private String content;
    private String type;
    private List<HhContactsDTO> targetList;

    private MultipartFile[] attchedFiles;
    
    // 검색 조건
    private String keyword;	// 검색키워드
    private String searchType;  // 검색타입


    //체크 필드
    private String[] checkFieldArr;
    private String checkedFields;


}