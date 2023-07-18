package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminMenuDTO;
import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoAdminMenuProgramMapper {

    /**
     * 프로그램 리스트 취득
     * @param BoAdminMenuProgramDTO boAdminMenuProgramDTO
     * @return BoAdminMenuProgramDTO
     */
    List<BoAdminMenuProgramDTO> selectProgramList(BoAdminMenuProgramDTO boAdminMenuProgramDTO);

    /**
     * 프로그램 삭제
     * @param BoAdminMenuProgramDTO boAdminMenuProgramDTO
     */
    void deleteProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO);

    /**
     * 프로그램 추가
     * @param BoAdminMenuProgramDTO boAdminMenuProgramDTO
     */
    void insertProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO);



}
