$(document).ready(function(){
    $('[data-role=careerModify]').hide();
    $('[data-role=careerDelete]').hide();
    $("span[name=phone]").text(Utils.formatPhoneNumber($("span[name=phone]").text())); // 핸드폰번호 정규식
})

$("button[data-role=payment]").click(function(){
    if(!confirm("2000포인트를 사용하여 열람권을 구매하시겠습니까?")) {
        return false;
    } else {
        let myPoint = $("input[name=myPoint]").val();
        console.log("현재 보유하고 있는 포인트 :: " + myPoint);

        if(myPoint >= 2000) { // 열람권사용 -2000

            let params = {
                memberId : $("input[name=memberId]").val(),
                resumeId : $("input[name=resumeId]").val()
            }

            $.ajax({
                url: "/m/update/point",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(params),
                success: function () {
                    alert("열람권 구매가 완료되었습니다.");
                    window.location.reload();
                },
                error: function () {
                    alert("예외가 발생했습니다. 관리자에게 문의하세요");
                }
            });
        }else { // 총 포인트 < 2000 -> alert
            alert("보유하신 포인트가 부족합니다.\n" +
                "홈페이지에서 포인트 충전 후 다시 이용하실 수 있습니다.\n");
            return false;
        }
    }
});

// 포지션 추천 버튼 클릭 시
$("button[data-role=register]").click(function() {
    if($("select[name=doingPosition]").val() == "") {
        alert("포지션을 선택해주세요.");
        return false;
    }

    let params = {
        apMemberId : $("input[name=apMemberId]").val(),
        hhMemberId : $("input[name=memberId]").val(),
        positionId : $("select[name=doingPosition]").val()
    }

    if (!confirm("추가하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/hh/position/recommend-duplicate",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (d) {
                console.log(d.serviceCode);
                if(d.serviceCode == "DUPLICATE") {
                    alert("해당 포지션에 추천 이력이 존재합니다.");
                    return false;
                } else {
                    $.ajax({
                        url: "/hh/position/recommend",
                        type: "post",
                        contentType: "application/json",
                        data: JSON.stringify(params),
                        success: function (d) {
                            if(d.status == 200) {
                                alert("추가되었습니다.");
                                window.location.reload();
                            } else {
                                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
                            }
                        },
                        error: function () {
                            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
                        }
                    });
                }
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        });
    }
})


// 내인재 삭제 버튼 클릭 시
$("button[data-role=delete]").click(function() {

    let params = {
        memberId : $("input[name=memberId]").val(),
        resumeId : $("input[name=resumeId]").val()
    }

    $.ajax({
        url: "/m/delete/my-pool",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (d) {
            if(d.status == 200) {
                alert("삭제되었습니다.");
                window.location.reload();
            } else {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            }
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
        }
    });
})