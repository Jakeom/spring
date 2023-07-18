var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/management/operation/payment/list",
		dataSrc: function(data) {
			return data.data;
		}
	},
	searching : false,
	order: [[2, 'desc']],
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
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.memberId + "'>" + row.name + "</a>";
			}
		},
		{ "data": "purchasedAt","defaultContent": "-" },
		{ "data": "method" },
		{ "data": "price" },
		{ "data": "statusCd" },
		{ 
			"render": function (data, type, row) {
				if(row.status == 'PAY_SUCCESS') {
					return "<button data-role='cancelPurchase' data-history-id='" + row.pointUseHistoryId + "' data-receipt-id='" + row.receiptId +"' data-payment-id='" + row.id +"' data-price='" + row.remainPrice +"' data-member-id='" + row.memberId +"' type='button' class='btn btn-outline-info'>결제 취소</button>";
				} else {
					return "-"
				}
			}
		}
	],
	columnDefs: [
		{
			"targets": [6],
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
	$('#paymentStartDt').datepicker('setDate', start);
	$('#paymentEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#paymentStartDt').datepicker('setDate', start);
	$('#paymentEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#paymentStartDt').datepicker('setDate', start);
	$('#paymentEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start=null;
	end=null;
	$('#paymentStartDt').datepicker('setDate', null);
	$('#paymentEndDt').datepicker('setDate', null);
})

$('#paymentStartDt').change(function() {
	start = $(this).val();
})

$('#paymentEndDt').change(function() {
	end = $(this).val();
})


/** [공지] 검색 클릭시 */
$("button[data-role=search]").click(function() {

	let writer = $("form[name=searchPayment] input[data-role=depositor] ").val();
	let memberPersonalInfo = document.getElementById("personalInfo").value;
	let writerPhone;
	let writerName;
	let writerEmail;
	let status = $('#selectType').val();

	if (memberPersonalInfo == 'phone') {
		writerPhone = writer;
	}else if (memberPersonalInfo == 'name') {
		writerName = writer;
	}else if (memberPersonalInfo == 'email') {
		writerEmail = writer;
	}

	let params = {
		phone : writerPhone,
		email : writerEmail,
		name : writerName,
		searchStart: start,
		searchEnd: end,
		status : status
	};


	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/management/operation/payment/search",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		searching : false,
		order: [[2, 'desc']],
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
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.memberId + "'>" + row.name + "</a>";
				}
			},
			{ "data": "purchasedAt" ,"defaultContent": "-"},
			{ "data": "method" },
			{ "data": "price" },
			{ "data": "statusCd" },
			{
				"render": function (data, type, row) {
					if(row.status == 'PAY_SUCCESS') {
						return "<button data-role='cancelPurchase' data-history-id='" + row.pointUseHistoryId + "' data-receipt-id='" + row.receiptId +"' data-payment-id='" + row.id +"' data-price='" + row.remainPrice +"' data-member-id='" + row.memberId +"' type='button' class='btn btn-outline-info'>결제 취소</button>";
					} else {
						return "-"
					}
				}
			}
		],
		columnDefs: [
			{
				"targets": [6],
			}
		]
	})

})

/* [상태] 버튼 클릭시 */
$('#selectType').change(function(){
	let writer = $("form[name=searchPayment] input[data-role=depositor] ").val();
	let memberPersonalInfo = document.getElementById("personalInfo").value;
	let writerPhone;
	let writerName;
	let writerEmail;
	let status = $('#selectType').val();

	if (memberPersonalInfo == 'phone') {
		writerPhone = writer;
	}else if (memberPersonalInfo == 'name') {
		writerName = writer;
	}else if (memberPersonalInfo == 'email') {
		writerEmail = writer;
	}

	let params = {
		phone : writerPhone,
		email : writerEmail,
		name : writerName,
		searchStart: start,
		searchEnd: end,
		status : status
	};

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/management/operation/payment/search",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		searching : false,
		order: [[2, 'desc']],
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
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.memberId + "'>" + row.name + "</a>";
				}
			},
			{ "data": "purchasedAt","defaultContent": "-" },
			{ "data": "method" },
			{ "data": "price" },
			{ "data": "statusCd" },
			{
				"render": function (data, type, row) {
					if(row.status == 'PAY_SUCCESS') {
						return "<button data-role='cancelPurchase' data-history-id='" + row.pointUseHistoryId + "' data-receipt-id='" + row.receiptId +"' data-payment-id='" + row.id +"' data-price='" + row.remainPrice +"' data-member-id='" + row.memberId +"' type='button' class='btn btn-outline-info'>결제 취소</button>";
					} else {
						return "-"
					}
				}
			}
		],
		columnDefs: [
			{
				"targets": [6],
			}
		]
	})

})

//구매포인트 취소하기
$(document).on("click", "button[data-role=cancelPurchase]", function(){
	let params = { 'type' : 'REFUND'
		, 'receiptId' : $(this).data("receiptId")
		, 'paymentId' : $(this).data("paymentId")
		, 'cancelledPrice' : $(this).data("price")
		, 'memberId' : $(this).data("memberId")
		, 'pointUseHistoryId' : $(this).data("historyId")
	}
	$.ajax({
		url: '/bo/management/operation/payment/purchase-cancel',
		type: 'POST',
		data: params,
		dataType: 'json',
		success: function (d) {
			if(d.status == 200){
				alert("취소되었습니다.")
				window.location.reload()
			} else {
				alert('관리자에게 문의하세요.');
			}
		},
		error: function(){
			alert('관리자에게 문의하세요.');
		}
	});
})