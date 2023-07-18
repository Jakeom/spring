package com.fw.fo.user.searchh.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.headhunter.FoSearchHhDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.headhunter.service.FoHeadhunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 마이페이지 Controller
 * @author jung
 */
@Controller
public class FoSearchHhController {
    @Autowired
    private FoHeadhunterService foSearchHhService;

    /**
     * front 메인 헤드헌터검색
     */
    @GetMapping("/fo/user/searchHunter/hunter-search")
    public String searchHeadhunter(FoSearchHhDTO foSearchHhDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if(foSearchHhDTO.getCheckFieldArr()!=null&&foSearchHhDTO.getCheckFieldArr().length==1){
            String hhfields = foSearchHhDTO.getCheckFieldArr()[0];
            foSearchHhDTO.setCheckedFields(hhfields);
        }else if(foSearchHhDTO.getCheckFieldArr()!=null&&foSearchHhDTO.getCheckFieldArr().length>1){
            String hhfields = foSearchHhDTO.getCheckFieldArr()[0];
            for(int i=1;i<foSearchHhDTO.getCheckFieldArr().length;i++){
                hhfields+=","+foSearchHhDTO.getCheckFieldArr()[i];
            }
            foSearchHhDTO.setCheckedFields(hhfields);
        }

        foSearchHhDTO.setTotalCount(foSearchHhService.selectSearchHhListCntForPaging(foSearchHhDTO));
        foSearchHhDTO.setRowSize(3);
        model.addAttribute("hhList",foSearchHhService.selectSearchHhListPaging(foSearchHhDTO));
        model.addAttribute("searchInfo",foSearchHhDTO);
        return "fo/headhunter/headhunter-list";
    }

    /**
     * 헤드헌터 관심 하기, 취소
     */
    @PostMapping("/fo/headhunter/interest")
    @ResponseBody
    public ResponseEntity<ResponseVO> scrap(ModelMap model, FoSearchHhDTO foSearchHhDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            if(foSearchHhDTO.getInterestHh().equals("addInterest")){
                foSearchHhService.insertInterestHh(foSearchHhDTO);
            }else if(foSearchHhDTO.getInterestHh().equals("delete")){
                foSearchHhService.updateInterestHh(foSearchHhDTO);
            }
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}