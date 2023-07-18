package com.fw.m.user.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.mapper.db1.fo.FoPositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MUserService {

    private final CommonFileService commonFileService;
    private final FoPositionMapper foPositionMapper;

    @Transactional
    public void updateResume(FoPositionDTO foPositionDTO) throws Exception {
        int agreeFileId = commonFileService.fileUpload(foPositionDTO.getAgreeFiles(), "USER_AGREE");
        int resumeFileId = commonFileService.fileUpload(foPositionDTO.getResumeFiles(), "USER_RESUME");
        foPositionDTO.setAgreeFileId(String.valueOf(agreeFileId));
        foPositionDTO.setResumeFileId(String.valueOf(resumeFileId));
        foPositionMapper.updatePositionApplicant(foPositionDTO);
    }

}
