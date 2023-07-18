let hhFieldsLow = $('#modal-headhunter').find('span[name=hhFieldsLow]').detach();

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

// 헤드헌터 보기
$("#modal-headhunter").on("shown.bs.modal", function(e) {
    let event = $(e.relatedTarget);
    let memberId = event.data('memberId')
    $.ajax({
        url: '/fo/position/headhunter-info',
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
                        $("#modal-" +
                            " .headhunter-profile-content .headhunter-profile .headhunter-profile-img").css("background-image", "url('" + hhInfo.commonFileList[0].url + "')");
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

//지원여부
$("[data-role=checkApplicant]").click(function(){
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