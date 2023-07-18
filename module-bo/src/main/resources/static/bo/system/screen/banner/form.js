/** int 컬럼 입력란 문자입력 방지 */
let replaceNotInt = /[^0-9]/gi;
$("form[name=insertBannerForm] input[name=bannerOrder]").on("focusout", function() {
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
	window.location.href = "/bo/system/screen/banner";
});
/* [저장] 버튼 클릭시 */
$("button[data-role=save]").click(function(){
	let bannerTypeCd = $("form[name=insertBannerForm] select[name=selectType] option:selected").val();
	let bannerNm = $("form[name=insertBannerForm] input[name=bannerNm]").val();
	let bannerOrder = $("form[name=insertBannerForm] input[name=bannerOrder]").val();
	let useFlag = $('form[name=insertBannerForm] input[name=useFlag]:checked').val();
	let bannerStartDt = $("form[name=insertBannerForm] input[name=bannerStartDt]").val();
	let bannerEndDt = $("form[name=insertBannerForm] input[name=bannerEndDt]").val();
	let linkUrl = $("form[name=insertBannerForm] input[name=linkUrl]").val().trim();

	/** 필수값 입력 alert */
	if (bannerTypeCd == "") {
		alert("배너 종류를 입력해주세요");
		$("form[name=insertBannerForm] select[name=selectType]").focus();
		return false;
	} else if (bannerNm == "") {
		alert("배너 이름을 입력해주세요");
		$("form[name=insertBannerForm] input[name=bannerNm]").focus();
		return false;
	} else if (bannerOrder == "") {
		alert("배너순서를 입력해주세요");
		$("form[name=insertBannerForm] input[name=bannerOrder]").focus();
		return false;
	} else if (bannerStartDt == "") {
		alert("팝업시작일을 입력해주세요");
		$("form[name=insertBannerForm] input[name=bannerStartDt]").focus();
		return false;
	} else if (bannerStartDt > bannerEndDt) {
		alert("날짜를 확인해주세요.");
		$("form[name=insertBannerForm] input[name=bannerEndDt]").focus();
		return false;
	} else if (linkUrl == "") {
		alert("링크 URL을 입력해주세요");
		$("form[name=insertBannerForm] input[name=linkUrl]").focus();
		return false;
	}
	let params = {
		bannerTypeCd: bannerTypeCd,
		bannerNm: bannerNm,
		bannerOrder: bannerOrder,
		useFlag: useFlag,
		bannerStartDt: bannerStartDt,
		bannerEndDt: bannerEndDt,
		linkUrl: linkUrl
	}
	console.log(params);

	if (!confirm("등록하시겠습니까?")) {
	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/screen/banner/insert",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function(data) {
				alert(data.message);
				/** insert 후 리스트 페이지로 이동 */
				location.href = "/bo/system/screen/banner";
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});