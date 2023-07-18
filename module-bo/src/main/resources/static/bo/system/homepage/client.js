let option = "ALL"
let start = "";
let end = toStringByFormatting(new Date(), '-');
$('#clientStartDt').datepicker('setDate', null);
$('#clientEndDt').datepicker('setDate', null);

$(document).ready(function () {

    $.ajax({
        url: "/bo/system/homepage/client/wefirm/list",
        method: "get",
        datatype: "data",
    })
        .done(function(data){
            let wefirmList = data.data;
            for (let i = 0; i < wefirmList.length; i++) {
                let selectBox = '<option name="wefirmOption" value="'+ wefirmList[i].wefirmId + '">' + wefirmList[i].wefirmName + '</option>';
                $("#wefirmSelect").append(selectBox);
            }
        })
        .fail(function (xhr, status, errorThrown) {
            alert("*****ajax fail*****");
        })

    $('#clientStartDt').change(function() {
        start = $('#clientStartDt').val()
        $('button[name=colorChange]').removeClass('btn-primary');
        $('button[name=colorChange]').addClass('btn-secondary');
    });
    $('#clientEndDt').change(function() {
        end = $('#clientEndDt').val()
        $('button[name=colorChange]').removeClass('btn-primary');
        $('button[name=colorChange]').addClass('btn-secondary');
    });
    clientTable()
})

$("button[data-role=search]").click(function() {
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        clientTable()
    }
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

$("button[data-role=all]").click(function() {
    start="";
    end=toStringByFormatting(new Date(), '-');
    $('#clientStartDt').datepicker('setDate', null);
    $('#clientEndDt').datepicker('setDate', null);
})

$("button[data-role=today]").click(function() {
    let current = toStringByFormatting(new Date(), '-');
    start = current;
    end = current;
    $('#clientStartDt').datepicker('setDate', start);
    $('#clientEndDt').datepicker('setDate', end);
})

$("button[data-role=week]").click(function() {
    const current = new Date();
    current.setDate(current.getDate() - 7);
    let lastWeek = toStringByFormatting(current, '-');
    start = lastWeek;
    end = toStringByFormatting(new Date(), '-');
    $('#clientStartDt').datepicker('setDate', start);
    $('#clientEndDt').datepicker('setDate', end);
})

$("button[data-role=month]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#clientStartDt').datepicker('setDate', start);
    $('#clientEndDt').datepicker('setDate', end);
})

function clientTable() {
    let name = "";
    let companyName = "";
    let licenseNo = "";
    let selectOption = $("#searchSelect").val()
    if(selectOption == "name") {
        name = $("#inputSelect").val();
    } else if(selectOption == "companyName") {
        companyName = $("#inputSelect").val();
    } else if(selectOption == "licenseNo") {
        licenseNo = $("#inputSelect").val();
    }
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        licenseNoOption: licenseNo,
        companyNameOption: companyName
    };

    $("#datatable").DataTable().clear().destroy();
    $("#datatable").DataTable({
        destroy: true,
        ordering: false,
        searching: false,
        info: false,
        ajax:{
            url:"/bo/system/homepage/client/list",
            data: params,
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
            {"data": "licenseNo"},
            {"data": "companyName"},
            {"data": "name"},
            {"data": "createdAt"},
            {"data": "expiredAt"},
            {"data": "wefirmName"}
        ],
        columnDefs: [
            {
                "targets": [6],
            }
        ]

    })
}

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

$("select[name=wefirmSelect]").on("change", function () {
    clientTable()
})