var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/management/operation/ticket/list",
		dataSrc: function(data) {
			return data.data;
		}
	},
	columns: [
		{ "data": "memberId" },
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.memberId + "'>" + row.memberName + "</a>";
			}
		},
		{ "data": "createdAt" },
		{
			render: function(data, type, row) {
				if(row.increase > 0){
					return "지급"
				}else{
					return "차감"
				}
			}
		},
		{ "data": "balance" },
		{
			render: function() {
				return "이력서 열람";
			}
		},

	],
	columnDefs: [
		{
			"targets": [5],
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
	$('#ticketStartDt').datepicker('setDate', start);
	$('#ticketEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#ticketStartDt').datepicker('setDate', start);
	$('#ticketEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#ticketStartDt').datepicker('setDate', start);
	$('#ticketEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start=null;
	end=null;
	$('#ticketStartDt').datepicker('setDate', null);
	$('#ticketEndDt').datepicker('setDate', null);
})

$('#ticketStartDt').change(function() {
	start = $(this).val();
})

$('#ticketEndDt').change(function() {
	end = $(this).val();
})


/** [공지] 검색 클릭시 */
$("button[data-role=search]").click(function() {

	let writer = $("form[name=searchTicket] input[data-role=headhunter] ").val();
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
		memberPhone : writerPhone,
		memberEmail : writerEmail,
		memberName : writerName,
		searchStart: start,
		searchEnd: end,
	};


	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/management/operation/ticket/search",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		columns: [
			{ "data": "id" },
			{
				render: function(data, type, row) {
					return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.memberId + "'>" + row.memberName + "</a>";
				}
			},
			{ "data": "createdAt" },
			{
				render: function(data, type, row) {
					if(row.increase > 0){
						return "지급"
					}else{
						return "차감"
					}
				}
			},
			{ "data": "balance" },
			{
				render: function() {
					return "이력서 열람";
				}
			},

		],
		columnDefs: [
			{
				"targets": [5],
			}
		]
	})

})

