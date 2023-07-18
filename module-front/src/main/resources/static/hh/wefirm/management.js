// 로고 파일 변경시
$("input[name=logoFiles]").change(function(e){
    if(!Utils.alert(Utils.fileCheck($(this), 10, true)).result) return false;
    $(this).closest(".form-upload").find(".file-name").text(e.target.files[0].name);
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm]").submit();
});

// 운영자로 추가
$("[data-role=addAdmin]").click(function(){
    if($("input[name=idArr]:checked").length != 1){
        alert("운영자로 지정 할 회원을 한명 선택해주세요");
        return false;
    }

    if(confirm("운영자로 등록하시겠습니까? 기존 지정했던 운영자는 삭제 처리됩니다.")){
        $.ajax({
            type: "post",
            url: "/hh/wefirm/add-admin",
            data: {
                memberId: $("input[name=idArr]:checked").val(),
                wefirmId: $("input[name=wefirmId]").val()
            },
            success: function (d) {
                if(d.status == 200){
                    alert("운영자 등록이 완료되었습니다.");
                    window.location.reload();
                } else {
                    alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            },
            error: function () {
                alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        });
    }
});

// We펌 정보 수정
$("[data-role=modifyWefirm]").click(function() {
    if(!Utils.alert(Utils.valid($("input[name=websiteUrl]"), null, "회사 홈페이지")).result) return false;
    if(!Utils.alert(Utils.valid($("textarea[name=description]"), null, "We펌 상세설명")).result) return false;

    let formData = new FormData();

    // 기본 정보
    let jsonData = {
        'websiteUrl': $("input[name=websiteUrl]").val(),
        'wefirmId': $("input[name=wefirmId]").val(),
        'description': $("textarea[name=description]").val(),
    }

    // logoFile
    if($("input[name=logoFiles]")[0].files != ""){
        Array.from($("input[name=logoFiles]")[0].files).map(e => formData.append('logoFiles', e));
    }

    formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));

    if(confirm("We펌 정보를 수정하시겠습니까?")) {
        // 전송
        $.ajax({
            url: "/hh/wefirm/update-wefirm",
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            success: function (d) {
                if (d.status == 200) {
                    alert("We펌 정보수정이 완료되었습니다.");
                    window.location.reload();
                } else {
                    alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            }
        });
    }
});

$("button[data-role=deleteWefirm]").click(function(){
   let recentPassword = $("input[name=recentPassword]").val();

    if (!confirm("탈퇴하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/hh/auth/current-password-check",
            data: {password : recentPassword},
            success: function (data) {
                console.log(data.serviceCode);
				if (data.serviceCode == "SUCCESS") { // 비밀번호 일치 시 탈퇴 request
					$.ajax({
						type: 'get',
						url: '/hh/wefirm/selectWefirmHhCnt',
						data: { wefirmId: $("input[name=wefirmId]").val() },
						success: function(data) {
							console.log(data.data);
							if (data.data == 1) {
								$.ajax({
									type: 'post',
									url: '/hh/wefirm/request-history',
									data: { wefirmId: $("input[name=wefirmId]").val() },
									success: function(data) {
										if (data.serviceCode == "DUPLICATE_REQUEST") {
											alert("이미 탈퇴신청을 하셨습니다.");
										} else {
											alert("탈퇴 신청이 완료되었습니다.");
										}
									}
								});
							} else {
								alert("WE펌 내 모든 회원을 탈퇴 시킨 후 탈퇴신청이 가능합니다")
								return false;
							}
						}
					})
                } else { // 비밀번호 불일치
                    alert("현재 비밀번호가 일치하지 않습니다.");
                }
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
});