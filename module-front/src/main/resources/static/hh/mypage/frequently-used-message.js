CKEDITOR.replace('emailContent');
let clickNum = 0;
let clickStatus = 'SMS'

$("[data-role=clickName]").on("click", function () {
    $("#smsName").val($(this).text())
    clickNum = $(this).closest("li").find("input[name=contentId]").val()
    let subject = $(this).closest("li").find("input[name=subject]").val()
    let content = $(this).closest("li").find("input[name=content]").val()
    $("#smsSubject").val(subject)
    $("#smsContent").val(content)
})

$("[data-role=clickNameEmail]").on("click", function () {
    $("#emailName").val($(this).text())
    $("#fileArea").empty()
    $("#fileLabel").text("")
    $("#fileLabel").append('<i class="icon-input-add"></i>')
    clickNum = $(this).closest("li").find("input[name=contentId]").val()
    let subject = $(this).closest("li").find("input[name=subject]").val()
    let content = $(this).closest("li").find("input[name=content]").val()
    $("#emailSubject").val(subject)
    CKEDITOR.instances.emailContent.setData(content)

    $.ajax({
        url: "/hh/mypage/frequently-used-message/file/detail",
        method: "post",
        data: {
            id: clickNum
        },
        datatype: "data",
        success: function (data) {
            if(data.data) {
                /** 형태 자르기 자바스크립트형식 */
                $("#fileArea").append("<a class='fileCheck' href=' " + data.data[0].url + " '>" + data.data[0].originName + "</a>")
                $("#fileArea").append("<button class='btn btn-primary p-l-10 font-size-12px' data-role='emptyFile'>파일 삭제</button>")
            }
        },
        error: function () {
            alert("파일 불러오기 실패")
        }
    })
})
$(document).on("click", "[data-role=emptyFile]", function () {
    $("#fileArea").empty()
})

