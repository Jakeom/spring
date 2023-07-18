package com.fw.core.mapper.db1.fo;


import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoSimpleLoginMapper {

    // 간편 인증 등록
    void insertMemberSimpleAuth(FoSimpleLoginDTO foSimpleLoginDTO);
    // 간편 인증 삭제
    void updateMemberSimpleAuthDel(FoSimpleLoginDTO foSimpleLoginDTO);
    // 간편 로그인 등록 여부
    FoSimpleLoginDTO selectSimpleLoginInfo(FoSimpleLoginDTO foSimpleLoginDTO);

}
