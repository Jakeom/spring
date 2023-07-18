package com.fw.fo.application.service;

import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoPositionApplicantDTO;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.fo.FoPositionMapper;
import com.fw.core.mapper.db1.fo.application.FoApplicationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoApplicationService {
    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    private final FoApplicationMapper foApplicationMapper;
    private final FoPositionMapper foPositionMapper;
    private final CommonMapper commonMapper;
    private final CommonService commonService;

    //지원내역 리스트 카운트 취득
    public int selectApplicationListCntForPaging(FoPositionApplicantDTO foPoApplicantDTO){
        return foApplicationMapper.selectApplicationListCntForPaging(foPoApplicantDTO);
    }

    //지원내역 리스트
    public List<FoPositionApplicantDTO> selectApplicationListPaging(FoPositionApplicantDTO foPoApplicantDTO){
        return foApplicationMapper.selectApplicationListPaging(foPoApplicantDTO);
    }

    //내 이력서 열람내역 리스트 카운트 취득
    public int selectResumeReadingHistoryCntForPaging(FoPositionApplicantDTO foPoApplicantDTO){
        return foApplicationMapper.selectResumeReadingHistoryCntForPaging(foPoApplicantDTO);
    }

    //내 이력서 열람내역 리스트
    public List<FoPositionApplicantDTO> selectResumeReadingHistoryPaging(FoPositionApplicantDTO foPoApplicantDTO){
        return foApplicationMapper.selectResumeReadingHistoryPaging(foPoApplicantDTO);
    }

    // 포지션 제안 수락/거절
    public void updateProposalAceeptance(FoPositionApplicantDTO foPositionApplicantDTO) {
        if(StringUtils.equals(foPositionApplicantDTO.getAcceptance(),"REJECT")) { // 제안거절
            foPositionApplicantDTO.setProposalStatus("REJECT");
        } else { // 제안수락
            foPositionApplicantDTO.setProposalStatus("ACCEPT");
            foPositionApplicantDTO.setProgressStep("REGISTER");
            foPositionApplicantDTO.setReceiptPath("PROPOSAL_ACCEPT"); // TODO :: 확인필요

            // 지원 정보조회
            FoPositionApplicantDTO applicantInfo = foApplicationMapper.selectPositionApplicantInfo(foPositionApplicantDTO);

            // 수신자 정보조회
            FoPositionDTO foPositionDTO = new FoPositionDTO();
            foPositionDTO.setId(applicantInfo.getPositionId());
            FoPositionDTO positionInfo = foPositionMapper.selectPositionInfo(foPositionDTO);

            // 지원자 정보조회
            FoMemberDTO foMemberDTO = new FoMemberDTO();
            foMemberDTO.setMemberId(applicantInfo.getApMemberId());
            FoMemberDTO applicant = commonMapper.selectMemberInfoByMemberId(foMemberDTO);

            if(applicant != null && positionInfo != null) {

                String content = "";
                if (StringUtils.isBlank(applicant.getBirth())) {
                    content = applicant.getName() + "님이 해당포지션에 이력서를 접수했습니다.";
                } else {
                    content = applicant.getName() + " " + applicant.getYear() + "(" + applicant.getAge() + ")" + "님이 해당포지션에 이력서를 접수했습니다.";
                }

                // 이력서 접수알림 push
                PushHistDTO push = PushHistDTO.builder()
                        .dispType("RESUME_RECEIPT")
                        .memberId(positionInfo.getMemberId())
                        .title(positionInfo.getTitle())
                        .content(content)
                        .createId(foPositionApplicantDTO.getFrontSession().getId())
                        .url(WEB_DOMAIN + "/m/resume/resume-detail-ap?resumeId=" + foPositionApplicantDTO.getResumeId()).build();
                commonService.insertPushHist(push);
            }
        }
        foApplicationMapper.updateProposalAcceptance(foPositionApplicantDTO);
    }

    // 포지션 지원 상세
    public FoPositionApplicantDTO selectPositionApplicantInfo(FoPositionApplicantDTO foPositionApplicantDTO) {
        return foApplicationMapper.selectPositionApplicantInfo(foPositionApplicantDTO);
    }

}