// 전역변수 선언부
let careerSave = $("div.career-area > div.career").clone();
let addEducation = $('div.university-area').clone();
let addPortfolio = $('div.portfolio-area > div').clone();
// 전역변수 선언부

// Validation Check
$("input[name=birth], input[name=lastSalary], input[name=salarys], input[name=desiredSalary]").keyup(e => Utils.onlyNumber($(e.target))); // 입력값 강제로 숫자만 입력받도록 처리
$(document).on("focusout", "[name=graduationYm], [name=entranceYm], [name=resignationYms], [name=entranceYms]", e => Utils.alert(Utils.dateCheck($(e.target), "YYYYMM"))); // 검증
$("input[name=birth]").focusout(e => Utils.alert(Utils.dateCheck($(e.target), "YYYYMMDD"))); // 생년월일 검증
$("#img-upload").change(e => Utils.fileCheck($(e.target), 2, true)); // 이미지 체크

// 고등학교명 포커스 아웃시 후 처리
$("input[name=schoolName]").focusout(function(){
    setTimeout(function(){
        $(".search-result-list.high-school ul").empty();
    }, 500);
});

// 고등학교명 선택시 처리
$(document).on("click", "[data-role=btnSelectHighSchool]", function(){
    let self = $(this);
    $(".high-school-area input[name=locationCd]").val(self.data("locationCd"));
    $(".high-school-area input[name=schoolName]").val(self.data("name"));
    $(".high-school-area input[name=schoolCd]").val(self.data("schoolCd"));
    $(".search-result-list.high-school ul").empty();
});

// 고등학교명 직접입력 처리
$(document).on("click", "[data-role=directHighSchoolInput]", function(){
    $(".high-school-area input[name=locationCd]").val("");
    $(".high-school-area input[name=schoolName]").val("");
    $(".high-school-area input[name=schoolCd]").val("H9999");
    $(".search-result-list.high-school ul").empty();
});

$("input[name=schoolName]").keyup(function(){
    let val = $(this).val().trim();
    console.log(val)
    if(val == ""){
        return false;
    }

    $.ajax({
        url: "/bo/common/high-school/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function(d){
            $(".search-result-list.high-school ul").empty();
            if(d.data.length == 0){
                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="directHighSchoolInput">(직접입력)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                $(".search-result-list.high-school ul").append(li);
            } else {
                $(d.data).each(function(){
                    let li =  '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectHighSchool" data-name="' + this.name + '" data-school-cd="' + this.code + '" data-location-cd="' + this.region + '">';
                    li += '                <p class="company-name">' + this.name + ' <small>(' + this.address + ')</small></p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    $(".search-result-list.high-school ul").append(li);
                });
            }
        },
    });
});




// 대학교명 포커스 아웃시 후 처리
$(document).on("focusout", "input[name=universityName]", function(){
    setTimeout(function(){
        $(".search-result-list.university ul").empty();
    }, 500);
});

// 대학교명 선택시 처리
$(document).on("click", "[data-role=btnSelectUniversity]", function(){
    let self = $(this);
    self.closest(".university-area").find("input[name=locationCd]").val(self.data("locationCd"));
    self.closest(".university-area").find("input[name=universityName]").val(self.data("name"));
    self.closest(".university-area").find("input[name=schoolCd]").val(self.data("schoolCd"));
    self.closest(".university-area").find("input[name=schoolType]").val(self.data("schoolType"));
    self.closest(".university-area").find("input[name=estType]").val(self.data("estType"));
    $(".search-result-list.university ul").empty();
});

// 대학교명 직접입력 처리
$(document).on("click", "[data-role=directUniversityInput]", function(){
    let self = $(this).closest(".university-area");
    self.find("input[name=locationCd]").val("");
    self.find("input[name=universityName]").val("");
    self.find("input[name=schoolCd]").val("H9999");
    $(".search-result-list.university ul").empty();
});

