// 초기 로딩시
$("input[name=formatPhone]").val(Utils.formatPhoneNumber($("input[name=phone]").val()));

// 본인인증 창 팝업
$("button[data-role=cert]").click(function(){
    $.ajax({
        url: "/hh/auth/cert",
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

// 본인인증 완료시 인증하기 버튼 변경
$("#di").click(function(){
    $("button[name=certText]").text("인증완료");
    $("button[name=certText]").attr("disabled",true);
});

// 탈퇴하기 버튼 클릭시
$("button[data-role=doWithdrawal]").click(function(){
    if(!$("input:checkbox[name=agreeWithdrawal]").is(":checked")) {
        alert("회원탈퇴 동의에 체크해주세요.")
        return false;
    }
    if($("button[name=certText]").text()!="인증완료") {
        alert("휴대폰인증을 해주세요.")
        return false;
    }

    //비밀번호 후 탈퇴
    let obj = {};
    obj.password = $("input[name=password]").val();
    $.ajax({
        url: "/hh/withdrawal",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(obj),
        success: function (d) {
            alert("탈퇴 되었습니다.");
            location.href = "/hh/auth/logout";
        },
        error: function(e, t){
            alert("현재 비밀번호가 일치하지 않습니다.");
        }
    });

});

//한번 더 생각해볼래요
$("[data-role=goBack]").click(function(){
    location.href = "/";
})