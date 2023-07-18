package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthGroupDTO;
import com.fw.core.dto.bo.BoGroupDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoAuthGroupMapper {

    /**
     * 메뉴프로그램 리스트
     */
    List<BoAdminMenuProgramDTO> selectMenuProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO);

    /**
     * 그룹 리스트
     */
    List<BoGroupDTO> selectGroupList(BoGroupDTO boGroupDTO);

    /**
     * 그룹별 권한 정보
     */
    List<BoAuthGroupDTO> selectGroupAuth(BoAuthGroupDTO boAuthGroupDTO);

    /**
     * 그룹별 권한 부여
     */
    void insertGroupAuth(BoAuthGroupDTO boAuthGroupDTO);

    /**
     * 그룹별 권한 초기화
     */
    void deleteGroupAuth(BoAuthGroupDTO boAuthGroupDTO);
}
