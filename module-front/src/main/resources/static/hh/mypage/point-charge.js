function getList(page){
    $("input[name=page]").val(page);
    $.ajax({
        type: "get",
        url: "/hh/mypage/point-use-history",
        data: $("#sleep").serialize(),
        success: function (html) {
            $("#sleep").html(html);
            $(".select-design").select2({
                minimumResultsForSearch: -1
            });
        },
        error: function () {
            alert("시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
        }
    });
}
getList(1);

$(document).on("click", "button[data-role=date]", function (e) {
    $("input[name=selectPeriod]").val($(this).val());
    getList(1);
})

$(document).on("change", "select[name=pointUseType]", function (e) {
    $("input[name=useType]").val($(this).val());
    getList(1);
})

$(document).on("click", 'a[data-role=btnGoPage]', function (e) {
    e.preventDefault();
    getList($(this).data("page"));
});

$("#myTab .nav-link").click(function(){
    if($(this).attr("id") == "profile"){
        getList(1);
    }
});

// datepicker
$(".form-date").datepicker({
    monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    showMonthAfterYear: true,
    showOtherMonths: true,
    dateFormat: "yy-mm-dd",
    gotoCurrent: true,
    changeYear: true,
    changeMonth: true
})

function nvl(v) {
    return (v == null || v == '' || v == undefined ? '' : v);
}

//진입시 취소가능포인트 계산
let totalCanDoRefundPoint = 0;
$('.canDoRefund').each(function (){
    totalCanDoRefundPoint += $(this).data("point");
})
$('#refundPoint').text(totalCanDoRefundPoint)

$("button[data-role=charge]").click(function(){
    if(nvl($('input[name=amount]:checked').val()) == '') {
        alert("충전 포인트를 선택해주세요.");
        return false;
    }
    if(nvl($('input[name=paymentType]:checked').val()) == '') {
        alert("결제수단을 선택해주세요.");
        return false;
    }
    if(!$("input[name=notiCheck]").prop('checked')) {
        alert("포인트 충전시 구매 유의사항 동의는 필수입니다.");
        return false;
    }

    let productId = $('input[name=amount]:checked').attr("id");
    let productName = $('input[name=amount]:checked').data("name");
    let price = $('input[name=amount]:checked').val();
    let userName = $('input[name=paymentUserName]').val();
    let userEmail = $('input[name=paymentUserEmail]').val();
    let userPhone = $('input[name=paymentUserPhone]').val();
    let method = $('input[name=paymentType]:checked').data("method");
    let freeIncrease = "";
    let paidIncrease = "";
    switch (productId){
        case 'ChargeOpt1' :
            freeIncrease = 0
            paidIncrease = 10000; break;
        case 'ChargeOpt2' :
            freeIncrease = 500
            paidIncrease = 30000; break;
        case 'ChargeOpt3' :
            freeIncrease = 1000
            paidIncrease = 50000; break;
        case 'ChargeOpt4' :
            freeIncrease = 25000
            paidIncrease = 100000; break;
    }
    pointPayment(productId, productName, price, userName, userEmail, userPhone, method, freeIncrease, paidIncrease);
});

// 조회기간에 따른 검색
$("button[data-role=date]").click(function(){
    if($(this).hasClass("active")){
        $("#startDt").val("");
        $("#endDt").val("");
        $("[data-role=date].active").removeClass("active");
    } else {
        $("[data-role=date].active").removeClass("active");
        $(this).addClass("active");
    }

    if($(this).hasClass("active")){
        let d = new Date();
        let year = d.getFullYear();
        let month = ("0"+(d.getMonth()+1)).slice(-2);
        let day = ("0"+d.getDate()).slice(-2);
        let nowDate = year+'-'+month+'-'+day;
        $("#endDt").val(nowDate);
        $("#endDt").datepicker('setDate', nowDate);
        console.log(nowDate);

        let val = $(this).val();
        if(val == "1") {
            month = ("0"+(d.getMonth())).slice(-2);
        }
        if(val == "3") {
            month = ("0"+(d.getMonth()-2)).slice(-2);
        }
        if(val == "6") {
            month = ("0"+(d.getMonth()-5)).slice(-2);
        }
        if(val == "12") {
            year = d.getFullYear()-1;
        }

        let date = year+'-'+month+'-'+day;
        $("#startDt").val(date);
        $("#startDt").datepicker('setDate', date);
    }

    getList(1);
});

// datepicker 선택에 따른 검색
$("input[name=dateSearch]").change(function(){
    let startDt = $("#startDt").val();
    let endDt = $("#endDt").val();

    $("#startDt").datepicker('setDate', startDt);
    $("#endDt").datepicker('setDate', endDt)

    if(startDt > endDt && endDt != '') {
        alert("날짜 검색조건이 올바르지 않습니다.\n 조건을 다시 확인해주세요.");
        $("#startDt").focus();
        return false;
    }

    getList(1);
})

// 포인트 사용종류에 따른 검색
$("select[name=pointUseType]").change(function(){
    getList(1);
});

//포인트 충전하기 버튼 클릭시 결제창
async function pointPayment(productId, productName, price, userName, userEmail, userPhone, method, freeIncrease, paidIncrease){
    const orderId = moment().local().locale('ko') + '-' + productId + '-' + method;
    try {
        const response = await Bootpay.requestPayment({
            "application_id": $('#paymentApplicationId').val(),
            "price": price, //실제 결제되는 가격
            "order_id": orderId, //고유 주문번호로, 생성하신 값을 보내주셔야 합니다.
            "order_name": "R9 포인트 충전",
            "pg": "다날",
            "method": method,
            "user": {
                "id": userEmail,
                "username": userName,
                "phone": userPhone,//,
                "email": userEmail
            },
            "items": [
                {   id: productId,
                    name: productName, //상품명
                    qty: 1, //수량
                    unique: productId, //해당 상품을 구분짓는 primary key
                    price: price, //상품 단가
                    cat1: 'R9 포인트', // 대표 상품의 카테고리 상, 50글자 이내
                    cat2: productName, // 대표 상품의 카테고리 중, 50글자 이내
                    cat3: productName, // 대표상품의 카테고리 하, 50글자 이내
                }
            ],
            user_info: {
                username: userName,
                email: userEmail,
                addr: '',
                phone: userPhone
            },
            params: {callback1: ''},
            "extra": {
                vbank_result: 1, // 가상계좌 사용시 사용, 가상계좌 결과창을 볼지(1), 말지(0), 미설정시 봄(1)
                quota: '0,2,3', // 결제금액이 5만원 이상시 할부개월 허용범위를 설정할 수 있음, [0(일시불), 2개월, 3개월] 허용, 미설정시 12개월까지 허용,
            }
        })
        switch (response.event) {
            case 'issued':
                // 가상계좌 입금 완료 처리
                break
            case 'done':
                console.log(response)
                updatePoint(freeIncrease, paidIncrease, response, productId)
                // 결제 완료 처리
                break
            case 'confirm': //payload.extra.separately_confirmed = true; 일 경우 승인 전 해당 이벤트가 호출됨
                console.log(response.receipt_id)
                /**
                 * 1. 클라이언트 승인을 하고자 할때
                 * // validationQuantityFromServer(); //예시) 재고확인과 같은 내부 로직을 처리하기 한다.
                 */
                const confirmedData = await Bootpay.confirm() //결제를 승인한다
                if(confirmedData.event === 'done') {
                    //결제 성공
                } else if(confirmedData.event === 'error') {
                    //결제 승인 실패
                }

                /**
                 * 2. 서버 승인을 하고자 할때
                 * // requestServerConfirm(); //예시) 서버 승인을 할 수 있도록  API를 호출한다. 서버에서는 재고확인과 로직 검증 후 서버승인을 요청한다.
                 * Bootpay.destroy(); //결제창을 닫는다.
                 */
                break
        }
    } catch (e) {
        // 결제 진행중 오류 발생
        // e.error_code - 부트페이 오류 코드
        // e.pg_error_code - PG 오류 코드
        // e.message - 오류 내용
        console.log(e.message)
        switch (e.event) {
            case 'cancel':
                // 사용자가 결제창을 닫을때 호출
                console.log(e.message);
                break
            case 'error':
                // 결제 승인 중 오류 발생시 호출
                console.log(e.error_code);
                break
        }
    }
}

//포인트 구매시 포인트 업데이트
function updatePoint(freeIncrease, paidIncrease, response, productId){
    let methodName = "";
    let pointProductId = "";
    switch (response.method){
        case 'card' : methodName = '카드'; break;
        case 'phone' : methodName = '핸드폰결제'; break;
        case 'bank' : methodName = '계좌이체'; break;
        case 'vbank' : methodName = '가상계좌'; break;
    }
    switch (productId){
        case 'ChargeOpt1' : pointProductId = '1'; break;
        case 'ChargeOpt2' : pointProductId = '2'; break;
        case 'ChargeOpt3' : pointProductId = '3'; break;
        case 'ChargeOpt4' : pointProductId = '4'; break;
    }
    let params = { 'type' : 'ADD_PURCHASE'
                 , 'freeIncrease' : freeIncrease
                 , 'paidIncrease' : paidIncrease
                 , 'method' : response.data.method_origin_symbol
                 , 'methodName' : methodName
                 , 'orderId' : response.data.order_id
                 , 'paymentData' : JSON.stringify(response.data)
                 , 'pg' : response.data.pg
                 , 'pgName' : response.data.pg
                 , 'price' : response.data.price
                 , 'productName' : response.data.order_name
                 , 'purchasedAt' : moment(response.data.purchased_at).format("YYYY-MM-DD HH:mm:ss")
                 , 'receiptId' : response.data.receipt_id
                 , 'status' : 'PAY_SUCCESS'
                 , 'unit' : 'krw'
                 , 'pointProductId' : pointProductId
                 , 'requestedAt' : moment(response.data.requested_at).format("YYYY-MM-DD HH:mm:ss")
                }
                console.log(params)
    $.ajax({
        url: '/hh/mypage/purchase-point',
        type: 'POST',
        data: params,
        dataType: 'json',
        success: function (d) {
            if(d.status == 200){
                window.location.reload()
            } else {
                alert('관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('관리자에게 문의하세요.');
        }
    });
}

//구매포인트 취소하기
$("button[data-role=cancelPurchase]").click(function(){
    let params = { 'type' : 'REFUND'
                 , 'receiptId' : $(this).data("receiptId")
                 , 'paymentId' : $(this).data("paymentId")
                 , 'cancelledPrice' : $(this).data("price")
                 , 'id' : $(this).data("historyId")
                 }
    console.log(params)
    $.ajax({
        url: '/hh/mypage/purchase-cancel',
        type: 'POST',
        data: params,
        dataType: 'json',
        success: function (d) {
            if(d.status == 200){
                alert("취소되었습니다.")
                window.location.reload()
            } else {
                alert('관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('관리자에게 문의하세요.');
        }
    });
})


