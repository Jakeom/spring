/*var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/management/operation/recruit/list",
		dataSrc: function(data) {
			return data.data.data;
		}
	},
	searching : false,
	order: [[0, 'desc']],
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
			"data": "name", "defaultContent": "-",
			"render": function (data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.id + "'>" + data + "</a>"
			}
		},
		{ "data": "companyName" , "defaultContent": "-" },
		{ "data": "createdAt" , "defaultContent": "-" },
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/management/operation/recruit/detail?id=" + row.id + "'>" + row.title + "</a>";
			}
		},
		{ "data": "industry", "defaultContent": "-" },
		{ "data": "endDate" },
		{
			"data": "isCowork",
			"render": function (data, type, row) {
				if(data == 1) {
					return "코웍"
				} else if(data == 0) {
					return "단독"
				} else {
					return "-"
				}
			}
		},
	],
	columnDefs: [
		{
			"targets": [6],
		}
	]
});*/
/** [등록] 버튼 클릭시 */
$("button[data-role=form]").click(function() {
	window.location.href = "/recruit/form";
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
let word;

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
	$('#recruitStartDt').datepicker('setDate', start);
	$('#recruitEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#recruitStartDt').datepicker('setDate', start);
	$('#recruitEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#recruitStartDt').datepicker('setDate', start);
	$('#recruitEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start=null;
	end=null;
	$('#recruitStartDt').datepicker('setDate', null);
	$('#recruitEndDt').datepicker('setDate', null);
})

$('#recruitStartDt').change(function() {
	start = $(this).val();
})

$('#recruitEndDt').change(function() {
	end = $(this).val();
})

/** [채용공고 금칙어 ] 관리 클릭시 */
$("button[data-role=banWordSubmit]").click(function() {
	let word = $("#bannedWord").val()
	if(word == '' || word == null) {
		alert("금칙어를 입력해주세요")
		return false;
	}
	$.ajax({
		type: "post",
		url: "/bo/management/operation/recruit/ban/insert",
		data: {word: word},
		success: function (data) {
			alert("추가되었습니다.");
			let id = data.data.id
			let append = '<div class="p-l-20" name="' + id + '"><span>' + $("#bannedWord").val() + '</span><button type="button" class="btn" id="deleteBanWordBtn" data-role="deleteBanWord" name="' + id + '">&times;</button></div>'
			$(".banWordContent").prepend(append)
			let spanAppend = '<span name="' + id + '">' + $("#bannedWord").val() + ',</span>'
			$(".banWordSpan").prepend(spanAppend)
		},
		error: function () {
			alert("*****ajax fail*****");
		}
	});
})

$(document).on('click', 'button[data-role=deleteBanWord]',function(e) {
	let id = $(this).attr('name')
	if (!confirm("삭제하시겠습니까?")) {
		return false;
	} else {
		$.ajax({
			type: "post",
			url: "/bo/management/operation/recruit/ban/delete",
			data: {id: id},
			success: function (data) {
				alert("삭제되었습니다.");
				$("div[name=" + id + "]").remove()
				$("span[name=" + id + "]").remove()
			},
			error: function () {
				alert("*****ajax fail*****");
			}
		});
	}
});

/** [공지] 검색 클릭시 */
$("button[data-role=search]").click(function() {
	$("form[name=searchRecruit] input[name=page]").val('1');
	$("form[name=searchRecruit]").submit();
})

/* [상태] 버튼 클릭시 */
$('#isCowork').change(function(){
	$("form[name=searchRecruit] input[name=page]").val('1');
	$("form[name=searchRecruit]").submit();
})

// paging
$("a[data-role=btnGoPage]").click(function() {
	let page = $(this).data("page");
	$("form[name=searchRecruit] input[name=page]").val(page);
	$("form[name=searchRecruit]").submit();
});