package com.fw.core.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

@Slf4j
@Component
public class S3Util {

    @Value("${cloud.aws.s3.access-key}")
    private String accessKey;

    @Value("${cloud.aws.s3.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket-private}")
    private String bucketPrivate;

    @Value("${cloud.aws.s3.region}")
    private String clientRegion;

    private AmazonS3 s3Client;

    private static S3Util instance = null;

    public void create() {
        this.createS3Client();
    }

    /**
     * AWS S3 업로드
     * 
     * @param file File
     * @param key String
     * @param path String
     * @throws Exception
     */
    public void upload(File file, String key, String path) throws Exception {
        this.uploadS3(file, key, path);
    }

    /**
     * AWS S3 파일 복사
     * 
     * @param orgKey String
     * @param copyKey String
     */
    public void copy(String orgKey, String copyKey) {
        this.copyS3(orgKey, copyKey);
    }

    /**
     * AWS S3 삭제
     * 
     * @param key String
     */
    public void delete(String key) {
        this.deleteS3(key);
    }

    /**
     * AWS 인스턴스 취득
     * 
     * @return AwsUtil
     */
    public static S3Util getInstance() {
        if (instance == null) {
            return new S3Util();
        } else {
            return instance;
        }
    }

    public void createS3Client() {
        log.info("accessKey:{}  secretKey:{}", accessKey, secretKey);
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }

    /**
     * AWS S3 업로드
     * 
     * @param file File
     * @param key String
     * @param path String
     */
    public void uploadS3(File file, String key, String path) throws Exception {
        /* 업로드 */
        uploadToS3(new PutObjectRequest(this.bucketPrivate.concat(path), key, file).withCannedAcl(CannedAccessControlList.Private));
    }

    /**
     * 업로드
     * 
     * @param is InputStream
     * @param key String
     * @param path String
     * @param contentType String
     * @param contentLength long
     * @throws Exception
     */
    public void uploadS3(InputStream is, String key, String path, String contentType, long contentLength) throws Exception {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(contentLength);
        objectMetadata.addUserMetadata("title", "someTitle");
        PutObjectRequest s3Request = new PutObjectRequest(this.bucketPrivate, key, is, objectMetadata);
        s3Request.setMetadata(objectMetadata);
        uploadToS3(new PutObjectRequest(this.bucketPrivate, path.concat(key), is, objectMetadata));
    }

    /**
     * S3 업로드 PutObjectRequest는 Aws S3 버킷에 업로드할 객체 메타 데이터와 파일 데이터로 이루어져있다.
     * 
     * @param putObjectRequest PutObjectRequest
     * @return PutObjectResult
     * @throws Exception
     */
    private PutObjectResult uploadToS3(PutObjectRequest putObjectRequest) {
        PutObjectResult result = null;

        try {
            result = this.s3Client.putObject(putObjectRequest);
        } catch (AmazonServiceException e) {
            log.error("{}[{}]{}", putObjectRequest.getBucketName(), putObjectRequest.getKey(), e.getMessage());
            log.error("[HTTP Status Code]{}", e.getStatusCode());
            log.error("[AWS Error Code]{}", e.getErrorCode());
            log.error("[Error Type]{}", e.getErrorType());
            log.error("[Request ID]{}", e.getRequestId());
            log.error("[AmazonServiceException]", e);
        } catch (AmazonClientException e) {
            log.error("{}[{}]{}", putObjectRequest.getBucketName(), putObjectRequest.getKey(), e.getMessage());
            log.error("[AmazonClientException]", e);
        } catch (Exception e) {
            log.error("{}[{}]{}", putObjectRequest.getBucketName(), putObjectRequest.getKey(), e.getMessage());
            log.error("[Exception]", e);
        }
        return result;
    }

    /**
     * 복사
     * 
     * @param orgKey String
     * @param copyKey String
     */
    public void copyS3(String orgKey, String copyKey) {
        try {
            CopyObjectRequest copyObjRequest = new CopyObjectRequest(this.bucketPrivate, orgKey, this.bucketPrivate, copyKey);
            this.s3Client.copyObject(copyObjRequest);
            log.debug(String.format("Finish copying [%s] to [%s]", orgKey, copyKey));
        } catch (AmazonServiceException e) {
            log.error("[{}]{}", orgKey, e.getMessage());
            log.error("[AmazonServiceException]", e);
        } catch (SdkClientException e) {
            log.error("[{}]{}", orgKey, e.getMessage());
            log.error("[AmazonServiceException]", e);
        }
    }

