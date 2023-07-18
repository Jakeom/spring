$("select").selectAjax();

/* textarea 내용 입력란 ckeditor 연결 */
CKEDITOR.replace('infoContent');
CKEDITOR.config.autoParagraph = false;

/* [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function(){
	window.location.href = "/bo/management/operation/info";
});

/* [저장] 버튼 클릭시 */
$("button[data-role=save]").click(function(){
	let type = $("form[name=insertInfo] select[name=selectType] option:selected").val();
	let content = CKEDITOR.instances.infoContent.getData();
	console.info(content + type);

	/** 필수값 입력 alert */
	if (type == "") {
        alert("정보 종류 선택해주세요.");
        $("form[name=insertInfo] select[name=selectType]").focus();
        return false;
    }if (content == "") {
		alert("내용 입력해주세요");
		$("#infoContent").focus();
		return false;
	}

	let params = {
        infoType: type,
		content: content,
	}
	console.log(params);

	if (!confirm("등록하시겠습니까?")) {
	} else {
		$.ajax({
			type: "post",
			url: "/bo/management/operation/info/insert",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function(data) {
				alert("등록되었습니다.");
				/** insert 후 리스트 페이지로 이동 */
				location.href = "/bo/management/operation/info";
			},
			error: function() {
				alert("등록에 실패하였습니다.");
			}
		});
	}
});