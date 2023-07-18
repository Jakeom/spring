$("select").selectAjax();

/* textarea 내용 입력란 ckeditor 연결 */
CKEDITOR.replace('noticeContent');
CKEDITOR.config.autoParagraph = false;

/** int 컬럼 입력란 문자입력 방지 */
let replaceNotInt = /[^0-9]/gi;
$("#bannerOrder, #createSeq").on("focusout", function() {
	let x = $(this).val();
	if (x.length > 0) {
		if (x.match(replaceNotInt)) {
			x = x.replace(replaceNotInt, "");
		}
		$(this).val(x);
	}
}).on("keyup", function() {
	$(this).val($(this).val().replace(replaceNotInt, ""));
});

/** datepicker */
$(".form-datepicker").datepicker({
	minDate: 0,
	monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
	monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
	dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
	showMonthAfterYear: true,
	showOtherMonths: true,
	dateFormat: "yy-mm-dd",
	gotoCurrent: true,
	beforeShow: function beforeShow(input, inst) {
		$("#ui-datepicker-div").addClass("datepicker-box");
	}
})

/* [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function(){
	window.location.href = "/bo/system/homepage/notice";
});

/* [저장] 버튼 클릭시 */
$("button[data-role=save]").click(function(){
	let noticeTypeCd = $("form[name=insertNotice] select[name=selectType] option:selected").val();
	let noticeContent = $("form[name=insertNotice] textarea[name=noticeContent]").val();
	let noticeTitle = $("form[name=insertNotice] input[name=noticeTitle]").val();


	/** 필수값 입력 alert */
	if (noticeTypeCd == "") {
        alert("공지 종류 선택해주세요.");
        $("form[name=insertNotice] select[name=selectType]").focus();
        return false;
    }if (noticeContent == "") {
		alert("내용 입력해주세요");
		$("#noticeContent").focus();
		return false;
	}if (noticeTitle == "") {
		alert("제목 입력해주세요");
		$("#noticeTitle").focus();
		return false;
	}


	let params = {
		noticeTypeCd: noticeTypeCd,
		title: noticeTitle,
		content: noticeContent,
	}
	console.log(params);

	if (!confirm("등록하시겠습니까?")) {
	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/notice/insert",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function(data) {
				alert("등록되었습니다.");
				/** insert 후 리스트 페이지로 이동 */
				location.href = "/bo/system/homepage/notice";
			},
			error: function() {
				alert("등록에 실패하였습니다.");
			}
		});
	}
});