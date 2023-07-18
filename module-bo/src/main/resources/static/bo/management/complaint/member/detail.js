let dtype = $("input[name=memberDetailDtype]").val(); // 현재 member의 멤버 타입
let memberId = $("input[name=memberDetailId]").val(); // 현재 접속해있는 member_id
let balance = $("input[name=memberDetailBalance]").val(); // 현재 접속해있는 member_id
let nowBtn = "memberInfo"; // 화면에 보여주고있는 버튼
let marketingSave;

/** 검색조건 세팅 */
let start="";
let end=toStringByFormatting(new Date(), '-');
$('#detailStartDt').datepicker('setDate', null);
$('#detailEndDt').datepicker('setDate', null);

/** memeber type에 의해서 바뀌는 화면 */
$(document).ready(function() {
    if(dtype == "AP") {
        $('#dtypeDiv').load('./ap?id=' + memberId + ' #APDiv', function () {
            let deleted = $("td[id=inputDeleted]").text();
            if(deleted == '탈퇴회원') {
                let appendDeleted = '<button type="button" data-role="statusDeleted" class="btn btn-outline-dark">정상</button>'
                $("td[id=inputDeleted]").append(appendDeleted);
            }
        });
    } else if(dtype == "HH") {
        $('#dtypeDiv').load('./hh?id='+ memberId +' #HHDiv', function () {
            let deleted = $("td[id=inputDeleted]").text();
            if(deleted == '탈퇴회원') {
                let appendDeleted = '<button type="button" data-role="statusDeleted" class="btn btn-outline-dark">정상</button>'
                $("td[id=inputDeleted]").append(appendDeleted);
            }
        });
    }
});

/** AP_memberinfo_수정하기 버튼 */
$(document).on('click', '#apModify', function () {
    marketingSave = $('#inputMarketing').text();
    let name = $("td[id=inputName]").text();
    let phone = $("td[id=inputPhone]").text();
    let marketing = $('#inputMarketing').text();
    let restricted = $('#inputRestricted').text();
    let findingJob = $('#inputFindingJob').text();
    let private = $('#inputPrivate').text();
    let holiday = $('#inputExceptHoliday').text();
    let time = $('#inputTime').text();
    $('td[name=inputChange]').empty();
    $('#eventDiv').empty();
    $("#eventDiv").append('<button class="btn btn-primary" id="apSave">저장하기</button>');
    $('#inputName').append('<input class="form-control" id="inputboxName" name="name" value="' + name +'">');
    $('#inputPhone').append('<input class="form-control" name="phone" value="'+ phone +'">');

    if(marketing == 0){
        $('#inputMarketing').append('<select class="form-control" name="marketing" id="marketing"><option value="0">N</option><option value="1">Y</option></select>');
    } else {
        $('#inputMarketing').append('<select class="form-control" name="marketing" id="marketing"><option value="1">Y</option><option value="0">N</option></select>');
    }

    if(restricted == 0){
        $('#inputRestricted').append('<select class="form-control" name="restricted" id="restricted"><option value="0">N</option><option value="1">Y</option></select>');
    } else {
        $('#inputRestricted').append('<select class="form-control" name="restricted" id="restricted"><option value="1">Y</option><option value="0">N</option></select>');
    }

    if(findingJob == 0){
        $('#inputFindingJob').append('<select class="form-control" name="findingJob" id="findingJob"><option value="0">N</option><option value="1">Y</option></select>');
    } else {
        $('#inputFindingJob').append('<select class="form-control" name="findingJob" id="findingJob"><option value="1">Y</option><option value="0">N</option></select>');
    }

    if(private == 0){
        $('#inputPrivate').append('<select class="form-control" name="private" id="private"><option value="0">N</option><option value="1">Y</option></select>');
    } else {
        $('#inputPrivate').append('<select class="form-control" name="private" id="private"><option value="1">Y</option><option value="0">N</option></select>');
    }

    if(holiday == 0){
        $('#inputExceptHoliday').append('<select class="form-control" name="holiday" id="holiday"><option value="0">N</option><option value="1">Y</option></select>');
    } else {
        $('#inputExceptHoliday').append('<select class="form-control" name="holiday" id="holiday"><option value="1">Y</option><option value="0">N</option></select>');
    }

    if(time == 'ALWAYS'){
        $('#inputTime').append('<select class="form-control" name="time" id="time"><option>상시</option><option VALUE="EVENING">18시~22시</option></select>');
    } else {
        $('#inputTime').append('<select class="form-control" name="time" id="time"><option>18시~22시</option><option VALUE="ALWAYS">상시</option></select>');
    }
});

