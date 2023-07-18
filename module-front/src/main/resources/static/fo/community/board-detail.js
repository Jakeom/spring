function clickLike() {
    let params = {
        "communitySeq": $("#communitySeq").val(),
    };
    $.ajax({
        url: "/fo/community/insertRecommendCommunity",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function () {
            location.reload()
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
        }
    });
}

$("button[data-role=commentSave]").click(function(){

    if($("textarea[name=content]").val().trim() == "") {
        alert("댓글을 입력하세요.");
        return false;
    }

    let params = {
        "communitySeq": $("#communitySeq").val(),
        "content": $("textarea[name=content]").val(),
        "depth" : '0'
    };
    $.ajax({
        url: "/fo/community/insertComment",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function () {
            alert("댓글이 등록되었습니다.")
            location.reload()
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
        }
    });
})

//댓글창 열기
$("a[data-role=openCommentWrite]").click(function(){
    if($(this).parent().next().attr('style').includes('none')){
        $(this).parent().next().show();
    }else {
        $(this).parent().next().hide();
    }

})

//대댓글 저장
$("button[data-role=replSave]").click(function(){

    if($(this).prev().val() == "") {
        alert("댓글을 입력하세요.");
        return false;
    }

    let depth = ($(this).data('parentDepth')+1)
    let params = {
        "communitySeq": $("#communitySeq").val(),
        "content": $(this).prev().val(),
        "parentCommunityCommentSeq": $(this).data('parentSeq'),
        "depth" : depth
    };
    $.ajax({
        url: "/fo/community/insertComment",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function () {
            alert("댓글이 등록되었습니다.")
            location.reload()
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
        }
    });
})