CKEDITOR.config.autoParagraph = false;

$("button[data-role=faqList]").click(function () {
    window.location.href = "/bo/system/homepage/faq";
});

$("button[data-role=faqModify]").click(function () {
    window.location.href = '/bo/system/homepage/faq/modify?faqSeq=' + $("#faqDetailSeq").val();
});

$("button[data-role=delete]").click(function () {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/system/homepage/faq/delete",
            data: {faqSeq: $("#faqDetailSeq").val()},
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/system/homepage/faq";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})