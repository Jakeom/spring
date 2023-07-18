// 데이트 피커 날짜 제한
let now = new Date();
let maxDate = new Date();
maxDate.setDate(now.getDate() + 30);
$("#end-datepicker").datepicker({
    dateFormat: 'yy-mm-dd',
    minDate: 0,
    maxDate: maxDate
});

// 검색 버튼 클릭시
$("[data-role=search]").click(function(){
    $("form[name=searchForm]").submit();
});

// 연장 버튼 클릭시
$("[data-role=deadline]").click(function(e){
    e.stopPropagation();
    e.preventDefault();
});

// 연장 버튼 클릭시
$("#modal-deadline").on("shown.bs.modal", function(e){
    e.stopPropagation();
    e.preventDefault();
    $("input[name=positionId]").val($(e.relatedTarget).data("positionId"));
    $("input[name=endDate]").val("");
});

// [연장 모달] - 저장 클릭시
$("[data-role=saveDeadline]").click(function(){
    if(!Utils.alert(Utils.valid($("input[name=endDate]"), null, "마감일"))) return false;

    $.ajax({
        url: "/hh/position/deadline",
        type: "POST",
        data: {
            positionId: $("input[name=positionId]").val(),
            endDate: $("input[name=endDate]").val()
        },
        success: function(data){
            if(data.status == 200){
                alert('마감일 연장이 완료되었습니다.');
                location.href = '/hh/position/position-list';
            } else {
                alert('마감일 연장 중 오류가 발생하였습니다. 관리자에게 문의해주세요.');
            }
        }
    });
});

// [삭제 모달] - 오픈시
$("#modal-delete-position").on("shown.bs.modal", function(e){
    $("input[name=delPositionId]").val($(e.relatedTarget).data("id"));
});

// [삭제 모달] - 삭제 클릭시
$("[data-role=delPosition]").click(function(){
    $.ajax({
        url: "/hh/position/delete",
        type: "POST",
        data: {
            id: $("input[name=delPositionId]").val()
        },
        success: function(data){
            if(data.status == 200){
                alert('포지션 삭제 처리가 완료되었습니다.');
                window.location.reload();
            } else {
                alert('포지션 삭제 처리 중 오류가 발생하였습니다. 관리자에게 문의해주세요.');
            }
        },
        error: function(){
            alert('포지션 삭제 처리 중 오류가 발생하였습니다. 관리자에게 문의해주세요.');
        }
    });
});

// 포지션 상세 클릭시
$("[data-role=positionDetail]").click(function(e){
    e.preventDefault();
    window.location.href = $(this).data("url");
});

$("button[data-role=fail]").click(function() {
    if(confirm("남은인원을 탈락처리 하시겠습니까?")) {
        console.log($(this).data("id"));
        $.ajax({
            url: "/hh/position/remain-fail",
            type: "POST",
            data: {
                id: $(this).data("id")
            },
            success: function(data){
                if(data.status == 200){
                    alert("남은인원이 탈락처리 되었습니다.");
                    window.location.reload();
                } else {
                    alert('탈락처리 중 오류가 발생하였습니다. 관리자에게 문의해주세요.');
                }
            },
            error: function(){
                alert('탈락처리 중 오류가 발생하였습니다. 관리자에게 문의해주세요.');
            }
        })
    }
})