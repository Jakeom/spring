$(document).ready(function () {
    if($("#detailDisplayType").val() == "CUSTOMER") {
        $("#displayType").val("CUSTOMER").prop("selected", true);
    } else {
        $("#displayType").val("SEARCH").prop("selected", true);
    }
    if($("#detailUseFlag").val() == 1) {
        $("#useFlag").prop("checked", true);
    }
})


$("button[data-role=cancel]").click(function () {
    window.location.href = "/bo/system/homepage/maindisplay"
});

$("button[data-role=save]").click(function () {
    let url = $("#url").val();
    let mainDisplaySeq = $("#detailMainDisplaySeq").val();
    let sort = $("#sort").val();
    let displayType = $("select[name=displayType]").val();
    let useFlag
    $("input[name='useFlag']:checked").each(function(){
        useFlag = $(this).val()
    });
    console.log(useFlag)
    let formData = new FormData();
    $("#u_file").each(function(){
        Array.from($(this)[0].files).map(e => formData.append('maindisplayFiles', e));
    });
    let jsonData = {
        "mainDisplaySeq": mainDisplaySeq,
        "url": url,
        "sort": sort,
        "displayType": displayType,
        "useFlag": useFlag,
        "fileSeq": $("#mainDisplayFileSeq").val(),
        "companyName": $("#companyName").val()
    }
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
    let fileDeleteCheck = $("input[name=fileDeleteCheck]").val()
    let fileChangeCheck = $("input[name=fileChangeCheck]").val()
    let fileChange = 0;
    if(fileChangeCheck == 1) {
        if(fileDeleteCheck != 1) {
            fileChange = 1;
        }
    }
    if(fileChange == 1) {
        alert("파일이 삭제되지 않았습니다.")
    } else if(!confirm("수정하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/system/homepage/maindisplay/modify/update",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function () {
                alert("수정되었습니다.");
                window.location.href = "/bo/system/homepage/maindisplay"
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
});

$("[data-role=fileDelete]").on("click", function () {
    if(!confirm("확인을 누르시면 이미지가 수정과 상관없이 삭제됩니다.")) {
    } else {
        $("input[name=fileDeleteCheck]").val("1")
        $('#imgArea').attr('src' , '');
        let fileId = $(this).attr("name")
        $("[name=" + fileId + "]").remove()
        $(this).closest("br").remove()
        $.ajax({
            type: "post",
            url: "/bo/system/homepage/notice/deleteFile",
            data: {
                id: fileId
            },
            success: function(data) {
            },
            error: function() {
                alert("삭제에 실패했습니다.");
            }
        });
    }
})
$(":input[name='u_file']").change(function() {
    if( $(":input[name='u_file']").val() == '' ) {
        $('#imgArea').attr('src' , '');
    }
    $('#imgViewArea').css({ 'display' : '' });
    readURL(this);
    $("input[name=fileChangeCheck]").val("1")
});
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#imgArea').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}