// 비밀번호 유효성 검사
$("input[name=newPassword]").on("keyup paste change",function(){
    let newPassword = $("input[name=newPassword]").val();
    if(Utils.passwordCheck(newPassword)){
        $("[data-msg-target=newPassword]").text("");
    } else {
        $("[data-msg-target=newPassword]").text("비밀번호는 6~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
    }

    if(Utils.passwordMatch(newPassword, $("input[name=newPasswordChk]").val())){
        $("[data-msg-target=newPasswordChk]").text("");
    } else {
        $("[data-msg-target=newPasswordChk]").text("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
});

// 비밀번호 확인 검사
$("input[name=newPasswordChk]").on("keyup paste change",function(){
    let newPassword = $("input[name=newPassword]").val();
    if(Utils.passwordMatch(newPassword, $("input[name=newPasswordChk]").val())){
        $("[data-msg-target=newPasswordChk]").text("");
    } else {
        $("[data-msg-target=newPasswordChk]").text("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
});

// 변경하기
$("button[data-role=change]").click(function(){
    let obj = {};
    obj.password = $("input[name=password]").val();         // 현재 비밀번호
    obj.newPassword = $("input[name=newPassword]").val();   // 새 비밀번호

    if(!Utils.passwordCheck(obj.newPassword)){
        alert("비밀번호는 6~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
        return false;
    }

    if(!Utils.passwordMatch(obj.newPassword, $("input[name=newPasswordChk]").val())) {
        alert("비밀번호를 입력 및 확인해주세요.");
        return false;
    }

    $.ajax({
        url: "/fo/user/mypage/changePassword",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(obj),
        success: function (d) {
            alert("새 비밀번호로 변경되었습니다. 다시 로그인 해주세요.");
            location.href = "/fo/auth/logout";
        },
        error: function(e, t){
            alert("현재 비밀번호가 일치하지 않습니다.");
        }
    });
});