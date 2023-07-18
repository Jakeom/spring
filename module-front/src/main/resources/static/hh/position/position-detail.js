// 인재 등록이후 돌아왔을때 처리를 위함
var has = window.location.hash;
window.location.hash = '';
if(has.length > 0) {
    var strArray = has.split('|');
    
    if(strArray[0] == '#REGISTER'){
        $('.nav-link')[1].click();
    }
}

// Init
$("select").selectAjax({});
CKEDITOR.replace('emailContent', {height: 500});
CKEDITOR.replace('recommendContent', {height: 500});

// 이메일 제안
$("#modal-suggest-email").on("shown.bs.modal", function(e){
    $(".coworker-list").empty();
    if($("input[name=applicantIds]:checked").length == 0){
        $(this).modal("hide");
        alert("제안을 할 대상을 선택해주세요.");
    }

    let alertFlag = false;
    $("input[name=applicantIds]:checked").each(function(){
        let flag = true;
        let id = $(this).val();
        $("input[name=idArr]").each(function(){
            if(id == $(this).val()){
                flag = false;
            }
        });

        if($(this).data("emailFlag") == "N"){
            alertFlag = false;
            flag = false;
        }

        if($(this).data("regPath") == "2"){
            alertFlag = true;
            flag = false;
        }

        if(flag){
            let html = '<div class="btn btn-sm btn-outline-gray round d-flex justify-content-around recipient-row">' +
                '<input type="hidden" name="idArr" value="' + id +  '" />' +
                '<span class="name">' + $(this).data("name") + '</span>' +
                '<button type="button" class="close" data-role="removeApplicantId"><i class="icon-b-close"></i></button>' +
                '</div>';
            $(".coworker-list").append(html);
        }
    });

    if(alertFlag){
        alert("직접지원한 대상에게는 제안 대상에 포함되지 않습니다.");
    }
});

// 문자 안내
$("#modal-suggest-sms").on("shown.bs.modal", function(e){
    if($("input[name=applicantIds]:checked").length == 0){
        $(this).modal("hide");
        alert("제안을 할 대상을 선택해주세요.");
    }

    $("input[name=applicantIds]:checked").each(function(){
        let flag = true;
        let id = $(this).val();
        $("input[name=idArr]").each(function(){
            if(id == $(this).val()){
                flag = false;
            }
        });

        if(flag){
            let html = '<div class="btn btn-sm btn-outline-gray round d-flex justify-content-around recipient-row">' +
                '<input type="hidden" name="idArr" value="' + id +  '" />' +
                '<span class="name">' + $(this).data("name") + '</span>' +
                '<button type="button" class="close" data-role="removeApplicantId"><i class="icon-b-close"></i></button>' +
                '</div>';
            $(".coworker-list").append(html);
        }
    });
});

// [발송 모달 / 이메일] - 템플릿 선택시
$("select[name=emailTemplate]").change(function(){
   if($(this).val() == ""){
       $("input[name=emailSubject]").val("");
       $("textarea[name=emailContent]").val("");
       return false;
   }
   $("input[name=emailSubject]").val($(this).find("option:selected").data("subject"));
   CKEDITOR.instances["emailContent"].setData($(this).find("option:selected").data("content"));
});

// [발송 모달 / SMS] - 템플릿 선택시
$("select[name=smsTemplate]").change(function(){
    if($(this).val() == ""){
        $("input[name=smsSubject]").val("");
        $("textarea[name=smsContent]").val("");
        return false;
    }
    $("input[name=smsSubject]").val($(this).find("option:selected").data("subject"));
    $("textarea[name=smsContent]").val($(this).find("option:selected").data("content"));
});

// [발송 모달] - 수신자 삭제
$(document).on("click", "[data-role=removeApplicantId]", function(){
    $(this).closest(".recipient-row").remove();
});

