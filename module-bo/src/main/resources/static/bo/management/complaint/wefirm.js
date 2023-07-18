var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/management/complaint/wefirm/list",
		dataSrc: function(data) {
			return data.data;
		}
	},
	searching : false,
	order: [[1, 'desc']],
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
		{ "data": "id" },
		{ "data": "completedAt" ,"defaultContent": "-" },
		{ "data": "name" },
		{ "data": "ceoName" },
		{ "data": "joinHeadhunter" },
		{ "data": "joinPosition" },
		{
			render: function(data, type, row) {
				if(row.closed == '0'){
					return '정상';
				}else if(row.closed == '1'){
					return '폐쇄';
				}
			}
		},
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/management/complaint/wefirm/detail?id=" + row.id + "'>" + "조회" + "</a>";
			}
		}
	],
	columnDefs: [
		{
			"targets": [7],
		}
	]
});
/** [배너등록] 버튼 클릭시 */
$("button[data-role=form]").click(function() {
	window.location.href = "/wefirm/form";
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
	$('#wefirmStartDt').datepicker('setDate', start);
	$('#wefirmEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#wefirmStartDt').datepicker('setDate', start);
	$('#wefirmEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#wefirmStartDt').datepicker('setDate', start);
	$('#wefirmEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start=null;
	end=null;
	$('#wefirmStartDt').datepicker('setDate', null);
	$('#wefirmEndDt').datepicker('setDate', null);
})

$('#wefirmStartDt').change(function() {
	start = $(this).val();
})

$('#wefirmEndDt').change(function() {
	end = $(this).val();
})



/** [채용] 검색 클릭시 */
$("button[data-role=search]").click(function() {
	loadTable()
})

/* [상태] 버튼 클릭시 */
$('#selectType').change(function(){
	loadTable()
})

function loadTable() {

	let writer = $("form[name=searchWefirm] input[data-role=writer] ").val();
	let memberPersonalInfo =   document.getElementById("personalInfo").value;
	let writerPhone;
	let writerName;
	let writerEmail;
	let status = $('#selectType').val()

	if (memberPersonalInfo == 'phone') {
		writerPhone = writer;
	}else if (memberPersonalInfo == 'name') {
		writerName = writer;
	}else if (memberPersonalInfo == 'email') {
		writerEmail = writer;
	}

	let params = {
		memberPhone : writerPhone,
		memberEmail : writerEmail,
		ceoName : writerName,
		searchStart: start,
		searchEnd: end,
		closed : status
	};


	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/management/complaint/wefirm/search",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		searching : false,
		order: [[1, 'desc']],
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
			{ "data": "id" },
			{ "data": "completedAt" ,"defaultContent": "-" },
			{ "data": "name" },
			{ "data": "ceoName" },
			{ "data": "joinHeadhunter" },
			{ "data": "joinPosition" },
			{
				render: function(data, type, row) {
					if(row.closed == '0'){
						return '정상';
					}else if(row.closed == '1'){
						return '폐쇄';
					}
				}
			},
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/management/complaint/wefirm/detail?id=" + row.id + "'>" + "조회" + "</a>";
				}
			}
		],
		columnDefs: [
			{
				"targets": [7],
			}
		]
	});
}