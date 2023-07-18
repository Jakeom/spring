package com.fw.fo.resume.service;

import com.fw.core.dto.fo.resume.FoResumeDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FoFilterService {

    public String getUnivFilter(FoResumeDTO foResumeDTO) {
        String resultCode = "HIGH";
        foResumeDTO.setSchoolName(foResumeDTO.getName());

        if(StringUtils.equals("서울대학교", foResumeDTO.getSchoolName()) && StringUtils.equals("서울특별시", foResumeDTO.getLocationCd())
                || StringUtils.equals("연세대학교", foResumeDTO.getSchoolName()) && StringUtils.equals("서울특별시", foResumeDTO.getLocationCd())
                || StringUtils.equals("고려대학교", foResumeDTO.getSchoolName()) && StringUtils.equals("서울특별시", foResumeDTO.getLocationCd())
                || StringUtils.equals("한국과학기술원", foResumeDTO.getSchoolName()) && StringUtils.equals("대전광역시", foResumeDTO.getLocationCd())
                || StringUtils.equals("포항공과대학교", foResumeDTO.getSchoolName()) && StringUtils.equals("경상북도", foResumeDTO.getLocationCd())
        ){
            resultCode = "SKY";
        } else if(StringUtils.equals("서울특별시", foResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", foResumeDTO.getDegreeCd())
        ){
            resultCode = "SEOUL";
        } else if(StringUtils.equals("서울특별시", foResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", foResumeDTO.getDegreeCd())
                || StringUtils.equals("경기도", foResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", foResumeDTO.getDegreeCd())
                || StringUtils.equals("인천광역시", foResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", foResumeDTO.getDegreeCd())
        ){
            resultCode = "CAPICAL";
        } else if(!StringUtils.equals("서울특별시", foResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", foResumeDTO.getDegreeCd())
        ){
            resultCode = "REGION";
        } else if((StringUtils.equals("일반대학", foResumeDTO.getSchoolType()))
        ){
            resultCode = "NATION";
        } else if((StringUtils.equals("전문대학", foResumeDTO.getSchoolType())) || StringUtils.equals("기능대학(폴리텍대학)", foResumeDTO.getSchoolType())
        ){
            resultCode = "JUNIOR";
        }

        if(StringUtils.equals("1", foResumeDTO.getInOverseas())){
            resultCode = "OVERSEAS";
        }

        return resultCode;
    }

}
