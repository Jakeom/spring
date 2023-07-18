package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

/**
 * 관리자 DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoAdminDTO extends CommonDTO implements UserDetails, Serializable {

    private String adminSeq;            // 사용자 순번
    private String orgId;               // 조직 id
    private String adminId;             // 사용자 id
    private String adminNm;             // 사용자이름
    private String email;               // 이메일
    private String emailId;             // 이메일 아이디
    private String emailDomain;         // 이메일 도메인
    private String adminStatusCd;       // 공통코드
    private String adminPassword;       // 사용자 비밀번호
    private String useStartDt;          // 사용 시작일시
    private String useEndDt;            // 사용 종료일시
    private String approvalSeq;         // 승인자 번호
    private String approvalDt;          // 승인일시
    private String createSeq;           // 생성자 번호
    private String createDt;            // 생성일시
    private String updateSeq;           // 수정자번호
    private String updateDt;            // 수정일시
    private String delFlag;             // 삭제여부
    private int passwordFailCount;      // 비밀번호 실패 횟수
    private String passwordChangeDt;    // 비밀번호 변경 일시
    private String loginId;
    private String adminStatusNm;

    private List<BoAdminIpDTO> ipList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.getAdminPassword();
    }

    @Override
    public String getUsername() {
        return this.getAdminId();
    }

    /* 계정 만료 여부 */
    @Override
    public boolean isAccountNonExpired() {
        boolean result = true;
        LocalDate startDt;
        LocalDate endDt;
        LocalDate now = LocalDate.now();
        if(StringUtils.isNotBlank(this.getUseStartDt())) {
            startDt = LocalDate.parse(this.getUseStartDt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(!startDt.equals(now) && !startDt.isAfter(now)){
                return false;
            }
        }

        if(StringUtils.isNotBlank(this.getUseEndDt())) {
            endDt = LocalDate.parse(this.getUseEndDt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(!endDt.equals(now) && !endDt.isBefore(now)){
                return false;
            }
        }
        return result;
    }

    /* 계정 정지 여부 */
    @Override
    public boolean isAccountNonLocked() {
        return StringUtils.equals("BLOCK", this.getAdminStatusCd());
    }

    /* 비밀번호 만료 여부 */
    @Override
    public boolean isCredentialsNonExpired() {
        boolean result = true;
        LocalDate startDt;
        LocalDate now = LocalDate.now().plusDays(180);
        if(StringUtils.isNotBlank(this.getPasswordChangeDt())) {
            startDt = LocalDate.parse(this.getPasswordChangeDt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(!startDt.equals(now) && !startDt.isAfter(now)){
                return false;
            }
        }
        return result;
    }

    /* 사용 가능한 계정인지 여부 */
    @Override
    public boolean isEnabled() {
        return (StringUtils.equals("NOT_APPROVED", this.getAdminStatusCd()) || (StringUtils.equals("PENDING", this.getAdminStatusCd())));
    }

}
