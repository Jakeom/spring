// datepicker
$(".form-date").datepicker({
    monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    showMonthAfterYear: true,
    showOtherMonths: true,
    dateFormat: "yy-mm-dd",
    gotoCurrent: true,
    changeYear: true,
    changeMonth: true
})

$(document).on('click', '#register-customer',function(e) {
    $("input[name=companyNames]").val("");
    $("input[name=licenseNumber]").val("");
    $("select[name=pm]").val("");
})

// paging
$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// searchType&searchValue 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// orderType 검색정렬
$("select[name=orderType]").change(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 체크박스
$("input[name=checkAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=checkCustomer]").prop("checked", true)
    } else {
        $("input[name=checkCustomer]").prop("checked", false);
    }
});

$("input[name=checkCustomer]").change(function(){
    if ($("input[name=checkCustomer]").length === $("input[name=checkCustomer]:checked").length) {
        $("input[name=checkAll]").prop("checked", true);
    } else {
        $("input[name=checkAll]").prop("checked", false);
    }
});

$("input[name=companyNames]").on('keypress', function(e) { // 기업검색 enterkey
    if(e.keyCode == '13') {
        $("button[data-role=searchCompany]").trigger('click');
    }
})

// 나이스 기업 검색
$("button[data-role=searchCompany]").click(function(){
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
                li += '                <p class="company-name">검색결과가 없습니다.</p>';
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
                    li += '            <a href="javascript:;" data-role="btnSelectCompany" data-buisness="' + this.business +'" data-name="' + this.korentrnm + '" data-industry="' + this.sanup + '">';
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

// 나이스 검색결과 기업 선택 시
$(document).on("click", "[data-role=btnSelectCompany]", function(){
    $("input[name=companyNames]").val($(this).data("name"));
    $("input[name=licenseNumber]").val($(this).data("buisness"));
    $(".search-result-list.company ul").empty();
});

// 포커스 아웃시 창 닫기
$(document).on("focusout", "input[name=companyNames]", function(){
    setTimeout(function(){
        $(".search-result-list.company ul").empty();
    }, 500);
});

// [고객사 등록 모달] 등록버튼 클릭 시
$("button[data-role=register]").click(function(){
    let companyName = $("input[name=companyNames]").val();
    let licenseNo = $("input[name=licenseNumber]").val();
    let pmMemberId = $("select[name=pmMemberId]").val();

    // 필수값체크
    if(companyName == "") {
        alert("회사명은 필수기입 사항입니다.");
        $("input[name=companyNames]").focus();
        return false;
    } else if(licenseNo == "") {
        alert("회사명 검색 후 회사를 선택하세요.");
        $("input[name=companyNames]").focus();
        return false;
    } else if(pmMemberId == "") {
        alert("PM을 선택하세요.");
        return false;
    }

    let params = {
        companyName : companyName,
        licenseNo : licenseNo,
        pmMemberId : pmMemberId
    }
    console.log("params ## " + JSON.stringify(params));

   if(!confirm("고객사를 신규등록 하시겠습니까?")) {
        return false;
    } else {
       $.ajax({
           url: "/hh/wefirm/register-customer",
           type: "post",
           contentType: "application/json",
           data: JSON.stringify(params),
           success: function (d) {
               if(d.data == "ALREADY_EXISTS") {
                   alert("이미 다른 PM이 점유중입니다.");
                   return false;
               } else {
                   alert("등록되었습니다.");
                   window.location.reload();
               }
           },
           error: function () {
               alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
           }
       });
   }
});

// 삭제 버튼 클릭 시
$("button[data-role=delete]").click(function() {
    let customerArr = [];
    $("input[name=checkCustomer]:checked").each(function () {
        let customerVal = $(this).val();
        customerArr.push(customerVal);
    })

    let params = {
        customerArr: customerArr
    }

    if(customerArr.length < 1) {
        alert("선택된 고객사가 없습니다.");
        return false;
    }

    if(!confirm("선택된 고객사를 삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/hh/wefirm/delete-customer",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("삭제되었습니다.");
                window.location.reload();
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
            }
        });
    }
});

