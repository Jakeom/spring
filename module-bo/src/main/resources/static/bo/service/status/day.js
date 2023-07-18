let start="";
let end=""
$(document).ready(function () {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    start = toStringByFormatting(current, '-');
    end = toStringByFormatting(new Date(), '-');
    selectChangeDataTable()
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
/** 1개월 클릭 시 */
$("button[data-role=month]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})
/** 3개월 클릭 시 */
$("button[data-role=threeMonths]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 3);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})
/** 6개월 클릭 시 */
$("button[data-role=sixMonths]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 6);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})
/** 12개월 클릭 시 */
$("button[data-role=twelveMonths]").click(function() {
    const current = new Date();
    current.setFullYear(current.getFullYear() - 1);
    let lastYear = toStringByFormatting(current, '-');
    start = lastYear;
    end = toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})
/** 검색하기 버튼 클릭 시 */
$("button[data-role=search]").click(function () {
    selectChangeDataTable()
})

$('#startDate').change(function() {
    start = $(this).val();
})

$('#endDate').change(function() {
    end = $(this).val();
})

$("button[data-role=downloadExcel]").click(function () {
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        location.href = "/bo/service/status/day/downloadExcel?startDate=" + start + "&endDate=" + end
    }
})

function selectChangeDataTable() {
    let params = {
        startDate: start,
        endDate: end,
    };
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        $("#datatable").DataTable().clear().destroy();
        $("#datatable").DataTable({
            destroy: true,
            ordering: false,
            searching: false,
            info: false,
            ajax: {
                type: "post",
                url: "/bo/service/status/day/search",
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
                {"data": "date"},
                {"data": "apMemberCnt"},
                {"data": "quitApMemberCnt"},
                {"data": "resumeCnt"},
                {"data" : "openResumeCnt"},
                {"data": "closeResumeCnt"},
                {"data": "hhMemberCnt"},
                {"data": "quitHhMemberCnt"}
            ],
            columnDefs: [
                { className: "text-center", targets: "_all" },
            ]
        })
    }
}

function toStringByFormatting(source, delimiter = '-') {
    var year = source.getFullYear();
    var month = source.getMonth() + 1 ;
    var day = source.getDate() ;
    return [year, month >= 10  ? month : '0' + month, day >= 10 ? day : '0' + day].join(delimiter);
}