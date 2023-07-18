package com.fw.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.util.DateUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternalDateDTO {

    @JsonIgnore
    private String _nowDate;
    @JsonIgnore
    private String _nowDateHour;
    @JsonIgnore
    private String _nowDateHourMin;
    @JsonIgnore
    private String _nowDateHourMinSec;

    public String get_nowDate() {
        return DateUtil.getStrNow("yyyy-MM-dd");
    }
    public String get_nowDateHour() {
        return DateUtil.getStrNow("yyyy-MM-dd HH");
    }
    public String get_nowDateHourMin() {
        return DateUtil.getStrNow("yyyy-MM-dd HH:mm");
    }
    public String get_nowDateHourMinSec() {
        return DateUtil.getStrNow("yyyy-MM-dd HH:mm:ss");
    }
}
