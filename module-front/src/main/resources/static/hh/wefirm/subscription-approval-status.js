// 위펌 신청 취소
$("[data-role=cancelJoin]").click(function(){
    let id = $(this).data("id");
    if (confirm("We펌 신청을 취소하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/hh/wefirm/cancel-join",
            data: {
                id: id
            },
            success: function (d) {
                if(d.status == 200){
                    alert("신청 취소가 완료되었습니다.");
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