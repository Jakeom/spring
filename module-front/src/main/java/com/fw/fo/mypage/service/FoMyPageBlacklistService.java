package com.fw.fo.mypage.service;

import com.fw.core.dto.fo.FoTbBlacklistDTO;
import com.fw.core.mapper.db1.fo.FoBlacklistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoMyPageBlacklistService {
    private final FoBlacklistMapper foBlacklistMapper;

    public int selectBlacklistCntForPaging(FoTbBlacklistDTO foTbBlacklistDTO){
        return foBlacklistMapper.selectBlacklistCntForPaging(foTbBlacklistDTO);
    }

    public List<FoTbBlacklistDTO> selectBlacklist(FoTbBlacklistDTO foTbBlacklistDTO){
        return foBlacklistMapper.selectBlacklist(foTbBlacklistDTO);
    }

    public void updateBlacklist(FoTbBlacklistDTO foTbBlacklistDTO){
        foBlacklistMapper.updateBlacklist(foTbBlacklistDTO);
    }
}
