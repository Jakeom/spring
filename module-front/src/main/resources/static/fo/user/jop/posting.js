let hhFieldsLow = "";
$(document).ready(function(){
    hhFieldsLow = $('#modal-headhunter').find('span[name=hhFieldsLow]').detach();
})

// paging
$('a[data-role=btnGoPage]').click(function() {
    var page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
    $(".nav-tabs li a.active").trigger("click");
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

//헤드헌터 보기
$('a[data-role=hhInfo]').click(function() {
    var memberId = $(this).data('memberid')
    $.ajax({
        url: '/fo/user/jop/posting/hhInfo',
        type: 'POST',
        data: {
            memberId: $(this).data('memberid')
        },
        success: function(data) {
            if(data.status==200){
                let hhInfo = data.data.hhInfo;
                let hhFieldInfo = data.data.hhFieldInfo;
                let modalHeadhunter = $('#modal-headhunter');
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
                modalHeadhunter.find('#recentLogin').text(hhInfo.updatedAt);
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

                if(hhInfo.commonFileList[0] != null){
                    $("#modal-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('" + hhInfo.commonFileList[0].url + "')");
                } else {
                    $("#modal-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('/static/images/no-img.png')");
                }

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

//출력 순
function changeSort(){
    $("form[name=searchForm]").submit();
}