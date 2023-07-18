
let hhFieldsLow = "";
$(document).ready(function(){

   //최근본 공고에 저장
    let arr = localStorage.getItem('watched');
    if( arr == null){
        arr = [];
    }else{
        arr = JSON.parse(arr)
    }
    for(var i = 0; i < arr.length; i++){
        if (arr[i] == $('input[name=id]').val()) {
            arr.splice(i, 1);
            i--;
        }
    }
    arr.unshift($('input[name=id]').val())
    arr = new Set(arr);
    arr = [...arr];
    localStorage.setItem('watched',JSON.stringify(arr));
    console.log(localStorage.getItem('watched'))
    hhFieldsLow = $('#modal-headhunter').find('span[name=hhFieldsLow]').detach();
})

//헤드헌터 보기
$('div[data-role=hhInfo]').click(function() {
    let memberId = $(this).data('memberId')
    $.ajax({
        url: '/fo/position/headhunter-info',
        type: 'GET',
        data: {
            memberId: memberId
        },
        success: function(data) {
            if(data.status==200){
                let hhInfo = data.data.hhInfo;
                let hhFieldInfo = data.data.hhFieldInfo;
                let modalHeadhunter = $('#modal-headhunter');

                $("form[name=headhunterForm] input[name=headhunterId]").val(memberId);
                $("form[name=headhunterForm] input[name=hhMemberId]").val(memberId);

                //모달 정보 비우기
                modalHeadhunter.find('span[name=hhName]').empty()
                modalHeadhunter.find('span[name=hhPosition]').empty()
                modalHeadhunter.find('span[name=hhSchool]').empty()
                modalHeadhunter.find('span[name=hhPhone]').empty()
                modalHeadhunter.find('span[name=hhPhone]').empty()
                modalHeadhunter.find('a[id=hhEmail]').empty()
                modalHeadhunter.find('a[id=hhEmail]').empty()
                modalHeadhunter.find('a[id=hhPostingCnt]').empty()
                modalHeadhunter.find('a[id=hhPostingCnt]').empty()
                modalHeadhunter.find('p[name=hhIntro]').empty()
                modalHeadhunter.find('#recentLogin').empty()
                $("#modal-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('/static/images/no-img.png')");

                // 모달에 정보 채우기
                modalHeadhunter.find('span[name=hhName]').text(hhInfo.name);
                modalHeadhunter.find('span[name=hhPosition]').text(hhInfo.position);
                modalHeadhunter.find('span[name=hhSchool]').text((typeof hhInfo.school == "undefined" ? '' : hhInfo.school) + ' ' + (typeof hhInfo.major == "undefined" ? '' : hhInfo.major));
                modalHeadhunter.find('span[name=hhPhone]').text(hhInfo.readCnt > 0 ? Utils.formatPhoneNumber(hhInfo.phone) : '***-****-****');
                modalHeadhunter.find('span[name=hhPhone]').data("phone", Utils.formatPhoneNumber(hhInfo.phone));
                modalHeadhunter.find('a[id=hhEmail]').text(hhInfo.email);
                modalHeadhunter.find('a[id=hhEmail]').attr("href", "mailto:" + hhInfo.email);
                modalHeadhunter.find('a[id=hhPostingCnt]').text(hhInfo.postingCnt);
                modalHeadhunter.find('a[id=hhPostingCnt]').attr("href","/fo/user/jop/posting-hunter?memberId="+memberId)
                modalHeadhunter.find('p[name=hhIntro]').text(hhInfo.greeting);
                modalHeadhunter.find('#recentLogin').text(hhInfo.recentLogin);
                if(hhInfo.readCnt > 0){
                    modalHeadhunter.find('[data-role=showPhone]').hide();
                } else {
                    modalHeadhunter.find('[data-role=showPhone]').show();
                }

                if(hhInfo.blackCnt > 0){
                    modalHeadhunter.find('[data-role=addBlack]').hide();
                } else {
                    modalHeadhunter.find('[data-role=addBlack]').show();
                }

                modalHeadhunter.find('.history').empty();
                if(hhInfo.careerDesc!=undefined&&hhInfo.careerDesc!=null){
                    let arr = hhInfo.careerDesc.split("\n");
                    $(arr).each(function(){
                        let $li = $("<li>").text(this);
                        modalHeadhunter.find('div[name=hhCareer] .history').append($li);
                    });
                }

                if(hhInfo.commonFileList != null){
                    $(hhInfo.commonFileList).each(function(){
                        $("#modal-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('" + hhInfo.commonFileList[0].url + "')");
                    });
                } else {
                    $("#modal-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('/static/images/no-img.png')");
                }

                /*
                if(data.data.commonFileList != null){
                    $(data.data.commonFileList).each(function(){
                        $(".headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background", "url('" + this.url + "')");
                    });
                } else {
                    $(".headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background", "url('/static/images/no-img.png')");
                }*/

                modalHeadhunter.find('div[name=hhFields]').empty();

                //전문분야
                for(let i=0; i<hhFieldInfo.length; i++){
                    let hhFieldsLows = hhFieldsLow.clone();
                    hhFieldsLows.find('em[name=field]').text(hhFieldInfo[i].fieldCdNm);
                    hhFieldsLows.appendTo($('#modal-headhunter').find('div[name=hhFields]'))
                }
            }else{
                alert('관리자에게 문의하세요.')
            }
        }
    });
})

