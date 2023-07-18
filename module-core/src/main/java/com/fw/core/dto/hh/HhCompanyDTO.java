package com.fw.core.dto.hh;

import java.util.List;

import com.fw.core.dto.CommonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HhCompanyDTO extends CommonDTO {

	private String id;
	private String hrCompanyId;
	private String address;
	private String ceo;
	private String closed;
	private String companyName;
	private String companyScale;
	private String companyStatus;
	private String deleted;
	private String establishDate;
	private String industry;
	private String licenseNumber;
	private String location;
	private String marketListing;
	private String phone;
	private String managerPhone;
	private String memberId;
	private String delFlag;
	private String content;
	private String email;
	private String name;
	private String workAddress;
	private String positionCreatedAt;
	private String coworkCnt;
	private String resumeCnt;
	private String companyId;
	private String managerId;
	private String memoId;

	// position
	private String title;
	private String endDate;
	private String status;
	private String createdAt;
	private String jobDescription;
	private String feeInfo;
	private String warrantyTerm;
	private String etcComment;
	private String positionId;
	private String mode;
	private String memberName;
	private String team;
	private String salary;
	private String targetCompany;
	private String directPositionFlag;
	private String positionReason;

	private String coJoinFlag;
	private String[] idArray;
	private List<HhCompanyDTO> managerList;

}
