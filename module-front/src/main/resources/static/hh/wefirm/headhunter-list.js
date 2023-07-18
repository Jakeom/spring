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
        $("input[name=checkHeadhunter]").prop("checked", true)
    } else {
        $("input[name=checkHeadhunter]").prop("checked", false);
    }
});

/** 체크박스 변경시 */
$("input[name=checkHeadhunter]").change(checkAll);
function checkAll() {
    if ($("input[name=checkHeadhunter]").length === $("input[name=checkHeadhunter]:checked").length) {
        $("input[name=checkAll]").prop("checked", true);
    } else {
        $("input[name=checkAll]").prop("checked", false);
    }
}

/** 헤드헌터 탈퇴 */
$("button[data-role=delete]").click(function(){
	
	let idArray = [];
	$("input[name=checkHeadhunter]:checked").each(function () {
        let chk = $(this).val();
        idArray.push(chk);
    })
	let params = {
		idArray : idArray
	}
	
	if($("input[name=checkHeadhunter]:checked").length == 0){
		alert("체크된 헤드헌터가 없습니다.");
		return false;
	}
	
	if(!confirm("강제탈퇴 하시겠습니까?")) {
		return false;
	} else{
		    $.ajax({
            url: "/hh/wefirm/deleteHeadhunter",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("탈퇴되었습니다.");
                window.location.href = "/hh/wefirm/headhunter-list"
            },
            error: function () {
                alert("오류가 발생했습니다. 관리자에게 문의하세요.")
            }
        });
	}
})