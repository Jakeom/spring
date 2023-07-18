package com.fw.fo.mypage.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoApplicantDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.mapper.db1.fo.FoApplicantMapper;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoMyPageProfileInfoService {
    private final FoMemberMapper foMemberMapper;
    private final FoApplicantMapper foApplicantMapper;
    private final CommonFileService commonFileService;

    public FoMemberDTO selectProfileInfo(FoMemberDTO foMemberDTO) {
        FoMemberDTO f = foMemberMapper.selectProfileInfo(foMemberDTO);
        if(!Objects.isNull(f)){
            String fileSeq = f.getProfilePictureFileId();
            if(StringUtils.isNotBlank(fileSeq)){
                f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return f;
    }

    public void updateProfile(FoMemberDTO foMemberDTO) throws Exception {
        if(StringUtils.equals("Y", foMemberDTO.getPhotoChangeFlag())) {
            Integer fileSeq = commonFileService.fileUploadResizeImage(foMemberDTO.getFiles(), "USER");
            foMemberDTO.setProfilePictureFileId(String.valueOf(fileSeq));
        }

        foMemberMapper.updateMember(foMemberDTO);

        FoApplicantDTO foApplicantDTO = FoApplicantDTO.builder()
                .birth(foMemberDTO.getBirth())
                .genderCd(foMemberDTO.getGenderCd())
                .memberId(foMemberDTO.getMemberId())
                .contactExceptHoliday(foMemberDTO.getContactExceptHoliday())
                .contactableTime(foMemberDTO.getContactableTime()).build();
        foApplicantMapper.updateApplicant(foApplicantDTO);
    }

}
