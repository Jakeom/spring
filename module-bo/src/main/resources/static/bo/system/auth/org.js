/** 전체선택 체크박스 변경시 */
$("input[name=checkAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=checkAuth]").prop("checked", true)
    } else {
        $("input[name=checkAuth]").prop("checked", false);
    }
});

/** 체크박스 변경시 */
$("input[name=checkAuth]").change(checkAll);

/** select box로 조직명 변경시 */
$("select[name=orgId]").change(function () {
    let orgId = $(this).val();
    let params = {
        'orgId': orgId
    }

    // 선택 내용 초기화
    $("input[name=checkAuth]").prop("checked", false);
    $.ajax({
        type: "get",
        url: "/bo/system/auth/org/list",
        data: params,
        success: function (data) {
            $(data.data).each(function (i, d) {
                let self = this;
                $("input[name=checkAuth]").each(function () {
                    if ($(this).val() == self.programCd) {
                        $(this).prop("checked", true);
                    }
                });
            });
            checkAll();
        }
    });
});

/** [적용] 버튼 클릭시 */
$("button[data-role=save]").click(function () {
    let checkAuthArr = [];

    $("input[name=checkAuth]:checked").each(function () {
        checkAuthArr.push({
            programCd: $(this).val(),
            menuCd: $(this).data("menuCd")
        });
    })

    if (confirm("적용하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/auth/org/insert",
            contentType: "application/json",
            data: JSON.stringify({
                orgId: $("select[name=orgId]").val(),
                authList: checkAuthArr
            }),
            success: function (data) {
                alert(data.message);
            }
        });
    }
});

/** 초기화 */
$("select[name=orgId]").trigger("change");


function checkAll() {
    if ($("input[name=checkAuth]").length == $("input[name=checkAuth]:checked").length) {
        $("input[name=checkAll]").prop("checked", true);
    } else {
        $("input[name=checkAll]").prop("checked", false);
    }
}