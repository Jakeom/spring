$("#image2").hide();
var table = $("#datatable").DataTable({
	order: [[4, 'desc']],
	ajax: {
		url: "/bo/management/operation/rewardMall/list",
		dataSrc: function(data) {
			return data.data;
		}
	},
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
		{ "data": "goodComp" },
		{ "data": "goodNm"},
		{ "data": "point" },
		{
			"data": "commonFileList",
			render: function(data, type, row) {
				if(data) {
					if(data.length != 0) {
						return "<img src='" + data[0].url + "' style='width:50px; height:50px'>";
					} else {
						return "-"
					}
				} else {
					return "-"
				}
			}
		},
		{ "data": "updateDt" },
		{
			render: function(data, type, row) {
				return "<button type='button'  onclick='details("+row.id+")' data-toggle='modal' data-target='#myModal2' data-role='create'>조회</button>";
			}
		},
		{
			render: function(data, type, row) {
				return "<button type='button'  onclick='orders("+row.id+")' >"+row.count+"</button>";
			}
		},
	],
	columnDefs: [
		{
			"targets": [6],
		}
	]
});
orders('')

function orders(id){
	$("#style").show();

	$("#datatable2").DataTable().clear().destroy();
	$("#datatable2").DataTable({

		ajax: {
			type: "post",
			url: "/bo/management/operation/reward/detail?id="+id,
			dataSrc: function(data) {
				return data.data;
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
			{ "data": "createdAt" },
			{ "data": "loginId" },
			{ "data": "memberName" },
			{ "data": "goodNm"},
			{ "data": "point" },
			{
				render: function(data, type, row) {
					if(row.sendFlag != 'Y') {
						return "<button type='button'  data-toggle='modal' data-target='#messageModal' data-role='message'>문자</button><input type='hidden' name='detailGoods' value='" + row.memberName + "님에게 " + row.goodComp + " " + row.goodNm + " 교환권이 지급되었습니다.'><input type='hidden' name='memberPhoneNo' value='" + row.memberPhone +"' ><input type='hidden' name='rewardHistoryId' value='" + row.id +"' >";
					} else {
						return "발송 완료"
					}
				}
			},
		],
		columnDefs: [
			{
				"targets": [5],
			}
		]
	})
}


function details(id){

	$.ajax({
		type: "post",
		url: '/bo/management/operation/rewardMall/detail?id='+ id ,
		dataSrc: function(data) {
			return data.data;
		},
		success: function (data) {

			CKEDITOR.instances.detail_content.setData(data.data.goodDetail);
			if(data.data.commonFileList != null && data.data.commonFileList.length != 0) {
				$('#imgArea').attr('src', data.data.commonFileList[0].url);
				$("input[id=fileSeq]").attr('value', data.data.fileSeq);
			} else {
				$('#imgArea').attr('src', '');
			}
			$("input[data-role=point2]").attr('value',data.data.point);
			$("input[data-role=name]").attr('value',data.data.goodNm);
			$("input[data-role=com]").attr('value',data.data.goodComp);
			$("input[data-role=detailId]").attr('value',data.data.id);
		},
	});

}
$("button[data-role=close]").click(function (){
	$("#datatable2").DataTable().clear().destroy();
	$("#style").hide();
})

$("#addFile").on('click' , function (){
	$("#image2").show();
	$('#imgArea').attr('src' , '' );
	 $('#imgArea').hide();
	 $(this).hide();
})



$("button[data-role=update]").click(function() {
	let formData = new FormData();
	let goodDetail = CKEDITOR.instances.detail_content.getData();
	let point = $("input[data-role=point2]").val();
	let goodNm = $("input[data-role=name]").val();
	let goodComp = $("input[data-role=com]").val();
	let fileSeq = $("input[id=fileSeq]").val();
	let id = $("#detailId").val();
	let fileCheck = 'N';
	if($("#image2").val() != null && $("#image2").val() != '') {
		fileCheck = 'Y'
	}
	if(!$('#addFile').is(':visible') && fileCheck == 'N') {
		alert("썸네일을 추가해주세요.");
		return false;
	}
	let jsonData = {
		fileSeq : fileSeq,
		id : id,
		point : point,
		goodNm : goodNm,
		goodComp : goodComp,
		goodDetail : goodDetail,
		fileCheck : fileCheck
	}

	$("#image2").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('imageFile', e));
	});

	formData.set("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

	if (!confirm("수정 하시겠습니까?")) {
		return;
	} else {
		$.ajax({
			type: "post",
			url: "/bo/management/operation/rewardMall/update" ,
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function () {
				alert("수정 되었습니다.");
				location.href = "/bo/management/operation/rewardMall";
			},
			error: function () {
				alert("수정에 실패했습니다.");
			}
		});
	}
})


