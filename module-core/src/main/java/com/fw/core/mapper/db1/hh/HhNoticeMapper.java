package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhNoticeDTO;

@Mapper
public interface HhNoticeMapper {

	// 리스트 카운트 취득
	public int selectNoticeListCntForPaging(HhNoticeDTO hhNoticeDTO);

	// 공고 리스트 취득
	public List<HhNoticeDTO> selectNoticeListPaging(HhNoticeDTO hhNoticeDTO);

	// 공고 상세보기

	public HhNoticeDTO selectNoticeInfo(HhNoticeDTO hhNoticeDTO);

	// 메인 공지사항
	public List<HhNoticeDTO> selectNoticeList(HhNoticeDTO hhNoticeDTO);

	// 공지사항 조회수
	void updateNoticeHit(HhNoticeDTO hhNoticeDTO);

}
