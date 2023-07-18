package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BO Info 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoInfoMapper {

	/**
	 * 정보 리스트 취득
	 */
	List<BoInfoDTO> selectInfoList(BoInfoDTO boInfoDTO);
	/**
	 * 정보 상세조회 취득
	 */
	BoInfoDTO selectInfoDetail(BoInfoDTO boInfoDTO);
	/**
	 * 정보 상세 취득
	 */
	BoInfoDTO selectInfoModify(BoInfoDTO boInfoDTO);
    /**
     * 정보  검색
     */
    List<BoInfoDTO> searchInfoList(BoInfoDTO boInfoDTO);
	/**
	 * 정보 수정
	 */
	void updateInfo(BoInfoDTO boInfoDTO);
	/**
	 * 정보 등록
	 */
	void insertInfo(BoInfoDTO boInfoDTO);
	/**
	 * 정보 삭제
	 */
	void deleteInfo(BoInfoDTO boInfoDTO);

}
