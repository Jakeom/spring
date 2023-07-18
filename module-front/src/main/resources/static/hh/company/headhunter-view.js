/** [목록] 버튼 클릭시 */
$("button[data-role=goBack]").click(function(){
	window.history.back();
})

// 코워커 참여하기 
$("[data-role=cowork]").click(function(){
					
	// co-work 참여하기
	let params = {
		positionId : $("input[name=positionId]").val()
	}
	let coJoinFlag = $("input[name=coJoinFlag]").val(); // 코웍참여여부
	if(coJoinFlag == 'N') {
		if (confirm("참여하시겠습니까?")) { // 코웍참여여부 미존재시
			$.ajax({
				type: "post",
				url: "/hh/position/cowork-join",
				data: JSON.stringify(params),
				dataType: 'json',
				contentType: 'application/json; charset=utf-8',
				success: function (d) {
					if (d.status == 200) {
						alert("Co-work 참여가 완료되었습니다.");
						window.location.reload();
					} else {
						alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
					}
				},
				error: function () {
					alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
				}
			});
		}
	}
	
	try{
		// ------------------------------ 채팅 방에 헤드헌터 추가하기 [start]--------------------------------------
		/*
		 	채팅방 파라미터 세팅하기
		 	masterUserId, masterName : 헤드헌터 id, 이름
		 	loginId , loginName : 로그인 사람 id, 이름
		 	loginType : 로그인 한사람 dType으로 HH, FO 인지 구분
		 	userList : 1:1 채팅이면 1건 , 그룹이면 1건 이상 (구성원 정보) 
		 	pairYn : 1:1 채팅이면 Y, 그룹채팅이면 N  
		 	chatOpen : 채팅창 띄워서 바로 진입원하면  Y, 채팅방만 만드는 경우 N   	 
		*/    
		
		// 아래 파라미터 값 가져오게 해야 함
		let positionId = $("input[name=positionId]").val();       
		let loginId = $("input[name=headhunterId]").val();
		let loginName = $("input[name=headhunterName]").val();
		let loginType = "HH";

		let headhunterId = $("input[name=registerId]").val();
		let headhunterName = $("input[name=registerName]").val();
		  
		var userList = [];     
		var userObj = { "userId" : loginId , "userNm" : loginName, "dType" : loginType }
		userList.push(userObj);
			     
		var channelObj = {
			 loginUserId : loginId
			,loginUserNm : loginName
			,loginType : loginType 
			,masterUserId : headhunterId
			,masterUserNm : headhunterName		
			,pairYn : "N"
			,positionId : positionId
			,userList : userList
			,chatOpen : 'N'
		}		
		chatObj.channel_group_enter(channelObj);
		// ------------------------------채팅 방에 헤드헌터 추가하기 [end]--------------------------------------
	}catch(e){}
});


