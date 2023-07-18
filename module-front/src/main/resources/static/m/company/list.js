// [비밀번호 확인] 버튼 클릭 시
$("button[data-role=checkPwd]").click(function(){
    if(!Utils.alert(Utils.valid($("input[name=pwd]"), null, "비밀번호")).result) return false;

    $.ajax({
        url: "/m/company/checkPwd",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify({
            password: $("input[name=pwd]").val(),
            id: $("input[name=recommendId]").val()
        }),
        success: function (d) {
            if (d.status == 200) {
                if(d.serviceCode == "true"){
                    $(".step-2").show();
                    $(".step-1").hide();
                } else {
                    alert("비밀번호를 확인해주세요.");
                }
            } else {
                alert("처리 중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
            }
        },
        error: function(){
            alert("처리 중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        }
    });
});

// [전형결과 통보] 버튼 클릭 시
$("button[data-role=send]").click(function(){
    let flag = true;
    $(".result").each(function(){
        if(typeof $(this).find("input[type=radio]:checked").val() == "undefined"){
            flag = false;
        }
    });

    if(!flag){
        alert("통보되지 않은 대상이 있습니다.")
        return false;
    }

    let resultArr = [];
    $(".result").each(function(){
        resultArr.push({
            id: $(this).find("input[name=id]").val(),
            processStatus: $(this).find("input[type=radio]:checked").val()
        });
    });

    if (confirm("통보하시겠습니까?")) {
        $.ajax({
            url: "/m/company/update",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify({
                list: resultArr
            }),
            success: function (d) {
                if (d.status == 200) {
                    alert("채용결과 통보가 완료되었습니다.");
                } else {
                    alert("처리도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
                }
            },
            error: function(){
                alert("처리 중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
            }
        });
	}
});