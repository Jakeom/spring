let option = "ALL"
let start = "";
let end = toStringByFormatting(new Date(), '-');
$('#memberStartDt').datepicker('setDate', null);
$('#memberEndDt').datepicker('setDate', null);
$(document).ready(function () {
    searchTable();
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
})

$("button[data-role=search]").click(function() {
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        searchTable();
    }
})

function searchTable() {
    let optionTypeSelect = $("#optionTypeSelect").val()
    let name = "";
    let phone = "";
    let email = "";
    let id = "";
    if(optionTypeSelect == "name") {
        if($("#inputSelect").val()){
            name = $("#inputSelect").val();
        }
    } else if(optionTypeSelect == "phone") {
        if($("#inputSelect").val()){
            phone = $("#inputSelect").val();
        }
    } else if(optionTypeSelect == "email") {
        if($("#inputSelect").val()){
            email = $("#inputSelect").val();
        }
    } else if(optionTypeSelect == "id") {
        if($("#inputSelect").val()){
            id = $("#inputSelect").val();
        }
    }
    let params = {
        startDate: start,
        endDate: end,
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        idOption: id,
        blacklistType: $("#memberSelect").val(),
        useFlag: $("#useFlagSelect").val()
    };

    $("#datatable").DataTable().clear().destroy();
    $("#datatable").DataTable({
        searching: false,
        ordering: false,
        info: false,
        ajax: {
            type: "get",
            url: "/bo/management/complaint/blacklist/list",
            data: params,
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
            {
                "data": "memberId",
                "render": function (data) {
                    if(data) {
                        return data
                    } else {
                        return "비회원"
                    }
                }
            },
            {"data": "name"},
            {"data": "phone"},
            {"data": "email"},
            {
                "data": "useFlag",
                "render": function (data) {
                    if(data == 0) {
                        return "비활성"
                    } else {
                        return "활성"
                    }
                }
            },
            {"data": "regDate"},
            {
                "data" : "blacklistSeq",
                "render" : function(data){
                    return "<a href='/bo/management/complaint/blacklist/detail?blacklistSeq=" + data + "'>조회</a>";
                }
            }
        ],
        columnDefs: [
            {
                "targets": [6],
            }
        ]
    })
}
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
    var day = source.getDate() + 1;
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

$(document).on('click', '#months',function() {
    const startCurrent = new Date();
    startCurrent.setMonth(startCurrent.getMonth() - 3);
    let startMonth = toStringByFormatting(startCurrent, '-');
    start = startMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#memberStartDt').datepicker('setDate', start);
    $('#memberEndDt').datepicker('setDate', end);
})

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

$("button[data-role=blacklistForm]").click(function () {
    location.href = "/bo/management/complaint/blacklist/form";
});

$("input[data-role=enter]").keyup(function(e) {
    if (e.keyCode === 13) {
        document.getElementById('search').click();
    }
});

$("#useFlagSelect").on("change", function () {
    searchTable()
})
$("#memberSelect").on("change", function () {
    searchTable()
})