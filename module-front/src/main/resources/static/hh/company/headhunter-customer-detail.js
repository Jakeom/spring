/** 전역변수 선언 */
let addManager = $('tbody.manager-add').clone();
let contact = $('.trClone').removeAttr('style').detach();

$(".manager-list").each(function(){
	$(this).text(Utils.formatPhoneNumber($(this).text()));
});

$(".company-list").each(function(){
	$(this).text(Utils.formatPhoneNumber($(this).text()));
});

if($("input[name=mode]").val() != ""){
	$("#" + $("input[name=mode]").val() + "-tab").trigger("click");
	$(document.activeElement).blur();
}

$(".nav-link").click(function(){
	$("input[name=mode]").val($(this).attr("aria-controls"));
});

/** int 컬럼 입력란 문자입력 방지 */
$("input[name=licenseNumber], input[name=establishDate], input[name=phone]").keyup(e => Utils.onlyNumber($(e.target))); // 입력값 강제로 숫자만 입력받도록 처리

/** 상세 페이지에서 [수정]버튼 클릭시 */
$("button[data-role=update]").click(function(){
	
	$("#detail").css("display", "none");
	$("#inputModify").css("display", "");
	
	$("#managerDetail").css("display", "none");
	$("#managerModify").css("display", "");
	
	$("#update").css("display", "none");
	$("#goList").css("display", "none");
	$("#managerAdd").css("display", "");
	$("#modifySave").css("display", "");
	$("#modifyCancel").css("display", "");
	
});
/** 기업정보수정 에서 [취소] 버튼 클릭 시 */
$("button[data-role=cancel]").click(function(){
	history.go(-1);
});
/** 기업정보수정 에서 [저장] 버튼 클릭 시 */
$("button[data-role=save]").click(function(){
	let id = $("#inputModify input[name=id]").val();
	let companyName = $("#inputModify input[name=companyName]").val().trim();
	let licenseNumber = $("#inputModify input[name=licenseNumber]").val().trim();
	let ceo = $("#inputModify input[name=ceo]").val().trim();
	let establishDate = $("#inputModify input[name=establishDate]").val().trim();
	let phone = $("#inputModify input[name=phone]").val().trim();
	let address = $("#inputModify input[name=address]").val().trim();
	let companyScale = $("#inputModify input[name=companyScale]").val().trim();
	let industry = $("#inputModify input[name=industry]").val().trim();
	let marketListing = $("#inputModify input[name=marketListing]").val().trim();
	let closed = $("#inputModify select[name=closed] option:selected").val();
	let companyStatus = $("#inputModify input[name=companyStatus]").val().trim();
	
	/** 필수값 입력 alert */
	if(!Utils.alert(Utils.valid($("#inputModify input[name=companyName]"), null, "기업명")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=licenseNumber]"), null, "사업자등록번호")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=ceo]"), null, "대표자")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=establishDate]"), null, "설립일")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=phone]"), null, "전화번호")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=address]"), null, "도로명주소")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=companyScale]"), null, "기업규모")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=industry]"), null, "업종")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=marketListing]"), null, "상장")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify select[name=closed] option:selected"), null, "휴 · 폐업여부")).result) return false;
	if(!Utils.alert(Utils.valid($("#inputModify input[name=companyStatus]"), null, "기업상태")).result) return false;
	
	let flag = true;
	let managerList = [];
	$(".manager-area").each(function(){
		let self = $(this);
		flag = Utils.alert(Utils.valid(self.find("input[name=name]"), null, "기업담당자 - 이름")).result;
		flag = Utils.alert(Utils.valid(self.find("input[name=email]"), null, "기업담당자 - 이메일주소")).result;
		flag = Utils.alert(Utils.valid(self.find("input[name=phone]"), null, "기업담당자 - 유선연락처")).result;
		managerList.push({
			"hrCompanyId":id,
			"name": self.find("input[name=name]").val(),
			"email": self.find("input[name=email]").val(),
			"phone": self.find("input[name=phone]").val()
		});
	});
	
	if(!flag){
		return false;
	}
	
	let params = {
		id: id,
		companyName: companyName,
		licenseNumber: licenseNumber,
		ceo: ceo,
		establishDate: establishDate,
		phone: phone,
		address: address,
		companyScale: companyScale,
		industry: industry,
		marketListing: marketListing,
		closed: closed,
		companyStatus: companyStatus,
		managerList: managerList
	}
	
	console.log(params);
	if (!confirm("수정하시겠습니까?")) {
		return false;
	} else {
		$.ajax({
			type: "POST",
			url: "/hh/company/updateHrCompany",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function() {
				alert("수정되었습니다.");
				window.location.href = '/hh/company/headhunter-customer-detail?id=' + id + '&mode=home';
			},
			error: function() {
				alert("수정에 실패했습니다.");
			}
		});
	}
});
/** 메모에서 [등록] 버튼 클릭 시 */
$("button[data-role=insertMemo]").click(function(){
	let id = $("input[name=id]").val();
	let content = $("input[name=content]").val().trim();
	
	if (content == "") {
		alert("메모를 입력해주세요");
		$("input[name=content]").focus();
		return false;
	}
	let params = {
		id: id,
		content: content
	}
	
	if (!confirm("메모를 등록하시겠습니까?")) {
		return false;
	} else {
		$.ajax({
			type: "POST",
			url: "/hh/company/insertHrMemo",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function() {
				alert("등록되었습니다.");
				window.location.href = '/hh/company/headhunter-customer-detail?id=' + id + '&mode=contact';
			},
			error: function() {
				alert("등록에 실패했습니다.");
			}
		});
	}
});
/** 메모에서 [삭제] 버튼 클릭 시 */
$("button[data-role=memoDelete]").click(function(){
	let self = $(this);
	let id = self.val();
	let params = {
		id : id
	}

	if (!confirm("메모를 삭제하시겠습니까?")) {
		return false;
	} else {
		$.ajax({
			type: "POST",
			url: "/hh/company/deleteHrMemo",
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function(d) {
				alert("삭제가 성공하였습니다.");
				self.closest(".memo-area").remove();
			},
			error: function() {
				alert("삭제에 실패했습니다.");
			}
		});
	}
});
/** 인사담당자 추가 */
$(document).on("click", "[data-role=managerAdd]", function (){
    $('#managerModify').find('tbody').append(contact.clone());
    
    $("#managerId").attr('id', +$("#managerId").attr('id') + 1);
});
/** [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function(){
	window.location.href="/hh/company/company-list"
})
/** 인사담당자 추가 시 정보 입력없이 삭제 */
$(document).on('click', 'button[data-role=managerClose]',function(e) {
	$(this).closest(".manager-area").remove();
});