/** AP_memberinfo 수정사항 저장하기 버튼 */
$(document).on('click', '#apSave', function () {
    let name = $('input[name=name]').val();
    let phone = $('input[name=phone]').val();
    let marketing = $("select[name=marketing]").val();
    let restricted = $("select[name=restricted]").val();
    let findingJob = $("select[name=findingJob]").val();
    let private = $("select[name=private]").val();
    let holiday = $("select[name=holiday]").val();
    let time = $("select[name=time]").val();

    let url = "";
    if(marketing == marketingSave) {
        url = "/bo/management/complaint/member/detail/update";
    } else {
        url = "/bo/management/complaint/memberMarketing/detail/update";
    }

    let params = {
        id: memberId,
        name: name,
        phone: phone,
        agreeMarketing: marketing,
        resumeRestricted: restricted,
        findingJob: findingJob,
        isPrivateAgreement: private,
        contactExceptHoliday: holiday,
        contactableTime: time
    };

    if(!confirm("수정하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: url,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("수정되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
});


/** HH MemberInfo 수정사항 저장하기 버튼 */
$(document).on('click', '#hhSave', function () {
    let name = $('#inputboxName').val();
    let email = $('#inputboxEmail').val();
    let agreeMarketing = $('#marketing').val();
    let sfCeoName = $('#inputboxSfCeoName').val();
    let sfName = $('#inputboxSfName').val();
    let sfPhone = $('#inputboxSfPhone').val();
    let sfUrl = $('#inputboxSfHomepageUrl').val();

    let url = "";
    if(agreeMarketing == marketingSave) {
        url = "/bo/management/complaint/hhmember/detail/update";
    } else {
        url = "/bo/management/complaint/hhmemberMarketing/detail/update";
    }

    let params = {
        id: memberId,
        name: name,
        email: email,
        agreeMarketing: agreeMarketing,
        sfCeoName: sfCeoName,
        sfName: sfName,
        sfPhone: sfPhone,
        sfHomepageUrl: sfUrl,
    };

    if(!confirm("수정하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: url,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("수정되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
});

/** HH_memberinfo_수정하기 버튼 */
$(document).on('click', '#hhModify', function () {
    marketingSave = $('#inputMarketing').text();
    let email = $('#inputEmail').text();
    let name = $("td[id=inputName]").text();
    let marketing = $('#inputAgreeMarketing').text();
    let sfName = $('#inputSfName').text();
    let sfCeoName = $('#inputSfCeoName').text();
    let sfPhone = $('#inputSfPhone').text();
    let sfUrl = $('#inputSfHomepageUrl').text();


    $('td[name=inputChange]').empty();
    $('#eventDiv').empty();
    $("#eventDiv").append('<button class="btn btn-primary" id="hhSave">저장하기</button>');
    $('#inputEmail').append('<input id="inputboxEmail" name="inputboxEmail" value="' + email +'">');
    $('#inputName').append('<input id="inputboxName" name="inputboxName" value="' + name +'">');
    $('#inputSfName').append('<input id="inputboxSfName" name="inputboxSfName" value="' + sfName +'">');
    $('#inputSfCeoName').append('<input id="inputboxSfCeoName" name="inputboxSfCeoName" value="' + sfCeoName +'">');
    $('#inputSfPhone').append('<input id="inputboxSfPhone" name="inputboxSfPhone" value="' + sfPhone +'">');
    $('#inputSfHomepageUrl').append('<input id="inputboxSfHomepageUrl" name="inputboxSfHomepageUrl" value="' + sfUrl +'">');

    if(marketing == 0){
        $('#inputMarketing').append('<select name="marketing" id="marketing"><option value="0">N</option><option value="1">Y</option></select>');
    } else {
        $('#inputMarketing').append('<select name="marketing" id="marketing"><option value="1">Y</option><option value="0">N</option></select>');
    }

});

/** memberinfo(회원정보) 버튼 클릭시 */
$(document).on('click', '#memberInfo',function() {
    nowBtn = "memberInfo"
    if(dtype == "AP"){
        if(($('#inputboxName').prop('tagName')) == 'INPUT') {
            let name = $('#inputboxName').val()
            let phone = $('input[name=phone]').val();
            let marketing = $('#inputMarketing').text();
            let restricted = $('#inputRestricted').text();
            let findingJob = $('#inputFindingJob').text();
            let private = $('#inputPrivate').text();
            let exceptHoliday = $('#inputExceptHoliday').text();
            let time = $('#inputTime').text();

            $('td[name=inputChange]').empty();
            $('#inputName')[0].innerText = name;
            $('#inputPhone')[0].innerText = phone;
            $('#inputMarketing')[0].innerText = marketing;
            $('#inputRestricted')[0].innerText = restricted;
            $('#inputFindingJob')[0].innerText = findingJob;
            $('#inputPrivate')[0].innerText = private;
            $('#inputExceptHoliday')[0].innerText = exceptHoliday;
            $('#inputTime')[0].innerText = time;
        }
        if(!$('#tableDiv').is(':visible'))
        {
            $('#selectDiv').hide()
            $('#searchDiv').hide();
            $('#searchDt').hide();
            $('#datatable_wrapper').remove();
            $('#eventDiv').empty();
            $("#eventDiv").append('<button class="btn btn-primary" id="apModify" data-role="apModify">수정하기</button>');
            $('#tableDiv').show();
            $('#rewardDiv').hide();
        }
    }
    if(dtype == "HH") {
        nowBtn = "memberInfo"
        if(($('#inputboxName').prop('tagName')) == 'INPUT') {
            let name = $('#inputboxName').val();
            let email = $('#inputboxEmail').val();
            let agreeMarketing = $('#marketing').val();
            let sfCeoName = $('#inputboxSfCeoName').val();
            let sfName = $('#inputboxSfName').val();
            let sfPhone = $('#inputboxSfPhone').val();
            let sfUrl = $('#inputboxSfHomepageUrl').val();

            $('td[name=inputChange]').empty();
            $('#inputName')[0].innerText = name;
            $('#inputEmail')[0].innerText = email;
            $('#inputAgreeMarketing')[0].innerText = agreeMarketing;
            $('#inputSfCeoName')[0].innerText = sfCeoName;
            $('#inputSfName')[0].innerText = sfName;
            $('#inputSfPhone')[0].innerText = sfPhone;
            $('#inputSfHomepageUrl')[0].innerText = sfUrl;
        }
        if(!$('#tableDiv').is(':visible'))
        {
            $('#rewardDiv').hide();
            $('#selectDiv').hide()
            $('#searchDiv').hide();
            $('#searchDt').hide();
            $('#datatable_wrapper').remove();
            $('#eventDiv').empty();
            $("#eventDiv").append('<button class="btn btn-primary" id="hhModify">수정하기</button>');
            $('#tableDiv').show();
        }
    }
});

/** resumeInfo(이력서정보) 버튼 클릭시 */
$(document).on('click', '#resumeInfo',function() {

    nowBtn = "resumeInfo"
    let datatableDiv = '<table class="table" id="datatable"></table>'
    let th_table = '<thead><tr><th>id</th><th>등록일시</th><th>구분</th><th>이력서 제목</th><th>비고</th></tr></thead>';
    let option = '<option value="">전체</option><option value="representation">기본이력서</option><option value="applicant">지원이력서</option><option value="other">일반</option>'
    hideContent(datatableDiv, th_table, option)
    $('#rewardDiv').hide();
    let params = {
        startDate: start,
        endDate: end
    };

    $("#datatable").DataTable({
        destroy: true,
        searching: false,
        ordering: false,
        info: false,
        ajax: {
            url: "/bo/management/complaint/member/resume/list/" + memberId,
            data: params,
            dataSrc: function (data) {
                return data.data;
            }
        },
        language: {
            emptyTable: "데이터가 없습니다.",
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            info: "현재 _START_ - _END_ / _TOTAL_건",
            infoEmpty: "데이터 없음",
            infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
            search: "",
            zeroRecords: "일치하는 데이터가 없습니다.",
            loadingRecords: "로딩중...",
            processing: "잠시만 기다려 주세요.",
            paginate: {
                next: "다음",
                previous: "이전",
            },
        },
        columns: [
            {"data": "id"},
            {"data": "createdAt"},
            {
                "data": "title",
                "render": function (data, type, row) {
                    if(data) {
                        if(row.representation == '1'){
                            return "기본이력서"
                        }else {
                            return "지원이력서"
                        }
                    } else {
                        return "일반"
                    }
                }
            },
            {
              "data": "title",
              "render": function (data, type, row) {
                  if(data) {
                      if(row.representation == '1') {
                          return applicantResumeTitleChange(row.name, row.totalCareer, row.birth, row.lastCareer, row.lastAcademic);
                      } else {
                          return row.createdAtFormat + "_" + data
                      }
                  } else {
                      return applicantResumeTitleChange(row.name, row.totalCareer, row.birth, row.lastCareer, row.lastAcademic);
                  }
              }
            },
            {
                "data": "id",
                "render": function (data) {
                    return "<a style='font-weight: bold;' href='/bo/management/operation/resume/status/" + data + "'>" + "조회" + "</a>";
                }
            },
        ],
        columnDefs: [
            {
                "targets": [4],
            }
        ]
    });

});

/** AP채용정보 버튼 클릭시 */
$(document).on('click', '#APPosition',function() {
    nowBtn = "APPosition"
    let datatableDiv = '<table class="table" id="datatable"></table>'
    let th_table = '<thead><tr><th>No.</th><th>지원일시</th><th>구분</th><th>공고제목</th><th>접수탭 상태</th><th>전형결과</th></tr></thead>';
    let option = '<option value="">전체</option><option value="1">포지션제안</option><option value="2">직접지원</option><option value="3">수동입력</option>';
    hideContent(datatableDiv, th_table, option);
    $('#rewardDiv').hide();
    let params = {
        startDate: start,
        endDt: end
    };

    $("#datatable").DataTable({
        destroy: true,
        searching: false,
        ordering: false,
        info: false,
        ajax: {
            data: params,
            url: "/bo/management/complaint/member/position/list/" + memberId,
            dataSrc: function(data) {
                return data.data;
            }
        },
        language: {
            emptyTable: "데이터가 없습니다.",
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            info: "현재 _START_ - _END_ / _TOTAL_건",
            infoEmpty: "데이터 없음",
            infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
            search: "",
            zeroRecords: "일치하는 데이터가 없습니다.",
            loadingRecords: "로딩중...",
            processing: "잠시만 기다려 주세요.",
            paginate: {
                next: "다음",
                previous: "이전",
            },
        },
        columns: [
            {"data": "id"},
            {"data": "poaCreatedAt", "defaultContent": ""},
            {
                "data" : "regPath", "defaultContent": "",
                "render" : function (data) {
                    if(data == "1") {
                        return '포지션제안'
                    } else if(data == '2') {
                        return '직접지원'
                    } else if(data == '3') {
                        return '수동입력'
                    } else {
                        return '-'
                    }
                }
            },
            {"data" : "title"},
            {"data" : "receiptTabStatus"},
            {"data" : "processResult"}
        ],
        columnDefs: [
            {
                "targets": [5],
            }
        ]
    });

});

/** HH채용정보 */
$(document).on('click', '#positionInfo',function() {
    nowBtn = "positionInfo"
    let datatableDiv = '<table class="table" id="datatable"></table>'
    let th_table = '<thead><tr><th>No.</th><th>등록 일시</th><th>공고제목</th><th>구분</th><th>상태</th><th>종료(마감)일시</th></tr></thead>';
    let option = '<option value="ALL">전체</option><option value="END">종료</option><option value="DOING">진행중</option><option value="CLOSE">마감</option>'
    hideContent(datatableDiv, th_table, option)
    let params = {
        startDate: start,
        endDt: end
    };


    $("#datatable").DataTable({
        destroy: true,
        searching: false,
        info: false,
        ordering: false,
        ajax: {
            url: "/bo/management/complaint/member/position/info/" + memberId,
            data: params,
            dataSrc: function (data) {
                return data.data;
            }
        },
        language: {
            emptyTable: "데이터가 없습니다.",
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            info: "현재 _START_ - _END_ / _TOTAL_건",
            infoEmpty: "데이터 없음",
            infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
            search: "",
            zeroRecords: "일치하는 데이터가 없습니다.",
            loadingRecords: "로딩중...",
            processing: "잠시만 기다려 주세요.",
            paginate: {
                next: "다음",
                previous: "이전",
            },
        },
        columns: [
            {"data": "id"},
            {"data": "createdAt"},
            {
                "data": "title",
                "render": function (data, type, row) {
                    return "<a style='font-weight: bold;' href='/bo/management/operation/recruit/detail?id=" + row.id + "'>" + data + "</a>";
                }
            },
            {
                "data": "isCowork",
                "render": function (data, type, row) {
                    if(row.participantId == memberId) {
                        return "CO";
                    }
                    if(data == 0) {
                        return "단독"
                    } else {
                        return "PM"
                    }
                }
            },
            {"data": "status"},
            {"data": "endDate", "defaultContent": "-"}
        ],
        columnDefs: [
            {
                "targets": [5],
            }
        ]
    });

});

/** HH내 인재정보 */
$(document).on('click', '#HHHistory',function() {
    nowBtn = "HHHistory"
    let datatableDiv = '<table class="table" id="datatable"></table>'
    let th_table = '<thead><tr><th>No.</th><th>이름</th><th>AP ID</th><th>내인재 등록일시</th><th>등록방식</th><th>만료일</th></tr></thead>';
    let option = '<option value="ALL">전체</option><option value="REGISTER">인재등록</option><option value="TICKET">열람권</option><option value="TEMP">임시등록</option><option value="POINT">포인트</option>'
    hideContent(datatableDiv, th_table, option)

    let params = {
        startDate: start,
        endDate: end
    };


    $("#datatable").DataTable({
        destroy: true,
        searching: false,
        info: false,
        ordering: false,
        ajax: {
            url: "/bo/management/complaint/member/hh/history/" + memberId,
            data: params,
            dataSrc: function (data) {
                return data.data;
            }
        },
        language: {
            emptyTable: "데이터가 없습니다.",
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            info: "현재 _START_ - _END_ / _TOTAL_건",
            infoEmpty: "데이터 없음",
            infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
            search: "",
            zeroRecords: "일치하는 데이터가 없습니다.",
            loadingRecords: "로딩중...",
            processing: "잠시만 기다려 주세요.",
            paginate: {
                next: "다음",
                previous: "이전",
            },
        },
        columns: [
            {"data": "id"},
            {"data": "name"},
            {
                "data": "resumeMemberId",
                "render": function (data){
                    return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + data + "'>" + data + "</a>"
                }
            },
            {"data": "createdAt"},
            {"data": "registerPathCd", "defaultContent": "-"},
            {"data": "expireAt"}
        ],
        columnDefs: [
            {
                "targets": [5],
            }
        ]
    });

});


/** 날짜 선택창에서 오늘 클릭시 */
$(document).on('click', '#today',function() {
    let current = toStringByFormatting(new Date(), '-');
    start = current;
    end = current;
    $('#detailStartDt').datepicker('setDate', start);
    $('#detailEndDt').datepicker('setDate', end);
})

/** 날짜 선택창에서 7일 클릭시 */
$(document).on('click', '#week',function() {
    const current = new Date();
    current.setDate(current.getDate() - 7);
    let lastWeek = toStringByFormatting(current, '-');
    start = lastWeek;
    end = toStringByFormatting(new Date(), '-');
    $('#detailStartDt').datepicker('setDate', start);
    $('#detailEndDt').datepicker('setDate', end);
})

/** 날짜 선택창에서 1개월 클릭시 */
$(document).on('click', '#month',function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#detailStartDt').datepicker('setDate', start);
    $('#detailEndDt').datepicker('setDate', end);
})

/** 날짜 선택창에서 3개월 클릭시 */
$(document).on('click', '#months',function() {
    const startCurrent = new Date();
    startCurrent.setMonth(startCurrent.getMonth() - 3);
    let startMonth = toStringByFormatting(startCurrent, '-');
    start = startMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#detailStartDt').datepicker('setDate', start);
    $('#detailEndDt').datepicker('setDate', end);
})

/** 날짜 선택창에서 전체 클릭시 */
$(document).on('click', '#all',function() {
    start="";
    end=toStringByFormatting(new Date(), '-');
    $('#detailStartDt').datepicker('setDate', null);
    $('#detailEndDt').datepicker('setDate', null);
})

/** 날짜 선택창에서 조회하기 버튼 클릭시 */
$(document).on('click', '#search',function() {
    let option = $('#divisionSelect').val();
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        selectChange(option);
    }
})


/** 비밀번호 초기화모달에서 변경버튼 클릭시 */
$(document).on("click", "#passwordChange", function(){
    let password = $('#password').val();
    let passwordCheck = $('#passwordCheck').val();
    if(password == '' || password == null) {
        alert("비밀번호를 입력해주세요.")
        return false;
    }

    let params = {
        password: password
    };

    if(!confirm("비밀번호를 변경하시겠습니까?")) {
        return false;
    } else if(password != passwordCheck){
        alert("두 비밀번호의 값이 다릅니다.")
    } else {
        $.ajax({
            url: "/bo/management/complaint/member/detail/password/update/" + memberId,
            type: "post",
            data: params,
            success: function () {
                alert("비밀번호가 변경되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
});

/**이용 정지 버튼 클릭시*/
$(document).on("click", "#isStop", function(){
    if(!confirm("해당 회원을 이용정지하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/management/complaint/member/detail/isStop/update/" + memberId,
            type: "post",
            success: function () {
                alert("이용이 정지되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }

});

/**이용 정지 버튼 클릭시*/
$(document).on("click", "#isStopNo", function(){
    if(!confirm("해당 회원을 이용정지하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/management/complaint/member/detail/isStopNo/update/" + memberId,
            type: "post",
            success: function () {
                alert("이용정지가 해제되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }

});

/**임시회원 삭제 버튼 클릭시*/
$(document).on("click", "#isTemp", function(){
    let isTemp = $("input[name=memberDetailIsTemp]").val(); // 현재 접속해있는 member의 정지여부
    if(!confirm("임시회원을 삭제하시겠습니까?")) {
        return false;
    } else if(isTemp == '0'){
        alert("해당 회원은 임시회원이 아닙니다.")
    } else {
        $.ajax({
            url: "/bo/management/complaint/member/detail/isTemp/delete/" + memberId,
            type: "post",
            success: function () {
                alert("임시회원이 삭제되었습니다.");
                location.href = "/bo/management/complaint/member";
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }

});

/**강제탈퇴 버튼 클릭시*/
$(document).on("click", "#deleted", function(){
    let deleted = $("input[name=memberDetailDeleted]").val(); // 현재 접속해있는 member의 정지여부

    if(!confirm("강제탈퇴를 하시겠습니까?")) {
        return false;
    } else if(deleted == '1'){
        alert("탈퇴된 회원입니다.")
    } else {
        $.ajax({
            url: "/bo/management/complaint/member/detail/deleted/update/" + memberId,
            type: "post",
            success: function () {
                alert("강제탈퇴 되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }

});

/** 이력서 정보, 채용정보. 리워드 정보 select태그 option에 무엇이 들었는지 바뀔때마다 값을 table 생성 함수에 보내줌 */
$(document).on("change", "select[name='divisionSelect']", function(){
    let option = $('#divisionSelect').val();
    selectChange(option);
});

/** 시작 날짜가 바뀌면 값 저장 */
$(document).on("change", "input[name='detailStartDt']", function (){
    start = $(this).val();
    $('button[name=dateColorChange]').removeClass('btn-primary');
    $('button[name=dateColorChange]').addClass('btn-secondary');
})

/** 종료 날짜가 바뀌면 값 저장 */
$(document).on("change", "input[name='detailEndDt']", function (){
    end = $(this).val();
    $('button[name=dateColorChange]').removeClass('btn-primary');
    $('button[name=dateColorChange]').addClass('btn-secondary');
})
/** select값이 바뀌면 알맞은 table로 table 재생성 */
function selectChange(option) {
    let datatableDiv = '';
    let th_table = '';
    let url = '';
    let params = '';
    let columns = '';
    let targets = '';
    if(nowBtn == "resumeInfo")
    {
        params = {
            representation:option ,
            startDate: start,
            endDate: end,
        };
        columns = [
            {"data": "id"},
            {"data": "createdAt"},
            {
                "data": "title",
                "render": function (data, type, row) {
                    if(data) {
                        if(row.representation == '1'){
                            return "기본이력서"
                        }else {
                            return "지원이력서"
                        }
                    } else {
                        return "일반"
                    }
                }
            },
            {
                "data": "title",
                "render": function (data, type, row) {
                    if(data) {
                        if(row.representation == '1') {
                            return applicantResumeTitleChange(row.name, row.totalCareer, row.birth, row.lastCareer, row.lastAcademic);
                        } else {
                            return row.createdAtFormat + "_" + data
                        }
                    } else {
                        return applicantResumeTitleChange(row.name, row.totalCareer, row.birth, row.lastCareer, row.lastAcademic);
                    }
                }
            },
            {
                "data": "id",
                "render": function (data) {
                    return "<a style='font-weight: bold;' href='/bo/management/operation/resume/status/" + data + "'>" + "조회" + "</a>";
                }
            },
        ];
        targets = [4];
        datatableDiv = '<table class="table" id="datatable"></table>'
        th_table = '<thead><tr><th>id</th><th>등록일시</th><th>구분</th><th>이력서 제목</th><th>비고</th></tr></thead>';
        url = "/bo/management/complaint/member/resume/list/option/" + memberId;
    } else if(nowBtn == "APPosition") {
        params = {
            startDate: start,
            endDt: end,
            regPath: option
        };
        columns = [
            {"data": "id"},
            {"data": "poaCreatedAt", "defaultContent": ""},
            {
                "data" : "regPath", "defaultContent": "",
                "render" : function (data) {
                    if(data == "1") {
                        return '포지션제안'
                    } else if(data == '2') {
                        return '직접지원'
                    } else if(data == '3') {
                        return '수동입력'
                    } else {
                        return '-'
                    }
                }
            },
            {"data" : "title"},
            {"data" : "receiptTabStatus"},
            {"data" : "processResult"}
        ]
        targets = [5];
        datatableDiv = '<table class="table" id="datatable"></table>'
        th_table = '<thead><tr><th>No.</th><th>지원일시</th><th>구분</th><th>공고제목</th><th>접수탭 상태</th><th>전형결과</th></tr></thead>';
        url = "/bo/management/complaint/member/position/list/" + memberId;
    } else if(nowBtn == "positionInfo") {
        params = {
            startDate: start,
            endDt: end,
            status: option
        };
        columns = [
            {"data": "id"},
            {"data": "createdAt"},
            {
                "data": "title",
                "render": function (data, type, row) {
                    return "<a style='font-weight: bold;' href='/bo/management/operation/recruit/detail?id=" + row.id + "'>" + data + "</a>";
                }
            },
            {
                "data": "isCowork",
                "render": function (data, type, row) {
                    if(row.participantId) {
                        return "CO";
                    }
                    if(data == 0) {
                        return "단독"
                    } else {
                        return "PM"
                    }
                }
            },
            {"data": "status"},
            {"data": "endDate", "defaultContent": "-"}
        ];
        targets = [5];
        datatableDiv = '<table class="table" id="datatable"></table>'
        th_table = '<thead><tr><th>No.</th><th>등록 일시</th><th>공고제목</th><th>구분</th><th>상태</th><th>종료(마감)일시</th></tr></thead>';
        url = "/bo/management/complaint/member/position/info/option/" + memberId;
    } else if(nowBtn == "HHHistory") {
        params = {
            startDate: start,
            endDate: end,
            registerPathCd: option
        };
        columns = [
            {"data": "id"},
            {"data": "name"},
            {
                "data": "resumeMemberId",
                "render": function (data){
                    return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + data + "'>" + data + "</a>"
                }
            },
            {"data": "createdAt"},
            {"data": "registerPathCd", "defaultContent": "-"},
            {"data": "expireAt"}
        ];
        targets = [5];
        datatableDiv = '<table class="table" id="datatable"></table>'
        th_table = '<thead><tr><th>No.</th><th>이름</th><th>AP ID</th><th>내인재 등록일시</th><th>등록방식</th><th>만료일</th></tr></thead>';
        url = "/bo/management/complaint/member/hh/history/option/" + memberId;
    }
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        $('#tableDiv').hide();
        $('#datatable_wrapper').remove();
        $("#datatableDiv").append(datatableDiv);
        $("#datatable").append(th_table);

        $("#datatable").DataTable({
            destroy: true,
            ordering: false,
            searching: false,
            info: false,
            ajax: {
                url: url,
                data: params,
                dataSrc: function (data) {
                    return data.data;
                }
            },
            language: {
                emptyTable: "데이터가 없습니다.",
                lengthMenu: "페이지당 _MENU_ 개씩 보기",
                info: "현재 _START_ - _END_ / _TOTAL_건",
                infoEmpty: "데이터 없음",
                infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
                search: "",
                zeroRecords: "일치하는 데이터가 없습니다.",
                loadingRecords: "로딩중...",
                processing: "잠시만 기다려 주세요.",
                paginate: {
                    next: "다음",
                    previous: "이전",
                },
            },
            columns: columns,
            columnDefs: [
                {
                    "targets": targets,
                }
            ]
        });
    }
}

/** 달력 초기설정 */
function toStringByFormatting(source, delimiter = '-') {
    let year = source.getFullYear();
    let month = source.getMonth() + 1 ;
    let day = source.getDate() ;
    return [year, month >= 10  ? month : '0' + month, day >= 10 ? day : '0' + day].join(delimiter);
}

/** 이력서 정보, 채용정보, 리워드 설정 겹치는 값 */
function hideContent(datatableDiv, th_table, option) {
    $('#divisionSelect').empty()
    $('#selectDiv').show()
    $('#searchDt').show();
    $('#searchDiv').show();
    $('#tableDiv').hide();
    $('#datatable_wrapper').remove();
    $("#datatableDiv").append(datatableDiv)
    $("#datatable").append(th_table);
    $('#eventDiv').empty();
    $('#divisionSelect').append(option)
    $('#rewardDiv').hide();
    $('#balance').empty();


    $(".form-datepicker").datepicker({
        monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
        showMonthAfterYear: true,
        showOtherMonths: true,
        dateFormat: "yy-mm-dd",
        gotoCurrent: true,
        beforeShow: function beforeShow(input, inst) {
            $("#ui-datepicker-div").addClass("datepicker-box");
        }
    })
}

/** 버튼 색 변경*/
let dateBtnId = "all";
$(document).on("click", "button[name='dateColorChange']", function(){
    if(dateBtnId != null || dateBtnId == ""){
        document.getElementById(dateBtnId).classList.remove('btn-primary');
        document.getElementById(dateBtnId).classList.add('btn-secondary');
    }
    $(this).removeClass('btn-secondary');
    $(this).addClass('btn-primary');
    dateBtnId = $(this).attr('id');
});

let menuBtnId = "memberInfo";
$(document).on("click", "button[name='menuColorChange']", function(){
    if(menuBtnId != null || menuBtnId == ""){
        document.getElementById(menuBtnId).classList.remove('btn-primary');
        document.getElementById(menuBtnId).classList.add('btn-secondary');
    }
    $(this).removeClass('btn-secondary');
    $(this).addClass('btn-primary');
    menuBtnId = $(this).attr('id');
});

function priceToString(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

/** 임동범  -> 임** 로 변환 */
function delNameChange(name) {
    let size = name.length
    let nameTemp = name[0]
    for(i = 0; i < size - 1; i++) {
        nameTemp = nameTemp + "*";
    }
    name = nameTemp;
    return name;
}

function applicantResumeTitleChange(name, totalCareer, birth, lastCareer, lastAcademic) {
    let temp;
    let resumeTitle = name + "_"
    if(!totalCareer) {
        resumeTitle = resumeTitle + "0년0개월"
    } else {
        temp = totalCareer/12
        temp = (temp + "").split(".");
        resumeTitle = resumeTitle + temp[0] + "년" + (totalCareer - temp[0] * 12) + "개월_"
    }
    let age = ""
    if(!birth) {
        age = 0
        temp = "0000"
    } else {
        temp = birth.substring(0, 4);
        const today = new Date();
        const birthDate = new Date(temp);
        age = today.getFullYear()
            - birthDate.getFullYear()
            + 1;
    }
    if(!lastCareer) {
        resumeTitle = resumeTitle + temp + "(" + age + ")"
    } else {
        resumeTitle = resumeTitle + temp + "(" + age + ")_" + lastCareer
    }
    if(!lastAcademic) {
    } else {
        resumeTitle = resumeTitle + "_" + lastAcademic
    }
    return resumeTitle;
}

$(document).on('click', 'button[data-role="statusDeleted"]', function () {
    let params = {
        id: memberId
    };
    if(!confirm("정상복귀하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/management/complaint/member/detail/restore",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("정상복귀되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
})