// 대학교명 검색시 검색결과 팝업
$(document).on("keyup", "input[name=universityName]", function(){
    let self = $(this).closest(".university-area");
    let val = $(this).val().trim();

    if(val == ""){
        return false;
    }

    $.ajax({
        url: "/bo/common/university/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function(d){
            self.find(".search-result-list.university ul").empty();
            if(d.data.length == 0){
                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="directUniversityInput">(직접입력)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.university ul").append(li);
            } else {
                $(d.data).each(function(){
                    let li =  '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectUniversity" data-name="' + this.name + '" data-school-cd="' + this.code + '" data-location-cd="' + this.region + '" data-school-type="' + this.schoolType +'" data-est-type="' + this.estType + '">';
                    li += '                <p class="company-name">' + this.name + ' <small>(' + this.campusName + ')</small></p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    self.find(".search-result-list.university ul").append(li);
                });
            }
        },
    });
});


// 학력 항목 추가
$("[data-role=addEducation]").click(function (){
    $(this).parent().before(addEducation.clone());
});

// 학력 항목 삭제
$("[data-role=deleteEducation]").click(function(){
    $(".university-area:last").remove();
});

// 고등학교 해외여부 선택시
$("#overseas").change(function(){
    if($(this).is(":checked")){
        $('.high-school-area input[name=inOverseas]').val("1");
    } else {
        $('.high-school-area input[name=inOverseas]').val("0");
    }
});

// 대학교 해외여부 선택시
$(document).on("change", ".univ-oversea", function(){
    if($(this).is(":checked")){
        $(this).closest(".university-area").find('input[name=inOverseas]').val("1");
    } else {
        $(this).closest(".university-area").find('input[name=inOverseas]').val("0");
    }
});


// 기업명 검색시 검색결과 팝업
$("input[name=companyNames]").keyup(function(){
    let self = $(this).closest(".company-area");
    let val = $(this).val().trim();

    if(val == ""){
        return false;
    }

    $.ajax({
        url: "/bo/common/company/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function(d){
            self.find(".search-result-list.company ul").empty();
            if(d.data.length == 0){
                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="niceCompanyInput">(찾아보기)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.company ul").append(li);
            } else {
                $(d.data).each(function(){
                    let li =  '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectCompany" data-name="' + this.companyName + '" data-industry="' + this.industry + '">';
                    li += '                <p class="company-name">' + this.companyName + '</p>';
                    li += '                <p class="company-obj">' + this.companyScale + ' | ' + this.industry + '</p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    self.find(".search-result-list.company ul").append(li);
                });

                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name"><a href="javascript:;" data-role="niceCompanyInput">더 찾아보기</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.company ul").append(li);
            }
        },
    });
});

// 나이스 기업 검색
$(document).on("click", "[data-role=niceCompanyInput]", function(){
    let val =  $("input[name=companyNames]").val().trim();
    let self = $(this).closest(".company-area");
    $.ajax({
        url: "/bo/common/nice-company/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function(d){
            console.log(d);
            self.find(".search-result-list.company ul").empty();
            if(d.data.length == 0){
                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="directCompanyInput">(직접입력)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.company ul").append(li);
            } else {
                $(d.data).each(function(){
                    const companyScale = {
                        1: '대기업'
                        , 2: '중소기업'
                        , 3: '중견기업'
                        , 0: '대상아님'
                    };

                    let li =  '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectCompany" data-name="' + this.korentrnm + '" data-industry="' + this.sanup + '">';
                    li += '                <p class="company-name">' + this.korentrnm + '</p>';
                    li += '                <p class="company-obj">' + companyScale[this.scaledivcd] + ' | ' + this.sanup + '</p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    self.find(".search-result-list.company ul").append(li);
                });
            }
        },
    });
});

// 직접 입력
$("[data-role=directCompanyInput]").click(function(){
    let self = $(this).closest(".company-area");
    self.find(".search-result-list.company ul").empty();
});

// 기업선택시
$(document).on("click", "[data-role=btnSelectCompany]", function(){
    $("input[name=companyNames]").val($(this).data("name"));
    $(".search-result-list.company ul").empty();
});

