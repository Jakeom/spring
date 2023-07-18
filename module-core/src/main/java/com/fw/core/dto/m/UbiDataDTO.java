package com.fw.core.dto.m;

import java.io.Serializable;
import java.util.List;

import com.fw.core.dto.CommonDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UbiDataDTO extends CommonDTO implements Serializable {

	// member
	private String originResume;
	private String representation;
	private String name;
	private String birth;
	private String genderCd;
	private String employmentStatusCd;
	private String phone;
	private String email;
	private String address;
	private String isVeterans;
	private String veteransReason;
	private String disability;
	private String disabilityRatingCd;
	private String disabilityRatingReason;
	private String militaryServiceCd;
	private String militaryServiceNm;
	private String coreAbility;
	private String careerDescription;
	private String selfIntroduction;
	private String desiredSalary;
	private String bylaws;
	private String desiredPosition;
	private String joinDateCd;
	private String showHeader;
	private String constructor;
	private String location;
	private String howcome;
	private String specNm;
	private String spec;

	// academicbackground
	private String degreeCd;
	private String schoolName;
	private String locationCd;
	private String inOverseas;
	private String entranceYm;
	private String entranceStatusCd;
	private String graduationYm;
	private String graduationStatusCd;
	private String grades;
	private String majorClassCd;
	private String majorName;

	// careers
	private String companyName;
	private String departmentName;
	private String positionCd;
	private String positionInput;
	private String dutyCd;
	private String dutyInput;
	private String annualSalary;
	private String task;
	private String careerEntranceYm;
	private String resignationYm;
	private String holdOffice;
	private String salary;

	// mCareers
	private String carCompanyName;
	private String carDepartmentName;
	private String carPositionCd;
	private String carPositionInput;
	private String carDutyCd;
	private String carDutyInput;
	private String carAnnualSalary;
	private String carTask;
	private String carResignationYm;
	private String carHoldOffice;

	// company
	private String mCompanyName;

	// ExternalActivities
	private String content;

	// awards
	private String awardNm;

	// Industry
	private String industry;

	// Language
	private String languageCd;
	private String languageInput;
	private String languageCertificationCd;
	private String conversationCd;
	private String testTypeCd;
	private String testInput;
	private String testScore;
	private String obtainYm;

	// License
	private String licenseName;
	private String agency;
	private String licenseObtainYm;

	// picturefile
	private String id;
	private String url;
	private String path;
	private String pictureFileName;
	private String width;
	private String ext;
	private String pictureFileId;
	private String attachFileId;
	private String enAttachFileId;

	// skill
	private String skillName;
	private String skillContent;

	// englishResume
	private String enAttachType;
	private String enUrl;

	// TotalCareer
	private String totalCareer;
	private String totalCareerNm;

	// Wefirm
	private String wefirmName;

	private String resumeId;
	private String apPhone;
	private String companyScale;
	private String apAddress;
	private String createdAt;

	private CommonDTO pictureFile;
	private List<UbiDataDTO> academicBackgrounds;
	private List<UbiDataDTO> careers;
	private List<UbiDataDTO> externalActivities;
	private List<UbiDataDTO> awards;
	private List<UbiDataDTO> licenses;
	private List<UbiDataDTO> languages;
	private List<UbiDataDTO> skills;
	private List<UbiDataDTO> portfolios;
	private List<UbiDataDTO> specs;
	private List<UbiDataDTO> idList;
	private UbiDataDTO englishResume;
	private UbiDataDTO position;
	private UbiDataDTO applicant;
	private UbiDataDTO resume;
	private List<UbiDataDTO> company;

}
