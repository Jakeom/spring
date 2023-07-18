$(document).on('click', '#writeCommunityModal',function(e) {
    $("select[data-role=communitySelect]").val($("input[name=communityType]").val()).trigger('change');
})

// 카테고리 변경 시 입력값 초기화
$("select[name=communityType]").change(function(){
    $("input[name=title]").val("");
    $("textarea[name=content]").val("");
    $("input[name=files]").val("");
    $(".preview-image-wrap").empty();
});

$(document).on('click', 'button[data-role=save]',function(e) {

    let title = $("input[name=title]").val().trim();
    let content = $("textarea[name=content]").val().trim();

    if(title == "") {
        alert("제목을 입력하세요.");
        $("input[name=title]").focus();
        return false;
    }

    if(content == "") {
        alert("내용을 입력하세요");
        $("textarea[name=content]").focus();
        return false;
    }

    if(!confirm("게시글을 등록하시겠습니까?")) {
        return false;
    } else {

        let form = $('form[name=hhCommunityForm]')[0];
        let formData = new FormData(form);

        $.ajax({
            url: "/hh/community/insert",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if(data.status == 200){
                    alert("등록되었습니다.");
                    window.location.href = "/hh/community/board?communityType=" + $("select[name=communityType]").val();
                } else {
                    alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
                }
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
})

// 이미지
$(document).on('change', '[data-role=upFile]',function(e) {
//$("[data-role=upFile]").change(function(e){
    let seq = $(this).data('seq')
    var ext = $(this).val().split('.').pop().toLowerCase();
    if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
        alert('등록할 수 없는 파일입니다.');
        $("#hhCommunityFormFile"+seq).val("");
        return false;
    }
    let reader = new FileReader();
    let files = e.target.files;
    let filesArr = Array.prototype.slice.call(files);
    if($("input[name=files]").length < 5) { // 파일 최대갯수 3장제한
        filesArr.forEach(function (f) {
            reader.onload = function (e) {
                var appedHtml = '<div class="preview-image" id="preview-image' + seq + '" style="background-image: url(' + e.target.result + ')">';
                appedHtml += '<div class="hover-close">';
                appedHtml += '<button type="button" data-delseq="' + seq + '" data-role="delImg"><i class="icon-del"></i></button>';
                appedHtml += '</div></div>';
                $("#modal-write-hh .preview-image-wrap").append(appedHtml)
                $("#modal-write-hh .form-upload").find('input[id=hhCommunityFormFile' + seq + ']').hide();
                $("#modal-write-hh .form-upload").find('label[for=hhCommunityFormFile' + seq + ']').hide();
                $("#modal-write-hh .form-upload").append('<input type="file" data-role="upFile" data-seq="' + (seq + 1) + '" name="files" id="hhCommunityFormFile' + (seq + 1) + '"/>');
                $("#modal-write-hh .form-upload").append('<label for="hhCommunityFormFile' + (seq + 1) + '"><i class="icon-input-add"></i></label>');
            }
            reader.readAsDataURL(f);
        });
    } else {
        alert("파일은 최대 3장까지 등록가능합니다.");
        $("#hhCommunityFormFile"+seq).val("");
        return false;
    }
});

$(document).on('click', '[data-role=delImg]',function(e) {
    let delSeq = $(this).data('delseq');
    $('#preview-image'+delSeq).remove();
    $('#hhCommunityFormFile'+delSeq).remove();
})