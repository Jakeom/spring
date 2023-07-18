$(document).ready(function(){
let today = new Date();   

let nowYear = today.getFullYear();	 // 년도
let nowMonth = today.getMonth()+1;   // 월
let nowDate = today.getDate();     	 // 날짜
let now = nowYear + '.' + nowMonth + '.' + nowDate;

let ninetyDayAgo = new Date(today.setMonth(today.getMonth()-3));

let ninetyDayAgoYear = ninetyDayAgo.getFullYear(); 	// 년도
let ninetyDayAgoMonth = ninetyDayAgo.getMonth()+1;   // 월
let ninetyDayAgoDate = ninetyDayAgo.getDate();     	// 날짜
let ninetyDay = ninetyDayAgoYear + '.' + ninetyDayAgoMonth + '.' + ninetyDayAgoDate;

$("#logHistory").html("로그인 기록 기간:" + ninetyDay + "~" + now);

})
// paging
$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});