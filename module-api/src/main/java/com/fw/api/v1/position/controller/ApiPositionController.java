package com.fw.api.v1.position.controller;

import com.fw.api.v1.position.service.ApiPositionService;
import com.fw.api.v1.resume.service.ApiResumeService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.api.*;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * position Controller
 * @author yjw
 * @since 2022.10
 */

@Api("Position API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiPositionController {

    private final ApiPositionService positionService;

    private final CommonFileService fileService;

    private final ApiResumeService apiResumeService;

    /**
     * 포지션(채용공고) 리스트 조회
     */
    @ApiOperation(value = "포지션(채용공고) 리스트 조회", notes = "포지션(채용공고) 리스트 조회")
    @PostMapping("/position/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectPositionList(@RequestBody ApiPositionDTO apiPositionDTO) {
        ResponseCode code = ResponseCode.SUCCESS;

        /* 필수값 체크 */
        String memberId = apiPositionDTO.getMemberId();             // 회원 일련번호
        String positionId = apiPositionDTO.getPositionId();         // 포지션 일련번호
        String searchApply = apiPositionDTO.getSearchApply();       // 지원한 리스트 검색용
        String searchScrap = apiPositionDTO.getSearchScrap();       // 스크랩된 리스트 검색용
        String searchContact = apiPositionDTO.getSearchContact();   // Contact한 리스트 검색용
        String searchHh = apiPositionDTO.getSearchHh();             // 내 포지션 관리(HH) 검색용

        if(Objects.equals(searchApply, "Y") || Objects.equals(searchScrap, "Y") || Objects.equals(searchHh, "Y")) {
            /* 지원한 리스트 & 스크랩된 리스트 & 내 포지션 관리(HH) 검색일 경우 회원 일련번호 필수 */
            if(StringUtils.isBlank(memberId)) {
                code = ResponseCode.INVALID_PARAMETER;
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
            }
        }

        /* 컨택리스트 검색일 경우 포지션 일련번호 필수*/
        if(Objects.equals(searchContact,"Y")) {
            if(StringUtils.isBlank(positionId)) {
                code = ResponseCode.INVALID_PARAMETER;
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
            }
        }

        List<ApiPositionDTO> list = positionService.selectPositionList(apiPositionDTO);

        // fileList
        for(ApiPositionDTO apd : list) {
            if(StringUtils.isNotBlank(apd.getProfilePictureFileId())) {
                String fileSeq = apd.getProfilePictureFileId();
                apd.setCommonFileList(fileService.selectFileDetailList(fileSeq));
            } else if(StringUtils.isNotBlank(apd.getPictureFileId())) {
                String fileSeq = apd.getPictureFileId();
                apd.setCommonFileList(fileService.selectFileDetailList(fileSeq));
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", positionService.selectPositionCnt(apiPositionDTO));
        map.put("list", list);

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
    }

    /**
     * 포지션(채용공고) 열람 이력 등록
     */
    @ApiOperation(value = "포지션(채용공고) 열람 이력 등록" , notes= "포지션(채용공고) 열람 이력 등록")
    @PostMapping("/position/insert-history")
    public ResponseEntity<ResponseVO> insertPositionHistory(@RequestBody ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        /* 필수 값 체크 */
        String positionId = apiPositionReadingHistoryDTO.getPositionId();
        String memberId = apiPositionReadingHistoryDTO.getMemberId();

        if(StringUtils.isBlank(positionId) || StringUtils.isBlank(memberId)) {
            code = ResponseCode.INVALID_PARAMETER;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
        }

        try {
            positionService.insertPositionHistory(apiPositionReadingHistoryDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
    }

    /**
     * 포지션(채용공고) 열람 이력 조회
     */
    @ApiOperation(value = "포지션(채용공고) 열람 이력 조회", notes = "포지션(채용공고) 열람 이력 조회")
    @PostMapping("/position/reading-history")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectPositionReadingHistory(@RequestBody ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        /* 필수값 체크 */
        String memberId = apiPositionReadingHistoryDTO.getMemberId();

        if(StringUtils.isBlank(memberId)) {
            code = ResponseCode.INVALID_PARAMETER;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
        }

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount", positionService.selectPositionReadingHistoryCnt(apiPositionReadingHistoryDTO));
        map.put("list", positionService.selectPositionReadingHistory(apiPositionReadingHistoryDTO));

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
    }

    /**
     * 포지션(채용공고) 수정 (마감일 연장)
     */
    @ApiOperation(value = "포지션(채용공고) 수정 (마감일 연장)", notes = "포지션(채용공고) 수정 (마감일 연장)")
    @PostMapping("/position/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updatePosition(@RequestBody ApiPositionDTO apiPositionDTO) throws ParseException {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "SUCCESS";

        /* 필수값 체크 */
        String positionId = apiPositionDTO.getPositionId(); // 포지션 일련번호
        String memberId = apiPositionDTO.getMemberId();     // 헤드헌터 일련번호
        String endDate = apiPositionDTO.getEndDate();       // 마감일시

        if(StringUtils.isBlank(positionId) || StringUtils.isBlank(memberId) || StringUtils.isBlank(endDate)) {
            code = ResponseCode.INVALID_PARAMETER;
        }

        /* 연장 마감 횟수 체크 */
        Integer endAddCnt = positionService.selectEndAddCnt(apiPositionDTO);
        if(endAddCnt != null && endAddCnt >= 2) { // 연장 마감 횟수 - MAX2회
            code = ResponseCode.MAX_COUNT_OVER;
        }

        if(code.equals(ResponseCode.SUCCESS)) {
            try {
                positionService.updatePosition(apiPositionDTO);
            } catch (Exception e) {
                log.error("error", e);
                code = ResponseCode.INTERNAL_SERVER_ERROR;
            }
        }

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
    }

    /**
     * 포지션(채용공고) 스크랩
     */
    @ApiOperation(value = "포지션(채용공고) 스크랩", notes = "포지션(채용공고) 스크랩")
    @PostMapping("/position/scrap")
    @ResponseBody
    public ResponseEntity<ResponseVO> positionScrap(@RequestBody ApiScrapPositionDTO apiScrapPositionDTO) {
        ResponseCode code = ResponseCode.SUCCESS;

        /* 필수값 체크 */
        String positionId = apiScrapPositionDTO.getPositionId();
        String memberId = apiScrapPositionDTO.getMemberId();

        if(StringUtils.isBlank(positionId) || StringUtils.isBlank(memberId)) {
            code = ResponseCode.INVALID_PARAMETER;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
        }

        try {
            positionService.insertScrap(apiScrapPositionDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());

    }

    /**
     * 포지션(채용공고) 지원
     */
    @ApiOperation(value = "포지션(채용공고) 지원", notes = "포지션(채용공고) 지원")
    @PostMapping("/position/apply")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertPositionApply(@RequestBody ApiPositionApplicantDTO apiPositionApplicantDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "SUCCESS";

        /* 필수값 체크 */
        String positionId = apiPositionApplicantDTO.getPositionId();    // 포지션 일련번호
        String memberId = apiPositionApplicantDTO.getMemberId();        // 회원 일련번호
        String resumeId = apiPositionApplicantDTO.getResumeId();        // 이력서 일련번호

        if(StringUtils.isBlank(positionId) || StringUtils.isBlank(memberId) || StringUtils.isBlank(resumeId)) {
            code = ResponseCode.INVALID_PARAMETER;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
        }

        // 이력서 존재여부 확인
        ApiResumeDTO apiResumeDTO = new ApiResumeDTO();
        apiResumeDTO.setMemberId(memberId);
        apiResumeDTO.setResumeId(resumeId);
        boolean existence = apiResumeService.selectResumeExists(apiResumeDTO);
        if(!existence) {
            serviceCode = "RESUME_NOT_EXISTS";
            code = ResponseCode.INVALID_PARAMETER;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).serviceCode(serviceCode).build());
        }

        apiPositionApplicantDTO.setPhoneOpened("0");
        apiPositionApplicantDTO.setPmSubmitted("0");
        apiPositionApplicantDTO.setProgressStep("CONTACT");
        apiPositionApplicantDTO.setProposedEamil("0");
        apiPositionApplicantDTO.setProposedSms("0");
        apiPositionApplicantDTO.setRegPath("2");
        apiPositionApplicantDTO.setUpdateRequest("0");
        apiPositionApplicantDTO.setApMemberId(apiPositionApplicantDTO.getMemberId());
        apiPositionApplicantDTO.setReceiptPath("DIRECT");

        ApiPositionApplicantDTO result = positionService.selectPositionHh(apiPositionApplicantDTO);
        apiPositionApplicantDTO.setHhMemberId(result.getMemberId());

        // 이력서 상세조회 -> representation 조회
        ApiResumeDTO resumeInfo = apiResumeService.selectResumeInfo(apiResumeDTO);

        try {
            if(StringUtils.equals(resumeInfo.getRepresentation(),"1")) { // 기본이력서일 경우 복사후 지원
                apiResumeDTO.setRepresentation("0");
                apiResumeService.insertResumeClone(apiResumeDTO);
                apiPositionApplicantDTO.setResumeId(apiResumeService.selectLastResume(apiResumeDTO).getId());
            }
            positionService.insertPositionApply(apiPositionApplicantDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(apiPositionApplicantDTO).serviceCode(serviceCode).build());
    }

    /**
     * 이력서 수정권한 여부 설정
     */
    @ApiOperation(value = "이력서 수정권한 여부 설정" , notes = "")
    @PostMapping("/position/update/modify-flag")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateModifyFlag(@RequestBody ApiPositionApplicantDTO apiPositionApplicantDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        /* 필수값 체크 */
        String applicantId = apiPositionApplicantDTO.getApplicantId();

        if(StringUtils.isBlank(applicantId)) {
            code = ResponseCode.INVALID_PARAMETER;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
        }

        try {
            positionService.updateModifyFlag(apiPositionApplicantDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
    }

    /**
     * 포지션(채용공고) 채용현황 조회 (headhunter)
     */
    @ApiOperation(value = "포지션(채용공고) 채용현황 조회", notes = "포지션(채용공고) 채용현황 조회")
    @PostMapping("/position/status")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectPositionStatus(@RequestBody ApiPositionStatusDTO apiPositionStatusDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        /* 필수값 체크 */
        String memberId = apiPositionStatusDTO.getMemberId();

        if(StringUtils.isBlank(memberId)) {
            code = ResponseCode.INVALID_PARAMETER;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
        }

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(positionService.selectPositionStatus(apiPositionStatusDTO)).build());
    }
}