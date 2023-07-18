package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HhWefirmCustomerDTO extends CommonDTO {

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
	private String isPrivate;
	private String dDay;

	// 고객사 관리자 client_pm
	private String licenseNo;       // 사업자번호
	private String expiredAt;       // 만료일시
	private String updatedAt;       // 수정일시
	private String certifiedAt;     // 인증일시
	private String registerAt;      // 등록일
	private String pmName;			// PM 이름
	private String cerifiedFlag;	// 인증여부
	private String pmMemberId;
	private String wefirmId;
	private String formatExpiredAt;

	// 합격자 client_pass
	private String clientPassId;	// cliend_pass id
	private String pmId;            // 관리자 아이디
	private String passName;        // 합격자명
	private String apprFlag;        // 합격자 승인상태
	private String taxFileId;       // 세금계산서 파일 아이디
	private String rejectMemo;      // 반려사유

	private String fileChangeFlag;
	private String statusMsg;
	private String[] customerArr;
	private MultipartFile[] taxFiles;

	private String resultCode;
	private String registerType;
	
	private String sfName;
	private String type;
}
