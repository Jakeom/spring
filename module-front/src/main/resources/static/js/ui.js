"use strict";
$(document).ready(function () {
    // 헤더에 my눌렀을때
    $(".mypage-con").click(function () {
        if ($(".my-info").attr("style").includes("none")) {
            $(".my-info").show();
        } else {
            $(".my-info").hide();
        }
    }); // 메인 로그인 버튼 눌렀을 때

    $(".login-group__con > button").click(function () {
        let role = $(this).data("hasrole");
        if(role=="ap-login"){
            $(".ap").show();
            $(".hh").hide();
        }else{
            $(".ap").hide();
            $(".hh").show();
        }

    });
    $(".close").click(function () {
        $(".sign-wrap__login").hide();
    });

    $(document).on("change keydown paste input", ".search-box input", function () {
        $(this).closest(".search-box").next(".search-result-list").show();
        if ($(this).val() == "") {
            $(this).closest(".search-box").next(".search-result-list").hide();
        }
    });
    $(".select-design").select2({
        minimumResultsForSearch: -1
    });

    $(".add-info-box a").on("click", function () {
        let getThis = $(this).children().find("i");
        let getClass = getThis.attr("class");

        if (!$(this).hasClass("active")) {
            $(this).addClass("active");
            $(this)
                .children()
                .find("i")
                .addClass(getClass + "-active");
        }
    });

    $(".accordion-agree .item").click(function () {
        $(this).toggleClass("active").siblings().removeClass("active");
    });

    // datepicker
    $("#datepicker").datepicker({
        changeMonth: true,
        changeYear: true,
        nextText: "다음 달",
        prevText: "이전 달",
        currentText: "오늘 날짜",
        closeText: "닫기",
        dateFormat: "yy-mm-dd",
        showMonthAfterYear: true,
        dayNamesMin: ["월", "화", "수", "목", "금", "토", "일"],
        monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    });
    $("#datepicker .highlight a").tooltip("disable");

    $(".more-dot").click(function () {
        if($(this).hasClass("active")){
            $(this).removeClass("active");
        } else {
            $(".more-dot").removeClass("active");
            $(this).addClass("active");
        }
    });

    $(".editGroup").focusout(function () {
        $(".more-dot").removeClass("active");
    });

    // 치환태그
    $("#replacement-btn").click(function () {
        $(".replacement-tag-notice").addClass("active");
    });
    $(".replacement-tag-notice .close").click(function () {
        $(".replacement-tag-notice").removeClass("active");
    });

    //사이드메뉴클릭
    // 버튼 active
    $('[data-toggle="btn-select"]').on('click', function (e) {
        e.preventDefault();
        $(this).toggleClass("active");
    });
});


$("[data-role=mainSearch]").click(function(){
$('form[name=mainSearchForm]').submit();
})

// 채팅창 모바일
//$(".message-list .list li").click(function () {
//    $(".message-content").addClass("active");
//});
//$(".opponent .back").click(function () {
//    $(".message-content").removeClass("active");
//});

// 관심헤드헌터
$(".open-btn").click(function () {
    $(this).closest(".headhunter-profile-list").toggleClass("active");
});

$(".top-btn").click(function () {
    $("html, body").animate({
        scrollTop: 0
    }, 400);
    return false;
});

// 사용자 채팅창 만들기
$(document).on("click", ".chat-notice, a[data-role=chat]", function(){
    var _loginUserId = $("input[name=loginUser]").val();
    var _loginUserNm = $("input[name=loginName]").val();
    var _loginType = $("input[name=loginType]").val();

    if(_loginUserId == "" || _loginUserNm == ""){
        return false;
    }

    var channelObj = {
        channelNm : ""
        ,loginUserId : _loginUserId
        ,loginUserNm : _loginUserNm
        ,loginType : _loginType
        ,masterUserId : ""
        ,masterUserNm : ""
        ,pairYn : "Y"
        ,positionId : ""
        ,userList : []
        ,chatOpen : 'Y'
    }
    console.log(channelObj);

    chatObj.channel_start(channelObj);
    // window.open(url, '채팅팝업', 'width=1200px,height=800px,scrollbars=yes');
})

//로그아웃 AP
$("[data-role=logOut]").click(function(){
    localStorage.removeItem("autoLoginToken");
    localStorage.removeItem("autoLogindType");
    localStorage.removeItem("autoLoginMemberId");
    localStorage.removeItem("autoLoginLoginId");

    $.ajax({
        url: '/fo/auth/autoLoginLogout',
        type: 'POST',
        data: {
            id : $(this).data('id')
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
})

//로그아웃 HH
$("[data-role=logOutHH]").click(function(){
    localStorage.removeItem("autoLoginToken");
    localStorage.removeItem("autoLogindType");
    localStorage.removeItem("autoLoginMemberId");
    localStorage.removeItem("autoLoginLoginId");

    $.ajax({
        url: '/fo/auth/autoLoginLogout',
        type: 'POST',
        data: {
            id : $(this).data('id')
        },
        success: function(data) {
            if(data.status == 200){
                location.href ="/hh/auth/logout";
            }else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                location.href ="/hh/auth/logout";
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
})