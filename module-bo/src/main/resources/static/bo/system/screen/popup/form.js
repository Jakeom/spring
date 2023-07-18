/* textarea 내용 입력란 ckeditor 연결 */
CKEDITOR.replace('popupContent');

/* int 컬럼 입력란 문자입력 방지 */
let replaceNotInt = /[^0-9]/gi;
$("#popupOrder, #offsetX, #offsetY").on("focusout", function () {
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
$("button[data-role=list]").click(function () {
    window.location.href = "/bo/system/screen/popup";
});

/* [저장] 버튼 클릭시 */
$("button[data-role=save]").click(function () {
    let title = $("form[name=insertPopupForm] input[name=title]").val().trim();
    /* CKEDITOR 값 처리 */
    let content = CKEDITOR.instances.popupContent.getData();
    console.log(content);
    $("form[name=insertPopupForm] textarea[name=content]").val(content);
    let popupOrder = $("form[name=insertPopupForm] input[name=popupOrder]").val();
    let useFlag = $('form[name=insertPopupForm] input[name=useFlag]:checked').val();
    let popupStartDt = $("form[name=insertPopupForm] input[name=popupStartDt]").val();
    let popupEndDt = $("form[name=insertPopupForm] input[name=popupEndDt]").val();
    let linkUrl = $("form[name=insertPopupForm] input[name=linkUrl]").val().trim();
    let offsetX = $("form[name=insertPopupForm] input[name=offsetX]").val();
    let offsetY = $("form[name=insertPopupForm] input[name=offsetY]").val();

    /* 필수입력값 alert */
    if (title == "") {
        alert("제목을 입력해주세요");
        $("form[name=insertPopupForm] input[name=title]").focus();
        return false;
    } else if (content == "") {
        alert("내용을 입력해주세요");
        return false;
    } else if (popupOrder == "") {
        alert("메뉴순서를 입력해주세요");
        $("form[name=insertPopupForm] input[name=popupOrder]").focus();
        return false;
    } else if (popupStartDt == "") {
        alert("팝업시작일을 입력해주세요");
        $("form[name=insertPopupForm] input[name=popupStartDt]").focus();
        return false;
    } else if (popupStartDt > popupEndDt) {
        alert("날짜를 확인해주세요.");
        $("form[name=insertPopupForm] input[name=popupEndDt]").focus();
        return false;
    } else if (linkUrl == "") {
        alert("링크 URL을 입력해주세요");
        $("form[name=insertPopupForm] input[name=linkUrl]").focus();
        return false;
    } else if (offsetX == "") {
        alert("오프셋X를 입력해주세요");
        $("form[name=insertPopupForm] input[name=offsetX]").focus();
        return false;
    } else if (offsetY == "") {
        alert("오프셋Y를 입력해주세요");
        $("form[name=insertPopupForm] input[name=offsetY]").focus();
        return false;
    }

    /* insert 될 data -> params 그룹화 */
    let params = {
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

    if (confirm("등록하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: "/bo/system/screen/popup/insert",
            data: params,
            success: function (data) {
                alert("등록되었습니다.");
                /** insert 후 리스트 페이지로 이동 */
                window.location.href = "/bo/system/screen/popup";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
});