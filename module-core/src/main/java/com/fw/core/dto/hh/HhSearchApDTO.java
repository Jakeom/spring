package com.fw.core.dto.hh;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhSearchApDTO {
    private String id     ;       
    private String memberId   ;  
    private String industry     ; 
    private String careerSt     ;
    private String careerEn     ;
    private String ageSt        ;
    private String ageEn        ;
    private String salarySt     ;
    private String salaryEn     ;
    private String hp1           ;
    private String hp2           ;
    private String workplace;
    private String company       ;
    private String foreignLang  ;
    private String certificate   ;
    private String keyword       ;
    private String createdAt     ;
    private String updatedAt     ;
    private String delFlag       ;
}
