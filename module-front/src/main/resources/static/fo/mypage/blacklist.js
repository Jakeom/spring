
$("[data-role=deleteBlack]").click(function(){
    let useFlag = $(this).val();
    let blacklistSeq = $(this).data("blacklistSeq");

    let params = {
        id : $(this).data('id')
    };

    $.ajax({
        url: "/fo/user/mypage/updateBlacklist",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (d) {
            if(d.status=='200') {
                alert("해제되었습니다.");
                location.href = '/fo/mypage/blacklist';
            } else {
                alert("관리자에게 문의하세요.");
            }
        },
        error: function(e, t){
            alert("error");
        }
    });
})