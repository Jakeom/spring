$("button[data-role=cancel]").click(function () {
    window.location.href = "/bo/system/homepage/maindisplay"
});

$("button[data-role=modify]").click(function () {

    window.location.href = "/bo/system/homepage/maindisplay/modify?mainDisplaySeq=" + $("#mainDisplaySeq").val()
});

$("button[data-role=delete]").click(function () {
    let params = {
        "mainDisplaySeq": $("#mainDisplaySeq").val(),
        "fileSeq": $("#mainDisplayFileSeq").val()
    };
    if(!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/system/homepage/maindisplay/delete",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("삭제되었습니다.");
                window.location.href = "/bo/system/homepage/maindisplay";
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
});

