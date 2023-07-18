$('[data-role=careerModify]').hide();
$('[data-role=careerDelete]').hide();
$("span[name=phone]").text(Utils.formatPhoneNumber($("span[name=phone]").text())); // 핸드폰번호 정규식


$("#form-file").change(function(e){
    if(!Utils.alert(Utils.fileCheck($(this), 10, false)).result) return false;
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

$("[data-role=uploadResume]").click(function(){
    let id = $('[name=applicantId]').val();
    let formData = new FormData();

    // 기본 정보
    let jsonData = {
        'id': id
    }

    if($("input[name=files]")[0].files != ""){
        Array.from($("input[name=files]")[0].files).map(e => formData.append('files', e));
    }

    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    $.ajax({
        url: "/hh/position/upload-file",
        type: "POST",
        contentType: false,
        processData: false,
        data: formData,
        enctype: 'multipart/form-data',
        success: function (d) {
            if (d.status == 200) {
                alert("추천용 이력서 등록이 완료되었습니다.");
                window.location.reload();
            } else {
                alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

$("#modal-resume-form").on("shown.bs.modal", function(e) {
    $('[name=openReportResumeId]').val($(e.relatedTarget).data("id"));
});

$("[data-role=openReport]").click(function(){
    let id = $('[name=openReportResumeId]').val();

    $.ajax({
        url: "/m/ubi-report/resume",
        type: "GET",
        contentType: "application/json",
        data: {
            id : id
        },
        success: function (d) {
            if (d.status == 200) {
                console.log(JSON.stringify(d.data));
                if($('[name=resumeFormChoice]:checked').val() == 'basic'){
                    window.open("", "reportFormBasic");
                    $("form[name=reportFormBasic] input[name=data]").val(JSON.stringify(d.data));
                    $("form[name=reportFormBasic]").submit();
                } else if($('[name=resumeFormChoice]:checked').val() == 'line'){
                    window.open("", "reportFormLine");
                    $("form[name=reportFormLine] input[name=data]").val(JSON.stringify(d.data));
                    $("form[name=reportFormLine]").submit();
                } else if($('[name=resumeFormChoice]:checked').val() == 'table'){
                    window.open("", "reportFormTable");
                    $("form[name=reportFormTable] input[name=data]").val(JSON.stringify(d.data));
                    $("form[name=reportFormTable]").submit();
                }
            } else {
                alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});