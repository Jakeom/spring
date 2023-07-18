package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoLoginMapper {

    FoSessionDTO selectAccountInfo(String username);
    FoSessionDTO selectDoSimpleLoginInfo(FoSessionDTO foSessionDTO);
    FoSimpleLoginDTO selectAutoLogin(String memberId);
    void updateDeleteAutoLogin(String memberId);
    int selectBlack(String memberId);
    FoSessionDTO selectTempMember(String username);
    String selectHhApproved(FoSessionDTO foSessionDTO);
    void updateLoginInfo(FoSessionDTO foSessionDTO);

}
