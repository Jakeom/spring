package com.fw.hh.community.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoTbCommunityCommentDTO;
import com.fw.core.dto.fo.FoTbCommunityDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import com.fw.core.mapper.db1.hh.HhCommunityMapper;
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
public class HhCommunityService {

    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    private final HhCommunityMapper hhCommunityMapper;
    private final CommonFileService commonFileService;
    private final CommonService commonService;
    private final CommonMapper commonMapper;
    private final FoMemberMapper foMemberMapper;

    public void insertCommunity(FoTbCommunityDTO foTbCommunityDTO) throws Exception {
        Integer fileSeq = commonFileService.fileUpload(foTbCommunityDTO.getFiles(), "COMMUNITY");
        foTbCommunityDTO.setFileSeq(String.valueOf(fileSeq));
        hhCommunityMapper.insertCommunity(foTbCommunityDTO);

        // R9 개선사항 요청 글등록 push알림 (관리자)
        if(StringUtils.equals(foTbCommunityDTO.getCommunityType(), "R9_IMPROVE")) {
            List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
            for(FoMemberDTO admin : adminList) {
                PushHistDTO push = PushHistDTO.builder()
                        .memberId(admin.getApprovalSeq())
                        .dispType("R9_IMPROVE_REQUEST")
                        .title("RESUME9 개선사항 요청글")
                        .content("RESUME9 개선사항 요청글이 등록되었습니다.")
                        .createId(foTbCommunityDTO.getFrontSession().getId()).build();
                commonService.insertPushHist(push);
            }
        }
    }

    public void insertCommunityComment(FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        hhCommunityMapper.insertCommunityComment(foTbCommunityCommentDTO);

        // 게시글 상세 정보 조회
        FoTbCommunityDTO foTbCommunityDTO = new FoTbCommunityDTO();
        foTbCommunityDTO.setCommunitySeq(foTbCommunityCommentDTO.getCommunitySeq());
        FoTbCommunityDTO data = hhCommunityMapper.selectCommunityDetail(foTbCommunityDTO);

        if (!StringUtils.equals(foTbCommunityCommentDTO.getFrontSession().getId(), data.getCreateSeq())) {
            // 댓글 등록 시 해당 게시글 작성자에게 push 알림 전송
            PushHistDTO push = PushHistDTO.builder()
                    .dispType("HH_COMMENT")
                    .memberId(data.getCreateSeq())
                    .title("[" + data.getCommunityTypeNm() + "]" + " " + "[" + data.getTitle() + "]")
                    .content("커뮤니티에 댓글이 달렸습니다.")
                    .createId(foTbCommunityCommentDTO.getFrontSession().getId())
                    .url(WEB_DOMAIN + "/m/community/detail?communitySeq=" + data.getCommunitySeq() + "&memberId=" + data.getCreateSeq()).build();
            commonService.insertPushHist(push);
        }

        // R9 개선사항 요청 댓글 push 알림 (관리자)
        if (StringUtils.equals(data.getCommunityType(), "R9_IMPROVE")) {
            List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
            for(FoMemberDTO admin : adminList) {
                    PushHistDTO push = PushHistDTO.builder()
                            .memberId(admin.getApprovalSeq())
                            .dispType("R9_IMPROVE_REQUEST")
                            .title("RESUME9 개선사항 요청 댓글")
                            .content("RESUME9 개선사항 요청 댓글이 등록되었습니다.")
                            .createId(foTbCommunityCommentDTO.getFrontSession().getId()).build();
                    commonService.insertPushHist(push);
            }
        }
    }

    public List<FoTbCommunityDTO> selectCommunityList(FoTbCommunityDTO foTbCommunityDTO) throws ParseException {
        List<FoTbCommunityDTO> foTbCommunityDTO1 = foTbCommunityDTO.getCommunityType() == null ?
                hhCommunityMapper.selectBestList(foTbCommunityDTO) : hhCommunityMapper.selectCommunityList(foTbCommunityDTO);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date time = new Date();
        Date nowDate = dateFormat.parse(dateFormat.format(time));
        for (int i = 0; i < foTbCommunityDTO1.size(); i++) {
            Date createDate = dateFormat.parse(foTbCommunityDTO1.get(i).getCreateDt());
            long diffDays = (nowDate.getTime() - createDate.getTime()) / (24 * 60 * 60 * 1000);
            long diffMin = (nowDate.getTime() - createDate.getTime()) / 60000; //분 차이
            long diffHor = (nowDate.getTime() - createDate.getTime()) / 3600000; //시 차이

            if (diffDays < 1) {
                if (diffHor >= 1) {
                    diffMin = diffMin - diffHor * 60;
                    foTbCommunityDTO1.get(i).setDifDate(diffHor + "시간 " + diffMin + "분 전");
                } else {
                    foTbCommunityDTO1.get(i).setDifDate(diffMin + "분 전");
                }
            } else {
                String[] tokens = foTbCommunityDTO1.get(i).getCreateDt().split(" ");
                foTbCommunityDTO1.get(i).setDifDate(String.valueOf(tokens[0]));
            }
        }
        return foTbCommunityDTO1;
    }

