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

// 검색어 정렬
$("select[name=orderType]").change(function() {
    $("form[name=searchForm]").submit();
});