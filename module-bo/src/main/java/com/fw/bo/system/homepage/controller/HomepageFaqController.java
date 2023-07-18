package com.fw.bo.system.homepage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fw.core.dto.bo.BoNoticeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.bo.system.homepage.service.HomepageFaqService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import com.fw.core.dto.bo.BoFaqCategoryDTO;
import com.fw.core.dto.bo.BoFaqDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * FAQ 관리 Controller
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomepageFaqController {
  
    private final HomepageFaqService homepageFaqService;
  
    /**
     * FAQ 관리 페이지
     */
    @GetMapping("/bo/system/homepage/faq")
    public String faq(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/system/homepage/faq";
    }
      
    /**
     * FAQ 상세 페이지
     */
    @GetMapping("/bo/system/homepage/faq/detail")
    public String detail(BoFaqDTO bofaqDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", homepageFaqService.selectFaq(bofaqDTO));
        return "/bo/system/homepage/faq/detail";
    }
  
    /**
     * FAQ 수정 페이지
     */
    @GetMapping("/bo/system/homepage/faq/modify")
    public String faqModify(BoFaqDTO bofaqDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("modify", homepageFaqService.selectFaq(bofaqDTO));
        return "/bo/system/homepage/faq/modify";
    }
  
  
    /**
     * FAQ 추가 페이지
     */
    @GetMapping("/bo/system/homepage/faq/create")
    public String create(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "/bo/system/homepage/faq/create";
    }
  
    /**
     * FAQ 목록 리스트
     */
    @GetMapping("/bo/system/homepage/faq/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectFaqList(BoFaqDTO bofaqDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(homepageFaqService.selectFaqList(bofaqDTO)).build());
    }

    /**
     * FAQ display 리스트
     */
    @GetMapping("/bo/system/homepage/faq/display")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectFaqDisplayList(BoFaqDTO bofaqDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(homepageFaqService.selectFaqDisplayList(bofaqDTO)).build());
    }

    /**
     * FAQ display 리스트
     */
    @GetMapping("/bo/system/homepage/faq/display/hh")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectHHFaqDisplayList(BoFaqDTO bofaqDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(homepageFaqService.selectHHFaqDisplayList(bofaqDTO)).build());
    }

    /**
     * FAQ 카테고리 리스트
     */
    @GetMapping("/bo/system/homepage/faq/modal")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectCategoryNm(BoFaqCategoryDTO boFaqCategoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(homepageFaqService.selectCategoryNm(boFaqCategoryDTO)).build());
    }
  
    /**
     * FAQ 추가
     */
    @PostMapping("/bo/system/homepage/faq/create/insert")
    public ResponseEntity<ResponseVO> insertFaq(@RequestBody BoFaqDTO boFaqDTO, BoAdminSessionDTO boAdminSessionDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageFaqService.insertFaq(boFaqDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
  
    /**
     * Category 추가
     */
    @PostMapping("/bo/system/homepage/modal/insert")
    public ResponseEntity<ResponseVO> insertCategory(@RequestBody BoFaqCategoryDTO boFaqCategoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageFaqService.insertCategory(boFaqCategoryDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());}
  
    /**
     * Category 삭제(delFlag 'Y' 업데이트)
     */
    @PostMapping("/bo/system/homepage/faq/modal/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteCategory(BoFaqCategoryDTO boFaqCategoryDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageFaqService.deleteCategory(boFaqCategoryDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
  
    /**
     * Faq 삭제(delFlag 'Y' 업데이트)
     */
    @PostMapping("/bo/system/homepage/faq/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteFaq(BoFaqDTO boFaqDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageFaqService.deleteFaq(boFaqDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * Faq 수정
     */
    @PostMapping("/bo/system/homepage/faq/modifysave")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateFaq(BoFaqDTO boFaqDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageFaqService.updateFaq(boFaqDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    @PostMapping("/bo/system/homepage/faq/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> searchFaqList(BoFaqDTO boFaqDTO, HttpServletRequest request, HttpServletResponse response) {
        boFaqDTO.setStartDate(boFaqDTO.getStartDate());
        boFaqDTO.setEndDate(boFaqDTO.getEndDate());
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(homepageFaqService.searchFaqList(boFaqDTO)).build());
    }

    /**
     * Faq 메인 노출 추가
     */
    @PostMapping("/bo/system/homepage/faq/updatedisplayflag")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateDisplayFlag(@RequestBody BoFaqDTO boFaqDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageFaqService.updateDisplayFlag(boFaqDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * Faq 메인 노출 삭제
     */
    @PostMapping("/bo/system/homepage/faq/deleteDisplayflag")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteDisplay(@RequestBody BoFaqDTO boFaqDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            homepageFaqService.deleteDisplay(boFaqDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
    
}