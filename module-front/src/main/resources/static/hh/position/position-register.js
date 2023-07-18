// 전역변수 선언부
let companyIntroduce = $("div.company-introduce-area").clone();
let contact = $('div.contact-area').clone();

CKEDITOR.replace('jobDescription', {height: 500});

// 데이트 피커 날짜 제한
let now = new Date();
let maxDate = new Date();
maxDate.setDate(now.getDate() + 30);
$("#datepicker").datepicker({
    dateFormat: 'yy-mm-dd',
    minDate: 0,
    maxDate: maxDate
});

$("select").selectAjax({});

// 기업명 검색시 검색결과 팝업
$("input[name=companyName]").keyup(function(){
    let self = $(".content");
    let val = $(this).val().trim();

    if($("input[name=directInput]").val() == "1"){
        return false;
    }

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
                    self.find(".search-result-list.company ul li:last a[data-role=btnSelectCompany]").data(this);
                });

                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name"><a href="javascript:;" data-role="niceCompanyInput">더 찾아보기</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.company ul").append(li);

                let li2 =  '<li>';
                li2 += '        <div class="result-company">';
                li2 += '            <a href="javascript:;">';
                li2 += '                <p class="company-name"><a href="javascript:;" data-role="directCompanyInput">직접입력</a></p>';
                li2 += '            </a>';
                li2 += '        </div>';
                li2 += '    </li>';
                self.find(".search-result-list.company ul").append(li2);
            }
        },
    });
});

// 나이스 기업 검색
$(document).on("click", "[data-role=niceCompanyInput]", function(){
    let val =  $("input[name=companyNames]").val().trim();
    let self = $(".content");
    $.ajax({
        url: "/fo/common/nice-company/list",
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

                    const companyStatus = {
                        '00':'정상'
                        , '01':'업종변경'
                        , '02':'사업자번호 폐업후 재사용'
                        , '03':'피흡수합병'
                        , 11:'법인전환'
                        , 12:'조직변경'
                        , 13:'상속'
                    };

                    const marketListing = {
                        1: '코스피'
                        , 2: '코스닥'
                        , 3: '코넥스'
                        , 4: '제3시장'
                        , 9: '대상아님'
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

                    self.find(".search-result-list.company ul li:last a[data-role=btnSelectCompany]").data({
                        name: this.korentrnm,
                        licenseNumber: this.business,
                        ceo : this.korreprnm,
                        establishDate : this.obz_date,
                        phone : this.tel,
                        address : this.koraddr,
                        companyScale : companyScale[this.scaledivcd],
                        industry : this.sanup,
                        marketListing : marketListing[this.ltgmktdivcd],
                        companyStatus : companyStatus[this.eprdatastsdivcd],
                    });
                });
            }
        },
    });
});

// 직접 입력
$(document).on("click", "[data-role=directCompanyInput]", function(){
    $(".search-result-list.company ul").empty();
    $("input[name=directInput]").val("1");
    $(".license-area").show();
});

// 포커스 아웃
$("input[name=companyName]").focusout(function(){
    setTimeout(function(){
        $(".search-result-list.company ul").empty();
    }, 500);
});

// 기업선택시
$(document).on("click", "[data-role=btnSelectCompany]", function(){
    $("input[name=companyName]").val($(this).data("name"));
    $("input[name=licenseNumber]").val($(this).data().licenseNumber);
    $("input[name=ceo]").val($(this).data().ceo);
    $("input[name=establishDate]").val($(this).data().establishDate);
    $("input[name=phone]").val($(this).data().phone);
    $("input[name=address]").val($(this).data().address);
    $("input[name=companyScale]").val($(this).data().companyScale);
    $("input[name=companyIndustry]").val($(this).data().industry);
    $("input[name=marketListing]").val($(this).data().marketListing);
    $("input[name=companyStatus]").val($(this).data().companyStatus);
    $(".search-result-list.company ul").empty();
});

