$("select").selectAjax();

/* 사용자 아이디 중복체크 */
$("button[data-role=idCheck]").click(function () {
    let id = $("form[name=insertForm] input[name=adminId]").val().trim();
    console.log(id);

    /* 중복체크 전 아이디 공란일 경우 */
    if (id == "") {
        alert("아이디를 먼저 입력해주세요.");
        $("form[name=insertForm] input[name=adminId]").focus();
        return false;
    }
    $.ajax({
        type: "post",
        url: "/bo/system/org/admin/idCheck",
        data: {adminId: id},
        success: function (result) {
            if (result == 0) { // result=0 사용가능한 아이디일 경우
                alert("사용 가능한 아이디입니다.");
                $("form[name=insertForm] input[name=adminId]").attr("data-check-result", "success")
                $("button[data-role=idCheck]").hide();
            } else { // result != 0 이미 사용중인 아이디일 경우
                alert("이미 사용중인 아이디입니다.");
                $("form[name=insertForm] div[name=idChkMsg]").css("display", "inline-block");
                $("form[name=insertForm] input[name=adminId]").attr("data-check-result", "already").focus();
                return false;
            }

        },
        error: function () {
            alert("*****ajax fail*****");
        }
    });
});

/* 이미 사용중인 아이디일 경우 다른 아이디 재 선택 시 문구삭제 + 중복확인 다시 */
$("form[name=insertForm] input[name=adminId]").change(function () {
    $("form[name=insertForm] div[name=idChkMsg]").css("display", "none");
    $("form[name=insertForm] input[name=adminId]").attr("data-check-result", "no");
    $("button[data-role=idCheck]").show();
});

/* 도메인 select 따른 값 대입 */
$("form[name=insertForm] select[name=selectDomain]").change(function () {
    let domain = $("form[name=insertForm] select[name=selectDomain] option:selected").val();
    if (domain != "self") {
        $("form[name=insertForm] input[name=emailDomain]").val(domain).attr("readonly", true);
    } else {
        $("form[name=insertForm] input[name=emailDomain]").val("").attr("readonly", false);
    }
})

