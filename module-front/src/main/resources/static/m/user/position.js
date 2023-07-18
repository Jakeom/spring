$("#agreeFile, #resumeFile").change(function(e){
    if(!Utils.alert(Utils.fileCheck($(this), 10, false)).result) return false;
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

$("[data-role=uploadResume]").click(function(){
    let id = $('[name=memberId]').val();
    let formData = new FormData();

    if($("#agreeFile")[0].files.length == 0){
        alert("동의서 파일을 반드시 업로드 하셔야 합니다.");
        return false;
    }

    if($("#resumeFile")[0].files.length == 0){
        alert("이력서 파일을 반드시 업로드 하셔야 합니다.");
        return false;
    }

    // 기본 정보
    let jsonData = {
        'memberId': id
    }

    Array.from($("#agreeFile")[0].files).map(e => formData.append('agreeFiles', e));
    Array.from($("#resumeFile")[0].files).map(e => formData.append('resumeFiles', e));

    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    $.ajax({
        url: "/m/user/position",
        type: "POST",
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        data: formData,
        success: function (d) {
            if (d.status == 200) {
                alert("이력서 등록이 완료되었습니다.");
                window.location.reload();
            } else {
                alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        },
        error: function(){
            alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
        }
    });
});