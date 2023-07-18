//페이지
$('a[data-role=btnGoPage]').click(function () {
    var page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
    $(".nav-tabs li a.active").trigger("click");
});

// 파일 변경시
$("#form-file").change(function(){
    let fileVal = $(this).val();
    let ext = fileVal.split('.').pop().toLowerCase();
    if($.inArray(ext, ['jpg','gif','png','pdf']) == -1) {
        alert('등록할 수 없는 파일입니다.');
        return false;
    }
    let fileSize = $(this)[0].files[0].size;
    let fileNm = $(this)[0].files[0].name;
    if(fileSize > 10 * 1024 * 1024){
        alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
        return false;
    }
    $(".file-name").text(fileNm);
});

$('a[data-role=submit]').click(function() {
    let subject = $("input[name=subject]").val().trim();
    let content = $("textarea[name=content]").val().trim();
    let kindCd = $("select[name=kindCd]").val();

    if(kindCd == ""){
        alert("문의항목을 선택해주세요.");
        return false;
    }

    if(subject == ""){
        alert("제목을 입력해주세요.");
        return false;
    }

    if(content == ""){
        alert("문의내용을 입력해주세요.");
        return false;
    }

    if (!confirm("문의 하시겠습니까?")) {
        return false;
    } else {
        let form = $('form[name=inquiryForm]')[0];
        let formData = new FormData(form);

        $.ajax({
            url: "/hh/service/inquiry",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                if(data.status == 200) {
                    alert("문의 접수되었습니다.");
                    location.href = "/hh/service/inquiry";
                } else {
                    alert("문의 접수에 실패했습니다. 관리자에게 문의부탁드립니다.");
                }
            },
            error: function() {
                alert("문의 접수에 실패했습니다. 관리자에게 문의부탁드립니다.");
            }
        });
    }
})

