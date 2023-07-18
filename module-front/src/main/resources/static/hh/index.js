$('button[data-role=positionWrite]').click(function (){
    location.href='/hh/position/position-register';
})

function nvl(v) {
    return (v == null || v == '' || v == undefined ? '' : v);
}

$(document).ready(function(){

    if(nvl($("#autoLoginToken").val()) != ''){
        localStorage.setItem("autoLoginToken", $("#autoLoginToken").val());
        localStorage.setItem("autoLogindType", $("#autoLogindType").val());
        localStorage.setItem("autoLoginMemberId", $("#autoLoginMemberId").val());
        localStorage.setItem("autoLoginLoginId", $("#autoLoginLoginId").val());
    }
})