// 포지션 등록
$('[data-role=positionRegister]').click(function (){     
    // validation
    if(!Utils.alert(Utils.valid($("input[name=title]"), null, "공고제목")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=companyName]"), null, "회사명")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=industry]"), null, "직무")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=endDate]"), null, "채용마감일")).result) return false;

    if($('input[name=isCowork]').is(":checked")){
        if(!Utils.alert(Utils.valid($("input[name=positionReason]"), null, "채용사유")).result) return false;
        if(!Utils.alert(Utils.valid($("input[name=team]"), null, "팀 구성")).result) return false;
        if(!Utils.alert(Utils.valid($("input[name=salary]"), null, "연봉 수준 및 연봉 조정 여부")).result) return false;
        if(!Utils.alert(Utils.valid($("input[name=targetCompany]"), null, "타깃 회사")).result) return false;
    }

    if($("input[name=directInput]").val() == "1"){
        if(!Utils.alert(Utils.valid($("input[name=licenceFile]"), null, "사업자등록증")).result) return false;
    }

    if($("#positionAgree:checked").length == 0 || $("#positionAgree2:checked").length == 0){
        alert("필수확인사항에 동의해주세요");
        return false;
    }

    let jobDescription = CKEDITOR.instances.jobDescription.getData();
    if(jobDescription == ""){
        alert("공고상세를 입력해주세요.");
        return false;
    }


    let formData = new FormData();

    // 기본 정보
    let jsonData = {
        'title': $("input[name=title]").val(),
        'companyName': $('input[name=companyName]').val(),
        'licenseNumber': $('input[name=licenseNumber]').val(),
        'ceo': $('input[name=ceo]').val(),
        'establishDate': $('input[name=establishDate]').val(),
        'phone': $('input[name=phone]').val(),
        'address': $('input[name=address]').val(),
        'companyIndustry': $('input[name=companyIndustry]').val(),
        'companyScale': $('input[name=companyScale]').val(),
        'industry': $('input[name=industry]').val(),
        'marketListing': $('input[name=marketListing]').val(),
        'companyStatus': $('input[name=companyStatus]').val(),
        'keywords': $('input[name=keywords]').val(),
        'publicCd': $('input[name=publicCd]:checked').val(),
        'jobDescription': jobDescription,
        'endDate': $('input[name=endDate]').val(),
        'positionReason': $('input[name=positionReason]').val(),
        'salary': $('input[name=salary]').val(),
        'team': $('input[name=team]').val(),
        'targetCompany': $('input[name=targetCompany]').val(),
        'etcComment': $('input[name=etcComment]').val(),
        'warrantyTerm': $('input[name=warrantyTerm]').val() == "" ? "0" : $('input[name=warrantyTerm]').val(),
        'isCowork': $('input[name=isCowork]').is(":checked") ? "1" : "0",
        'isPrivate': $('input[name=isPrivate]').is(":checked") ? $("input[name=isPrivate]:checked").val() : "0",
        'directPositionFlag': $('input[name=directPositionFlag]').is("checked") ? $("input[name=directPositionFlag]:checked").val() : "",
        'awardName': $('textarea[name=awardName]').val(),
        'content': $('textarea[name=content]').val(),
        'feeInfo': $('textarea[name=feeInfo]').val(),
        'pmComment': $('textarea[name=pmComment]').val(),
        'etc': $('textarea[name=etc]').val()
    }

    // licenceFile
    Array.from($("#licenceFile")[0].files).map(e => formData.append('licenceFile', e));

    // companyInfoFiles
    $("input[name=companyInfoFiles]").each(function(){
        Array.from($(this).find("input[type=file]")[0].files).map(e => formData.append('companyInfoFiles', e));
    });

    let contactInfoList = [];
    $(".contact-area").each(function(){
        contactInfoList.push({
            email: $(this).find("input[name=email]").val(),
            name: $(this).find("input[name=name]").val(),
            phone: $(this).find("input[name=phone]").val()
        });
    });
    jsonData.contactInfoList = contactInfoList;

    let coworkerList = [];
    $(".cowork .coworker-list > div").each(function(){
        if(typeof $(this).find("input[name=coworkerId]").val() != "undefined"){
            coworkerList.push({
                memberId: $(this).find("input[name=coworkerId]").val(),
            });
        }
    });
    jsonData.coworkerList = coworkerList;

    console.log(jsonData);
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    let params = { // 고객사등록 & 다른 PM 점유여부 확인 parameter
        companyName : $("input[name=companyName]").val(),
        licenseNo : $("input[name=licenseNumber]").val(),
        registerType : "POSITION"
    }

    // 고객사등록 & 다른 PM점유여부 확인
    $.ajax({
        url: "/hh/wefirm/register-customer",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (d) {
            if(d.data == "ALREADY_EXISTS") {
                alert("이미 다른 PM이 점유중이므로 포지션 등록이 불가합니다.");
                return false;
            } else {
                // 전송
                $.ajax({
                    url: "/hh/position/position-register",
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    enctype: 'multipart/form-data',
                    success: function (data) {
                        if(data.data.banMsg == "TITLE_BAN_WORDS") {
                            alert("공고제목에 금칙어가 포함되어 있습니다.");
                            $("input[name=title]").focus();
                            return false;
                        }
                        if(data.data.banMsg == "JD_BAN_WORDS") {
                            alert("채용공고 JD에 금칙어가 포함되어 있습니다.");
                            return false;
                        }
                        if(data.data.banMsg == "INDUSTRY_BAN_WORDS") {
                            alert("직무에 금칙어가 포함되어 있습니다.");
                            $("input[name=industry]").focus();
                            return false;
                        }
                        if(data.status == 200){
                            alert('포지션 등록이 완료되었습니다.');
							
							// 코워크 선택이 되어 있으면 채팅방 만들기
                            if($('input[name=isCowork]').is(":checked")){
                                // TODO :: 채팅 구현 요망
                                // ------------------------------ 그룹 채팅 방 만들기 [start]--------------------------------------
                                /*
                                     채팅방 파라미터 세팅하기
                                     masterUserId, masterName : 헤드헌터 id, 이름
                                     loginId , loginName : 로그인 사람 id, 이름
                                     channelNm : 채팅방 이름
                                     loginType : 로그인 한사람 dType으로 HH, FO 인지 구분
                                     userList : 1:1 채팅이면 1건 , 그룹이면 1건 이상 (구성원 정보)
                                     pairYn : 1:1 채팅이면 Y, 그룹채팅이면 N
                                     chatOpen : 채팅창 띄워서 바로 진입원하면  Y, 채팅방만 만드는 경우 N
                                */

                                // 아래 파라미터 값 가져오게 해야 함
                                let positionId = data.data.id;
                                let headhunterId = $("input[name=registerId]").val();
                                let headhunterName = $("input[name=registerName]").val();
                                let loginId = $("input[name=registerId]").val();
                                let loginName = $("input[name=registerName]").val();
                                let loginType = "HH";
                                let channelNm = $("input[name=title]").val();

                                var userList = [];
                                $(".cowork .coworker-list > div").each(function(){
                                    if(typeof $(this).find("input[name=coworkerId]").val() != "undefined"){
                                        var memberId = $(this).find("input[name=coworkerId]").val();
                                        var memberName = $(this).find(".name").text();
                                        var dType = "HH";
                                        var userObj = { "userId" : memberId , "userNm" : memberName, "dType" : dType }
                                        userList.push(userObj);
                                    }
                                });

                                var isPrivate = $("input[name=isPrivate]:checked").val(); // 0 (전체), 1(선택)
							    var channelObj = {
							         channelNm : channelNm
							         ,loginUserId : loginId
							         ,loginUserNm : loginName
							         ,loginType : loginType
							         ,masterUserId : headhunterId
							         ,masterUserNm : headhunterName
							         ,pairYn : "N"
							         ,positionId : positionId
							         ,userList :  isPrivate == 0 ? [] : userList  
							         ,chatOpen : 'N'
							         ,limitYn : isPrivate == 0 ? 'N' : 'Y'   // 제한여부
							    }
							     
                                chatObj.channel_make(channelObj);
                                // ------------------------------ 그룹 채팅 방 만들기 [end]--------------------------------------
                            }

                            location.href = '/hh/position/position-list';
                        } else {
                            alert('포지션 등록 중 오류가 발생하였습니다. 관리자에게 문의해주세요/');
                        }
                    }
                });
            }
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
        }
    });
});

