package com.fw.fo.position.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoBlacklistHhDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoPositionApplicantDTO;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.dto.fo.resume.FoHhReadingHistoryDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.fo.FoBlacklistHhMapper;
import com.fw.core.mapper.db1.fo.FoHhReadingHistoryMapper;
import com.fw.core.mapper.db1.fo.FoPositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoPositionService {

    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    private final FoPositionMapper foPositionMapper;
    private final FoHhReadingHistoryMapper foHhReadingHistoryMapper;
    private final FoBlacklistHhMapper foBlacklistHhMapper;
    private final CommonMapper commonMapper;
    private final CommonService commonService;

    private final CommonFileService commonFileService;

    public List<FoPositionDTO> selectPositionList(FoPositionDTO foPositionDTO){
        return foPositionMapper.selectPositionList(foPositionDTO);
    }

    public int selectPositionListCntForPaging(FoPositionDTO foPositionDTO){
        return foPositionMapper.selectPositionListCntForPaging(foPositionDTO);
    }

    public List<FoPositionDTO> selectPositionListPaging(FoPositionDTO foPositionDTO){
        return foPositionMapper.selectPositionListPaging(foPositionDTO);
    }

    public FoPositionDTO selectPositionInfo(FoPositionDTO foPositionDTO){
        FoPositionDTO f = foPositionMapper.selectPositionInfo(foPositionDTO);
        if(!Objects.isNull(f)){
            String fileSeq = f.getProfilePictureFileId();
            if(StringUtils.isNotBlank(fileSeq)){
                f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return f;
    }

    public void insertPositionApplicant(FoPositionApplicantDTO foPositionApplicantDTO){
        foPositionMapper.insertPositionApplicant(foPositionApplicantDTO);

        // 지원자 정보조회
        FoMemberDTO foMemberDTOApplicant = new FoMemberDTO();
        foMemberDTOApplicant.setMemberId(foPositionApplicantDTO.getFrontSession().getId());
        FoMemberDTO applicant = commonMapper.selectMemberInfoByMemberId(foMemberDTOApplicant);

        // 포지션 정보조회
        FoPositionDTO foPositionDTO = new FoPositionDTO();
        foPositionDTO.setId(foPositionApplicantDTO.getPositionId());
        FoPositionDTO position = foPositionMapper.selectPositionInfo(foPositionDTO);

        String content = "";
        if (StringUtils.isBlank(applicant.getBirth())) {
            content = applicant.getName() + "님이 해당포지션에 이력서를 접수했습니다.";
        } else {
            content = applicant.getName() + " " + applicant.getYear() + "(" + applicant.getAge() + ")" + "님이 해당포지션에 이력서를 접수했습니다.";
        }

        // 이력서 접수알림 push
        PushHistDTO push = PushHistDTO.builder()
                .dispType("RESUME_RECEIPT")
                .memberId(foPositionApplicantDTO.getHhMemberId())
                .title(position.getTitle())
                .content(content)
                .createId(foPositionApplicantDTO.getFrontSession().getId())
                .url(WEB_DOMAIN + "/m/resume/resume-detail-ap?resumeId=" + foPositionApplicantDTO.getResumeId()).build();
        commonService.insertPushHist(push);
    }

    public void updateApplicant(FoPositionApplicantDTO foPositionApplicantDTO){
        foPositionMapper.updateApplicant(foPositionApplicantDTO);
    }

    public void insertHhReadingHistory(FoHhReadingHistoryDTO foHhReadingHistoryDTO){
        foHhReadingHistoryMapper.insertHhReadingHistory(foHhReadingHistoryDTO);
    }

    public void insertBlacklistHh(FoBlacklistHhDTO foBlacklistHhDTO){
        foBlacklistHhMapper.insertBlacklistHh(foBlacklistHhDTO);
    }

    public void insertScrap(FoPositionDTO foPositionDTO){
        foPositionMapper.insertScrap(foPositionDTO);
    }

    public void updateScrap(FoPositionDTO foPositionDTO){
        foPositionMapper.updateScrap(foPositionDTO);
    }

    public int selectApplicant(FoPositionDTO foPositionDTO){
        return foPositionMapper.selectApplicant(foPositionDTO);
    }
    public int selectScrapPositionListCntForPaging(FoPositionDTO foPositionDTO){
        return foPositionMapper.selectScrapPositionListCntForPaging(foPositionDTO);
    }

    public List<FoPositionDTO> selectScrapPositionListPaging(FoPositionDTO foPositionDTO){
        return foPositionMapper.selectScrapPositionListPaging(foPositionDTO);
    }

    public List<FoPositionDTO> selectMyPassPosition(FoPositionDTO foPositionDTO) { return foPositionMapper.selectMyPassPosition(foPositionDTO); }

    public boolean selectPositionExistByCompany(FoPositionDTO foPositionDTO) {return foPositionMapper.selectPositionExistByCompany(foPositionDTO);}

    public List<FoPositionDTO> selectMyPassPositionNotReview(FoPositionDTO foPositionDTO) {
        return foPositionMapper.selectMyPassPositionNotReview(foPositionDTO);
    }
}