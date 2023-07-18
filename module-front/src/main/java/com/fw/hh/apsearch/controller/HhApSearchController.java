package com.fw.hh.apsearch.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.dto.hh.HhMyapListDTO;
import com.fw.core.dto.hh.HhResumeFormDTO;
import com.fw.core.dto.hh.HhSearchApDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.resume.service.FoResumeService;
import com.fw.hh.apsearch.service.HhApSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhApSearchController {

    private final HhApSearchService hhApSearchService;
    private final FoResumeService foResumeService;

    @Value("${service.wss_domain}")
    private String WSS_DOMAIN;

    /**
     * 인재 검색
     */
    @GetMapping("/hh/apsearch/ap-list")
    public String apSearch(ModelMap model, HhMyapListDTO hhMyapListDTO){
        if("search".equals(hhMyapListDTO.getStartFlag())){
            // 희망근무지 값 설정
            if(hhMyapListDTO.getWorkplace() != null && hhMyapListDTO.getWorkplace().length()>0){
                hhMyapListDTO.setWorkplaceArr(hhMyapListDTO.getWorkplace().split(","));
            }

            // 학력2 값 설정
            if(hhMyapListDTO.getEdu2() != null && hhMyapListDTO.getEdu2().length()>0){
                hhMyapListDTO.setEdu2Arr(hhMyapListDTO.getEdu2().split(","));
            }

            // 키워드 값 설정
            if(hhMyapListDTO.getKeyword() != null){
                List<String> andArr = new ArrayList<>();
                String and[] = hhMyapListDTO.getKeyword().split(",");
                for (String andData : and) {
                    String or[] = andData.split(" ");
                    List<String> orArr = new ArrayList<>();
                    for(String orData : or) {
                        if(orData.length() != 0) {
                            orArr.add(orData.trim());
                        }
                    }
                    andArr = orArr;
                }
                String[] arr = andArr.toArray(new String[0]);
                hhMyapListDTO.setKeywordArr(arr);
            }

            hhMyapListDTO.setTotalCount(hhApSearchService.selectHhApListCount(hhMyapListDTO));
            model.addAttribute("detailList", hhApSearchService.selectHhApList(hhMyapListDTO));
        } else {

            // 저장된 검색 내용 설정
            HhSearchApDTO hhSearchApDTO = hhApSearchService.selectHhSearchApCondition(hhMyapListDTO.getFrontSession());

            if(hhSearchApDTO != null){
                hhMyapListDTO.setCareerSt(hhSearchApDTO.getCareerSt());
                hhMyapListDTO.setCareerEn(hhSearchApDTO.getCareerEn());
                hhMyapListDTO.setAgeSt(hhSearchApDTO.getAgeSt());
                hhMyapListDTO.setAgeEn(hhSearchApDTO.getAgeEn());
                hhMyapListDTO.setAgeSt(hhSearchApDTO.getAgeSt());
                hhMyapListDTO.setAgeEn(hhSearchApDTO.getAgeEn());
                hhMyapListDTO.setSalarySt(hhSearchApDTO.getSalarySt());
                hhMyapListDTO.setSalaryEn(hhSearchApDTO.getSalaryEn());
                hhMyapListDTO.setEdu1(hhSearchApDTO.getHp1());
                hhMyapListDTO.setEdu2(hhSearchApDTO.getHp2());
                hhMyapListDTO.setWorkplace(hhSearchApDTO.getWorkplace());
                hhMyapListDTO.setCompany(hhSearchApDTO.getCompany());
                hhMyapListDTO.setLanguage(hhSearchApDTO.getForeignLang());
                hhMyapListDTO.setLicense(hhSearchApDTO.getCertificate());
                hhMyapListDTO.setKeyword(hhSearchApDTO.getIndustry());
            }

            model.addAttribute("detailList", new ArrayList<>());
        }
        model.addAttribute("searchInfo", hhMyapListDTO);
        return "hh/apsearch/ap-list";
    }

    /**
     * 헤드헌터 내인재검색 조건 저장
     */
    @PostMapping("/hh/apsearch/saveForm")
    @ResponseBody
    public ResponseEntity<ResponseVO> saveForm(HhMyapListDTO hhMyapListDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhApSearchService.deleteHhSearchApCondition(hhMyapListDTO);
            hhApSearchService.insertHhSearchApCondition(hhMyapListDTO);

            if(hhMyapListDTO.getWorkplace() != null && hhMyapListDTO.getWorkplace().length() > 0){
                hhMyapListDTO.setWorkplaceArr(hhMyapListDTO.getWorkplace().split(","));
                hhApSearchService.insertHhSearchApConditionLoc(hhMyapListDTO);
            }
            if(hhMyapListDTO.getEdu2() != null && hhMyapListDTO.getEdu2().length() > 0){
                hhMyapListDTO.setEdu2Arr(hhMyapListDTO.getEdu2().split(","));
                hhApSearchService.insertHhSearchApConditionHp(hhMyapListDTO);
            }

        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 헤드헌터 내인재검색 조건 초기화
     */
    @PostMapping("/hh/apsearch/reset")
    @ResponseBody
    public ResponseEntity<ResponseVO> reset(HhMyapListDTO hhMyapListDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhApSearchService.deleteHhSearchApCondition(hhMyapListDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 인재 등록
     */
    @GetMapping("/hh/apsearch/ap-registration")
    public String apRegistration(ModelMap model, FoResumeDTO foMemberDTO){
        return "hh/apsearch/ap-registration";
    }

    /**
     * 인재 등록 (이력서 등록신청)
     */
    @GetMapping("/hh/apsearch/ap-registration-apply")
    public String apRegistrationApply(ModelMap model, HhResumeFormDTO hhResumeFormDTO){

        int totalCount = hhApSearchService.selectHhResumeFormCount(hhResumeFormDTO);

            List<HhResumeFormDTO> dataList = null;
            if(totalCount != 0){
            dataList = hhApSearchService.selectHhResumeFormList(hhResumeFormDTO);
            }

        model.addAttribute("detailList", dataList);
        hhResumeFormDTO.setTotalCount(totalCount);
        model.addAttribute("searchInfo", hhResumeFormDTO);

        return "hh/apsearch/ap-registration-apply";

    }

    /**
     * 인재 등록 (이력서 등록신청하기)
     */
    @PostMapping(value = "hh/apsearch/registrationApply", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> registrationApply(
            @RequestPart(value = "jsonData") HhResumeFormDTO hhResumeFormDTO,
            @RequestPart(value = "file1", required = false) MultipartFile[] file1,
            @RequestPart(value = "file2", required = false) MultipartFile[] file2
    ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            
            hhResumeFormDTO.setFile1(file1);
            hhResumeFormDTO.setFile2(file2);
            
            hhApSearchService.insertHhRegistrationApply(hhResumeFormDTO);
            
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }


    /**
     * 인재 등록(이력서 로봇 프로젝트)
     */
    @GetMapping("/hh/apsearch/ap-registration-bot")
    public String apRegistrationBot(ModelMap model, FoMemberDTO foMemberDTO){
        model.addAttribute("wssDomain", WSS_DOMAIN);
        return "hh/apsearch/ap-registration-bot";
    }

    /**
     * 인재 등록(이력서 직접입력)
     */
    @GetMapping("/hh/apsearch/ap-registration-direct")
    public String apRegistrationDirect(ModelMap model, FoResumeDTO foMemberDTO){
        model.addAttribute("wssDomain", WSS_DOMAIN);
        model.addAttribute("searchInfo", foMemberDTO);
        return "hh/apsearch/ap-registration-direct";
    }

    /**
     * 인재 등록 (이력서 직접입력하기)
     */
    @PostMapping(value = "/hh/apsearch/registrationDirect", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> registrationDirect(
        @RequestPart(value = "jsonData") FoResumeDTO foResumeDTO,
        @RequestPart(value = "resumeImageFiles", required = false) MultipartFile[] resumeImageFiles,
        @RequestPart(value = "enResumeFile", required = false) MultipartFile[] enResumeFile,
        @RequestPart(value = "agreeResumeFile", required = false) MultipartFile[] agreeResumeFile,
        @RequestPart(value = "portFolioFiles", required = false) MultipartFile[] portFolioFiles
        ) {
            ResponseCode code = ResponseCode.SUCCESS;
            Map<String, Object> map = null;
            try {
                // 이력서 등록
                foResumeDTO.setResumeImageFiles(resumeImageFiles);
                foResumeDTO.setAgreeResumeFile(agreeResumeFile);
                foResumeDTO.setEnResumeFile(enResumeFile);
                foResumeDTO.setPortFolioFiles(portFolioFiles);
                foResumeDTO.setRepresentation("1"); // 대표이력서로 설정
                map = hhApSearchService.insertResume(foResumeDTO);
            } catch(Exception e) {
                log.error("error", e);
                code = ResponseCode.INTERNAL_SERVER_ERROR;
            }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
    }


}
