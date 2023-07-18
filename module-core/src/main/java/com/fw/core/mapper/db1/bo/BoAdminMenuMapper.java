package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JYoo, skayhlj@gmail.com
 * @since 2022.09
 */
@Mapper
public interface BoAdminMenuMapper {

    /**
     * 트리리스트 취득
     */
    List<BoAdminMenuDTO> selectMenuList(BoAdminMenuDTO boAdminMenuDTO);


    /**
     * 메뉴 삭제
     */
    void deleteMenu(BoAdminMenuDTO boAdminMenuDTO);

    /**
     * 권한별(개인) 사이드메뉴 리스트 취득
     */
    List<BoAdminMenuDTO> selectMenuListForUser(BoAdminDTO boAdminDTO);

    /**
     * 권한별(그룹) 사이드메뉴 리스트 취득
     */
    List<BoAdminMenuDTO> selectMenuListForDept(BoAdminDTO boAdminDTO);

    /**
     * 권한별(조직) 사이드메뉴 리스트 취득
     */
    List<BoAdminMenuDTO> selectMenuListForOrg(BoAdminDTO boAdminDTO);

    /**
     * 메뉴 추가,업데이트
     */
    void insertUpdateMenu(BoAdminMenuDTO boAdminMenuDTO);

}