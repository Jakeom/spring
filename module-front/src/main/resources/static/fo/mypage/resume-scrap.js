// paging
$("a[data-role=btnGoPage]").click(function() {
	let page = $(this).data("page");
	$("form[name=searchForm] input[name=page]").val(page);
	$("form[name=searchForm]").submit();
});

//지원여부
$("[data-role=checkApplicant]").click(function(){
	let id = $(this).data('id');
	$.ajax({
		url: '/fo/position/check-applicant',
		type: 'POST',
		data: {
			id : $(this).data('id')
		},
		success: function(data) {
			if(data.status == 200 && data.data > 0){
				alert('이미 지원하셨습니다.');
			}else if(data.status == 200 && data.data == 0){
				location.href = '/fo/user/jop/posting/apply?id='+id
			}else {
				alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
			}
		},
		error: function(){
			alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
		}
	});
})

$("[data-role=goReadingForm]").click(function(){
	let arr = localStorage.getItem("watched")
	arr = JSON.parse(arr);
	$('form[name=resumeReadingForm] input[name=positionIdList]').val(arr)
	$('form[name=resumeReadingForm]').submit();
})

//스크랩
$("[data-role=clickScrap]").click(function(){
	$(this).toggleClass("active");
	$.ajax({
		url: '/fo/position/scrap',
		type: 'POST',
		data: {
			scrap : $(this).attr("class").includes("active")? 'addScrap':'delete',
			positionId : $(this).data('positionid')
		},
		success: function(data) {
			if(data.status!=200){
				alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
			}
		},
		error: function(){
			alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
		}
	});
})