package com.fw.bo.system.homepage.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoMaindisplayDTO;
import com.fw.core.dto.bo.BoParsingErrorAcceptDTO;
import com.fw.core.mapper.db1.bo.BoMaindisplayMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HomepageMainDisplayService {

    public final BoMaindisplayMapper boMaindisplayMapper;
    public final CommonFileService commonFileService;

    public List<BoMaindisplayDTO> selectMaindisplayList(BoMaindisplayDTO boMaindisplayDTO) {
        List<BoMaindisplayDTO> list = boMaindisplayMapper.selectMaindisplayList(boMaindisplayDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getFileSeq();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    if(commonFileService.selectFileDetailList(fileSeq).size() != 0){
                        list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                    } else {
                        System.out.println(commonFileService.selectFileDetailList(fileSeq).size());
                    }
                }
            }
        }
        return list;
    }

    public BoMaindisplayDTO selectMaindisplay(BoMaindisplayDTO boMaindisplayDTO) {
        return boMaindisplayMapper.selectMaindisplay(boMaindisplayDTO);
    }

    public List<FileDTO> selectMaindisplayFile(BoMaindisplayDTO boMaindisplayDTO) {
        boMaindisplayDTO = boMaindisplayMapper.selectMaindisplay(boMaindisplayDTO);
        List<FileDTO> list = commonFileService.selectFileDetailList(boMaindisplayDTO.getFileSeq());
        return list;
    }

    public void insertMaindisplay(BoMaindisplayDTO boMaindisplayDTO) throws Exception {
        Integer profileFileId = commonFileService.fileUpload(boMaindisplayDTO.getMaindisplayFiles(), "MAIN_DISPLAY");
        if(profileFileId != null){
            boMaindisplayDTO.setFileSeq(String.valueOf(profileFileId));
        }
        boMaindisplayMapper.insertMaindisplay(boMaindisplayDTO);
    }

    public void deleteMaindisplay(BoMaindisplayDTO boMaindisplayDTO) {
        commonFileService.updateFileDetail(boMaindisplayDTO.getFileSeq());
        boMaindisplayMapper.deleteMaindisplay(boMaindisplayDTO);
    }

    public void updateMaindisplay(BoMaindisplayDTO boMaindisplayDTO) throws Exception {
        if(boMaindisplayDTO.getFileSeq() != null && !boMaindisplayDTO.getFileSeq().equals("")) {
            commonFileService.fileUpdate(boMaindisplayDTO.getMaindisplayFiles(), "RESUME_PROFILE", boMaindisplayDTO.getFileSeq());
        } else {
            Integer profileFileId = commonFileService.fileUpload(boMaindisplayDTO.getMaindisplayFiles(), "MAIN_DISPLAY");
            if(profileFileId != null){
                boMaindisplayDTO.setFileSeq(String.valueOf(profileFileId));
            }
        }
        boMaindisplayMapper.updateMaindisplay(boMaindisplayDTO);
    }

}
