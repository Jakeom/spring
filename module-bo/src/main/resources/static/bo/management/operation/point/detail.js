$("select").selectAjax();

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
	$('#pointStartDt').datepicker('setDate', start);
	$('#pointEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#pointStartDt').datepicker('setDate', start);
	$('#pointEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#pointStartDt').datepicker('setDate', start);
	$('#pointEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start=null;
	end=null;
	$('#pointStartDt').datepicker('setDate', null);
	$('#pointEndDt').datepicker('setDate', null);
})

$('#pointStartDt').change(function() {
	start = $(this).val();
})

$('#pointEndDt').change(function() {
	end = $(this).val();
})


/** [공지] 검색 클릭시 */
$("button[data-role=search]").click(function() {
	$('form[name=searchForm]').submit();
})
/*
$(".selectChange").change(function() {
	$('form[name=searchForm]').submit();
})

$("#pointType").change(function() {
	/!*$('form[name=searchForm]').submit();*!/
})*/

$("button[data-role=save]").click(function () {
	let checkRadio
	let point = $("input[name=insertPoint]").val()
	if(point == null || point == '') {
		alert("지급할 포인트를 입력해주세요.")
		return;
	}
	$("input[name='flexRadioDefault']:checked").each(function(){
		checkRadio = $(this).val()
	});
	if(checkRadio == 0) {
		point = Number(point) * -1
	}
	let params = {
		freeIncrease: point,
		memberId: $("input[name=id]").val(),
		reasonCd: ''
	}
	$.ajax({
		url: "/bo/operation/reward/detail/point/insert",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(params),
		success: function () {
			alert("등록되었습니다.");
			$('form[name=searchForm]').submit();
		},
		error: function () {
			alert("ajax fail")
		}
	});

})