 $("select").selectAjax();

var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/management/operation/info/list",
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
	searching : false,
	columns: [
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/management/operation/info/detail?mainDisplaySeq=" + row.mainDisplaySeq+ "'>" + row.mainDisplaySeq + "</a>";
			}
		},
		{ "data": "regDate" },
		{ "data": "infoTypeCd" },
		{ "data": "content" },

	],
	columnDefs: [
		{
			"targets": [3],
		}
	]
});


/** [등록] 버튼 클릭시 */
$("button[data-role=form]").click(function() {
	window.location.href = "/bo/management/operation/info/form";
})


/** datepicker */
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



/****기간 검색**** */
let start="";
let end=toStringByFormatting(new Date(), '-');

/** date format */
function toStringByFormatting(source, delimiter = '-') {
	var year = source.getFullYear();
	var month = source.getMonth() + 1 ;
	var day = source.getDate() ;
	return [year, month >= 10  ? month : '0' + month, day >= 10 ? day : '0' + day].join(delimiter);
}

/** 오늘 검색 */
$("button[data-role=today]").click(function() {
	let current = toStringByFormatting(new Date(), '-');
	start = current;
	end = current;
	$('#infoStartDt').datepicker('setDate', start);
	$('#infoEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#infoStartDt').datepicker('setDate', start);
	$('#infoEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#infoStartDt').datepicker('setDate', start);
	$('#infoEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start="";
	end=toStringByFormatting(new Date(), '-');
	$('#infoStartDt').datepicker('setDate', null);
	$('#infoEndDt').datepicker('setDate', null);
})

$('#infoStartDt').change(function() {
	start = $(this).val();
})

$('#infoEndDt').change(function() {
	end = $(this).val();
})


/** [기간] 검색 클릭시 */
$("button[data-role=search]").click(function() {

	let params = {
		searchStart: start,
		searchEnd: end
	};

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/management/operation/info/search",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		searching : false,
		columns: [
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/management/operation/info/detail?mainDisplaySeq=" + row.mainDisplaySeq+ "'>" + row.mainDisplaySeq + "</a>";
				}
			},
			{ "data": "regDate" },
			{ "data": "infoTypeCd" },
			{ "data": "content" },

		],
		columnDefs: [
			{
				"targets": [3],
			}
		]
	})

})