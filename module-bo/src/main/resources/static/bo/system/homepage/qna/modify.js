let qnaId = $("#id").val();
CKEDITOR.replace('p_content');
CKEDITOR.config.autoParagraph = false;
/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function() {
	window.location.href = "/bo/system/homepage/qna/detail?id=" + $("#id").val();
});

/* [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function() {
	history.back();
});

/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function() {

	let formData = new FormData();
	let answer = CKEDITOR.instances.p_content.getData();
	if(answer == '' || answer == null) {
		alert("답변을 입력해주세요.")
		return false;
	}
	let jsonData = {
		'attachFileId' : $("#attachFileId").val(),
		'answer' : answer,
		'id' : qnaId,
	}

	// $("#attachment").each(function(){
	// 	Array.from($(this)[0].files).map(e => formData.append('qnaFiles', e));
	// });

	formData.set("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

	if (!confirm("수정하시겠습니까?")) {
		return;
	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/qna/update",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function(data) {
				alert("수정되었습니다.");
				location.href = "/bo/system/homepage/qna/detail?id=" + $("#id").val();
			},
			error: function() {
				alert("수정에 실패했습니다.");
			}
		});
	}
});

/** attach files */
// const dt = new DataTransfer();
//
// $("#attachment").on('change', function(e){
// 	for(var i = 0; i < this.files.length; i++){
// 		let fileBloc = $('<span/>', {class: 'file-block'}),
// 			fileName = $('<span/>', {class: 'name', text: this.files.item(i).name});
// 		fileBloc.append('<span class="file-delete"><span class="btn btn-outline-danger">삭제</button></span>')
// 			.append(fileName);
// 		$("#filesList > #files-names").append(fileBloc);
// 	};
//
// 	for (let file of this.files) {
// 		dt.items.add(file);
// 	}
//
// 	this.files = dt.files;
//
// 	$('span.file-delete').click(function(){
// 		let name = $(this).next('span.name').text();
//
// 		$(this).parent().remove();
// 		for(let i = 0; i < dt.items.length; i++){
//
// 			if(name === dt.items[i].getAsFile().name){
//
// 				dt.items.remove(i);
// 				continue;
// 			}
// 		}
//
// 		document.getElementById('attachment').files = dt.files;
// 	});
// });


// $('button[data-role=deleteFile]').on('click', function () {
// 	$(this).parent().remove();
// })
//
// $("button[data-role=deleteFile]").on("click", function () {
// 	if(!confirm("확인을 누르시면 파일이 수정과 상관없이 삭제됩니다.")) {
// 	} else {
// 		let fileId = $(this). attr("name");
// 		$("[name=" + fileId + "]").remove()
// 		$(this).closest("br").remove()
// 		$.ajax({
// 			type: "post",
// 			url: "/bo/system/homepage/notice/deleteFile",
// 			data: {
// 				id: fileId
// 			},
// 			success: function(data) {
// 			},
// 			error: function() {
// 				alert("삭제에 실패했습니다.");
// 			}
// 		});
// 	}
// })