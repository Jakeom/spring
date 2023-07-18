/* [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function () {
    window.location.href = '/bo/system/homepage/notice/modify?noticeSeq=' + $("#noticeDetailSeq").val();
});

/* [삭제] 버튼 클릭시 */
$("button[data-role=delete]").click(function () {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        console.log($("#noticeFileSeq").val())
        $.ajax({
            type: "post",
            url: "/bo/system/homepage/notice/delete",
            data: {
                  noticeSeq: $("#noticeDetailSeq").val()
                , fileSeq: $("#noticeFileSeq").val()
            },
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/system/homepage/notice";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})

/* [취소] 버튼 클릭시 */
$("button[data-role=list]").click(function () {
    window.location.href = '/bo/system/homepage/notice'
});