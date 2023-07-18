package com.fw.core.mapper.db1.hh;

import com.fw.core.dto.hh.HhMyAlarmDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HhMyAlarmMapper {

    List<HhMyAlarmDTO> selectSendMediaList(HhMyAlarmDTO hhMyAlarmDTO);
    List<HhMyAlarmDTO> selectMailMsgHistory(HhMyAlarmDTO hhMyAlarmDTO);

    List<HhMyAlarmDTO> selectCountTemplateGroup(HhMyAlarmDTO hhMyAlarmDTO);
    HhMyAlarmDTO selectSendMediaInfo(HhMyAlarmDTO hhMyAlarmDTO);

    HhMyAlarmDTO selectEmailTemplateFileSeq(HhMyAlarmDTO hhMyAlarmDTO);
    int selectSendMediaListCount(HhMyAlarmDTO hhMyAlarmDTO);

    void deleteSmsTemplate(HhMyAlarmDTO hhMyAlarmDTO);

    void updateSmsTemplate(HhMyAlarmDTO hhMyAlarmDTO);

    void insertTemplateEmailFile(HhMyAlarmDTO hhMyAlarmDTO);

    void insertTemplateContent(HhMyAlarmDTO hhMyAlarmDTO);

    void insertTemplateGroup(HhMyAlarmDTO hhMyAlarmDTO);

    void updateTemplateFile(HhMyAlarmDTO hhMyAlarmDTO);

    void deleteSendMail(HhMyAlarmDTO hhMyAlarmDTO);

    void deleteSendMessage(HhMyAlarmDTO hhMyAlarmDTO);
}
