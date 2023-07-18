/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function(){
	window.location.href = "/bo/system/org/group/detail?groupId=" + $("form[name=updateGroupForm] input[name=groupId]").val();
});
/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	
	let groupId = $("form[name=updateGroupForm] input[name=groupId]").val();
	let groupNm = $("form[name=updateGroupForm] input[name=groupNm]").val();
	let useFlag = $('form[name=updateGroupForm] input[name=useFlag]:checked').val();
	
	/** 필수값 입력 alert */
	if (groupId == "") {
		alert("그룹 아이디를 입력해주세요");
		$("form[name=updateGroupForm] input[name=groupId]").focus();
		return false;
	} else if (groupNm == "") {
		alert("그룹명을 입력해주세요");
		$("form[name=updateGroupForm] input[name=groupNm]").focus();
		return false;
	}
	let params = {
		groupId: groupId,
		groupNm: groupNm,
		useFlag: useFlag
	}
	console.log(params);
	if (!confirm("수정하시겠습니까?")) {

	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/group/group/update",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function(data) {
				alert(data.message);
				location.href = "/bo/system/org/group"
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});