// 포커스 아웃시 창 닫기
$(document).on("focusout", "input[name=companyNames]", function(){
    setTimeout(function(){
        $(".search-result-list.company ul").empty();
    }, 500);
});

// 포트폴리오 항목추가
$(document).on("click", "[data-role=addPortfolio]", function (){
    $(this).closest(".content").find(".portfolio-area").append(addPortfolio.clone());
    $(".portfolio-list").each(function(idx){
        $(this).find("#portfolioFile").attr("id", "portfolioFile" + idx);
        $(this).find("label").attr("for", "portfolioFile" + idx);
    });
});

// 포트폴리오 형태 변경시
$(document).on("change", ".attach-type", function(){
    let self = $(this).closest(".row");
    if($(this).val() == "URL"){
        self.find(".portfolio-url").show();
        self.find(".portfolio-file").hide();
    } else {
        self.find(".portfolio-url").hide();
        self.find(".portfolio-file").show();
    }
});

// 포트폴리오 항목 삭제
$("[data-role=deletePortfolio]").click(function(){
    if($(".portfolio-area > div").length != 1){
        $(".portfolio-area > div:last").remove();
        $(".portfolio-list").each(function(idx){
            $(this).find("#portfolioFile").attr("id", "portfolioFile" + idx);
            $(this).find("label").attr("for", "portfolioFile" + idx);
        });
    }
});


// 경력사항 저장
$("button[data-role=careerSave]").click(function (){
    let companyNames = $('input[name=companyNames]');
    let entranceYms = $('input[name=entranceYms]');
    let resignationYms = $('input[name=resignationYms]');
    let holdOffices = $('input[name=holdOffices]');
    let departmentNames = $('input[name=departmentNames]');
    let positionInputs = $('input[name=positionInputs]');
    let dutyInputs = $('input[name=dutyInputs]');
    let salarys = $('input[name=salarys]');
    let inputTasks = $('textarea[name=inputTasks]');

    // Validation
    if(!Utils.alert(Utils.valid(companyNames, null, "회사명")).result) return false;
    if(!Utils.alert(Utils.valid(departmentNames, null, "부서명")).result) return false;
    if(!Utils.alert(Utils.valid(entranceYms, null, "입사년월")).result) return false;
    if(!Utils.alert(Utils.valid(resignationYms, null, "퇴사년월")).result) return false;
    if(!Utils.alert(Utils.valid(positionInputs, null, "직급")).result) return false;
    if(!Utils.alert(Utils.valid(dutyInputs, null, "직책")).result) return false;
    if(!Utils.alert(Utils.valid(salarys, null, "최종연봉")).result) return false;
    if(!Utils.alert(Utils.valid(inputTasks, null, "담당업무")).result) return false;

    // Creation
    let mode = $("input[name=mode]").val();
    if(mode == "INSERT"){
        let careerSaveBlock = careerSave.clone().show();
        careerSaveBlock.find('input[name=companyName]').val(companyNames.val());
        careerSaveBlock.find('input[name=departmentName]').val(departmentNames.val());
        careerSaveBlock.find('input[name=entranceYm]').val(entranceYms.val());
        careerSaveBlock.find('input[name=resignationYm]').val(resignationYms.val());
        careerSaveBlock.find('input[name=positionInput]').val(positionInputs.val());
        careerSaveBlock.find('input[name=dutyInput]').val(dutyInputs.val());
        careerSaveBlock.find('input[name=salary]').val(salarys.val());
        careerSaveBlock.find('input[name=inputTask]').val(inputTasks.val());
        careerSaveBlock.find('input[name=holdOffice]').val(holdOffices.val());
        careerSaveBlock.find('.company').text(companyNames.val());
        careerSaveBlock.find('.position-input').text(positionInputs.val());
        careerSaveBlock.find('.entranceYms').text(entranceYms.val());
        careerSaveBlock.find('.department-name').text(departmentNames.val());
        careerSaveBlock.find('.duty-input').text(dutyInputs.val());
        careerSaveBlock.find('.holdOffice').text(holdOffices.val());
        careerSaveBlock.find('.salary').text(salarys.val());
        careerSaveBlock.find('.career-txt').html(inputTasks.val());
        careerSaveBlock.appendTo($(".career-area"));
        if(holdOffices.val() == '1') {
            careerSaveBlock.find('.holdOffice').text('재직중');
        } else {
            careerSaveBlock.find('.resignationYms').text(resignationYms.val());
        }
    } else {
        let career = $(".career:eq(" + mode + ")");
        career.find("input[name=companyName]").val(companyNames.val());
        career.find("input[name=departmentName]").val(departmentNames.val());
        career.find("input[name=entranceYm]").val(entranceYms.val());
        career.find("input[name=resignationYm]").val(resignationYms.val());
        career.find("input[name=positionInput]").val(positionInputs.val());
        career.find("input[name=dutyInput]").val(dutyInputs.val());
        career.find("input[name=salary]").val(salarys.val());
        career.find("input[name=inputTask]").val(inputTasks.val());
        career.find('.company').text(companyNames.val());
        career.find('.position-input').text(positionInputs.val());
        career.find('.entranceYms').text(entranceYms.val());
        career.find('.department-name').text(departmentNames.val());
        career.find('.duty-input').text(dutyInputs.val());
        career.find('.salary').text(salarys.val());
        career.find('.career-txt').html(inputTasks.val());
        if(holdOffices.val() == '1') {
            career.find('.holdOffice').text('재직중');
        } else {
            career.find('.resignationYms').text(resignationYms.val());
        }
    }

    // Init
    companyNames.val('');
    entranceYms.val('');
    resignationYms.val('');
    holdOffices.val('');
    $("#holdOffice").prop("checked", false);
    departmentNames.val('');
    positionInputs.val('');
    dutyInputs.val('');
    salarys.val('');
    inputTasks.val('');
    $("input[name=mode]").val("INSERT");

    // 경력 계산
    let month = 0;
    $(".career").each(function(i){
        if(i != 0){
            let resignationYm = moment($(this).find("input[name=resignationYm]").val(), "YYYYMM");
            let entranceYm = moment($(this).find("input[name=entranceYm]").val(), "YYYYMM");
            month += resignationYm.diff(entranceYm, 'months');
        }
    });

    $('input[name=totalCareer]').val(month);
    $("#totalCareer").text(Math.floor(month / 12) + "년 " + (month % 12) + "개월");
});

