$(document).ready(function () {
    // 연령 selectbox option 추가
    let year = new Date().getFullYear();
    let age = 20;
    for(let i = year-21; i >= year-61; i--) {
        let ageOption = $("<option value='"+age+"'>"+age+"세("+i+")</option>");
        $("select[name=ageSt]").append(ageOption);
        $("select[name=ageEn]").append(ageOption.clone());
        age++;
    }

    let apAlertSeq = $("input[name=apAlertSeq]").val();
    if(apAlertSeq != null && apAlertSeq != "") { // 맞춤인재알림 seq 존재 시 값 set

        $("select[name=ageSt]").val($("input[name=ageSt]").val());
        $("select[name=ageEn]").val($("input[name=ageEn]").val());

        $.ajax({
            type: "get",
            url: "/hh/mypage/hp2",
            data: {apAlertSeq: $("input[name=apAlertSeq]").val()},
            success: function (data) {
                $(data.data).each(function (i, d) {
                    let self = this;
                    $("input[name=hp2]").each(function () {
                        if ($(this).val() === self.hp2) {
                            $(this).prop("checked", true);
                        }
                    });
                });
            }
        });

        $.ajax({
            type: "get",
            url: "/hh/mypage/loc",
            data: {apAlertSeq: $("input[name=apAlertSeq]").val()},
            success: function (data) {
                $(data.data).each(function (i, d) {
                    let self = this;
                    $("input[name=loc]").each(function () {
                        if ($(this).val() === self.loc) {
                            $(this).prop("checked", true);
                        }
                    });
                });
            }
        });
    }
});

$("button[data-role=save]").click(function(){

    let hhApFlag = $("input[name=checkAlarm]").val();
    if($("input[name=checkAlarm]").is(':checked')) {
        hhApFlag = 'Y';
    }
    let careerSt = $("select[name=careerSt]").val();
    let careerEn = $("select[name=careerEn]").val();
    let ageSt = $("select[name=ageSt]").val();
    let ageEn = $("select[name=ageEn]").val();
    let salarySt = $("select[name=salarySt]").val();
    let salaryEn = $("select[name=salaryEn]").val();
    let company = $("input[name=company]").val();
    let foreignLang = $("input[name=foreignLang]").val();
    let certificate = $("input[name=certificate]").val();
    let hp1 = $("input[name=hp1]:checked").val();
    let apAlertSeq = $("input[name=apAlertSeq]").val();
    let industry = $("input[name=industry]").val();

    // 학력2 선택값
    let hp2List = [];
    $("input[name=hp2]:checked").each(function(){
        let hp2 = $(this).val();
        hp2List.push(hp2)
    })

    // loc 선택값
    let locList = [];
    $("input[name=loc]:checked").each(function(){
        let loc = $(this).val();
        locList.push(loc);
    })

    let params = {
        apAlertSeq : apAlertSeq ,
        hhApFlag : hhApFlag ,
        careerSt : careerSt ,
        careerEn : careerEn ,
        ageSt : ageSt ,
        ageEn : ageEn ,
        salarySt : salarySt ,
        salaryEn : salaryEn ,
        company : company ,
        foreignLang : foreignLang ,
        certificate : certificate ,
        hp1 : hp1 ,
        hp2List : hp2List ,
        locList : locList ,
        industry : industry
    }
    console.log(JSON.stringify(params));
    // 조건체크
    if(parseInt(careerSt) > parseInt(careerEn)) { // 경력설정
        alert("경력조건을 다시 확인해주세요.");
        return false;
    }

    if(parseInt(ageSt) > parseInt(ageEn)) { // 연령설정
        alert("연령조건을 다시 확인해주세요.");
        return false;
    }

    if(parseInt(salarySt) > parseInt(salaryEn)) { // 연봉설정
        alert("연봉조건을 다시 확인해주세요.");
        return false;
    }

    if(!confirm("저장하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/hh/mypage/update-notice-setting",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("저장되었습니다.");
                location.reload();
            },
            error: function () {
                alert("시스템 오류가 발생했습니다. 관리자에게 문의하세요.")
            }
        });
    }
});

// 학력 동일 값 클릭 시 체크 해제
$("input[name=hp1]").change(function(){
    if($(this).val() == "RESET"){
        $(this).prop("checked", false);
    }
});