package com.fw.core.mapper.db1.hh;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.dto.hh.HhMyapListDTO;
import com.fw.core.dto.hh.HhResumeFormDTO;
import com.fw.core.dto.hh.HhSearchApDTO;

import java.util.List;

@Mapper
public interface HhApSearchsMapper {

    
    List<HhMyapListDTO> selectHhApAlertList();


    int selectHhApListCount(HhMyapListDTO hhMyapListDTO);
    List<HhMyapListDTO> selectHhApList(HhMyapListDTO hhMyapListDTO);
    List<HhMyapListDTO> selectHhApListByBatch(HhMyapListDTO hhMyapListDTO);

    HhSearchApDTO selectHhSearchApCondition(FoSessionDTO foSessionDTO);
    int insertHhSearchApCondition(HhMyapListDTO hhMyapListDTO);
    int insertHhSearchApConditionHp(HhMyapListDTO hhMyapListDTO);
    int insertHhSearchApConditionLoc(HhMyapListDTO hhMyapListDTO);

    
    int deleteHhSearchApCondition(HhMyapListDTO hhMyapListDTO);

    int selectHhResumeFormCount(HhResumeFormDTO hhResumeFormDTO);
    List<HhResumeFormDTO> selectHhResumeFormList(HhResumeFormDTO hhResumeFormDTO);

    int insertHhResumeForm(HhResumeFormDTO hhResumeFormDTO);

    int insertResume(FoResumeDTO fResumeDTO);






}
