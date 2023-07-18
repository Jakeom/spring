
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
$("select[name=sortColumn]").change(function() {
    $("form[name=searchForm]").submit();
});

//이력서 수정 동의여부
$("button[data-role=resumeModifyFlag]").click(function (){

    if($(this).attr('disabled')=='disabled'){
        return false;
    }

if(confirm('이력서 수정 동의 하시겠습니까?')){
    $.ajax({
        url: '/fo/user/jop/posting/editResumeAgree',
        type: 'POST',
        data: {
            id : $(this).data('id')
        },
        success: function(data) {
            if(data.status==200){
                location.href ="/fo/mypage/application-status"
            }else{
                alert('관리자에게 문의하세요.')
            }
        }
    });
  }
})

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