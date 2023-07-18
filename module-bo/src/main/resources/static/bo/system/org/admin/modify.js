$("select").selectAjax();

/* 이메일 도메인 readonly */
let domain = $("form[name=updateAdminForm] input[name=emailDomain]").val();
if (domain === "softpuzzle.co.kr" || domain === "gmail.com" || domain === "naver.com") {
    $("form[name=updateAdminForm] input[name=emailDomain]").attr("readonly", true);
}

/* datepicker */
$(".form-datepicker").datepicker({
    minDate: 0,
    monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    showMonthAfterYear: true,
    showOtherMonths: true,
    dateFormat: "yy-mm-dd",
    gotoCurrent: true,
    beforeShow: function beforeShow(input, inst) {
        $("#ui-datepicker-div").addClass("datepicker-box");
    }
});

/* 눈 아이콘 비밀번호 show/hide */
$(".input-group.mb-3 i").click(function () {
    $("input").toggleClass("active");
    if ($("input").hasClass("active")) {
        $(this).attr("class", "fa fa-eye-slash fa-lg")
            .prev("input").attr("type", "text");
    } else {
        $(this).attr("class", "fa fa-eye fa-lg")
            .prev("input").attr("type", "password");
    }
});

/* ip setting */
let seq = $("#adminModifySeq").val();

let param = {
    adminSeq: seq
};

$.ajax({
    type: "get",
    url: "/bo/system/org/admin/selectIpForAdminSeq",
    data: param,
    success: function (data) {
        if(data.data.length > 0) {
            /* 첫번째 ip */
            $("form[name=updateAdminForm] input[name=mytext]").val(data.data[0].ip);
            /* 두번째 ip ~ */
            for (let i = 1; i < data.data.length; i++) {
                let appendIp = '<div class="input-group mb-3">' +
                    '<input placeholder="Enter Ip" type="text" value="' + data.data[i].ip + '" name="mytext" class="form-control col-xl-2">' +
                    '<div class="input-group-append">' +
                    '<button class="btn btn-outline-danger remove_field" type="button">Remove</button></div></div>'
                $(".input_fields_wrap").append(appendIp);
            }
        }
    },
    error: function () {

    }
});

/* [IP] [Add More Fields] 버튼 클릭 시 */
$("button[data-role=MoreFields]").click(function () {
    $(".input_fields_wrap").append('<div class="input-group mb-3">' +
        '<input placeholder="Enter Ip" type="text" name="mytext" class="form-control col-xl-2">' +
        '<div class="input-group-append">' +
        '<button class="btn btn-outline-danger remove_field" type="button">Remove</button></div></div>');
});

/* [IP] [Remove] 버튼 클릭 시 */
$("form[name=updateAdminForm] div[name=wrapper]").on("click", ".remove_field", function () {
    $(this).parent('div').parent('div').remove();
})

/* 도메인 select 따른 값 대입 */
$("form[name=updateAdminForm] select[name=selectDomain]").change(function () {
    let domain = $("form[name=updateAdminForm] select[name=selectDomain] option:selected").val();
    if (domain != "self") {
        $("form[name=updateAdminForm] input[name=emailDomain]").val(domain).attr("readonly", true);
    } else {
        $("form[name=updateAdminForm] input[name=emailDomain]").val("").attr("readonly", false);
    }
});

/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function () {
    window.location.href = '/bo/system/org/admin/detail?adminSeq=' + $("#adminModifySeq").val();
});

/* 아이디 입력란 클릭 시 */
$("form[name=updateAdminForm] input[name=adminId]").click(function () {
    alert("ID는 수정이 불가능합니다.");
    return false;
});

/* 비밀번호 변경 시 메세지 */
$("form[name=updateAdminForm] input[name=adminPassword]").change(function () {
    let adminPassword = $("form[name=updateAdminForm] input[name=adminPassword]").val();
    if (adminPassword != "") { // 새로운 비밀번호로 변경 시
        $("form[name=updateAdminForm] div[name=currentPw]").css("display", "none");
        $("form[name=updateAdminForm] div[name=changePw]").css("display", "inline-block");
    } else { // 기존 비밀번호 유지 시
        $("form[name=updateAdminForm] div[name=currentPw]").css("display", "inline-block");
        $("form[name=updateAdminForm] div[name=changePw]").css("display", "none");
    }
});

