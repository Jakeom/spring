CKEDITOR.replace('contentFaq');

let memberTypeCd=$("#faqModifyType").val();
let categoryNm =$("#faqModifyNm").val();

selectBox(memberTypeCd, categoryNm);

function selectBox(memberTypeCd, categoryNm) {
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
			var selectBox = '<option value="'+ selectnum[i].faqCategorySeq + '">' + selectnum[i].categoryNm + '</option>'
	        if(categoryNm  == selectnum[i].categoryNm) {
				selectBox = '<option value="'+ selectnum[i].faqCategorySeq + '" selected="selected">' + selectnum[i].categoryNm + '</option>'
			}
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

$("button[data-role=cancel]").click(function () {
    window.location.href = "/bo/system/homepage/faq";
});

$("button[data-role=delete]").click(function () {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/system/homepage/faq/delete",
            data: {faqSeq: $("#faqModifySeq").val()},
            success: function (data) {
                alert("삭제되었습니다.");
                window.location.href = "/bo/system/homepage/faq";
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})


$("button[data-role=save]").click(function () {
	let title = $("form[name=modifyForm] input[name=title]").val();
	let content = CKEDITOR.instances.contentFaq.getData();
    let faqCategorySeq = $("select[name=selectBox]").val(); 
	    if (!confirm("저장하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/system/homepage/faq/modifysave",
            data: {
				faqSeq: $("#faqModifySeq").val(),
				title: title,
				content: content,
				faqCategorySeq: faqCategorySeq
            },
            success: function (data) {
                alert("저장되었습니다.");
                window.location.href = '/bo/system/homepage/faq/detail?faqSeq=' + $("#faqModifySeq").val();
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
})