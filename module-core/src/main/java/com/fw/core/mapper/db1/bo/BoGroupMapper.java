package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoGroupDTO;

@Mapper
public interface BoGroupMapper {

	/** 그룹 리스트 조회 22.09.07 */
	List<BoGroupDTO> selectGroupList(BoGroupDTO boGroupDTO);

	/** 그룹 정보 조회 22.09.07 */
	BoGroupDTO selectGroupInfo(BoGroupDTO boGroupDTO);

	/** 그룹관리 Modify */
	BoGroupDTO selectGroupModify(BoGroupDTO boGroupDTO);

	/** 그룹아이디 중복체크 22.09.08 */
    int selectGroupIdCheck(String groupId);

	/** 그룹 정보 등록 22.09.07 */
    void insertGroupInfo(BoGroupDTO boGroupDTO);

	/** 그룹 정보 수정 22.09.07 */
    void updateGroupInfo(BoGroupDTO boGroupDTO);

	/** 그룹 정보 삭제 22.09.14 */
    void deleteGroupInfo(BoGroupDTO boGroupDTO);
}
