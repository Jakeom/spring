function getList(self, page){
    let url = self.data("url");
    let target = self.data("bsTarget");
    $(target +" form[name=searchForm] input[name=page]").val(page);
    $.ajax({
        type: "get",
        url: url,
        data: $(target +" form[name=searchForm]").serialize(),
        success: function (html) {
            $(target).html(html);
            $(target + " .select-design").select2({
                minimumResultsForSearch: -1
            });
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
        }
    });
}

if($("input[name=mode]").val() != ""){
	$("#" + $("input[name=mode]").val() + "-tab").trigger("click");
	$(document.activeElement).blur();
}

$(".nav-link").click(function(){
	$("input[name=mode]").val($(this).attr("aria-controls"));
});

$("#myTab .nav-link").click(function(){
    getList($(this), 1);
});

$("#myTab .nav-link:eq(0)").trigger("click");
$(document.activeElement).blur();

// 페이징
$(document).on("click", "a[data-role=btnGoPage]", function () {
    let page = $(this).data("page");
    getList($(".nav-tabs li button.active"), page);
});

$(document).on("change", "select[name=rowSize]", function () {
    getList($(".nav-tabs li button.active"), 1);
});

$(document).on("click", "button[data-role=search]", function () {
    getList($(".nav-tabs li button.active"), 1);
});

// 체크박스 전체선택 클릭 시 -- 메일
$(document).on("change", "input[name=mailAllChk]", function () {
    if ($(this).is(":checked")) {
        $("input[name=mailChk]").prop("checked", true)
    } else {
        $("input[name=mailChk]").prop("checked", false);
    }
});

$(document).on("change", "input[name=mailChk]", function () {
    // 체크박스 length로 전체 체크선택 -- 메일
    if ($("input[name=mailChk]").length === $("input[name=mailChk]:checked").length) {
        $("input[name=mailAllChk]").prop("checked", true);
    } else {
        $("input[name=mailAllChk]").prop("checked", false);
    }
});

// 체크박스 전체선택 클릭 시 -- 메세지
$(document).on("change", "input[name=msgAllChk]", function () {
    if ($(this).is(":checked")) {
        $("input[name=msgChk]").prop("checked", true)
    } else {
        $("input[name=msgChk]").prop("checked", false);
    }
});

$(document).on("change", "input[name=msgChk]", function () {
    // 체크박스 length로 전체 체크선택 -- 메세지
    if ($("input[name=msgChk]").length === $("input[name=msgChk]:checked").length) {
        $("input[name=msgAllChk]").prop("checked", true);
    } else {
        $("input[name=msgAllChk]").prop("checked", false);
    }
});

// 메일 선택삭제
$(document).on("click", "button[data-role=deleteMail]", function () {
   let mailArr = [];
   $("input[name=mailChk]:checked").each(function(){
       let checkVal =$(this).val();
       mailArr.push(checkVal)
   })

    if(mailArr.length < 1) {
        alert("선택된 대상이 존재하지 않습니다.");
        return false;
    }

    let params = {
       mailArr : mailArr
    }

    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/hh/mypage/delete-send-mail",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = '/hh/mypage/mail-history?id=' + '&mode=home';
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
            }
        });
    }

});

// [메일 상세 모달] 값 세팅
$(document).on("click", "p[name=detail]", function () {
    $.ajax({
        url: "/hh/mypage/history/mail-info",
        type: "get",
        data: {id : $(this).data("seq")},
        success: function (d) {
            $("p[name=subject]").text(d.data.subject+" / "+d.data.createdAt);
            $("p[name=name]").text(d.data.name+"("+d.data.target+")");
            $("textarea[name=content]").val(d.data.content.replace(/(<([^>]+)>)/ig,""));
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
        }
    });
})

// 메세지 선택삭제
$(document).on("click", "button[data-role=deleteMsg]", function () {
    let msgArr = [];
    $("input[name=msgChk]:checked").each(function(){
        let checkVal =$(this).val();
        msgArr.push(checkVal)
    })

    if(msgArr.length < 1) {
        alert("선택된 대상이 존재하지 않습니다.");
        return false;
    }

    let params = {
        msgArr : msgArr
    }

    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/hh/mypage/delete-send-message",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("삭제되었습니다.");
                $("#profile-tab").trigger('click');
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
            }
        });
    }
});

// 메일 선택 재발송 버튼 클릭 시
$(document).on("click", "button[data-role=resendMail]", function () {

    if($("input[name=mailChk]:checked").length == 0) {
        alert("선택된 대상이 존재하지 않습니다.");
        return false;
    }

    let idList = [];
    $("input[name=mailChk]:checked").each(function(){
        idList.push({
            "id": $(this).val()
        });
    });

    let params = {
        idList : idList
    }

    if(confirm("메일 재발송 하시겠습니까?")) {
        $.ajax({
            url: "/hh/mypage/resend-mail",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (d) {
                if (d.status == 200) {
                    alert("메일 재발송이 완료되었습니다.");
                    window.location.reload();
                } else {
                    alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
                }
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
            }
        });
    }
})

// 문자 선택 재발송 버튼 클릭 시
$(document).on("click", "button[data-role=resendMsg]", function () {

    if($("input[name=msgChk]:checked").length == 0) {
        alert("선택된 대상이 존재하지 않습니다.");
        return false;
    }

    let idList = [];
    let smsCnt = 0;
    let lmsCnt = 0;
    $("input[name=msgChk]:checked").each(function(){
        idList.push({
            "id": $(this).val()
        });
        if($(this).data('media') == 'SMS') {
            smsCnt += 1;
        } else {
            lmsCnt += 1;
        }
    });

    let decreasePoint = (smsCnt * 20) + (lmsCnt * 60);
    console.log("차감되는 총 포인트 >>> {}",decreasePoint);

    let params = {
        idList : idList ,
        decreasePoint : decreasePoint
    }

    if(confirm("문자 재발송 하시겠습니까?")) {
        $.ajax({
            url: "/hh/mypage/resend-message",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (d) {
                if (d.serviceCode == 'NO_POINT') {
                    alert("보유하신 포인트가 부족합니다.\n" + "홈페이지에서 포인트 충전 후 다시 이용하실 수 있습니다.\n");
                    $("#profile-tab").trigger('click');
                } else {
                    alert("문자 재발송이 완료되었습니다.");
                    $("#profile-tab").trigger('click');
                }
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
            }
        });
    }
})
