/** 전체선택 체크박스 변경시 */
$("input[name=checkAll]").change(function () {
    if ($(this).is(":checked")) {
        $("input[name=checkAdminAuth]").prop("checked", true)
    } else {
        $("input[name=checkAdminAuth]").prop("checked", false);
    }
});

/** 체크박스 변경시 */
$("input[name=checkAdminAuth]").change(checkAll);

/* 관리자 변경 시 */
$("select[name=selectAdmin]").change(function () {
    /* 변경 시 해당 관리자 seq 취득 */
    let seq = $(this).val();
    console.log("해당 관리자 SEQ >>>> " + seq);
    let param = {
        adminSeq: seq
    }

    /* 선택 내용 초기화 */
    $("input[name=checkAdminAuth]").prop("checked", false);

    /* 관리자별 권한 정보 */
    $.ajax({
        type: "get",
        url: "/bo/system/auth/admin/list",
        data: param,
        success: function (data) {
            $(data.data).each(function (i, d) {
                let self = this;
                $("input[name=checkAdminAuth]").each(function () {
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

    /** 체크박스 선택 adminAuthList -> 배열 */
    let adminAuthList = [];
    $("input[name=checkAdminAuth]:checked").each(function () {
        let chkProgramMenu = $(this).val();
        let chkMenuCd = $(this).data("menuCd");

        let pObject = {};
        pObject.menuCd = chkMenuCd;
        pObject.programCd = chkProgramMenu;
        adminAuthList.push(pObject);

    })
    console.log(adminAuthList);
    /* 관리자 seq 취득 */
    let adminSeq = $("select[name=selectAdmin] option:selected").val();

    /* params 그룹화 */
    let params = {
        adminAuthList: adminAuthList,
        adminSeq: adminSeq
    }

    if (confirm("적용하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/auth/admin/insert",
            data: JSON.stringify(params),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                alert(data.message);
            }
        });
    }
});

/** 초기화 */
$("select[name=selectAdmin]").trigger("change");

function checkAll() {
    if ($("input[name=checkAdminAuth]").length === $("input[name=checkAdminAuth]:checked").length) {
        $("input[name=checkAll]").prop("checked", true);
    } else {
        $("input[name=checkAll]").prop("checked", false);
    }
}
