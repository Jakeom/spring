/* textarea 내용 입력란 ckeditor 연결 */
CKEDITOR.replace('popupContentDetail');

/* int 컬럼 입력란 문자입력 방지 */
let replaceNotInt = /[^0-9]/gi;
$("form[name=updatePopupForm] input[name=popupOrder] , form[name=updatePopupForm] input[name=offsetX] , form[name=updatePopupForm] input[name=offsetY]").on("focusout", function () {
    let x = $(this).val();
    if (x.length > 0) {
        if (x.match(replaceNotInt)) {
            x = x.replace(replaceNotInt, "");
        }
        $(this).val(x);
    }
}).on("keyup", function () {
    $(this).val($(this).val().replace(replaceNotInt, ""));
});

/* datepicker */
$(".form-datepicker").datepicker({
    minDate: 0,
    monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    showMonthAfterYear: true,
    showOtherMonths: true,
    dateFormat: "yy-mm-dd",
    gotoCurrent: true,
    beforeShow: function beforeShow(input, inst) {
        $("#ui-datepicker-div").addClass("datepicker-box");
    }
})

/* [취소] 버튼 클릭시 */
$("button[data-role=detail]").click(function () {
    window.location.href = '/bo/system/screen/popup/detail?popupSeq=' + $("#popupModifySeq").val();
});

/* [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function () {
    let popupSeq = $("#popupModifySeq").val();
    let title = $("form[name=updatePopupForm] input[name=title]").val().trim();
    let content = CKEDITOR.instances.popupContentDetail.getData();
    let popupOrder = $("form[name=updatePopupForm] input[name=popupOrder]").val();
    let useFlag = $('form[name=updatePopupForm] input[name=useFlag]:checked').val();
    let popupStartDt = $("form[name=updatePopupForm] input[name=popupStartDt]").val();
    let popupEndDt = $("form[name=updatePopupForm] input[name=popupEndDt]").val();
    let linkUrl = $("form[name=updatePopupForm] input[name=linkUrl]").val().trim();
    let offsetX = $("form[name=updatePopupForm] input[name=offsetX]").val();
    let offsetY = $("form[name=updatePopupForm] input[name=offsetY]").val();

    /* 필수입력값 alert */
    if (title == "") {
        alert("제목을 입력해주세요");
        $("form[name=updatePopupForm] input[name=title]").focus();
        return false;
    } else if (content == "") {
        alert("내용을 입력해주세요");
        return false;
    } else if (popupOrder == "") {
        alert("메뉴순서를 입력해주세요");
        $("form[name=updatePopupForm] input[name=popupOrder]").focus();
        return false;
    } else if (popupStartDt == "") {
        alert("팝업시작일을 입력해주세요");
        $("form[name=updatePopupForm] input[name=popupStartDt]").focus();
        return false;
    } else if (popupStartDt > popupEndDt) {
        alert("날짜를 확인해주세요.");
        $("form[name=updatePopupForm] input[name=popupEndDt]").focus();
        return false;
    } else if (linkUrl == "") {
        alert("링크 URL을 입력해주세요");
        $("form[name=updatePopupForm] input[name=linkUrl]").focus();
        return false;
    } else if (offsetX == "") {
        alert("오프셋X를 입력해주세요");
        $("form[name=updatePopupForm] input[name=offsetX]").focus();
        return false;
    } else if (offsetY == "") {
        alert("오프셋Y를 입력해주세요");
        $("form[name=updatePopupForm] input[name=offsetY]").focus();
        return false;
    }

    /* update 될 data -> params 그룹화 */
    let params = {
        popupSeq: popupSeq,
        title: title,
        content: content,
        popupOrder: popupOrder,
        useFlag: useFlag,
        popupStartDt: popupStartDt,
        popupEndDt: popupEndDt,
        linkUrl: linkUrl,
        offsetX: offsetX,
        offsetY: offsetY
    }
    console.log(params);

    if (confirm("수정하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/screen/popup/update",
            data: params,
            success: function (data) {
                alert("수정되었습니다.");
                /* update후 상세 페이지로 이동 */
                window.location.href = '/bo/system/screen/popup/detail?popupSeq=' + $("#popupModifySeq").val();
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})