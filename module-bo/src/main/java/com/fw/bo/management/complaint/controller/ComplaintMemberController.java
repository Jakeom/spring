package com.fw.bo.management.complaint.controller;

import com.fw.bo.management.complaint.service.ComplaintMemberService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.*;
import com.fw.core.mapper.db1.bo.BoMemberMapper;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 회원 조회 및 관리 Controller
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ComplaintMemberController {

    private final ComplaintMemberService complaintMemberService;

    /**
     * 회원 조회 및 관리 페이지
     */
    @RequestMapping(value = {"/bo/management/complaint/member"})
    public String member(ModelMap model, HttpServletRequest request, HttpServletResponse response, BoMemberDTO boMemberDTO) {
        int totalCnt = complaintMemberService.selectSearchMemberListCnt(boMemberDTO);
        boMemberDTO.setTotalCount(totalCnt);
        model.addAttribute("memberList", complaintMemberService.selectSearchMemberList(boMemberDTO));
        model.addAttribute("searchInfo", boMemberDTO);
        return "bo/management/complaint/member";
    }

    /**
     * member_type HH일 때, memberDetail에 들어갈 페이지
     */
    @GetMapping("/bo/management/complaint/member/hh")
    public String memberHH(BoMemberDTO boMemberDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", complaintMemberService.selectHeadHunterMemberList(boMemberDTO));

        return "/bo/management/complaint/member/hh";
    }

    /**
     * meeber_type AP일 때, memberDetail에 들어갈 페이지
     */
    @GetMapping("/bo/management/complaint/member/ap")
    public String memberAP(BoMemberDTO boMemberDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", complaintMemberService.selectMemberInfo(boMemberDTO));
        return "/bo/management/complaint/member/ap";
    }

    /**
     * 회원 조회 및 관리 상세페이지
     */
    @GetMapping("/bo/management/complaint/member/detail")
    public String detail(BoMemberDTO boMemberDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", complaintMemberService.selectMember(boMemberDTO));
        return "/bo/management/complaint/member/detail";
    }

    /**
     * member 이력서 리스트
     */
    @GetMapping("/bo/management/complaint/member/resume/list/{memberId}")
    public ResponseEntity<ResponseVO> selectResumeList(BoResumeDTO boResumeDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectResumeList(boResumeDTO)).build());
    }

    /**
     * member 이력서 검색 조건
     */
    @GetMapping("/bo/management/complaint/member/resume/list/option/{memberId}")
    public ResponseEntity<ResponseVO> selectResumeOption(BoResumeDTO boResumeDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        if(boResumeDTO.getRepresentation().equals("other")) {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectResumeOtherOption(boResumeDTO)).build());
        } else if(boResumeDTO.getRepresentation().equals("representation")) {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectResumeRepresentationOption(boResumeDTO)).build());
        } else if(boResumeDTO.getRepresentation().equals("applicant")) {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectResumeApplicantOption(boResumeDTO)).build());
        } else {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectResumeList(boResumeDTO)).build());
        }
    }

    /**
     * member 채용정보 리스트
     */
    @GetMapping("/bo/management/complaint/member/position/list/{memberId}")
    public ResponseEntity<ResponseVO> selectPositionList(BoPositionDTO boPositionDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectPositionList(boPositionDTO)).build());
    }

    /**
     * HH member의 채용정보 table 리스트
     */
    @GetMapping("/bo/management/complaint/member/position/info/{memberId}")
    public ResponseEntity<ResponseVO> selectPositionInfo(BoPositionDTO boPositionDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectPositionInfo(boPositionDTO)).build());
    }

    /**
     * HH member의 채용정보 table 리스트 검색조건
     */
    @GetMapping("/bo/management/complaint/member/position/info/option/{memberId}")
    public ResponseEntity<ResponseVO> selectPositionInfoOption(BoPositionDTO boPositionDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        if(boPositionDTO.getStatus().equals("ALL")) {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectPositionInfo(boPositionDTO)).build());
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectPositionInfoOption(boPositionDTO)).build());
    }

    /**
     * member리스트 검색 조건
     */
    @GetMapping("/bo/management/complaint/member/search")
    public ResponseEntity<ResponseVO> selectSearchMemberList(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(complaintMemberService.selectMemberList(boMemberDTO)).build());
    }

    /**
     * HH 회원정보 이력서 테이블 Option 추가 조회
     */
    @GetMapping("/bo/management/complaint/member/hh/history/option/{memberId}")
    public ResponseEntity<ResponseVO> selectHhResumeReadingHistoryOption(BoHhResumeReadingHistoryDTO boHhResumeReadingHistoryDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        if(boHhResumeReadingHistoryDTO.getRegisterPathCd().equals("ALL")) {
            return ResponseEntity.status(code.getHttpStatus())
                    .body(ResponseVO.builder(code).data(complaintMemberService.selectHhResumeReadingHistoryList(boHhResumeReadingHistoryDTO)).build());
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectHhResumeReadingHistoryOption(boHhResumeReadingHistoryDTO)).build());
    }

    /**
     * member 회원정보 수정
     */
    @PostMapping("/bo/management/complaint/member/detail/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateMember(@RequestBody BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateMember(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * member 회원정보 수정 마케팅 변경 날짜 포함
     */
    @PostMapping("/bo/management/complaint/memberMarketing/detail/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateMemberMarketing(@RequestBody BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateMemberMarketing(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * HH member 회원정보 수정
     */
    @PostMapping("/bo/management/complaint/hhmember/detail/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateHHMember(@RequestBody BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateHHMember(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * HH member 회원정보 수정 마케팅 변경 날짜 포함
     */
    @PostMapping("/bo/management/complaint/hhmemberMarketing/detail/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateHHMemberMarketing(@RequestBody BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateHHMemberMarketing(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * HH member 이용정지 업데이트
     */
    @PostMapping("/bo/management/complaint/member/detail/isStop/update/{id}")
    public ResponseEntity<ResponseVO> updateIsStop(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateIsStop(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * HH member 이용정지 해제 업데이트
     */
    @PostMapping("/bo/management/complaint/member/detail/isStopNo/update/{id}")
    public ResponseEntity<ResponseVO> updateIsStopNo(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateIsStopNo(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * member 임시회원 삭제 업데이트
     */
    @PostMapping("/bo/management/complaint/member/detail/isTemp/delete/{id}")
    public ResponseEntity<ResponseVO> deleteIstemp(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.deleteIstemp(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * member 임시회원 삭제 업데이트
     */
    @PostMapping("/bo/management/complaint/member/detail/deleted/update/{id}")
    public ResponseEntity<ResponseVO> updateDeleted(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateDeleted(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * member 임시회원 삭제 업데이트
     */
    @PostMapping("/bo/management/complaint/member/detail/restore")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateRestore(@RequestBody BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updateRestore(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * member 임시회원 삭제 업데이트
     */
    @PostMapping("/bo/management/complaint/member/detail/password/update/{id}")
    public ResponseEntity<ResponseVO> updatePassword(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            complaintMemberService.updatePassword(boMemberDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     *  아이디 찾기
     */
    @PostMapping("/bo/management/complaint/member/find-id")
    public ResponseEntity<ResponseVO> findId(BoMemberDTO boMemberDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(complaintMemberService.selectMemberWithName(boMemberDTO)).build());
    }
}