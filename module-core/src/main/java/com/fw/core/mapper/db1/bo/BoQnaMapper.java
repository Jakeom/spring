package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoQnaDTO;

/**
 * BO Qna 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoQnaMapper {

	/**
	 * 1:1 문의 리스트 취득
	 */
	List<BoQnaDTO> selectQnaList(BoQnaDTO boQnaDTO);
	/**
	 * 1:1 문의 상세조회 취득
	 */
	BoQnaDTO selectQnaDetail(BoQnaDTO boQnaDTO);
	/**
	 * 1:1 문의상세 취득
	 */
	BoQnaDTO selectQnaModify(BoQnaDTO boQnaDTO);
	/**
	 * 1:1 문의 검색
	 */
	List<BoQnaDTO> searchQnaList(BoQnaDTO boQnaDTO);
	/**
	 * 1:1 문의수정
	 */
	void updateQna(BoQnaDTO boQnaDTO);

}
