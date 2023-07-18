package com.fw.core.mapper.db1.m;

import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MResumeMapper {

    FoPointDTO selectMyPoint(FoPointDTO foPointDTO);

    void updatePoint(FoPointDTO foPointDTO);

    void insertPointUseHistory(FoPointDTO foPointDTO);
    void updateHhResumeReadingHistory(FoPointDTO foPointDTO);

    void insertHhResumeReadingHistory(FoPointDTO foPointDTO);

    FoResumeDTO selectTicketInfo(FoResumeDTO foResumeDTO);

    boolean selectTicketExist(FoResumeDTO foResumeDTO);

    void deleteMyPool(FoResumeDTO foResumeDTO);
    FoResumeDTO selectReward(FoResumeDTO foResumeDTO);
    void insertReward(FoResumeDTO foResumeDTO);
    void updateReward(FoResumeDTO foResumeDTO);
    void insertRewardHistory(FoResumeDTO foResumeDTO);

}
