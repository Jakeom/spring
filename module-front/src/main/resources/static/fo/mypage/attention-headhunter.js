let hhFieldsLow = $('#modal-headhunter').find('span[name=hhFieldsLow]').detach();

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

				if(hhInfo.commonFileList != null){
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

$("button[data-role=deleteInterestHh]").click(function() {
	
		let id = $("#interestHhId").val();
		if (confirm("해제 하시겠습니까?")) {
			$.ajax({
				url: '/fo/headhunter/interest',
				type: 'POST',
				data: {
					interestHh : 'delete',
					memberId : $(this).data('memberId')
				},
				success: function(data) {
					if(data.status==200){
						location.href = '/fo/mypage/attention-headhunter';
					}else {
						alert('오류가 발생하였습니다. 관리자에게 문의하세요.')
					}
				},
				error: function(){
					alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
				}
			});
	}
		
	
	
	});