// 연락처 항목추가
$(document).on("click", "[data-role=contactAdd]", function (){
    $(this).closest(".desc").find(".contact").append(contact.clone());
});

// 연락처 항목 삭제
$("[data-role=contactDelete]").click(function(){
    if($(".contact-area").length != 1){
        $(".contact-area:last").remove();
    }
});

// 기업소개 항목추가
$(document).on("click", "[data-role=companyIntroduceAdd]", function (){
    $(this).closest(".desc").find(".company-introduce").append(companyIntroduce.clone());
    $(".company-introduce-area").each(function(idx){
        $(this).find("#companyInfoFile").attr("id", "companyInfoFile" + idx);
        $(this).find("label").attr("for", "companyInfoFile" + idx);
    });

    $(".select-design").select2({
        minimumResultsForSearch: -1
    });
});

// 기업소개 항목 삭제
$("[data-role=companyIntroduceDelete]").click(function(){
    if($(".company-introduce-area").length != 1){
        $(".company-introduce-area:last").remove();
        $(".company-introduce-area").each(function(idx){
            $(this).find("#companyInfoFile").attr("id", "companyInfoFile" + idx);
            $(this).find("label").attr("for", "companyInfoFile" + idx);
        });
    }
});

// 코워커 선택 변경
$("input[name=isCowork]").change(function(){
    if($(this).is(":checked")){
        $(".cowork").show();
    } else {
        $(".cowork").hide();
    }
});

// 코웤 - 전체공개 / 선택공개
$("input[name=isPrivate]").change(function(){
    if($("input[name=isPrivate]:checked").val() == "1"){
        $(".coworker-wrap").show();
    } else {
        $(".coworker-wrap").hide();
    }
});

