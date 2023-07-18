// 최초 로딩시 아이디 저장이면 값 가져옴
let loginId = localStorage.getItem("loginId", $("#loginId").val());
if(loginId != ""){
    $("#loginId").val(loginId);
    $("#idCheck").prop("checked", true);
}

// 자동로그인 체크
$("[data-role=autoLoginCheck]").click(function() {
    if($("#autoLogin").is(":checked")){
        $('[name=autoLogin]').val('Y')
    } else {
        $('[name=autoLogin]').val('N')
    }
})

// 로그인
$("button[data-role=login]").click(function() {
    if($("#loginId").val().trim() == ""){
        alert("아이디를 입력해주세요.");
        return false;
    }

    if($("#password").val().trim() == ""){
        alert("비밀번호를 입력해주세요.");
        return false;
    }

    if($("#idCheck").is(":checked")){
        localStorage.setItem("loginId", $("#loginId").val());
    } else {
        localStorage.removeItem("loginId");
    }

    //헤드헌터 승인여부 체크
    $.ajax({
        url: '/fo/auth/check-Approved',
        type: 'POST',
        data: $("form[name=login]").serialize(),
        dataType: 'json',
        success: function (d) {
            if(d.status == 200){
                if(d.data == 0){
                    alert("가입대기 승인 중입니다.");
                    window.location.href = "/";
                } else if(d.data == 1) {
                    //로그인
                    $.ajax({
                        url: '/hh/auth/login',
                        type: 'POST',
                        data: $("form[name=login]").serialize(),
                        dataType: 'json',
                        success: function (d) {
                            if(d.status == 200){
                                window.location.href = "/hh/";
                            } else {
                                if(d.serviceCode == "login_fail") {
                                    alert(d.data);
                                } else {
                                    alert(d.data);
                                    // 서치펌 변경 이력유무 확인
                                    $.ajax({
                                        url: '/hh/auth/sf-change-history',
                                        type: 'POST',
                                        data: $("form[name=login]").serialize(),
                                        dataType: 'json',
                                        success: function (d) {
                                            if (d.data) { // 신청이력존재
                                                alert('서치펌 변경신청 관리자 승인 대기중입니다.');
                                            } else { // 신청이력존재X
                                                const sfChange = new bootstrap.Modal($("#modal-change-sf")[0]);
                                                sfChange.show();
                                            }
                                        },
                                        error: function () {
                                            alert('시스템 오류가 발생했습니다. 관리자에게 문의해주세요.');
                                        }
                                    })
                                }
                            }
                        },
                        error: function(){
                            alert(d.data);
                        }
                    });
                } else {
                    alert('아이디 혹은 비밀번호를 확인해주세요');
                }
            } else {
                alert('아이디 혹은 비밀번호를 확인해주세요');
            }
        },
        error: function(){
            alert('아이디 혹은 비밀번호를 확인해주세요');
        }
    });
});

// 로그인 input 제어
$("#password").keyup(function(e) {
    if(e.keyCode == 13) {
        $("button[data-role=login]").trigger("click")
        return false;
    }
});

// 파일 변경 시 - 등록
$("input[name=sfWorkerFile]").change(function(e){
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// int 컬럼 입력란 문자입력 방지
$("input[name=sfRegNumber],input[name=sfPhone]").keyup(e => Utils.onlyNumber($(e.target))); // 입력값 강제로 숫자만 입력받도록 처리

$("button[data-role=request]").click(function(){
    let formData = new FormData();
    let jsonData = {};
    jsonData.sfName = $("input[name=sfName]").val();
    jsonData.sfCeoName = $("input[name=sfCeoName]").val();
    jsonData.sfRegNumber = $("input[name=sfRegNumber]").val();
    jsonData.sfPhone = $("input[name=sfPhone]").val();
    jsonData.loginId = $("input[name=loginId]").val();

    console.log(JSON.stringify(jsonData));

    if(jsonData.sfName == ""){
        alert("회사명을 입력해주세요.");
        $("input[name=sfName]").focus();
        return false;
    }

    if(jsonData.sfCeoName == ""){
        alert("대표자명을 입력해주세요.");
        $("input[name=sfCeoName]").focus();
        return false;
    }

    if(jsonData.sfRegNumber == ""){
        alert("사업자등록번호를 입력해주세요.");
        $("input[name=sfRegNumber]").focus();
        return false;
    }

    if(jsonData.sfPhone == ""){
        alert("회사 전화번호를 입력해주세요.");
        $("input[name=sfPhone]").focus();
        return false;
    }

    if($("input[name=sfWorkerFile]").val() == ""){
        alert("직원 명단을 등록해주세요.");
        return false;
    }

    let fileCheckObj = Utils.fileCheck($("input[name=sfWorkerFile]"), 10, false);
    if(!fileCheckObj.result){
        $("input[name=sfWorkerFile]").val("");
        alert(fileCheckObj.msg);
        return false;
    }

    if($("input[name=sfWorkerFile]")[0].files != ""){
        Array.from($("input[name=sfWorkerFile]")[0].files).map(e => formData.append('sfWorkerFile', e));
    }
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    $.ajax({
        url: "/hh/auth/request-sf-change",
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (d) {
            if (d.status == 200) {
                alert("서치펌 변경 신청되었습니다.");
                window.location.href = "/";
            } else {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
})