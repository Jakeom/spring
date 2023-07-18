package com.fw.fo.community.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoTbCommunityCommentDTO;
import com.fw.core.dto.fo.FoTbCommunityDTO;
import com.fw.core.dto.hh.HhCompanyDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.fo.FoCommunityMapper;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import com.fw.core.mapper.db1.hh.HhCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoCommunityService {

    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    private final FoMemberMapper foMemberMapper;
    private final FoCommunityMapper foCommunityMapper;
    private final CommonFileService commonFileService;
    private final CommonMapper commonMapper;
    private final CommonService commonService;
    private final HhCompanyMapper hhCompanyMapper;

    public void insertCommunity(FoTbCommunityDTO foTbCommunityDTO) throws Exception {
        Integer fileSeq = commonFileService.fileUpload(foTbCommunityDTO.getFiles(), "COMMUNITY");
        foTbCommunityDTO.setFileSeq(String.valueOf(fileSeq));
        foCommunityMapper.insertCommunity(foTbCommunityDTO);

        if(StringUtils.equals(foTbCommunityDTO.getCommunityType(),"COMPANY_CURIOUS")) { // 포지션 등록 헤드헌터 회사정보 질문 push 알림
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
                            .createId(foTbCommunityDTO.getFrontSession().getId())
                            .url(WEB_DOMAIN + "/m/community/detail?communitySeq=" + foTbCommunityDTO.getCommunitySeq() + "&memberId=" + hh.getMemberId()).build();
                    commonService.insertPushHist(push);
                }
            }
        }
    }

    public void insertCommunityComment(FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        foCommunityMapper.insertCommunityComment(foTbCommunityCommentDTO);

        // 게시글 상세 정보 조회
        FoTbCommunityDTO foTbCommunityDTO = new FoTbCommunityDTO();
        foTbCommunityDTO.setCommunitySeq(foTbCommunityCommentDTO.getCommunitySeq());
        FoTbCommunityDTO data = foCommunityMapper.selectCommunityDetail(foTbCommunityDTO);
        if (!StringUtils.equals(foTbCommunityCommentDTO.getFrontSession().getId(), data.getCreateSeq())) {
            // 댓글 등록 시 해당 게시글 작성자에게 push 알림 전송
            PushHistDTO push = PushHistDTO.builder()
                    .dispType("AP_COMMENT")
                    .memberId(data.getCreateSeq())
                    .title("[" + data.getCommunityTypeNm() + "]" + " " + "[" + data.getTitle() + "]")
                    .content("커뮤니티에 댓글이 달렸습니다.")
                    .createId(foTbCommunityCommentDTO.getFrontSession().getId())
                    .url(WEB_DOMAIN + "/m/community/detail?communitySeq=" + data.getCommunitySeq() + "&memberId=" + data.getCreateSeq()).build();
            commonService.insertPushHist(push);
        }
    }

    public List<FoTbCommunityDTO> selectCommunityList(FoTbCommunityDTO foTbCommunityDTO) throws ParseException {
        List<FoTbCommunityDTO> foTbCommunityDTO1 = foTbCommunityDTO.getCommunityType() ==null?
                foCommunityMapper.selectBestList(foTbCommunityDTO):foCommunityMapper.selectCommunityList(foTbCommunityDTO);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date time = new Date();
        Date nowDate = dateFormat.parse(dateFormat.format(time));
        for(int i = 0; i < foTbCommunityDTO1.size(); i++) {
            Date createDate = dateFormat.parse(foTbCommunityDTO1.get(i).getCreateDt());
            long diffDays  = (nowDate.getTime() - createDate.getTime()) / (24*60*60*1000);
            long diffMin = (nowDate.getTime() - createDate.getTime()) / 60000; //분 차이
            long diffHor = (nowDate.getTime() - createDate.getTime()) / 3600000; //시 차이
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if(diffDays < 1 && (formatter.format(createDate).equals(formatter.format(nowDate))) ) {
                if(diffHor >= 1) {
                    diffMin = diffMin - diffHor * 60;
                    foTbCommunityDTO1.get(i).setDifDate(diffHor + "시간 " + diffMin + "분 전");
                } else {
                    foTbCommunityDTO1.get(i).setDifDate(diffMin + "분 전");
                }
            } else {
                String []tokens=foTbCommunityDTO1.get(i).getCreateDt().split(" ");
                foTbCommunityDTO1.get(i).setDifDate(String.valueOf(tokens[0]));
            }
        }
        return foTbCommunityDTO1;
    }

    public FoTbCommunityDTO selectCommunityDetail(FoTbCommunityDTO foTbCommunityDTO) {
        FoTbCommunityDTO f = foCommunityMapper.selectCommunityDetail(foTbCommunityDTO);
        f.setCommonFileList(commonFileService.selectFileDetailList(f.getFileSeq()));
        return f;
    }

    public void updateRecommend(FoTbCommunityDTO tbCommunityDTO) {
        foCommunityMapper.updateRecommend(tbCommunityDTO);
    }

    public void updateCommunityHit(FoTbCommunityDTO tbCommunityDTO) {
        foCommunityMapper.updateCommunityHit(tbCommunityDTO);
    }

    public List<FoTbCommunityCommentDTO> selectCommunityCommentList(FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        List<FoTbCommunityCommentDTO> communityList = foCommunityMapper.selectCommunityCommentList(foTbCommunityCommentDTO);
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
        FoTbCommunityDTO communityDTO = foCommunityMapper.selectRecommend(tbCommunityDTO);
        if (communityDTO != null) {
            tbCommunityDTO.setCommunityRecommendSeq(communityDTO.getCommunityRecommendSeq());
            foCommunityMapper.deleteCommunityRecommend(tbCommunityDTO);
            tbCommunityDTO.setRecommend("-1");
            if(tbCommunityDTO.getCommunityCommentSeq() != null && !tbCommunityDTO.getCommunityCommentSeq().isEmpty()){
                foCommunityMapper.updateRecommendComment(tbCommunityDTO);
            } else {
                foCommunityMapper.updateRecommend(tbCommunityDTO);
            }

        } else {
            foCommunityMapper.insertCommunityRecommend(tbCommunityDTO);
            tbCommunityDTO.setRecommend("1");
            if(tbCommunityDTO.getCommunityCommentSeq() != null && !tbCommunityDTO.getCommunityCommentSeq().isEmpty()){
                foCommunityMapper.updateRecommendComment(tbCommunityDTO);
            } else {
                foCommunityMapper.updateRecommend(tbCommunityDTO);
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
        return foCommunityMapper.selectCommunityCategory(foTbCommunityDTO);
    }
}
