package com.fw.fo.common.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoCommonDTO;
import com.fw.core.mapper.db1.fo.FoCommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoCommonService {

    private final FoCommonMapper foCommonMapper;
    private final CommonFileService commonFileService;

    public FoCommonDTO selectInfo(FoCommonDTO foCommonDTO){
        return foCommonMapper.selectInfo(foCommonDTO);
    }

    public List<FoCommonDTO> selectBannerList(FoCommonDTO foCommonDTO){
        List<FoCommonDTO> list = foCommonMapper.selectBannerList(foCommonDTO);
        for(FoCommonDTO f : list){
            String fileSeq = f.getFileSeq();
            if(StringUtils.isNotBlank(fileSeq)){
                f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return list;
    }

    public List<FoCommonDTO> selectMainDisplayList(FoCommonDTO foCommonDTO){
        List<FoCommonDTO> list = foCommonMapper.selectMainDisplayList(foCommonDTO);
        for(FoCommonDTO f : list){
            String fileSeq = f.getFileSeq();
            if(StringUtils.isNotBlank(fileSeq)){
                f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return list;
    }

}