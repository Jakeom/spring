package com.fw.core.dto.hh;
import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.FileDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HhResumeFormDTO extends CommonDTO {
    
    private String id;                          // 이력서 일련번호
    private String name;
    private String status;
    private String formUrl;
    private String formFileId;
    private String statusNm;
    private String createdAt;
    private String denyReason;

    private String searchtype;
    private String keyword;
    private String searchStDate;
    private String searchEndate;


    private String directInput;
    private String etc;
    private String resumeFileId;
    private String agreeFileId;
    private List<FileDTO> agreeFileList;

    MultipartFile[] file1;
    MultipartFile[] file2;

}
