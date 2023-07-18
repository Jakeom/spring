$("select").selectAjax();

/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function(){
	
	window.location.href = "/bo/system/homepage/notice/detail?noticeSeq=" + $("#noticeSeq").val();
});

/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	let noticeTypeCd = $("form[name=updateNotice] select[name=selectType] option:selected").val();
	let noticeSeq = $("form[name=updateNotice] input[name=noticeSeq]").val();
	let title = $("form[name=updateNotice] input[name=title]").val();
	let content = $("form[name=updateNotice] textarea[name=content]").val();
	
	

	/** 필수값 입력 alert */
	if (title == "") {
		alert("그룹 아이디를 입력해주세요");
		$("#groupId").focus();
		return false;
	} else if (content == "") {
		alert("그룹명을 입력해주세요");
		$("#groupNm").focus();
		return false;
	}
	
	let params = {
		noticeTypeCd : noticeTypeCd,
		noticeSeq: noticeSeq,
		content: content,
		title: title,	
	}

	console.log(params);

	if (!confirm("수정하시겠습니까?")) {
		return;
	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/notice/update",
			data: params,
			success: function(data) {
				alert("수정되었습니다.");
				
				location.href = "/bo/system/homepage/notice"
			},
			error: function() {
				alert("수정에 실패했습니다.");
			}
		});
	}
});
