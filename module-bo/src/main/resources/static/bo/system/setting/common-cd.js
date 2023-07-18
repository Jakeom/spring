let commonCodeDetailDetach = $('.common-code-detail-detach').removeClass('common-code-detail-detach').detach();

/** 그룹 코드 클릭시 */
$("tr[data-role=detailList]").click(function(e) {
	/** 그룹코드 선택시 버튼, 상세 목록 활성화 */
	$(".detail-form").css("display","block");
	
	let groupCd = $(this).data("groupCd");
	let groupNm = $(this).data("groupNm");
	let params = {
		groupCd : groupCd,
		groupNm : groupNm
	}
	/** 공통 코드 상세 등록 모달에서 그룹코드, 그룹이름 불러옴 */
	$("form[name=insertCdForm] input[name=groupCd]").val(groupCd);
	$("form[name=insertCdForm] input[name=groupNm]").val(groupNm);
	
	$.ajax({
		type: "post",
		url: "/bo/system/setting/common-cd/list",
		data: params,
		success: function(data) {
			$('#commonCodeDetailForm').empty();
			
			for (let i=0; i<data.data.length; i++) {
				let commonCodeDetailClone = commonCodeDetailDetach.clone();
				commonCodeDetailClone.find('td').eq(0).text(data.data[i].cd);
				commonCodeDetailClone.find('td').eq(1).text(data.data[i].cdNm);
				commonCodeDetailClone.find('td').eq(2).text(data.data[i].useFlag);
				commonCodeDetailClone.find('td').eq(3).find("button").data({
					"groupCd": data.data[i].groupCd,
					"cd": data.data[i].cd
				});
				$('#commonCodeDetailForm').append(commonCodeDetailClone);
			}
		},
	});
});
/** 공통 코드 등록 모달에서 [등록] 버튼 클릭시 */
$("button[data-role=groupCdSave]").click(function(){
	let groupCd = $("form[name=insertgroupCdForm] input[name=groupCd]").val();
	let groupNm = $("form[name=insertgroupCdForm] input[name=groupNm]").val();
	let cdNm = $("form[name=insertgroupCdForm] input[name=cdNm]").val();
	let cdOrder = $("form[name=insertgroupCdForm] input[name=cdOrder]").val();
	let useFlag = $('form[name=insertgroupCdForm] input[name=useFlag]:checked').val();
	let cdLevel = $("form[name=insertgroupCdForm] input[name=cdLevel]").val();

	/** 필수값 입력 alert */
	if (groupCd == "") {
		alert("그룹 코드를 입력해주세요");
		$("form[name=insertgroupCdForm] input[name=groupCd]").focus();
		return false;
	} else if (groupNm == "") {
		alert("그룹 이름을 입력해주세요");
		$("form[name=insertgroupCdForm] input[name=groupNm]").focus();
		return false;
	} else if (cdNm == "") {
		alert("코드 이름을 입력해주세요");
		$("form[name=insertgroupCdForm] input[name=cdNm]").focus();
		return false;
	} else if (cdOrder == "") {
		alert("코드 순서를 입력해주세요");
		$("form[name=insertgroupCdForm] input[name=cdOrder]").focus();
		return false;
	}

	let params = {
		groupCd: groupCd,
		groupNm: groupNm,
		cdNm: cdNm,
		cdOrder: cdOrder,
		useFlag: useFlag,
		cdLevel: cdLevel,
	}

	let id = $("form[name=insertgroupCdForm] input[name=groupCd]").val();
	
	$.ajax({
    	type: "get",
    	url: "/bo/system/setting/common-cd/check",
         data: {groupCd : id},
         success: function(result) {
            if(result.data == 0) {
            	if (confirm("등록하시겠습니까?")) {
					$.ajax({
						type: "post",
						url: "/bo/system/setting/common-cd/insert",
						data: JSON.stringify(params),
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						success: function(data) {
							alert(data.message);
							/** insert 후 리스트 페이지로 이동 */
							window.location.href="/bo/system/setting/common-cd"
						}
						,error: function(data) {
							alert(data.message);
						}
					})
				}
            } else {
               	alert("중복된 그룹코드 입니다.");
               	return false;
            }
         }
	}); 
});
/* 공통 코드 상세 등록 모달에서 [등록] 버튼 클릭시 */
$("button[data-role=cdSave]").click(function(){
	
	let groupCd = $("form[name=insertCdForm] input[name=groupCd]").val();
	let groupNm = $("form[name=insertCdForm] input[name=groupNm]").val();
	let cd = $("form[name=insertCdForm] input[name=cd]").val();
	let cdNm = $("form[name=insertCdForm] input[name=cdNm]").val();
	let cdOrder = $("form[name=insertCdForm] input[name=cdOrder]").val();
	let useFlag = $('form[name=insertCdForm] input[name=useFlag]:checked').val();
	let cdLevel = $("form[name=insertCdForm] input[name=cdLevel]").val();
	let cdReplace1 = $("form[name=insertCdForm] input[name=cdReplace1]").val();
	let cdReplace2 = $("form[name=insertCdForm] input[name=cdReplace2]").val();

	/** 필수값 입력 alert */
	 if (cd == "") {
		alert("코드를 입력해주세요");
		$("form[name=insertCdForm] input[name=cd]").focus();
		return false;
	} else if (cdNm == "") {
		alert("코드이름을 입력해주세요");
		$("form[name=insertCdForm] input[name=cdNm]").focus();
		return false;
	} else if (cdOrder == "") {
		alert("코드 순서를 입력해주세요");
		$("form[name=insertCdForm] input[name=cdOrder]").focus();
		return false;
	} else if (cdReplace1 == "") {
		$("form[name=insertCdForm] input[name=cdReplace1]").val(null);
	} else if (cdReplace2 == "") {
		$("form[name=insertCdForm] input[name=cdReplace2]").val(null);
	}
	let params = {
		groupCd : groupCd,
		groupNm : groupNm,
		cd : cd,
		cdNm: cdNm,
		cdOrder: cdOrder,
		useFlag: useFlag,
		cdLevel: cdLevel,
		cdReplace1: cdReplace1,
		cdReplace2: cdReplace2
	}
	let id = $("form[name=insertCdForm] input[name=cd]").val();
	$.ajax({
    	type: "get",
    	url: "/bo/system/setting/common-cd/cd/check",
         data: {cd : id},
         success: function(result) {
            if(result.data == 0) {
            	if (confirm("등록하시겠습니까?")) {
					$.ajax({
						type: "post",
						url: "/bo/system/setting/common-cd/detail/insert",
						data: JSON.stringify(params),
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						success: function(data) {
							alert(data.message);
							/** insert 후 리스트 페이지로 이동 */
							window.location.href="/bo/system/setting/common-cd"
						}
						,error: function(data) {
							alert(data.message);
						}
					})
				}
            } else {
               	alert("중복된 코드 입니다.");
               	return false;
            }
         }
	}); 
});
/** [수정]버튼 클릭 후 그룹 코드 상세 모달에서 [저장] 버튼 클릭시 */
$("button[data-role=groupCdUpdate]").click(function(){
	
	let groupCd = $("form[name=updateGroupCdForm] input[name=groupCd]").val();
	let groupNm = $("form[name=updateGroupCdForm] input[name=groupNm]").val();
	let cdOrder = $("form[name=updateGroupCdForm] input[name=cdOrder]").val();
	let useFlag = $('form[name=updateGroupCdForm] input[name=groupCdUseFlag]:checked').val();
	
	if (confirm("수정하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/bo/system/setting/common-cd/update",
			contentType: "application/json",
			data: JSON.stringify({
				groupCd : groupCd,
				groupNm : groupNm,
				cdOrder : cdOrder,
				useFlag : useFlag
			}),
			success: function(data) {
				alert(data.message);
				window.location.href="/bo/system/setting/common-cd"
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});
/** [수정]버튼 클릭 후 코드 상세 모달에서 [저장] 버튼 클릭시 */
$("button[data-role=cdUpdate]").click(function(){
	
	let cd = $("form[name=updateCdForm] input[name=cd]").val();
	let cdNm = $("form[name=updateCdForm] input[name=cdNm]").val();
	let cdOrder = $("form[name=updateCdForm] input[name=cdOrder]").val();
	let useFlag = $('form[name=updateCdForm] input[name=cdUseFlag]:checked').val();
	let cdReplace1 = $("form[name=updateCdForm] input[name=cdReplace1]").val();
	let cdReplace2 = $("form[name=updateCdForm] input[name=cdReplace2]").val();
	
	if (confirm("수정하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/bo/system/setting/common-cd/detail/update",
			contentType: "application/json",
			data: JSON.stringify({
				cd : cd,
				cdNm : cdNm,
				cdOrder : cdOrder,
				useFlag : useFlag,
				cdReplace1 : cdReplace1,
				cdReplace2 : cdReplace2
			}),
			success: function(data) {
				alert(data.message);
				window.location.href="/bo/system/setting/common-cd"
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});
/** 그룹 코드 상세 모달에서 [삭제] 버튼 클릭시 */
$("button[data-role=groupCdDelete]").click(function(){
	
	let groupCd = $("form[name=updateGroupCdForm] input[name=groupCd]").val();
	if (confirm("삭제하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/bo/system/setting/common-cd/delete",
			contentType: "application/json",
			data: JSON.stringify({
				groupCd : groupCd
			}),
			success: function(data) {
				alert(data.message);
				window.location.href="/bo/system/setting/common-cd"
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});
/** 코드 상세 모달에서 [삭제] 버튼 클릭시 */
$("button[data-role=cdDelete]").click(function(){
	
	let cd = $("form[name=updateCdForm] input[name=cd]").val();
	if (confirm("삭제하시겠습니까?")) {
		$.ajax({
			type: "post",
			url: "/bo/system/setting/common-cd/detail/delete",
			contentType: "application/json",
			data: JSON.stringify({
				cd : cd
			}),
			success: function(data) {
				alert(data.message);
				window.location.href="/bo/system/setting/common-cd"
			},
			error: function() {
				alert(data.message);
			}
		});
	}
});
/** 그룹 코드(cd_level=1)에서 [수정] 버튼 클릭시 */
$("button[data-role=groupCdModify]").click(function(e){
	e.stopPropagation();
	let groupCd = $(this).data("groupCd");
	let params = {
		groupCd : groupCd
	}
	$.ajax({
		type: "post",
		url: "/bo/system/setting/common-cd/info",
		data: params,
		success: function(data) {
			$("form[name=updateGroupCdForm] input[name=groupCd]").val(data.data.groupCd);
			$("form[name=updateGroupCdForm] input[name=groupNm]").val(data.data.groupNm);
			$("form[name=updateGroupCdForm] input[name=cdNm]").val(data.data.cdNm);
			$("form[name=updateGroupCdForm] input[name=cdOrder]").val(data.data.cdOrder);
			$("form[name=updateGroupCdForm] input[name=cdLevel]").val(data.data.cdLevel);
			if(data.data.useFlag == "Y") {
				$("#updateGroupCdFormUseY").prop("checked",true)
			} else {
				$("#updateGroupCdFormUseN").prop("checked",true)
			}
			$("#groupCdDetailModal").modal("show");
		},
	});
});
/** 코드(cd_level=2)에서 [수정] 버튼 클릭시 */
$(document).on("click", "button[data-role=cdModify]", function(e){
	e.stopPropagation();
	let groupCd = $(this).data("groupCd");
	let cd = $(this).data("cd");
	let params = {
		groupCd : groupCd,
		cd : cd
	}
	$.ajax({
		type: "post",
		url: "/bo/system/setting/common-cd/detail/info",
		data: params,
		success: function(data) {
			$("form[name=updateCdForm] input[name=groupCd]").val(data.data.groupCd);
			$("form[name=updateCdForm] input[name=groupNm]").val(data.data.groupNm);
			$("form[name=updateCdForm] input[name=cd]").val(data.data.cd);
			$("form[name=updateCdForm] input[name=cdNm]").val(data.data.cdNm);
			$("form[name=updateCdForm] input[name=cdOrder]").val(data.data.cdOrder);
			$("form[name=updateCdForm] input[name=cdLevel]").val(data.data.cdLevel);
			if(data.data.useFlag == "Y") {
				$("#updateCdFormUseFlagY").prop("checked",true)
			} else {
				$("#updateCdFormUseFlagN").prop("checked",true)
			}
			$("form[name=updateCdForm] input[name=cdReplace1]").val(data.data.cdReplace1);
			$("form[name=updateCdForm] input[name=cdReplace2]").val(data.data.cdReplace2);
			$("#cdDetailModal").modal("show");
		},
	});
});