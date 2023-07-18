$("select").selectAjax();
CKEDITOR.replace('infoContent');
CKEDITOR.config.autoParagraph = false;
/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function(){

	window.location.href = "/bo/management/operation/info/detail?mainDisplaySeq=" + $("#infoSeq").val();
});

/** [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	let type = $("select[name=selectType] option:selected").val();
	let content = CKEDITOR.instances.infoContent.getData();

	console.info(type);

	/** 필수값 입력 alert */
	if (content == "") {
		alert("내용 입력해주세요");
		$("#infoContent").focus();
		return false;
	}

	let params = {
        infoType : type,
		mainDisplaySeq : $("#infoSeq").val(),
		content: content,
	}

	console.log(params);

	if (!confirm("수정하시겠습니까?")) {

	} else {
		$.ajax({
			type: "post",
			url: "/bo/management/operation/info/update",
			data: params,
			success: function(data) {
				alert("수정되었습니다.");
				location.href = "/bo/management/operation/info/detail?mainDisplaySeq=" + $("#infoSeq").val();
			},
			error: function() {
				alert("수정에 실패했습니다.");
			}
		});
	}
});
