$(document).ready(function () {
    let useFlag = $("#blacklistUseFlag").val()
    if(useFlag == 0) {
        $("input[name=status][value='0']").prop("checked",true);
    } else {
        $("input[name=status][value='1']").prop("checked",true);
    }
})

$("button[data-role=save]").click(function() {
    let params = {
        "useFlag": $('input[name=status]:checked').val(),
        "blacklistSeq": $('input[name=blacklistSeq]').val()
    };
    if(!confirm("저장하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/management/complaint/blacklist/detail/update",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("등록되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
})

$("button[data-role=delete]").click(function() {
    let params = {
        "blacklistSeq": $('input[name=blacklistSeq]').val()
    };
    if(!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/management/complaint/blacklist/detail/delete",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("삭제되었습니다.");
                window.location.href = "/bo/management/complaint/blacklist"
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
})

$("button[data-role=blacklist]").click(function () {
    window.location.href = "/bo/management/complaint/blacklist"
});