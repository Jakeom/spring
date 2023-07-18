function resumeRestricted(obj) {
    let prams = "";
    let asideRestricted="";
    if($(obj).prop("checked")){
        prams = {resumeRestricted : '0'}
        asideRestricted = '공개 중'
    }else{
        prams = {resumeRestricted : '1'}
        asideRestricted = '비공개'
        $("#findingJobCb").prop("checked",false)
       let pramss = {findingJob : '0'}

        $.ajax({
            url: '/fo/user/resume/resume-findingJob',
            type: 'POST',
            data: pramss,
            success: function(data) {
                if(data.status!=200){
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
                }
            },
            error: function(){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
    }
    $.ajax({
        url: '/fo/user/resume/resume-restricted',
        type: 'POST',
        data: prams,
        success: function(data) {
            if(data.status==200){
                $('#resumeRestrictedText').text(asideRestricted)
            }else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}

function findingJob(obj) {
    if(!$("#restrictedCb").prop("checked")){
        $("#findingJobCb").prop("checked",false)
        return false;
    }
    let prams = "";
    if($(obj).prop("checked")){
        prams = {findingJob : '1'}
    }else{
        prams = {findingJob : '0'}
    }
    $.ajax({
        url: '/fo/user/resume/resume-findingJob',
        type: 'POST',
        data: prams,
        success: function(data) {
            if(data.status!=200){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}
