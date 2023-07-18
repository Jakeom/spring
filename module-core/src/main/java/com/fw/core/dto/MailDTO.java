package com.fw.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class MailDTO {

    private String fromEmail;
    private List<String> receiveEmail;
    private String subject;
    private String content;
    private String template;
    private String filepath;
    private boolean useHtmlYn;
    private Map<String, Object> templateData;
    private List<String> toEmailList;
    private List<String> ccEmailList;
    private List<String> hiddenCcEmailList;
    private String replyToEmail;
    private List<FileDTO> attachFileList;

}
