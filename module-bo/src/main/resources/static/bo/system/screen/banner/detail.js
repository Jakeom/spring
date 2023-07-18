/* [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function(){
	window.location.href = "/bo/system/screen/banner";
});
/* [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function(){
	window.location.href = "/bo/system/screen/banner/modify?bannerSeq=" + $("#bannerDetailBannerSeq").val();
});
/** 삭제 */
$("button[data-role=delete]").click(function(){

	let bannerSeq = $("#bannerDetailBannerSeq").val();
	console.log(bannerSeq);
	if (confirm("삭제하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/bo/system/screen/banner/delete",
			data:{ 
				bannerSeq: bannerSeq 
				},
			success: function(data) {
				alert(data.message);
				location.href = "/bo/system/screen/banner";
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});
/** 상세 화면에서 라디오 버튼 수정 불가  */
$(':radio:not(:checked)').attr('disabled', true);