$("[data-role=deleteTemplate]").on("click", function () {
    let deleteTemplateList = [];
    let i = 0;
    $('input[data-role="checkBox"]:checked').each(function (index) {
        deleteTemplateList[i] = $(this).attr("name");
        i++;
    });
    if(!confirm("템플릿을 삭제하시겠습니까?")) {
        return false
    } else {
        $.ajax({
            url: "/hh/mypage/frequently-used-message/delete/template",
            method: "post",
            data: {
                deleteTemplateList: deleteTemplateList,
                mediaTypeCd: clickStatus
            },
            datatype: "data",
            success: function () {
                alert("삭제되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
})

$("[data-role=smsSave]").on("click", function () {
    let smsSubject = $("#smsSubject").val()
    let smsContent = $("#smsContent").val()
    let name = $("#smsName").val()
    let id = clickNum
    let url;
    let params;
    
    if (smsSubject == "") {
		alert("제목을 입력해주세요.");
		$("#smsSubject").val().focus;
		return false;
	} else if (smsContent == "") {
		alert("내용을 입력해주세요.");
		$("#smsContent").val().focus;
		return false;
	} else if (name == "") {
		alert("템플릿 제목을 입력해주세요.");
		$("#smsName").val().focus;
		return false;
	}
    
    if(id == 0) {
        params = {
            'mediaTypeCd': clickStatus,
            'subject': smsSubject,
            'content': smsContent,
            'name' : $("#smsName").val()
        }
        url = "/hh/mypage/frequently-used-message/create/template/sms";
    } else {
        params = {
            id: id,
            'mediaTypeCd': clickStatus,
            'subject': smsSubject,
            'content': smsContent,
            'name' : $("#smsName").val()
        }
        url = "/hh/mypage/frequently-used-message/modify/sms"
    }
    $.ajax({
        url: url,
        method: "post",
        data: params,
        datatype: "data",
        success: function () {
            alert("저장되었습니다.");
            location.reload()
        },
        error: function () {
            alert("ajax fail")
        }
    });
})

$("#home-tab").on("click", function () {
    clickStatus = 'SMS'
})

$("#profile-tab").on("click", function () {
    clickStatus = 'EMAIL'
})
$("#form-file").on("change", function () {
    let fileName = $(this).val()
    let file = fileName.split("\\");
    $("#fileLabel").text(file[2])
    $("#fileArea").empty()
})

$("[data-role=emailSave]").on("click", function () {
    let fileChangeCheck
    if($(".fileCheck").length != 0) {
        fileChangeCheck = 0
    } else {
        fileChangeCheck = 1
    }
    let emailSubject = $("#emailSubject").val()
    let emailContent = CKEDITOR.instances.emailContent.getData()
    let name = $("#emailName").val()
    let id = clickNum
    let url
    let formData = new FormData();
   
    if (emailSubject == "") {
		alert("제목을 입력해주세요.");
		$("#emailSubject").val().focus;
		return false;
	} else if (emailContent == "") {
		alert("내용을 입력해주세요.");
		CKEDITOR.instances.emailContent.getData().focus;
		return false;
	} else if (name == "") {
		alert("템플릿 제목을 입력해주세요.");
		$("#emailName").val()
		return false;
	}
    
    if(id == 0) {
        $("#form-file").each(function(){
            Array.from($(this)[0].files).map(e => formData.append('emailFiles', e));
        });
        let jsonData = {
            'mediaTypeCd': clickStatus,
            'subject': emailSubject,
            'content': emailContent,
            'name': $("#emailName").val()
        }
        formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
        url = "/hh/mypage/frequently-used-message/create/template/email";
    } else {
        if($(".fileCheck").length == 0) {
            $("#form-file").each(function(){
                Array.from($(this)[0].files).map(e => formData.append('emailFiles', e));
            });
        }
        let jsonData = {
            'id': id,
            'subject': emailSubject,
            'content': emailContent,
            'fileChangeCheck' :fileChangeCheck,
            'name': $("#emailName").val()
        }
        formData.append("jsonData", new Blob([JSON.stringify(jsonData)], {type: 'application/json'}));
        url = "/hh/mypage/frequently-used-message/modify"
    }

    $.ajax({
        url: url,
        method: "post",
        data: formData,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function () {
            alert("저장되었습니다.");
            location.reload()
        },
        error: function () {
            alert("ajax fail")
        }
    });
})

$("[data-role=createTemplate]").on("click", function () {
    if(clickStatus == "SMS") {
        $("#smsSubject").focus()
        $("#smsSubject").val("")
        $("#smsName").val("")
        $("#smsContent").val("")
        clickNum = 0;
    } else {
        $("#emailSubject").focus()
        $("#fileArea").empty()
        $("#fileLabel").text("")
        $("#fileLabel").append('<i class="icon-input-add"></i>')
        $("#emailName").val("")
        $("#emailSubject").val("")
        CKEDITOR.instances.emailContent.setData("")
        clickNum = 0;
    }
})

$('#smsContent').keyup(function (e) {
    let content = $(this).val();

    // 글자수 세기
    if (content.length == 0 || content == '') {
        $('#textCnt').text('0');
    } else {
        $('#textCnt').text(content.length );
    }
    // 글자수 제한
    if (content.length > 2000) {
        // 200자 부터는 타이핑 되지 않도록
        $(this).val($(this).val().substring(0, 2000));
        // 200자 넘으면 알림창 뜨도록
        alert('글자수는 2000자까지 입력 가능합니다.');
    };
});

$("button[data-role=replacement-btn]").click(function () {
    $(".replacement-tag-notice").addClass("active");
});

$(".replacement-tag-notice .close").click(function () {
    $(".replacement-tag-notice").removeClass("active");
});

$(".nav-link").click(function(){
   $(".replacement-tag-notice .close").trigger("click");
});

$("tr[data-role=name]").click(function(){
	let type = $(this).data("value");
	let content = "";
	if(type == "name") {
		content = "{$수신자이름$}";
	}
	if(type == "title") {
		content = "{$포지션제목$}";
	}
	if(type == "companyNm") {
		content = "{$채용사이름$}";
	}

    let id = $(".tab-pane.active").attr("id");
    if(id == "home"){
        $("#smsContent").val($("#smsContent").val() + content);
    } else {
        CKEDITOR.instances["emailContent"].setData(CKEDITOR.instances["emailContent"].getData() + content);
    }
	//$("#emailContent").html(content);
})