// 경력사항 삭제
$(document).on("click", "[data-role=careerDelete]", function (){
    $(this).closest(".career").remove();
    // 경력 계산
    let month = 0;
    $(".career").each(function(i){
        if(i != 0){
            let resignationYm = moment($(this).find("input[name=resignationYm]").val(), "YYYYMM");
            let entranceYm = moment($(this).find("input[name=entranceYm]").val(), "YYYYMM");
            month += resignationYm.diff(entranceYm, 'months');
        }
    });

    $('input[name=totalCareer]').val(month);
    $("#totalCareer").text(Math.floor(month / 12) + "년 " + (month % 12) + "개월");
});

// 재직중 선택시
$("#holdOffice").change(function(){
    if($(this).is(":checked")){
        $('input[name=resignationYms]').val(moment().format("YYYYMM"));
        $('input[name=holdOffices]').val("1");
    } else {
        $('input[name=holdOffices]').val("0");
    }
});

// 내규에 따름 선택시
$("#bylaws").change(function(){
    if($(this).is(":checked")){
        $('input[name=bylaws]').val("1");
    } else {
        $('input[name=bylaws]').val("0");
    }
});

// 경력사항 수정
$(document).on("click", "[data-role=careerModify]", function(){
    let self = $(this);
    $("input[name=mode]").val(self.closest(".career").index());
    $("input[name=companyNames]").val(self.closest(".career").find("input[name=companyName]").val());
    $("input[name=departmentNames]").val(self.closest(".career").find("input[name=departmentName]").val());
    $("input[name=entranceYms]").val(self.closest(".career").find("input[name=entranceYm]").val());
    $("input[name=resignationYms]").val(self.closest(".career").find("input[name=resignationYm]").val());
    $("input[name=positionInputs]").val(self.closest(".career").find("input[name=positionInput]").val());
    $("input[name=dutyInputs]").val(self.closest(".career").find("input[name=dutyInput]").val());
    $("input[name=salarys]").val(self.closest(".career").find("input[name=salary]").val());
    $("textarea[name=inputTasks]").val(self.closest(".career").find("input[name=inputTask]").val());
    $("#holdOffice").prop("checked", self.closest(".career").find("input[name=holdOffice]").val() == "1");
});

