$("button[data-role=saveIndustry]").click(function() {
    if($("input[name=industryValue]").val().trim()==null||$("input[name=industryValue]").val()==''){
        alert('직무를 입력해주세요.');
        return false;
    }
    if($("input[name=industryValue]").val().length < 2){
        alert('2글자 이상 입력해주세요.');
        return false;
    }
    let cnt = 0;
    $(".jop-result-list").find("button[data-role=deleteIndustry]").each(function() {
        cnt++;
    })
    if(cnt==20){
        alert('최대 20개 기업까지 알림설정 하실 수 있습니다.');
        return false;
    }
    $('.customizedPostingList').empty();
    $.ajax({
        url: '/fo/user/resume/customized-posting/insert',
        type: 'POST',
        data: {
            industry : $("input[name=industryValue]").val()
        },
        success: function(data) {
            if(data.status==200){
                $("input[name=industryValue]").val("")
                let list = data.data;
                for(let i = 0; i<list.length; i++){
                    let appendHtml = '<div class="jop-result-list">';
                    appendHtml += '<p class="p-r-10">'+list[i].industry+'</p>'
                    appendHtml += '<button type="button" data-role="deleteIndustry" data-seq='+list[i].positionAlertSeq+' class="btn btn-outline-danger btn-sm">삭제</button>'
                    appendHtml += '</div>'
                    $('.customizedPostingList').append(appendHtml);
                }
            }else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
})
$(document).on("click", "[data-role=deleteIndustry]", function(){
    $('.customizedPostingList').empty();
    $.ajax({
        url: '/fo/user/resume/customized-posting/delete',
        type: 'POST',
        data: {
            positionAlertSeq : $(this).data('seq')
        },
        success: function(data) {
            if(data.status==200){
                let list = data.data;
                for(let i = 0; i<list.length; i++){
                    let appendHtml = '<div class="jop-result-list">';
                    appendHtml += '<p class="p-r-10">'+list[i].industry+'</p>'
                    appendHtml += '<button type="button" data-role="deleteIndustry" data-seq='+list[i].positionAlertSeq+' class="btn btn-outline-danger btn-sm">삭제</button>'
                    appendHtml += '</div>'
                    $('.customizedPostingList').append(appendHtml);
                }
            }else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
})