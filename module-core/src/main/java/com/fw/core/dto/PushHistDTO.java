package com.fw.core.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PushHistDTO extends CommonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String pushHistSeq;
	private String dispType;
	private String dispTypeNm;
	private String memberId;
	private String title;
	private String content;
	private String imgNm;
	private String imgPath;
	private String imgSize;
	private String url;
	private String phoneType;
	private String phoneToken;
	private String sendTime;
	private String setTime;
	private String sendYn;
	private String reserveYn;
	private String createId;
	private String createTime;
	private String updateId;
	private String updateTime;
	private String receiveYn;
	private String receiveTime;
	private String delFlag;
	private String userType;
	private String userId;
	private String message;
	private Integer pushCnt;
}