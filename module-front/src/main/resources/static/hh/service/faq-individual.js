$(document).ready(function(){
    if(nvl($('#faqSeq').val())!=''){
        let cateSeq = $('#faqCategorySeq').val();
        $('#cate'+cateSeq).trigger('click')
    }
})

//카테고리 클릭시
$('input[name=register]').click(function() {
    $('.accordion-agree').empty();
    $('input[name=register]').removeClass("active");
    $(this).addClass("active");
        $.ajax({
            url: '/hh/service/faqList',
            type: 'GET',
            data: { faqCategorySeq : $(this).data('faqcategoryseq'),
                    searchVal : $('input[name=searchVal]')[1].value
            },
            success: function(data) {
                if(data.status == 200){
                    let faqList = data.data;
                   for(let i=0; i<faqList.length; i++){
                       let appendHtml = "";
                       appendHtml +='<div class="item" id="faq'+faqList[i].faqSeq+'" data-faqSeq="'+faqList[i].faqSeq+'" onclick="addActive(this)">';
                       appendHtml +='<div class="agree-top">';
                       appendHtml +='<p>Q. '+faqList[i].title+'</p>';
                       appendHtml +='<a href="javascript:;" class="toggler">';
                       appendHtml +='<i class="icon-arrow"><span class="sr-only">자세히보기</span></i>';
                       appendHtml +='</a>';
                       appendHtml +='</div>';
                       appendHtml +='<div class="agree-body">';
                       appendHtml += faqList[i].content
                       appendHtml +='</div>';
                       appendHtml +='</div>';
                       $('.accordion-agree').append(appendHtml);
                   }
                    let faqSeq =$('#faqSeq').val();
                    $('#faq'+faqSeq).addClass("active")
                } else {
                    alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
                }
            },
            error: function(){
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        });
})

function addActive(obj){
    $(obj).toggleClass("active");
}

$('[data-role=searchBtn]').click(function() {
    let seq = $('input[name=register]')[0].id
    $('#'+seq).trigger("click")
})

function nvl(v) {
    return (v == null || v == '' || v == undefined ? '' : v);
}