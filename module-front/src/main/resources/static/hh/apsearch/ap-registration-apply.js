$("input").on("keyup",function(key){
    if(key.keyCode==13) {
        $("button[data-role=search]").click();
    }
});

$('a[data-role=btnGoPage]').click(function () {
    var page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
    $(".nav-tabs li a.active").trigger("click");
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
}); 

$(document).on("change", "#file1", function(e){
    let fileCheckObj = Utils.fileCheck($(this), 10, false);
    if(!fileCheckObj.result){
        alert(fileCheckObj.msg);
        return false;
    }
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

$(document).on("change", "#file2", function(e){
    let fileCheckObj = Utils.fileCheck($(this), 10, false);
    if(!fileCheckObj.result){
        alert(fileCheckObj.msg);
        return false;
    }
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

function save(){
    let formData = new FormData();

    // 첨부파일
    Array.from($("#file1")[0].files).map(e => formData.append('file1', e));
    Array.from($("#file2")[0].files).map(e => formData.append('file2', e));

    let jsonData = {
    }

    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    // 전송
    $.ajax({
        url: '/hh/apsearch/registrationApply',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (data) {
            if(data.status == 200){
                window.location.reload();
            }else{
                alert('등록에 실패 하였습니다.')
            }
        }
    });
}