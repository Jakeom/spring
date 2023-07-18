// 초기 로딩시
$("input[name=formatPhone]").val(Utils.formatPhoneNumber($("input[name=phone]").val()));

// 이미지 클릭시 파일 창 팝업
$(".upload-img img").click(function(){
    $("#form-file").trigger("click");
});

// 이미지 변경시 업데이트
$("#form-file").change(function(e){
    var ext = $(this).val().split('.').pop().toLowerCase();
    if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
        alert('등록할 수 없는 파일입니다.');
        $("#form-file").val("");
        return false;
    }

    let reader = new FileReader();
    let files = e.target.files;
    let filesArr = Array.prototype.slice.call(files);
    filesArr.forEach(function(f) {
        reader.onload = function (e) {
            $(".upload-img img").attr('src', e.target.result);
        }
        reader.readAsDataURL(f);
    });

    $("input[name=photoChangeFlag]").val("Y");
});

// 이름, 생년월일 클릭시 본인인증창 팝업
$("#phone, #name").click(function(){
    $("button[data-role=cert]").trigger("click");
});

// 본인인증 창 팝업
$("button[data-role=cert]").click(function(){
    $.ajax({
        url: "/fo/auth/cert",
        success: function(d){
            let certNum = d.data;
            $("input[name=tr_cert]").val(certNum);
            window.open("", "certForm", 'width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250');
            $("form[name=certForm]").submit();
        },
        error: function(e, t){
            alert(t);
        }
    });
});

// 본인인증 완료시 핸드폰 번호 변경
$("#di").click(function(){
    $("input[name=formatPhone]").val(Utils.formatPhoneNumber($("input[name=phone]").val()));
});

// 수정완료 버튼 클릭시
$("button[data-role=modify]").click(function(){
    /* 주말/휴일제외 여부 */
    let contactExceptHoliday = "1";
    if($("input:checkbox[name=contactExceptHoliday]").is(":checked")) {
        contactExceptHoliday = "0"
    }

    if (!confirm("정보수정 하시겠습니까?")) {
        return false;
    } else {
        let form = $('form[name=infoForm]')[0];
        let formData = new FormData(form);
        formData.append("contactExceptHoliday", $("#contactExceptHoliday").is(":checked") ? "1" : "0");

        $.ajax({
            url: "/fo/mypage/profile-info",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                if(data.serviceCode == "true") {
                    alert("정보수정에 성공하였습니다.");
                    location.href = "/";
                } else {
                    alert("정보수정에 실패했습니다. 관리자에게 문의부탁드립니다.");
                }
            },
            error: function() {
                alert("정보수정에 실패했습니다. 관리자에게 문의부탁드립니다.");
            }
        });
    }
});

$("input[name=birth]").focusout(e => Utils.alert(Utils.dateCheck($(e.target), "YYYYMMDD"))); // 생년월일 검증