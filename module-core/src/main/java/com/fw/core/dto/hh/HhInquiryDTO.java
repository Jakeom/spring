package com.fw.core.dto.hh;

import java.io.Serializable;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhInquiryDTO extends CommonDTO implements Serializable {

	private String id; // 자주묻는질문 일련번호
	private String createdAt; // 생성일시
	private String updatedAt; // 수정일시
	private String answer; // 답변내용
	private String answeredAt; // 답변일시
	private String content; // 내용
	private String kindCd; // 문의종류 코드참조
	private String subject; // 제목
	private String attachFileId; // 파일 일련번호
	private String memberId; // 작성자
	private String backofficeAdminId; // 관리자 일련번호
	private String cnt; // paging count

}
