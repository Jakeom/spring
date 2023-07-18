/* [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function () {
    window.location.href = '/bo/management/operation/info/modify?mainDisplaySeq=' + $("#info_seq").val();
});

/* [삭제] 버튼 클릭시 */
$("button[data-role=delete]").click(function () {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/management/operation/info/delete",
            data: {
                mainDisplaySeq: $("#info_seq").val()
            },
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/management/operation/info";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})

/* [취소] 버튼 클릭시 */
$("button[data-role=list]").click(function () {
    window.location.href = '/bo/management/operation/info'
});