package com.fw.core.mapper.db1.fo;


import com.fw.core.dto.fo.FoMemberRoleDTO;
import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoMemberRoleMapper {

    // 지원자 역할 등록
    void insertMemberRole(FoMemberRoleDTO foMemberRoleDTO);

}
