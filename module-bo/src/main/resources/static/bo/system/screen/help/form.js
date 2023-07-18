/** 목록으로 */
$("button[data-role=list]").click(function() {
	location.href = "/bo/system/screen/help"
});

/** 등록 버튼 클릭시 */
$("button[data-role=save]").click(function() {
	let menuCd = $("form[name=insertHelp] input[name=menuCd]").val();
	let content = $("form[name=insertHelp] input[name=content]").val();

	/** 필수 값 */
	if (menuCd == "") {
		alert("메뉴 Cd를 입력해주세요");
		$("#menuCd").focus();
		return false;
	} else if (content == "") {
		$("#content").focus();
		return false;
	}

	let params = {
		menuCd: menuCd,
		content: content
	}

	console.log(params);

	if (confirm("등록하시겠습니까?"))
		console.log(params);
	$.ajax({
		url: "/bo/system/screen/help/insert",
		type: "post",
		data: params,
		success: function(data) {
			alert("등록되었습니다.");
			location.href = "/bo/system/screen/help";
		},
		error: function() {
			alert("등록에 실패하였습니다.");
		}
	});
	
});
