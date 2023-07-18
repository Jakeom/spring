/** 전체선택 체크박스 변경시 */
$("input[name=checkAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=checkGroupAuth]").prop("checked", true)
    } else {
        $("input[name=checkGroupAuth]").prop("checked", false);
    }
});

/** 체크박스 변경시 */
$("input[name=checkGroupAuth]").change(checkAll);

/* 그룹 변경 시 */
$("select[name=selectGroup]").change(function () {
    /* 변경 시 해당 그룹 seq 취득 */
    let groupId = $(this).val();
    console.log("해당 그룹 SEQ >>>> " + groupId);
    let param = {
        groupId: groupId
    }

    /* 선택 내용 초기화 */
    $("input[name=checkGroupAuth]").prop("checked", false);

    /* 그룹별 권한 정보 */
    $.ajax({
        type: "get",
        url: "/bo/system/auth/group/list",
        data: param,
        success: function (data) {
            $(data.data).each(function (i, d) {
                let self = this;
                $("input[name=checkGroupAuth]").each(function () {
                    if ($(this).val() === self.programCd) {
                        $(this).prop("checked", true);
                    }
                });
            });
            checkAll();
        }
    });
});

/* [적용하기] 버튼 클릭 시 */
$("button[data-role=apply]").on("click", function () {

    /** 체크박스 선택 groupAuthList -> 배열 */
    let groupAuthList = [];
    $("input[name=checkGroupAuth]:checked").each(function () {
        let chkProgramMenu = $(this).val();
        let chkMenuCd = $(this).data("menuCd");

        let gObject = {};
        gObject.menuCd = chkMenuCd;
        gObject.programCd = chkProgramMenu;
        groupAuthList.push(gObject);

    })
    console.log(groupAuthList);
    /* 그룹 seq 취득 */
    let groupId = $("select[name=selectGroup] option:selected").val();

    /* params 그룹화 */
    let params = {
        groupAuthList: groupAuthList,
        groupId: groupId
    }

    if (confirm("적용하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/auth/group/insert",
            data: JSON.stringify(params),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                alert(data.message);
            }
        });
    }
});

/** change 함수 trigger */
$("select[name=selectGroup]").trigger("change");

function checkAll() {
    if ($("input[name=checkGroupAuth]").length === $("input[name=checkGroupAuth]:checked").length) {
        $("input[name=checkAll]").prop("checked", true);
    } else {
        $("input[name=checkAll]").prop("checked", false);
    }
}
