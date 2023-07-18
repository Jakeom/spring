// CKEDITOR
CKEDITOR.replace("basic");
CKEDITOR.replace("proposal");

if($("input[name=signatureCd]:checked").val() == "PROPOSAL") { // 시작화면 기본용/제안 체크에 따른 textarea 노출
    $("#proposal").closest("div").css("display","");
    $("#basic").closest("div").css("display","none");
} else {
    $("#basic").closest("div").css("display","");
    $("#proposal").closest("div").css("display","none");
}

$("span[name=phone]").text(Utils.formatPhoneNumber($("span[name=phone]").text()));

$("input[name=signatureCd]").click(function(){ // 기본용/제안 체크에 따른 textarea 노출
    let signatureCd = $(this).val();
    if(signatureCd == "BASIC") {
        $("#basic").closest("div").css("display","");
        $("#proposal").closest("div").css("display","none");
    } else {
        $("#proposal").closest("div").css("display","");
        $("#basic").closest("div").css("display","none");
    }
});

function inputReset() { // 입력값 초기화
    $("input[name=password]").val("");
    $("input[name=newPassword]").val("");
    $("input[name=newPasswordChk]").val("");
}

$("span[name=copyText]").click(function(){ // 클립보드 복사
    let copyText = $(this).text();
    window.navigator.clipboard.writeText(copyText).then(() => {
        alert("복사되었습니다.");
    });
})

$("span[name=copyIcon]").click(function(){ // 클립보드 복사
    let copyText = $(this).data("code");
    window.navigator.clipboard.writeText(copyText).then(() => {
        alert("복사되었습니다.");
   });
})

// 모달클릭
$(document).on('click', '#changePassword',function(e){
    inputReset();
    $("[data-msg-target=newPassword]").text("");
    $("[data-msg-target=newPasswordChk]").text("");
})

// 모달창 취소버튼 클릭 시
$("button[data-role=close]").click(function(){
    inputReset();
    $("[data-msg-target=newPassword]").text("");
    $("[data-msg-target=newPasswordChk]").text("");
})

// 본인인증 창 팝업
$("button[data-role=cert]").click(function(){
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

// 본인인증 완료 -> 회원정보수정
$("input[name=di]").click(function(){
    $.ajax({
        url: "/fo/auth/cert",
        success: function(d){
            let certNum = d.data;
            $("input[name=tr_cert]").val(certNum);
            window.open("", "certForm", 'width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250');
            $("form[name=certForm]").submit();

            let obj = {};
            obj.phone = $("#phone").val();
            obj.name = $("#name").val();
            obj.di = $("#di").val();
            obj.preDi = $("#preDi").val();

            console.log(JSON.stringify(obj));
            $.ajax({
                url: "/hh/mypage/change-phone",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function (d) {
                    alert("변경되었습니다.");
                    $("span[name=phone]").val(Utils.formatPhoneNumber(obj.phone));
                    $("p[name=hhName]").text(obj.name);
                },
                error: function(e, t){
                    alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
                }
            })
        },
        error: function(e, t){
            alert(t);
        }
    });
});

// 비밀번호 유효성 검사
$("input[name=newPassword]").on("keyup paste change",function(){
    let newPassword = $("input[name=newPassword]").val();
    if(Utils.passwordCheck(newPassword)){
        $("[data-msg-target=newPassword]").text("");
    } else {
        $("[data-msg-target=newPassword]").text("비밀번호는 8~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
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

// 비밀번호 변경 모달 변경버튼 클릭 시
$("button[data-role=change]").click(function(){
    let obj = {};
    obj.password = $("input[name=password]").val();
    obj.newPassword = $("input[name=newPassword]").val();

    if(!Utils.passwordCheck(obj.newPassword)){
        alert("비밀번호는 6~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
        return false;
    }

    if(!Utils.passwordMatch(obj.newPassword, $("input[name=newPasswordChk]").val())){
        alert("비밀번호를 입력 및 확인해주세요.");
        return false;
    }

    $.ajax({
        url: "/hh/mypage/change-password",
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
})

$("button[data-role=save]").click(function(){

    let obj = {};
    obj.signatureBasic = CKEDITOR.instances.basic.getData();
    obj.signatureProposal = CKEDITOR.instances.proposal.getData();
    obj.signatureCd = $("input[name=signatureCd]:checked").val();

    console.log(JSON.stringify(obj));

    if(!confirm("저장하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/hh/mypage/update-signature",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (d) {
                if(obj.signatureCd == "BASIC") {
                    alert("선택하신 기본용 서명이 기본값으로 저장되었습니다.");
                    window.location.reload();
                } else {
                    alert("선택하신 제안용 서명이 기본값으로 저장되었습니다.");
                    window.location.reload();
                }
            },
            error: function (e, t) {
                alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
            }
        });
    }
})
