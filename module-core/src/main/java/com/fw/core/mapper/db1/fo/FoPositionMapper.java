package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.dto.fo.FoPositionApplicantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoPositionMapper {

    List<FoPositionDTO> selectPositionList(FoPositionDTO foPositionDTO);
    int selectPositionListCntForPaging(FoPositionDTO foPositionDTO); // 채용공고 리스트 카운트 취득
    List<FoPositionDTO> selectPositionListPaging(FoPositionDTO foPositionDTO); // 채용공고 리스트 취득
    FoPositionDTO selectPositionInfo(FoPositionDTO foPositionDTO); // 채용공고 상세보기
    void insertPositionApplicant(FoPositionApplicantDTO foPositionApplicantDTO); // 채용공고 지원하기
    void updateApplicant(FoPositionApplicantDTO foPositionApplicantDTO); // 이력서 수정동의
    void insertScrap(FoPositionDTO foPositionDTO); // 스크랩 하기
    void updateScrap(FoPositionDTO foPositionDTO); // 스크랩 취소
    int selectApplicant(FoPositionDTO foPositionDTO); // 채용공고 리스트 카운트 취득
    int selectScrapPositionListCntForPaging(FoPositionDTO foPositionDTO); // 스크랩 채용공고 리스트 카운트 취득
    List<FoPositionDTO> selectScrapPositionListPaging(FoPositionDTO foPositionDTO); // 스크랩 채용공고 리스트 취득
    List<FoPositionDTO> selectMyPassPosition(FoPositionDTO foPositionDTO); // 최종합격 채용공고
    boolean selectPositionExistByCompany(FoPositionDTO foPositionDTO); // 포지션 존재유무 조회 by 회사
    List<FoPositionDTO> selectMyPassPositionNotReview(FoPositionDTO foPositionDTO); // 합격후기 작성이력 없는 최종합격 채용공고
    List<FoPositionDTO> selectDoingPositionList(FoPositionDTO foPositionDTO); // 진행중인 포지션 조회
    void updatePositionStatus(FoPositionDTO foPositionDTO); // 포지션 진행상태 변경
    void updatePositionApplicant(FoPositionDTO foPositionDTO);  // 이력서 및 동의서 업데이트
}
