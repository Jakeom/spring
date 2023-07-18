$("select").selectAjax();
var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/system/homepage/qna/list",
		dataSrc: function(data) {
			return data.data;
		}
	},
	order: [[2, 'desc']],
	searching : false,
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
		{ "data": "memberId" },
		{ "data": "memberName" },
		{ "data": "createdAt" },
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/system/homepage/qna/detail?id=" + row.id + "'>" + row.subject + "</a>";
			}
		},

		{ "data": "qnaTypeCd"},
		{ "data": "memberType"},
		{
			render : function (data, type, row) {
				switch(row.answerYn){
					case 'Y' : return '답변완료';
					case 'N' : return '접수';
				}
			}
		},
		{ "data": "adminName" ,"defaultContent": "-"},
		{ "data": "answeredAt" ,"defaultContent": "-"},



	],
	columnDefs: [
		{
			"targets": [8],
		}
	]
});
/** [배너등록] 버튼 클릭시 */
$("button[data-role=form]").click(function() {
	window.location.href = "/bo/system/homepage/qna/form";
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
let start;
let end;

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
	$('#qnaStartDt').datepicker('setDate', start);
	$('#qnaEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#qnaStartDt').datepicker('setDate', start);
	$('#qnaEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#qnaStartDt').datepicker('setDate', start);
	$('#qnaEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start="";
	end=toStringByFormatting(new Date(), '-');
	$('#qnaStartDt').datepicker('setDate', null);
	$('#qnaEndDt').datepicker('setDate', null);
})

$('#qnaStartDt').change(function() {
	start = $(this).val();
})

$('#qnaEndDt').change(function() {
	end = $(this).val();
})


/** [공지] 검색 클릭시 */
$("button[data-role=search]").click(function() {

	/** name or phone or email */
	let writer = $("input[data-role=writer] ").val();
	let adminName = $("input[name=admin]").val();
	let memberPersonalInfo = document.getElementById("personalInfo").value;
	let writerPhone;
	let writerName;
	let writerEmail;

	if (memberPersonalInfo == 'phone') {
		writerPhone = writer;
	}else if (memberPersonalInfo == 'name') {
		writerName = writer;
	}else if (memberPersonalInfo == 'email') {
		writerEmail = writer;
	}

	let params = {
		memberName : writerName,
		memberEmail : writerEmail,
		memberPhone : writerPhone,
		adminName : adminName,
		searchStart: start,
		searchEnd: end,

	};

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/system/homepage/qna/search",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		order: [[2, 'desc']],
		searching : false,
		columns: [

			{ "data": "memberId" },
			{ "data": "memberName" },
			{ "data": "createdAt" },
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/system/homepage/qna/detail?id=" + row.id + "'>" + row.subject + "</a>";
				}
			},
			{ "data": "qnaTypeCd"},
			{ "data": "memberType"},
			{
				render : function (data, type, row) {
					switch(row.answerYn){
						case 'Y' : return '답변완료';
						case 'N' : return '접수';
					}
				}
			},
			{ "data": "adminName" ,"defaultContent": "-"},
			{ "data": "answeredAt" ,"defaultContent": "-"},
		],
		columnDefs: [
			{
				"targets": [8],
			}
		]
	})

})

/* [상태] 버튼 클릭시 */
$('#selectType').on('change', function(){
	let type = $(this).val();

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			url: "/bo/system/homepage/qna/list",
			data : { answerYn : type },
			dataSrc: function(data) {
				return data.data;
			}
		},
		order: [[2, 'desc']],
		searching : false,
		columns: [
			{ "data": "memberId" },
			{ "data": "memberName" },
			{ "data": "createdAt" },
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/system/homepage/qna/detail?id=" + row.id + "'>" + row.subject + "</a>";
				}
			},
			{ "data": "qnaTypeCd"},
			{ "data": "memberType"},
			{
				render : function (data, type, row) {
					switch(row.answerYn){
						case 'Y' : return '답변완료';
						case 'N' : return '접수';
					}
				}
			},
			{ "data": "adminName" ,"defaultContent": "-"},
			{ "data": "answeredAt" ,"defaultContent": "-"},

		],
		columnDefs: [
			{
				"targets": [8],
			}
		]
	})

})

/* [상태] 버튼 클릭시 */
$('#subSelectType').on('change', function(){
	let type = $(this).val();

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			url: "/bo/system/homepage/qna/list",
			data : { memberType : type },
			dataSrc: function(data) {
				return data.data;
			}
		},
		order: [[2, 'desc']],
		searching : false,
		columns: [
			{ "data": "memberId" },
			{ "data": "memberName" },
			{ "data": "createdAt" },
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/system/homepage/qna/detail?id=" + row.id + "'>" + row.subject + "</a>";
				}
			},
			{ "data": "qnaTypeCd"},
			{ "data": "memberType"},
			{
				render : function (data, type, row) {
					switch(row.answerYn){
						case 'Y' : return '답변완료';
						case 'N' : return '접수';
					}
				}
			},
			{ "data": "adminName" ,"defaultContent": "-"},
			{ "data": "answeredAt" ,"defaultContent": "-"},

		],
		columnDefs: [
			{
				"targets": [8],
			}
		]
	})

})