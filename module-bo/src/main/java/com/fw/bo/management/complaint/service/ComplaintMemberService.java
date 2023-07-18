package com.fw.bo.management.complaint.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.bo.*;
import com.fw.core.mapper.db1.bo.BoMemberMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ComplaintMemberService {

    private final CommonFileService commonFileService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final BoMemberMapper boMemberMapper;

    public List<BoMemberDTO> selectSearchMemberList(BoMemberDTO boMemberDTO) {
        return boMemberMapper.selectSearchMemberList(boMemberDTO);
    }

    public List<BoMemberDTO> selectMemberList(BoMemberDTO boMemberDTO) {
        return boMemberMapper.selectMemberList(boMemberDTO);
    }

    public BoMemberDTO selectHeadHunterMemberList(BoMemberDTO boMemberDTO) {
        BoMemberDTO dto = boMemberMapper.selectHeadHunterMemberList(boMemberDTO);
        String fileSeq = dto.getSfWorkerListFileId();
        if(!Objects.isNull(fileSeq)){
            if(StringUtils.isNotBlank(fileSeq)){
                dto.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return dto;
    }

    public BoMemberDTO selectMember(BoMemberDTO boMemberDTO) {
        return boMemberMapper.selectMember(boMemberDTO);
    }

    public BoMemberDTO selectMemberInfo(BoMemberDTO boMemberDTO) {
        return boMemberMapper.selectMemberInfo(boMemberDTO);
    }

    public List<BoResumeDTO> selectResumeList(BoResumeDTO boResumeDTO) {
        return boMemberMapper.selectResumeList(boResumeDTO);
    }

    public List<BoResumeDTO> selectResumeOtherOption(BoResumeDTO boResumeDTO) {
        return boMemberMapper.selectResumeOtherOption(boResumeDTO);
    }

    public List<BoResumeDTO> selectResumeRepresentationOption(BoResumeDTO boResumeDTO) {
        return boMemberMapper.selectResumeRepresentationOption(boResumeDTO);
    }

    public List<BoResumeDTO> selectResumeApplicantOption(BoResumeDTO boResumeDTO) {
        return boMemberMapper.selectResumeApplicantOption(boResumeDTO);
    }

    public List<BoPositionDTO> selectPositionList(BoPositionDTO boPositionDTO) {
        return boMemberMapper.selectPositionList(boPositionDTO);
    }

    public List<BoPositionDTO> selectPositionInfo(BoPositionDTO boPositionDTO) {
        return boMemberMapper.selectPositionInfo(boPositionDTO);
    }

    public List<BoPositionDTO> selectPositionInfoOption(BoPositionDTO boPositionDTO) {
        return boMemberMapper.selectPositionInfoOption(boPositionDTO);
    }

    public List<BoHhResumeReadingHistoryDTO> selectHhResumeReadingHistoryList(BoHhResumeReadingHistoryDTO boHhResumeReadingHistoryDTO) {
        return boMemberMapper.selectHhResumeReadingHistoryList(boHhResumeReadingHistoryDTO);
    }

    public List<BoHhResumeReadingHistoryDTO> selectHhResumeReadingHistoryOption(BoHhResumeReadingHistoryDTO boHhResumeReadingHistoryDTO) {
        return boMemberMapper.selectHhResumeReadingHistoryOption(boHhResumeReadingHistoryDTO);
    }

    public int selectSearchMemberListCnt(BoMemberDTO boMemberDTO) {
        return boMemberMapper.selectSearchMemberListCnt(boMemberDTO);
    }

    public void updateMember(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateMember(boMemberDTO);
        boMemberMapper.updateApplicant(boMemberDTO);
    }

    public void updateMemberMarketing(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateMemberMarketing(boMemberDTO);
        boMemberMapper.updateApplicant(boMemberDTO);
    }

    public void updateHHMember(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateHHMember(boMemberDTO);
        boMemberMapper.updateHeadHunter(boMemberDTO);
    }

    public void updateHHMemberMarketing(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateHHMemberMarketing(boMemberDTO);
        boMemberMapper.updateHeadHunter(boMemberDTO);
    }

    public void updateIsStop(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateIsStop(boMemberDTO);
    }

    public void updateIsStopNo(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateIsStopNo(boMemberDTO);
    }

    public void deleteIstemp(BoMemberDTO boMemberDTO) {
        boMemberMapper.deleteIstemp(boMemberDTO);
    }

    public void updatePassword(BoMemberDTO boMemberDTO) {
        boMemberDTO.setPassword(bCryptPasswordEncoder.encode(boMemberDTO.getPassword()));
        boMemberMapper.updatePassword(boMemberDTO);
    }

    public void updateDeleted(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateDeleted(boMemberDTO);
    }

    public void updateRestore(BoMemberDTO boMemberDTO) {
        boMemberMapper.updateRestore(boMemberDTO);
    }

    /**
     * 아이디 찾기
     **/
    public BoMemberDTO selectMemberWithName(BoMemberDTO boMemberDTO) {
        return boMemberMapper.selectMemberWithName(boMemberDTO);
    }
}
