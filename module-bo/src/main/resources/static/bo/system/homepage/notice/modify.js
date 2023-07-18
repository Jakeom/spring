$("select").selectAjax();
CKEDITOR.replace('noticeContent');
CKEDITOR.config.autoParagraph = false;
/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function(){

	window.location.href = "/bo/system/homepage/notice/detail?noticeSeq=" + $("#noticeSeq").val();
});

/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	let noticeTypeCd = $("form[name=updateNotice] select[name=selectType] option:selected").val();
	let noticeSeq = $("form[name=updateNotice] input[name=noticeSeq]").val();
	let title = $("form[name=updateNotice] input[name=title]").val();
	let content = CKEDITOR.instances.noticeContent.getData();



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

	let formData = new FormData();
	$("#notice-upload").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('noticeFiles', e));
	});
	let jsonData = {
		fileSeq : $("#noticeFileSeq").val(),
		noticeTypeCd : noticeTypeCd,
		noticeSeq: noticeSeq,
		content: content,
		title: title,
	}
	formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

	if (!confirm("수정하시겠습니까?")) {

	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/notice/update",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
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

$("[data-role=fileDelete]").on("click", function () {
	if(!confirm("확인을 누르시면 파일이 수정과 상관없이 삭제됩니다.")) {
	} else {
		let fileId = $(this).attr("name")
		$("[name=" + fileId + "]").remove()
		$(this).closest("br").remove()
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/notice/deleteFile",
			data: {
				id: fileId,
			},
			success: function(data) {
			},
			error: function() {
				alert("삭제에 실패했습니다.");
			}
		});
	}
})