package com.fw.fo.headhunter.controller;

import com.fw.core.dto.fo.headhunter.FoSearchHhDTO;
import com.fw.fo.headhunter.service.FoHeadhunterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 헤드헌터 Controller
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoHeadhunterController {
    private final FoHeadhunterService foHeadhunterService;

    /**
     * 헤드헌터검색
     */
    @GetMapping("/fo/headhunter/headhunter-list")
    public String searchHeadhunter(FoSearchHhDTO foSearchHhDTO, ModelMap model) {
        if(foSearchHhDTO.getCheckFieldArr() != null && foSearchHhDTO.getCheckFieldArr().length == 1){
            String hhfields = foSearchHhDTO.getCheckFieldArr()[0];
            foSearchHhDTO.setCheckedFields(hhfields);
        } else if(foSearchHhDTO.getCheckFieldArr() != null && foSearchHhDTO.getCheckFieldArr().length > 1) {
            String hhfields = foSearchHhDTO.getCheckFieldArr()[0];
            for(int i = 1 ; i<foSearchHhDTO.getCheckFieldArr().length ; i++){
                hhfields += ","+foSearchHhDTO.getCheckFieldArr()[i];
            }
            foSearchHhDTO.setCheckedFields(hhfields);
        }

        foSearchHhDTO.setTotalCount(foHeadhunterService.selectSearchHhListCntForPaging(foSearchHhDTO));
        foSearchHhDTO.setRowSize(9);
        model.addAttribute("hhList", foHeadhunterService.selectSearchHhListPaging(foSearchHhDTO));
        model.addAttribute("searchInfo", foSearchHhDTO);
        return "fo/headhunter/headhunter-list";
    }

}