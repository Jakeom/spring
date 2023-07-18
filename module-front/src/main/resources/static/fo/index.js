function nvl(v) {
    return (v == null || v == '' || v == undefined ? '' : v);
}

$(document).ready(function(){
    let autoLoginToken = localStorage.getItem("autoLoginToken")
    let autoLoginLoginId = localStorage.getItem("autoLoginLoginId")
    let autoMemberId = localStorage.getItem("autoLoginMemberId")
    let autoLogindType = localStorage.getItem("autoLogindType")

    if(nvl(autoLoginToken) != ''&&nvl($('[name=hiddenLoginId]').val()) == ''&&nvl($("#errorMessage").val()) == ''){
        $('form[name=loginApForm] input[name=autoLoginToken]').val(autoLoginToken);
        $('form[name=loginHhForm] input[name=autoLoginToken]').val(autoLoginToken);
        $('form[name=loginApForm] input[name=loginId]').val(autoLoginLoginId);
        $('form[name=loginHhForm] input[name=loginId]').val(autoLoginLoginId);
        $('form[name=loginApForm] input[name=id]').val(autoMemberId);
        $('form[name=loginHhForm] input[name=id]').val(autoMemberId);
        if(autoLogindType == 'AP'){
            $('form[name=loginApForm]').submit();
        }else if(autoLogindType == 'HH'){
            $('form[name=loginHhForm]').submit();
        }

    }
    if(nvl($("#errorMessage").val()) != ''){
        localStorage.removeItem("autoLoginToken");
        localStorage.removeItem("autoLogindType");
        localStorage.removeItem("autoLoginMemberId");
        localStorage.removeItem("autoLoginLoginId");
        alert($("#errorMessage").val())

        $.ajax({
            url: '/fo/auth/autoLoginLogout',
            type: 'POST',
            data: {
                id : autoMemberId
            },
            success: function(data) {
                if(data.status == 200){
                    location.href ="/fo/auth/logout";
                }else {
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                    location.href ="/fo/auth/logout";
                }
            },
            error: function(){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
    }

    if(nvl($("#autoLoginToken").val()) != ''){
        localStorage.setItem("autoLoginToken", $("#autoLoginToken").val());
        localStorage.setItem("autoLogindType", $("#autoLogindType").val());
        localStorage.setItem("autoLoginMemberId", $("#autoLoginMemberId").val());
        localStorage.setItem("autoLoginLoginId", $("#hiddenLoginId").val());
    }

})

// 자동로그인 체크
$("[data-role=autoLoginCheck]").click(function() {
    if($(this).is(":checked")){
        $('form[name=loginApForm] input[name=autoLogin]').val('Y')
        $('form[name=loginHhForm] input[name=autoLogin]').val('Y')
    } else {
        $('form[name=loginApForm] input[name=autoLogin]').val('N')
        $('form[name=loginHhForm] input[name=autoLogin]').val('N')
    }
})

$('button[data-role=goPosition]').click(function (){
    location.href='/fo/position/position-list';
})

$('button[data-role=resumeWrite]').click(function (){
    location.href='/fo/user/resume/resume-write';
})

// ap로그인
$("button[data-role=loginAp]").click(function() {
    if($("form[name=loginApForm] input[name=loginId]").val().trim() == ""){
        alert("아이디를 입력해주세요.");
        return false;
    }
    if($("form[name=loginApForm] input[name=password]").val().trim() == ""){
        alert("비밀번호를 입력해주세요.");
        return false;
    }

    $.ajax({
        url: '/fo/auth/checkTemp',
        type: 'POST',
        data: $("form[name=loginApForm]").serialize(),
        dataType: 'json',
        success: function (d) {
            if(d.status == 200){
                if(d.data != null && d.data.isTemp > 0){
                    alert("임시회원상태의 회원입니다.\n휴대폰 본인인증 및 비밀번호 설정 후 서비스를 이용하실 수 있습니다.")
                    $('form[name=tempMember] input[name=memberId]').val(d.data.id)
                    $('form[name=tempMember]').submit();
                } else {
                    //로그인
                    $('form[name=loginApForm]').submit();
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

// hh로그인
$("button[data-role=loginHh]").click(function() {
    if($("form[name=loginHhForm] input[name=loginId]").val().trim() == ""){
        alert("아이디를 입력해주세요.");
        return false;
    }
    if($("form[name=loginHhForm] input[name=password]").val().trim() == ""){
        alert("비밀번호를 입력해주세요.");
        return false;
    }

    //헤드헌터 승인여부 체크
    $.ajax({
        url: '/fo/auth/check-Approved',
        type: 'POST',
        data: $("form[name=loginHhForm]").serialize(),
        dataType: 'json',
        success: function (d) {
            if(d.status == 200){
                if(d.data = 0){
                    alert("가입대기 승인 중입니다.");
                    window.location.href = "/";
                } else if(d.data = 1) {
                    //로그인
                    $('form[name=loginHhForm]').submit();
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

//네이버 로그인
var naver_id_login = new naver_id_login("uGdiTzSfBeo0GY_8A_19", $('#returnURL').val()+"/fo/auth/naverLoginCallback");
var state = naver_id_login.getUniqState();
naver_id_login.setButton("white", 2,40);
naver_id_login.setDomain($("input[name=webDomain]").val());
naver_id_login.setState(state);
naver_id_login.setPopup();
naver_id_login.init_naver_id_login();

$(document).on("click", "#naver_login", function(){
    $('#naver_id_login').children().eq(0).trigger('click');
});

//카카오로그인
Kakao.init('8922a383a4062a0cfb2d6e15ffd41020'); //발급받은 키 중 javascript키를 사용해준다.
console.log(Kakao.isInitialized()); // sdk초기화여부판단
//카카오로그인
function kakaoLogin() {
    Kakao.Auth.login({
        success: function (response) {
            Kakao.API.request({
                url: '/v2/user/me',
                success: function (response) {
                    console.log(response)
                    console.log(response.kakao_account.email)
                    //alert(response.id)
                    //alert(response.kakao_account.email)
                    var email = response.kakao_account.email;
                    var simpleAuthVal = response.id;
                    var simpleAuthCd = "kakao";
                    $.ajax({
                        url : '/fo/auth/simpleLoginCheck',
                        type : 'POST',
                        data : {
                            simpleAuthVal : response.id,
                            simpleAuthCd : 'KAKAO',
                            email : response.kakao_account.email
                        },
                        dataType : 'json',
                        success : function ( data) {
                            if(data.status==200){
                                if(data.data==null){
                                    $("input[name=email]").val(email);
                                    $("input[name=simpleAuthVal]").val(simpleAuthVal);
                                    $("input[name=simpleAuthCd]").val(simpleAuthCd);
                                    alert("회원가입을 해주세요")
                                    $("#simpleLoginInfo").submit();
                                }else{
                                    $.ajax({
                                        url : '/fo/auth/doSimpleLogin',
                                        type : 'POST',
                                        data : {
                                            id : data.data.memberId
                                        },
                                        dataType : 'json',
                                        success : function ( data) {
                                            if(data.status==200){
                                                location.href ="/";
                                            }else{
                                                alert('관리자에게 문의하세요.')
                                            }
                                        }
                                    });
                                }
                            }else{
                                alert('관리자에게 문의하세요.')
                            }
                        }
                    });
                },
                fail: function (error) {
                    console.log(error)
                },
            })
        },
        fail: function (error) {
            console.log(error)
        },
    })
}
//카카오로그아웃
function kakaoLogout() {
    if (Kakao.Auth.getAccessToken()) {
        Kakao.API.request({
            url: '/v1/user/unlink',
            success: function (response) {
                console.log(response)
            },
            fail: function (error) {
                console.log(error)
            },
        })
        Kakao.Auth.setAccessToken(undefined)
    }
}

//구글로그인
window.onload = function () {
    google.accounts.id.initialize({
        client_id: '242938931453-r9fkeenfgm6krl5uuiom88ff9hkgl839.apps.googleusercontent.com',
        callback: handleCredentialResponse
    });
    google.accounts.id.prompt();
};

function handleCredentialResponse(response) {
    // decodeJwtResponse() is a custom function defined by you
    // to decode the credential response.
    const responsePayload = parseJwt(response.credential);

    console.log("ID: " + responsePayload.sub);
    console.log('Full Name: ' + responsePayload.name);
    console.log('Given Name: ' + responsePayload.given_name);
    console.log('Family Name: ' + responsePayload.family_name);
    console.log("Image URL: " + responsePayload.picture);
    console.log("Email: " + responsePayload.email);

    var email = responsePayload.email;
    var simpleAuthVal = responsePayload.sub;
    var simpleAuthCd = "google";
    $.ajax({
        url : '/fo/auth/simpleLoginCheck',
        type : 'POST',
        data : {
            simpleAuthVal : responsePayload.sub,
            simpleAuthCd : 'GOOGLE',
            email : responsePayload.email
        },
        dataType : 'json',
        success : function ( data) {
            if(data.status==200){
                if(data.data==null){
                    $("input[name=email]").val(email);
                    $("input[name=simpleAuthVal]").val(simpleAuthVal);
                    $("input[name=simpleAuthCd]").val(simpleAuthCd);
                    alert("회원가입을 해주세요")
                    $("#simpleLoginInfo").submit();
                }else{
                    $.ajax({
                        url : '/fo/auth/doSimpleLogin',
                        type : 'POST',
                        data : {
                            id : data.data.memberId
                        },
                        dataType : 'json',
                        success : function ( data) {
                            if(data.status==200){
                                location.href ="/";
                            }else{
                                alert('관리자에게 문의하세요.')
                            }
                        }
                    });
                }
            }else{
                alert('관리자에게 문의하세요.')
            }
        }
    });
}

function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
};

//페이스북 (로그인) 기본 설정
window.fbAsyncInit = function () {
    //페이스북 로그인 기능 클라이언트 설정
    FB.init({
        appId: '506837347842526',
        autoLogAppEvents: true,
        xfbml: true,
        version: 'v10.0'
    });

    //페이스북 로그인 여부 확인
    FB.getLoginStatus(function (response) {
        statusChangeCallback(response);
    });
};

//페이스북 (로그인)
const facebookLogin = ()=>{
    //로그인 정보 GET
    FB.login((res)=>{
        //사용자 정보 GET
        FB.api(
            `/${res.authResponse.userID}/`,
            'GET',
            {"fields":"id,name,email"},
            (res2) => {
                //res.authResponse.accessToken : 엑세스 토큰
                //res.authResponse.graphDomain : 공급자 (페이스북)
                //res.authResponse.userID : 유저 아이디 구분 (숫자)
                //res2.name : 유저 이름
                //res2.email : 유저 이메일 정보
                console.log(res,res2);
                var email = res2.email;
                var simpleAuthVal = res.authResponse.userID;
                var simpleAuthCd = "facebook";
                $.ajax({
                    url : '/fo/auth/simpleLoginCheck',
                    type : 'POST',
                    data : {
                        simpleAuthVal : res.authResponse.userID,
                        simpleAuthCd : 'FACEBOOK',
                        email : res2.email
                    },
                    dataType : 'json',
                    success : function ( data) {
                        if(data.status==200){
                            if(data.data==null){
                                $("input[name=email]").val(email);
                                $("input[name=simpleAuthVal]").val(simpleAuthVal);
                                $("input[name=simpleAuthCd]").val(simpleAuthCd);
                                alert("회원가입을 해주세요")
                                $("#simpleLoginInfo").submit();
                            }else{
                                $.ajax({
                                    url : '/fo/auth/doSimpleLogin',
                                    type : 'POST',
                                    data : {
                                        id : data.data.memberId
                                    },
                                    dataType : 'json',
                                    success : function (data) {
                                        if(data.status==200){
                                            location.href ="/";
                                        }else{
                                            alert('관리자에게 문의하세요.')
                                        }
                                    }
                                });
                            }
                        }else{
                            alert('관리자에게 문의하세요.')
                        }
                    }
                });
            });
    });

}

//페이스북 (로그아웃)
const facebookLogout = ()=>{
    FB.logout((res)=>{
        document.querySelector('#facebook_id').value= "로그인";
    });
}

//페이스북 로그인 버튼 클릭시
document.querySelector('#facebook_id').addEventListener('click',e=>{
    facebookLogin();
});