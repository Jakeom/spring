var naver_id_login = new naver_id_login("uGdiTzSfBeo0GY_8A_19", $('#returnURL').val()+"/fo/auth/naverLoginCallback");
// 접근 토큰 값 출력
//alert(naver_id_login.oauthParams.access_token);
// 네이버 사용자 프로필 조회
naver_id_login.get_naver_userprofile("naverSignInCallback()");
// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
function naverSignInCallback() {
    //alert(naver_id_login.getProfileData('id'));
    //alert(naver_id_login.getProfileData('email'));
    //alert(naver_id_login.getProfileData('nickname'));
    //alert(naver_id_login.getProfileData('age'));
    var email = naver_id_login.getProfileData('email');
    var simpleAuthVal = naver_id_login.getProfileData('id');
    var simpleAuthCd = "NAVER";
    $.ajax({
        url : '/fo/auth/simpleLoginCheck',
        type : 'POST',
        data : {
             simpleAuthVal : naver_id_login.getProfileData('id'),
            simpleAuthCd : 'NAVER',
             email : naver_id_login.getProfileData('email')
        },
        dataType : 'json',
        success : function ( data) {
            if(data.status==200){
                if(data.data==null){
                    opener.$("input[name=email]").val(email);
                    opener.$("input[name=simpleAuthVal]").val(simpleAuthVal);
                    opener.$("input[name=simpleAuthCd]").val(simpleAuthCd);
                    alert("회원가입을 해주세요")
                    opener.$("#simpleLoginInfo").submit();
                    window.close();
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
                                opener.location.href ="/"+data.data;
                                window.close();
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