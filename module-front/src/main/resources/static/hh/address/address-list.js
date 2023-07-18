$("select").selectAjax({});

function phoneFomatter(num,type){
    var formatNum = '';
    if(num.length==11){
        if(type==0){
            formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
        }else{
            formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
    }else if(num.length==8){
        formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
    }else{
        if(num.indexOf('02')==0){
            if(type==0){
                formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
            }else{
                formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
            }
        }else{
            if(type==0){
                formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
            }else{
                formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
            }
        }
    }
    return formatNum;
}

function ValidateEmail(mail) 
{
 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail.value))
  {
    return (true)
  }
    return (false)
}


$('._phone').each(function () {
    $(this).text(phoneFomatter($(this).text()));
    $(this).show();
});

$('._time').each(function () {
    $(this).text($(this).text().substring(0, 16));
    $(this).show();
});

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

// 그룹 이동
$("#addressGroupBody .addressGroupRow").on("click",function(e){
    $("form[name=searchForm] input[name=groupId]").val($(this).data().id);
    $("form[name=searchForm] input[name=keyword]").val('');
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 선택된 그룹값 설정
$('.editGroup').on("click",function(){
    $("form[name=addressGroupForm] input[name=id]").val($(this).data().id);
    $("form[name=addressGroupForm] input[name=name]").val($(this).data().name);
    $('#modal-group-edit #name').val($(this).data().name);
    $('#modal-gorup-delete #name').text($(this).data().name);
});


// 그룹 추가
function groupAdd(){
    
    if($('#modal-group-add #name').val().length == 0){
        alert("그룹명을 입력해주세요.");
        return;
    }

    // 전달값
    $("form[name=addressGroupForm] input[name=name]").val($('#modal-group-add #name').val());
    $("form[name=addressGroupForm] input[name=id]").val(null);
    $("form[name=addressGroupForm] input[name=delFlag]").val('N');

   
    $.ajax({
        url: '/hh/address/addGroup',
        type: 'POST',
        data: $("form[name=addressGroupForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}

// 그룹 수정
function groupEdit(){
    
    if($('#modal-group-edit #name').val().length == 0){
        alert("그룹명을 입력해주세요.");
        return;
    }

    // 전달값
    $("form[name=addressGroupForm] input[name=name]").val($('#modal-group-edit #name').val());
    $("form[name=addressGroupForm] input[name=delFlag]").val('N');

   
    $.ajax({
        url: '/hh/address/editGroup',
        type: 'POST',
        data: $("form[name=addressGroupForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                alert("수정되었습니다.");
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}


// 그룹 삭제
function groupDel(){

    //전달값
    $("form[name=addressGroupForm] input[name=delFlag]").val('Y');

    $.ajax({
        url: '/hh/address/editGroup',
        type: 'POST',
        data: $("form[name=addressGroupForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                alert("삭제되었습니다.");
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });    
}

// 그룹 이동
/*function groupMove(){

    $.ajax({
        url: '/hh/address/moveGroup',
        type: 'POST',
        data: $("form[name=addressGroupMoveForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}*/

// 연락쳐 추가()
function addContact(calback){

    var _name = $("form[name=addressContactForm] input[name=name]").val();
    var _phone = $("form[name=addressContactForm] input[name=phone]").val();
    var _email = $("form[name=addressContactForm] input[name=email]").val();


    if(_name.length ==0){
        alert('이름을 입력해주세요.');
        return false;
    }

    if(_phone.length ==0 && _email.length ==0 ) {
        alert("이메일주소/휴대폰번호를 입력해주세요.");
        return false;
    }

    if(_email.length != 0) {
        if(ValidateEmail(_email)) {
            alert("이메일을 확인해주세요.");
            return false;
        }
    }

    if(_phone.length != 0) {
        if(_phone.length != 11) {
            alert("휴대폰번호를 확인해주세요.");
            return false;
        }
    }
    
    $.ajax({
        url: '/hh/address/addContact',
        type: 'POST',
        data: $("form[name=addressContactForm]").serialize(),
        success: function(data) {
            if(calback != null){
                calback(data);
            }else{
                if(data.status == 200){
                    window.location.reload();
                }else if(data.status == 405){
                    $('#modal-addContact-alert').modal('show');
                    $('#modal-add-address').modal('hide');
                } else {
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                }
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}

function openContactDel(){
    if(getCheckBoxCnt() == 0){
        alert("연락처를 선택해주세요.");
        return;
    }
    $('#modal-contact-delete').modal('show');
}

//이동 선택
function openContactMove(obj){

    if(getCheckBoxCnt() == 0){
        alert("연락처를 선택해주세요.");
        $('#groupIdPop').val('');
        $('#groupIdPop').select2({
            minimumResultsForSearch: -1,
        });
        return;
    }

    if($(obj).val()!=''){
        $("form[name=addressContactForm] input[name=groupIdMove]").val($("select[name=groupIdPop] option:selected").val());
        $('#modal-gorup-move #name').text($("select[name=groupIdPop] option:selected").text());
        $('#modal-gorup-move').modal('show');
    }

    $('#groupIdPop').val('');
    $('#groupIdPop').select2({
      minimumResultsForSearch: -1,
    });

}

// 그룹이동
function moveContact() {

    let params = {
        checkedFields : getCheckBoxValues(),
        groupId : $("form[name=addressContactForm] input[name=groupIdMove]").val()
    }

    if($("input[name=groupId]").val() == params.groupId) {
        alert("현재 소속되어있는 그룹입니다.")
        window.location.reload();
        return false;
    }

    $.ajax({
        url: '/hh/address/moveGroup',
        type: 'POST',
        data: JSON.stringify(params),
        contentType: "application/json",
        success: function(data) {
            if(data.status == 200){
                alert("선택한 연락처의 그룹이 이동되었습니다.");
                window.location.href="/hh/address/address-list?groupId="+params.groupId;
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}

// 연락쳐 삭제()
function editContact(delFlg){

    if(getCheckBoxCnt() == 0){
        alert("연락처를 선택해주세요.");
        return;
    }
    if('Y' == delFlg){
        $("form[name=addressContactForm] input[name=groupId]").val(null);
    }
    $("form[name=addressContactForm] input[name=delFlag]").val(delFlg);
    $("form[name=addressContactForm] input[name=checkedFields]").val(getCheckBoxValues());

    $.ajax({
        url: '/hh/address/editContact',
        type: 'POST',
        data: $("form[name=addressContactForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                alert("삭제되었습니다.");
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });

}

// 체크박스 전체 처리
function allCheck(){
    var _is = $('#multipleCheckAll').is(':checked');
    $( '._talbeCheckbox' ).each(function() {
        $( this ).prop("checked",_is);
    });    
}

// 체크된 박스 숫자 체크 
function getCheckBoxCnt(){
    var _isTrueCnt = 0;
    $( '._talbeCheckbox' ).each(function() {
        if($( this ).is(':checked')){
            _isTrueCnt++;
        }
    });
    return _isTrueCnt;
}

// 체크된 박스 값 리턴 
function getCheckBoxValues(){
    var checkedFields = '';
    $( '._talbeCheckbox' ).each(function() {
        if($( this ).is(':checked')){
            if(checkedFields != ''){
                checkedFields = checkedFields + ',';
            }
            checkedFields = checkedFields + $( this ).val();
        }
    });
    return checkedFields;
}

// 체크박스 처리
$('._talbeCheckbox').on('change', function(){
    if($( '._talbeCheckbox' ).length  == getCheckBoxCnt()){
        $('#multipleCheckAll').prop("checked",true);
    }else{
        $('#multipleCheckAll').prop("checked",false);
    }
});

// 모달 sms 탭을 누른 경우
function smsTab(){

    $('._s').addClass('active');
    $('._e').removeClass('active');

    $('#modal-suggest-email').modal('hide');
    $('#modal-suggest-sms').modal('show');
}

// 모달 email 탭을 누른 경우
function emailTab(){

    $('._e').addClass('active');
    $('._s').removeClass('active');

    $('#modal-suggest-sms').modal('hide');
    $('#modal-suggest-email').modal('show');
}


CKEDITOR.replace('emailContent', {height: 500});

function openAddCC(){
    $('#modal-email-add').modal('show');
    $("#addName").val("");
    $("#addphone").val("");
    $("#addEmail").val("");
}

function modalAddCC(){
    
    $('#addContactChk').is(':checked');

    var _data = {
        name: $('#addName').val(),
        phone: $('#addphone').val(),
        email: $('#addEmail').val(),
    }

    if(_data.name.length ==0){
        alert('이름을 입력해주세요.');
        return false;
    }

    if(_data.phone.length ==0 && _data.email.length ==0 ) {
        alert("이메일주소/휴대폰번호를 입력해주세요.");
        return false;
    }

    if(_data.phone.length != 0) {
        if(_data.phone.length != 11) {
            alert("휴대폰번호를 확인해주세요.");
            return false;
        }
    }

    if(_data.email.length != 0) {
        if(ValidateEmail(_data.email)) {
            alert("이메일을 확인해주세요.");
            return false;
        }
    }

    if($('#addContactChk').is(':checked')){

        $("form[name=addressContactForm] input[name=name]").val(_data.name);
        $("form[name=addressContactForm] input[name=phone]").val(_data.phone);
        $("form[name=addressContactForm] input[name=email]").val(_data.email); 
        addContact(function(data){
            if(data.status == 200){
                addCC(_data);
                $('#modal-email-add').modal('hide');
            }else if(data.status == 405){
                alert('이미 등록된 주소록입니다.');                
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
    }else{
        addCC(_data);

        $('#modal-email-add').modal('hide');
    }


     
}

function addCC(_data){
    if(_data != null){
        let html = '<div class="btn btn-sm btn-outline-gray round d-flex justify-content-around recipient-row">' +
        '<input type="hidden" name="idArr" value="' + _data.name+":"+_data.phone+":"+_data.email +  '" />' +
        '<span class="name">' + _data.name + '</span>' +
        '<button type="button" class="close" data-role="removeApplicantId"><i class="icon-b-close"></i></button>' +
        '</div>';
        $(".coworker-list").append(html);
    }
}

// 이메일 제안
$("#modal-suggest-email").on("shown.bs.modal", function(e){

    $('._e').addClass('active');
    $('._s').removeClass('active');

    if($(e.relatedTarget).data() != null){
        var _data = $(e.relatedTarget).data();
        $(".coworker-list").empty();
        if(_data.type == "btn"){
            if(getCheckBoxCnt()>0){
                var ids = getCheckBoxValues();
                ids.split(",").forEach(function(e){
                    addCC($('#_c'+e).data());
                });
            } else {
                $(this).modal("hide");
                alert("발송할 인원을 선택해주세요");
                return false;
            }
        }else{
            addCC(_data);
        }
    }
    

});

// 문자 안내
$("#modal-suggest-sms").on("shown.bs.modal", function(e){

    $('._s').addClass('active');
    $('._e').removeClass('active');
    
    if($(e.relatedTarget).data() != null){
        var _data = $(e.relatedTarget).data();
        $(".coworker-list").empty();
        addCC(_data);
    }
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

$(document).on("click", "[data-role=removeApplicantId]", function(){
    $(this).closest(".recipient-row").remove();
});

// [발송 모달 / 이메일] - 템플릿 선택시
$("select[name=emailTemplate]").change(function(){
    if($(this).val() == ""){
        $("input[name=emailSubject]").val("");
        CKEDITOR.instances["emailContent"].setData("");
        return false;
    }
    $("input[name=emailSubject]").val($(this).find("option:selected").data("subject"));
    CKEDITOR.instances["emailContent"].setData($(this).find("option:selected").data("content"));
 });

// [발송 모달 / SMS/MMS] - 템플릿 선택시
$("select[name=smsTemplate]").change(function() {
    if($(this).val() == "") {
        $("input[name=smsSubject]").val("");
        $("textarea[name=smsContent]").val("")
        return false;
    }
    $("input[name=smsSubject]").val($(this).find("option:selected").data("subject"));
    $("textarea[name=smsContent]").val($(this).find("option:selected").data("content"));
})

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

    let targetList = [];
    $("#modal-suggest-email input[name=idArr]").each(function(){
        let targetName = $(this).val().split(":")[0];
        let targetEmail = $(this).val().split(":")[2];

        let targetObj = {};
        targetObj.name = targetName;
        targetObj.email = targetEmail;
        targetList.push(targetObj);
    });

    let jsonData = {
                targetList: targetList,
                subject: $("#modal-suggest-email input[name=emailSubject]").val(),
                content: data,
            }

    // 전송
    $.ajax({
        url: '/hh/address/send-mail',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(jsonData),
        success: function (data) {
            if(data.status == 200){
                alert("발송이 완료되었습니다.");
                window.location.reload();
            }
        }
    });
});

var emails;
var smss;

// [SMS 안내] - 발송 버튼 클릭시
$("[data-role=sendSms]").click(function(){
    // validation
    if(!Utils.alert(Utils.valid($("#modal-suggest-sms textarea[name=smsContent]"), null, "내용")).result) return false;

    if($("#modal-suggest-sms input[name=idArr]").length == 0){
        alert("수신 대상이 존재하지 않습니다.");
        return false;
    }

    let targetList = [];
    $("#modal-suggest-email input[name=idArr]").each(function(){
        let targetName = $(this).val().split(":")[0];
        let targetPhone = $(this).val().split(":")[1];

        let targetObj = {};
        targetObj.name = targetName;
        targetObj.phone = targetPhone;
        targetList.push(targetObj);
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
    if(totalByte > 60 || $("#modal-suggest-sms input[name=smsSubject]").val().trim().length != 0){ // SMS에 제목 입력 시 LMS처리
        type = "LMS";
    }
    console.log("SMS/LMS TYPE >> {}",type);

    $.ajax({
        url: "/hh/address/send-sms",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            targetList: targetList,
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

$("button[data-role=replacement-btn]").click(function () {
    $(".replacement-tag-notice").addClass("active");
});
$(".replacement-tag-notice .close").click(function () {
    $(".replacement-tag-notice").removeClass("active");
});