// 이력서 사진 파일 변경시
$("#img-upload").change(function(e){
    let fileCheckObj = Utils.fileCheck($(this), 2, true);
    if(!fileCheckObj.result){
        $("img-upload").css("display", "none");
        $("#img-upload-file").css("display", "none");
        $(".img-upload-label").css("opacity", "1");
        alert(fileCheckObj.msg);
        return false;
    }

    let reader = new FileReader();
    let files = e.target.files;
    let filesArr = Array.prototype.slice.call(files);
    filesArr.forEach(function(f) {
        reader.onload = function (e) {
            $("#img-upload-file").attr('src', e.target.result);
        }
        reader.readAsDataURL(f);
    });

    $("#img-upload-file").css("display", "block");
    $(".img-upload-label").css("opacity", "0");
});

// 포트폴리오 파일 변경시
$(document).on("change", ".portfolio-files", function(e){
    let fileCheckObj = Utils.fileCheck($(this), 10, false);
    if(!fileCheckObj.result){
        alert(fileCheckObj.msg);
        return false;
    }
    $(this).closest(".portfolio-file").find(".file-name").text(e.target.files[0].name);
});

// 영문 이력서 파일 변경시
$(document).on("change", "#enResumeFile", function(e){
    let fileCheckObj = Utils.fileCheck($(this), 10, false);
    if(!fileCheckObj.result){
        alert(fileCheckObj.msg);
        return false;
    }
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});


$(document).on("click", "[data-role=clickImg]", function(e){
    $("#img-upload").click()
})







