package com.fw.core.common.service;

import com.fw.core.dto.FileDTO;
import com.fw.core.mapper.db1.common.CommonFileMapper;
import com.fw.core.util.DateUtil;
import com.fw.core.util.ResizeUtil;
import com.fw.core.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonFileService {

    @Value("${cloud.aws.s3.bucket-private}")
    private String SERVICE_FILE_UPLOAD;
    @Value("${cloud.aws.s3.service-url}")
    private String SERVICE_URL;

    private final CommonFileMapper commonFileMapper;
    private final S3Util s3Util;

    // 공통 파일 업로드
    public Integer fileUpload(MultipartFile[] files, String folderId) throws Exception {
        Integer rtnValue = null;
        if(files != null && files.length != 0){
            FileDTO fd = FileDTO.builder().build();
            commonFileMapper.insertFile(fd);
            rtnValue = Integer.parseInt(fd.getFileSeq());

            for(MultipartFile multipartFile : files){
                if(multipartFile.isEmpty()){
                    continue;
                }

                String fileName = multipartFile.getOriginalFilename();
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                String today =  DateUtil.getDtStrNow("yyyyMMddHHmmss");
                String fileUuid = UUID.randomUUID().toString().replaceAll("-", "");
                String fileSaveName = today + "_" + fileUuid + "." + ext;
                s3Util.createS3Client();

                s3Util.uploadS3(
                        multipartFile.getInputStream(),
                        fileSaveName,
                        folderId + "/",
                        multipartFile.getContentType(),
                        multipartFile.getSize()
                );

                String[] fileSplit = fileName.split("\\.");

                String fileExt = fileSplit[fileSplit.length - 1];
                String width = null;
                String height = null;
                int isImage = 0;

                if(StringUtils.equals("jpeg", fileExt) || StringUtils.equals("png", fileExt) || StringUtils.equals("jpg", fileExt) || StringUtils.equals("gif", fileExt) || StringUtils.equals("bmp", fileExt)
                        || StringUtils.equals("JPEG", fileExt)|| StringUtils.equals("PNG", fileExt)|| StringUtils.equals("JPG", fileExt)|| StringUtils.equals("GIF", fileExt)|| StringUtils.equals("BMP", fileExt)) {
                    BufferedImage bimg = ImageIO.read(multipartFile.getInputStream());
                    width = String.valueOf(bimg.getWidth());
                    height = String.valueOf(bimg.getHeight());
                    isImage = 1;
                }

                FileDTO fileDto = FileDTO.builder().originName(fileName)
                        .path(SERVICE_FILE_UPLOAD + "/" + folderId)
                        .url(SERVICE_URL + "/" + SERVICE_FILE_UPLOAD + "/" + folderId + "/" + fileSaveName)
                        .fileSeq(fd.getFileSeq())
                        .size(String.valueOf(multipartFile.getSize()))
                        .name(fileSaveName)
                        .ext(fileExt)
                        .width(width)
                        .height(height)
                        .isImage(String.valueOf(isImage))
                        .build();

                commonFileMapper.insertFileDetail(fileDto);
            }
        }
        return rtnValue;
    }

    // 공통 파일 업로드 (파일 교체)
    public void fileUpdate(MultipartFile[] files, String folderId, String rtnValue) throws Exception {
        if(files != null && files.length != 0){
            for(MultipartFile multipartFile : files){
                String fileName = multipartFile.getOriginalFilename();
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                String today =  DateUtil.getDtStrNow("yyyyMMddHHmmss");
                String fileUuid = UUID.randomUUID().toString().replaceAll("-", "");
                String fileSaveName = today + "_" + fileUuid + "." + ext;
                s3Util.createS3Client();

                s3Util.uploadS3(
                        multipartFile.getInputStream(),
                        fileSaveName,
                        folderId + "/",
                        multipartFile.getContentType(),
                        multipartFile.getSize()
                );

                String[] fileSplit = fileName.split("\\.");

                String fileExt = fileSplit[fileSplit.length - 1];
                String width = null;
                String height = null;
                int isImage = 0;

                if(StringUtils.equals("png", fileExt) || StringUtils.equals("jpg", fileExt) || StringUtils.equals("gif", fileExt) || StringUtils.equals("bmp", fileExt)) {
                    BufferedImage bimg = ImageIO.read(multipartFile.getInputStream());
                    width = String.valueOf(bimg.getWidth());
                    height = String.valueOf(bimg.getHeight());
                    isImage = 1;
                }

                FileDTO fileDto = FileDTO.builder().originName(fileName)
                        .path(SERVICE_FILE_UPLOAD + "/" + folderId)
                        .url(SERVICE_URL + "/" + SERVICE_FILE_UPLOAD + "/" + folderId + "/" + fileSaveName)
                        .fileSeq(rtnValue)
                        .size(String.valueOf(multipartFile.getSize()))
                        .name(fileSaveName)
                        .ext(fileExt)
                        .width(width)
                        .height(height)
                        .isImage(String.valueOf(isImage))
                        .build();

                commonFileMapper.insertFileDetail(fileDto);
            }
        }
    }

    // 공통 파일 업로드 (이미지 리사이징)
    public Integer fileUploadResizeImage(MultipartFile[] files, String folderId) throws Exception {
        Integer rtnValue = null;
        if(files != null && files.length != 0){
            FileDTO fd = FileDTO.builder().build();
            commonFileMapper.insertFile(fd);
            rtnValue = Integer.parseInt(fd.getFileSeq());

            for(MultipartFile multipartFile : files){
                if(multipartFile.isEmpty()){
                    continue;
                }

                String fileName = multipartFile.getOriginalFilename();
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                String today =  DateUtil.getDtStrNow("yyyyMMddHHmmss");
                String fileUuid = UUID.randomUUID().toString().replaceAll("-", "");
                String fileSaveName = today + "_" + fileUuid + "." + ext;

                ResizeUtil resizeUtil = new ResizeUtil(multipartFile.getInputStream());
                ResizeUtil resizeImage = resizeUtil.resize(100, 150);
                File f = new File(fileName + "." + ext);
                FileOutputStream out = new FileOutputStream(f);
                resizeImage.writeTo(out, ext);

                s3Util.createS3Client();
                s3Util.uploadS3(
                        Files.newInputStream(f.toPath()),
                        fileSaveName,
                        folderId + "/",
                        multipartFile.getContentType(),
                        f.length()
                );

                String[] fileSplit = fileName.split("\\.");

                String fileExt = fileSplit[fileSplit.length - 1];
                String width = null;
                String height = null;
                int isImage = 0;

                if(StringUtils.equals("jpeg", fileExt) || StringUtils.equals("png", fileExt) || StringUtils.equals("jpg", fileExt) || StringUtils.equals("gif", fileExt) || StringUtils.equals("bmp", fileExt)
                        || StringUtils.equals("JPEG", fileExt)|| StringUtils.equals("PNG", fileExt)|| StringUtils.equals("JPG", fileExt)|| StringUtils.equals("GIF", fileExt)|| StringUtils.equals("BMP", fileExt)) {
                    BufferedImage bimg = ImageIO.read(multipartFile.getInputStream());
                    width = String.valueOf(bimg.getWidth());
                    height = String.valueOf(bimg.getHeight());
                    isImage = 1;
                }

                FileDTO fileDto = FileDTO.builder().originName(fileName)
                        .path(SERVICE_FILE_UPLOAD + "/" + folderId)
                        .url(SERVICE_URL + "/" + SERVICE_FILE_UPLOAD + "/" + folderId + "/" + fileSaveName)
                        .fileSeq(fd.getFileSeq())
                        .size(String.valueOf(multipartFile.getSize()))
                        .name(fileSaveName)
                        .ext(fileExt)
                        .width(width)
                        .height(height)
                        .isImage(String.valueOf(isImage))
                        .build();

                commonFileMapper.insertFileDetail(fileDto);
            }
        }
        return rtnValue;
    }

    public List<FileDTO> selectFileDetailList(String fileSeq){
        List<FileDTO> list = null;
        s3Util.createS3Client();
        if(StringUtils.isNotBlank(fileSeq)) {
            list = commonFileMapper.selectFileDetailList(fileSeq);
            for (FileDTO f : list) {
                String url = s3Util.getUrlByPath(f.getPath(), f.getName());
                log.info(">>> url - {}", url);
                f.setUrl(url);
            }
        }
        return list;
    }

    /** 파일 삭제 */
    public void updateFileDetail(String fileSeq){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileSeq(fileSeq);
        commonFileMapper.updateFileDetail(fileDTO);
    }

    /** 파일 삭제 */
    public void updateFile(FileDTO fileDTO){
        commonFileMapper.updateFile(fileDTO);
    }

}
