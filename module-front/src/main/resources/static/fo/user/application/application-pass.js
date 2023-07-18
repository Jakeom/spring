
// paging
$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

$("[data-role=clickScrap]").click(function(){
    $(this).toggleClass("active");
    $.ajax({
        url: '/fo/position/scrap',
        type: 'POST',
        data: {
            scrap : $(this).attr("class").includes("active")? 'addScrap':'delete',
            positionId : $(this).data('positionid')
        },
        success: function(data) {
            if(data.status!=200){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });

})