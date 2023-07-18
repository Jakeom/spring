/* [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	window.location.href = "/bo/system/org/group/modify?groupId=" + $("#groupDetailGroupId").val();
});
/* [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function(){
	window.location.href = "/bo/system/org/group";
});
/** [삭제] 버튼 클릭시*/
$("button[data-role=delete]").click(function(){
	if (confirm("삭제하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/bo/system/group/group/delete",
			contentType: "application/json",
			data: JSON.stringify({
				groupId :  $("#groupDetailGroupId").val()
				}),
			success: function(data) {
				alert(data.message);
				location.href = "/bo/system/org/group";
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});