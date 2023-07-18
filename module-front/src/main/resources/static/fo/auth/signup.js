


// 5분 타이머
let timeIntv;

// 파라미터 숨기기
history.replaceState({}, null, location.pathname);

const signupAgree = new bootstrap.Modal($("#modal-signup-agree")[0]);
signupAgree.show();

// 이메일 인증요청
$("button[data-role=btnSendCertCode]").click(function(){
    let auth = $("input[name=loginId]").val().trim();
    if(auth == ""){
        alert("이메일 주소를 입력해주세요.");
        return false;
    }

    if(!Utils.emailCheck(auth)){
        alert("이메일 형식에 맞게 입력해주세요.");
        return false;
    }

    $.ajax({
        url: "/fo/auth/duplicate-email-check",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            loginId: auth
        }),
        success: function(d){
            if(d.data != null){
                if(!d.data.temp){
                    alert("가입된 이메일 입니다.");
                } else {
                    alert("임시회원상태의 회원입니다.\n휴대폰 본인인증 및 비밀번호 설정 후 서비스를 이용하실 수 있습니다.");
                    $("form[name=tempJoinForm] input[name=memberId]").val(d.data.id);
                    $("form[name=tempJoinForm]").submit();
                }
            } else {
                $.ajax({
                    url: "/fo/token/send/SIGN_UP/EMAIL",
                    type: "POST",
                    dataType: "JSON",
                    data: {
                        auth: auth
                    },
                    success: function(d){
                        $(".email-area").show();
                        $("#certCode").val("");

                        // 인증요청 비활성화 후 5초 후 활성화 처리
                        $("button[data-role=btnSendCertCode]").attr("disabled",true);
                        setTimeout(function(){
                            $("button[data-role=btnSendCertCode]").removeAttr("disabled");
                        }, 5000);
                        clearTime();
                        checkTime();

                        alert("인증메일이 발송되었습니다.");
                        $("input[name=certId]").val(d.data.id);
                    },
                    error: function(e, t){
                        alert(t);
                    }
                });
            }
        },
        error: function(e, t){
            alert(t);
        }
    });
});

// 이메일 인증 확인
$("button[data-role=btnCheckCertCode]").click(function(){
    let id = $("input[name=certId]").val();
    let token = $("input[name=certCode]").val().trim();
    if(token == ""){
        alert("인증번호를 입력해주세요.");
        return false;
    }

    $.ajax({
        url: "/fo/token/check",
        type: "GET",
        dataType: "JSON",
        data: {
            id: id,
            token: token
        },
        success: function(d){
            alert(d.data.serviceMessage);
            if(d.data.serviceCode == "Y") {
                $("input[name=emailCertFlag]").val("Y");
                $("button[data-role=btnSendCertCode]").hide();
                $(".email-area").hide();
                $("button[data-role=btnChangeEmail]").show();
                clearInterval(timeIntv);
            }
        },
        error: function(e, t){
            alert(t);
        }
    });
});

// 이메일 변경
$("button[data-role=btnChangeEmail]").click(function(){
    $('#loginId').removeAttr('readonly');
    $('#loginId').val("");
    $("button[data-role=btnSendCertCode]").show();
    $("button[data-role=btnChangeEmail]").hide();
    $("input[name=emailCertFlag]").val("N");
})

// 이름, 전화번호 클릭시 본인인증
$("#name, #phone").click(function(){
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
    obj.dtype = $("input[name=dtype]").val(); // 본인인증 값
    obj.di = $("input[name=di]").val(); // 본인인증 값
    obj.simpleAuthCd = $("input[name=simpleAuthCd]").val(); // 간편인증 코드
    obj.simpleAuthVal = $("input[name=simpleAuthVal]").val();   // 간편인증 값
    obj.loginId = $("input[name=loginId]").val();   // 로그인 이메일
    obj.name = $("input[name=name]").val(); // 성명
    obj.phone = $("input[name=phone]").val();   // 핸드폰
    obj.password = $("input[name=password]").val(); // 비밀번호
    obj.hhReferralCode = $("input[name=hhReferralCode]").val(); // 추천
    obj.agreeMarketing = ($("input[name=agreeMarketing]:checked").val() == "Y" ? 1 : 0); // 마케팅 정보 수신 동의
    obj.agreeAd = ($("input[name=agreeAd]:checked").val() == "Y" ? 1 : 0); // 광고 정보 수신 동의
    obj.privacyExpire = $("input[name=privacyExpire]").val();   // 개인 정보 보유 기간

    if($("input[name=emailCertFlag]").val() != "Y"){
        alert("이메일 인증을 완료해주세요.");
        return false;
    }

    if(obj.di == ""){
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

    //black체크 후 가입
    $.ajax({
        url: "/fo/auth/blacklist-check",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(obj),
        success: function (d) {
            if(d.data){
                alert("관리자에게 문의하시기 바랍니다. 오류코드 - (99)");
                location.replace("/");
            } else {
                $.ajax({
                   url: "/fo/auth/signup",
                   type: "POST",
                   contentType: "application/json",
                   data: JSON.stringify(obj),
                   success: function (d) {
                       if(d.serviceCode){
                           alert("회원가입이 완료되었습니다.");
                           location.replace("/fo/login/login");
                       } else {
                           alert("회원가입중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
                       }
                   },
                   error: function(e, t){
                       alert('탈퇴한 이메일입니다. 다른 이메일을 사용하세요.');
                   }
                    });
            }
        },
        error: function(e, t){
            alert('관리자에게 문의하시기 바랍니다.');
        }
    });
});

function checkTime() {
    let minute = 60;
    let start = minute * 5;
    $('#loginId').attr('readonly',true);
    $("#timer").text("05:00");
    timeIntv = setInterval(() => {
        start -= 1;
        let m = zeroPad(Math.floor(start / minute))
        let s = zeroPad(Math.floor(start % minute));
        let txt = (m + ":" + s);
        $('#timer').text(txt);
        if(start == 0) {
            clearInterval(timeIntv);
            $("button[data-role=btnSendCertCode]").show();
        }
    }, 1000);
}

function clearTime() {
    clearInterval(timeIntv);
}

function zeroPad(v) {
    return (v < 10 ? "0" + v : v);
}

// 본인인증 완료시 인증하기 버튼 변경
$("#di").click(function(){
    $("button[data-role=btnMemberCert]").text("인증완료");
    $("button[data-role=btnMemberCert]").attr("disabled",true);
    $("#name, #phone").off('click')
});

