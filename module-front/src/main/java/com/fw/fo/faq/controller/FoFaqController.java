package com.fw.fo.faq.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.faq.FoFaqDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.faq.service.FoFaqService;
import com.fw.fo.mypage.service.FoMyPageProfileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * faq Controller
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoFaqController {


    private final FoFaqService foFaqService;
    private final FoMyPageProfileInfoService foMyPageProfileInfoService;
    @Value("${cert.server}")
    private String CERT_SERVER;

    @Value("${cert.callback-url}")
    private String CERT_CALLBACK_URL;
    /**
     * 자주 묻는 질문 (회원)
     */
    @GetMapping("/fo/service/faq-individual")
    public String faqIndividual(FoFaqDTO foFaqDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("faqCategorySeq",foFaqDTO.getFaqCategorySeq());
        model.addAttribute("faqSeq",foFaqDTO.getFaqSeq());
        foFaqDTO.setMemberTypeCd("AP");
        List<FoFaqDTO> cateList = foFaqService.selectFaqCateList(foFaqDTO);
        model.addAttribute("cateList",cateList);
        foFaqDTO.setFaqCategorySeq(cateList.get(0).getFaqCategorySeq());
        model.addAttribute("faqList",foFaqService.selectFaqList(foFaqDTO));
        model.addAttribute("searchInfo", foFaqDTO);
        return "fo/service/faq-individual";
    }

    /**
     * 자주 묻는 질문 카테고리별
     */
    @GetMapping("/fo/service/faqList")
    @ResponseBody
    public ResponseEntity<ResponseVO> faqList(FoFaqDTO foFaqDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("faqList",foFaqService.selectFaqList(foFaqDTO));
        ResponseCode code = ResponseCode.SUCCESS;
        List<FoFaqDTO> list= new ArrayList<>();
        try {
            list =  foFaqService.selectFaqList(foFaqDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(list).build());
    }
    /**
     * 자주 묻는 질문 (헤트한터)
     */
    @GetMapping("/fo/service/faq-headhunter")
    public String faqHeadhunter(FoFaqDTO foFaqDTO,ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foFaqDTO.setMemberTypeCd("HH");
        List<FoFaqDTO> cateList = foFaqService.selectFaqCateList(foFaqDTO);
        model.addAttribute("cateList",cateList);
        foFaqDTO.setFaqCategorySeq(cateList.get(0).getFaqCategorySeq());
        model.addAttribute("faqList",foFaqService.selectFaqList(foFaqDTO));
        model.addAttribute("searchInfo", foFaqDTO);
        return "fo/service/faq-headhunter";
    }

    /**
     * 회원탈퇴
     */
    @GetMapping("/fo/service/withdrawal")
    public String withdrawal(ModelMap model, FoMemberDTO foMemberDTO , HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL
        model.addAttribute("profileInfo", foMyPageProfileInfoService.selectProfileInfo(foMemberDTO));
        return "fo/service/withdrawal";
    }
}
