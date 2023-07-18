package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * file_mgr DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoFileMgrDTO extends CommonDTO {

    private String id;              // 파일 일련번호
    private String path;            // s3 버킷에 해당하는 파일 경로	
    private String name;            // s3에 저장된 파일명
    private String url;             // s3 URL
    private String originName;      // 원래 파일명
    private String isImage;         // 이미지 여부
    private String width;           // 가로 너비(이미지인 경우)
    private String height;          // 세로 길이(이미지인 경우)
    private String ext;             // 파일 확장자
    private String size;            // 파일크기

}
