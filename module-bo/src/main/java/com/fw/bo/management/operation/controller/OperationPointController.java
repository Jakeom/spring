package com.fw.bo.management.operation.controller;

import com.fw.bo.management.operation.service.OperationPointService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoPointUseHistoryDTO;
import com.fw.core.dto.bo.BoQnaDTO;
import com.fw.core.dto.bo.BoRewardHistoryDTO;
import com.fw.core.dto.bo.BoRewardMallDTO;
import com.fw.core.dto.fo.MsgDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 포인트 관리 Controller
 * @author dongl
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationPointController {
    private final OperationPointService operationPointService;

    /**
     * 포인트 관리 페이지
     */
    @GetMapping("bo/management/operation/point")
    public String Point(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/operation/point";
    }
    /**
     * 포인트 상세 페이지
     */
    @RequestMapping("/bo/management/operation/point/detail")
    public String PointDetail(ModelMap model, BoPointUseHistoryDTO boPointHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detailList", operationPointService.selectPointDetail(boPointHistoryDTO));
        model.addAttribute("detailMember", operationPointService.selectPointMemberDetail(boPointHistoryDTO));
        return "/bo/management/operation/point/detail";
    }
    /**
     * 상품 페이지
     */
    @GetMapping("bo/management/operation/rewardMall")
    public String RewardMall(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/operation/rewardMall";
    }

    /**
     * 포인트 리스트
     */
    @GetMapping("/bo/management/operation/point/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectPointList(BoPointUseHistoryDTO boPointHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(operationPointService.selectPointList(boPointHistoryDTO)).build());
    }

    /**
     * 포인트 검색
     */
    @PostMapping("/bo/management/operation/point/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchPointList(BoPointUseHistoryDTO boPointHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(operationPointService.saerchPointList(boPointHistoryDTO)).build());
    }
    /**
     * 포인트 상태 확인
     * */
    @PostMapping("/bo/management/operation/point/search/status")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchStatus(BoPointUseHistoryDTO boPointUseHistoryDTO, HttpServletRequest request, HttpServletResponse response) {

       boPointUseHistoryDTO.setReasonCd(boPointUseHistoryDTO.getReasonCd());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(operationPointService.saerchStatus(boPointUseHistoryDTO)).build());
    }

    /**
     * 리워드 상세 (신청현황)
     */
    @PostMapping ("/bo/management/operation/reward/detail")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectOrderDetail(BoRewardHistoryDTO boRewardHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(operationPointService.selectOrderDetail(boRewardHistoryDTO)).build());
    }

    /**
     * HH 포인트 지급/회수 및 열람권 balance 조정
     */
    @PostMapping("/bo/operation/reward/detail/point/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertRewardHistory(@RequestBody BoPointUseHistoryDTO boPointUseHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            operationPointService.insertPointUseHistory(boPointUseHistoryDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 상품 리스트
     */
    @GetMapping("/bo/management/operation/rewardMall/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectRewardMallList(BoRewardMallDTO boRewardMallDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(operationPointService.selectRewardMallList(boRewardMallDTO)).build());
    }
    /**
     * 상품 등록
     */

     @PostMapping(value= "/bo/management/operation/rewardMall/insert" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
     @ResponseBody public ResponseEntity<ResponseVO> insertRewardMall(

             @RequestPart(value = "jsonData") BoRewardMallDTO boRewardMallDTO,
             @RequestPart(value = "imageFile", required = false) MultipartFile[] imageFile, HttpServletRequest request, HttpServletResponse response) {

     boRewardMallDTO.setImageFile(imageFile);

     ResponseCode code = ResponseCode.SUCCESS;
       try {
         operationPointService.insertRewardMall(boRewardMallDTO);
     } catch (Exception e) {
         log.error("error", e);
         code = ResponseCode.INTERNAL_SERVER_ERROR;
     }
       return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
     }

    /**
     * 상품 상세
     */
    @PostMapping ("/bo/management/operation/rewardMall/detail")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectRewardDetail(BoRewardMallDTO boRewardMallDTO, HttpServletRequest request, HttpServletResponse response) {
        boRewardMallDTO.setId(boRewardMallDTO.getId());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(operationPointService.selectRewardDetail(boRewardMallDTO)).build());
    }
    /**
     * 상품 정보 수정
     */
    @PostMapping(value="/bo/management/operation/rewardMall/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> updateReward(

            @RequestPart(value = "jsonData") BoRewardMallDTO boRewardMallDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile[] imageFile, HttpServletRequest request, HttpServletResponse response) {

        boRewardMallDTO.setFileSeq(boRewardMallDTO.getFileSeq());
        boRewardMallDTO.setImageFile(imageFile);

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            operationPointService.updateReward(boRewardMallDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 상품 정보 삭제
     */
    @PostMapping("/bo/management/operation/rewardMall/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteReward(BoRewardMallDTO boRewardMallDTO, HttpServletRequest request, HttpServletResponse response) {
        boRewardMallDTO.setId(boRewardMallDTO.getId());
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            operationPointService.deleteReward(boRewardMallDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 상품 검색
     */
    @PostMapping("/bo/management/operation/rewardMall/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchRewardMall(BoRewardMallDTO boRewardMallDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(operationPointService.saerchRewardMall(boRewardMallDTO)).build());
    }

    /**
     * SMS 전송
     */
    @PostMapping("/bo/management/operation/rewardMall/snedSms")
    @ResponseBody
    public void sendSms(BoRewardHistoryDTO boRewardHistoryDTO, MsgDTO msgDTO, HttpServletRequest request, HttpServletResponse response) {
        operationPointService.sendSms(boRewardHistoryDTO, msgDTO);
    }
}
