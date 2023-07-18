//반복될 문구들
let educationRow = ''; //학력
let careerRow = ''; //경력
let specRow = ''; //핵심역량
let languageConversationRow = ''; //어학(회화)
let languagePublicRow = ''; //어학(공인시험)
let licenseRow = ''; //자격증
let awardRow = ''; //수상
let exActivityRow = ''; //대외활동
let portfolioRow = ''; //포트폴리오
let enResumeRow = ''; //영문이력서

$(document).ready(function(){
    //반복될 문구들 미리 클론
    educationRow = $('div[name=educationRow]').detach();
    careerRow = $('div[name=careerRow]').detach();
    specRow = $('div[name=specRow]').detach();
    languageConversationRow = $('div[name=languageRow]').detach();
    languagePublicRow = $('div[name=languagePublicRow]').detach();
    licenseRow = $('div[name=licenseRow]').detach();
    awardRow = $('div[name=awardRow]').detach();
    exActivityRow = $('div[name=exActivityRow]').detach();
    portfolioRow = $('div[name=portfolioRow]').detach();
    enResumeRow = $('div[name=enResumeRow]').detach();
})

function selectResume(obj){
    let selectOneResume = obj.value;
    if(nvl(selectOneResume)!=''){
        $('.send-btn').show();
        $('.apply-wrap').show();
    }else{
        $('.send-btn').hide();
        $('.apply-wrap').hide();
        return false;
    }

    //초기화
    $('div[name=education]').children().remove(); //학력
    $('div[name=career]').children().remove(); //경력
    $('div[name=spec]').children().remove(); //핵심역량
    $('div[name=languageConversation]').children().remove(); //어학(회화)
    $('div[name=languagePublic]').children().remove(); //어학(공인시험)
    $('div[name=license]').children().remove(); //자격증
    $('div[name=award]').children().remove(); //수상
    $('div[name=exActivity]').children().remove(); //대외활동
    $('div[name=portfolio]').children().remove(); //포트폴리오
    $('div[name=enResume]').children().remove(); //영문이력서

    $.ajax({
        url: "/m/resume/resume-detail-ap",
        type:"GET",
        data: {id : selectOneResume,
            resumeId : selectOneResume
                },
        dataType : "html",
        success:function(d){
            $(".apply-wrap").empty();
            //let apply = html.find('.apply-wrap')
            $(".apply-wrap").html($(d).find('.apply-wrap'));
            $('[data-role=careerModify]').hide();
            $('[data-role=careerDelete]').hide();
            $(".apply-wrap").show();

        }
    });
}

//지원하기
$('a[data-role=apply]').click(function(){

    let params = {
        resumeId : $('select[name=selectedResume]').val(),
        acceptance : "ACCEPT",
        applicantId : $("input[name=applicantId]").val(),
        representation : $('#representation').val()
    }

    $.ajax({
        url: '/fo/user/application/proposal-acceptance',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function(data) {
            if(data.status==200){

            }else{
                alert('시스템 오류가 발생했습니다. 관리자에게 문의하세요.');
            }
        }
    });
    })

//이력서 수정동의
$('button[data-role=editResumeAgree]').click(function(){
    $.ajax({
        url: '/fo/user/jop/posting/editResumeAgree',
        type: 'POST',
        data: {
            id : $("input[name=applicantId]").val()
        },
        success: function(data) {
            if(data.status==200){
                location.href ="/fo/user/application/job-position"
            }else{
                alert('시스템 오류가 발생했습니다. 관리자에게 문의하세요.');
            }
        }
    });
})

//이력서 수정거부
$('button[data-role=editResumeRefuse]').click(function(){
    location.href ="/fo/mypage/application-status"
})

function nvl(v) {
    return (v == null || v == '' || v == undefined ? '' : v);
}