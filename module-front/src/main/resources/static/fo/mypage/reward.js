$("div[data-role=rewardDetail]").click(function(){
    location.href = "/fo/reward/detail?id="+$(this).data('id')
})

// paging
$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});