package com.fw.fo.headhunter.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.headhunter.FoSearchHhDTO;
import com.fw.core.mapper.db1.fo.searchHh.FoSearchHhMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoHeadhunterService {

    private final FoSearchHhMapper foSearchHhMapper;
    private final CommonFileService commonFileService;

    public int selectSearchHhListCntForPaging(FoSearchHhDTO foSearchHhDTO){
        return foSearchHhMapper.selectSearchHhListCntForPaging(foSearchHhDTO);
    }

    public List<FoSearchHhDTO> selectSearchHhListPaging(FoSearchHhDTO foSearchHhDTO){
        List<FoSearchHhDTO> list = foSearchHhMapper.selectSearchHhListPaging(foSearchHhDTO);
        for(int i=0; i<list.size();i++){
        String fileSeq = list.get(i).getProfilePictureFileId();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    public FoSearchHhDTO selectHhInfo(FoSearchHhDTO foSearchHhDTO){
        FoSearchHhDTO f = foSearchHhMapper.selectHhInfo(foSearchHhDTO);
        if(!Objects.isNull(f)){
            String fileSeq = f.getProfilePictureFileId();
            if(StringUtils.isNotBlank(fileSeq)){
                f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return f;
        //return foSearchHhMapper.selectHhInfo(foSearchHhDTO);
    }

    public List<FoSearchHhDTO> selectHhFieldInfo(FoSearchHhDTO foSearchHhDTO){
        return foSearchHhMapper.selectHhFieldInfo(foSearchHhDTO);
    }

    public void insertInterestHh(FoSearchHhDTO foSearchHhDTO){
      foSearchHhMapper.insertInterestHh(foSearchHhDTO);
    }

    public void updateInterestHh(FoSearchHhDTO foSearchHhDTO){
        foSearchHhMapper.updateInterestHh(foSearchHhDTO);
    }

}