    /**
     * 삭제
     * 
     * @param key String
     */
    public void deleteS3(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucketPrivate, key);
            this.s3Client.deleteObject(deleteObjectRequest);
            log.debug("[{}] deletion complete", key);
        } catch (AmazonServiceException e) {
            log.error("[{}]{}", key, e.getMessage());
            log.error("[AmazonServiceException]", e);
        } catch (SdkClientException e) {
            log.error("[{}]{}", key, e.getMessage());
            log.error("[AmazonServiceException]", e);
        }
    }

    /**
     * 다운로드
     * 
     * @param key String
     * @return S3Object
     */
    public S3Object download(String key) {
        if (key == null) {
            return null;
        }

        S3Object fullObject = null;
        try {
            fullObject = s3Client.getObject(this.bucketPrivate, key);
            if (fullObject == null) {
                return null;
            }
        } catch (AmazonS3Exception e) {
            log.error("[{}]{}", key, e.getMessage());
            log.error("[AmazonS3Exception]", e);
        }
        return fullObject;
    }

    /**
     * 다운로드
     * 
     * @param key String
     * @param downloadFileName String
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return boolean
     */
    public boolean download(String key, String downloadFileName, HttpServletRequest request, HttpServletResponse response) {
        if (key == null) {
            return false;
        }

        S3Object fullObject = null;
        try {
            fullObject = s3Client.getObject(this.bucketPrivate, key);
            if (fullObject == null) {
                return false;
            }
        } catch (AmazonS3Exception e) {
            log.error("[{}]{}", key, e.getMessage());
            log.error("[AmazonS3Exception]", e);
        }

        OutputStream os = null;
        FileInputStream fis = null;
        boolean success = false;

        try {
            S3ObjectInputStream objectInputStream = fullObject.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(objectInputStream);

            String fileName = null;
            if (downloadFileName != null) {
                fileName = getEncodedFilename(request, downloadFileName);
            } else {
                fileName = getEncodedFilename(request, key);
            }

            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Length", String.valueOf(fullObject.getObjectMetadata().getContentLength()));
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");

            FileCopyUtils.copy(bytes, response.getOutputStream());

            success = true;
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                log.debug(e.getMessage(), e);
            }

            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.debug(e.getMessage(), e);
            }
        }

        return success;
    }


    /**
     * 다운로드
     * 
     * @param key String
     */
    public byte[] downloadS3(String key) {
        byte[] bytes = null;
        if (key == null) {
            return null;
        }

        S3Object fullObject = null;
        S3ObjectInputStream objectInputStream = null;
        try {
            fullObject = s3Client.getObject(this.bucketPrivate, key);
            if (fullObject == null) {
                return null;
            }
            objectInputStream = fullObject.getObjectContent();
        } catch (AmazonS3Exception e) {
            log.error("[{}]{}", key, e.getMessage());
            log.error("[AmazonS3Exception]", e);
        }

        try {
            if (objectInputStream == null)
                return null;
            bytes = IOUtils.toByteArray(objectInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }


    /**
     * 파일명이 한글인 경우 URL Encode이 필요함.
     * 
     * @param request HttpServletRequest
     * @param displayFileName String
     * @return String
     * @throws UnsupportedEncodingException
     */
    private String getEncodedFilename(HttpServletRequest request, String displayFileName) throws UnsupportedEncodingException {
        String header = request.getHeader("User-Agent");
        String encodedFilename = null;
        if (header.indexOf("MSIE") > -1) {
            encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (header.indexOf("Trident") > -1) {
            encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (header.indexOf("Chrome") > -1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < displayFileName.length(); i++) {
                char c = displayFileName.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else if (header.indexOf("Opera") > -1) {
            encodedFilename = "\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (header.indexOf("Safari") > -1) {
            encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"", "UTF-8");
        } else {
            encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"", "UTF-8");
        }
        return encodedFilename;
    }

    /**
     * private Key없이 업로드 (File객체인경우)
     * 
     * @param file File
     * @param awsSavePath String
     * @return boolean
     * @throws UnsupportedEncodingException
     */
    public boolean awsUploadNoKey(File file, String awsSavePath) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider(false)).build();
            PutObjectRequest s3Request = new PutObjectRequest(bucketPrivate, awsSavePath, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            s3Request.setMetadata(metadata);
            s3Client.putObject(s3Request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * privateKey없이 업로드 (Multipart 객체인경우)
     * 
     * @param multipartFile MultipartFile
     * @param awsSavePath String
     * @return String
     * @throws UnsupportedEncodingException
     */
    public boolean awsUploadNoKey(MultipartFile multipartFile, String awsSavePath) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider(false)).build();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            PutObjectRequest s3Request = new PutObjectRequest(this.bucketPrivate, awsSavePath, multipartFile.getInputStream(), metadata);
            s3Request.setMetadata(metadata);
            s3Client.putObject(s3Request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUrlByPath(String filePath, String fileName){
        Date date = new Date(new Date().getTime() + 1000 * 60 * 60);

        return s3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(filePath, fileName)
                                                .withMethod(HttpMethod.GET)
                                                .withExpiration(date)).toString();
    }

}
