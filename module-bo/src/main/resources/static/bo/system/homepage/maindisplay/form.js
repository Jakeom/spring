$("button[data-role=cancel]").click(function () {
    window.location.href = "/bo/system/homepage/maindisplay"
});

$("button[data-role=save]").click(function () {
    let url = $("#url").val();
    let sort = $("#sort").val();
    let displayType = $("select[name=displayType]").val();
    let companyName = $("#companyName").val()
    let useFlag
    $("input[name='useFlag']:checked").each(function(){
        useFlag = $(this).val()
    });

    let formData = new FormData();
    $("#u_file").each(function(){
        Array.from($(this)[0].files).map(e => formData.append('maindisplayFiles', e));
    });
    let jsonData = {
        "url": url,
        "sort": sort,
        "displayType": displayType,
        "useFlag": useFlag,
        "companyName": companyName
    }
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
    console.log(formData.get(""))
    if(!confirm("등록하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/system/homepage/maindisplay/form/insert",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function () {
                alert("등록되었습니다.");
                window.location.href = "/bo/system/homepage/maindisplay"
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }



})

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#imgArea').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

$(":input[name='u_file']").change(function() {
    if( $(":input[name='u_file']").val() == '' ) {
        $('#imgArea').attr('src' , '');
    }
    $('#imgViewArea').css({ 'display' : '' });
    readURL(this);
});

