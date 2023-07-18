// paging
$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 정렬순 변경
$("select[name=orderType]").change(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 위펌 신청
$("[data-role=apply]").click(function() {
    let id = $(this).data("id");
    if (confirm("We펌 신청하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/hh/wefirm/apply",
            data: {
                id: id
            },
            success: function (d) {
                if(d.status == 200){
                    if(d.serviceCode == "SUCCESS"){
                        alert("We펌 신청이 완료되었습니다.");
                    } else if(d.serviceCode == "REGISTER") {
                        alert("We펌 개설 신청이 있어 We펌 신청이 불가합니다.");
                    } else if(d.serviceCode == "WAIT") {
                        alert("We펌 신청 내역이 있어 We펌 신청이 불가합니다.");
                    }
                    window.location.reload();
                } else {
                    alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            },
            error: function () {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        });
    }
});