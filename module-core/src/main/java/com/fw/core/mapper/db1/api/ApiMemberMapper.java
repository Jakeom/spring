package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiHhNotificationDTO;
import com.fw.core.dto.api.ApiMemberDTO;
import com.fw.core.dto.api.ApiScrapPositionDTO;

import java.util.List;

/***
 * @author wsh
 */
public interface ApiMemberMapper {

	/** 알림 리스트 */
	List<ApiHhNotificationDTO> selectAlarmList(ApiHhNotificationDTO apiHhNotificationDTO);

	/** 알림 읽기 */
	ApiHhNotificationDTO selectAlarmRead(ApiHhNotificationDTO apiHhNotificationDTO);

	/** 알림 설정 리스트 */
	List<ApiHhNotificationDTO> selectAlarmSetList(ApiHhNotificationDTO apiHhNotificationDTO);

	/** 알림 설정 */
	ApiHhNotificationDTO selectAlarmSet(ApiHhNotificationDTO apiHhNotificationDTO);

	/** 후보자 회원가입 */
	int insertMember(ApiMemberDTO apiMemberDTO);

	/** 헤드헌터 추천코드 확인 */
	boolean selectHeadhunterByRefferalCd(ApiMemberDTO apiMemberDTO);

	/** 회원 정보수정 */
	int updateMember(ApiMemberDTO apiMemberDTO);

	/** 회원 롤 등록 */
	int insertMemberRole(ApiMemberDTO apiMemberDTO);

	/** 로그인설정(등록) */
	void insertMemberSimpleAuth(ApiMemberDTO apiMemberDTO);

	/** 로그인설정(조회)*/
	List<ApiMemberDTO> selectMemeberSimpleAuth(ApiMemberDTO apiMemberDTO);

	/** 회원 간변 등록 정보삭제 */
	int updateMemberSimpleAuthDel(ApiMemberDTO apiMemberDTO);

	/** 지원자 정보등록 */
	int insertApplicant(ApiMemberDTO apiMemberDTO);

	/** 지원자 정보수정 */
	int updateApplicant(ApiMemberDTO apiMemberDTO);

	/** 로그인 회원정보 검색 */
	ApiMemberDTO selectLoginInfo(ApiMemberDTO apiMemberDTO);

	/** 스크랩된 공고 조회 / COUNT */
	List<ApiScrapPositionDTO> selectMyScrapPosition(ApiScrapPositionDTO apiScrapPositionDTO);
	int selectMyScrapPositionCnt(ApiScrapPositionDTO apiScrapPositionDTO);

    /** 회원정보 검색 by resume */
	List<ApiMemberDTO> selectMemberByResume(ApiMemberDTO apiMemberDTO);

	/** 회원정보 검색 by resume count */
	int selectMemberByResumeCnt(ApiMemberDTO apiMemberDTO);

	/** 회원 일련번호로 AP/HH 구분 */
	String selectUsertype(String memberId);

	/** 회원찾기 */
	List<ApiMemberDTO> selectSearchMember(ApiMemberDTO apiMemberDTO);

	/** 자동로그인 토큰 조회 */
	ApiMemberDTO selectAutoLoginToken(ApiMemberDTO apiMemberDTO);

	/** 아이디(이메일) 중복확인 */
	boolean selectDuplicateLoginId(ApiMemberDTO apiMemberDTO);

	/** 헤드헌터 정보조회 */
	ApiMemberDTO selectHhInfo(ApiMemberDTO apiMemberDTO);

	/** 헤드헌터 정보수정(member) */
	void updateMemberHh(ApiMemberDTO apiMemberDTO);

	/** 헤드헌터 정보수정(headhunter) */
	void updateHeadhunter(ApiMemberDTO apiMemberDTO);

	/** 헤드헌터 정보삭제(hh_position_field */
	void deleteHhPositionField(ApiMemberDTO apiMemberDTO);

	/** 헤드헌터 정보등록(hh_position_field) */
	void insertHhPositionField(ApiMemberDTO apiMemberDTO);

	void updatePushInfo(ApiMemberDTO apiMemberDTO);
}
