package com.fw.core.mapper.db1.hh;


import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.hh.HhPaymentDTO;
import com.fw.core.dto.hh.HhPointUseHistoryDTO;

import java.util.List;

public interface HhPointMapper {

    // 보유 포인트 조회
    public FoPointDTO selectHhPoint(FoPointDTO foPointDTO);

    // 포인트 update
    public void updatePoint(FoPointDTO foPointDTO);

    // 포인트 변경 내역
    public void insertPointUseHistory(FoPointDTO foPointDTO);

    // hh 포인트 테이블에 없을시 insert
    public void insertPointHh(FoPointDTO foPointDTO);

    // 포인트 구매시 payment insert
    public void insertPayment(HhPaymentDTO hhPaymentDTO);

    // 결제취소 가능 포인트 조회
    public List<HhPointUseHistoryDTO> selectPurchasePoint(HhPointUseHistoryDTO hhPointUseHistoryDTO);

    // 결제취소 구매내역 업데이트
    public void updatePayment(HhPaymentDTO hhPaymentDTO);

    // 결제취소 가능 포인트 상세조회
    public HhPointUseHistoryDTO selectPurchasePointInfo(HhPointUseHistoryDTO hhPointUseHistoryDTO);

}
