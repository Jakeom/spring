package com.fw.hh.myaddress.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.hh.HhContactsDTO;
import com.fw.core.dto.hh.HhContactsGroupDTO;
import com.fw.core.dto.hh.HhMyProfileDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.myaddress.service.HhAddressService;
import com.fw.hh.mypage.service.HhMyProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhAddressController {

    private final HhAddressService hhAddressService;
    private final HhMyProfileService hhMyProfileService;

    /**
     * 내 주소록 관리
     */
    @GetMapping("/hh/address/address-list")
    public String addressList(ModelMap model, HhContactsDTO hhContactsDTO){

        HhContactsGroupDTO hhContactsGroupDTO = new HhContactsGroupDTO();
        hhContactsGroupDTO.setFrontSession(hhContactsDTO.getFrontSession());

        HhMyProfileDTO HhMyProfileDTO = new HhMyProfileDTO();
        HhMyProfileDTO.setFrontSession(hhContactsDTO.getFrontSession());

        List<HhContactsGroupDTO> tmpGroupList = hhAddressService.selectContractGroup(hhContactsGroupDTO);
        if(tmpGroupList.size() > 0 && (hhContactsDTO.getGroupId() == null || hhContactsDTO.getGroupId().length() == 0)){
            HhContactsGroupDTO ttt = tmpGroupList.get(0);
            hhContactsDTO.setGroupId(ttt.getId());
        }

        model.addAttribute("profile", hhMyProfileService.selectMyProfileInfo(HhMyProfileDTO));
        model.addAttribute("groupList", tmpGroupList);
        model.addAttribute("detailList", hhAddressService.selectHhContactsList(hhContactsDTO));
        hhContactsDTO.setTotalCount(hhAddressService.selectHhContactsListCount(hhContactsDTO));
        model.addAttribute("searchInfo", hhContactsDTO);

        return "hh/address/address-list";
    }

    /**
     * 헤드헌터 내 주소록 그룹 추가 
     */
    @PostMapping("/hh/address/addGroup")
    @ResponseBody
    public ResponseEntity<ResponseVO> addGroup(HhContactsGroupDTO hhContactsGroupDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhAddressService.insertContactgroup(hhContactsGroupDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 헤드헌터 내 주소록 그룹 수정 / 삭제
     */
    @PostMapping("/hh/address/editGroup")
    @ResponseBody
    public ResponseEntity<ResponseVO> editGroup(HhContactsGroupDTO hhContactsGroupDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhAddressService.updateContactgroup(hhContactsGroupDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

     /**
     * 헤드헌터 내 주소록 그룹 이동
     */
    @PostMapping("/hh/address/moveGroup")
    @ResponseBody
    public ResponseEntity<ResponseVO> moveGroup(@RequestBody HhContactsDTO hhContactsDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            hhContactsDTO.setCheckFieldArr(hhContactsDTO.getCheckedFields().split(","));
            hhAddressService.updateContact(hhContactsDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }


     /**
     * 헤드헌터 내 주소록 연락처 추가
     */
    @PostMapping("/hh/address/addContact")
    @ResponseBody
    public ResponseEntity<ResponseVO> addContact(HhContactsDTO hhContactsDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            HhContactsDTO tmp = new HhContactsDTO();
            tmp.setFrontSession(hhContactsDTO.getFrontSession());
            tmp.setEmail(hhContactsDTO.getEmail());
            if(hhAddressService.selectHhContactsListCount(tmp) > 0){
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.METHOD_NOT_ALLOWED).build());
            }
            tmp.setEmail(null);
            tmp.setPhone(hhContactsDTO.getPhone());
            if(hhAddressService.selectHhContactsListCount(tmp) > 0){
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.METHOD_NOT_ALLOWED).build());
            }

            hhAddressService.insertContact(hhContactsDTO);

        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 헤드헌터 내 주소록 연락처 수정
     */
    @PostMapping("/hh/address/editContact")
    @ResponseBody
    public ResponseEntity<ResponseVO> editContact(HhContactsDTO hhContactsDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {

            hhContactsDTO.setCheckFieldArr(hhContactsDTO.getCheckedFields().split(","));
            hhAddressService.updateContact(hhContactsDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }


    /**
	 * 주소록 - 메일 발송
	 */
	@PostMapping(value = "/hh/address/send-mail")
	@ResponseBody
	public ResponseEntity<ResponseVO> sendMail(@RequestBody HhContactsDTO hhContactsDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		try {
			hhAddressService.updateSendMail(hhContactsDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 첨부파일 메일 보내기
     */
    @PostMapping(value = "/hh/address/send-mail2", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeEditDo(
            @RequestPart(value = "jsonData") HhContactsDTO hhContactsDTO,
            @RequestPart(value = "attchedFiles", required = false) MultipartFile[] attchedFiles
    ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            
            hhContactsDTO.setAttchedFiles(attchedFiles);
            
            hhAddressService.updateSendMail(hhContactsDTO);

            
            // int fileSeq = commonFileService.fileUpload(attchedFiles, "EMAIL");
            // System.out.println(commonFileService.selectFileDetailList((""+fileSeq)));

            // foResumeDTO.setResumeImageFiles(resumeImageFiles);
            // foResumeDTO.setEnResumeFile(enResumeFile);
            // foResumeDTO.setPortFolioFiles(portFolioFiles);
            // foResumeService.updateResume(foResumeDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
	 * 주소록 - SMS 발송
	 */
	@PostMapping(value = "/hh/address/send-sms")
	@ResponseBody
	public ResponseEntity<ResponseVO> sendSms(@RequestBody HhContactsDTO hhContactsDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
        String result = "FAIL";
		try {
			result = hhAddressService.updateSendSms(hhContactsDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(result).build());
    }

}
