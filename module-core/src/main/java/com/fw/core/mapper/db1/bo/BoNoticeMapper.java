package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoNoticeDTO;

/**
 * BO Notice 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoNoticeMapper {

	/**
	 * 공지 리스트 취득
	 */
	List<BoNoticeDTO> selectNoticeList(BoNoticeDTO boNoticeDTO);

	/**
	 * 공지 상세조회 취득
	 */
	BoNoticeDTO selectNoticeDetail(BoNoticeDTO boNoticeDTO);

	/**
	 * 공지사항 상세 취득
	 */
	BoNoticeDTO selectNoticeModify(BoNoticeDTO boNoticeDTO);

	/**
	 * 공지사항 수정
	 */
	void updateNotice(BoNoticeDTO boNoticeDTO);

	/**
	 * 공지사항 등록
	 */
	void insertNotice(BoNoticeDTO boNoticeDTO);

	/**
	 * 공지사항 삭제
	 */
	void deleteNotice(BoNoticeDTO boNoticeDTO);

	/**
	 * 공지사항 검색
	 */
	List<BoNoticeDTO> searchNoticeList(BoNoticeDTO boNoticeDTO);

	List<BoNoticeDTO> selectNoticeDisplayList(BoNoticeDTO boNoticeDTO);

	void deleteDisplayOrder(BoNoticeDTO boNoticeDTO);
	void updateDisplayOrder(BoNoticeDTO boNoticeDTO);
}
