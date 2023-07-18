// /* [수정] 버튼 클릭시 */
// $("button[data-role=update]").click(function () {
//     window.location.href = '/bo/management/operation/recruit/modify?recruitSeq=' + $("#recruitDetailSeq").val();
// });
// /* [취소] 버튼 클릭시 */
// $("button[data-role=list]").click(function () {
//     window.location.href = '/bo/management/operation/recruit'
// });

let coWork= $('#coWork').val();

if(coWork == '0'){
    $('#coWorkDetail').hide();
}

let status = $("#status").val();

let defaultOption;
    if(status == "END"){
        $('#selectType').val("END");
    }
    if(status == "CLOSE"){
        $('#selectType').val("CLOSE");
    }
    if(status == "DEL"){
        $('#selectType').val("DEL");
    }
    if(status == "DOING"){
        $('#selectType').val("DOING");
    }


/* [상태] 버튼 클릭시 */
$('#selectType').change(function(){
    if (!confirm("포지션 상태를 변경하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/management/operation/recruit/update",
            data: {
                status : $("#selectType").val(),
                id : $("#recruitId").val()
            },
            success: function (data) {
                alert("변경되었습니다.");
                window.location.href = "/bo/management/operation/recruit/detail?id=" + $("#recruitId").val() ;
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})

$("button[data-role=delete]").click(function () {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/management/operation/recruit/delete",
            data: {recruitSeq: $("#recruitDetailSeq").val()},
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/management/operation/recruit";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})

$("button[data-role=moveList]").click(function () {
    window.location.href = "/bo/management/operation/recruit"
})
