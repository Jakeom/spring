// 구매
$("[data-role=purchase]").click(function() {
    if(Number($('input[name=myPoint]').val()) <Number($('input[name=goodsPoint]').val())){
        alert("리워드 포인트가 부족합니다.")
         return false;
    }
    let increase = '-'+$('input[name=goodsPoint]').val();
    let rewardGoodsId = $('input[name=rewardGoodsId]').val();
    $.ajax({
        url: '/fo/mypage/reward-purchase',
        type: 'POST',
        data: {'increase': increase,
            'rewardGoodsId': rewardGoodsId
        },
        success: function(data) {
            if(data.status == 200){
                alert('구매가 완료되었습니다.');
                location.href ='/fo/mypage/reward'
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
});