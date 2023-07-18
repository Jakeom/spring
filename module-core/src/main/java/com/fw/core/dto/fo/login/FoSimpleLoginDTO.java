package com.fw.core.dto.fo.login;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FoSimpleLoginDTO extends CommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String simpleAuthSeq;    /** 간편로그인 seq */
    private String memberId; /** 멤버 seq */
    private String simpleAuthCd;        /** 간편로그인 코드 */
    private String simpleAuthVal;      /** 간편로그인 아이디값 */
    private String delFlag;      /** 삭제여부 */
    private String regSeq;          /** 생성번호 */
    private String regDate;      /** 생성일시 */
    private String email;      /** 이메일 */
    private String referralCode; /** 추천코드 */
}
