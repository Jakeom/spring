// 전역변수 선언부
let careerSave = $("div.career-area > div.career").clone();
let addEducation = $('div.university-area').clone();
let addPortfolio = $('div.portfolio-area > div').clone();

// Validation Check
$("input[name=birth], input[name=salarys], input[name=lastSalarys]").keyup(e => Utils.onlyNumber($(e.target))); // 입력값 강제로 숫자만 입력받도록 처리
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

// 고등학교명 검색시 검색결과 팝업
$("input[name=schoolName]").keyup(function(){
    let val = $(this).val().trim();

    if(val == ""){
        return false;
    }

    $.ajax({
        url: "/fo/common/high-school/list",
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
        url: "/fo/common/university/list",
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
    $(".select-design").select2({
        minimumResultsForSearch: -1
    });
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
        url: "/fo/common/company/list",
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
        url: "/fo/common/nice-company/list",
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

    $(".select-design").select2({
        minimumResultsForSearch: -1
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
    $('input[name=resignationYms]').attr("disabled",false);
    let companyNames = $('input[name=companyNames]');
    let entranceYms = $('input[name=entranceYms]');
    let resignationYms = $('input[name=resignationYms]');
    let holdOffices = $('input[name=holdOffices]');
    let departmentNames = $('input[name=departmentNames]');
    let positionInputs = $('input[name=positionInputs]');
    let dutyInputs = $('input[name=dutyInputs]');
    let salarys = $('input[name=salarys]');
    let inputTasks = $('textarea[name=inputTasks]');

    //각각 경력
    let resYm = "";
    let entYm = moment(entranceYms.val(), "YYYYMM");
    if(holdOffices.val() == 1){
        let resignationYmm = moment().format('YYYYMM');
        resYm = moment(resignationYmm, "YYYYMM");
    }else {
        resYm = moment(resignationYms.val(), "YYYYMM");
    }
    let careerMonth = resYm.diff(entYm, 'months');


    // Validation
    if(!Utils.alert(Utils.valid(companyNames, null, "회사명")).result) return false;
    if(!Utils.alert(Utils.valid(departmentNames, null, "부서명")).result) return false;
    if(!Utils.alert(Utils.valid(entranceYms, null, "입사년월")).result) return false;
    if(holdOffices.val() != '1' ){
        if(!Utils.alert(Utils.valid(resignationYms, null, "퇴사년월")).result) return false;
    }
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
        if(holdOffices.val() == 1 ){
            careerSaveBlock.find('.careerPeriod').text(moment(entranceYms.val(),"YYYYMM").format("YYYY.MM")+'~재직중, ' +Math.floor(careerMonth / 12) + "년 " + (careerMonth % 12) + "개월");
        }else {
            careerSaveBlock.find('.careerPeriod').text(moment(entranceYms.val(),"YYYYMM").format("YYYY.MM")+"~"+moment(resignationYms.val(),"YYYYMM").format("YYYY.MM")+", "+Math.floor(careerMonth / 12) + "년 " + (careerMonth % 12) + "개월");
        }
        careerSaveBlock.find('.company').text(companyNames.val());
        careerSaveBlock.find('.position-input').text(positionInputs.val());
        careerSaveBlock.find('.department-name').text(departmentNames.val());
        careerSaveBlock.find('.duty-input').text(dutyInputs.val());
        careerSaveBlock.find('.salary').text(salarys.val()+'만원');
        careerSaveBlock.find('.career-txt').html(inputTasks.val().replaceAll("\n","<br/>"));
        careerSaveBlock.appendTo($(".career-area"));
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
        career.find('input[name=holdOffice]').val(holdOffices.val());
        if(holdOffices.val() == 1 ){
            career.find('.careerPeriod').text(moment(entranceYms.val(),"YYYYMM").format("YYYY.MM")+'~재직중, ' +Math.floor(careerMonth / 12) + "년 " + (careerMonth % 12) + "개월");
        }else {
            career.find('.careerPeriod').text(moment(entranceYms.val(),"YYYYMM").format("YYYY.MM")+"~"+moment(resignationYms.val(),"YYYYMM").format("YYYY.MM")+", "+Math.floor(careerMonth / 12) + "년 " + (careerMonth % 12) + "개월");
        }
        career.find('.company').text(companyNames.val());
        career.find('.position-input').text(positionInputs.val());
        career.find('.department-name').text(departmentNames.val());
        career.find('.duty-input').text(dutyInputs.val());
        career.find('.salary').text(salarys.val()+'만원');
        career.find('.career-txt').html(inputTasks.val().replaceAll("\n","<br/>"));
    }

    // Init
    companyNames.val('');
    entranceYms.val('');
    resignationYms.val('');
    holdOffices.val(0);
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
            if($(this).find("input[name=holdOffice]").val()==1){
                let resignationYmm = moment().format('YYYYMM');
                let resignationYm = moment(resignationYmm, "YYYYMM");
                let entranceYm = moment($(this).find("input[name=entranceYm]").val(), "YYYYMM");
                month += resignationYm.diff(entranceYm, 'months');
            }else {
                let resignationYm = moment($(this).find("input[name=resignationYm]").val(), "YYYYMM");
                let entranceYm = moment($(this).find("input[name=entranceYm]").val(), "YYYYMM");
                month += resignationYm.diff(entranceYm, 'months');
            }

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
        //$('input[name=resignationYms]').val(moment().format("YYYYMM"));
        $('input[name=resignationYms]').attr("disabled",true);
        $('input[name=resignationYms]').val("");
        $('input[name=holdOffices]').val("1");
    } else {
        $('input[name=resignationYms]').attr("disabled",false);
        $('input[name=holdOffices]').val("0");
    }
});

// 내규에 따름 선택시
$("#bylaws").change(function(){
    if($(this).is(":checked")){
        $('input[name=desiredSalary]').attr("disabled",true);
        $('input[name=bylaws]').val("1");
    } else {
        $('input[name=desiredSalary]').attr("disabled",false);
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
    $("input[name=holdOffices]").val(self.closest(".career").find("input[name=holdOffice]").val());
    $("#holdOffice").prop("checked", self.closest(".career").find("input[name=holdOffice]").val() == "1");
});

// 이력서 사진 파일 변경시
$("#img-upload").change(function(e){
    let fileCheckObj = Utils.fileCheck($(this), 2, true);
    if(!fileCheckObj.result){
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

// 이력서 저장
$('a[data-role=applyResume]').click(function (){
    // validation
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
    let selfIntroduction = $("textarea[name=selfIntroduction]");

    if(!Utils.alert(Utils.valid(birth, null, "생년월일")).result) return false;
    if(!Utils.alert(Utils.valid(lastSalary, null, "최종연봉")).result) return false;
    if(!Utils.alert(Utils.valid(address, null, "주소")).result) return false;
    if(!Utils.alert(Utils.valid(main, null, "핵심역량(MAIN)")).result) return false;
    if(!Utils.alert(Utils.valid(spec, null, "핵심역량(상세)")).result) return false;
    if(!Utils.alert(Utils.valid(etc, null, "핵심역량(기타)")).result) return false;
    if(!Utils.alert(Utils.valid(degreeCd, null, "고등학교 - 학교종류")).result) return false;
    if(!Utils.alert(Utils.valid(schoolName, null, "고등학교 - 학교명")).result) return false;
    if(!Utils.alert(Utils.valid(locationCd, null, "고등학교 - 소재지")).result) return false;
    if(!Utils.alert(Utils.valid(graduationYm, null, "고등학교 - 졸업년월")).result) return false;
    if(!Utils.alert(Utils.valid(graduationStatusCd, null, "고등학교 - 졸업상태")).result) return false;
    if(!Utils.alert(Utils.valid(selfIntroduction, null, "자기소개서")).result) return false;

    let flag = true;
    $(".add-education").each(function(){
        if(!Utils.alert(Utils.valid($(this).find("select[name=degreeCd]"), null, "대학교이상 - 학교종류")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=universityName]"), null, "대학교이상 - 학교명")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=locationCd]"), null, "대학교이상 - 소재지")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=entranceYm]"), null, "대학교이상 - 입학년월")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("select[name=entranceStatusCd]"), null, "대학교이상 - 입학상태")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("input[name=graduationYm]"), null, "대학교이상 - 졸업년월")).result) flag = false;
        if(!Utils.alert(Utils.valid($(this).find("select[name=graduationStatusCd]"), null, "대학교이상 - 졸업상태")).result) flag = false;
        //if(!Utils.alert(Utils.valid($(this).find("input[name=majorName]"), null, "대학교이상 - 주전공명")).result) flag = false;
        //if(!Utils.alert(Utils.valid($(this).find("input[name=majorGrades]"), null, "대학교이상 - 학점")).result) flag = false;
    });

    if($("input[name=agreeFlag]").val() != "Y"){
        alert("동의서를 업로드해야만 등록 가능합니다.");
        return false;
    }

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
        'memberId': $('input[name=memberId]').val(),
        'opened': $("input[name=opened]:checked").val()
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

    if(confirm("등록할 경우 해당 인재에게 자동으로 R9의 안내메일이 발송됩니다.\n\n 내 인재로 등록하시겠습니까?")){
        // 전송
        $.ajax({
            url: '/hh/apsearch/registrationDirect',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function (d) {
                if(d.data.RESULT == "SUCCESS"){
                    alert("등록이 완료되었습니다.");
                    window.location.href = "/hh/myap/myap-list";
                } else if(d.data.RESULT == "PHONE_DUPLICATION"){
                    alert("휴대폰이 중복된 회원이 존재합니다.");
                } else if(d.data.RESULT == "EMAIL_DUPLICATION"){
                    alert("이메일이 중복된 회원이 존재합니다.");
                } else if(d.data.RESULT == "DELETED_MEMBER"){
                    alert("탈퇴한 회원이 존재합니다.");
                } else if(d.data.RESULT == "MEMBER_DUPLICATION"){
                    alert("회원이 이미 존재합니다.");
                } else if(d.data.RESULT == "REJECT"){
                    alert("이력서 등록 거절 내역이 있어 등록하실 수 없습니다.")
                } else {
                    alert("등록중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            },
            error: function(){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
    }
});

// 동의서 파일 업로드
$("#agree-file").change(function(e){
    let formData = new FormData();
    // 파일 확장자 체크
    let fileVal = $(this).val();
    let ext = fileVal.split('.').pop().toLowerCase();
    if($.inArray(ext, ['docx','doc','pdf']) == -1) {
        alert('docx,doc,pdf 파일만 등록할 수 있습니다.');
        return false;
    }
    Array.from($(this)[0].files).map(e => formData.append('resumeFile', e));
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
    formData.append("memberId", $("input[name=mId]").val());
    formData.append("resumeType", 1);   // 0:이력서, 1:동의서, 2:동의서+이력서

    $(".loading").show();

    // 전송
    $.ajax({
        url: '/fo/auth/rabbit',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (data) {
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            $(".loading").hide();
        }
    });
});

// 이력서 파일 업로드
$("#resume-file").change(function(e){
    let formData = new FormData();
    // 파일 확장자 체크
    let fileVal = $(this).val();
    let ext = fileVal.split('.').pop().toLowerCase();
    if($.inArray(ext, ['docx','doc','pdf']) == -1) {
        alert('docx,doc,pdf 파일만 등록할 수 있습니다.');
        return false;
    }
    Array.from($(this)[0].files).map(e => formData.append('resumeFile', e));
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
    formData.append("memberId", $("input[name=mId]").val());
    formData.append("resumeType", 0);   // 0:이력서, 1:동의서, 2:동의서+이력서

    $(".loading").show();

    // 전송
    $.ajax({
        url: '/fo/auth/rabbit',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (data) {
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            $(".loading").hide();
        }
    });
});

// [X] 클릭 시 로딩중 닫기
$(".loading-close").click(function() {
    $(".loading").hide();
});

let url = $("input[name=wssDomain]").val();
let sock = new WebSocket(url + '/fo/auth/rabbit/wss');
let stomp = Stomp.over(sock);
stomp.connect({}, function(frame) {
    stomp.subscribe("/topic/all", function(data) {
            let jsonData = JSON.parse(data.body);
            const rpaNotice = new bootstrap.Modal($("#modal-rpa-notice")[0]);

            // 다른 이력서 검증
            if (jsonData.memberSn != 'ADMIN_' + $("input[name=mId]").val()) {
                 return false;
            }

            $(".loading").hide();

            // try ~ catch
            try {
                if (jsonData.consent.useAgree == "true" && jsonData.consent.thirdpartyAgree == "true") {
                    rpaNotice.show();
                    $("input[name=userName]").val(jsonData.consent.name);
                    $("input[name=userName]").prop("readonly", "readonly");
                    $("input[name=agreeFlag]").val("Y");
                    $(".resume").show();
                } else {
                    alert("동의서 확인부탁드립니다.");
                    return false;
                }

                // personalInfo - 인적사항
                let personalInfo = jsonData.personalInfo;
                if (personalInfo.birth.replace(/[^0-9]/g, "").length == 8) {
                    $("input[name=birth]").val(personalInfo.birth.replace(/[^0-9]/g, ""));
                }
                $("input[name=lastSalary]").val(personalInfo.finalsalary.replace(/[^0-9]/g, "").trim());
                $("input[name=phone]").val(personalInfo.phone.replace(/[^0-9]/g, ""));
                $("input[name=email]").val(personalInfo.email);
                $("input[name=address]").val(personalInfo.address);

                // 핵심역량
                let spec = jsonData.coreCapability;
                $("input[name=spec]:eq(0)").val(spec.main);
                $("input[name=spec]:eq(1)").val(spec.detail);
                $("input[name=spec]:eq(2)").val(spec.etc);

                // 고등학교 학력
                let highSchool = jsonData.academicHighSchoolBackground;
                if (highSchool.schoolLocation.toval.includes("해외")) { // 해외체크 시
                    $(".high-school-area input[name=locationCd]").val(highSchool.schoolLocation.toval.replace("☒해외", ""));
                    $(".high-school-area input[name=schoolName]").val(highSchool.schoolName.toval);
                    $(".high-school-area input[name=overseas]").attr('checked', true);
                    $('.high-school-area input[name=inOverseas]').val("1");
                } else {
                    $.ajax({
                        url: "/fo/common/high-school/list/rpa",
                        type: "GET",
                        async: false,
                        data: {
                            searchValue: highSchool.schoolName.toval.trim(),
                            address: highSchool.schoolLocation.toval.replace("☒해외", "").trim()
                        },
                        success: function (d) {
                            if (d.data.length == 1) {
                                $(".high-school-area input[name=schoolName]").val(d.data[0].name);
                                $(".high-school-area input[name=locationCd]").val(d.data[0].region);
                                $(".high-school-area input[name=schoolCd]").val(d.data[0].code);
                            } else {
                                $(".high-school-area input[name=schoolName]").val("");
                                $(".high-school-area input[name=locationCd]").val("");
                            }
                        },
                    });
                }
                if (highSchool.graduaionYear.toval.length == 6) {
                    $("input[name=graduationYm]").val(highSchool.graduaionYear.toval);
                }

                // 학력(대학이상)
                let academic = jsonData.academicBackground;
                $(".university-area").remove();
                for (let i = 0; i < academic.length; i++) {
                    $("[data-role=addEducation]").parent().before(addEducation.clone());
                    $(".select-design").select2({
                        minimumResultsForSearch: -1
                    });

                    // 학위
                    let kindDetail = academic[i].kindDetail;
                    let degreeCd = "";
                    if (kindDetail == "대학교(4년)") {
                        degreeCd = "COLLEGE";
                    }
                    if (kindDetail == "대학(2,3년)") {
                        degreeCd = "JUNIOR_COLLEGE";
                    }
                    if (kindDetail == "석사") {
                        degreeCd = "MASTER";
                    }
                    if (kindDetail == "박사") {
                        degreeCd = "PHD";
                    }
                    $(".university-area:last").find('select[name=degreeCd]').val(degreeCd).trigger("change");


                    // 입학상태
                    let entranceStatus = academic[i].entranceStatus;
                    let entranceStatusCd = "";
                    if (entranceStatus == "입학") {
                        entranceStatusCd = "ENTRANCE";
                    }
                    if (entranceStatus == "편입") {
                        entranceStatusCd = "TRANSFER";
                    }
                    $(".university-area:last").find('select[name=entranceStatusCd]').val(entranceStatusCd).trigger("change");

                    // 졸업상태
                    let graduationStatus = academic[i].graduationStatus;
                    let graduationStatusCd = "";
                    if (graduationStatus == "졸업") {
                        graduationStatusCd = "GRADUATION";
                    }
                    if (graduationStatus == "졸업예정") {
                        graduationStatusCd = "EXPECTANT_GRADUATION";
                    }
                    if (graduationStatus == "재학중") {
                        graduationStatusCd = "DOING"
                    }
                    if (graduationStatus == "휴학중") {
                        graduationStatusCd = "BREAK";
                    }
                    if (graduationStatus == "중퇴") {
                        graduationStatusCd = "SELF_LEAVE";
                    }
                    if (graduationStatus == "수료") {
                        graduationStatusCd = "COMPLETION";
                    }
                    $(".university-area:last").find('select[name=graduationStatusCd]').val(graduationStatusCd).trigger("change");

                    $(".university-area:last").find('input[name=entranceYm]').val(academic[i].entranceYear);
                    $(".university-area:last").find('input[name=graduationYm]').val(academic[i].graduationYear);
                    $(".university-area:last").find('input[name=majorName]').val(academic[i].major);
                    $(".university-area:last").find('input[name=majorGrades]').val(academic[i].majorGrade);
                    $(".university-area:last").find('input[name=minorName]').val(academic[i].minor);
                    $(".university-area:last").find('input[name=minorGrades]').val(academic[i].minorGrade);

                    if (academic[i].schoolLocation.includes('해외')) { // 해외 체크시
                        $(".university-area input[name=locationCd]").val(academic[i].schoolLocation.replace("☒해외", ""));
                        $("input[name=universityName]").val(academic[i].schoolName);
                        $("input[name=univOversea]").attr('checked', true);
                        $(".university-area input[name=inOverseas]").val("1");
                    } else { // 해외체크X -> 학교검색
                        let val = academic[i].schoolName.trim();
                        let address = academic[i].schoolLocation.replace("☒해외", "").trim();
                        if (val == "") {
                            $("input[name=universityName]:last").val("");
                            $(".university-area input[name=locationCd]:last").val("");
                        } else {
                            $.ajax({
                                url: "/fo/common/university/list/rpa",
                                type: "GET",
                                async: false,
                                data: {
                                    searchValue: val,
                                    address: address
                                },
                                success: function (d) {
                                    if (d.data.length == 1) {
                                        $(".university-area input[name=universityName]:last").val(d.data[0].name);
                                        $(".university-area input[name=locationCd]:last").val(d.data[0].region);
                                        $(".university-area input[name=schoolCd]:last").val(d.data[0].code);
                                        $(".university-area input[name=schoolType]:last").val(d.data[0].schoolType);
                                        $(".university-area input[name=estType]:last").val(d.data[0].estType);
                                    } else {
                                        $("input[name=universityName]:last").val("");
                                        $(".university-area input[name=locationCd]:last").val("");
                                    }
                                },
                            });
                        }
                    }
                }

                // 경력사항
                let career = jsonData.career;
                for (let i = 0; i < career.length; i++) {
                    let rpaCompanyName = career[i].companyName; // RPA 회사명
                    let rpaDepartmentName = career[i].department; // RPA 부서명
                    let rpaEntranceYm = career[i].dateEntry.trim().replace(/[^0-9]/g, "");
                    if (rpaEntranceYm.length != 6) {
                        rpaEntranceYm = "";
                    }
                    let rpaHoldOffice = "";
                    let compareResignationYm = career[i].dateResination; // 재직중 여부 확인
                    let rpaResignationYm = compareResignationYm.trim().replace(/[^0-9]/g, ""); // 퇴사일
                    if (compareResignationYm.includes("재직중")) {
                        rpaHoldOffice = 1;
                    } else {
                        if (rpaResignationYm.length != 6) {
                            rpaResignationYm = "";
                        }
                    }
                    let rpaPositionInput = career[i].companyPosition; // 직급
                    let rpaDutyInput = career[i].companyDuty; // 직책
                    let rpaSalary = career[i].subfinalSalary.trim().replace(/[^0-9]/g, ""); // 최종연봉
                    let rpaInputTask = career[i].detailsCompany.replaceAll("\n", "<br/>"); // 담당업무

                    if (rpaCompanyName == "" || rpaDepartmentName == "" || rpaEntranceYm == "" || (rpaHoldOffice != 1 && rpaResignationYm == "")
                        || rpaPositionInput == "" || rpaDutyInput == "" || rpaSalary == "" || rpaInputTask == "") { // 누락 항목 있을 시 경력생성X
                    } else { // 누락항목 없을 시에만 경력생성
                        //각각 경력
                        let resYm = "";
                        let entYm = moment(rpaEntranceYm, "YYYYMM");
                        if (rpaHoldOffice == 1) {
                            let resignationYmm = moment().format('YYYYMM');
                            resYm = moment(resignationYmm, "YYYYMM");
                        } else {
                            resYm = moment(rpaResignationYm, "YYYYMM");
                        }
                        let careerMonth = resYm.diff(entYm, 'months');

                        let careerSaveBlock = careerSave.clone().show();
                        careerSaveBlock.find('input[name=companyName]').val(rpaCompanyName);
                        careerSaveBlock.find('input[name=departmentName]').val(rpaDepartmentName);
                        careerSaveBlock.find('input[name=entranceYm]').val(rpaEntranceYm);
                        careerSaveBlock.find('input[name=resignationYm]').val(rpaResignationYm);
                        careerSaveBlock.find('input[name=positionInput]').val(rpaPositionInput);
                        careerSaveBlock.find('input[name=dutyInput]').val(rpaDutyInput);
                        careerSaveBlock.find('input[name=salary]').val(rpaSalary);
                        careerSaveBlock.find('input[name=inputTask]').val(rpaInputTask);
                        careerSaveBlock.find('input[name=holdOffice]').val(rpaHoldOffice);
                        if (rpaHoldOffice) {
                            careerSaveBlock.find('.careerPeriod').text(moment(rpaEntranceYm, "YYYYMM").format("YYYY.MM") + '~재직중, ' + Math.floor(careerMonth / 12) + "년 " + (careerMonth % 12) + "개월");
                        } else {
                            careerSaveBlock.find('.careerPeriod').text(moment(rpaEntranceYm, "YYYYMM").format("YYYY.MM") + "~" + moment(rpaResignationYm, "YYYYMM").format("YYYY.MM") + ", " + Math.floor(careerMonth / 12) + "년 " + (careerMonth % 12) + "개월");
                        }
                        careerSaveBlock.find('.company').text(rpaCompanyName);
                        careerSaveBlock.find('.position-input').text(rpaPositionInput);
                        careerSaveBlock.find('.department-name').text(rpaDepartmentName);
                        careerSaveBlock.find('.duty-input').text(rpaDutyInput);
                        careerSaveBlock.find('.salary').text(rpaSalary + '만원');
                        careerSaveBlock.find('.career-txt').html(rpaInputTask.replaceAll("\n", "<br/>"));
                        careerSaveBlock.appendTo($(".career-area"));
                        // 경력 계산
                        let month = 0;
                        $(".career").each(function (i) {
                            if (i != 0) {
                                if ($(this).find("input[name=holdOffice]").val() == 1) {
                                    let resignationYmm = moment().format('YYYYMM');
                                    let resignationYm = moment(resignationYmm, "YYYYMM");
                                    let entranceYm = moment($(this).find("input[name=entranceYm]").val(), "YYYYMM");
                                    month += resignationYm.diff(entranceYm, 'months');
                                } else {
                                    let resignationYm = moment($(this).find("input[name=resignationYm]").val(), "YYYYMM");
                                    let entranceYm = moment($(this).find("input[name=entranceYm]").val(), "YYYYMM");
                                    month += resignationYm.diff(entranceYm, 'months');
                                }

                            }
                        });
                        $('input[name=totalCareer]').val(month);
                        $("#totalCareer").text(Math.floor(month / 12) + "년 " + (month % 12) + "개월");
                    }
                }

                // 희망근무조건
                let wish = jsonData.wishWorkplace;

                // 희망근무조건 - 고용형태
                let employmentType = wish.employmentType;
                let desiredHire = "";
                if (employmentType == "정규직") {
                    desiredHire = "FULL_TIME";
                }
                if (employmentType == "계약직") {
                    desiredHire = "PART_TIME";
                }
                if (employmentType == "인턴") {
                    desiredHire = "INTERN";
                }
                if (employmentType == "파견직") {
                    desiredHire = "DISPATCH";
                }
                if (employmentType == "프리랜서") {
                    desiredHire = "FREELANCER";
                }
                if (employmentType == "병역특례") {
                    desiredHire = "MILITARY_SERVICE";
                }
                $("select[name=desiredHire]").val(desiredHire).trigger("change");

                // 희망근무조건 - 입사가능시기
                let possibleDate = wish.possibleDate;
                let joinDateCd = "";
                if (possibleDate == "즉시") {
                    joinDateCd = "IMMEDIATELY";
                } else if (possibleDate == "2주이내") {
                    joinDateCd = "TWO_WEEKS";
                } else if (possibleDate == "1달이내") {
                    joinDateCd = "MONTH";
                } else if (possibleDate == "협의후결정") {
                    joinDateCd = "CONSULTATION";
                }
                $("select[name=joinDateCd]").val(joinDateCd).trigger("change");

                // 희망근무조건 - 희망근무지
                let wishWorkplace = wish.wishWorkplace;
                let desiredLocationCd = "";
                if (wishWorkplace == "전국") {
                    desiredLocationCd = "ALL";
                }
                if (wishWorkplace == "수도권") {
                    desiredLocationCd = "IN";
                }
                if (wishWorkplace == "서울") {
                    desiredLocationCd = "SEOUL";
                }
                $("select[name=desiredLocationCd]").val(desiredLocationCd).trigger("change");

                // 희망근무조건 - 희망직급
                $("input[name=desiredPosition]").val(wish.wishPosition);

                // 희망근무조건 - 희망연봉
                let wishSalary = wish.wishSalary;
                if (wishSalary.includes("내규에따름")) { // 내규에 따름 체크 시 체크 + 희망연봉 빈값
                    $("#bylaws").attr("checked", true);
                    $("input[name=desiredSalary]").val("").attr("disabled", true);
                }
                $("input[name=desiredSalary]").val(wishSalary.replace(/[^0-9]/g, ""));

                // 채용우대자격 - 보훈대상여부
                let veterans = jsonData.preferential.veterans;
                let isVeterans = "";
                if (veterans == "비대상") {
                    isVeterans = 0;
                }
                if (veterans == "대상") {
                    isVeterans = 1;
                }
                $("select[name=isVeterans]").val(isVeterans).trigger("change");

                // 채용우대자격 - 장애여부
                let disability = jsonData.preferential.disability;
                let isDisability = "";
                if (disability == "비대상") {
                    isDisability = 0;
                }
                if (disability == "대상") {
                    isDisability = 1;
                }
                $("select[name=disability]").val(isDisability).trigger("change");

                // 채용우대자격 - 병역
                let military = jsonData.preferential.military;
                let militaryServiceCd = "";
                if (military == "군필") {
                    militaryServiceCd = "INCOMPLETE";
                }
                if (military == "미필") {
                    militaryServiceCd = "NOT";
                }
                if (military == "복무중") {
                    militaryServiceCd = "SERVICING";
                }
                if (military == "해당없음") {
                    militaryServiceCd = "EXCEPTION";
                }
                $("select[name=militaryServiceCd]").val(militaryServiceCd).trigger("change");

                // 어학
                $("textarea[name=languageInput]").val(jsonData.language.language);

                // 자격증
                $("textarea[name=licenseName]").val(jsonData.license.license);

                // 수상
                $("textarea[name=awardName]").val(jsonData.award.award);

                // 대외활동
                $("textarea[name=activityName]").val(jsonData.activity.activity);

                // 자기소개서
                $("textarea[name=selfIntroduction]").val(jsonData.introduce.introduce);
            } catch (e) {
                alert("등록중 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
    });
});