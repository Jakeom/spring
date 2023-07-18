package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * qna DTO
 *
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoQnaDTO extends CommonDTO {

	private String id; // 자주묻는질문 일련번호
	private String createdAt; // 생성일시
	private String updatedAt; // 수정일시
	private String answer; // 답변내용
	private String answeredAt; // 답변일시
	private String content; // 내용
	private String kindCd; // 문의종류 코드참조
	private String qnaTypeCd; // common_code qna_type
	private String subject; // 제목
	private String attachFileId; // 파일 일련번호
	private String memberId; // 작성자
	private String backofficeAdminId; // 관리자 일련번호

	private String searchStart;
	private String searchEnd;

	private String memberName; // 작성자 이름
	private String adminName; // 관리자 이름
	private String memberPhone;
	private String memberEmail;
	private String answerYn;
	private String memberType;

	private MultipartFile[] qnaFiles;

}
