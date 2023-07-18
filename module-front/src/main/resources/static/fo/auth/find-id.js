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

            // 회원 아이디 조회
            let obj = {};
            obj.phone = $("#phone").val();
            obj.name = $("#name").val();

            $.ajax({
                url: "/fo/auth/find-id",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function(data) {
                    if(data.data == null) { // 해당 회원 아이디 존재X
                        $("#findId").css("display","none");
                        $("#findIdFail").css("display","");
                    } else { // 해당 회원 아이디 존재O
                        $("#findId").css("display","none");
                        $("#findIdSuccess").css("display","");
                        $("#resultName").text(obj.name);
                        $("#resultLoginId").text(data.data.loginId);
                    }
                }
            })
        },
        error: function(e, t){
            alert(t);
        }
    });
});

// [로그인으로 이동] 버튼 클릭 시
$("button[data-role=login]").click(function() {
   window.location.href="/fo/auth/login";
});

// [회원가입으로 이동] 버튼 클릭 시
$("button[data-role=signup]").click(function() {
    window.location.href="/fo/auth/signup";
});