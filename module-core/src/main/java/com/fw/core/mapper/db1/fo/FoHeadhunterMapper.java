package com.fw.core.mapper.db1.fo;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.fo.FoHeadhunterDTO;

@Mapper
public interface FoHeadhunterMapper {

	boolean selectHeadhunterByRefferalCd(FoHeadhunterDTO foHeadhunterDTO); // 추천코드 확인

	String selectHeadhunterByMemberId(FoHeadhunterDTO foHeadhunterDTO); // 추천코드 조회

	void insertHeadhunter(FoHeadhunterDTO foHeadhunterDTO); // 헤드헌터 등록

	void insertHeadhunterRequest(FoHeadhunterDTO foHeadhunterDTO); // 승인 요청

}
