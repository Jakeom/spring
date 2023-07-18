$(document).ready(function () {
	if($(".fileCheck").length != 0) {
		$("input[name=fileDeleteCheck]").val("N")
	}
})

/** int 컬럼 입력란 문자입력 방지 */
let replaceNotInt = /[^0-9]/gi;
$("form[name=updateBanner] input[name=bannerOrder]").on("focusout", function() {
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
/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function(){
	history.back();
});
/** [저장] 버튼 클릭시 */
$("button[data-role=update]").click(function(){

	let bannerSeq = $("input[name=bannerSeq]").val();
	let bannerTypeCd = $("form[name=updateBanner] select[name=selectType] option:selected").val();
	let bannerNm = $("form[name=updateBanner] input[name=bannerNm]").val();
	let bannerOrder = $("form[name=updateBanner] input[name=bannerOrder]").val();
	let useFlag = $('form[name=updateBanner] input[name=useFlag]:checked').val();
	let bannerStartDt = $("form[name=updateBanner] input[name=bannerStartDt]").val();
	let bannerEndDt = $("form[name=updateBanner] input[name=bannerEndDt]").val();
	let linkUrl = $("form[name=updateBanner] input[name=linkUrl]").val().trim();

	/** 필수값 입력 alert */
	if (bannerTypeCd == "") {
		alert("배너 종류를 입력해주세요");
		$("form[name=updateBanner] select[name=selectType]").focus();
		return false;
	} else if (bannerNm == "") {
		alert("배너 이름을 입력해주세요");
		$("form[name=updateBanner] input[name=bannerNm]").focus();
		return false;
	} else if (bannerOrder == "") {
		alert("배너순서를 입력해주세요");
		$("form[name=updateBanner] input[name=bannerOrder]").focus();
		return false;
	} else if (bannerStartDt == "") {
		alert("배너시작일을 입력해주세요");
		$("form[name=updateBanner] input[name=bannerStartDt]").focus();
		return false;
	} else if (bannerStartDt > bannerEndDt) {
		alert("날짜를 확인해주세요.");
		$("form[name=updateBanner] input[name=bannerEndDt]").focus();
		return false;
	} else if (linkUrl == "") {
		alert("링크 URL을 입력해주세요");
		$("form[name=updateBanner] input[name=linkUrl]").focus();
		return false;
	}
	if($("input[name=fileDeleteCheck]").val() == 'N' && $("#banner-upload").val() != '') {
		alert("파일 삭제를 진행해주세요.")
		return false;
	}

	let formData = new FormData();
	$("#banner-upload").each(function(){
		Array.from($(this)[0].files).map(e => formData.append('bannerFiles', e));
	});
	let jsonData
	if($("#banner-upload").val() == null || $("#banner-upload").val() == '') {
		jsonData = {
			bannerSeq: bannerSeq,
			bannerTypeCd: bannerTypeCd,
			bannerNm: bannerNm,
			bannerOrder: bannerOrder,
			useFlag: useFlag,
			bannerStartDt: bannerStartDt,
			bannerEndDt: bannerEndDt,
			linkUrl: linkUrl,
			fileSeq: $("input[name=fileSeq]").val()
		}
	} else {
		jsonData = {
			bannerSeq: bannerSeq,
			bannerTypeCd: bannerTypeCd,
			bannerNm: bannerNm,
			bannerOrder: bannerOrder,
			useFlag: useFlag,
			bannerStartDt: bannerStartDt,
			bannerEndDt: bannerEndDt,
			linkUrl: linkUrl,
			fileSeq: $(".fileCheck").attr("name")
		}
	}
	formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
	if (!confirm("수정하시겠습니까?")) {
		return false;
	} else {
		$.ajax({
			type: "post",
			url: "/bo/system/screen/banner/update",
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function(data) {
				alert("수정되었습니다.");

				location.href = "/bo/system/screen/banner"
			},
			error: function() {
				alert("수정에 실패했습니다.");
			}
		});
	}
});

$("[data-role=fileDelete]").on("click", function () {
	if(!confirm("확인을 누르시면 파일이 수정과 상관없이 삭제됩니다.")) {
	} else {
		let fileId = $(this).attr("name")
		$("[name=" + fileId + "]").remove()
		$(this).closest("br").remove()
		$.ajax({
			type: "post",
			url: "/bo/system/homepage/banner/deleteFile",
			data: {
				id: fileId,
			},
			success: function(data) {
				$("input[name=fileDeleteCheck]").val("Y");
			},
			error: function() {
				alert("삭제에 실패했습니다.");
			}
		});
	}
})

$("#banner-upload").on("change", function () {
	if( $("#banner-upload").val() == '' ) {
		$('#newBannerImg').attr('src' , '');
	}
	$('#newBannerImg').css({ 'display' : '' });
	readURL(this);
})

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$('#newBannerImg').attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
}