$("button[data-role=delete]").click(function() {

	let id = $("#detailId").val();

	if (!confirm("삭제 하시겠습니까?")) {
		return;
	} else {
		$.ajax({
			type: "post",
			url: "/bo/management/operation/rewardMall/delete?id="+id ,
			success: function () {
				alert("삭제 되었습니다.");
				location.href = "/bo/management/operation/rewardMall";
			},
			error: function () {
				alert("삭제에 실패했습니다.");
			}
		});
	}
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
	$('#rewardMallStartDt').datepicker('setDate', start);
	$('#rewardMallEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#rewardMallStartDt').datepicker('setDate', start);
	$('#rewardMallEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#rewardMallStartDt').datepicker('setDate', start);
	$('#rewardMallEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start=null;
	end=null;
	$('#rewardMallStartDt').datepicker('setDate', null);
	$('#rewardMallEndDt').datepicker('setDate', null);
})

$('#rewardMallStartDt').change(function() {
	start = $(this).val();
})

$('#rewardMallEndDt').change(function() {
	end = $(this).val();
})


/** 검색 클릭시 */
$("button[data-role=search]").click(function() {
	let params = {
		searchStart: start,
		searchEnd: end,
	};

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({
		order: [[4, 'desc']],
		ajax: {
			url: "/bo/management/operation/rewardMall/list",
			data: params,
			dataSrc: function(data) {
				return data.data;
			}
		},
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
			{ "data": "goodComp" },
			{ "data": "goodNm"},
			{ "data": "point" },
			{
				"data": "commonFileList",
				render: function(data, type, row) {
					if(data) {
						if(data.length != 0) {
							return "<img src='" + data[0].url + "' style='width:50px; height:50px'>";
						} else {
							return "-"
						}
					} else {
						return "-"
					}
				}
			},
			{ "data": "updateDt" },
			{
				render: function(data, type, row) {
					return "<button type='button'  onclick='details("+row.id+")' data-toggle='modal' data-target='#myModal2' data-role='create'>조회</button>";
				}
			},
			{
				render: function(data, type, row) {
					return "<button type='button'  onclick='orders("+row.id+")' >"+row.count+"</button>";
				}
			},
		],
		columnDefs: [
			{
				"targets": [6],
			}
		]
	});
})


$("button[data-role=save]").click(function (){
	let formData = new FormData();
	let point =$("input[data-role=point]").val();
	let goodNm =$("input[data-role=goodsName]").val();
	let goodComp =$("input[data-role=company]").val();
	let goodDetail = CKEDITOR.instances.p_content.getData();
	if($("#customFile").val() == null || $("#customFile").val() == '') {
		alert("썸네일을 올려주세요.")
		return;
	}
	let jsonData = {
		point : point,
		goodNm : goodNm,
		goodComp : goodComp,
		goodDetail : goodDetail,
	}

	$("#customFile").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('imageFile', e));
	});
	formData.set("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

	if (!confirm("저장 하시겠습니까?")) {
		return;
	} else {
		$.ajax({
			type: "post",
			url: "/bo/management/operation/rewardMall/insert",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function (data) {
				alert("저장 되었습니다.");
				location.href = "/bo/management/operation/rewardMall";
			},
			error: function () {
				alert("저장에 실패했습니다.");
			}
		});
	}

})

$(".custom-file-input").on("change", function() {
	var fileName = $(this).val().split("\\").pop();
	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});

CKEDITOR.replace('p_content');
CKEDITOR.replace('detail_content');
CKEDITOR.config.autoParagraph = false;

$(document).on("click", "button[data-role=message]", function () {
	$("p[name=smsContent]").text($(this).closest("td").find("input[name=detailGoods]").val());
	$("input[name=memberPhone]").val($(this).closest("td").find("input[name=memberPhoneNo]").val());
	$("input[name=historyId]").val($(this).closest("td").find("input[name=rewardHistoryId]").val());
})

$("button[data-role=sendMessage]").click(function() {

	let content = $("p[name=smsContent]").text() + $("input[name=url]").val()

	$.ajax({
		type: "post",
		url: "/bo/management/operation/rewardMall/snedSms",
		data: {
			content: content,
			phone: $("input[name=memberPhone]").val(),
			id: $("input[name=historyId]").val()
		},
		success: function () {
			alert("발송 되었습니다.");
			orders($("input[name=clickId]").val())
		},
		error: function () {
			alert("발송이 실패했습니다.");
		}
	});
})

$("button[data-role=create]").click(function () {
	$("#customFile").val('')
	$("label[for=customFile]").text('Choose file')
	$("input[name=company]").val('')
	$("input[name=goodsName]").val('')
	$("input[name=point]").val('')
	CKEDITOR.instances.p_content.setData('');
})