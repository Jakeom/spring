// 로고 파일 변경시
$("input[name=logoFiles]").change(function(e){
    if(!Utils.alert(Utils.fileCheck($(this), 10, true)).result) return false;
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// 파일 추가시
$("[data-role=fileAdd]").click(function(){
    let html = '<div class="file-flex">\n' +
        '                                    <div class="form-upload mt-3">\n' +
        '                                        <input type="file" class="" name="files" id="files"/>\n' +
        '                                        <label for="files"><span class="file-name m-r-10"></span><i class="icon-input-add"></i></label>\n' +
        '                                    </div>\n' +
        '                                    <button type="button" class="btn btn-outline-dark btn-md m-l-10 mt-3" data-role="fileDel">- 삭제</button>\n' +
        '                                </div>';

    $(".file-add-area").append(html);
    $(".file-add-area .file-flex").each(function(idx){
        $(this).find("input").attr("id", "files" + idx);
        $(this).find("label").attr("for", "files" + idx);
    });
});

// 파일 삭제시
$(document).on("click", "[data-role=fileDel]", function(e){
    $(this).closest(".file-flex").remove();
});

// 파일 변경시
$(document).on("change", "input[name=files]", function(e){
    if(!Utils.alert(Utils.fileCheck($(this), 10, false)).result) return false;
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// Wefirm 개설신청
$('[data-role=wefirmRegister]').click(function (){
    // validation
    if(!Utils.alert(Utils.valid($("input[name=name]"), null, "위펌명")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=establishYear]"), null, "설립년도")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=ceoName]"), null, "대표자명")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=email]"), null, "이메일")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=websiteUrl]"), null, "회사 홈페이지")).result) return false;
    if(!Utils.alert(Utils.valid($("input[name=wefirmUrl]"), null, "We펌 주소")).result) return false;
    if(!Utils.alert(Utils.valid($("textarea[name=description]"), null, "We펌 상세설명")).result) return false;

    if(!Utils.alert(Utils.valid($("input[name=logoFiles]"), null, "회사 로고이미지")).result) return false;

    if($("input[name=files]").length == 0){
        alert("인증제출 파일은 필수입니다.");
        return false;
    }

    let flag = true;
    $("input[name=files]").each(function(){
        if($(this)[0].files == ""){
            flag = false;
        }
    });

    if(!flag){
        alert("인증제출 파일을 첨부해주세요.");
        return false;
    }

    if($("#agree:checked").length == 0){
        alert("We펌 개설 및 이용약관 동의하셔야 합니다.");
        return false;
    }

    let formData = new FormData();

    // 기본 정보
    let jsonData = {
        'sfRegNumber': $("input[name=sfRegNumber]").val(),
        'name': $('input[name=name]').val(),
        'establishYear': $('input[name=establishYear]').val(),
        'sfPhone': $('input[name=sfPhone]').val(),
        'ceoName': $('input[name=ceoName]').val(),
        'email': $('input[name=email]').val(),
        'websiteUrl': $('input[name=websiteUrl]').val(),
        'wefirmUrl': $('input[name=wefirmUrl]').val(),
        'description': $('textarea[name=description]').val()
    }

    // logoFile
    Array.from($("input[name=logoFiles]")[0].files).map(e => formData.append('logoFiles', e));

    // File
    $("input[name=files]").each(function(){
        Array.from($(this)[0].files).map(e => formData.append('files', e));
    });

    console.log(jsonData);
    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    // 전송
    $.ajax({
        url: "/hh/wefirm/application-open",
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (d) {
            if(d.status == 200){
                if(d.serviceCode == "SUCCESS"){
                    alert("We펌 개설 신청이 완료되었습니다.");
                    location.href = '/';
                } else if(d.serviceCode == "REGISTER") {
                    alert("We펌 개설 신청이 있어 We펌 개설 신청이 불가합니다.");
                    window.location.reload();
                } else if(d.serviceCode == "WAIT") {
                    alert("We펌 신청 내역이 있어 We펌 개설 신청이 불가합니다.");
                    window.location.reload();
                } else if(d.serviceCode == "DUPLICATE") {
                    alert("R9 We펌 주소가 이미 등록되었습니다. 다른 주소를 입력해주세요.");
                }
            } else {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });

});