/** [등록] 버튼 클릭시 */
$("button[data-role=save]").click(function() {

	let orgId = $("form[name=insertOrgForm] input[name=orgId]").val().trim();
	let orgNm = $("form[name=insertOrgForm] input[name=orgNm]").val();
	let useFlag = $("form[name=insertOrgForm] input[name=useFlag]:checked").val();
	let dataCheckResult = $("form[name=insertOrgForm] input[name=orgId]").attr("data-check-result");

	/** 필수값 입력 */
	if (orgId == "") {
		alert("조직 아이디를 입력해주세요");
		$("form[name=insertOrgForm] input[name=orgId]").focus();
		return false;
	}
	else if(dataCheckResult == "no") {
		  alert("중복확인을 해주세요.");
		  $("form[name=insertOrgForm] input[name=orgId]").focus();
		  return false;
	 }
	 else if(dataCheckResult == "fail") {
		  alert("이미 사용중인 아이디 입니다.");
		  $("form[name=insertOrgForm] input[name=orgId]").focus();
		  return false;
	 }
	else if (orgNm == "") {
		alert("조직명을 입력해주세요");
		$("form[name=insertOrgForm] input[name=orgNm]").focus();
		return false;
	}
	else if (useFlag == "") {
		alert("사용여부를 체크해주세요");
		return false;
	}
	let params = {
		orgId : orgId,
		orgNm : orgNm,
		useFlag : useFlag,
	}
	console.log(params);

	if(confirm("등록하시겠습니까?")) {	 
		$.ajax({
			url: "/bo/system/org/org/insert",
			type: "post",
			dataType: 'json',
			contentType: "application/json",
			data: JSON.stringify(params),
			success: function(data) {
				alert(data.message);
				location.href = "/bo/system/org/org";
			},
			error: function() {
				alert(data.message);
			}
		});	
	}
});
/** [목록] 버튼 클릭시 */
$("button[data-role=list]").click(function() {
	window.location.href = "/bo/system/org/org";
});
/** [중복확인] 버튼 클릭시 */
$("button[data-role=check]").click(function(){  	
      
   let id = $("form[name=insertOrgForm] input[name=orgId]").val().trim();
   
    $("form[name=insertOrgForm] input[name=orgId]").attr("data-check-result");  
    
    if (id == "") {
		$("form[name=insertOrgForm] input[name=orgId]").focus();
		alert("조직 아이디 입력 후 중복확인 버튼을 눌러주세요");
			return false;
		}
       $.ajax({
         type: "get",
         url: "/bo/system/org/org/check",
         data: {orgId : id},
         success: function(result) {
            if(result.data == 0) {
                alert("사용 가능한 아이디입니다.");
                $("form[name=insertOrgForm] input[name=orgId]").attr("data-check-result", "ok");
				$("button[data-role=check]").attr("disabled", "disabled");
            } else {
               alert("이미 사용중인 아이디입니다.");
               $("form[name=insertOrgForm] input[name=orgId]").focus();
               $("form[name=insertOrgForm] input[name=orgId]").attr("data-check-result", "fail");
               return false;
            }
         },
      });
});
/** 조직등록 시 조직아이디 중복검사 후 조직아이디 변경 됐을때 중복확인 버튼 disabled 해제 */
$("form[name=insertOrgForm] input[name=orgId]").change(function(){
	$("button[data-role=check]").attr("disabled", false);
	$("form[name=insertOrgForm] input[name=orgId]").attr("data-check-result", "no");
 });
/** 조직등록 시 조직아이디 중복검사 후 조직아이디 수정시 focus */
 $("form[name=insertOrgForm] input[name=orgId]").focus(function(){
	$("button[data-role=check]").attr("disabled", false);
	$("form[name=insertOrgForm] input[name=orgId]").attr("data-check-result", "no");
});