    public FoTbCommunityDTO selectCommunityDetail(FoTbCommunityDTO foTbCommunityDTO) {
        FoTbCommunityDTO f = hhCommunityMapper.selectCommunityDetail(foTbCommunityDTO);
        f.setCommonFileList(commonFileService.selectFileDetailList(f.getFileSeq()));
        return f;
    }

    public void updateRecommend(FoTbCommunityDTO tbCommunityDTO) {
        hhCommunityMapper.updateRecommend(tbCommunityDTO);
    }

    public void updateCommunityHit(FoTbCommunityDTO tbCommunityDTO) {
        hhCommunityMapper.updateCommunityHit(tbCommunityDTO);
    }

    public List<FoTbCommunityCommentDTO> selectCommunityCommentList(FoTbCommunityCommentDTO foTbCommunityCommentDTO) {
        List<FoTbCommunityCommentDTO> communityList = hhCommunityMapper.selectCommunityCommentList(foTbCommunityCommentDTO);
        for (FoTbCommunityCommentDTO ftccd : communityList) {
            int dateDiff = Integer.parseInt(ftccd.getDateDiff());
            if (dateDiff < 60) {
                ftccd.setDateDiff(dateDiff + "분 전");
            } else if (dateDiff < (60 * 24)) {
                dateDiff = dateDiff / 60;
                ftccd.setDateDiff(dateDiff + "시간 전");
            } else {
                String[] tokens = ftccd.getCreateDt().split(" ");
                ftccd.setDateDiff(tokens[0]);
            }
        }

        return this.getSubList(0, null, communityList);
    }

    public void insertCommunityRecommend(FoTbCommunityDTO tbCommunityDTO) {
        FoTbCommunityDTO communityDTO = hhCommunityMapper.selectRecommend(tbCommunityDTO);
        if (communityDTO != null) {
            tbCommunityDTO.setCommunityRecommendSeq(communityDTO.getCommunityRecommendSeq());
            hhCommunityMapper.deleteCommunityRecommend(tbCommunityDTO);
            tbCommunityDTO.setRecommend("-1");
            if (tbCommunityDTO.getCommunityCommentSeq() != null && !tbCommunityDTO.getCommunityCommentSeq().isEmpty()) {
                hhCommunityMapper.updateRecommendComment(tbCommunityDTO);
            } else {
                hhCommunityMapper.updateRecommend(tbCommunityDTO);
            }

        } else {
            hhCommunityMapper.insertCommunityRecommend(tbCommunityDTO);
            tbCommunityDTO.setRecommend("1");
            if (tbCommunityDTO.getCommunityCommentSeq() != null && !tbCommunityDTO.getCommunityCommentSeq().isEmpty()) {
                hhCommunityMapper.updateRecommendComment(tbCommunityDTO);
            } else {
                hhCommunityMapper.updateRecommend(tbCommunityDTO);
            }
        }
    }

    public List<FoTbCommunityCommentDTO> getSubList(int depth, String seq, List<FoTbCommunityCommentDTO> communityList) {
        List<FoTbCommunityCommentDTO> subList = new ArrayList<>();
        for (FoTbCommunityCommentDTO f : communityList) {
            if (((Integer.parseInt(f.getDepth()) == depth) && StringUtils.equals(f.getParentCommunityCommentSeq(), seq) && StringUtils.isNotBlank(seq)) ||
                    (Integer.parseInt(f.getDepth()) == depth) && StringUtils.isBlank(seq)) {
                subList.add(f);
                f.setSubList(getSubList(Integer.parseInt(f.getDepth()) + 1, f.getCommunityCommentSeq(), communityList));
            }
        }
        return subList;
    }
}
