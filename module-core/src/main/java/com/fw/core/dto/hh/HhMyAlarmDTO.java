package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class HhMyAlarmDTO extends CommonDTO {

    // hh_send_msg
    private String id;
    private String createdAt;
    private String updatedAt;
    private String content;
    private String kindCd;
    private String media;
    private String subject;
    private String memberId;
    private String recipientName;
    private String success;
    private String delFlag;
    private String decreasePoint;

    // hh_send_msg_file
    private String fileId;
    private String msgId;

    // hh_send_msg_target
    private String isRead;
    private String name;
    private String target;
    private String val;

    // hh_template_content
    private String isDefault;
    private String templateGroupId;

    // hh_template_file
    private String templateContentId;
    private String templateFileId;

    // hh_template_group
    private String mediaTypeCd;
    private List<String> mediaList;
    private List<String> deleteTemplateList;

    private MultipartFile[] emailFiles;

    private String searchType;  // 검색타입
    private String keyword;     // 검색키워드
    private String fileChangeCheck;     // 검색키워드

    private String[] mailArr;
    private String[] msgArr;

    private List<HhMyAlarmDTO> idList;
    
    private String mode;
    private String pointUseType;
    
}
