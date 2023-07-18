package com.fw.m.community.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.*;
import com.fw.core.dto.hh.HhCompanyDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import com.fw.core.mapper.db1.fo.FoRewardMapper;
import com.fw.core.mapper.db1.hh.HhCompanyMapper;
import com.fw.core.mapper.db1.m.MCommunityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MCommunityService {

    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    private final FoMemberMapper foMemberMapper;
    private final MCommunityMapper mCommunityMapper;
    private final CommonFileService commonFileService;
    private final FoRewardMapper foRewardMapper;
    private final CommonMapper commonMapper;
    private final CommonService commonService;
    private final HhCompanyMapper hhCompanyMapper;

    public void insertCommunity(FoTbCommunityDTO foTbCommunityDTO) throws Exception {
        Integer fileSeq = commonFileService.fileUpload(foTbCommunityDTO.getFiles(), "COMMUNITY");
        foTbCommunityDTO.setFileSeq(String.valueOf(fileSeq));
        foTbCommunityDTO.setDtype(foMemberMapper.selectDtype(foTbCommunityDTO.getMemberId())); // set dtype
        mCommunityMapper.insertCommunity(foTbCommunityDTO);

        // R9 개선사항 요청 글등록 push알림 (관리자)
        if (StringUtils.equals(foTbCommunityDTO.getCommunityType(), "R9_IMPROVE")) {
            List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
            for(FoMemberDTO admin : adminList) {
                PushHistDTO push = PushHistDTO.builder()
                        .memberId(admin.getApprovalSeq())
                        .dispType("R9_IMPROVE_REQUEST")
                        .title("RESUME9 개선사항 요청글")
                        .content("RESUME9 개선사항 요청글이 등록되었습니다.")
                        .createId(foTbCommunityDTO.getMemberId()).build();
                commonService.insertPushHist(push);
            }
        }

        if (StringUtils.equals(foTbCommunityDTO.getCommunityType(), "COMPANY_CURIOUS")) { // 포지션 등록 헤드헌터 회사정보 질문 push 알림
            // 같은 사업자번호로 등록된 고객사의 헤드헌터에게 푸쉬전송
            HhCompanyDTO hhCompanyDTO = new HhCompanyDTO();
            hhCompanyDTO.setId(foTbCommunityDTO.getCompanySeq());
            HhCompanyDTO companyInfo = hhCompanyMapper.selectHrCompanyDetail(hhCompanyDTO);
            if(companyInfo != null) {
                hhCompanyDTO.setLicenseNumber(companyInfo.getLicenseNumber());
                List<HhCompanyDTO> headhunter = hhCompanyMapper.selectCompanyDetailByLicense(hhCompanyDTO);
                for (HhCompanyDTO hh : headhunter) {
                    PushHistDTO push = PushHistDTO.builder()
                            .dispType("COMPANY_QUESTION")
                            .memberId(hh.getMemberId())
                            .title(companyInfo.getCompanyName())
                            .content("RESUME9 APP에서 답변을 달아주세요.")
                            .createId(foTbCommunityDTO.getMemberId())
                            .url(WEB_DOMAIN + "/m/community/detail?communitySeq=" + foTbCommunityDTO.getCommunitySeq() + "&memberId=" + hh.getMemberId()).build();
                    commonService.insertPushHist(push);
                }
            }
        }

        if(StringUtils.equals(foTbCommunityDTO.getCommunityType(),"PASS_REVIEW")) { // 합격후기 등록 시 포인트지급
            FoSessionDTO sessionDTO = new FoSessionDTO();
            sessionDTO.setId(foTbCommunityDTO.getMemberId());

            FoRewardDTO foRewardDTO = new FoRewardDTO();
            foRewardDTO.setFrontSession(sessionDTO);

            if(foRewardMapper.selectReward(foRewardDTO) == 0) {
                foRewardMapper.insertReward(foRewardDTO);
            }
            foRewardDTO.setIncrease("3000");
            foRewardDTO.setGoodsCd("REVIEW_GIVE");
            foRewardDTO.setReasonCd("REWARD");
            foRewardMapper.updateReward(foRewardDTO);
        }
    }

    public void insertCommunityComment(FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        mCommunityMapper.insertCommunityComment(foTbCommunityCommentDTO);

        // 게시글 상세 정보 조회
        FoTbCommunityDTO foTbCommunityDTO = new FoTbCommunityDTO();
        foTbCommunityDTO.setCommunitySeq(foTbCommunityCommentDTO.getCommunitySeq());
        FoTbCommunityDTO data = mCommunityMapper.selectCommunityDetail(foTbCommunityDTO);

        if (!StringUtils.equals(foTbCommunityDTO.getMemberId(), data.getCreateSeq())) {
            String dispType = "";
            if(StringUtils.equals(data.getDtype(),"AP")) {
                dispType = "AP_COMMENT";
            } else {
                dispType = "HH_COMMENT";
            }
            // 댓글 등록 시 해당 게시글 작성자에게 push 알림 전송
            PushHistDTO push = PushHistDTO.builder()
                    .dispType(dispType)
                    .memberId(data.getCreateSeq())
                    .title("[" + data.getCommunityTypeNm() + "]" + " " + "[" + data.getTitle() + "]")
                    .content("커뮤니티에 댓글이 달렸습니다.")
                    .createId(foTbCommunityCommentDTO.getMemberId())
                    .url(WEB_DOMAIN + "/m/community/detail?communitySeq=" + data.getCommunitySeq() + "&memberId=" + data.getCreateSeq()).build();
            commonService.insertPushHist(push);
        }

        // R9 개선사항 요청 댓글 push 알림 (관리자)
        if (StringUtils.equals(data.getCommunityType(), "R9_IMPROVE")) {
            List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
            for(FoMemberDTO admin : adminList) {
                PushHistDTO push = PushHistDTO.builder()
                        .dispType("R9_IMPROVE_REQUEST")
                        .memberId(admin.getApprovalSeq())
                        .title("RESUME9 개선사항 요청 댓글")
                        .content("RESUME9 개선사항 요청 댓글이 등록되었습니다.")
                        .createId(foTbCommunityCommentDTO.getMemberId()).build();
                commonService.insertPushHist(push);
            }
        }
    }

    public FoTbCommunityDTO selectCommunityDetail(FoTbCommunityDTO foTbCommunityDTO) {
        FoTbCommunityDTO f = mCommunityMapper.selectCommunityDetail(foTbCommunityDTO);
        f.setCommonFileList(commonFileService.selectFileDetailList(f.getFileSeq()));
        return f;
    }

    public void updateRecommend(FoTbCommunityDTO tbCommunityDTO) {
        mCommunityMapper.updateRecommend(tbCommunityDTO);
    }

    public void updateCommunityHit(FoTbCommunityDTO tbCommunityDTO) {
        mCommunityMapper.updateCommunityHit(tbCommunityDTO);
    }

    public List<FoTbCommunityCommentDTO> selectCommunityCommentList(FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        List<FoTbCommunityCommentDTO> communityList = mCommunityMapper.selectCommunityCommentList(foTbCommunityCommentDTO);
        for(FoTbCommunityCommentDTO ftccd : communityList) {
            int dateDiff = Integer.parseInt(ftccd.getDateDiff());
            if(dateDiff < 60) {
                ftccd.setDateDiff(dateDiff + "분 전");
            } else if(dateDiff < (60 * 24)) {
                dateDiff = dateDiff / 60;
                ftccd.setDateDiff(dateDiff + "시간 전");
            }  else {
                String []tokens = ftccd.getCreateDt().split(" ");
                ftccd.setDateDiff(tokens[0]);
            }
        }

        return this.getSubList(0, null, communityList);
    }

    public void insertCommunityRecommend(FoTbCommunityDTO tbCommunityDTO) {
        FoTbCommunityDTO communityDTO = mCommunityMapper.selectRecommend(tbCommunityDTO);
        if (communityDTO != null) {
            tbCommunityDTO.setCommunityRecommendSeq(communityDTO.getCommunityRecommendSeq());
            mCommunityMapper.deleteCommunityRecommend(tbCommunityDTO);
            tbCommunityDTO.setRecommend("-1");
            if(tbCommunityDTO.getCommunityCommentSeq() != null && !tbCommunityDTO.getCommunityCommentSeq().isEmpty()){
                mCommunityMapper.updateRecommendComment(tbCommunityDTO);
            } else {
                mCommunityMapper.updateRecommend(tbCommunityDTO);
            }

        } else {
            mCommunityMapper.insertCommunityRecommend(tbCommunityDTO);
            tbCommunityDTO.setRecommend("1");
            if(tbCommunityDTO.getCommunityCommentSeq() != null && !tbCommunityDTO.getCommunityCommentSeq().isEmpty()){
                mCommunityMapper.updateRecommendComment(tbCommunityDTO);
            } else {
                mCommunityMapper.updateRecommend(tbCommunityDTO);
            }
        }
    }

    public List<FoTbCommunityCommentDTO> getSubList(int depth, String seq, List<FoTbCommunityCommentDTO> communityList){
        List<FoTbCommunityCommentDTO> subList = new ArrayList<>();
        for(FoTbCommunityCommentDTO f : communityList) {
            if(((Integer.parseInt(f.getDepth()) == depth) && StringUtils.equals(f.getParentCommunityCommentSeq(), seq) && StringUtils.isNotBlank(seq)) ||
                    (Integer.parseInt(f.getDepth()) == depth) && StringUtils.isBlank(seq)){
                subList.add(f);
                f.setSubList(getSubList(Integer.parseInt(f.getDepth()) + 1, f.getCommunityCommentSeq(), communityList));
            }
        }
        return subList;
    }

    public List<FoTbCommunityDTO> selectCommunityCategory(FoTbCommunityDTO foTbCommunityDTO) {
        // AP/HH 구분
        foTbCommunityDTO.setDtype(foMemberMapper.selectDtype(foTbCommunityDTO.getMemberId()));
        return mCommunityMapper.selectCommunityCategory(foTbCommunityDTO);
    }
}
