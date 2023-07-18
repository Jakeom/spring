let option = "ALL"
let start = "";
let end = toStringByFormatting(new Date(), '-');
$('#memberStartDt').datepicker('setDate', null);
$('#memberEndDt').datepicker('setDate', null);
$(document).ready(function () {
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

    let params = {
        startDate: start,
        endDate: end,
    };
    let url = "/bo/management/operation/resume/resume-form/list"
    resumeFormTable(url, params)
})

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


$(document).ready(function() {
    $('#divisionSelect').change(function() {
        option = $('#divisionSelect').val();
        selectChange(option);
    });
});

function selectChange(option) {
    let selectOption = $("#searchSelect").val()
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
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        status: option
    };
    let url = "/bo/management/operation/resume/resume-form/list/option"
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        $("#datatable").DataTable().clear().destroy();
        resumeFormTable(url, params)
    }
};


$("button[data-role=search]").click(function() {
    let selectOption = $("#searchSelect").val()
    let url = "/bo/management/operation/resume/resume-form/list/search";

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
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        status: option
    };
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else{
        $("#datatable").DataTable().clear().destroy();
        resumeFormTable(url, params)
    }
})



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



$(document).on('click', 'button[data-role="accept"]',function() {
    let url = "/bo/management/operation/resume/resume-form/accept/update"
    let btnName = $(this).attr('name')
    let params = {
        id: btnName
    };
    if(!confirm("완료 처리 하시겠습니까??")) {
        return false;
    } else {
        $.ajax({
            url: url,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("완료되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
});
let btnRefuseName;
$(document).on('click', 'button[data-role="nameCheck"]',function() {
    btnRefuseName = $(this).attr('name')
})

$(document).on('click', '#modalSave',function() {
    let url = "/bo/management/operation/resume/resume-form/refuse/update"
    let denyReason = $("#refuseContent").val()
    let params = {
        id: btnRefuseName,
        denyReason: denyReason
    };
    $.ajax({
        url: url,
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function () {
            alert("거절처리되었습니다.");
            location.reload();
        },
        error: function () {
            alert("ajax fail")
        }
    });
});

function resumeFormTable(url, params) {
    $("#datatable").DataTable({
        destroy: true,
        searching: false,
        ordering: false,
        info: false,
        ajax: {
            url: url,
            data: params,
            dataSrc: function (data) {
                return data.data;
            }
        },
        columns: [
            {"data": "id"},
            {"data": "name"},
            {"data": "createdAt"},
            {
                "data": "commonFileList",
                render: function (data) {
                    if(data[0]) {
                        return "<a class='font-weight-bold text-primary' type='file' href='" + data[0].url + "'>이력서</a>"
                    } else {
                        return "-"
                    }
                }
            },
            {"data": "statusCode"},
            {"data": "backofficeAdminName", "defaultContent": "-"},
            {"data": "completedAt", "defaultContent": "-"},
            {
                "data": "id",
                "render": function (data, type, row) {
                    if(row.status == "WAITING_CONFIRM"){
                        return "<button name=" + data + " data-toggle='modal' data-role='nameCheck' data-target='#refuseModal' class='btn btn-outline-info'>거절</button> <button name=" + data + " data-role='accept' class='btn btn-outline-danger'>승인</button>"
                    } else{
                        return "-";
                    }
                }
            },
            {"data": "denyReason", "defaultContent": "-"}
        ],
        columnDefs: [
            {
                "targets": [7],
            }
        ]
    })
}

$("button[data-role=moveStatus]").click(function () {
    window.location.href = "/bo/management/operation/resume/status";
});

$("button[data-role=moveError]").click(function () {
    window.location.href = "/bo/management/operation/resume/parsing-error";
});

$("button[data-role=moveRequest]").click(function () {
    window.location.href = "/bo/management/operation/resume/request-regist-resume";
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