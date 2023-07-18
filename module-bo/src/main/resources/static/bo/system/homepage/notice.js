displayTable();
$("select").selectAjax();

/** 공지사항 테이블*/
var table = $("#datatable").DataTable({
	ajax: {
		url: "/bo/system/homepage/notice/list",

		dataSrc: function(data) {
			return data.data;
		}
	},
	order: [[4, 'desc']],
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
		{
			render : function (data, type, row) {
				return "<td class='text-center x'><input type='checkbox' class='form-check-input m-0 align-middle' name='check'" + " value ='" + row.displayOrder + "' " + " id ='"+ row.noticeSeq +"' "+ "/></td>";

			}
		},
		{ "data": "noticeSeq" },
		{
			render: function (data, type, row) {
				if (row.noticeTypeCd == 'EVENT') {
					return '이벤트';
				} else {
					return '공지사항';
				}
			}
		},
		{
			render: function(data, type, row) {
				if( row.fileSeq == null || row.fileSeq == '') return "<a style='font-weight: bold;' href='/bo/system/homepage/notice/detail?noticeSeq=" + row.noticeSeq + "'>" + row.title + "</a>";
				else return "<a style='font-weight: bold;' href='/bo/system/homepage/notice/detail?noticeSeq=" + row.noticeSeq + "'>" + row.title + "</a><i class=\"icon-clip\"></i>";

			}
		},
		{ "data": "createDt" },
		{ "data": "writer" ,"defaultContent": "-"},
		{ "data": "updateDt" },
		{ "data": "editor" ,"defaultContent": "-"},
		{ "data": "hit" },

	],
	columnDefs: [
		{
			"targets": [8],
		}
	]
});

/** Display 테이블  */
function displayTable() {
	$.ajax({
		url: "/bo/system/homepage/notice/display/list",
		success: function (data) {
			//console.info(data.data[0].noticeSeq);
			// let i = 0 ;
			// $("#row"+ (i+1) ).append( '<td>'+ data.data[0].noticeSeq  +'</td>');

			for(let i=0; i < data.data.length ; i++) {

				let num = data.data[i].displayOrder;
				let title;
				if(data.data[i].fileSeq == null || data.data[i].fileSeq == '') {
					title = "<a href='/bo/system/homepage/notice/detail?noticeSeq=" + data.data[i].noticeSeq + "'>" + data.data[i].title +"</a>"
				} else {
					title = "<a href='/bo/system/homepage/notice/detail?noticeSeq=" + data.data[i].noticeSeq + "'>" + data.data[i].title + "</a><i class=\"icon-clip\"></i>";
				}
				let typeCd ;
				let editor = data.data[i].editor;
				if( editor == ''  || editor == null ) {
					editor = '-'
				};
				if(data.data[i].noticeTypeCd  == 'NORMAL') typeCd = '공지사항'; else{ typeCd = '이벤트' };
				let displayAppender = '<td>' + typeCd + '</td><td>' + title + '</td><td>' + data.data[i].updateDt
					+ '</td><td>' + editor + '</td>' + '<td><button type="button" name="' + data.data[i].noticeSeq + '" data-role="deleteDisplay"  value="' + data.data[i].noticeSeq + '">삭제</button></td>';
				$('#row'+ num ).append(displayAppender);

			}
		},
		error: function () {
			alert("메인 노출 테이블 불러오기 오류")
		}
	});
}

$(document).on("click", "button[data-role=deleteDisplay]", function () {
	if(!confirm("메인에서 삭제하시겠습니까?")) {
		return false;
	} else {
		$.ajax({
			url: "/bo/system/homepage/notice/deleteDisplayOrder",
			type: "post",
			data : { noticeSeq : $(this).attr('name') },
			success: function () {
				alert("삭제되었습니다.");
				location.reload();
			},
			error: function () {
				alert("삭제 fail")
			}
		});
	}
})


$('button[data-role= saveDisplay]').on("click", function () {
	var aArray = [];
	var count = 0;
	$('input[name=check]:checked' ).each(function () {
		if($(this).val() == null || $(this).val() == '' || $(this).val() == 'undefined') {
			aArray.push($(this).attr('id'));
			count++;
		}
		else{
			alert('이미 등록된 항목입니다.');
			count = -1 ;
			return false;
		}
	})
	if(count == -1) return false;
	else {
		let displayCount = 0;
		let displayPositions = [];
		let j = 0;
		for (let i = 1; i < 6; i++) {
			if ($('#row' + i + ' td').length > 5) {
				displayCount++;
			} else {
				displayPositions[j] = i;
				j++;
			}
		}
		if(count == 0) {
			alert("메인에 노출 등록할 공지사항을 체크해주세요")
			return false;
		}
		if (aArray.length + displayCount > 5) {
			alert("5개 이상 등록하실수 없습니다.")
		} else {

			if (!confirm("메인에 추가하시겠습니까?")) {
				return false;
			} else {
				$.ajax({
					url: "/bo/system/homepage/notice/updateDisplayOrder",
					type: "post",
					data: {
						noticeIds: aArray,
						displayPositions: displayPositions,
					},
					success: function () {
						alert("등록되었습니다.");
						location.reload();
					},
					error: function () {
						alert("메인 노출 fail")
					}
				});
			}
		}
	}
})
// function selectChange(option) {
// 	$.ajax({
// 		url: "/bo/system/homepage/faq/modal",
// 		method: "get",
// 		data: {memberTypeCd: option},
// 		datatype: "data",
// 	})
// 		.done(function(data){
// 			var selectnum = data.data;
// 			for (var i = 0; i < selectnum.length; i++) {
// 				var selectBox = '<option name="resetOption" value="'+ selectnum[i].categoryNm + '">' + selectnum[i].categoryNm + '</option>';
// 				$("#subMemberTypeSelect").append(selectBox);
// 			}
// 		})
// 		.fail(function (xhr, status, errorThrown) {
// 			alert("*****ajax fail*****");
// 		})
// }

