package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoRewardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoRewardMapper {
    public int selectRewardListCntForPaging(FoRewardDTO foRewardDTO);
    public int selectRewardHistoryListCntForPaging(FoRewardDTO foRewardDTO);
    public List<FoRewardDTO> selectRewardList(FoRewardDTO foRewardDTO);
    public FoRewardDTO selectRewardDetail(FoRewardDTO foRewardDTO);
    public List<FoRewardDTO> selectRewardHistoryListPaging(FoRewardDTO foRewardDTO);
    public void updateReward(FoRewardDTO foRewardDTO);
    public void insertRewardHistory(FoRewardDTO foRewardDTO);
    public int selectReward(FoRewardDTO foRewardDTO);
    public void insertReward(FoRewardDTO foRewardDTO);


}
