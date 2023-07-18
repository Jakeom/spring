let newMember = [];
$("#datatable").DataTable({
	stateSave: true,
	ajax: {
		type: "get",
		url: "/bo/management/complaint/member/search",
		searching: false,
		ordering: false,
		info: false,
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
			render : function (data, type, row) {
				return "<td class='text-center x'><input type='checkbox' class='form-check-input m-0 align-middle' name='check'" + " value ='" + row.name + "' " + " id ='"+ row.id +"' "+ "/></td>";
			}
		},
		{"data": "id"},
		{"data": 'dtype'},
		{"data": "name"},
		{"data": "phone"},
		{"data": "email"},
	],
	columnDefs: [
		{
			"targets": [5],
		}
	]
})


/** checkbox 전체 선택 */
$('input:checkbox[id=mainCheckbox]').on("click", function(){
	$("#datatable").DataTable.$("input[name='check']").prop('checked', $(this).is(":checked"));
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
	let member = $("input[data-role=member] ").val();
	let memberPersonalInfo = document.getElementById("personalInfo").value;
	let id;
	let name;

	if (memberPersonalInfo == 'id') {
		id = member;
	}else if (memberPersonalInfo == 'name') {
		name = member;
	}
	let params = {
		nameOption: name,
		idOption: id,
		startDate: start,
		endDate: end
	};
	console.log(params)
	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({
		stateSave: true,
		ajax: {
			type: "get",
			url: "/bo/management/complaint/member/search",
			searching: false,
			ordering: false,
			info: false,
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
		searching : false,
		columns: [

			{
				render : function (data, type, row) {
					return "<td class='text-center x'><input type='checkbox' class='form-check-input m-0 align-middle' name='check'" + " value ='" + row.name + "' " + " id ='"+ row.id +"' "+ "/></td>";
				}
			},
			{"data": "id"},
			{"data": 'dtype'},
			{"data": "name"},
			{"data": "phone"},
			{"data": "email"},
		],
		columnDefs: [
			{
				"targets": [5],
			}
		]
	})
})

/** Mail Modal에 [추가] 버튼 클릭시 */
$("#addName").click(function() {
	newMember = [];
	let email = $('input[data-role=email]').val();
	let regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;

	if (!regExp.test(email)) {
		alert("이메일 형식을 확인해주세요.");
		return false;
	}
	if(email== ""){
		alert("이메일을 입력해주세요.");
	}else {
		newMember.push(email);
		$('input[data-role=email]').val('');
		$('form[name=register]').find('.name-area').append(" <button type='button' class='btn'>"+email+"</button> ");
	}
})

/** SMS Modal에 [추가] 버튼 클릭시 */
$("#msgAddName").click(function() {
	newMember = [];
	let phone = $('input[data-role=msgPhone]').val();
	if(phone== ""){
		alert("휴대폰를 입력해주세요.");
	}else {
		newMember.push(phone); // name phone 아직
		$('input[data-role=msgPhone]').val('');
		$('form[name=register]').find('.msg-name-area').append(" <button type='button' class='btn'>"+phone+"</button> ");
	}
})

/** [문자발송] 버튼 클릭시 */
$("#openSmsModal").click(function() {
	$("#datatable").DataTable.$('input[name=check]:checked').each(function () {
		let name = $(this).val();
		$('form[name=register]').find('.msg-name-area').append("<button type='button' class='btn'>"+name+"</button> ");
	});
})

/** [이메일발송] 버튼 클릭시 */
$("#openMailModal").click(function() {
	$("#datatable").DataTable.$('input[name=check]:checked').each(function () {
		let name = $(this).val();
		$('form[name=register]').find('.name-area').append(" <button type='button' class='btn'>"+name+"</button> ");
	});
})

/** SMS Modal 닫기 */
$("#closeSmsModal").click(function() {
	$('input[data-role=msgName]').val('');
	$('input[data-role=msgSubject]').val('');
	$('input[data-role=msgPhone]').val('');
	CKEDITOR.instances.msgContent.setData('');
	$('form[name=register]').find('.msg-name-area button').remove();
	$('#smsModal').modal('hide');
})
/** Mail Modal 닫기 */
$("#closeMailModal").click(function() {
	$('input[data-role=name]').val('');
	$('input[data-role=subject]').val('');
	$('input[data-role=email]').val('');
	CKEDITOR.instances.content.setData('');
	$('form[name=register]').find('.name-area button').remove();
	$('#mailModal').modal('hide');
})

/** [이메일전송] 버튼 클릭시 */
$("button[data-role=sendEmail]").click(function() {
	var aArray = [];
	let subject = $('input[data-role=subject]').val();
	let content = CKEDITOR.instances.content.getData();
	let emailCheck = 0;
	/* 이메일 형식 체크 */
	$('input[name=check]:checked').each(function () {
		aArray.push($(this).attr('id'));
	})

	if(newMember.length + aArray.length == 0) {
		alert("이메일을 입력해주세요.")
		return false;
	}
	if(subject == '' || subject == null) {
		alert("제목을 입력해주세요.")
		return false;
	}
	if(content == '' || content == null) {
		alert("내용을 입력해주세요.")
		return false;
	}

	$.ajax({
		type: "get",
		url : "/bo/management/operation/email/send",
		data : {
			idList : aArray,
			nonMemberList: newMember,
			subject : subject,
			content : content
		},
		success : function () {
			alert('전송되었습니다.');
			$('#mailModal').modal('hide');
		}
	})
})

/** [SMS전송] 버튼 클릭시 */
$("button[data-role=sendSms]").click(function() {
	var aArray = [];
	let subject = $('input[data-role=msgSubject]').val();
	let content = CKEDITOR.instances.msgContent.getData();

	$('input[name=check]:checked').each(function () {
		aArray.push($(this).attr('id'));
	})

	if(newMember.length + aArray.length == 0) {
		alert("휴대폰 번호를 입력해주세요.")
		return false;
	}
	if(subject == '' || subject == null) {
		alert("제목을 입력해주세요.")
		return false;
	}
	if(content == '' || content == null) {
		alert("내용을 입력해주세요.")
		return false;
	}
	$.ajax({
		type: "get",
		url : "/bo/management/operation/sms/send",
		data : {
			idList : aArray,
			nonMemberList: newMember,
			subject : subject,
			message : content,
		},
		success : function () {
			alert('전송되었습니다.');
			$('#smsModal').modal('hide');
		}
	})
})

$("#refresh").click(function () {
	$("#datatable").DataTable().clear().destroy();
})