/** [등록] 버튼 클릭시 */
$("button[data-role=form]").click(function() {
	window.location.href = "/bo/system/homepage/notice/form";
})

/** checkbox 전체 선택 */
$('input:checkbox[id=mainCheckbox]').on("click", function(){
	table.$("input[name='check']").prop('checked', $(this).is(":checked"));
});

// $('input[name=check]:checked').each(function () {
// 	aArray.push($(this).attr('id'));
// })


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
let start="";
let end=toStringByFormatting(new Date(), '-');

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
	$('#noticeStartDt').datepicker('setDate', start);
	$('#noticeEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
	const current = new Date();
	current.setDate(current.getDate() - 7);
	let lastWeek = toStringByFormatting(current, '-');
	start = lastWeek;
	end = toStringByFormatting(new Date(), '-');
	$('#noticeStartDt').datepicker('setDate', start);
	$('#noticeEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
	const current = new Date();
	current.setMonth(current.getMonth() - 1);
	let lastMonth = toStringByFormatting(current, '-');
	start = lastMonth;
	end = toStringByFormatting(new Date(), '-');
	$('#noticeStartDt').datepicker('setDate', start);
	$('#noticeEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
	start="";
	end=toStringByFormatting(new Date(), '-');
	$('#noticeStartDt').datepicker('setDate', null);
	$('#noticeEndDt').datepicker('setDate', null);
})

$('#noticeStartDt').change(function() {
	start = $(this).val();
})

$('#noticeEndDt').change(function() {
	end = $(this).val();
})


/** [공지] 검색 클릭시 */
$("button[data-role=search]").click(function() {

	let params = {
		searchStart: start,
		searchEnd: end
	};

	$("#datatable").DataTable().clear().destroy();
	$("#datatable").DataTable({

		ajax: {
			type: "post",
			url: "/bo/system/homepage/notice/search",
			data: params,
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
		order: [[4, 'desc']],
		searching : false,
		columns: [
			{
				render : function (data, type, row) {
					return "<td class='text-center x'><input type='checkbox' class='form-check-input m-0 align-middle' name='check'" + " value ='" + row.displayOrder + "' " + " id ='"+ row.noticeSeq +"' "+ "/></td>";
				}
			},
			{ "data": "noticeSeq" },
			{ render: function (data, type, row) {
					if (row.noticeTypeCd == 'EVENT') {
						return '이벤트';
					} else {
						return '공지사항';
					}
				} },
			{
				render: function(data, type, row) {
					if( row.fileSeq == null || row.fileSeq == '') return "<a style='font-weight: bold;' href='/bo/system/homepage/notice/detail?noticeSeq=" + row.noticeSeq + "'>" + row.title + "</a>";
					else return "<a style='font-weight: bold;' href='/bo/system/homepage/notice/detail?noticeSeq=" + row.noticeSeq + "'>" + row.title + "</a><i class=\"icon-clip\"></i>";
				}
			},
			{ "data": "createDt" },
			{ "data": "writer" ,"defaultContent": "-"},
			{ "data": "updateDt" },
			{ "data": "editor" ,"defaultContent": "-"},
			{ "data": "hit" },

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
			url: "/bo/system/homepage/notice/list",
			data : {noticeTypeCd : type},
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
		order: [[4, 'desc']],
		searching : false,
		columns: [
			{
				render : function (data, type, row) {
					return "<td class='text-center x'><input type='checkbox' class='form-check-input m-0 align-middle' name='check'" + " value ='" + row.displayOrder + "' " + " id ='"+ row.noticeSeq +"' "+ "/></td>";
				}
			},
			{ "data": "noticeSeq" },
			{ render: function (data, type, row) {
					if (row.noticeTypeCd == 'EVENT') {
						return '이벤트';
					} else {
						return '공지사항';
					}
				} },
			{
				render: function(data, type, row) {
					if( row.fileSeq == null || row.fileSeq == '') return "<a style='font-weight: bold;' href='/bo/system/homepage/notice/detail?noticeSeq=" + row.noticeSeq + "'>" + row.title + "</a>";
					else return "<a style='font-weight: bold;' href='/bo/system/homepage/notice/detail?noticeSeq=" + row.noticeSeq + "'>" + row.title + "</a><i class=\"icon-clip\"></i>";
				}
			},
			{ "data": "createDt" },
			{ "data": "writer" ,"defaultContent": "-"},
			{ "data": "updateDt" },
			{ "data": "editor" ,"defaultContent": "-"},
			{ "data": "hit" },

		],
		columnDefs: [
			{
				"targets": [8],
			}
		]
	})

})