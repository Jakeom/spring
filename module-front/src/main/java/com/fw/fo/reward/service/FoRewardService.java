package com.fw.fo.reward.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoRewardDTO;
import com.fw.core.mapper.db1.fo.FoRewardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class FoRewardService {

    private final FoRewardMapper foRewardMapper;
    private final CommonFileService commonFileService;

    public int selectRewardListCntForPaging(FoRewardDTO foRewardDTO){
        return foRewardMapper.selectRewardListCntForPaging(foRewardDTO);
    }
    public int selectRewardHistoryListCntForPaging(FoRewardDTO foRewardDTO){
        return foRewardMapper.selectRewardHistoryListCntForPaging(foRewardDTO);
    }
    public List<FoRewardDTO> selectRewardList(FoRewardDTO foRewardDTO){
        List<FoRewardDTO> list = foRewardMapper.selectRewardList(foRewardDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getFileSeq();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    public FoRewardDTO selectRewardDetail(FoRewardDTO foRewardDTO){
        FoRewardDTO f = foRewardMapper.selectRewardDetail(foRewardDTO);
        f.setCommonFileList(commonFileService.selectFileDetailList(f.getFileSeq()));
        return f;
    }

    public List<FoRewardDTO> selectRewardHistoryListPaging(FoRewardDTO foRewardDTO){
        return foRewardMapper.selectRewardHistoryListPaging(foRewardDTO);
    }

    //리워드 업데이트
    @Transactional
    public void updateReward(FoRewardDTO foRewardDTO){
        foRewardMapper.updateReward(foRewardDTO);
        foRewardMapper.insertRewardHistory(foRewardDTO);
    }

    //리워드 유무
    public int selectReward(FoRewardDTO foRewardDTO){
        return foRewardMapper.selectReward(foRewardDTO);
    }

    //리워드 insert
    public void insertReward(FoRewardDTO foRewardDTO){
        foRewardMapper.insertReward(foRewardDTO);
    }

}