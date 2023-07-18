$(document).ready(function () {
    mainDisplayTable();
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
        mainDisplayTable();
    }
})

$("button[data-role=moveForm]").click(function () {
    window.location.href = "/bo/system/homepage/maindisplay/form"
});
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
    let year = source.getFullYear();
    let month = source.getMonth() + 1 ;
    let day = source.getDate();
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

$("#displayType").on("change", function () {
    mainDisplayTable()
})

function mainDisplayTable() {
    let params = {
        startDate: start,
        endDate: end,
        displayType: $("#displayType").val()
    };

    $("#datatable").DataTable().clear().destroy();
    $("#datatable").DataTable({
        searching: false,
        order: [[0, 'desc']],
        ordering: true,

        info: false,
        ajax: {
            type: "get",
            url: "/bo/system/homepage/maindisplay/list",
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
            {"data": "mainDisplaySeq"},
            {"data": "commoncdType"},
            {
                "data" : "commonFileList",
                "render" : function(data, type, row){
                    if(data){
                        return "<a href='/bo/system/homepage/maindisplay/detail?mainDisplaySeq=" + row.mainDisplaySeq + "'><img src='" + data[0].url + "' href='/bo/system/homepage/maindisplay/detail?mainDisplaySeq=" + row.mainDisplaySeq + "' style='width:150px; height:50px'></a>";
                    } else {
                        return "<a href='/bo/system/homepage/maindisplay/detail?mainDisplaySeq=" + row.mainDisplaySeq + "'>No image</a>";
                    }
                }
            },
            {
                "data": "useFlag",
                render : function (data) {
                    if(data == 'Y') {
                        return "활성"
                    } else {
                        return "비활성"
                    }
                }
            },
            {"data": "companyName"},
            {"data": "regSeq", "defaultContent": "-"},
            {"data": "regDate"},
        ],
        columnDefs: [
            {
                "targets": [6],
            }
        ]
    })
}