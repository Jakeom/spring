$("select").selectAjax();
var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/management/operation/point/list",
		dataSrc: function(data) {
			return data.data;
		}
	},
	order: [[7, 'desc']],
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
		{ "data" : "memberType"},
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.memberId + "'>" + row.memberName + "</a>";
			}
		},
		{ "data":  "reasonCd" ,"defaultContent": "-"},
		{
			"render": function (data, type, row) {
				let balance = (Number(row.freeBalance) + Number(row.paidBalance))
				balance = priceToString(balance);
				let freeBalance = row.freeBalance
				freeBalance = priceToString(freeBalance)
				let paidBalance = row.paidBalance
				paidBalance = priceToString(paidBalance)
				return balance + "(" + freeBalance + "/" + paidBalance + ")"
			}
		},
		{
			"data" : "freeIncrease",
			"render": function (data) {
				return priceToString(data)
			}
		},
		{
			"data" : "paidIncrease",
			"render": function (data) {
				return priceToString(data)
			}
		},
		{ "data":  "createdAt" }
	],
	columnDefs: [
		{
			"targets": [7],
		}
	]
});



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
	$('#PointStartDt').datepicker('setDate', start);
	$('#PointEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#PointStartDt').datepicker('setDate', start);
	$('#PointEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#PointStartDt').datepicker('setDate', start);
	$('#PointEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start=null;
	end=null;
	$('#PointStartDt').datepicker('setDate', null);
	$('#PointEndDt').datepicker('setDate', null);
})

/** [포인트] 검색 클릭시 */
$("button[data-role=search]").click(function() {
	loadTable()
})

/* [지급/차감] 버튼 클릭시 */
$('#selectType').change(function(){
	loadTable()
})


/* [지급/차감] 버튼 클릭시 */
$('#selectMemberType').change(function(){
	loadTable()
})


/* [지급/차감] 버튼 클릭시 */
$('#paidPayType').change(function(){
	loadTable()
})


/* [지급/차감] 버튼 클릭시 */
$('#freePayType').change(function(){
	loadTable()
})

function loadTable() {
	let pointType = $("#selectType").val()
	let memberType = $("#selectMemberType").val()
	let freePayType = $("#freePayType").val()
	let paidPayType = $("#paidPayType").val()
	let member = $("input[data-role=member] ").val();
	let memberPersonalInfo = document.getElementById("personalInfo").value;
	let memberPhone;
	let memberName;
	let memberEmail;

	if (memberPersonalInfo == 'phone') {
		memberPhone = member;
	}else if (memberPersonalInfo == 'name') {
		memberName = member;
	}else if (memberPersonalInfo == 'email') {
		memberEmail = member;
	}

	let params = {
		memberPhone : memberPhone,
		memberEmail : memberEmail,
		memberName : memberName,
		pointType : pointType,
		memberType : memberType,
		freePayType : freePayType,
		paidPayType : paidPayType,
		searchStart: start,
		searchEnd: end,
	};


	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({
		ajax: {
			type: "post",
			url: "/bo/management/operation/point/search",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		order: [[7, 'desc']],
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
			{ "data" : "memberType"},
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.memberId + "'>" + row.memberName + "</a>";
				}
			},
			{ "data":  "reasonCd" ,"defaultContent": "-"},
			{
				"render": function (data, type, row) {
					let balance = (Number(row.freeBalance) + Number(row.paidBalance))
					balance = priceToString(balance);
					let freeBalance = row.freeBalance
					freeBalance = priceToString(freeBalance)
					let paidBalance = row.paidBalance
					paidBalance = priceToString(paidBalance)
					return balance + "(" + freeBalance + "/" + paidBalance + ")"
				}
			},
			{
				"data" : "freeIncrease",
				"render": function (data) {
					return priceToString(data)
				}
			},
			{
				"data" : "paidIncrease",
				"render": function (data) {
					return priceToString(data)
				}
			},
			{ "data":  "createdAt" }
		],
		columnDefs: [
			{
				"targets": [7],
			}
		]
	});
}

function priceToString(number) {
	return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

$("button[data-role=save]").click(function () {
	let checkRadio
	let pointRadio
	let point = $("input[name=insertPoint]").val()
	let id = $("input[name=insertId]").val()

	if(point == null || point == '') {
		alert("지급할 포인트를 입력해주세요.")
		return;
	}

	if(id == null || id == '') {
		alert("지급할 포인트를 입력해주세요.")
		return;
	}

	$("input[name='flexRadioDefault']:checked").each(function(){
		checkRadio = $(this).val()
	});

	$("input[name='pointTypeRadio']:checked").each(function(){
		pointRadio = $(this).val()
	});

	let pointCd
	if(checkRadio == 0) {
		point = Number(point) * -1
		pointCd = 'ADMIN_INPUT_REASON'
	} else {
		pointCd = 'ADMIN_INPUT_REASON'
	}
	let freeIncrease
	let paidIncrease
	if(pointRadio == 0) {
		paidIncrease = point
		freeIncrease = 0
	} else {
		freeIncrease = point
		paidIncrease = 0
	}
	let params = {
		freeIncrease: freeIncrease,
		paidIncrease: paidIncrease ,
		memberId: $("input[name=insertId]").val(),
		reasonCd: pointCd
	}
	$.ajax({
		url: "/bo/operation/reward/detail/point/insert",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(params),
		success: function () {
			alert("등록되었습니다.");
			loadTable()
		},
		error: function () {
			alert("ajax fail")
		}
	});

})