/* int 컬럼 입력란 문자입력 방지 */
$("input[name=approvalSeq]").keyup(function() {
    let replaceStr = $(this).val().replaceAll(/[^0-9]/g, "");
    $("input[name=approvalSeq]").val(replaceStr);
})

/* [수정] 버튼 클릭 시 */
$("button[data-role=update]").click(function () {
    /* update data */
    let adminSeq = $("#adminModifySeq").val();
    let orgId = $("form[name=updateAdminForm] select[name=orgId] option:selected").val();
    let adminNm = $("form[name=updateAdminForm] input[name=adminNm]").val();
    let emailId = $("form[name=updateAdminForm] input[name=emailId]").val();
    let emailDomain = $("form[name=updateAdminForm] input[name=emailDomain]").val();
    let email = emailId + '@' + emailDomain;
    let adminStatusCd = $("form[name=updateAdminForm] select[name=adminStatusCd] option:selected").val();
    let adminPassword = $("form[name=updateAdminForm] input[name=adminPassword]").val();
    let useStartDt = $("form[name=updateAdminForm] input[name=useStartDt]").val();
    let useEndDt = $("form[name=updateAdminForm] input[name=useEndDt]").val();
    /* 변경하지 않을 시 기존 비밀번호 */
    let currentPw = $("form[name=updateAdminForm] input[name=currentPassword]").val();
    /* 이메일 형식 체크 */
    let regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
    /* 기존 비밀번호 유지 시 처리 */
    if (adminPassword == "") { // 비밀번호 변경X -> 기존 비밀번호 유지
        adminPassword = currentPw;
    }

    /** update ips */
    let ips = []; //An array of ips

    $("input[name=mytext]").each(function () {
        let ip = $(this).val();
        if (ip !== "") {
            ips.push({
                ip: ip
            });
        }
    });

    /* 필수입력값 alert */
    if (orgId == "") {
        alert("조직ID를 선택해주세요.");
        $("form[name=updateAdminForm] select[name=orgId]").focus();
        return false;
    }
    if (adminNm == "") {
        alert("사용자 이름을 입력해주세요.");
        $("form[name=updateAdminForm] input[name=adminNm]").focus();
        return false;
    }
    if (emailId == "") {
        alert("이메일 아이디를 입력해주세요.");
        $("form[name=updateAdminForm] input[name=emailId]").focus();
        return false;
    }
    if (emailDomain == "") {
        alert("도메인을 선택하거나 입력해주세요")
        $("form[name=updateAdminForm] input[name=emailDomain]").focus();
        return false;
    }
    if (!regExp.test(email)) {
        alert("이메일 형식을 확인해주세요.");
        return false;
    }
    if (adminStatusCd == "") {
        alert("공통코드를 입력해주세요.");
        $("form[name=updateAdminForm] input[name=adminStatusCd]").focus();
        return false;
    }
    if (useStartDt == "") {
        alert("사용 시작일을 설정해주세요.");
        $("form[name=updateAdminForm] input[name=useStartDt]").focus();
        return false;
    }
    if (useStartDt > useEndDt) {
        alert("날짜가 맞는지 확인 후 다시 설정해주세요.");
        $("form[name=updateAdminForm] input[name=useEndDt]").focus();
        return false;
    }

    /* update 될 data -> params 그룹화 */
    let params = {
        adminSeq: adminSeq,
        orgId: orgId,
        adminNm: adminNm,
        email: email,
        adminStatusCd: adminStatusCd,
        adminPassword: adminPassword,
        useStartDt: useStartDt,
        useEndDt: useEndDt,
        ipList: ips,
        approvalSeq : $("input[name=approvalSeq]").val()
    }
    console.log(params);

    if (confirm("수정하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/org/admin/update",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("수정되었습니다.");
                /* update 후 리스트 페이지로 이동 */
                window.location.href = '/bo/system/org/admin/detail?adminSeq=' + $("#adminModifySeq").val();
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
});