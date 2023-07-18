// c21.date_addDay(7, 'yyyy.MM.dd', dt);
var c21 = {
	date_zero : function(d, digits) {
		if (digits == undefined) {
			digits = 2;
		}
		var zero = "";
		d = d.toString();
		if (d.length < digits) {
			for (var i = 0; i < digits - d.length; i++) {
				zero += "0";
			}
		}
		return zero + d;
	},

	date_addDay: function(num, type, defineDate){
		var defineDate = defineDate.replace(/-/gi, "");
		var year = defineDate.substring(0,4);
		var month = defineDate.substring(4,6);
		var	day = defineDate.substring(6,8);
		var	date = new Date(year+"-"+month+"-"+day);
		date.setDate(date.getDate() + num);
		var year  = date.getFullYear();
		var month = date.getMonth() + 1; // 0부터 시작하므로 1더함 더함
		var day   = date.getDate();
		if (('' + month).length == 1) { month = '0' + month; }
		if (('' + day).length   == 1) { day   = '0' + day;   }
		if(type =="yyyy-MM-dd") return year+'-'+month+'-'+day;
		if(type == "yyyy.MM.dd") return year+'.'+month+'.'+day;
		return year+''+month+''+day;
	},

	// 날짜 변환
	number_date : function (number, format){
		var date = new Date(number);
		if (!date) {
			return "";
		}
		if (!format) {
			format = 'yyyy-MM-dd';
		}
		return format.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
			switch ($1) {
				case "yyyy": return date.getFullYear();
				case "yy": return date.getFullYear() % 1000 % 100;
				case "MM": return c21.date_zero(date.getMonth() + 1);
				case "dd": return c21.date_zero(date.getDate());
				case "E": return weekName[date.getDay()];
				case "HH": return c21.date_zero(date.getHours());
				case "hh": return c21.date_zero((h = date.getHours() % 12) ? h : 12);
				case "mm": return c21.date_zero(date.getMinutes());
				case "ss": return c21.date_zero(date.getSeconds());
				case "a/p": return date.getHours() < 12 ? "오전" : "오후";
				default: return $1;
			}
		});
	},

	// 오늘날짜  fn_date_today("yyyy년 MM월 dd일 a/p hh:mm:ss")
	date_today : function (format){
		var date = new Date();
		if (!date) {
			return "";
		}
		if (!format) {
			format = 'yyyy-MM-dd';
		}
		return format.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
			switch ($1) {
				case "yyyy": return date.getFullYear();
				case "yy": return date.getFullYear() % 1000 % 100;
				case "MM": return c21.date_zero(date.getMonth() + 1);
				case "dd": return c21.date_zero(date.getDate());
				case "E": return weekName[date.getDay()];
				case "HH": return c21.date_zero(date.getHours());
				case "hh": return c21.date_zero((h = date.getHours() % 12) ? h : 12);
				case "mm": return c21.date_zero(date.getMinutes());
				case "ss": return c21.date_zero(date.getSeconds());
				case "a/p": return date.getHours() < 12 ? "오전" : "오후";
				default: return $1;
			}
		});
	},

	// 시간차이
	time_diff : function (number, type){
		var endDate = new Date();
		var stDate = new Date(number);
		var diff = endDate.getTime() - stDate.getTime();
		var minute = parseInt(diff / (1000*60));
		if(type =="second") return  parseInt(diff/(1000));
		else if(type =="minute") return  parseInt(diff/(1000*60));
		else if(type =="hour") return  parseInt(diff/(1000*60*60));
		else if(type =="day") return  parseInt(diff/(1000*60*60*24));
		return "";
	},

	// 도메인 유효성 검사
	check_domain : function (url){
	  let regex = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
	  if(regex.test(url)){
	  	return true;
	  }else{
		 return false;
	  }
	},

	// 글자 내부에 hyperlink 걸기
	change_hyperlink : function (text) {
	  var urlRegex = /(https?:\/\/[^\s]+)/g;
	  return text.replace(urlRegex, function(url) {
		  url = url.replace(/<br>/gi, "");
		  url = url.replace(/<br\/>/gi, "");
	    return '<a href="' + url + '" class="link" target="_blank">' + url + '</a>';
	  })
	},

	is_mobile : function() {
		return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
	}
}