//지원여부
$("[data-role=apply]").click(function(){
    let id = $(this).data('id');
    $.ajax({
        url: '/fo/position/check-applicant',
        type: 'POST',
        data: {
            id : $(this).data('id')
        },
        success: function(data) {
            if(data.status == 200 && data.data > 0){
                alert('이미 지원하셨습니다.');
            }else if(data.status == 200 && data.data == 0){
                location.href = '/fo/user/jop/posting/apply?id='+id
            }else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
})

//목록
$("[data-role=goBack]").click(function(){
    window.history.back();
})

// 블랙리스트 추가
$("[data-role=addBlack]").click(function(){
    $.ajax({
        url: '/fo/position/hh-black',
        type: 'POST',
        data: {headhunterId : $('form[name=headhunterForm] input[name=headhunterId]').val()},
        success: function(data) {
            if(data.status == 200){
                let modalHeadhunter = $('#modal-headhunter');
                modalHeadhunter.find('[data-role=addBlack]').hide();
                alert("블랙리스트에 추가되었습니다.")
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
});

// 연락처 보기
$("[data-role=showPhone]").click(function(){
    $.ajax({
        url: '/fo/position/hh-reading',
        type: 'POST',
        data: {hhMemberId : $('form[name=headhunterForm] input[name=hhMemberId]').val()},
        success: function(data) {
            if(data.status == 200){
                let modalHeadhunter = $('#modal-headhunter');
                modalHeadhunter.find('span[name=hhPhone]').text(modalHeadhunter.find('span[name=hhPhone]').data("phone"));
                modalHeadhunter.find('[data-role=showPhone]').hide();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
});

//헤드헌터 즐겨찾기
$("[data-role=likeHh]").click(function(e){
    e.stopPropagation();
    $(this).toggleClass("active");
    $.ajax({
        url: '/fo/headhunter/interest',
        type: 'POST',
        data: {
            interestHh : $(this).attr("class").includes("active")? 'addInterest':'delete',
            memberId : $(this).data('memberId')
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

//스크랩
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

// 제안거부 - 지원의사 없음 버튼 클릭 시
$("button[data-role=reject]").click(function() {
    let obj = {};
    obj.positionId = $("input[name=id]").val();
    obj.applicantId = $("form[name=proposalAcceptForm] input[name=applicantId]").val();
    obj.acceptance = "REJECT";

    if (!confirm("제안을 거절하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/fo/user/application/proposal-acceptance",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (d) {
                if(d.status = 200) {
                    alert("제안을 거절했습니다.");
                    window.location.href = "/fo/user/application/job-position"
                } else {
                    alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.")
                }
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.")
            }
        });
    }
})

// 지원수락 버튼 클릭 시
$("button[data-role=accept]").click(function(){
    if(!confirm("지원하시겠습니까?")) {
        return false;
    } else { // 지원 페이지 이동
        $("form[name=proposalAcceptForm]").submit();
    }
})