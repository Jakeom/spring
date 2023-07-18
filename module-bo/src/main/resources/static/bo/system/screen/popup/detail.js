/* 내용란 ckeditor 적용 */
CKEDITOR.replace('detailCkeditor');

/* [수정] 버튼 클릭시 */
$("button[data-role=modify]").click(function () {
    window.location.href = '/bo/system/screen/popup/modify?popupSeq=' + $("#popupDetailSeq").val();
});

/* [삭제] 버튼 클릭시 */
$("button[data-role=delete]").click(function () {
    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/screen/popup/delete",
            data: {popupSeq: $("#popupDetailSeq").val()},
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/system/screen/popup";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})

/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function () {
    window.location.href = '/bo/system/screen/popup'
});