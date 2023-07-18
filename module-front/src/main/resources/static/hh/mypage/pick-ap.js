$("select[name=filter]").change(function(){
	
})
// 검색어 정렬
$("select[name=orderType]").change(function() {
    $("form[name=searchForm]").submit();
});
