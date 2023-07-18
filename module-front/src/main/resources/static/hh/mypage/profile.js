$(document).ready(function(){

    // 휴대폰번호 정규식
    $("p[name=phone]").text(Utils.formatPhoneNumber($("p[name=phone]").text()));

    // 전분문야 필드 값 세팅
    $.ajax({
        type: "get",
        url: "/hh/mypage/profile/hh-position-field",
        data: {memberId : $("input[name=memberId]").val()},
        success: function (data) {
            $(data.data).each(function (i, d) {
                let self = this;
                $("input[name=positionField]").each(function () {
                    if ($(this).val() === self.fieldCd) {
                        $(this).prop("checked", true);
                    }
                });
            });
        }
    });
});

// 이미지 등록시
$("#form-file").change(function(e){
    let fileVal = $(this).val();
    let ext = fileVal.split('.').pop().toLowerCase();
    if($.inArray(ext, ['jpg','gif','png','pdf']) == -1) {
        alert('등록할 수 없는 파일입니다.');
        return false;
    }
    let fileNm = $(this)[0].files[0].name;
    $(".file-name").text(fileNm);

    // 모달 사진 미리보기
    let file = e.target.files[0];
    let fileUrl = window.URL.createObjectURL(file);
    $("#modal-profile-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('" + fileUrl + "')");

    setTimeout(function () {
        window.URL.revokeObjectURL(fileUrl)
    }, 1000*60*50)

    $("input[name=photoChangeFlag]").val("Y");
});

// 전문분야 선택 시
$("input[name=positionField]").change(function() {
   if($("input[name=positionField]:checked").length > 5) { // 5개이상 선택 시 alert
       alert("최대 5개까지 선택 가능합니다.");
       $(this).prop("checked",false);
   }
});

// 미리보기
$("#modal-profile-headhunter").on("shown.bs.modal", function(e) {
    $("span[name=hhPosition]").text($("input[name=position]").val());
    $("span[name=hhSchool]").text($("input[name=school]").val() + ' ' + $("input[name=major]").val());
    $("p[name=hhGreeting]").text($("textarea[name=greeting]").val());

    // 주요이력
    $(".history").empty();
    let careerArr = $("textarea[name=careerDesc]").val().replace(/\r\n/g,"\n").split("\n");
    for(let i=0; i<careerArr.length; i++) {
        let liCareer = "<li>"+careerArr[i]+"</li>"
        $(".history").append(liCareer);
    }

    // 전문분야
    $(".fieldList").empty();
    let fieldArr = [];
    $("input[name=positionField]:checked").each(function(){
        let fieldCd = $(this).val();
        let fieldNm = "";

        if(fieldCd == "SERVICE") {
            fieldNm = "서비스";
        } else if(fieldCd == "EDUCATION") {
            fieldNm = "교육";
        } else if(fieldCd == "FINANCE") {
            fieldNm = "금융·은행";
        } else if(fieldCd == "ASSOCIATION") {
            fieldNm = "기관·협회";
        } else if(fieldCd == "MEDIA") {
            fieldNm = "미디어·광고·출판";
        } else if(fieldCd == "CONSTRUCTION") {
            fieldNm = "건설·건축";
        } else if(fieldCd == "IT") {
            fieldNm = "IT·인터넷";
        } else if(fieldCd == "ELECTRONIC") {
            fieldNm = "전자·통신·반도체";
        } else if(fieldCd == "MEDICAL") {
            fieldNm = "의료·제약";
        } else if(fieldCd == "SALE") {
            fieldNm = "유통·판매";
        } else if(fieldCd == "ART") {
            fieldNm = "문화·예술·디자인";
        } else if(fieldCd == "CHEMISTRY") {
            fieldNm = "제조·생산·화학";
        }
        fieldArr.push(fieldNm);
    })
    for(i=0; i<fieldArr.length; i++) {
        $(".fieldList").append("<span class=\"tag tag-lg tag-outline-primary none-border m-0\">"+"<em>"+fieldArr[i]+"</em>"+"</span>")
    }
})

// 입력완료 버튼 클릭 시
$("button[data-role=save]").click(function(){

    if (!confirm("수정하시겠습니까?")) {
        return false;
    } else {
        let checkFieldArr = [];
        $("input[name=positionField]:checked").each(function(){
            let checkVal = $(this).val();
            checkFieldArr.push(checkVal);
        })

        let form = $('form[name=profileInfo]')[0];
        let formData = new FormData(form);
        formData.set("checkFieldArr",checkFieldArr);

        $.ajax({
            url: "/hh/mypage/profile-setting",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                if(data.status == 200) {
                    alert("수정되었습니다.");
                    location.reload();
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