package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoMemberDTO;
import com.fw.core.dto.bo.BoTbBlacklistDTO;

import java.util.List;

public interface BoBlacklistMapper {
    List<BoMemberDTO> selectMemberList(BoMemberDTO boMemberDTO);

    int selectMemberCnt(BoMemberDTO boMemberDTO);

    List<BoTbBlacklistDTO> selectBlacklistList(BoTbBlacklistDTO boTbBlacklistDTO);

    BoTbBlacklistDTO selectBlacklistDetail(BoTbBlacklistDTO boTbBlacklistDTO);

    void insertBlacklist(BoTbBlacklistDTO boTbBlacklistDTO);

    void insertBlacklistGuest(BoTbBlacklistDTO boTbBlacklistDTO);

    void updateBlacklist(BoTbBlacklistDTO boTbBlacklistDTO);

    void deleteBlacklist(BoTbBlacklistDTO boTbBlacklistDTO);
}
