package com.fw.core.mapper.db1.fo;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.fo.FoMemberDTO;

@Mapper
public interface FoMemberMapper {

	void insertMember(FoMemberDTO foMemberDTO); // 회원정보 등록

	boolean selectMemberNameDuplicateCheck(FoMemberDTO foMemberDTO); // 회원정보 중복 조회

	FoMemberDTO selectMemberEmailDuplicateCheck(FoMemberDTO foMemberDTO); // 중복 이메일 가입 조회

	void updateChangePassword(FoMemberDTO foMemberDTO); // 비밀번호 변경

	FoMemberDTO selectMemberPassword(FoMemberDTO foMemberDTO); // 기존 비밀번호 조회

	FoMemberDTO selectMember(FoMemberDTO foMemberDTO); // 회원정보 조회

	FoMemberDTO selectProfileInfo(FoMemberDTO foMemberDTO); // 회원상세정보 조회 (회원정보 수정)

	void updateMember(FoMemberDTO foMemberDTO); // 회원정보 수정

	void updatePassword(FoMemberDTO foMemberDTO); // 비밀번호 재설정

	FoMemberDTO selectLoginId(FoMemberDTO foMemberDTO); // 아이디 조회(아이디 찾기)

	void updateDeleteMember(FoMemberDTO foMemberDTO); // 탈퇴

	String selectDtype(String String); // 회원 DTYPE 조회

	void updateTempMember(FoMemberDTO foMemberDTO); // 임시회원 회원가입

	boolean selectMemberBlackList(FoMemberDTO foMemberDTO); // 블랙리스트 조회 조회
}
