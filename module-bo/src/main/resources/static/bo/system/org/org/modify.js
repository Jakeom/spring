/** 목록으로 */
$("button[data-role=list]").click(function(){
	window.location.href = "/bo/system/org/org/detail?orgId=" + $("form[name=updateOrgForm] input[name=orgId]").val();
});
/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	
	let orgId = $("form[name=updateOrgForm] input[name=orgId]").val();
	let orgNm = $("form[name=updateOrgForm] input[name=orgNm]").val();
	let useFlag = $('form[name=updateOrgForm] input[name=useFlag]:checked').val();
	
	/** 필수값 입력 alert */
	if (orgId == "") {
		alert("조직 아이디를 입력해주세요");
		$("form[name=updateOrgForm] input[name=orgId]").focus();
		return false;
	} else if (orgNm == "") {
		alert("조직명을 입력해주세요");
		$("form[name=updateOrgForm] input[name=orgNm]").focus();
		return false;
	}
	let params = {
		orgId: orgId,
		orgNm: orgNm,
		useFlag: useFlag,
	}
	console.log(params);
	if (confirm("수정하시겠습니까?")) {	
		$.ajax({
			type: "post",
			url: "/bo/system/org/org/update",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function(data) {
				alert(data.message);
				location.href = "/bo/system/org/org"
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});
