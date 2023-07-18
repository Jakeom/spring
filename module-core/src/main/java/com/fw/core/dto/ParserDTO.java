package com.fw.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String memberSn;
    private String fileName;
    private String filePath;
    private String callType;

}
