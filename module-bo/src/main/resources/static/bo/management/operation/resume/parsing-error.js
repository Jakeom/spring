let option = "ALL"
$(document).ready(function () {
    let params = {
        startDate: start,
        endDate: end,
    };
    let url = "/bo/management/operation/resume/parsing-error/list"
    parsingErrorTable(url, params)

})

let start = "";
let end = toStringByFormatting(new Date(), '-');
$('#memberStartDt').datepicker('setDate', null);
$('#memberEndDt').datepicker('setDate', null);

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
    $('#memberStartDt').datepicker('setDate', null);
    $('#memberEndDt').datepicker('setDate', null);
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

$("button[data-role=moveStatus]").click(function () {
    window.location.href = "/bo/management/operation/resume/status";
});

$("button[data-role=moveRequest]").click(function () {
    window.location.href = "/bo/management/operation/resume/request-regist-resume";
});

$("button[data-role=moveForm]").click(function () {
    window.location.href = "/bo/management/operation/resume/resume-form";
});

$(document).ready(function() {
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

function selectChange(option) {
    let selectOption = $("#personalInfo").val()
    let name = "";
    let phone = "";
    let email = "";
    if(selectOption == "name") {
        name = $("#inputSelect").val();
    } else if(selectOption == "phone") {
        phone = $("#inputSelect").val();
    } else if(selectOption == "email") {
        email = $("#inputSelect").val();
    }
    let inputName = $("#inputAdminName").val();
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        inputNameOption: inputName,
        acceptStatus: option
    };
    let url = "/bo/management/operation/resume/parsing-error/list/option"
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        $("#datatable").DataTable().clear().destroy();
        parsingErrorTable(url, params)
    }
};

$("button[data-role=search]").click(function() {
    let selectOption = $("#personalInfo").val()
    let name = "";
    let phone = "";
    let email = "";
    if(selectOption == "name") {
        name = $("#inputSelect").val();
    } else if(selectOption == "phone") {
        phone = $("#inputSelect").val();
    } else if(selectOption == "email") {
        email = $("#inputSelect").val();
    }
    let inputName = $("#inputAdminName").val();
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        inputNameOption: inputName,
        acceptStatus: option
    };
    let url = "/bo/management/operation/resume/parsing-error/list/option"
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        $("#datatable").DataTable().clear().destroy();
        parsingErrorTable(url, params)
    }
})

$(document).on('click', 'a[data-role="modalbtn"]',function() {
    $('#modalContent').empty();
    let btnId = $(this).attr('id');
    if(btnId != 'null') {
        $('#modalContent').append(btnId);
    } else{
        $('#modalContent').append(' ');
    }
});

let btnId ='all';
$('button[name=colorChange]').click(function() {
    if(btnId != null || btnId == ""){
        document.getElementById(btnId).classList.remove('btn-primary');
        document.getElementById(btnId).classList.add('btn-secondary');
    }
    $(this).removeClass('btn-secondary');
    $(this).addClass('btn-primary');
    btnId = $(this).attr('id');
});

function parsingErrorTable(url, params) {
    $("#datatable").DataTable({
        searching: false,
        ordering: false,
        info: false,
        ajax:{
            url:url,
            data:params,
            dataSrc: function(data){
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
            {"data": "createdAt"},
            {
                "data": "commonFileList",
                render: function (data) {
                    if(data[0]) {
                        return "<a class='font-weight-bold text-primary' type='file' href='" + data[0].url + "'>받기</a>"
                    } else {
                        return "-"
                    }
                }
            },
            {
                "data" : "errorType",
                "render" : function(data, type, row){
                    if(data == 'ETC') {
                        return "<a type='button' class='font-weight-bold text-primary' data-role='modalbtn' id='" + row.directInput + "' data-toggle='modal' data-target='#errorModal'>보기</a>"
                    }
                    return "<a type='button' class='font-weight-bold text-primary' data-role='modalbtn' id='" + data + "' data-toggle='modal' data-target='#errorModal'>보기</a>";
                }
            },
            {
                "data" : "acceptStatus",
                "render" : function(data){
                    if(data == 'REGIST_IMPOSSIBLE') {
                        return "등록불가";
                    } else if(data == 'REGIST_COMPLETE') {
                        return "완료"
                    } else {
                        return "처리중"
                    }
                }
            },
            {"data": "backofficeAdminName", "defaultContent": "-"},
            {"data": "completedAt", "defaultContent": "-"},
            {
                "data": "email",
                "render": function (data, type, row) {
                    if(row.acceptStatus == 'ACCEPT_COMPLETE') {
                        return "<button type='button' class='btn btn-outline-danger' data-role='sendButton'>발송</button> <input type='hidden' name='email' value='" + data +"'/><input type='hidden' name='id' value='" + row.id +"'/>"
                    } else {
                        return "-"
                    }
                }
            }
        ],
        columnDefs: [
            {
                "targets": [7],
            }
        ]
    })
}

$("input[data-role=enter]").keyup(function(e) {
    if (e.keyCode === 13) {
        document.getElementById('search').click();
    }
});

$(document).on("click", "button[data-role=sendButton]", function () {
    let email = $(this).closest("td").find("input[name=email]").val();
    let id = $(this).closest("td").find("input[name=id]").val();
    if(!confirm("확인을 누를시 신고자에게 메일이 발송됩니다.")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/management/operation/resume/parsing-error/sendEmail",
            type: "post",
            data: {
                email: email,
                id: id
            },
            success: function () {
                alert("발송되었습니다..");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
})