/* 비밀번호 일치/불일치 여부 판단 */
$("form[name=insertForm] input[name=adminPasswordChk]").focusout(function () {

    let pw = $("form[name=insertForm] input[name=adminPassword]").val();
    let pwCheck = $("form[name=insertForm] input[name=adminPasswordChk]").val();

    if (pwCheck == "") {
        $("form[name=insertForm] div[name=pwChkMsgN]").css("display", "none");
        $("form[name=insertForm] div[name=pwChkMsgY]").css("display", "none");
    }

    /* 비밀번호 입력란 공란 */
    if (pw == "") {
        /* 하단 메세지 전부 숨김 */
        $("form[name=insertForm] div[name=pwChkMsgN]").css("display", "none");
        $("form[name=insertForm] div[name=pwChkMsgY]").css("display", "none");
        alert("비밀번호를 먼저 입력해주세요.");
        $("form[name=insertForm] input[name=adminPassword]").focus();
        /* 재확인 비밀번호 초기화 */
        $("form[name=insertForm] input[name=adminPasswordChk]").val("");
        return false;
    }

    if (pw != "" && pwCheck != "") {

        if (pw == pwCheck) {
            $("form[name=insertForm] div[name=pwChkMsgN]").css("display", "none");
            $("form[name=insertForm] div[name=pwChkMsgY]").css("display", "inline-block");
        } else {
            $("form[name=insertForm] div[name=pwChkMsgN]").css("display", "inline-block");
            $("form[name=insertForm] div[name=pwChkMsgY]").css("display", "none");
        }
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

/* int 컬럼 입력란 문자입력 방지 */
$("input[name=approvalSeq]").keyup(function() {
    let replaceStr = $(this).val().replaceAll(/[^0-9]/g, "");
    $("input[name=approvalSeq]").val(replaceStr);
})

/* [등록] 버튼 클릭시 */
$("button[data-role=save]").click(function () {
    /* insert data */
    let orgId = $("form[name=insertForm] select[name=orgId] option:selected").val();
    let adminId = $("form[name=insertForm] input[name=adminId]").val();
    let adminNm = $("form[name=insertForm] input[name=adminNm]").val();
    let adminStatusCd = $("form[name=insertForm] select[name=adminStatusCd] option:selected").val();
    let adminPassword = $("form[name=insertForm] input[name=adminPassword]").val();
    let useStartDt = $("form[name=insertForm] input[name=useStartDt]").val();
    let useEndDt = $("form[name=insertForm] input[name=useEndDt]").val();
    let emailId = $("form[name=insertForm] input[name=emailId]").val();
    let emailDomain = $("form[name=insertForm] input[name=emailDomain]").val();
    let email = emailId + '@' + emailDomain;
    /** insert Ips */
    let Ip = $("input[name=mytext]").val(); // a list of ip Strings
    console.log(Ip);

    let ips = []; //An array of ips

    $("input[name=mytext]").each(function () {
        let ip = $(this).val();
        if (ip !== "") {
            ips.push({
                ip: ip
            });
        }
    });

    /* 비밀번호 재확인 */
    let adminPasswordChk = $("form[name=insertForm] input[name=adminPasswordChk]").val();
    /* 중복검사 완료여부 판단 */
    let checkResult = $("form[name=insertForm] input[name=adminId]").attr("data-check-result");
    /* 이메일 형식 체크 */
    let regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
    /* 비밀번호 한글체크 */
    let hangulcheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    /* 필수입력값 alert */
    if (orgId == "") {
        alert("조직ID를 선택해주세요.");
        $("form[name=insertForm] select[name=orgId]").focus();
        return false;
    }
    if (adminId == "") {
        alert("사용자ID를 입력해주세요.");
        $("form[name=insertForm] input[name=adminId]").focus();
        return false;
    }
    /** check_result : no -> 중복확인 하지 않은 경우 */
    if (checkResult == "no") {
        alert("중복검사를 해주세요");
        return false;
    }
    /** check_result : already -> 중복된 아이디를 사용하여 등록할 경우 */
    if (checkResult == "already") {
        alert("다른 아이디를 사용해주세요.");
        $("form[name=insertForm] input[name=adminId]").focus();
        return false;
    }
    if (adminNm == "") {
        alert("사용자 이름을 입력해주세요.");
        $("form[name=insertForm] input[name=adminNm]").focus();
        return false;
    }
    if (emailId == "") {
        alert("이메일 아이디를 입력해주세요.");
        $("form[name=insertForm] input[name=emailId]").focus();
        return false;
    }
    if (emailDomain == "") {
        alert("도메인을 선택하거나 입력해주세요")
        $("form[name=insertForm] input[name=emailDomain]").focus();
        return false;
    }
    if (!regExp.test(email)) {
        alert("이메일 형식을 확인해주세요.");
        return false;
    }
    if (adminStatusCd == "") {
        alert("공통코드를 입력해주세요.");
        $("form[name=insertForm] input[name=adminStatusCd]").focus();
        return false;
    }
    if (adminPassword == "") {
        alert("비밀번호를 입력해주세요.");
        $("form[name=insertForm] input[name=adminPassword]").focus();
        return false;
    }
    if (adminPassword.search(/\s/) != -1 || hangulcheck.test(adminPassword)) {
        alert("비밀번호에 공백이나 한글은 사용할 수 없습니다.");
        $("form[name=insertForm] input[name=adminPassword]").focus().val("");
        $("form[name=insertForm] input[name=adminPasswordChk]").val("");
        $("form[name=insertForm] div[name=pwChkMsgN]").css("display", "none");
        $("form[name=insertForm] div[name=pwChkMsgY]").css("display", "none");
        return false;
    }
    if (adminPasswordChk == "") {
        alert("비밀번호 재확인을 진행해주세요.");
        $("form[name=insertForm] input[name=adminPasswordChk]").focus();
        return false;
    }
    /* 비밀번호 불일치 */
    if (adminPassword != adminPasswordChk && adminPasswordChk != "") {
        alert("비밀번호 일치여부를 확인해주세요.");
        $("form[name=insertForm] input[name=adminPasswordChk]").focus();
        return false;
    }
    if (useStartDt == "") {
        alert("사용 시작일을 설정해주세요.");
        $("form[name=insertForm] input[name=useStartDt]").focus();
        return false;
    }
    if (useStartDt > useEndDt) {
        alert("날짜가 맞는지 확인 후 다시 설정해주세요.");
        $("form[name=insertForm] input[name=useEndDt]").focus();
        return false;
    }

    /* insert 될 data -> params 그룹화 */
    let params = {
        orgId: orgId,
        adminId: adminId,
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

    if (confirm("등록하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/org/admin/insert",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("등록되었습니다.");
                /* insert 후 리스트 페이지로 이동 */
                window.location.href = "/bo/system/org/admin";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
});

/* [취소] 버튼 클릭 시 */
$("button[data-role=list]").click(function () {
    window.location.href = "/bo/system/org/admin";
});

/* [IP] [Add More Fields] 버튼 클릭 시 */
$("button[data-role=MoreFields]").click(function () {
    $(".input_fields_wrap").append('<div class="input-group mb-3">' +
        '<input placeholder="Enter Ip" type="text" name="mytext" class="form-control col-xl-2">' +
        '<div class="input-group-append">' +
        '<button class="btn btn-outline-danger remove_field" type="button">Remove</button></div></div>');
});

/* [IP] [Remove] 버튼 클릭 시 */
$("form[name=insertForm] div[name=wrapper]").on("click", ".remove_field", function () {
    $(this).parent('div').parent('div').remove();
})