// 이력서 저장
$('button[data-role=applyResume]').click(function (){
    // validation
    let name = $("input[name=userName]")
    let email = $("input[name=email]")
    let phone = $("input[name=phone]")
    let birth = $("input[name=birth]");
    let lastSalary = $("input[name=lastSalary]");
    let address = $("input[name=address]");
    let main = $("input[name=spec]:eq(0)");
    let spec = $("input[name=spec]:eq(1)");
    let etc = $("input[name=spec]:eq(2)");
    let degreeCd = $(".high-school-area input[name=degreeCd]");
    let schoolName = $(".high-school-area input[name=schoolName]");
    let locationCd = $(".high-school-area input[name=locationCd]");
    let graduationYm = $(".high-school-area input[name=graduationYm]");
    let graduationStatusCd = $(".high-school-area select[name=graduationStatusCd]");

    if(!Utils.alert(Utils.valid(name, null, "이름")).result) return false;
    if(!Utils.alert(Utils.valid(birth, null, "생년월일")).result) return false;
    if(!Utils.alert(Utils.valid(lastSalary, null, "최종연봉")).result) return false;
    if(!Utils.alert(Utils.valid(phone, null, "휴대폰")).result) return false;
    if(!Utils.alert(Utils.valid(email, null, "이메일")).result) return false;
    if(!Utils.alert(Utils.valid(address, null, "주소")).result) return false;
    if(!Utils.alert(Utils.valid(main, null, "핵심역량(MAIN)")).result) return false;
    if(!Utils.alert(Utils.valid(spec, null, "핵심역량(상세)")).result) return false;
    if(!Utils.alert(Utils.valid(etc, null, "핵심역량(기타)")).result) return false;
    if(!Utils.alert(Utils.valid(degreeCd, null, "고등학교 - 학교종류")).result) return false;
    if(!Utils.alert(Utils.valid(schoolName, null, "고등학교 - 학교명")).result) return false;
    if(!Utils.alert(Utils.valid(locationCd, null, "고등학교 - 소재지")).result) return false;
    if(!Utils.alert(Utils.valid(graduationYm, null, "고등학교 - 졸업년월")).result) return false;
    if(!Utils.alert(Utils.valid(graduationStatusCd, null, "고등학교 - 졸업상태")).result) return false;

    let flag = true;
    $(".add-education").each(function(){
        if(!Utils.alert(Utils.valid($(this).find("select[name=degreeCd]"), null, "대학교이상 - 학교종류")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=universityName]"), null, "대학교이상 - 학교명")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=locationCd]"), null, "대학교이상 - 소재지")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=entranceYm]"), null, "대학교이상 - 입학년월")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("select[name=entranceStatusCd]"), null, "대학교이상 - 입학상태")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=graduationYm]"), null, "대학교이상 - 졸업년월")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("select[name=graduationStatusCd]"), null, "대학교이상 - 졸업상태")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=majorName]"), null, "대학교이상 - 주전공명")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=majorGrades]"), null, "대학교이상 - 학점")).result) flag = false;
    });

    if(!flag) {
        return false;
    }

    let formData = new FormData();

    // 프로필 이미지
    $("#img-upload").each(function(){
        Array.from($(this)[0].files).map(e => formData.append('resumeImageFiles', e));
    });

    // 기본 정보
    let jsonData = {
        'lastSalary': $("input[name=lastSalary]").val(),
        'name': $('input[name=userName]').val(),
        'phone': $('input[name=phone]').val(),
        'email': $('input[name=email]').val(),
        'address': $('input[name=address]').val(),
        'birth': $('input[name=birth]').val(),
        'bylaws': $('input[name=bylaws]').val(),
        'desiredPosition': $('input[name=desiredPosition]').val(),
        'desiredSalary': $('input[name=desiredSalary]').val() == "" ? 0 : $('input[name=desiredSalary]').val(),
        'disability': $('select[name=disability]').val() == "" ? 0 : $('select[name=disability]').val(),
        'militaryServiceCd': $('select[name=militaryServiceCd]').val(),
        'isVeterans': $('select[name=isVeterans]').val() == "" ? 0 : $('select[name=isVeterans]').val(),
        'selfIntroduction': $('textarea[name=selfIntroduction]').val(),
        'totalCareer': $('input[name=totalCareer]').val(),
        'joinDateCd': $('select[name=joinDateCd]').val(),
        'desiredLocationCd': $('select[name=desiredLocationCd]').val(),
        'desiredHire': $('select[name=desiredHire]').val(),
        'languageInput': $('textarea[name=languageInput]').val(),
        'licenseName': $('textarea[name=licenseName]').val(),
        'awardName': $('textarea[name=awardName]').val(),
        'content': $('textarea[name=content]').val(),
        'careerDescription': $('.career-area').html(),
        'memberId': $("input[name=memberId]").val(),
        'parsingErrorId': $("input[name=id]").val()
    }

    // 핵심 역량
    let specList = [];
    $("input[name=specType]").each(function(i){
        specList.push({
            specType: $(this).val(),
            spec: $('input[name=spec]:eq(' + i + ')').val()
        });
    });
    jsonData.specList = specList;

    // 학력 - 고등학교
    let academicBackgroundList = [];
    academicBackgroundList.push({
        'degreeCd':  'HIGH',
        'name':  $('.high-school-area input[name=schoolName]').val(),
        'schoolCd':  $('.high-school-area input[name=schoolCd]').val(),
        'locationCd':  $('.high-school-area input[name=locationCd]').val(),
        'graduationYm':  $('.high-school-area input[name=graduationYm]').val(),
        'entranceYmYm':  $('.high-school-area input[name=entranceYm]').val(),
        'entranceStatusCd': $('.high-school-area input[name=entranceStatusCd]').val(),
        'graduationStatusCd': $('.high-school-area select[name=graduationStatusCd]').val(),
        'majorClassCd': $('.high-school-area input[name=majorClassCd]').val(),
        'inOverseas': $('.high-school-area input[name=inOverseas]').val(),
        'estType': $('.high-school-area input[name=estType]').val(),
        'schoolType': $('.high-school-area input[name=schoolType]').val(),
        'majorName': $('.high-school-area input[name=majorName]').val(),
        'majorGrades': $('.high-school-area input[name=majorGrades]').val(),
        'minorClassCd': $('.high-school-area input[name=minorClassCd]').val(),
        'minorName': $('.high-school-area input[name=minorName]').val(),
        'minorGrades': $('.high-school-area input[name=minorGrades]').val(),
        'academicBackgroundMajorList': [{
            'majorClassCd': $('.high-school-area input[name=majorClassCd]').val(),
            'majorName': $('.high-school-area input[name=majorName]').val(),
            'majorGrades': $('.high-school-area input[name=majorGrades]').val(),
            'minorClassCd': $('.high-school-area input[name=minorClassCd]').val(),
            'minorName': $('.high-school-area input[name=minorName]').val(),
            'minorGrades': $('.high-school-area input[name=minorGrades]').val()
        }]
    });

    // 대학교 이상
    $('div.university-area').each(function(){
        academicBackgroundList.push({
            'degreeCd':  $(this).find('select[name=degreeCd]').val(),
            'name':  $(this).find('input[name=universityName]').val(),
            'schoolCd':  $(this).find('input[name=schoolCd]').val(),
            'locationCd':  $(this).find('input[name=locationCd]').val(),
            'graduationYm':  $(this).find('input[name=graduationYm]').val(),
            'entranceYm':  $(this).find('input[name=entranceYm]').val(),
            'entranceStatusCd': $(this).find('select[name=entranceStatusCd]').val(),
            'graduationStatusCd': $(this).find('select[name=graduationStatusCd]').val(),
            'majorClassCd': $(this).find('input[name=majorClassCd]').val(),
            'inOverseas': $(this).find('input[name=inOverseas]').val(),
            'estType': $(this).find('input[name=estType]').val(),
            'schoolType': $(this).find('input[name=schoolType]').val(),
            'majorName': $(this).find('input[name=majorName]').val(),
            'majorGrades': $(this).find('input[name=majorGrades]').val(),
            'minorClassCd': $(this).find('input[name=minorClassCd]').val(),
            'minorName': $(this).find('input[name=minorName]').val(),
            'minorGrades': $(this).find('input[name=minorGrades]').val(),
            'academicBackgroundMajorList': [{
                'majorClassCd': $(this).find('input[name=majorClassCd]').val(),
                'majorName': $(this).find('input[name=majorName]').val(),
                'majorGrades': $(this).find('input[name=majorGrades]').val()
            },{
                'majorClassCd': $(this).find('input[name=minorClassCd]').val(),
                'majorName': $(this).find('input[name=minorName]').val(),
                'majorGrades': $(this).find('input[name=minorGrades]').val()
            }]
        });
    });
    jsonData.academicBackgroundList = academicBackgroundList;

    // 경력
    let careerList = [];
    $('div.career').each(function(idx){
        if(idx != 0){
            if(!Utils.alert(Utils.valid($(this).find("input[name=companyName]"), null, "경력사항")).result) flag = false;
            careerList.push({
                'companyName': $(this).find('input[name=companyName]').val(),
                'departmentName': $(this).find('input[name=departmentName]').val(),
                'entranceYm': $(this).find('input[name=entranceYm]').val(),
                'resignationYm': $(this).find('input[name=resignationYm]').val(),
                'holdOffice': $(this).find('input[name=holdOffice]').val(),
                'positionInput': $(this).find('input[name=positionInput]').val(),
                'dutyInput': $(this).find('input[name=dutyInput]').val(),
                'salary': $(this).find('input[name=salary]').val(),
                'task': $(this).find('input[name=inputTask]').val(),
                'certFlag': $(this).find('input[name=certFlag]').val()
            });
        }
    });
    jsonData.careerList = careerList;

    // 포트폴리오
    let portfolioList = [];
    $(".portfolio-list").each(function(){
        portfolioList.push({
            'attachType': $(this).find('select[name=attachType]').val(),
            'url': $(this).find('input[name=url]').val()
        });
        Array.from($(this).find("input[type=file]")[0].files).map(e => formData.append('portFolioFiles', e));
    });
    jsonData.portfolioList = portfolioList;

    // 영문이력서
    Array.from($("#enResumeFile")[0].files).map(e => formData.append('enResumeFile', e));

    console.log(jsonData);
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
    // 전송
    $.ajax({
        url: '/bo/user/resume/resume-write',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (data) {
            if(data.data) {
                alert(data.data)
            } else if(data.status == 200){
                alert('이력서 등록이 완료되었습니다.')
                location.href='/bo/management/operation/resume/request-regist-resume';
            } else {
                alert("ajax fail")
            }
        }
    });
})








