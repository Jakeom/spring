/** int 컬럼 입력란 문자입력 방지 */
let replaceNotInt = /[^0-9]/gi;
$("input[name=phone], input[name=licenseNumber]").on("focusout", function() {
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

/** 전체선택 체크박스 변경시 */
$("input[name=checkAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=checkCompany]").prop("checked", true)
    } else {
        $("input[name=checkCompany]").prop("checked", false);
    }
});

/** 체크박스 변경시 */
$("input[name=checkCompany]").change(checkAll);
function checkAll() {
    if ($("input[name=checkCompany]").length === $("input[name=checkCompany]:checked").length) {
        $("input[name=checkAll]").prop("checked", true);
    } else {
        $("input[name=checkAll]").prop("checked", false);
    }
}

/** 고객사 등록 */ 
$("button[data-role=insert]").click(function(){
	
	let id = $("input[name=id]").val();
	let companyName = $("input[name=companyName]").val().trim();
	let licenseNumber = $("input[name=licenseNumber]").val().trim();
	let location = $("input[name=location]").val().trim();
	let name = $("input[name=name]").val().trim();
	let email = $("input[name=email]").val().trim();
	let phone = $("input[name=phone]").val().trim();
	
	/** 이메일 형식 체크 */
    let regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
	
	/** 필수값 체크 */
	if (companyName == "") {
		alert("기업명을 입력해주세요");
		$("input[name=companyName]").focus();
		return false;
	} else if (licenseNumber == "") {
		alert("사업자 등록번호를 입력해주세요");
		$("input[name=licenseNumber]").focus();
		return false;
	} else if (location == "") {
		alert("소새지를 입력해주세요");
		$("input[name=location]").focus();
		return false;
	} 
	
	let params = {
		id : id,
		companyName : companyName,
		licenseNumber : licenseNumber,
		location : location,
		name : name,
		email : email,
		phone : phone,
	}
	
	if(!confirm("등록하시겠습니까?")) {
        return false;
    } else {
       $.ajax({
		url: '/hh/company/insertHrCompany',
		type: 'POST',
		contentType: "application/json",
		data: JSON.stringify(params),
		success: function(data) {
            if(data.status == 200){
				alert('등록되었습니다.');
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
	})
   }
});

/** 고객사 삭제 */
$("button[data-role=delete]").click(function(){
	
	let idArray = [];
	$("input[name=checkCompany]:checked").each(function () {
        let chk = $(this).val();
        idArray.push(chk);
    })
	let params = {
		idArray : idArray
	}
	
	if($("input[name=checkCompany]:checked").length == 0){
		alert("체크된 고객사가 없습니다.");
		return false;
	}
	
	if(!confirm("삭제하시겠습니까?")) {
		return false;
	} else{
		    $.ajax({
            url: "/hh/company/deleteHrCompany",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("삭제되었습니다.");
                window.location.href = "/hh/company/company-list"
            },
            error: function () {
                alert("삭제에 실패했습니다.")
            }
        });
	}
})