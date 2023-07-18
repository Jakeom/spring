package com.fw.core.mapper.db1.common;

import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {

    String selectCommonCodeNm(CommonDTO commonDTO);
    List<CommonDTO> selectHighSchoolList(CommonDTO commonDTO);
    List<CommonDTO> selectHighSchoolListRpa(CommonDTO commonDTO);
    List<CommonDTO> selectUniversityList(CommonDTO commonDTO);
    List<CommonDTO> selectUniversityListRpa(CommonDTO commonDTO);
    List<CommonDTO> selectCompanyList(CommonDTO commonDTO);
    List<CommonDTO> selectTemplateList(CommonDTO commonDTO);
    List<CommonDTO> selectCommonCodeList(CommonDTO commonDTO);
    void insertPushHist(PushHistDTO pushHistDTO);
    FoMemberDTO selectMemberInfoByMemberId(FoMemberDTO foMemberDTO);
    List<FoMemberDTO> selectAdminList(FoMemberDTO foMemberDTO);
}