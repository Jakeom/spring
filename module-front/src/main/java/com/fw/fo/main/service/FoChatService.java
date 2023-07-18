package com.fw.fo.main.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fw.core.dto.PushHistDTO;
import com.fw.core.mapper.db1.fo.FoChatMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoChatService {
	private final FoChatMapper foChatMapper;

	public List<PushHistDTO> selectAlarmList(PushHistDTO pushHistDTO) {
		List<PushHistDTO> list = foChatMapper.selectAlarmList(pushHistDTO);
		for (PushHistDTO p : list) {
			if (StringUtils.isNotBlank(p.getUrl())) { // 댓글,회사질문 알림 시 게시글 상세 URL 변경
				String mobileUrl = p.getUrl(); // 웹뷰 URL
				String foWebUrl = mobileUrl.replace("/m/community/detail", "/fo/community/board/detail"); // 후보자 게시판 URL
				String hhWebUrl = mobileUrl.replace("/m/community/detail", "/hh/community/board/detail"); // 헤드헌터 게시판 URL
				if (StringUtils.equals(p.getDispType(), "COMMENT")) {
					if (StringUtils.equals(p.getUserType(), "AP")) {
						p.setUrl(foWebUrl);
					} else {
						p.setUrl(hhWebUrl);
					}
				}
			}
		}
		return list;
	}

	public void updateAlarmReceive(PushHistDTO pushHistDTO) {
		pushHistDTO.setReceiveYn("Y");
		foChatMapper.updateAlarmReceive(pushHistDTO);
	}

	public PushHistDTO selectAlarmCnt(PushHistDTO pushHistDTO) {
		return foChatMapper.selectAlarmCnt(pushHistDTO);
	}
}
