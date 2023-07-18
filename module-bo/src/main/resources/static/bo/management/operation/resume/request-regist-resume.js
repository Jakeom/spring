let option = "";
let start = "";
let end = toStringByFormatting(new Date(), '-');
$('#memberStartDt').datepicker('setDate', null);
$('#memberEndDt').datepicker('setDate', null);
$( document ).ready(function() {
    let params = {
        startDate: start,
        endDate: end,
    };
    requestRegistResumeTable(params, true);

    $('#divisionSelect').change(function() {
        option = $('#divisionSelect').val();
        selectChange(option);
    });

    $('#memberStartDt').change(function() {
        start = $('#memberStartDt').val()
        $('button[name=colorChange]').removeClass('btn-primary');
        $('button[name=colorChange]').addClass('btn-secondary');
    });
    $('#memberEndDt').change(function() {
        end = $('#memberEndDt').val()
        $('button[name=colorChange]').removeClass('btn-primary');
        $('button[name=colorChange]').addClass('btn-secondary');
    });
});

$(".form-datepicker").datepicker({
    minDate: 0,
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

function toStringByFormatting(source, delimiter = '-') {
    var year = source.getFullYear();
    var month = source.getMonth() + 1 ;
    var day = source.getDate() ;
    return [year, month >= 10  ? month : '0' + month, day >= 10 ? day : '0' + day].join(delimiter);
}

$("button[data-role=all]").click(function() {
    start="";
    end=toStringByFormatting(new Date(), '-');
    $('#memberStartDt').datepicker('setDate', start);
    $('#memberEndDt').datepicker('setDate', end);
})

$("button[data-role=today]").click(function() {
    let current = toStringByFormatting(new Date(), '-');
    start = current;
    end = current;
    $('#memberStartDt').datepicker('setDate', start);
    $('#memberEndDt').datepicker('setDate', end);
})

$("button[data-role=week]").click(function() {
    const current = new Date();
    current.setDate(current.getDate() - 7);
    let lastWeek = toStringByFormatting(current, '-');
    start = lastWeek;
    end = toStringByFormatting(new Date(), '-');
    $('#memberStartDt').datepicker('setDate', start);
    $('#memberEndDt').datepicker('setDate', end);
})

$("button[data-role=month]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#memberStartDt').datepicker('setDate', start);
    $('#memberEndDt').datepicker('setDate', end);
})

function selectChange(option) {
    let selectOption = $("#personalInfo").val()
    let name = "";
    let phone = "";
    let email = "";
    let inputAdminName = "";
    if(selectOption == "name") {
        if($("#inputSelect").val()){
            name = $("#inputSelect").val();
        }
    } else if(selectOption == "phone") {
        if($("#inputSelect").val()){
            phone = $("#inputSelect").val();
        }
    } else if(selectOption == "email") {
        if($("#inputSelect").val()){
            email = $("#inputSelect").val();
        }
    }
    if($("#inputAdminName").val()) {
        inputAdminName =  $("#inputAdminName").val();
    }
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        backofficeAdminNameOption: inputAdminName,
        acceptStatus: option
    };
    requestRegistResumeTable(params)
};


$("button[data-role=search]").click(function() {
    let selectOption = $("#personalInfo").val()
    let name = "";
    let phone = "";
    let email = "";
    let inputAdminName = "";
    if(selectOption == "name") {
        if($("#inputSelect").val()){
            name = $("#inputSelect").val();
        }
    } else if(selectOption == "phone") {
        if($("#inputSelect").val()){
            phone = $("#inputSelect").val();
        }
    } else if(selectOption == "email") {
        if($("#inputSelect").val()){
            email = $("#inputSelect").val();
        }
    }
    if($("#inputAdminName").val()) {
        inputAdminName =  $("#inputAdminName").val();
    }
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        backofficeAdminNameOption: inputAdminName,
        acceptStatus: option
    };

    requestRegistResumeTable(params)
})


function requestRegistResumeTable(params, documentStart) {
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        if(!documentStart){
            $("#datatable").DataTable().clear().destroy();
        }
        $("#datatable").DataTable({
            destroy: true,
            ordering: false,
            searching: false,
            info: false,
            serverSide: true,
            ajax:{
                url: "/bo/management/operation/resume/request_regist_resume/list-with-paging",
                data: params,
                type: "POST",
                dataSrc: function(data){
                    return data.data.data;
                },
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
                {"data": "createdAt"},
                {
                    "data": "pageUrl",
                    "render": function (data) {
                        if(data == "REGIST_APPLICANT") {
                            return "내 인재 등록";
                        } else {
                            return "등록대행";
                        }
                    }
                },
                {
                    "data": "commonFileList",
                    "render": function (data, type, row) {
                        if(row.agreeFileid) {
                            if(data[0]) {
                                return "<a class='font-weight-bold text-primary' type='file' href='" + data[0].url + "'>이력서</a>" + " <a class='font-weight-bold text-primary' type='file' href='" + row.agreeFileList[0].url + "'>동의서</a>"
                            } else {
                                return "-"
                            }
                        } else if(data[0]) {
                            return "<a class='font-weight-bold text-primary' type='file' href='" + data[0].url + "'>이력서</a>";
                        } else {
                            return "-"
                        }
                    }
                },
                {"data": "acceptStatusCode"},
                {"data": "backofficeAdminName", "defaultContent": "-"},
                {"data": "completedAt", "defaultContent": "-"},
                {
                    "data": "acceptStatus",
                    "render": function (data, type, row) {
                        if(data == "ACCEPT_COMPLETE") {
                            return '<button name="'+ row.id + '" data-toggle="modal" data-role="nameCheck" data-target="#refuseModal" class="btn btn-outline-secondary">불가</button> '
                                    + '<button data-role="resumeWrite" name="' + row.id + '"class="btn btn-outline-danger">등록</button>'
                        } else {
                            return "-"
                        }
                    }
                }
            ],
            columnDefs: [
                {
                    "targets": [8],

                }
            ]
        })
    }
}

$("button[data-role=moveStatus]").click(function () {
    window.location.href = "/bo/management/operation/resume/status";
});

$("button[data-role=moveError]").click(function () {
    window.location.href = "/bo/management/operation/resume/parsing-error";
});

$("button[data-role=moveForm]").click(function () {
    window.location.href = "/bo/management/operation/resume/resume-form";
});

let btnId = 'all';
$('button[name=colorChange]').click(function() {
    if(btnId != null || btnId == ""){
        document.getElementById(btnId).classList.remove('btn-primary');
        document.getElementById(btnId).classList.add('btn-secondary');
    }
    $(this).removeClass('btn-secondary');
    $(this).addClass('btn-primary');
    btnId = $(this).attr('id');
});

$("input[data-role=enter]").keyup(function(e) {
    if (e.keyCode === 13) {
        document.getElementById('search').click();
    }
});

let btnRefuseName;
$(document).on('click', 'button[data-role="nameCheck"]',function() {
    btnRefuseName = $(this).attr('name')
})

$(document).on('click', 'button[data-role="modalSave"]',function() {
    let params = {
        id: btnRefuseName,
        etc: $('#refuseContent').val(),
    };
    $.ajax({
        url: "/bo/management/operation/resume/request-regist-resumerefuse/update",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function () {
            alert("등록 불가 처리되었습니다.");
            location.reload();
        },
        error: function () {
            alert("ajax fail")
        }
    });
})

$(document).on("click", "[data-role=resumeWrite]", function () {
    location.href = "/bo/management/operation/resume/request-regist-resume/resume-write/" + $(this).attr("name")
})
