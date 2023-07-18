package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoTbBlacklistDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoBlacklistMapper {

    int selectBlacklistCntForPaging(FoTbBlacklistDTO foTbBlacklistDTO); // 블랙리스트 cnt
    List<FoTbBlacklistDTO> selectBlacklist(FoTbBlacklistDTO foTbBlacklistDTO); // 블랙리스트 조회

    void updateBlacklist(FoTbBlacklistDTO foTbBlacklistDTO); // 블랙리스트 확인/해제
}
