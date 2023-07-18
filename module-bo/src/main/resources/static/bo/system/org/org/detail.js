/* [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	window.location.href = "/bo/system/org/org/modify?orgId=" + $("#orgDetailOrgId").val();
});
/** [삭제] 버튼 클릭시*/
$("button[data-role=delete]").click(function(){
	if (confirm("삭제하시겠습니까?")); {
		$.ajax({
			type: "post",
			url: "/bo/system/org/org/delete",
			contentType: "application/json",
			data: JSON.stringify({
				orgId :  $("#orgDetailOrgId").val()
				}),
			success: function(data) {
				alert(data.message);
				location.href = "/bo/system/org/org";
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});
/** [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function(){
	window.location.href="/bo/system/org/org"
});