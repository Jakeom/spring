package com.fw.core.common.service;

import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonMapper commonMapper;

    @Value("${nice.url}")
    private String NICE_URL;

    @Value("${nice.client-id}")
    private String NICE_CLIENT_ID;

    @Value("${nice.client-secret}")
    private String NICE_CLIENT_SECRET;

    @Value("${admin.member-id}")
    private String ADMIN_MEMBER_ID_STR;

    public String selectCommonCodeNm(CommonDTO commonDTO){
        return commonMapper.selectCommonCodeNm(commonDTO);
    }

    public List<CommonDTO> selectHighSchoolList(CommonDTO commonDTO){
        return commonMapper.selectHighSchoolList(commonDTO);
    }

    public List<CommonDTO> selectHighSchoolListRpa(CommonDTO commonDTO) {
        commonDTO.setMode("IN");
        List<CommonDTO> list = commonMapper.selectHighSchoolListRpa(commonDTO);
        if(list.size() > 1) {
            commonDTO.setMode("IN_ADDRESS");
            commonDTO.setRowNum(1); // 주소검색 시 LIMIT 1
            list = commonMapper.selectHighSchoolListRpa(commonDTO);
        }
        if(list.size() == 0) {
            commonDTO.setMode("CONCAT");
            list = commonMapper.selectHighSchoolListRpa(commonDTO);
            if(list.size() != 1) {
                commonDTO.setMode("CONCAT_ADDRESS");
                commonDTO.setRowNum(1); // 주소검색 시 LIMIT 1
                list = commonMapper.selectHighSchoolListRpa(commonDTO);
            }
        }
        return list;
    }
    public List<CommonDTO> selectUniversityList(CommonDTO commonDTO){
        return commonMapper.selectUniversityList(commonDTO);
    }

    public List<CommonDTO> selectUniversityListRpa(CommonDTO commonDTO){
        commonDTO.setMode("IN");
        List<CommonDTO> list = commonMapper.selectUniversityListRpa(commonDTO);
        if(list.size() > 1) {
            commonDTO.setMode("IN_ADDRESS");
            commonDTO.setRowNum(1); // 주소검색 시 LIMIT 1
            list = commonMapper.selectUniversityListRpa(commonDTO);
        }
        if(list.size() == 0) {
            commonDTO.setMode("CONCAT");
            list = commonMapper.selectUniversityListRpa(commonDTO);
            if(list.size() != 1) {
                commonDTO.setMode("CONCAT_ADDRESS");
                commonDTO.setRowNum(1); // 주소검색 시 LIMIT 1
                list = commonMapper.selectUniversityListRpa(commonDTO);
            }
        }
        return list;
    }

    public List<CommonDTO> selectCompanyList(CommonDTO commonDTO){
        return commonMapper.selectCompanyList(commonDTO);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectNiceCompanySearchList(CommonDTO commonDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-IBM-Client-Id", NICE_CLIENT_ID);
        headers.set("X-IBM-Client-Secret", NICE_CLIENT_SECRET);

        String nameSearchUrl = NICE_URL + "prn_rst_cnt=10&pge_st_no=0&sort=epr_cnu_yn:desc,etl_ipc_yn:desc,ltgmktdivcd:asc,eprmdydivcd:asc,eprdatastsdivcd:desc&nm=" + commonDTO.getSearchValue();
        String totalSearchUrl = NICE_URL + "prn_rst_cnt=20&pge_st_no=0&sort=epr_cnu_yn:desc,etl_ipc_yn:desc,ltgmktdivcd:asc,eprmdydivcd:asc,eprdatastsdivcd:desc&itg_srch=" + commonDTO.getSearchValue();

        Map<String, Object> nameSearchMap = RestTemplateUtil.get(nameSearchUrl, headers);
        Map<String, Object> totalSearchMap = RestTemplateUtil.get(totalSearchUrl, headers);

        List<Map<String, Object>> returnList = new ArrayList<>();

        if(!Objects.isNull(nameSearchMap)){
            Map<String, Object> map = (Map<String, Object>) nameSearchMap.get("items");
            if(!Objects.isNull(map)) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("item");
                if (!Objects.isNull(list)) {
                    returnList.addAll(list);
                }
            }
        }

        if(!Objects.isNull(totalSearchMap)){
            Map<String, Object> map = (Map<String, Object>) totalSearchMap.get("items");
            if(!Objects.isNull(map)) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("item");
                if (!Objects.isNull(list)) {
                    returnList.addAll(list);
                }
            }
        }

        return returnList;
    }

    public List<CommonDTO> selectTemplateList(CommonDTO commonDTO){
        return commonMapper.selectTemplateList(commonDTO);
    }

    public List<CommonDTO> selectCommonCodeList(CommonDTO commonDTO){
        return commonMapper.selectCommonCodeList(commonDTO);
    }

    public void insertPushHist(PushHistDTO pushHistDTO){
        // 알림 수신자 정보조회
        FoMemberDTO foMemberDTO = new FoMemberDTO();
        foMemberDTO.setMemberId(pushHistDTO.getMemberId());
        FoMemberDTO member = commonMapper.selectMemberInfoByMemberId(foMemberDTO);

        String type = pushHistDTO.getDispType(); // 알림종류
        if(member != null) {
            if(StringUtils.isNotBlank(member.getPhoneType()) && StringUtils.isNotBlank(member.getPushToken())) { // phoneType&pushToken 존재 시
                log.info(">>> 앱 사용 회원 시 PUSH");
                pushHistDTO.setPhoneType(member.getPhoneType());
                pushHistDTO.setPhoneToken(member.getPushToken());

                // phoneType&pushToken 존재 시 APP 알림설정과 연동
                if(StringUtils.equals(type, "RESUME_RECEIPT")) { // 이력서 접수알림의 경우 알림 수신여부 확인
                    if(!StringUtils.equals(member.getHhResuFlag(), "Y")) {
                        log.info(">>> 이력서 접수알림 수신거부");
                        return;
                    }
                }
                if(StringUtils.equals(type, "COMMENT") || StringUtils.equals(type, "COMPANY_QUESTION")) { // 커뮤니티 알림의 경우 커뮤니티 수신여부 확인
                    if(StringUtils.equals(member.getDtype(), "AP")) { // 후보자 커뮤니티 수신여부
                        if(!StringUtils.equals(member.getApCommFlag(), "Y")) {
                            log.info(">>> 후보자 커뮤니티 수신거부");
                            return;
                        }
                    }
                    if(StringUtils.equals(member.getDtype(), "HH")) { // 헤드헌터 커뮤니티 수신여부
                        if(!StringUtils.equals(member.getHhCommFlag(), "Y")) {
                            log.info(">>> 헤드헌터 커뮤니티 수신거부");
                            return;
                        }
                    }
                }
            }
            commonMapper.insertPushHist(pushHistDTO);
        }
    }

    public void insertPushHistAdmin(PushHistDTO pushHistDTO){
        Pattern.compile(",").splitAsStream(ADMIN_MEMBER_ID_STR).forEach((str) -> {
            log.info(">>> {}", str);
            pushHistDTO.setMemberId(str);
            FoMemberDTO m = commonMapper.selectMemberInfoByMemberId(FoMemberDTO.builder().memberId(str).build());
            pushHistDTO.setPhoneToken(m.getPushToken());
            pushHistDTO.setPhoneType(m.getPhoneType());
            commonMapper.insertPushHist(pushHistDTO);
        });
    }

    public FoMemberDTO selectMemberInfoByMemberId(FoMemberDTO foMemberDTO) {
        return commonMapper.selectMemberInfoByMemberId(foMemberDTO);
    }

    public List<FoMemberDTO> selectAdminList(FoMemberDTO foMemberDTO) {
        return commonMapper.selectAdminList(foMemberDTO);
    }
}