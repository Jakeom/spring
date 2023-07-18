package com.fw.m.resume.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import com.fw.core.mapper.db1.fo.resume.FoResumeMapper;
import com.fw.core.mapper.db1.hh.HhApSearchsMapper;
import com.fw.core.mapper.db1.m.MCommunityMapper;
import com.fw.core.mapper.db1.m.MResumeMapper;
import com.fw.fo.resume.service.FoFilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MResumeService {

    private final FoMemberMapper foMemberMapper;
    private final MCommunityMapper mCommunityMapper;
    private final CommonFileService commonFileService;
    private final MResumeMapper mResumeMapper;
    private final FoResumeMapper foResumeMapper;
    private final HhApSearchsMapper  hhApSearchsMapper; 
    private final FoFilterService foFilterService;

    public FoPointDTO selectMyPoint(FoPointDTO foPointDTO) {
        return mResumeMapper.selectMyPoint(foPointDTO);
    }

    @Transactional
    public void updatePoint(FoPointDTO foPointDTO) {
        FoPointDTO myPoint = mResumeMapper.selectMyPoint(foPointDTO);

        if(myPoint != null) {
            int amount = 2000;
            if (StringUtils.isNotBlank(foPointDTO.getType())) { // 사용한 포인트 - amount
                amount = foPointDTO.getAmount();
            }

            Integer paidIncrease = amount - myPoint.getFreePoint(); // 무상지급 포인트 선 소멸 후 소멸되는 결제 포인트

            if (myPoint.getFreePoint() >= amount) { // 무상지급 포인트 >= amount
                foPointDTO.setFreeIncrease(-amount); // 무상지급 포인트 증감 -amount
                foPointDTO.setPaidIncrease(0);
            } else { // 무상지급 포인트 < amount
                foPointDTO.setFreeIncrease(-myPoint.getFreePoint());
                foPointDTO.setPaidIncrease(-paidIncrease);
            }
            mResumeMapper.updatePoint(foPointDTO); // 포인트 업데이트
            mResumeMapper.insertPointUseHistory(foPointDTO); // 포인트 사용내역 추가

            if (StringUtils.equals(foPointDTO.getType(), "SHOW_PHONE")) { // 연락처보기시
                FoResumeDTO foResumeDTO = new FoResumeDTO();
                foResumeDTO.setMemberId(foPointDTO.getMemberId());
                foResumeDTO.setResumeId(foPointDTO.getResumeId());
                foResumeDTO.setRegisterPathCd("POINT");
                boolean history = mResumeMapper.selectTicketExist(foResumeDTO);
                foPointDTO.setRegisterPathCd("POINT");
                if (history) {
                    mResumeMapper.updateHhResumeReadingHistory(foPointDTO); // 열람만료일 수정
                } else {
                    mResumeMapper.insertHhResumeReadingHistory(foPointDTO); // 이력서 열람내역 추가
                }

                // 리워드 지급
                FoResumeDTO f = mResumeMapper.selectReward(foResumeDTO);
                if(Objects.isNull(f)){
                    mResumeMapper.insertReward(foResumeDTO);
                } else {
                    foResumeDTO.setRewardId(f.getId());
                }

                foResumeDTO.setBalance("1000");
                foResumeDTO.setIncrease("1000");
                foResumeDTO.setReasonCd("HH_OPEN");
                mResumeMapper.updateReward(foResumeDTO);
                mResumeMapper.insertRewardHistory(foResumeDTO);

            }
        }
    }

    public FoResumeDTO selectTicketInfo(FoResumeDTO foResumeDTO) {
        return mResumeMapper.selectTicketInfo(foResumeDTO);
    }

    public boolean selectTicketExist(FoResumeDTO foResumeDTO) {
        return mResumeMapper.selectTicketExist(foResumeDTO);
    }

    public void deleteMyPool(FoResumeDTO foResumeDTO) {
        mResumeMapper.deleteMyPool(foResumeDTO);
    }


    @Transactional
    public FoResumeDTO updateResume(FoResumeDTO foResumeDTO) throws Exception{

            // 이력서 프로필 사진 업로드
            Integer profileFileId = commonFileService.fileUpload(foResumeDTO.getResumeImageFiles(), "RESUME_PROFILE");
            if(profileFileId != null){
                foResumeDTO.setPictureFileId(String.valueOf(profileFileId));
            }

            // 이력서 등록
            hhApSearchsMapper.insertResume(foResumeDTO);
            String resumeId = foResumeDTO.getId();

            // 핵심역량 등록
            for (FoResumeDTO f : foResumeDTO.getSpecList()) {
                f.setResumeId(resumeId);
                foResumeMapper.insertResumeSpec(f);
            }

            // 경력 저장
            for (FoResumeDTO f : foResumeDTO.getCareerList()) {
                f.setResumeId(resumeId);
                foResumeMapper.insertResumeCareer(f);
            }

            // 학력 저장
            String filterCd = "";
            for (FoResumeDTO f : foResumeDTO.getAcademicBackgroundList()) {
                f.setResumeId(resumeId);
                foResumeMapper.insertResumeAcademicBackground(f);

                // 전공 저장
                String academicBackgroundId = f.getId();
                for (FoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
                    if(StringUtils.isNotBlank(fm.getMajorName())) {
                        fm.setAcademicBackgroundId(academicBackgroundId);
                        foResumeMapper.insertResumeAcademicBackgroundMajor(fm);
                    }
                }

                String resultFilterCd = foFilterService.getUnivFilter(f);
                if(StringUtils.isNotBlank(resultFilterCd)){
                    filterCd = resultFilterCd;
                }
            }

            // 필터링 저장
            foResumeMapper.insertResumeFiltering(FoResumeDTO.builder()
                    .filterCd(filterCd)
                    .resumeId(resumeId)
                    .build());

            // 어학 저장
            if(StringUtils.isNotBlank(foResumeDTO.getLanguageInput())) {
                foResumeMapper.insertResumeLanguage(FoResumeDTO.builder()
                        .conversationCd(foResumeDTO.getConversationCd())
                        .languageCd(foResumeDTO.getLanguageCd())
                        .languageCertificationCd(foResumeDTO.getLanguageCertificationCd())
                        .languageInput(foResumeDTO.getLanguageInput())
                        .obtainYm(foResumeDTO.getObtainYm())
                        .testScore(foResumeDTO.getTestScore())
                        .testTypeCd(foResumeDTO.getTestTypeCd())
                        .resumeId(resumeId)
                        .build());
            }

            // 자격증 저장
            if(StringUtils.isNotBlank(foResumeDTO.getLicenseName())) {
                foResumeMapper.insertResumeLicense(FoResumeDTO.builder()
                        .agency(foResumeDTO.getAgency())
                        .name(foResumeDTO.getLicenseName())
                        .obtainYmLicense(foResumeDTO.getObtainYmLicense())
                        .resumeId(resumeId)
                        .build());
            }

            // 수상 저장
            if(StringUtils.isNotBlank(foResumeDTO.getAwardName())) {
                foResumeMapper.insertResumeAward(FoResumeDTO.builder()
                        .agency(foResumeDTO.getAgency())
                        .awardYm(foResumeDTO.getAwardYm())
                        .name(foResumeDTO.getAwardName())
                        .resumeId(resumeId)
                        .build());
            }

            // 외부활동 저장
            if(StringUtils.isNotBlank(foResumeDTO.getContent())) {
                foResumeMapper.insertResumeExternalActivity(FoResumeDTO.builder()
                        .content(foResumeDTO.getContent())
                        .resumeId(resumeId)
                        .build());
            }

            // 포트폴리오 저장
            int i = 0;
            for (FoResumeDTO f : foResumeDTO.getPortfolioList()) {
                f.setResumeId(resumeId);
                if(StringUtils.equals("FILE", f.getAttachType())) {
                    Integer portFolioId = commonFileService.fileUpload(new MultipartFile[]{ foResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
                    f.setAttachFileId(String.valueOf(portFolioId));
                    i++;
                }
                foResumeMapper.insertResumePortfolio(f);
            }

            // 영문이력서 저장
            Integer enResumeId = commonFileService.fileUpload(foResumeDTO.getEnResumeFile(), "EN_RESUME");
            if(enResumeId != null){
                foResumeMapper.insertResumeEnglish(FoResumeDTO.builder()
                        .attachType("FILE")
                        .url(null)
                        .attachFileId(String.valueOf(enResumeId))
                        .resumeId(resumeId)
                        .build());
            }

            // 지역 저장
            foResumeMapper.insertResumeLocation(FoResumeDTO.builder()
                    .desiredLocationCd(foResumeDTO.getDesiredLocationCd())
                    .resumeId(resumeId)
                    .build());

            // 지원서 업데이트
            int cnt = foResumeMapper.updateApplicantForResumeId(foResumeDTO);

            if(cnt == 0){
                throw new Exception("Fail Find Applicant");
            }


        return foResumeDTO;
    }


}