// [고객사 수정모달] 값 세팅
$("p[name=modifyCustomer]").click(function() {
    $.ajax({
        url: "/hh/wefirm/customer-info",
        type: "get",
        data: {pmId : $(this).data("seq")},
        success: function (data) {
            $("input[name=modifyPmId]").val(data.data.pmId);
            $("input[name=modifyCompanyNames]").val(data.data.companyName);
            $("input[name=modifyLicenseNo]").val(data.data.licenseNo);
            $("select[name=modifyPmMemberId]").val(data.data.pmMemberId).trigger('change');
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
        }
    });
})

// PM 수정버튼 클릭 시
$("button[data-role=modify]").click(function(){
    let pmMemberId = $("select[name=modifyPmMemberId]").val();
    let pmId = $("input[name=modifyPmId]").val();

    let params = {
        pmMemberId : pmMemberId,
        pmId : pmId
    }

    if(pmMemberId == "") {
        alert("PM을 선택해주세요.");
        return false;
    }

    if(!confirm("해당 고객사의 PM을 수정하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/hh/wefirm/modify-customer",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("수정되었습니다.");
                window.location.reload();
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
            }
        });
    }
});

// 합격자등록 클릭 시 pmId 세팅
$("p[name=registerPasser]").click(function(){
    $("input[name=passerPmId]").val($(this).data("seq"));
});

