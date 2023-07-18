package com.fw.core.mapper.db1.fo;


import com.fw.core.dto.fo.FoApplicantDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoApplicantMapper {

    void insertApplicant(FoApplicantDTO foApplicantDTO); // 지원자 정보 등록
    void updateApplicant(FoApplicantDTO foApplicantDTO); // 지원자 정보 수정

}
