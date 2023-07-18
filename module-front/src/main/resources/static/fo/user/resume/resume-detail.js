$(document).ready(function(){
    $('[data-role=careerModify]').hide();
    $('[data-role=careerDelete]').hide();
})

$("span[name=phone]").text(Utils.formatPhoneNumber($("span[name=phone]").text())); // 핸드폰번호 정규식
