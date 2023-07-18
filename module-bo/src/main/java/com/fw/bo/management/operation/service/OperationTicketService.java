package com.fw.bo.management.operation.service;

import com.fw.core.dto.bo.BoTicketUseHistoryDTO;
import com.fw.core.mapper.db1.bo.BoTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationTicketService {

	private final BoTicketMapper boTicketMapper;

	public List<BoTicketUseHistoryDTO> selectTicketList(BoTicketUseHistoryDTO boTicketUseHistoryDTO) {
		return boTicketMapper.selectTicketList(boTicketUseHistoryDTO);
	}

	public List<BoTicketUseHistoryDTO> saerchTicketList(BoTicketUseHistoryDTO boTicketUseHistoryDTO) {
		return boTicketMapper.searchTicketList(boTicketUseHistoryDTO);
	}

	public List<BoTicketUseHistoryDTO> saerchStatus(BoTicketUseHistoryDTO boTicketUseHistoryDTO) {
		return boTicketMapper.searchStatus(boTicketUseHistoryDTO);
	}
}