// 코워커 등록
$("[data-role=coworkerRegister]").click(function(){
    $(".coworker-search").show();
    $("input[name=searchValue]").val("");
    $(".coworker-search-result > ul").empty();
});

// 코워커 검색
$("input[name=searchValue]").keyup(function(e){
    if(e.keyCode == 13){
        $("[data-role=coworkerSearch]").trigger("click");
    }
});

// 코워커 검색
$("[data-role=coworkerSearch]").click(function(){
    let val = $("input[name=searchValue]").val().trim();

    $.ajax({
        url: "/hh/position/coworker-list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function(d){
            $(".coworker-search-result > ul").empty();
            if(d.data.length == 0){
                let li =  '<li>';
                li += '       <p>검색결과가 없습니다.</p>';
                li += '    </li>';
                $(".coworker-search-result > ul").append(li);
            } else {
                $(d.data).each(function(){
                    let li =  '<li>';
                    li += '<p class="name">' + this.name + '</p>';
                    li += '<p class="email">' + this.email + '</p>';
                    li += '<a href="javascript:void(0)" class="add-more" data-role="coworkerAdd" data-id="' + this.id + '">추가하기</a>';
                    li += '    </li>';
                    $(".coworker-search-result > ul").append(li);
                });
            }
        },
    });
});

// 코워커 등록
$(document).on("click", "[data-role=coworkerAdd]", function(){
    let coworkerId = $(this).data("id");

    let flag = true;
    $(".cowork input[name=coworkerId]").each(function(){
        if($(this).val() == coworkerId){
            flag = false;
        }
    });

    if(flag){
        let div = '<div class="btn btn-sm btn-outline-gray round">';
            div += '<input type="hidden" name="coworkerId" value="' + coworkerId + '" />';
            div += '<span class="name">' + $(this).closest("li").find(".name").text() + '</span>';
            div += '<span class="email">' + $(this).closest("li").find(".email").text() + '</span>';
            div += '<button type="button" class="close" data-role="coworkerRemove"><i class="icon-b-close"></i></button>';
            div += '</div>';
        $(".coworker-list").append(div);
    }
});

// 코워커 삭제
$(document).on("click", "[data-role=coworkerRemove]", function(){
    $(this).closest(".round").remove();
});

// 코워커 검색 폼 닫기
$("[data-role=coworkerSearchClose]").click(function(){
    $(".coworker-search").hide();
});

// 복리후생 및 기업소개 파일 변경시
$(document).on("change", "input[name=companyInFiles]", function(e){
    if(!Utils.alert(Utils.fileCheck($(this), 10, false)).result) return false;
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// 사업자등록증 사본 파일 변경시
$(document).on("change", "#licenceFile", function(e){
    if(!Utils.alert(Utils.fileCheck($(this), 10, false)).result) return false;
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// 기등록 회사정보 불러오기 - 회사정보 선택시 설정
$("select[name=companyInfo]").change(function(){
    let data = $(this).find("option:selected").data();
    $("input[name=companyName]").val($(this).find("option:selected").text());
    $("input[name=licenseNumber]").val(data.licenseNumber);
    $("input[name=ceo]").val(data.ceo);
    $("input[name=establishDate]").val(data.establishDate);
    $(".company-information input[name=phone]").val(data.phone);
    $("input[name=address]").val(data.address);
    $("input[name=companyScale]").val(data.companyScale);
    $("input[name=companyIndustry]").val(data.industry);
    $("input[name=marketListing]").val(data.marketListing);
    $("input[name=companyStatus]").val(data.companyStatus);
});

// 기등록 회사정보 불러오기 - 포지션 선택시 설정
$("select[name=positionInfo]").change(function(){
    let data = $(this).find("option:selected").data();
    $("input[name=title]").val($(this).find("option:selected").text());
    $('input[name=keywords]').val(data.keywords);
    $('textarea[name=jobDescription]').val(data.jobDescription);
    $('input[name=industry]').val(data.industry);
    $('input[name=positionReason]').val(data.positionReason);
    $('input[name=salary]').val(data.salary);
    $('input[name=team]').val(data.team);
    $('input[name=targetCompany]').val(data.targetCompany);
    $('input[name=etcComment]').val(data.etcComment);
    $('input[name=warrantyTerm]').val(data.warrantyTerm);
    $('textarea[name=awardName]').val(data.awardName);
    $('textarea[name=content]').val(data.content);
    $('textarea[name=feeInfo]').val(data.feeInfo);
    $('textarea[name=pmComment]').val(data.pmComment);
    $('textarea[name=etc]').val(data.etc);
});

// 취소 클릭시
$("[data-role=positionCancel]").click(function(){
    window.location.href = "/hh/position/position-list";
});