package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoWefirmDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BO Wefirm 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoWefirmMapper {

	/**
	 * 위펌 리스트 취득
	 */
	List<BoWefirmDTO> selectWefirmList(BoWefirmDTO boWefirmDTO);

	/**
	 * 위펌 상세조회 취득
	 */
	BoWefirmDTO selectWefirmDetail(BoWefirmDTO boWefirmDTO);

	/**
	 * 위펌상세 취득
	 */
	BoWefirmDTO selectWefirmModify(BoWefirmDTO boWefirmDTO);

	/**
	 * 위펌상태 수정
	 */
	void updateWefirmStatus(BoWefirmDTO boWefirmDTO);
	void updateWefirmStatusHH(BoWefirmDTO boWefirmDTO);
	void updateHHWefirm(BoWefirmDTO boWefirmDTO);

	/**
	 * 위펌수정
	 */
	void updateWefirm(BoWefirmDTO boWefirmDTO);

	/**
	 * 위펌등록
	 */
	void insertWefirm(BoWefirmDTO boWefirmDTO);

	/**
	 * 위펌삭제
	 */
	void deleteWefirm(BoWefirmDTO boWefirmDTO);

	/**
	 * 위펌 검색
	 */
	List<BoWefirmDTO> searchWefirmList(BoWefirmDTO boWefirmDTO);
	/**
	 * 위펌상세 페이지
	 * headhunter 상세  리스트
	 */
	List<BoWefirmDTO> selectHeadhunterDetail(BoWefirmDTO boWefirmDTO);
	/**
	 * 위펌상세 페이지
	 * 채용 상세 리스트
	 */
	List<BoWefirmDTO> selectRecruitDetail(BoWefirmDTO boWefirmDTO);
	/**
	 * 위펌상세 페이지
	 * 공고 리스트
	 */
	List<BoWefirmDTO> selectWefirmNotice(BoWefirmDTO boWefirmDTO);
	/**
	 * 위펌 상태 확인
	 * */
    List<BoWefirmDTO> selectWefirmStatus(BoWefirmDTO boWefirmDTO);
}
