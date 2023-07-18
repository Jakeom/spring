// 본인인증
$("button[data-role=btnMemberCert]").click(function(){
    $.ajax({
        url: "/fo/auth/cert",
        success: function(d){
            let certNum = d.data;
            $("input[name=tr_cert]").val(certNum);
            window.open("", "certForm", 'width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250');
            $("form[name=certForm]").submit();
        },
        error: function(e, t){
            alert(t);
        }
    });
});

// 본인인증 결과
$("input[name=di]").click(function(){
    $.ajax({
        url: "/fo/auth/cert",
        success: function(d){
            let certNum = d.data;
            $("input[name=tr_cert]").val(certNum);
            window.open("", "certForm", 'width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250');
            $("form[name=certForm]").submit();

            // 회원 존재여부 판단
            let obj = {};
            obj.phone = $("#phone").val();
            obj.name = $("#name").val();

            $.ajax({
                url: "/fo/auth/duplicate-name-check",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function(data) {
                    console.log("존재여부 판단 값 {}",data.data);
                    if(data.data == true) { // 해당 회원 아이디 존재O
                        $("#findPassword").css("display","none");
                        $("#changePassword").css("display",""); // 비밀번호 재설정화면
                    } else { // 해당 회원 아이디 존재X
                        $("#findPassword").css("display","none");
                        $("#noLoginId").css("display",""); // 아이디존재X 알림화면
                    }
                }
            })
        },
        error: function(e, t){
            alert(t);
        }
    });
});


// 비밀번호 유효성 검사
$("input[name=password]").on("keyup paste change",function(){
    let password = $("input[name=password]").val();
    if(Utils.passwordCheck(password)){
        $("[data-msg-target=password]").text("");
    } else {
        $("[data-msg-target=password]").text("비밀번호는 8~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
    }

    if(Utils.passwordMatch(password, $("input[name=passwordChk]").val())){
        $("[data-msg-target=passwordChk]").text("");
    } else {
        $("[data-msg-target=passwordChk]").text("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
});

// 비밀번호 확인 검사
$("input[name=passwordChk]").on("keyup paste change",function(){
    let password = $("input[name=password]").val();
    if(Utils.passwordMatch(password, $("input[name=passwordChk]").val())){
        $("[data-msg-target=passwordChk]").text("");
    } else {
        $("[data-msg-target=passwordChk]").text("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
});

// [비밀번호 변경] 버튼 클릭 시
$("button[data-role=changePw]").click(function() {
    let obj = {};
    obj.password = $("input[name=password]").val();
    obj.name = $("#name").val();
    obj.phone = $("#phone").val();

    console.log(JSON.stringify(obj));

    if(!Utils.passwordCheck(obj.password)){
        alert("비밀번호는 6~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
        return false;
    }

    if(!Utils.passwordMatch(obj.password, $("input[name=passwordChk]").val())){
        alert("비밀번호를 입력 및 확인해주세요.");
        return false;
    }

    if (!confirm("비밀번호 재설정을 하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/fo/auth/pw-reset",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (data) {
                if (data.serviceCode == "true") {
                    $("#changePassword").css("display", "none"); // 비밀번호 재설정화면
                    $("#changePasswordSuccess").css("display", ""); // 재설정 완료화면
                    $("#successMsg").text(data.data.loginId + "(" + obj.name + ")"); // 재설정 완료 텍스트
                } else {
                    alert("비밀번호 재설정 중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
                }
            },
            error: function (e, t) {
                alert(t);
            }
        });
    }
});

// [로그인으로 이동] 버튼 클릭 시
$("button[data-role=login]").click(function() {
    window.location.href="/fo/auth/login";
});

// [회원가입으로 이동] 버튼 클릭 시
$("button[data-role=signup]").click(function(){
    window.location.href="/fo/auth/signup";
})
// 비밀번호 유효성 검사
$("input[name=password]").on("keyup paste change",function(){
    let password = $("input[name=password]").val();
    if(Utils.passwordCheck(password)){
        $("[data-msg-target=password]").text("");
    } else {
        $("[data-msg-target=password]").text("비밀번호는 6~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
    }

    if(Utils.passwordMatch(password, $("input[name=passwordChk]").val())){
        $("[data-msg-target=passwordChk]").text("");
    } else {
        $("[data-msg-target=passwordChk]").text("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
});

// 비밀번호 확인 검사
$("input[name=passwordChk]").on("keyup paste change",function(){
    let password = $("input[name=password]").val();
    if(Utils.passwordMatch(password, $("input[name=passwordChk]").val())){
        $("[data-msg-target=passwordChk]").text("");
    } else {
        $("[data-msg-target=passwordChk]").text("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
});