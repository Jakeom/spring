function resumeEdit(obj){

    if($(obj).val()==1){
        $.ajax({
            url: '/fo/user/resume/resume-copy',
            type: 'POST',
            data: {'id': $(obj).data('id') ,
                    'representation' : '0'
    },
            success: function(data) {
                if(data.status == 200){
                    alert('이력서가 복사되었습니다.');
                    window.location.reload();
                } else {
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                }
            },
            error: function(){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
    } else if ($(obj).val()==2){
        location.href='/fo/user/resume/resume-edit?id='+$(obj).data('id')+'&resumeId='+$(obj).data('id');
    } else if ($(obj).val()==3){
        $('.total-career').trigger("click");
        $('[name=openReportResumeId]').val('');
        $('[name=openReportResumeId]').val($(obj).data('id'));
    }else if ($(obj).val()==4){
        $.ajax({
            url: '/fo/user/resume/resume-representation',
            type: 'POST',
            data: {'id': $(obj).data('id')
            },
            success: function(data) {
                if(data.status == 200){
                    alert('기본이력서로 저장되었습니다.');
                    window.location.reload();
                } else {
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                }
            },
            error: function(){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
    } else if ($(obj).val()==5){
        if(confirm("이력서를 삭제하시겠습니까?")){
            $.ajax({
                url: '/fo/user/resume/resume-delete',
                type: 'POST',
                data: {'id': $(obj).data('id')
                },
                success: function(data) {
                    if(data.status == 200){
                        alert('삭제되었습니다.');
                        window.location.reload();
                    } else {
                        alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                    }
                },
                error: function(){
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                }
            });
        }
    }
}

//이력서 양식선택 저장 또는 인쇄
$("[data-role=modalResume]").click(function(){
    $('[name=openReportResumeId]').val('');
    $('[name=openReportResumeId]').val($(this).data('id'));
})

//이력서 양식선택 저장 또는 인쇄
$("[data-role=openReport]").click(function(){
    if(nvl($('[name=resumeFormChoice]:checked').val()) == ''){
    alert('이력서 양식을 선택해주세요.')
        return false;
    }

    let id = $('[name=openReportResumeId]').val();

    $.ajax({
        url: "/m/ubi-report/resume",
        type: "GET",
        contentType: "application/json",
        data: {
            id : id
        },
        success: function (d) {
            if (d.status == 200) {
                console.log(JSON.stringify(d.data));
                if($('[name=resumeFormChoice]:checked').val() == 'basic'){
                    window.open("", "reportFormBasic");
                    $("form[name=reportFormBasic] input[name=data]").val(JSON.stringify(d.data));
                    $("form[name=reportFormBasic]").submit();
                } else if($('[name=resumeFormChoice]:checked').val() == 'line'){
                    window.open("", "reportFormLine");
                    $("form[name=reportFormLine] input[name=data]").val(JSON.stringify(d.data));
                    $("form[name=reportFormLine]").submit();
                } else if($('[name=resumeFormChoice]:checked').val() == 'table'){
                    window.open("", "reportFormTable");
                    $("form[name=reportFormTable] input[name=data]").val(JSON.stringify(d.data));
                    $("form[name=reportFormTable]").submit();
                }
            } else {
                alert("작업도중 오류가 발생하였습니다. 관리자에게 문의바랍니다.");
            }
        }
    });
});

function nvl(v) {
    return (v == null || v == '' || v == undefined ? '' : v);
}