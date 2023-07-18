// PDF 출력
$("[data-role=openReport]").click(function(){
    if($("input[name=id]:checked").length == 0){
        alert("PDF 출력 할 양식을 선택해주세요.");
        return false;
    }

    let idList = [];
    $("input[name=id]:checked").each(function(){
       idList.push({
           id: $(this).val()
       });
    });

    $.ajax({
        url: "/m/ubi-report/employment-certificate",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            idList : idList
        }),
        success: function (d) {
            if (d.status == 200) {
                console.log(JSON.stringify(d.data));
                window.open("", "reportFormBasic");
                $("form[name=reportFormBasic] input[name=data]").val(JSON.stringify(d.data));
                $("form[name=reportFormBasic]").submit();
            } else {
                alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});