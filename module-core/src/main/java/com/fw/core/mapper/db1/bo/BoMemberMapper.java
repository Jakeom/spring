package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 * @author dongbeom
 */
@Mapper
public interface BoMemberMapper {

    List<BoMemberDTO> selectSearchMemberList(BoMemberDTO boMemberDTO);

    List<BoMemberDTO> selectMemberList(BoMemberDTO boMemberDTO);

    BoMemberDTO selectHeadHunterMemberList(BoMemberDTO boMemberDTO);

    List<BoResumeDTO> selectResumeList(BoResumeDTO boResumeDTO);

    List<BoResumeDTO> selectResumeOtherOption(BoResumeDTO boResumeDTO);
    List<BoResumeDTO> selectResumeRepresentationOption(BoResumeDTO boResumeDTO);
    List<BoResumeDTO> selectResumeApplicantOption(BoResumeDTO boResumeDTO);

    BoMemberDTO selectMember(BoMemberDTO boMemberDTO);

    BoMemberDTO selectMemberInfo(BoMemberDTO boMemberDTO);

    List<BoPositionDTO> selectPositionList(BoPositionDTO boPositionDTO);

    List<BoPositionDTO> selectPositionInfo(BoPositionDTO boPositionDTO);

    List<BoPositionDTO> selectPositionInfoOption(BoPositionDTO boPositionDTO);

    List<BoHhResumeReadingHistoryDTO> selectHhResumeReadingHistoryList(BoHhResumeReadingHistoryDTO boHhResumeReadingHistoryDTO);

    List<BoHhResumeReadingHistoryDTO> selectHhResumeReadingHistoryOption(BoHhResumeReadingHistoryDTO boHhResumeReadingHistoryDTO);

    int selectSearchMemberListCnt(BoMemberDTO boMemberDTO);

    void updateMember(BoMemberDTO boMemberDTO);

    void updateMemberMarketing(BoMemberDTO boMemberDTO);

    void updateApplicant(BoMemberDTO boMemberDTO);

    void updateHHMember(BoMemberDTO boMemberDTO);

    void updateHHMemberMarketing(BoMemberDTO boMemberDTO);

    void updateHeadHunter(BoMemberDTO boMemberDTO);

    void updateIsStop(BoMemberDTO boMemberDTO);

    void updateIsStopNo(BoMemberDTO boMemberDTO);

    void deleteIstemp(BoMemberDTO boMemberDTO);

    void updateDeleted(BoMemberDTO boMemberDTO);

    void updateRestore(BoMemberDTO boMemberDTO);

    void updatePassword(BoMemberDTO boMemberDTO);

    BoMemberDTO selectMemberWithName(BoMemberDTO boMemberDTO);
}
