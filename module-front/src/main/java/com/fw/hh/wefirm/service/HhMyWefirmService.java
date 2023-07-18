package com.fw.hh.wefirm.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.hh.HhWefirmCustomerDTO;
import com.fw.core.dto.hh.HhWefirmDTO;
import com.fw.core.dto.hh.HhWefirmHeadhunterDTO;
import com.fw.core.dto.hh.HhWefirmPositionDTO;
import com.fw.core.mapper.db1.hh.HhMyWefirmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class HhMyWefirmService {

    private final HhMyWefirmMapper hhMyWefirmMapper;
    private final CommonFileService commonFileService;

    /**
     * Wefrim 포지션 공고 리스트
     */
    public List<HhWefirmPositionDTO> selectViewPositionList(HhWefirmPositionDTO hhWefirmPositionDTO) {
        return hhMyWefirmMapper.selectViewPositionList(hhWefirmPositionDTO);
    }

    /**
     * Wefrim 헤드헌터 리스트
     */
    public List<HhWefirmHeadhunterDTO> selectHeadhunterList(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {

        List<HhWefirmHeadhunterDTO> list = hhMyWefirmMapper.selectHeadhunterList(hhWefirmHeadhunterDTO);
        for (int i = 0; i < list.size(); i++) {
            String fileSeq = list.get(i).getProfilePictureFileId();
            if (!Objects.isNull(fileSeq)) {
                if (StringUtils.isNotBlank(fileSeq)) {
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }

        return list;
    }

    // Wefirm 헤드헌터 상세
    public HhWefirmHeadhunterDTO selectHhInfo(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
        HhWefirmHeadhunterDTO f = hhMyWefirmMapper.selectHhInfo(hhWefirmHeadhunterDTO);
        if (!Objects.isNull(f)) {
            String fileSeq = f.getProfilePictureFileId();
            if (StringUtils.isNotBlank(fileSeq)) {
                f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return f;
    }

    public List<HhWefirmHeadhunterDTO> selectHhFieldInfo(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
        return hhMyWefirmMapper.selectHhFieldInfo(hhWefirmHeadhunterDTO);
    }
    
    public List<HhWefirmHeadhunterDTO> selectPmPositionList(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
        return hhMyWefirmMapper.selectPmPositionList(hhWefirmHeadhunterDTO);
    }

    public int selectHeadhunterListCnt(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
        return hhMyWefirmMapper.selectHeadhunterListCnt(hhWefirmHeadhunterDTO);
    }

    public int selectViewPositionListCnt(HhWefirmPositionDTO hhWefirmHeadhunterDTO) {
        return hhMyWefirmMapper.selectViewPositionListCnt(hhWefirmHeadhunterDTO);
    }

    public List<HhWefirmCustomerDTO> selectWefirmCustomerList(HhWefirmCustomerDTO hhWefirmCustomerDTO) {

        List<HhWefirmCustomerDTO> list = hhMyWefirmMapper.selectWefirmCustomerList(hhWefirmCustomerDTO);

        if (!Objects.isNull(list)) {
            for (HhWefirmCustomerDTO h : list) { // 현재시간,인증일시 비교 인증여부 처리
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowDate = dateFormat.format(new Date());
                String certifiedAt = h.getCertifiedAt();
                if (StringUtils.isNotBlank(certifiedAt)) {
                    if (certifiedAt.compareTo(nowDate) <= 0) { // 인증만료
                        h.setCerifiedFlag("N");
                    } else { // 인증유효
                        h.setCerifiedFlag("Y");
                    }
                } else { // certifiedAt null일시 인증만료 처리
                    h.setCerifiedFlag("N");
                }

                if(StringUtils.equals(h.getApprFlag(),"Y")) {
                    h.setStatusMsg("(완료)");
                } else if(StringUtils.equals(h.getApprFlag(),"N")) {
                    h.setStatusMsg("(처리중)");
                } else if(StringUtils.equals(h.getApprFlag(),"R")) {
                    h.setStatusMsg("(반려)");
                } else {
                    h.setStatusMsg("");
                }
            }
        }
        return list;
    }

    public HhWefirmCustomerDTO selectCustomerInfo(HhWefirmCustomerDTO hhWefirmCustomerDTO) {
        return hhMyWefirmMapper.selectClientPmInfo(hhWefirmCustomerDTO);
    }

    public HhWefirmCustomerDTO selectPasserInfo(HhWefirmCustomerDTO hhWefirmCustomerDTO) {
        HhWefirmCustomerDTO passerInfo = hhMyWefirmMapper.selectClientPassInfo(hhWefirmCustomerDTO);
        String fileSeq = passerInfo.getTaxFileId();
        if(!Objects.isNull(fileSeq)){
            if(StringUtils.isNotBlank(fileSeq)){
                passerInfo.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
            }
        }
        return passerInfo;
    }
    public int selectWefirmCustomerListCnt(HhWefirmCustomerDTO hhWefirmCustomerDTO) {
        return hhMyWefirmMapper.selectWefirmCustomerListCnt(hhWefirmCustomerDTO);
    }

    public List<HhWefirmHeadhunterDTO> selectWefirmHeadhunterList(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
        return hhMyWefirmMapper.selectWefirmHeadhunterList(hhWefirmHeadhunterDTO);
    }

    public void updateWefirmCustomer(HhWefirmCustomerDTO hhWefirmCustomerDTO) {
        hhMyWefirmMapper.updateClientPm(hhWefirmCustomerDTO);
    }
    public void deleteWefirmCustomer(HhWefirmCustomerDTO hhWefirmCustomerDTO) {
        for (int i = 0; i < hhWefirmCustomerDTO.getCustomerArr().length; i++) {
            hhWefirmCustomerDTO.setPmId(hhWefirmCustomerDTO.getCustomerArr()[i]);
            hhMyWefirmMapper.deleteClientPm(hhWefirmCustomerDTO); // 고객사 삭제
            hhMyWefirmMapper.deleteClientPass(hhWefirmCustomerDTO); // 합격자 삭제 --> delFlag 미존재 / 데이터 삭제중
        }
    }

    public void insertWefirmCustomer(HhWefirmCustomerDTO hhWefirmCustomerDTO) {

        hhWefirmCustomerDTO.setMemberId(hhWefirmCustomerDTO.getFrontSession().getId());
        HhWefirmDTO hhWefirmDTO = new HhWefirmDTO();
        hhWefirmDTO.setMemberId(hhWefirmCustomerDTO.getFrontSession().getId());
        hhWefirmCustomerDTO.setWefirmId(hhMyWefirmMapper.selectWefirmId(hhWefirmDTO));

        if(StringUtils.equals(hhWefirmCustomerDTO.getRegisterType(),"POSITION")) { // 포지션 등록 시 PM은 로그인된 헤드헌터
            hhWefirmCustomerDTO.setPmMemberId(hhWefirmCustomerDTO.getFrontSession().getId());
        }

        // 고객사 등록
        LocalDateTime now = LocalDateTime.now();
        log.info("now >>> {}",now);
        HhWefirmCustomerDTO data = hhMyWefirmMapper.selectClientPmInfo(hhWefirmCustomerDTO); // wefirmId & licenseNo 조회
        if(data == null) {
            log.info("## 중복되는 고객사가 없습니다. --> 등록을 진행합니다.");
            hhWefirmCustomerDTO.setExpiredAt(String.valueOf(now.plusMonths(6)));
            hhMyWefirmMapper.insertClientPm(hhWefirmCustomerDTO);
        } else {
            if(StringUtils.isNotBlank(data.getExpiredAt())) { // 만료일 존재
                LocalDateTime expiredAt = LocalDateTime.parse(data.getExpiredAt(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                log.info("expiredAt >>> {}",expiredAt);
                if(expiredAt.isBefore(now)) { // 만료
                    log.info("## PM점유X & 만료일초과 --> 만료일이 연장되었습니다.");
                    hhWefirmCustomerDTO.setExpiredAt(String.valueOf(now.plusMonths(6)));
                } else { // 만료X
                    if(StringUtils.equals(hhWefirmCustomerDTO.getPmMemberId(), data.getPmMemberId())) { // PM 점유여부 확인
                        hhWefirmCustomerDTO.setPmId(data.getPmId()); // clientPass 조회를 위한 pmId 세팅
                        List<HhWefirmCustomerDTO> clientPass = hhMyWefirmMapper.selectClientPassList(hhWefirmCustomerDTO);
                        if(clientPass.size() != 0) { // client_pass 데이터 존재
                            if(!Objects.equals(clientPass.get(0).getApprFlag(), "E")) { // apprFlag != E
                                    hhWefirmCustomerDTO.setClientPassId(clientPass.get(0).getClientPassId());
                                    hhWefirmCustomerDTO.setApprFlag("E");
                                    hhMyWefirmMapper.updateClientPass(hhWefirmCustomerDTO);
                                if (expiredAt.isBefore(now.plusMonths(6))) { // 만료일 < 이전일 + 6개월
                                    log.info("## 만료일 6개월 추가");
                                    hhWefirmCustomerDTO.setExpiredAt(String.valueOf(now.plusMonths(6)));
                                } else { // 만료일 > 이전일 + 6개월
                                    log.info("## 만료일 유지");
                                    hhWefirmCustomerDTO.setExpiredAt(String.valueOf(expiredAt));
                                }
                            } else { // apprFlag == E
                                if (expiredAt.isBefore(now.plusMonths(6))) {
                                    log.info("## 만료일 6개월 추가");
                                    hhWefirmCustomerDTO.setExpiredAt(String.valueOf(now.plusMonths(6)));
                                } else {
                                    log.info("## 만료일 유지");
                                    hhWefirmCustomerDTO.setExpiredAt(String.valueOf(expiredAt));
                                }
                            }
                        } else { // client_pass 데이터 미존재
                            if (expiredAt.isBefore(now.plusMonths(6))) {
                                log.info("## 만료일 6개월 추가");
                                hhWefirmCustomerDTO.setExpiredAt(String.valueOf(now.plusMonths(6)));
                            } else {
                                log.info("## 만료일 유지");
                                hhWefirmCustomerDTO.setExpiredAt(String.valueOf(expiredAt));
                            }
                        }
                    } else { // 만료되지 않았고 , 이미 다른 PM이 점유중인경우
                        log.info("## 다른 PM 점유중");
                        hhWefirmCustomerDTO.setResultCode("ALREADY_EXISTS");
                        return;
                    }
                }
            } else { // 만료일 미존재
                log.info("## 만료일NULL");
                hhWefirmCustomerDTO.setExpiredAt(String.valueOf(now.plusMonths(6)));
            }
            hhWefirmCustomerDTO.setPmId(data.getPmId());
            hhMyWefirmMapper.updateClientPm(hhWefirmCustomerDTO); // client_pm update
        }
    }

    @Transactional
    public void insertPasser(HhWefirmCustomerDTO hhWefirmCustomerDTO) throws Exception{
        Integer taxFileId = commonFileService.fileUpload(hhWefirmCustomerDTO.getTaxFiles(), "TAX");
        hhWefirmCustomerDTO.setTaxFileId(String.valueOf(taxFileId));
        hhWefirmCustomerDTO.setApprFlag("N");
        hhMyWefirmMapper.insertClientPass(hhWefirmCustomerDTO);
    }

    @Transactional
    public void updatePasser(HhWefirmCustomerDTO hhWefirmCustomerDTO) throws Exception{
        if(StringUtils.equals(hhWefirmCustomerDTO.getFileChangeFlag(),"Y")){
            Integer taxFileId = commonFileService.fileUpload(hhWefirmCustomerDTO.getTaxFiles(), "TAX");
            hhWefirmCustomerDTO.setTaxFileId(String.valueOf(taxFileId));
        }
        hhMyWefirmMapper.updateClientPass(hhWefirmCustomerDTO);
    }

	public HhWefirmHeadhunterDTO selectWefirmAuth(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		return hhMyWefirmMapper.selectWefirmAuth(hhWefirmHeadhunterDTO);
	}
    @Transactional
    public void updateApprovePasser(HhWefirmCustomerDTO hhWefirmCustomerDTO) throws Exception{
        if(StringUtils.equals(hhWefirmCustomerDTO.getApprFlag(),"Y")) { // 승인 시 만료일,인증일 1년 +
            LocalDateTime now = LocalDateTime.now();
            hhWefirmCustomerDTO.setExpiredAt(String.valueOf(now.plusYears(1)));
            hhWefirmCustomerDTO.setCertifiedAt(String.valueOf(now.plusYears(1)));
            hhMyWefirmMapper.updateClientPm(hhWefirmCustomerDTO);
        }
        hhMyWefirmMapper.updateClientPass(hhWefirmCustomerDTO);
    }
}