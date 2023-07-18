
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

// 블랙리스트 추가
$("[data-role=addBlack]").click(function(){
    if($(this).is(":checked")){
        $.ajax({
            url: '/fo/position/hh-black',
            type: 'POST',
            data: {headhunterId : $(this).data("id")},
            success: function(data) {
                if(data.status == 200){
                    $(this).prop("checked",true)
                    alert("블랙리스트에 추가되었습니다.")
                } else {
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                }
            },
            error: function(){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
    }else{
        let params = {
            id : $(this).data("blackId")
        };
        $.ajax({
            url: "/fo/user/mypage/updateBlacklist",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (d) {
                if(d.status=='200') {
                    alert("해제되었습니다.");
                    location.reload();
                } else {
                    alert("관리자에게 문의하세요.");
                }
            },
            error: function(e, t){
                alert("error");
            }
        });
    }
});