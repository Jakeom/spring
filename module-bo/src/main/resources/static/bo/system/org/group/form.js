/** [등록] 버튼 클릭시 */
$("button[data-role=save]").click(function(){ 

      let groupId = $("form[name=insertGroupForm] input[name=groupId]").val();
      let groupNm = $("form[name=insertGroupForm] input[name=groupNm]").val();
      let useFlag = $("form[name=insertGroupForm] input[name=useFlag]:checked").val();
	  let dataCheckResult = $("form[name=insertGroupForm] input[name=groupId]").attr("data-check-result");
      
     /** 필수값 입력 체크 */ 
     if(groupId == "") {
	 	  alert("그룹 아이디를 입력해주세요");
	 	  $("form[name=insertGroupForm] input[name=groupId]").focus();
		  return false;
	 }
	 else if(dataCheckResult == "no") {
		  alert("중복확인을 해주세요.");
		  $("form[name=insertGroupForm] input[name=groupId]").focus();
		  return false;
	 }
	 else if(dataCheckResult == "fail") {
		  alert("이미 사용중인 아이디 입니다.");
		  $("form[name=insertGroupForm] input[name=groupId]").focus();
		  return false;
	 }
	 else if(groupNm == "") {
		  alert("그룹명을 입력해주세요");
		  $("form[name=insertGroupForm] input[name=groupNm]").focus();
		  return false;
	 }
	 else if(useFlag == "") {
		  alert("사용여부를 체크해주세요");
		  return false;
	 } 
	  let params = {
			groupId : groupId ,
			groupNm : groupNm ,
			useFlag : useFlag 
	  }
     console.log(params);
	
	  if(confirm("등록하시겠습니까?")) {
		$.ajax({
		    url:"/bo/system/group/group/insert",
		    type: "post",
		    dataType: 'json',
		    contentType: "application/json",
		    data: JSON.stringify(params),
		    success : function(data) {
		    location.href="/bo/system/org/group";
			alert(data.message);
        },
		    error : function() {
			alert(data.message);
                }
           });  
		}
});
/** [중복확인] 버튼 클릭시 */
$("button[data-role=check]").click(function(){  	
      
       let id = $("form[name=insertGroupForm] input[name=groupId]").val().trim();
      
       $("form[name=insertGroupForm] input[name=groupId]").attr("data-check-result");
       
		if (id == "") {
		$("form[name=insertGroupForm] input[name=groupId]").focus();
		alert("그룹 아이디 입력 후 중복확인 버튼을 눌러주세요");
			return false;
		}
       $.ajax({
           type: "get",
           url: "/bo/system/org/group/check",
           data: {groupId : id},
           success: function(result) {
             if(result.data == 0) {
               alert("사용 가능한 아이디입니다.");
               $("form[name=insertGroupForm] input[name=groupId]").attr("data-check-result", "ok");
               $("button[data-role=check]").attr("disabled", "disabled");
            } else {
               alert("이미 사용중인 아이디입니다.");
               $("form[name=insertGroupForm] input[name=groupId]").focus();
               $("form[name=insertGroupForm] input[name=groupId]").attr("data-check-result", "fail");
               return false;
            }
         },
      });
});
/** 그룹등록 시 그룹아이디 중복검사 후 그룹아이디 변경 됐을때 중복확인 버튼 disabled 해제 */
$("form[name=insertGroupForm] input[name=groupId]").change(function(){
	$("button[data-role=check]").attr("disabled", false);
	$("form[name=insertGroupForm] input[name=groupId]").attr("data-check-result", "no");
 });
/** 그룹등록 시 그룹아이디 중복검사 후 그룹아이디 수정시 focus */
 $("form[name=insertGroupForm] input[name=groupId]").focus(function(){
	$("button[data-role=check]").attr("disabled", false);
	$("form[name=insertGroupForm] input[name=groupId]").attr("data-check-result", "no");
});
/** [목록]버튼 클릭시 */
$("button[data-role=list]").click(function(){
   window.location.href = "/bo/system/org/group";
});