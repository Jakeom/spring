// datepicker
$(".form-date").datepicker({
    monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    showMonthAfterYear: true,
    showOtherMonths: true,
    dateFormat: "yy-mm-dd",
    gotoCurrent: true,
    changeYear: true,
    changeMonth: true
})
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

// sortColumn 검색정렬
$("select[name=sortColumn]").change(function() {
	$("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});


// 기간 검색
$("button[data-role=selectPeriod]").click(function() {
    $("form[name=searchForm] input[name=selectPeriod]").val($(this).data('period'));
    $("form[name=searchForm]").submit();
});

$("input[data-role=datepicker]").change(function(){
	$("form[name=searchForm] input[name=startDt]").val($("#startDt").val());
	$("form[name=searchForm] input[name=endDt]").val($("#endDt").val());
    $("form[name=searchForm]").submit();
})