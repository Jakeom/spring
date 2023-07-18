package com.fw.api.v1.position.service;

import com.fw.core.dto.api.*;
import com.fw.core.mapper.db1.api.ApiMemberMapper;
import com.fw.core.mapper.db1.api.ApiPositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author YJW
 * @since 2022.10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiPositionService {

    private final ApiPositionMapper apiPositionMapper;
    private final ApiMemberMapper apiMemberMapper;

    /** 포지션(채용공고) 리스트 조회 */
    public List<ApiPositionDTO> selectPositionList(ApiPositionDTO apiPositionDTO){

            /* searchApply = "Y" -> 내가 지원한 리스트 조회 (후보자) */
            String searchApply = apiPositionDTO.getSearchApply();
            if(Objects.equals(searchApply, "Y")) {
                List<ApiPositionDTO> list = apiPositionMapper.selectApplyPositionList(apiPositionDTO);
                for(ApiPositionDTO ap : list) {
                   // 본인지원/제안받은포지션지원 - regPath
                   if(StringUtils.equals(ap.getRegPath(),"2")) {
                       ap.setReceiptPath("본인지원");
                   } else {
                       ap.setReceiptPath("제안받은포지션지원");
                   }

                   // 불합격/합격/전형중 - 전형상태 분류
                   String progressStep = ap.getProgressStepCd();
                   String processStatus = ap.getProcessStatusCd();
                   String proposalStatus = ap.getProposalStatusCd();

                   if(StringUtils.equals(progressStep,"PASS")) { // 합격
                       ap.setProcessStatus("합격");
                   }

                   if(StringUtils.equals(progressStep,"FAIL") || StringUtils.equals(processStatus,"FAIL") || StringUtils.equals(processStatus,"DOCUMENT_FAIL")) { // 불합격
                       ap.setProcessStatus("불합격");
                   }

                   if(!StringUtils.equals(progressStep,"PASS") && !StringUtils.equals(progressStep,"FAIL") &&
                           !StringUtils.equals(processStatus,"FAIL") && !StringUtils.equals(proposalStatus,"REJECT") && !StringUtils.equals(processStatus,"DOCUMENT_FAIL")) { // 전형중
                       ap.setProcessStatus("전형중");
                   }

                    // 합격후기 작성여부
                    apiPositionDTO.setCompanySeq(ap.getCompanySeq());
                    boolean review = apiPositionMapper.selectPassReviewExists(apiPositionDTO);
                    if(review) { // 작성이력존재
                        ap.setReviewFlag("Y");
                    } else { // 작성이력존재X
                        ap.setReviewFlag("N");
                    }

                }
                return list;
            }

            /* searchScrap = "Y" -> 내가 스크랩한 리스트 조회 (후보자) */
            String searchScrap = apiPositionDTO.getSearchScrap();
            if(Objects.equals(searchScrap,"Y")) {
                return apiPositionMapper.selectScrapPositionList(apiPositionDTO);
            }

            /* searchContact = "Y" Contact한 리스트 조회 (헤드헌터) */
            String searchContact = apiPositionDTO.getSearchContact();
            if(Objects.equals(searchContact, "Y")) {
                return apiPositionMapper.selectContactPositionList(apiPositionDTO);
            }

            /* searchHh = "Y" 내 포지션 관리 조회 (헤드헌터) */
            String searchHh = apiPositionDTO.getSearchHh();
            if(Objects.equals(searchHh, "Y")) {
                return apiPositionMapper.selectHhPositionList(apiPositionDTO);
            }

            /* positionId 파라미터O -> 상세 */
            String positionId = apiPositionDTO.getPositionId();
            if(StringUtils.isNotBlank(positionId)) {
                return apiPositionMapper.selectPositionDetail(apiPositionDTO);
            }

            /* memberId -> AP/HH 구분 */
            String memberId = apiPositionDTO.getMemberId();
            if(StringUtils.isNotBlank(memberId)) {
                String userType = apiMemberMapper.selectUsertype(memberId);
                apiPositionDTO.setUserType(userType);
            }

            List<ApiPositionDTO> positionList = apiPositionMapper.selectPositionList(apiPositionDTO);
            for(ApiPositionDTO ap : positionList) {
                if(!StringUtils.equals(ap.getPublicCd(),"0")) { // 공개가 아니면 전부 비공개
                    ap.setCompanyName("*****");
                }
            }
        return positionList;
    }

    /** 포지션(채용공고) 리스트 COUNT */
    public int selectPositionCnt(ApiPositionDTO apiPositionDTO){

        /* searchApply = "Y" -> 내가 지원한 리스트 조회 (후보자) */
        String searchApply = apiPositionDTO.getSearchApply();
        if(Objects.equals(searchApply, "Y")) {
            return apiPositionMapper.selectApplyPositionCnt(apiPositionDTO);
        }

        /* searchScrap = "Y" -> 내가 스크랩한 리스트 조회 (후보자) */
        String searchScrap = apiPositionDTO.getSearchScrap();
        if(Objects.equals(searchScrap,"Y")) {
            return apiPositionMapper.selectScrapPositionCnt(apiPositionDTO);
        }

        /* searchContact = "Y" Contact한 리스트 조회 (헤드헌터) */
        String searchContact = apiPositionDTO.getSearchContact();
        if (Objects.equals(searchContact, "Y")) {
            return apiPositionMapper.selectContactPositionCnt(apiPositionDTO);
        }

        /* searchHh = "Y" 내 포지션 관리 조회 (헤드헌터) */
        String searchHh = apiPositionDTO.getSearchHh();
        if(Objects.equals(searchHh, "Y")) {
            return apiPositionMapper.selectHhPositionCnt(apiPositionDTO);
        }

        /* positionId 파라미터O -> 상세 */
        String positionId = apiPositionDTO.getPositionId();
        if(StringUtils.isNotBlank(positionId)) {
            return 1;
        }

        /* memberId -> AP/HH 구분 */
        String memberId = apiPositionDTO.getMemberId();
        if(StringUtils.isNotBlank(memberId)) {
            String userType = apiMemberMapper.selectUsertype(memberId);
            apiPositionDTO.setUserType(userType);
        }

        return apiPositionMapper.selectPositionCnt(apiPositionDTO);
    }

    /** 포지션 상세 조회 */
    public ApiPositionDTO selectPositionInfo(ApiPositionDTO apiPositionDTO){
        return apiPositionMapper.selectPositionInfo(apiPositionDTO);
    }

    /** 포지션(채용공고) 수정 (마감일연장) */
    public void updatePosition(ApiPositionDTO apiPositionDTO) {
        apiPositionMapper.updatePosition(apiPositionDTO);
    }

    /** 포지션(채용공고) 스크랩 */
    public void insertScrap(ApiScrapPositionDTO apiScrapPositionDTO) {
        /* 스크랩 이력 조회 */
        boolean isScrap = apiPositionMapper.selectIsScrap(apiScrapPositionDTO);
        if(!isScrap) { // 스크랩 이력X -> INSERT
            apiPositionMapper.insertScrap(apiScrapPositionDTO);
        } else { // 스크랩 이력O -> 스크랩취소 (update delFlag)
            apiPositionMapper.updateScrap(apiScrapPositionDTO);
        }
    }

    /** 포지션(채용공고) 열람 이력 등록  */
    public void insertPositionHistory(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO) {
        /* 열람이력 조회 */
        boolean isRead = apiPositionMapper.selectIsRead(apiPositionReadingHistoryDTO);
        if(!isRead) { // 열람이력X -> INSERT
            apiPositionMapper.insertPositionHistory(apiPositionReadingHistoryDTO);
        } else { // 열람이력O -> 최근 열람시간 갱신
            apiPositionMapper.updatePositionHistory(apiPositionReadingHistoryDTO);
        }
    }

    /** 포지션(채용공고) 열람 이력 조회 / COUNT */
    public List<ApiPositionReadingHistoryDTO> selectPositionReadingHistory(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO){
        return apiPositionMapper.selectPositionReadingHistory(apiPositionReadingHistoryDTO);
    }
    public int selectPositionReadingHistoryCnt(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO) {
        return apiPositionMapper.selectPositionReadingHistoryCnt(apiPositionReadingHistoryDTO);
    }

    /** 포지션(채용공고) 지원 */
    public void insertPositionApply(ApiPositionApplicantDTO apiPositionApplicantDTO) {
        apiPositionMapper.insertPositionApply(apiPositionApplicantDTO);
    }

    /** 포지션(채용공고) 채용현황 조회 (headhunter) */
    public ApiPositionStatusDTO selectPositionStatus(ApiPositionStatusDTO apiPositionStatusDTO){
        return apiPositionMapper.selectPositionStatus(apiPositionStatusDTO);
    }

    /** 이력서 수정권한 여부 설정 */
    public void updateModifyFlag(ApiPositionApplicantDTO apiPositionApplicantDTO){
        apiPositionMapper.updateModifyFlag(apiPositionApplicantDTO);
    }

    public ApiPositionApplicantDTO selectPositionHh(ApiPositionApplicantDTO apiPositionApplicantDTO) {
        return apiPositionMapper.selectPositionHh(apiPositionApplicantDTO);
    }

    /** 마감일 연장 횟수 조회 */
    public Integer selectEndAddCnt(ApiPositionDTO apiPositionDTO) {
        return apiPositionMapper.selectEndAddCnt(apiPositionDTO);
    }
}