var chatObj = {
    app_id : "resume9",
    app_key : "",  	
	app_url : "https://resume9.gitplelive.io",  		
	in_url : "https://resume9.gitplelive.io",

	// 인증키 받아오기
	api_token : function(){
		var token = "";
		/*
			var url = chatObj.app_url+"/v1/applications/token";
			var header = {"APP_ID": chatObj.app_id, "APP_API_KEY": chatObj.app_key};
			$.ajax({
				type: "POST",
				url: url,
				contentType : 'application/json; charset=UTF-8; ',
				data : {
					  "expire_time": 300
				},
				async : false,
				headers: header,
				success: function (res) {
					token = res.token;
				},
				error: function(jqXHR, exception) {
					console.log(jqXHR);
				}
			});
			return token;
		*/

		var url = "/m/chat/token";
		var params= {};
		params.method = "GET";
		var _callback = function(data){
			token = data.token;
		}
		chatObj.inner_chat(url, params, _callback);
		return token;
	},

	// 외부 chat api json 호출하기
	api_chat : function(url, params, callback){
		var token = chatObj.api_token();
	    var headers = {"APP_ID": chatObj.app_id, "APP_API_TOKEN": token};
	    $.ajax({
			url: chatObj.app_url+"/v1"+url,
			dataType: 'json',
			contentType : 'application/json',
			data : JSON.stringify(params),
			method: params.method,
			async : false,
			headers: headers,
			success: function(data) {
				callback(data);
			},
			error: function(jqXHR, exception) {
				console.log(params);
				console.log(jqXHR);
				var obj = {
					"status" : jqXHR.status,
					"success" : false,
					"error"  : JSON.stringify(exception)
				}
				callback(obj);
			}
		});
	},

	// 외부 chat api 파일업로드 호출하기
	api_chat_file : function (url, formData, callback){
		var token = chatObj.api_token();
	    var headers = {"APP_ID": app_id, "APP_API_TOKEN":token};
		$.ajax({
			url: chatObj.app_url+"/v1"+url,
			type: "POST",
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			cache: false,
			data: formData,
			async : false,
			headers: headers,
			success: function(data) {
				callback(data);
			},
			error: function(jqXHR, exception) {
				console.log(jqXHR);
				var obj = {
					"status" : jqXHR.status,
					"success" : false,
					"error"  : JSON.stringify(exception)
				}
				callback(obj);
			}
		});
	},

	// 인증키 받아오기
	inner_token : function(){
		var token = "";
		var url = "/hh/applications/token";
		var header = {"APP_ID": chatObj.app_id, "APP_API_KEY": chatObj.app_key};
		$.ajax({
			type: "POST",
			url: url,
			contentType : 'application/json; charset=UTF-8; ',
			data : {
				  "expire_time": 300
			},
			async : false,
			headers: header,
			success: function (res) {
				token = res.token;
			},
			error: function(jqXHR, exception) {
				console.log(jqXHR);
			}
		});
		return token;
	},

	// 내부 서버 호출하기
	inner_chat : function (url, params, callback){
		$.ajax({
			url: url,
			dataType: 'json',
			data : params,
			method: params.method,   // HTTP 요청 메소드(GET, POST 등)
			async : false,
			success: function(data) {
				callback(data);
			},
			error: function(xhr) {
				console.log(xhr);
				var obj = {
					"status" : 500,
					"error"  : JSON.stringify(xhr)
				}
				callback(obj);
			}
		});
	},

	inner_chat_save : function (url, params, callback){
		$.ajax({
			url: url,
			dataType: "JSON", //응답받을 데이터 타입 (XML,JSON,TEXT,HTML,JSONP)
			contentType: "application/json; charset=utf-8", //헤더의 Content-Type을 설정
			data : JSON.stringify(params),
			method: params.method,   // HTTP 요청 메소드(GET, POST 등)
			async : false,
			success: function(data) {
				callback(data);
			},
			error: function(xhr) {
				console.log(xhr);
				var obj = {
					"status" : 500,
					"error"  : JSON.stringify(xhr)
				}
				callback(obj);
			}
		});
	},

	// 채팅 시작하기
	channel_start : function(channelObj){
		console.log(JSON.stringify(channelObj));
		/*
			var channelObj = {
				channelNm : "1:1 채팅방 만들기"
				,loginUserId : loginId
				,loginUserNm : loginName
				,loginType : loginType
				,masterUserId : headhunterId
				,masterUserNm : headhunterName
				,pairYn : "Y"
				,positionId : positionId
				,userList : userList
			}
		*/

		var dType = channelObj.loginType;
		dType = dType.toLowerCase();
		if(!dType){
			alert("로그인 타입(dtype)이 없습니다.");
			return false;
		}
		chatObj.open_chat(dType);
	},
	
	// 채널 만들기
	channel_make : function(channelObj){
		console.log("=============channel_make============"+JSON.stringify(channelObj));
		/*
			var channelObj = {
				channelNm : "1:1 채팅방 만들기"
				,loginUserId : loginId
				,loginUserNm : loginName
				,loginType : loginType
				,masterUserId : headhunterId
				,masterUserNm : headhunterName
				,pairYn : "Y"
				,positionId : positionId
				,userList : userList
				,limitYn : limitYn
			}
		*/

		var dType = channelObj.loginType;
		dType = dType.toLowerCase();
		if(!dType){
			alert("로그인 타입(dtype)이 없습니다.");
			return false;
		}				
		if(dType == "hh"){
			chatObj.channel_create(channelObj);  // 채팅방 만들기
		}		
		chatObj.open_chat(dType);
	},
	
	// 채널 정보 조회하기
	channel_info : function(channelId){		
		var channelObj = {};
		var url = "/group/channels/"+channelId+"/?show_members=true&show_managers=true&show_delivery_receipt=true&show_read_receipt=true&show_unread=true";
		var params= {};
		params.method = "GET";
		var _callback = function(data){

			console.log("==================channel_info 채널 단건 조회==============="+JSON.stringify(data));
			channelObj = data;			
		}
		chatObj.api_chat(url, params, _callback);
		return channelObj;
	},
	

	// 그룹 채팅 입장하기
	channel_group_enter : function(channelObj){
		console.log("=======channel_group_enter===="+JSON.stringify(channelObj));
		
		var channel_id = "channel_group_"+channelObj.positionId;
		
		// 일반 사용자 계정 만들기
		var userList = channelObj.userList;
		var userObj = userList[0];				
		chatObj.channel_userCreate(userObj);
		
		var loginUserId = channelObj.loginUserId;
		
		// 채팅창 입장 여부 확인
		var channel_info = chatObj.channel_info(channel_id);
		var meta = channel_info.meta;
		var limitYn = meta.limitYn;
		var isCheck = false;
		if(limitYn =="Y"){
			var members = channel_info.members;
			for(var i=0; i<members.length; i++){
				var userId = members[i].user_id;
				if(userId == loginUserId){
					isCheck = true;
				}
			}			
			if(!isCheck){
				alert("채팅방에 참가할 수 없습니다.\n담당자에서 문의해 주세요.");
				return false;				
			}
		}
				
		// 채널 입장 및 입장 메시지 보내기		
		chatObj.channel_userAdd("ADD", channel_id, userList);
		
		// 채팅창 띄우기
		var dType = channelObj.loginType;
		dType = dType.toLowerCase();
		chatObj.open_chat(dType);
	},

	// 사용자 생성하기
	channel_userCreate : function(userObj){
		var url = "/users";
		var params= {};
		params.method = "POST";
		params.user_id = "resume9_"+userObj.userId;
		params.name = userObj.userNm;
		params.meta = {
			"dType": userObj.loginType
		};
		var _callback = function(res){
			if(res.user_id != null){
				console.log("===============신규가입 성공=================="+params.user_id+"/"+JSON.stringify(res));
			}else{
				console.log("===============신규가입 실패=================="+params.user_id+"/"+JSON.stringify(res));
				return false;
			}
		}
		chatObj.api_chat(url, params, _callback);
	},

	//채팅창 만들기
	channel_create : function (classObj){
		var params = {}
		var today = c21.date_today("yyyyMMddHHmmss");
		params.name = classObj.channelNm;

		var members = [];
		var pairYn = classObj.pairYn;
		var limitYn = "N";
		var firstYn = "Y";
		if(pairYn =="Y"){
			// 1:1 채팅방
			var userObj = { "userId":  classObj.masterUserId , "userNm": classObj.masterUserNm , "dType": classObj.dType }
			chatObj.channel_userCreate(userObj);

			userObj = classObj.userList[0];
			chatObj.channel_userCreate(userObj);

			params.channel_id = "channel_"+classObj.positionId+"_"+classObj.masterUserId +"_"+userObj.userId;  // 채널 id

			useYn = "Y";
			firstYn = "Y";
			members.push("resume9_"+classObj.masterUserId);
			members.push("resume9_"+userObj.userId);
			
		}else{
			// 일반 그룹채팅방 만들기
			// params.channel_id = "channel_"+masterUserId+"_"+today;  // 채널 id
			params.channel_id = "channel_group_"+classObj.positionId;  // 채널 id

			// 마스터 계정 만들기
 			var masterObj = { "userId":  classObj.loginUserId , "userNm": classObj.loginUserNm , "dType": classObj.loginType }
 			chatObj.channel_userCreate(masterObj);
 			members.push("resume9_"+masterObj.userId);
 			
 			// 일반 사용자 계정 만들기
			var userList = classObj.userList;
			for(var i=0; i<userList.length; i++){
				// var userObj = { "userId":  classObj.masterUserId , "userNm": classObj.masterUserNm , "dType": classObj.dType }
				var userObj = userList[i];				
				chatObj.channel_userCreate(userObj);
				members.push("resume9_"+userObj.userId);
			}
			useYn = "Y";
			firstYn = "N";
			limitYn = classObj.limitYn;			
		}

		params.meta = {
			"firstYn" : firstYn,
			"useYn" : useYn,
			"image" : "",
			"master" : "resume9_"+classObj.masterUserId,
			"masterNm" : classObj.masterUserNm,
			"pairYn" : pairYn,
			"limitYn" : limitYn
		}

		var url = "/group/channels";
		params.method = "POST";
		params.type = "group";
		params.freeze = false;
		params.profile_url = "";  // 채팅방 이미지 url
		params.members = members;

		console.log("==================채팅방 신규 만들기1================="+JSON.stringify(params));

		var _callback = function(data){
			console.log("================채팅방 신규 만들기2=============="+params.channel_id+"/"+JSON.stringify(data));
		}
		chatObj.api_chat(url, params, _callback);
	},

	// 채팅방에 인원수 추가하기, 삭제하기
	channel_userAdd : function (type, channel_id, userList){
		var params = {};
		var url = "/group/channels/"+channel_id+"/join";  // 채팅방 들어가기
		params.method = "PUT";
		if(type=="DEL"){
			url = "/group/channels/"+channel_id+"/leave"; // 채팅방 나가기
		}
		
		for(var i=0; i<userList.length; i++){
			var obj = userList[i];
			params.member = "resume9_"+obj.userId;
			var _callback = function(res){
				console.log("=============대화상대 추가하기===="+type+"======"+JSON.stringify(res));
			}
			chatObj.api_chat(url, params, _callback);
		}
		
		// 채팅방 입장, 퇴장 메시지 보내기
		params = {};
		var url = "/group/channels/"+channel_id+"/messages";
		params.method = "POST";
		params.type = "text";
		params.file = "";
		for(var i=0; i<userList.length; i++){
			var obj = userList[i];
			params.user_id = "resume9_"+obj.userId;
			var name = obj.userNm;
			
			var content = name+"님이 들어왔습니다.";
			if(type =="DEL") content = name+"님이 나가셨습니다.";
			params.content = content;
			params.meta = {
				"del_yn" : "N",
				"bar_msg_yn" : "Y"
			};			
			var _callback = function(data){
				console.log("============채팅방 회원 추가, 탈퇴하기 메지시 보내기====="+JSON.stringify(data));
			}
			chatObj.api_chat(url, params, _callback);
		}		
	},

	// 채팅창 열기
	open_chat : function(dType){
		 window.open("/"+dType+"/chat", "chat_pop", "width=1200,height=800");
	}
};