// [이메일 제안] - 발송 버튼 클릭시
$("[data-role=sendEmail]").click(function(){
    var data = CKEDITOR.instances.emailContent.getData();
    // validation
    if(!Utils.alert(Utils.valid($("#modal-suggest-email input[name=emailSubject]"), null, "제목")).result) return false;

    if($("#modal-suggest-email input[name=idArr]").length == 0){
        alert("수신 대상이 존재하지 않습니다.");
        return false;
    }

    if(data.length == 0){
        alert("내용이 존재하지 않습니다.");
        return false;
    }

    let idList = [];
    $("#modal-suggest-email input[name=idArr]").each(function(){
        idList.push({
            "id": $(this).val()
        });
    });

    $.ajax({
        url: "/hh/position/send-mail",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            idList: idList,
            subject: $("#modal-suggest-email input[name=emailSubject]").val(),
            content: data,
        }),
        success: function(d){
            if(d.status == 200){
                alert("포지션 제안 발송이 완료되었습니다.");
                window.location.reload();
            } else {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// [SMS 안내] - 발송 버튼 클릭시
$("[data-role=sendSms]").click(function(){
    // validation
    if(!Utils.alert(Utils.valid($("#modal-suggest-sms textarea[name=smsContent]"), null, "내용")).result) return false;

    if($("#modal-suggest-sms input[name=idArr]").length == 0){
        alert("수신 대상이 존재하지 않습니다.");
        return false;
    }

    let idList = [];
    $("#modal-suggest-sms input[name=idArr]").each(function(){
        idList.push({
           "id": $(this).val()
        });
    });

    const textVal = $("#modal-suggest-sms textarea[name=smsContent]").val(); //입력한 문자
    const textLen = textVal.length; //입력한 문자수

    let totalByte = 0;
    for (let i = 0; i < textLen; i++) {
        const eachChar = textVal.charAt(i);
        const uniChar = escape(eachChar); //유니코드 형식으로 변환
        if (uniChar.length > 4) {
            totalByte += 2;
        } else {
            totalByte += 1;
        }
    }

    let type = "SMS";
    if(totalByte > 60){
        type = "LMS";
    }

    $.ajax({
        url: "/hh/position/send-sms",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            idList: idList,
            subject: $("#modal-suggest-sms input[name=smsSubject]").val(),
            content: $("#modal-suggest-sms textarea[name=smsContent]").val(),
            type: type
        }),
        success: function(d){
            if(d.status == 200){
                if(d.serviceCode == "NO_POINT"){
                    alert("포인트가 부족합니다. 충전해주세요.");
                    window.location.reload();
                } else {
                    alert("문자발송이 완료되었습니다.");
                    window.location.reload();
                }
            } else {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// 탈락 문자 안내
$("#modal-fail-sms").on("shown.bs.modal", function(e){
    if($("input[name=registerApplicantIds]:checked").length == 0){
        $(this).modal("hide");
        alert("탈락통보할 할 대상을 선택해주세요.");
    }

    $("input[name=registerApplicantIds]:checked").each(function(){
        let flag = true;
        let id = $(this).val();
        $("input[name=idArr]").each(function(){
            if(id == $(this).val()){
                flag = false;
            }
        });

        if(flag){
            let html = '<div class="btn btn-sm btn-outline-gray round d-flex justify-content-around recipient-row">' +
                '<input type="hidden" name="idArr" value="' + id +  '" />' +
                '<span class="name">' + $(this).data("name") + '</span>' +
                '<button type="button" class="close" data-role="removeApplicantId"><i class="icon-b-close"></i></button>' +
                '</div>';
            $(".coworker-list").append(html);
        }
    });
});

// [탈락 안내] - 발송 버튼 클릭시
$("[data-role=sendFailSms]").click(function(){
    // validation
    if(!Utils.alert(Utils.valid($("#modal-fail-sms textarea[name=failSmsContent]"), null, "내용")).result) return false;

    if($("#modal-fail-sms input[name=idArr]").length == 0){
        alert("수신 대상이 존재하지 않습니다.");
        return false;
    }

    let idList = [];
    $("#modal-fail-sms input[name=idArr]").each(function(){
        idList.push({
            "id": $(this).val()
        });
    });

    const textVal = $("#modal-fail-sms textarea[name=failSmsContent]").val(); //입력한 문자
    const textLen = textVal.length; //입력한 문자수

    let totalByte = 0;
    for (let i = 0; i < textLen; i++) {
        const eachChar = textVal.charAt(i);
        const uniChar = escape(eachChar); //유니코드 형식으로 변환
        if (uniChar.length > 4) {
            totalByte += 2;
        } else {
            totalByte += 1;
        }
    }

    let type = "SMS";
    if(totalByte > 60){
        type = "LMS";
    }

    $.ajax({
        url: "/hh/position/send-fail-sms",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            idList: idList,
            subject: $("#modal-fail-sms input[name=failSmsSubject]").val(),
            content: $("#modal-fail-sms textarea[name=failSmsContent]").val(),
            type: type
        }),
        success: function(d){
            if(d.status == 200){
                alert("문자발송이 완료되었습니다.");
                window.location.reload();
            } else {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// SMS 실패 모달 - 글자수 세기
$("textarea[name=smsContent]").keyup(function() {
    const textVal = $(this).val(); //입력한 문자
    const textLen = textVal.length; //입력한 문자수

    let totalByte = 0;
    for (let i = 0; i < textLen; i++) {
        const eachChar = textVal.charAt(i);
        const uniChar = escape(eachChar); //유니코드 형식으로 변환
        if (uniChar.length > 4) {
            totalByte += 2;
        } else {
            totalByte += 1;
        }
    }

    if(totalByte > 60){
        $("#modal-suggest-sms .msg-type").text(totalByte + " byte / LMS")
    } else {
        $("#modal-suggest-sms .msg-type").text(totalByte + " byte / SMS")
    }
});

// SMS 실패 모달 - 글자수 세기
$("textarea[name=failSmsContent]").keyup(function() {
    const textVal = $(this).val(); //입력한 문자
    const textLen = textVal.length; //입력한 문자수

    let totalByte = 0;
    for (let i = 0; i < textLen; i++) {
        const eachChar = textVal.charAt(i);
        const uniChar = escape(eachChar); //유니코드 형식으로 변환
        if (uniChar.length > 4) {
            totalByte += 2;
        } else {
            totalByte += 1;
        }
    }

    if(totalByte > 60){
        $("#modal-fail-sms .msg-type").text(totalByte + " byte / LMS")
    } else {
        $("#modal-fail-sms .msg-type").text(totalByte + " byte / SMS")
    }
});

// 컨택 리스트 전체 선택
$("input[name=contactListCheckAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=applicantIds]").prop("checked", true)
    } else {
        $("input[name=applicantIds]").prop("checked", false);
    }
});

// 접수 리스트 전체 선택
$("input[name=processListCheckAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=registerApplicantIds]").prop("checked", true)
    } else {
        $("input[name=registerApplicantIds]").prop("checked", false);
    }
});


$("[data-role=registerResume]").click(function(){
    window.location.hash = '#REGISTER';
    location.href='/hh/apsearch/ap-registration-direct?memberId='+$(this).data('memberid')+'&applicantId='+$(this).data('id');
});
// 컨텍리스트 수동 등록
$("[data-role=contactManualRegister]").click(function(){
    // validation
    if(!Utils.alert(Utils.valid($("#modal-position-list input[name=name]"), null, "성명")).result) return false;
    if(!Utils.alert(Utils.valid($("#modal-position-list input[name=phone]"), null, "핸드폰번호")).result) return false;
    if(!Utils.alert(Utils.valid($("#modal-position-list input[name=email]"), null, "이메일")).result) return false;

    $.ajax({
        url: "/hh/position/contact-manual",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            name: $("#modal-position-list input[name=name]").val(),
            phone: $("#modal-position-list input[name=phone]").val(),
            email: $("#modal-position-list input[name=email]").val(),
            positionId: $("#modal-position-list input[name=positionId]").val()
        }),
        success: function(d){
            if(d.serviceCode == "SUCCESS"){
                alert("등록이 완료되었습니다.");
                window.location.reload();
            } else if(d.serviceCode == "PHONE_DUPLICATION"){
                alert("휴대폰이 중복된 회원이 존재합니다.");
            } else if(d.serviceCode == "EMAIL_DUPLICATION"){
                alert("이메일이 중복된 회원이 존재합니다.");
            } else if(d.serviceCode == "DELETED_MEMBER"){
                alert("탈퇴한 회원이 존재합니다.");
            } else if(d.serviceCode == "MEMBER_DUPLICATION"){
                alert("회원이 이미 존재합니다.");
            } else if(d.serviceCode == "REJECT"){
                alert("이력서 등록 거절 내역이 있어 등록하실 수 없습니다.")
            } else {
                alert("등록중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        },
        error: function(){
            alert("등록중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
        }
    });
});

// 포지션 상태 변경시
$("select[name=status]").change(function(){
    if($(this).val() == "CLOSED"){
        $("#modal-position-end").modal("show");
        $(this).val("");
    }
});

// 코웤 상태 변경시
$("select[name=coStatus]").change(function(){
    if($(this).val() == "CLOSED"){
        $("#modal-coworker-end").modal("show");
        $(this).val("");
    }
});

// 포지션 종료
$("[data-role=positionEnd]").click(function(){
    if($("#modal-position-end input[name=stopReason]:checked").length == 0){
        alert("포지션 종료 사유를 입력해 주세요.");
        return false;
    }

    $.ajax({
        url: "/hh/position/position-end",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            positionId: $("#modal-position-end input[name=positionId]").val(),
            stopReason: $("#modal-position-end input[name=stopReason]:checked").val(),
            status: 'END'
        }),
        success: function(d){
            if(d.status == 200){
                alert("포지션 종료가 완료되었습니다.");
                window.location.href = "/hh/position/position-list";
            } else {
                alert("종료도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// 코웤 종료
$("[data-role=coworkerEnd]").click(function(){
    $.ajax({
        url: "/hh/position/coworker-end",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            positionId: $("#modal-coworker-end input[name=positionId]").val()
        }),
        success: function(d){
            if(d.status == 200){
                alert("코웤 종료가 완료되었습니다.");
                window.location.href = "/hh/position/position-list";
            } else {
                alert("종료도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// 컨텍리스트 삭제 버튼
$("[data-role=deleteContactList]").click(function(){
    if($("#home input[name=applicantIds]:checked").length == 0){
        alert("삭제할 대상이 없습니다.");
        return false;
    }

    $("#modal-delete-contact-list").modal("show");
});

// 컨텍리스트 삭제 확인
$("[data-role=deleteContactListConfirm]").click(function(){
    let idList = [];

    $("#home input[name=applicantIds]:checked").each(function(){
        idList.push({"id": $(this).val()});
    });

    $.ajax({
        url: "/hh/position/delete-contact-list",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            idList: idList
        }),
        success: function(d){
            if(d.status == 200){
                alert("컨택 리스트 삭제가 완료되었습니다.");
                window.location.reload();
            } else {
                alert("삭제도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// 상태값 SORT 변경시
$("[name=searchType]").change(function(){
    $(this).closest("form").submit();
});

// 상태값 SORT 변경시
$("[name=searchType2]").change(function(){
    $(this).closest("form").submit();
});

// 적합/비적합 모달 오픈시
$("#modal-progress-state").on("shown.bs.modal", function(e){
    let id = $(e.relatedTarget).data("id");
    let phone = $(e.relatedTarget).data("phone");
    $("#modal-progress-state input[name=id]").val(id);
    $("#modal-progress-state input[name=phone]").val(phone);
});

// 적합/비적합 선택시
$("[data-role=nextProgress]").click(function(){
    $.ajax({
        url: "/hh/position/next-progress",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            id: $("#modal-progress-state input[name=id]").val(),
            phone: $("#modal-progress-state input[name=phone]").val(),
            progress: $("#modal-progress-state input[name=progress]:checked").val()
        }),
        success: function(d){
            if(d.status == 200){
                alert("처리가 완료되었습니다.");
                window.location.reload();
            } else {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// 코웤 - 연락처 공개 모달 오픈시
$("#modal-open-info").on("shown.bs.modal", function(e){
    let id = $(e.relatedTarget).data("id");
    $("#modal-open-info input[name=id]").val(id);
});

// 코웤 - 연락처 공개
$("[data-role=openInfoConfirm]").click(function(){
    $.ajax({
        url: "/hh/position/open-info",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            id: $("#modal-open-info input[name=id]").val()
        }),
        success: function(d){
            if(d.status == 200){
                alert("연락처를 PM에게 공개하였습니다.");
                window.location.reload();
            } else {
                alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// 채용사 추천 모달
$("#modal-recommend-email").on("shown.bs.modal", function(e){
    $(".recommend-list").empty();
    if($("input[name=registerApplicantIds]:checked").length == 0){
        $(this).modal("hide");
        alert("채용사 추천 할 대상을 선택해주세요.");
    }

    let flag = true;
    $("input[name=registerApplicantIds]:checked").each(function(){
        if(typeof $(this).data("submitResumeFileId") == "undefined"){
            flag = false;
        } else {
            let html = '<input type="hidden" name="idArr" value="' + $(this).val() +  '" />';
            $(".recommend-list").append(html);
        }
    });

    if(!flag){
        alert("추천용 이력서 등록되지 않은 후보자가 있으니 확인부탁드립니다.");
        $(this).modal("hide");
    }
});

// 채용사 추천 클릭시
$("[data-role=sendRecommendEmail]").click(function(){
    var data = CKEDITOR.instances.recommendContent.getData();
    // validation
    if(!Utils.alert(Utils.valid($("#modal-recommend-email input[name=recommendSubject]"), null, "제목")).result) return false;
    if(!Utils.alert(Utils.valid($("#modal-recommend-email input[name=password]"), null, "비밀번호")).result) return false;

    if(data.length == 0){
        alert("내용이 존재하지 않습니다.");
        return false;
    }

    if($("#modal-recommend-email input[name=idArr]").length == 0){
        alert("수신 대상이 존재하지 않습니다.");
        return false;
    }

    let idArr = [];
    $("#modal-recommend-email input[name=idArr]").each(function(){
        idArr.push({
            "id": $(this).val()
        });
    });

    $.ajax({
        url: "/hh/position/company-recommend",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            positionId: $("form[name=contactForm] input[name=id]").val(),
            content: data,
            subject: $("#modal-recommend-email input[name=recommendSubject]").val(),
            password: $("#modal-recommend-email input[name=password]").val(),
            idList: idArr
        }),
        success: function(d){
            if(d.status == 200){
                alert("고객담당자에게 추천 메일이 발송되었습니다.");
                window.location.reload();
            } else {
                alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

// 최종합격
$("[data-role=finalPass]").click(function(){
    let id = $(this).data("id");
    if (!confirm("최종합격 처리하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/hh/position/final-pass",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                id: id
            }),
            success: function (d) {
                if (d.status == 200) {
                    alert("합격처리가 완료되었습니다.");
                    window.location.reload();
                } else {
                    alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            }
        });
    }
});

// PM 제출 클릭시
$("[data-role=submitPm]").click(function(){
    if($("input[name=registerApplicantIds]:checked").length == 0){
        alert("PM 제출 할 대상을 선택해주세요.");
        return false;
    }

    let flag = true;
    let idArr = [];
    $("input[name=registerApplicantIds]:checked").each(function(){
        if(typeof $(this).data("submitResumeFileId") == "undefined"){
            flag = false;
        }
        idArr.push({
            "id": $(this).val()
        });
    });

    if(!flag){
        alert("추천용 이력서 등록되지 않은 후보자가 있으니 확인부탁드립니다.");
        return false;
    }

    if (!confirm("PM에게 제출하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/hh/position/pm-submit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                idList: idArr
            }),
            success: function (d) {
                if (d.status == 200) {
                    alert("PM 제출이 완료되었습니다.");
                    window.location.reload();
                } else {
                    alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            }
        });
    }
});

// 채팅하기 클릭시
$("[data-role=chat]").click(function(){
    let positionId = $(this).data("positionId");
    let memberId = $(this).data("memberId");
    let memberName = $(this).data("memberName");

    let headhunterId = $(this).data("headhunterId");
    let headhunterName = $(this).data("headhunterName");

    let loginId = $(this).data("headhunterId");
    let loginName = $(this).data("headhunterName");
    let loginType = "HH";
    let channelNm = $(this).data("title");

    // ------------------------------ 1:1 채팅 방 만들기 [start]--------------------------------------
    /*
     	채팅방 파라미터 세팅하기
     	masterUserId, masterName : 헤드헌터 id, 이름
     	loginId , loginName : 로그인 사람 id, 이름
     	channelNm : 채팅방 이름
     	loginType : 로그인 한사람 dType으로 HH, FO 인지 구분
     	userList : 1:1 채팅이면 1건 , 그룹이면 1건 이상 (구성원 정보)
     	pairYn : 1:1 채팅이면 Y, 그룹채팅이면 N
     	chatOpen : 채팅창 띄워서 바로 진입원하면  Y, 채팅방만 만드는 경우 N
    */

    // 헤드헌터인 경우만  userList 만든다.
    var userObj = { "userId" : memberId , "userNm" : memberName, "dType" : "FO" }
    var userList = [];
    userList.push(userObj);

    var channelObj = {
		channelNm : channelNm
		,loginUserId : loginId
		,loginUserNm : loginName
		,loginType : loginType
		,masterUserId : headhunterId
		,masterUserNm : headhunterName
		,pairYn : "Y"
		,positionId : positionId
		,userList : userList
		,chatOpen : 'Y'
	}
	console.log(channelObj);

	chatObj.channel_make(channelObj);
	// ------------------------------ 1:1 채팅 방 만들기 [end]--------------------------------------
});

$("[data-role=show]").click(function(){
	
	let id = $(this).data("id");
	
	$.ajax({
            url: "/hh/position/updateShowHist",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                id: id 
            }),
            success: function (d) {
            }
        });
	
});	