// 파라미터 숨기기
history.replaceState({}, null, location.pathname);

const signupAgree = new bootstrap.Modal($("#modal-signup-agree")[0]);
signupAgree.show();

// 이름, 전화번호 클릭시 본인인증
$("#userName, #userPhone").click(function(){
    $("button[data-role=btnMemberCert]").trigger("click");
});

// 약관 동의시 이벤트 전파 방지
$("#nes1, #nes2, #nes3, .form-check-label").click(function(e){
    e.stopPropagation();
});

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

// 본인인증 결과 -> 이름/휴대폰번호 비교
$("input[name=di]").click(function(){
    $.ajax({
        url: "/fo/auth/cert",
        success: function(d){
            let certNum = d.data;
            $("input[name=tr_cert]").val(certNum);
            window.open("", "certForm", 'width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250');
            $("form[name=certForm]").submit();

            // PASS 입력 값 <-> db값 비교
            let passName = $("#name").val();
            let passPhone = $("#phone").val();
            let preName = $("input[name=preName]").val();
            let prePhone = $("input[name=prePhone]").val();

            if(passName == preName && passPhone == prePhone) {
                alert("인증되었습니다.");
                $("input[name=name]").val(preName);
                $("input[name=phone]").val(prePhone);
            } else {
                alert("임시회원으로 등록된 이름,휴대폰번호와\n본인인증값이 일치하지 않습니다. 관리자에게 문의바랍니다.");
                window.location.href = "/";
            }
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

// 회원가입
$("button[data-role=submit]").click(function(){
    let obj = {};
    obj.di = $("input[name=di]").val(); // 본인인증 값
    obj.name = $("input[name=name]").val(); // 성명
    obj.phone = $("input[name=phone]").val();   // 핸드폰
    obj.password = $("input[name=password]").val(); // 비밀번호
    obj.agreeMarketing = ($("input[name=agreeMarketing]:checked").val() == "Y" ? 1 : 0); // 마케팅 정보 수신 동의
    obj.agreeAd = ($("input[name=agreeAd]:checked").val() == "Y" ? 1 : 0); // 광고 정보 수신 동의
    obj.privacyExpire = $("input[name=privacyExpire]").val();   // 개인 정보 보유 기간
    obj.memberId = $("input[name=memberId]").val();

    if(obj.name == "" || obj.phone == ""){
        alert("본인 인증을 완료해주세요.");
        return false;
    }

    if(!Utils.passwordCheck(obj.password)){
        alert("비밀번호는 6~30자. 영문, 숫자, 특수문자 중 2가지 이상을 조합해주세요.");
        return false;
    }

    if(!Utils.passwordMatch(obj.password, $("input[name=passwordChk]").val())){
        alert("비밀번호를 입력 및 확인해주세요.");
        return false;
    }

    if(!$("#nes1").is(":checked") || !$("#nes2").is(":checked") || !$("#nes3").is(":checked")){
        alert("필수 이용약관을 동의해주셔야 합니다.");
        return false;
    }

    $.ajax({
        url: "/fo/auth/signup-temp-update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(obj),
        success: function (d) {
            if(d.status == 200){
                alert("회원가입이 완료되었습니다.");
                location.replace("/fo/login/login");
            } else {
                alert("회원가입중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
            }
        },
        error: function(e, t){
            alert('회원가입중 오류가 발생했습니다. 관리자에게 문의해주세요.');
        }
    });
});

// 본인인증 완료시 인증하기 버튼 변경
$("input[name=di]").click(function(){
    $("button[data-role=btnMemberCert]").text("인증완료");
    $("button[data-role=btnMemberCert]").attr("disabled",true);
    $("#userName, #userPhone").off('click')
});

