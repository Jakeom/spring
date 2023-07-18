var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/management/operation/approval/wefirm/list",
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
		{
			render: function(data, type, row) {
				return "<a>" +row.memberId + "</a>";
			}
		},
		{ "data": "memberName" ,  "defaultContent": "-"},
		{ "data": "createdAt" },
		{
			render: function(data, type, row) {
				if(row.requestType == "OPEN")
					return "WE펌 개설";
				else if(row.requestType == "CLOSE"){
					return "WE펌 폐쇄";
				}
			}
		},
		{ "data": "adminName",  "defaultContent": "-" },
		{ "data": "completedAt" ,"defaultContent": "-"},
		{
			render: function(data, type, row) {
				if(row.status == 'RECEIPT'){
					return "<a type='button' style='font-weight: bold;' data-toggle='modal' data-target='#myModal'" + " onclick='getMemberId(" + row.id + ")'>" + '접수' + "</a>";
				}
				else if(row.status == 'APPROVAL') {
					return "<a>" + '승인' + "</a>";
				}
				else  {
					return "<a>" + '거절' + "</a>";
				}
			}
		},
		{
			render: function(data, type, row) {
				if(row.status == 'RECEIPT') {
					return '<button name="'+ row.id + '" data-toggle="modal" data-role="nameCheck" data-target="#refuseModal" class="btn btn-outline-secondary">거절</button> '
						+ '<button data-role="approval" name="' + row.id + '"class="btn btn-outline-danger">승인</button><input type="hidden" name="memberId" value="' + row.memberId + '"><input type="hidden" name="wefirmId" value="' + row.wefirmId + '">'
				}
				else{
					return '-';
				}
			}
		},
	],
	columnDefs: [
		{
			"targets": [7],
		}
	]
});


/** 접수 버튼 클릭시 */
function getMemberId(id){
	$('#modalTable').empty();
	$.ajax({
		type: "get",
		url: "/bo/management/operation/approval/wefirm/wefirmDetail",
		data: {
			id : id,
		},
		dataSrc: function(data) {
			return data.data;
		},
		success : function (data) {
			if(data.data.requestType== 'OPEN') data.data.requestType = 'WE펌 개설';
			if(data.data.requestType== 'CLOSE') data.data.requestType = 'WE펌 폐쇄';
			if(data.data.sfHomepageUrl == null) data.data.sfHomepageUrl = '-';
			if( data.data.originName == null) data.data.originName = '';
			if( data.data.url == null) data.data.url = '';
			let append = '<tr><th>접수내역</th><td>' + data.data.requestType + '</td></tr><tr><th>접수일시</th><td>' + data.data.createdAt + '</td></tr><tr><th>이름</th><td>' + data.data.memberName +
				'</tr><tr><th>휴대폰번호</th><td>' + data.data.memberPhone + '</td></tr><tr><th>이에일주소</th><td>' + data.data.memberEmail + '</td></tr><tr><th>소속서치펌</th><td>' + data.data.name +
				'</td><tr><th>소속서치펌 대표자</th><td>' + data.data.ceoName + '</td></tr><tr><th>소속서치펌 연락처</th><td>' + '' + '</td></tr><tr><th>소속서치펌 홈페이지</th><td>' + data.data.websiteUrl +
				'</td></tr><tr><th>첨부파일</th><td><a  href="' + data.data.url + '">'+ data.data.originName +'</a></td></tr>';

			$('#modalTable').append(append);
		}
	})
}


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

/** [위펌] 검색 클릭시 */
$("button[data-role=search]").click(function() {
	loadTable()
})

/* [상태] 버튼 클릭시 */
$('#selectType').change(function(){
	loadTable()
})

$(document).on("click", "button[data-role=approval]", function () {
	$(this).closest("td").find("input[name=memberId]").val()
	let id = $(this).attr("name")
	let params = {
		id: id,
		memberId: $(this).closest("td").find("input[name=memberId]").val(),
		wefirmId: $(this).closest("td").find("input[name=wefirmId]").val(),
		status: 'APPROVAL'
	};
	if(!confirm("승인하시겠습니까?")) {
		return false;
	} else {
		$.ajax({
			url: "/bo/management/operation/approval/wefirm/accept",
			type: "post",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function () {
				alert("승인되었습니다.");
				loadTable()
			},
			error: function () {
				alert("ajax fail")
			}
		});
	}
})

$(document).on("click", "button[data-role=nameCheck]", function () {
	let id = $(this).attr("name")
	$("input[name=id]").val(id)
})

$(document).on("click", "button[data-role=modalSave]", function () {
	let refuseContent = $("#refuseContent").val()
	let id = $("input[name=id]").val()
	if(refuseContent == '' || refuseContent == null) {
		alert("거절 사유를 입력해주세요")
		return;
	}
	let params = {
		id: id,
		denyReason: refuseContent,
		status: 'DENY'
	};

	$.ajax({
		url: "/bo/management/operation/approval/wefirm/reject",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(params),
		success: function () {
			alert("거절되었습니다.");
			loadTable()
		},
		error: function () {
			alert("ajax fail")
		}
	});
})

function loadTable() {
	let member = $("input[data-role=member]").val();
	let adminName= $("input[data-role=admin]").val();
	let memberPersonalInfo = document.getElementById("personalInfo").value;
	let memberPhone;
	let memberName;
	let memberEmail;
	let status = $("#selectType").val()
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
		adminName : adminName,
		searchStart: start,
		searchEnd: end,
		status: status
	};

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/management/operation/approval/wefirm/search",
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
			{
				render: function(data, type, row) {
					return "<a>" +row.memberId + "</a>";
				}
			},
			{ "data": "memberName" ,  "defaultContent": "-"},
			{ "data": "createdAt" },
			{
				render: function(data, type, row) {
					if(row.requestType == "OPEN")
						return "WE펌 개설";
					else if(row.requestType == "CLOSE"){
						return "WE펌 폐쇄";
					}
				}
			},
			{ "data": "adminName",  "defaultContent": "-" },
			{ "data": "completedAt" ,"defaultContent": "-"},
			{
				render: function(data, type, row) {
					if(row.status == 'RECEIPT'){
						return "<a type='button' style='font-weight: bold;' data-toggle='modal' data-target='#myModal'" + " onclick='getMemberId(" + row.id + ")'>" + '접수' + "</a>";
					}
					else if(row.status == 'APPROVAL') {
						return "<a>" + '승인' + "</a>";
					}
					else  {
						return "<a>" + '거절' + "</a>";
					}
				}
			},
			{
				render: function(data, type, row) {
					if(row.status == 'RECEIPT') {
						return '<button name="'+ row.id + '" data-toggle="modal" data-role="nameCheck" data-target="#refuseModal" class="btn btn-outline-secondary">거절</button> '
							+ '<button data-role="approval" name="' + row.id + '"class="btn btn-outline-danger">승인</button><input type="hidden" name="memberId" value="' + row.memberId + '"><input type="hidden" name="wefirmId" value="' + row.wefirmId + '">'
					}
					else{
						return '-';
					}
				}
			},
		],
		columnDefs: [
			{
				"targets": [7],
			}
		]
	});
}