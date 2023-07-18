package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.Data;

/**
 * 
 * @author dongbeom
 *
 */
@Data
public class BoFaqCategoryDTO extends CommonDTO {
   
  private String faqCategorySeq;     // FAQ 카테고리 순번
  private String memberTypeCd;       // 멤버 타입
  private String categoryNm;         // 카테고리 이름
  private String delFlag;            // 삭제 여부
  private String createSeq;          // 생성 순번
  private String createDt;           // 생성 일시
  private String updateSeq;          // 업데이트 순번
  private String updateDt;           // 업데이트 일시

  /* RESUME9 faq_category DTO */
  private String id;                 // FAQ 카테고리 일련번호
  private String target;             // 구분(AP, HH)
  private String name;               // 카테고리명
  private String isMemberOnly;       // 로그인회원노출여부
  private String Field;
}
