// paging
$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

/** 전체선택 체크박스 변경시 */
$("input[name=checkAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=check]").prop("checked", true)
    } else {
        $("input[name=check]").prop("checked", false);
    }
});

/** 체크박스 변경시 */
$("input[name=check]").change(checkAll);
function checkAll() {
    if ($("input[name=check]").length === $("input[name=check]:checked").length) {
        $("input[name=checkAll]").prop("checked", true);
    } else {
        $("input[name=checkAll]").prop("checked", false);
    }
}

/** 가입신청 승인 */
$("button[data-role=AllApproval]").click(function(){
	
	let idArray = [];
	$("input[name=check]:checked").each(function () {
        let chk = $(this).val();
        idArray.push(chk);
    })
    let hhWefirmId = $(this).val();
    
	let params = {
		idArray : idArray,
		hhWefirmId : hhWefirmId
	}
	
	if($("input[name=check]:checked").length == 0){
		alert("체크된 헤드헌터가 없습니다.");
		return false;
	}
	
	if(!confirm("가입을 승인 하시겠습니까?")) {
		return false;
	} else{
		    $.ajax({
            url: "/hh/wefirm/approvalHeadhunter",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("승인 되었습니다.");
                window.location.href = "/hh/wefirm/subscription-approval"
            },
            error: function () {
                alert("오류가 발생했습니다. 관리자에게 문의하세요.")
            }
        });
	}
})

/** 가입신청 승인 */
$("button[data-role=approval]").click(function(){
	
	
	let idArray = [];
	let chk = $(this).val();
        idArray.push(chk);
	let params = {
		idArray : idArray
	}
	
	if(!confirm("가입을 승인 하시겠습니까?")) {
		return false;
	} else{
		    $.ajax({
            url: "/hh/wefirm/approvalHeadhunter",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("승인 되었습니다.");
                window.location.href = "/hh/wefirm/subscription-approval"
            },
            error: function () {
                alert("오류가 발생했습니다. 관리자에게 문의하세요.")
            }
        });
	}
})