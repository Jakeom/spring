package com.fw.bo.management.operation.service;

import com.fw.core.dto.bo.BoResumeDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BoFilterService {

    public String getUnivFilter(BoResumeDTO boResumeDTO) {
        String resultCode = "HIGH";

        boResumeDTO.setSchoolName(boResumeDTO.getName());

        if(StringUtils.equals("서울대학교", boResumeDTO.getSchoolName()) && StringUtils.equals("서울특별시", boResumeDTO.getLocationCd())
                || StringUtils.equals("연세대학교", boResumeDTO.getSchoolName()) && StringUtils.equals("서울특별시", boResumeDTO.getLocationCd())
                || StringUtils.equals("고려대학교", boResumeDTO.getSchoolName()) && StringUtils.equals("서울특별시", boResumeDTO.getLocationCd())
                || StringUtils.equals("한국과학기술원", boResumeDTO.getSchoolName()) && StringUtils.equals("대전광역시", boResumeDTO.getLocationCd())
                || StringUtils.equals("포항공과대학교", boResumeDTO.getSchoolName()) && StringUtils.equals("경상북도", boResumeDTO.getLocationCd())
        ){
            resultCode = "SKY";
        } else if(StringUtils.equals("서울특별시", boResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", boResumeDTO.getDegreeCd())
        ){
            resultCode = "SEOUL";
        } else if(StringUtils.equals("서울특별시", boResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", boResumeDTO.getDegreeCd())
                || StringUtils.equals("경기도", boResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", boResumeDTO.getDegreeCd())
                || StringUtils.equals("인천광역시", boResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", boResumeDTO.getDegreeCd())
        ){
            resultCode = "CAPICAL";
        } else if(!StringUtils.equals("서울특별시", boResumeDTO.getLocationCd()) && StringUtils.equals("COLLEGE", boResumeDTO.getDegreeCd())
        ){
            resultCode = "REGION";
        } else if((StringUtils.equals("일반대학", boResumeDTO.getSchoolType()))
        ){
            resultCode = "NATION";
        } else if((StringUtils.equals("전문대학", boResumeDTO.getSchoolType())) || StringUtils.equals("기능대학(폴리텍대학)", boResumeDTO.getSchoolType())
        ){
            resultCode = "JUNIOR";
        }

        if(StringUtils.equals("1", boResumeDTO.getInOverseas())){
            resultCode = "OVERSEAS";
        }

        return resultCode;
    }

}
