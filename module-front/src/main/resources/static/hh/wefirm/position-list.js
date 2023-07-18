let hhFieldsLow = $('#modal-headhunter').find('span[name=hhFieldsLow]').detach();

// 헤드헌터 보기
$("#modal-headhunter").on("shown.bs.modal", function(e) {
    let event = $(e.relatedTarget);
    let memberId = event.data('memberId')
    $.ajax({
        url: '/hh/wefirm/headhunter-info',
        type: 'GET',
        data: {
            memberId: memberId
        },
        success: function(data) {
            if(data.status == 200){
                let hhInfo = data.data.hhInfo;
                let hhFieldInfo = data.data.hhFieldInfo;
                let modalHeadhunter = $('#modal-headhunter');

                $("form[name=headhunterForm] input[name=headhunterId]").val(memberId);
                $("form[name=headhunterForm] input[name=hhMemberId]").val(memberId);

                //모달 정보 비우기
                modalHeadhunter.find('span[name=hhName]').empty()
                modalHeadhunter.find('span[name=hhSfName]').empty()
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
                modalHeadhunter.find('p[name=hhSfName]').text(hhInfo.sfName);
                modalHeadhunter.find('span[name=hhPosition]').text(hhInfo.position);
                modalHeadhunter.find('span[name=hhSchool]').text((typeof hhInfo.school == "undefined" ? '' : hhInfo.school) + ' ' + (typeof hhInfo.major == "undefined" ? '' : hhInfo.major));
                modalHeadhunter.find('span[name=hhPhone]').text(Utils.formatPhoneNumber(hhInfo.phone));
                modalHeadhunter.find('a[id=hhEmail]').text(hhInfo.email);
                modalHeadhunter.find('a[id=hhEmail]').attr("href", "mailto:" + hhInfo.email);
                modalHeadhunter.find('a[id=hhPostingCnt]').text(hhInfo.postingCnt);
                modalHeadhunter.find('a[id=hhPostingCnt]').attr("href","/fo/user/jop/posting-hunter?memberId="+memberId)
                modalHeadhunter.find('p[name=hhIntro]').text(hhInfo.greeting);
                modalHeadhunter.find('#recentLogin').text(hhInfo.updatedAt);

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
                    $(hhInfo.commonFileList).each(function(){
                        $("#modal-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('" + hhInfo.commonFileList[0].url + "')");
                    });
                } else {
                    $("#modal-headhunter .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('/static/images/no-img.png')");
                }

                /*if(data.data.commonFileList != null){
                    $(data.data.commonFileList).each(function(){
                        $(".headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background", "url('" + this.url + "')");
                    });
                } else {
                    $(".headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background", "url('/static/images/no-img.png')");
                }*/

                modalHeadhunter.find('div[name=hhFields]').empty();
                // 전문분야
                for(let i = 0; i<hhFieldInfo.length; i++){
                    let hhFieldsLows = hhFieldsLow.clone();
                    hhFieldsLows.find('em[name=field]').text(hhFieldInfo[i].fieldCdNm);
                    hhFieldsLows.appendTo(modalHeadhunter.find('div[name=hhFields]'));
                }
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
});