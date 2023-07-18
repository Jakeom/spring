package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminIpDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoAdminMapper {

    /**
     * 사용자관리 리스트
     */
    List<BoAdminDTO> selectAdminList(BoAdminDTO boAdminDTO);
    /**
     * ip 리스트
     */
    List<BoAdminIpDTO> selectIpForAdminSeq(BoAdminIpDTO boAdminIpDTO);
    /**
     * 사용자 아이디 중복체크
     */
    int selectIdCheck(String adminId);
    /**
     * 사용자관리 상세
     */
    BoAdminDTO selectAdmin(BoAdminDTO boAdminDTO);
    /**
     * 사용자관리 상세 (AdminId)
     */
    BoAdminDTO selectAdminForAdminId(BoAdminDTO boAdminDTO);
    /**
     * 사용자관리 등록
     */
    void insertAdmin(BoAdminDTO boAdminDTO);
    /**
     * 사용자관리 수정
     */
    void updateAdmin(BoAdminDTO boAdminDTO);
    /**
     * 사용자관리 삭제
     */
    void deleteAdmin(BoAdminDTO boAdminDTO);
    /**
     * 비밀번호 틀린 횟수 증가
     */
    void updatePasswordFailCount(BoAdminDTO boAdminDTO);
    /**
     * 비밀번호 틀린 횟수 0으로 초기화
     */
    void updateResetPasswordFailCount(BoAdminDTO boAdminDTO);
}
