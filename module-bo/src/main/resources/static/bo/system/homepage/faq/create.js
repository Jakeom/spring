CKEDITOR.replace('faqContent');

let memberTypeCd="AP";
selectBox(memberTypeCd);

function selectBox(memberTypeCd) {
	$.ajax({ 
		url: "/bo/system/homepage/faq/modal/",
		method: "get",
        data: {memberTypeCd: memberTypeCd},
		datatype: "data",
	})
	.done(function(data){
		if(memberTypeCd !="HH") {
			document.getElementById("AP").classList.remove('btn-outline-dark');
			document.getElementById("AP").classList.add('btn-primary');
			document.getElementById("HH").classList.remove('btn-primary');
			document.getElementById("HH").classList.add('btn-outline-dark');
		} else if(memberTypeCd !="AP") {
			document.getElementById("HH").classList.remove('btn-outline-dark');
			document.getElementById("HH").classList.add('btn-primary');
			document.getElementById("AP").classList.remove('btn-primary');
			document.getElementById("AP").classList.add('btn-outline-dark');
		}
		var selectnum = data.data;
		var selectBox = '';
		for (var i = 0; i < selectnum.length; i++) {
			var selectBox = '<option value="'+ selectnum[i].faqCategorySeq + '">' + selectnum[i].categoryNm + '</option>';
	        $("#selectBox").append(selectBox);
	    }
	})
	.fail(function (xhr, status, errorThrown) {
		alert("*****ajax fail*****");
	})
}


$("button[data-role=AP]").click(function () {
	memberTypeCd="AP";
	$("#selectBox").empty();
	selectBox(memberTypeCd);
});

$("button[data-role=HH]").click(function () {
	memberTypeCd="HH";
	$("#selectBox").empty();
	selectBox(memberTypeCd);
});


$("button[data-role=save]").click(function () {
    /* insert data */
    let title = $("form[name=insertForm] input[name=title]").val();
    let content = CKEDITOR.instances.faqContent.getData();
    let faqCategorySeq = $("select[name=selectBox]").val();
    
    if (title == "") {
        alert("제목을 입력해주세요.");
        $("form[name=insertForm] select[name=title]").focus();
        return false;
    }
    if (content == "") {
        alert("내용을 입력해주세요.");
        $("form[name=insertForm] input[name=faqContent]").focus();
        return false;
    }
    
    let params = {
        title: title,
        content: content,
        faqCategorySeq: faqCategorySeq
    }

    if (!confirm("등록하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/system/homepage/faq/create/insert",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("등록되었습니다.");
                window.location.href = "/bo/system/homepage/faq";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
      }
});

$("button[data-role=cancel]").click(function () {
    window.location.href = "/bo/system/homepage/faq";
});