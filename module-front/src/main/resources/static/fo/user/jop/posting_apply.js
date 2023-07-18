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

    /*$.ajax({
        url : '/fo/user/jop/posting/apply',
        type : 'POST',
        data : {
                id : selectOneResume,
                resumeId : selectOneResume
                } ,
        dataType : 'JSON',
        success : function (data){
            let resumeInfo = data.data.resumeInfo;
            let academicInfo = data.data.academicInfo;
            let careerInfo = data.data.careerInfo;
            let specInfo = data.data.specInfo;
            let desiredLocationInfo = data.data.desiredLocationInfo;
            let languageInfo = data.data.languageInfo;
            let licenseInfo = data.data.licenseInfo;
            let awardInfo = data.data.awardInfo;
            let activityInfo = data.data.activityInfo;
            let portfolioInfo = data.data.portfolioInfo;
            let englishResumeInfo = data.data.englishResumeInfo;

            //메인 세팅
            $('#profileImg').attr('src', resumeInfo.profileSrc); //프로필url
            $('#name').text(resumeInfo.name); //이력서 타이틀
            $('#selfIntroduction').html(resumeInfo.selfIntroduction.replaceAll("\n", "<br/>")); //자기소개
            $('#birth').text(resumeInfo.birth); //생년월일
            $('#employmentStatusCd').text(resumeInfo.employmentStatusCd); // 재직여부
            $('.address').text(resumeInfo.address); //주소
            $('#phone').text(resumeInfo.phone); //폰번호
            $('#email').text(resumeInfo.email); //이메일
            $('#careerDescription').html(resumeInfo.careerDescription.replaceAll("\n", "<br/>")); //경력기술서
            $('.isVeterans').text(resumeInfo.isVeterans); //보훈여부
            $('.disability').text(resumeInfo.disability); //장애여부
            $('.militaryServiceCd').text(resumeInfo.militaryServiceCd); //병역
            $('span[name=desiredLocationCd]').text(desiredLocationInfo.desiredLocationCd); //희망근무지
            $('span[name=desiredPosition]').text(resumeInfo.desiredPosition); //희망직급
            $('span[name=salary]').text(resumeInfo.desiredSalary); //희망연봉
            $('span[name=joinDateCd]').text(resumeInfo.joinDateCd); //입시가능날

            //학력 세팅
            for(let i = 0; i < academicInfo.length; i++){
                let educationRows = educationRow.clone();
                educationRows.find('p[name=educationPeriod]').text(academicInfo[i].entranceYm+'~'+academicInfo[i].graduationYm);
                educationRows.find('p[name=graduationStatus]').text(academicInfo[i].graduationStatusCd);
                educationRows.find('p[name=schoolName]').text(academicInfo[i].schoolName);
                educationRows.find('span[name=schoolLocation]').text('('+academicInfo[i].locationCd+')');
                educationRows.find('span[name=major]').text(academicInfo[i].majorName);
                educationRows.find('span[name=grades]').text(academicInfo[i].grades);
                educationRows.appendTo($('div[name=education]'));
            }
            //경력 세팅
            for(let i = 0; i < careerInfo.length; i++){
                let careerRows = careerRow.clone();
                if(careerInfo[i].holdOffice==1){
                    careerRows.find('p[name=careerPeriod]').text(careerInfo[i].entranceYm+'~'+'재직중');
                }else{
                    careerRows.find('p[name=careerPeriod]').text(careerInfo[i].entranceYm+'~'+careerInfo[i].resignationYm);
                }
                careerRows.find('p[name=companyName]').text(careerInfo[i].companyName);
                careerRows.find('span[name=departmentName]').text(careerInfo[i].departmentName);
                careerRows.find('span[name=positionCd]').text(careerInfo[i].positionCd);
                careerRows.find('span[name=dutyCd]').text(careerInfo[i].dutyCd);
                careerRows.find('span[name=salary]').text(careerInfo[i].salary);
                careerRows.appendTo($('div[name=career]'));
            }
            //핵심역량
            for(let i = 0; i < specInfo.length; i++){
                let specRows =specRow.clone();
                specRows.find('p[name=specType]').text(specInfo[i].specType);
                specRows.find('p[name=specInfo]').text(specInfo[i].spec);
                specRows.appendTo($('div[name=spec]'));
            }
            //어학
            for(let i = 0; i < languageInfo.length; i++){
                if(languageInfo[i].languageCertificationCd=='CONVERSATION'){
                    let languageConversationRows =languageConversationRow.clone();
                    languageConversationRows.find('span[name=languageCd]').text(languageInfo[i].languageCd)
                    languageConversationRows.find('span[name=conversationCd]').text(languageInfo[i].conversationCd)
                    languageConversationRows.appendTo($('div[name=languageConversation]'))
                }
                if(languageInfo[i].languageCertificationCd=='PUBLIC'){
                    let languagePublicRows =languagePublicRow.clone();
                    languagePublicRows.find('span[name=languageCd]').text(languageInfo[i].languageCd)
                    languagePublicRows.find('span[name=testTypeCd]').text(languageInfo[i].testTypeCd)
                    languagePublicRows.find('span[name=testScore]').text(languageInfo[i].testScore+'점')
                    languagePublicRows.find('span[name=obtainYm]').text(languageInfo[i].obtainYm)
                    languagePublicRows.appendTo($('div[name=languagePublic]'))
                }
            }
            //자격증
            for(let i = 0; i < licenseInfo.length; i++){
                let licenseRows = licenseRow.clone();
                licenseRows.find('span[name=licenseName]').text(licenseInfo[i].licenseName)
                licenseRows.find('span[name=agency]').text(licenseInfo[i].agency)
                licenseRows.find('span[name=obtainYm]').text(licenseInfo[i].obtainYm)
                licenseRows.appendTo($('div[name=license]'))
            }
            //수상
            for(let i = 0; i < awardInfo.length; i++){
                let awardRows = awardRow.clone();
                awardRows.find('span[name=awardName]').text(awardInfo[i].awardName)
                awardRows.find('span[name=agency]').text(awardInfo[i].awardAgency)
                awardRows.find('span[name=awardYm]').text(awardInfo[i].awardYm)
                awardRows.appendTo($('div[name=award]'))
            }
            //대외활동
            for(let i = 0; i < activityInfo.length; i++){
                let exActivityRows = exActivityRow.clone();
                exActivityRows.find('span[name=content]').text(activityInfo[i].content)
                exActivityRows.appendTo($('div[name=exActivity]'))
            }
            //포트폴리오
            for(let i = 0; i < portfolioInfo.length; i++){
                let portfolioRows = portfolioRow.clone();
                portfolioRows.find('a').attr('href',portfolioInfo[i].url)
                portfolioRows.appendTo($('div[name=portfolio]'))
            }
            //영문 이력서
            for(let i = 0; i < englishResumeInfo.length; i++){
                let enResumeRows = enResumeRow.clone();
                enResumeRows.find('a').attr('href',englishResumeInfo[i].enUrl)
                enResumeRows.appendTo($('div[name=enResume]'))
            }
        }
    });*/
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
    $.ajax({
        url: '/fo/user/jop/posting/doApply',
        type: 'POST',
        data: {
            positionId : $('input[name=positionId]').val(),
            resumeId: $('select[name=selectedResume]').val(),
            representation : $('#representation').val()
        },
        success: function(data) {
            if(data.status==200){
                $('#modal-apply').find('input[name=applicantId]').val(data.data)
            }else{
                alert('관리자에게 문의하세요.')
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
            id : $('#modal-apply').find('input[name=applicantId]').val()
        },
        success: function(data) {
            if(data.status==200){
                location.href ="/fo/mypage/application-status"
            }else{
                alert('관리자에게 문의하세요.')
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