let Utils = {
    // 이메일 형식이 맞는지 확인한다.
    emailCheck: function(email) {
        if((/[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i).test(email)) {
            return true;
        } else {
            return false;
        }
    },
    // 비밀번호 규칙이 맞는지 확인한다.
    passwordCheck: function(password) {
        password = password.trim()
        let num = password.search(/[0-9]/g);                         // 숫자 유효성
        let eng = password.search(/[a-z]/gi);                        // 영문자 유효성
        let spe = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);   // 특수 문자 유효성

        if(password == 0) {
            return false;
        }

        if(password.length > 30 || password.length < 8 || password.search(/\s/) != -1 || (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ) {
            return false;
        }
        return true;
    },
    // 기존 비밀번호와 변경된 비밀번호가 맞는지 확인한다.
    passwordMatch: function(password, confirmPassword) {
        if(password.trim() != "" && (password == confirmPassword)){
            return true;
        } else {
            return false;
        }
    },
    // 숫자만 받도록 강제로 치환한다.
    onlyNumber: function(selector){
        let replaceStr = selector.val().replaceAll(/[^0-9]/g, "");
        selector.val(replaceStr);
    },
    // 파일의 사이즈 및 이미지여부를 체크한다.
    fileCheck: function(selector, size, imageCheck){
        let ext = selector.val().split('.').pop().toLowerCase();
        let returnObj = {
            msg: "등록 가능한 파일입니다.",
            result: true
        };

        if(imageCheck){
            if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                selector.val("");
                returnObj = {
                    msg: "등록할 수 없는 파일입니다.",
                    result: false
                };
                return returnObj;
            }
        }

        if(size != null){
            let fileSize = selector[0].files[0].size;
            if(fileSize > 10 * 1024 * 1024){
                returnObj = {
                    msg: "첨부파일 사이즈는 " + size +"MB 이내로 등록 가능합니다.",
                    result: false
                };
                return returnObj;
            }
        }

        return returnObj;
    },
    // 날짜 검증 (moment.js 필수)
    dateCheck: function(selector, format){
        let returnObj = {
            msg: "입력 가능한 날짜입니다.",
            result: true
        };

        let date = moment(selector.val(), format, true).format();
        if(date == "Invalid date"){
            selector.val("");
            returnObj = {
                msg: "잘못된 날짜를 기입하셨습니다. 날짜형식을 (" + format + ")에 맞추어 주세요.",
                result: false,
                selector: selector
            };
        }
        return returnObj;
    },
    // 공통 유효성 검사
    valid: function(selector, opt, name){
        let returnObj = {
            msg: "성공",
            result: true
        };
        let val = $(selector).val();
        if(typeof(val) == "string"){
            if(val.trim() == ""){
                returnObj = { msg: name + "은(는) 필수값 입니다.", result: false};
            }
        } else if(typeof(val) == "undefined"){
            returnObj = { msg: name + "은(는) 필수값 입니다.", result: false};
        } else if(isNaN(data)){
            returnObj = { msg: name + "은(는) 필수값 입니다.", result: false};
        }
        return returnObj;
    },
    // 공통 Alert 메시지
    alert: function(returnObj){
        if(!returnObj.result){
            alert(returnObj.msg);
            // alert 무한루프 방지
            $(document.activeElement).blur();
        }
        return returnObj;
    }
}