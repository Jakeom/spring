package com.fw.bo.system.menu.service;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuDTO;
import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.mapper.db1.bo.BoAdminMenuMapper;
import com.fw.core.mapper.db1.bo.BoAdminMenuProgramMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JYoo, skayhlj@gmail.com
 * @since 2022.09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingMenuService {

    private final BoAdminMenuMapper boAdminMenuMapper;
    private final BoAdminMenuProgramMapper boAdminMenuProgramMapper;

    public List<BoAdminMenuDTO> selectMenuList(BoAdminMenuDTO boAdminMenuDTO){
        return boAdminMenuMapper.selectMenuList(boAdminMenuDTO);
    }

    public void insertMenu(BoAdminMenuDTO boAdminMenuDTO){
        // 삭제할 메뉴 삭제
        for (BoAdminMenuDTO menuMasterModel : boAdminMenuDTO.getDeleteMenuMasterList()) {
            boAdminMenuMapper.deleteMenu(menuMasterModel);
        }

        // 등록, 수정 처리
        for (BoAdminMenuDTO menuMasterModel : boAdminMenuDTO.getMenuMasterList()) {
            boAdminMenuMapper.insertUpdateMenu(menuMasterModel);
        }
    }

    public List<BoAdminMenuProgramDTO> selectProgramList(BoAdminMenuProgramDTO boAdminMenuProgramDTO){
        return boAdminMenuProgramMapper.selectProgramList(boAdminMenuProgramDTO);
    }

    public void deleteProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO){
         boAdminMenuProgramMapper.deleteProgram(boAdminMenuProgramDTO);
    }

    public void insertProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO){
        boAdminMenuProgramMapper.insertProgram(boAdminMenuProgramDTO);
    }

    public List<BoAdminMenuDTO> selectMenuListForUser(BoAdminDTO boAdminDTO){
        return boAdminMenuMapper.selectMenuListForUser(boAdminDTO);
    }

    public List<BoAdminMenuDTO> selectMenuListForDept(BoAdminDTO boAdminDTO){
        return boAdminMenuMapper.selectMenuListForDept(boAdminDTO);
    }

    public List<BoAdminMenuDTO> selectMenuListForOrg(BoAdminDTO boAdminDTO){
        return boAdminMenuMapper.selectMenuListForOrg(boAdminDTO);
    }

}