// 파일 변경 시 - 등록
$("input[name=taxFiles]").change(function(e){
    $("input[name=fileChangeFlag]").val("Y");
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// [합격자 등록모달] 등록버튼 클릭 시
$("button[data-role=register-passer]").click(function(){
    let formData = new FormData();
    let jsonData = {
        'passName': $("input[name=passName]").val(),
        'registerAt': $("input[name=registerAt]").val(),
        'pmId': $("input[name=passerPmId]").val()
    }

    if(jsonData.passName == "") {
        alert("합격자명을 입력해주세요.");
        $("input[name=passName]").focus();
        return false;
    }

    if($("input[name=taxFiles]").val() == "") {
        alert("세금계산서를 등록해주세요.");
        return false;
    }

    if(jsonData.registerAt == "") {
        alert("발행일자를 선택해주세요.");
        $("input[name=registerAt]").focus();
        return false;
    }

    if($("input[name=taxFiles]")[0].files != ""){
        Array.from($("input[name=taxFiles]")[0].files).map(e => formData.append('taxFiles', e));
    }
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    if(confirm("합격자를 등록하시겠습니까?")) {
        $.ajax({
            url: "/hh/wefirm/register-passer",
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function (d) {
                if (d.status == 200) {
                    alert("합격자가 등록되었습니다.");
                    window.location.reload();
                } else {
                    alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            }
        });
    }
});

// 파일 변경 시 - 수정
$("input[name=modifyTaxFiles]").change(function(e){
    $("input[name=fileChangeFlag]").val("Y");
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// [합격자수정 모달] 수정버튼 클릭 시
$("button[data-role=modify-passer]").click(function(){
    let formData = new FormData();
    let jsonData = {
        'passName': $("input[name=modifyPassName]").val(),
        'registerAt': $("input[name=modifyRegisterAt]").val(),
        'clientPassId': $("input[name=modifyClientPassId]").val(),
        'fileChangeFlag': $("input[name=fileChangeFlag]").val(), // 파일 변경여부
        'apprFlag' : 'N' // 기존 처리중 flag = 'N' / 재등록 시에도 'N'으로 업데이트 위해 apprFlag = 'N'을 같이 보낸다.
    }

    if(jsonData.passName == "") {
        alert("합격자명을 입력해주세요.");
        $("input[name=passName]").focus();
        return false;
    }

    if(jsonData.registerAt == "") {
        alert("발행일자를 선택해주세요.");
        $("input[name=modifyRegisterAt]").focus();
        return false;
    }

    if($("input[name=modifyTaxFiles]")[0].files != ""){
        Array.from($("input[name=modifyTaxFiles]")[0].files).map(e => formData.append('taxFiles', e));
    }

    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    // 수정,재등록 여부에 따른 메세지 노출
    let apprFlag = $("input[name=modifyApprFlag]").val();
    let confirmMessage = "";
    let resultMessage = "";
    if(apprFlag == 'R') {
        confirmMessage = '합격자를 재등록 하시겠습니까?';
        resultMessage = '합격자가 재등록 되었습니다.';
    } else {
        confirmMessage = '합격자를 수정하시겠습니까?';
        resultMessage = '합격자가 수정되었습니다.';
    }

    if(confirm(confirmMessage)) {
        $.ajax({
            url: "/hh/wefirm/modify-passer",
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function (d) {
                if (d.status == 200) {
                    alert(resultMessage);
                    window.location.reload();
                } else {
                    alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            }
        });
    }
});

// [합격자수정 모달] 값 세팅
$("p[name=modifyPasser]").click(function() {
    console.log($(this).data("seq"));
    $("input[name=modifyClientPassId]").val($(this).data("seq"));
    $.ajax({
        url: "/hh/wefirm/passer-info",
        type: "get",
        data: {clientPassId : $(this).data("seq")},
        success: function (data) {
            console.log(JSON.stringify(data.data));
            $("input[name=modifyPassName]").val(data.data.passName);
            $("input[name=modifyRegisterAt]").val(data.data.registerAt);
            $("span[name=currentFileName]").text(data.data.commonFileList[0].originName);
            $("input[name=modifyApprFlag]").val(data.data.apprFlag);
            $("#modifyRegisterAt").datepicker('setDate', data.data.registerAt);

            let apprFlag = data.data.apprFlag;
            console.log(apprFlag);
            if(apprFlag == 'R') { // 반려처리시 수정 -> 재등록 text 변경
                $("#exampleModalLabel4").text("합격자 재등록");
                $("button[data-role=modify-passer]").text("재등록");
                $("button[data-role=modify-passer]").css('display','');
                $("input[name=modifyPassName]").attr('readonly',false);
                $("input[name=modifyTaxFiles]").attr('disabled',false);
                $("input[name=modifyRegisterAt]").attr('disabled',false);
            }
            if(apprFlag == 'Y') {
                $("#exampleModalLabel4").text("합격자(상세)");
                $("button[data-role=modify-passer]").css('display','none');
                $("input[name=modifyPassName]").attr('readonly',true);
                $("input[name=modifyTaxFiles]").attr('disabled',true);
                $("input[name=modifyRegisterAt]").attr('disabled',true);
            }
            if(apprFlag == 'N') {
                $("#exampleModalLabel4").text("합격자 수정");
                $("button[data-role=modify-passer]").text("수정");
                $("button[data-role=modify-passer]").css('display','');
                $("input[name=modifyPassName]").attr('readonly',false);
                $("input[name=modifyTaxFiles]").attr('disabled',false);
                $("input[name=modifyRegisterAt]").attr('disabled',false);
            }
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
        }
    });
})

// [합격자승인 모달] 합격자명 세팅
$("p[name=approvePasser]").click(function(){
    $.ajax({
        url: "/hh/wefirm/passer-info",
        type: "get",
        data: {clientPassId : $(this).data("seq")},
        success: function (data) {
            $("input[name=approvePassName]").val(data.data.passName);
            $(".tax-file").find("a").attr("href",data.data.commonFileList[0].url);
            $(".tax-file").find("a").text(data.data.commonFileList[0].originName);
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
        }
    });
})

// 승인 / 반려 선택에 따른 반려사유 textarea 노출
$("input[name=approveStatus]").change(function(){
    if($("input[name=approveStatus]:checked").val() == "R") {
        $("#rejectMemoArea").css("display","");
    } else {
        $("#rejectMemoArea").css("display","none");
    }
})

// [합격자승인 모달] 값 세팅
$("p[name=approvePasser]").click(function() {
    $("input[name=approvePmId]").val($(this).data("id"));
    $("input[name=clientPassId]").val($(this).data("seq"))
})

// [합격자승인 모달] 저장 버튼 클릭 시
$("button[data-role=save-approve]").click(function(){
    if($("input[name=approveStatus]:checked").length < 1) {
        alert("승인/반려 중 선택해주세요.");
        return false;
    }

    let jsonData = {
        'apprFlag': $("input[name=approveStatus]:checked").val(),
        'rejectMemo': $("textarea[name=rejectMemo]").val(),
        'clientPassId': $("input[name=clientPassId]").val(),
        'pmId': $("input[name=approvePmId]").val()
    }

    console.log(JSON.stringify(jsonData));

    if(confirm("승인여부를 저장하시겠습니까?")) {
        $.ajax({
            url: "/hh/wefirm/approve-passer",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(jsonData),
            success: function (d) {
                if (d.status == 200) {
                    alert("저장되었습니다.");
                    window.location.reload();
                } else {
                    alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            }
        });
    }
})