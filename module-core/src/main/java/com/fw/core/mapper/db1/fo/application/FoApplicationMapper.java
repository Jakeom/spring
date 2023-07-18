package com.fw.core.mapper.db1.fo.application;

import com.fw.core.dto.fo.FoPositionApplicantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoApplicationMapper {

    // 지원내역 카운트 취득
    public int selectApplicationListCntForPaging(FoPositionApplicantDTO foPoApplicantDTO);

    // 입사지원 조회 페이징
    public List<FoPositionApplicantDTO> selectApplicationListPaging(FoPositionApplicantDTO foPoApplicantDTO);

    // 내 이력서 열람내역 카운트 취득
    public int selectResumeReadingHistoryCntForPaging(FoPositionApplicantDTO foPoApplicantDTO);

    // 내 이력서 열람내역 페이징
    public List<FoPositionApplicantDTO> selectResumeReadingHistoryPaging(FoPositionApplicantDTO foPoApplicantDTO);
    void updateProposalAcceptance(FoPositionApplicantDTO foPositionApplicantDTO); // 포지션 제안 거절/수락
    FoPositionApplicantDTO selectPositionApplicantInfo(FoPositionApplicantDTO foPositionApplicantDTO);

}
