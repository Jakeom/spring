$(document).ready(function () {
    $("#startBtn").click()
})


/****기간 검색**** */
let start="";
let end=toStringByFormatting(new Date(), '-');

/** date format */
function toStringByFormatting(source, delimiter = '-') {
    var year = source.getFullYear();
    var month = source.getMonth() + 1 ;
    var day = source.getDate() ;
    return [year, month >= 10  ? month : '0' + month, day >= 10 ? day : '0' + day].join(delimiter);
}

/** 오늘 검색 */
$("button[data-role=today]").click(function() {
    let current = toStringByFormatting(new Date(), '-');
    start = current;
    end = current;
    $('#wefirmStartDt').datepicker('setDate', start);
    $('#wefirmEndDt').datepicker('setDate', end);
})

/** 일주일 검색 */
$("button[data-role=week]").click(function() {
    const current = new Date();
    current.setDate(current.getDate() - 7);
    let lastWeek = toStringByFormatting(current, '-');
    start = lastWeek;
    end = toStringByFormatting(new Date(), '-');
    $('#wefirmStartDt').datepicker('setDate', start);
    $('#wefirmEndDt').datepicker('setDate', end);
})

/** 한 달 검색 */
$("button[data-role=month]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#wefirmStartDt').datepicker('setDate', start);
    $('#wefirmEndDt').datepicker('setDate', end);
})

/** 전체 검색 */
$("button[data-role=all]").click(function() {
    start="";
    end=toStringByFormatting(new Date(), '-');
    $('#wefirmStartDt').datepicker('setDate', null);
    $('#wefirmEndDt').datepicker('setDate', null);
})

$('#wefirmStartDt').change(function() {
    start = $(this).val();
})

$('#wefirmEndDt').change(function() {
    end = $(this).val();
})

$("button[data-role=save]").hide();
let wefirmId = $("input[data-role=wefirmId]").val();
/* [정상] 버튼 클릭시 */
$("button[data-role=changeStatus]").click(function() {
    let wefirmId = $("input[data-role=wefirmId]").val();
    let params ={
        id : wefirmId,
        closed: '0'
    }
    if (!confirm("수정하시겠습니까?")) {
        return;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/management/complaint/wefirm/status/update",
            data: params,
            success: function (data) {
                alert("상태가 변경되었습니다.");
                location.reload()
            },
            error: function () {
                alert("수정에 실패했습니다.");
            }
        });
    }
})

var table2 = $("#datatable2").DataTable({
    ajax: {
        url: "/bo/management/complaint/wefirm/headhunter/detail?id=" + wefirmId  ,
        dataSrc: function(data) {
            return data.data;
        }
    },
    searching : false,
    order: [[1, 'desc']],
    language: {
        emptyTable: "데이터가 없습니다.",
        lengthMenu: "페이지당 _MENU_ 개씩 보기",
        info: "현재 _START_ - _END_ / _TOTAL_건",
        infoEmpty: "데이터 없음",
        infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
        search: "",
        zeroRecords: "일치하는 데이터가 없습니다.",
        loadingRecords: "로딩중...",
        processing: "잠시만 기다려 주세요.",
        paginate: {
            next: "다음",
            previous: "이전",
        },
    },
    columns: [
        { "data": "memberId" },
        { "data": "createdAt", "defaultContent": "-"},
        { "data": "memberName" },
        { "data": "memberPhone" },
        { "data": "memberEmail" },
        { "data": "positionCnt" },
        { "data": "resumeCnt" },
        {
            render: function(data, type, row) {
                return "-";
            }
        },

    ],
    columnDefs: [
        {
            "targets": [7],
        }
    ]
});

var table3 = $("#datatable3").DataTable({
    ajax: {
        url: "/bo/management/complaint/wefirm/recruit/detail?id=" + wefirmId  ,
        dataSrc: function(data) {
            return data.data;
        }
    },
    searching : false,
    order: [[1, 'desc']],
    language: {
        emptyTable: "데이터가 없습니다.",
        lengthMenu: "페이지당 _MENU_ 개씩 보기",
        info: "현재 _START_ - _END_ / _TOTAL_건",
        infoEmpty: "데이터 없음",
        infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
        search: "",
        zeroRecords: "일치하는 데이터가 없습니다.",
        loadingRecords: "로딩중...",
        processing: "잠시만 기다려 주세요.",
        paginate: {
            next: "다음",
            previous: "이전",
        },
    },
    columns: [
        { "data": "id" },
        { "data": "createdAt" },
        { "data": "title" },
        { "data": "memberName" },
        { "data": "positionCd" },
        { "data": "endDate" },
    ],
    columnDefs: [
        {
            "targets": [5],
        }
    ]
});

/* [강제폐쇄] 버튼 클릭시 */
$("button[data-role=forceStop]").click(function () {

    let wefirmId = $("input[data-role=wefirmId]").val();
    let params ={
        id : wefirmId,
        closed: '1'
    }

    if (!confirm("강제폐쇄 하시겠습니까?")) {
        return;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/management/complaint/wefirm/status/update",
            data: params,
            success: function (data) {
                alert("강제폐쇄 되었습니다.");
                location.reload()
            },
            error: function () {
                alert("수정에 실패했습니다.");
            }
        });
    }
});

//update table into input fields
let input1 = document.createElement('input');
let input2 = document.createElement('input');
let input3 = document.createElement('input');
/* [수정] 버튼 클릭시 */
$("button[data-role=update]").click(function(){

    $("button[data-role=save]").show();
    let sfName= $('#searchFirmName');
    input1.value = sfName.text();
    input1.classList.add("form-control");
    sfName.empty();
    sfName.append(input1);
    input1.focus();

    let phone= $('#phone');
    input2.value = phone.text();
    input2.classList.add("form-control");
    phone.empty();
    phone.append(input2);
    input2.focus();

    let regNum= $('#regNum');
    input3.value = regNum.text();
    input3.classList.add("form-control");
    regNum.empty();
    regNum.append(input3);
    input3.focus();

})
/* [수정  ] 버튼 클릭시 */
$("button[data-role=save]").click(function(){

    let wefirmId = $("input[data-role=wefirmId]").val();
    if (!confirm("저장 하시겠습니까?")) {
        return false;
    } else {

        let params ={
            sfName : input1.value,
            sfPhone : input2.value,
            sfRegNumber : input3.value,
            id : wefirmId
        }
        console.info(params);

        $.ajax({
            type: "post",
            url: "/bo/management/complaint/wefirm/update",
            data: params,
            dataType : 'json',
            success: function () {
                alert("저장되었습니다.");
                window.location.href = "/bo/management/complaint/wefirm/detail?id=" + wefirmId ;
            },
            error: function () {
                alert("저장에 실패했습니다.");
            }
        });
    }
})

/** 엑셀 다운로드 */
$("button[data-role=downloadExcel]").click(function () {
    let name = ''
    let phone = ''
    let email = ''
    if ($("#personalInfo").val() == "name") {
        name = $("input[data-role=writer]").val()
    } else if($("#personalInfo").val() == "phone") {
        phone = $("input[data-role=writer]").val()
    } else if($("#personalInfo").val() == "email") {
        email = $("input[data-role=writer]").val()
    }
    location.href = "/bo/management/complaint/wefirm/headhunter/excelDownload?id=" + $("input[data-role=wefirmId]").val() + "&memberEmail=" + email +
        "&memberPhone=" + phone + "&memberName=" + name
})
$("button[data-role=search]").click(function () {
    let params
    let url
    let columns
    let targets
    let table
    if($("button[name=joinHh]").hasClass("active") === true) {
        let name = ''
        let phone = ''
        let email = ''
        if ($("#personalInfo").val() == "name") {
            name = $("input[data-role=writer]").val()
        } else if($("#personalInfo").val() == "phone") {
            phone = $("input[data-role=writer]").val()
        } else if($("#personalInfo").val() == "email") {
            email = $("input[data-role=writer]").val()
        }
        url = "/bo/management/complaint/wefirm/headhunter/detail?id=" + wefirmId
        params = {
            memberName: name,
            memberPhone: phone,
            memberEmail: email,
            id: $("input[data-role=wefirmId]").val()
        }

        columns = [
            { "data": "memberId" },
            { "data": "createdAt", "defaultContent": "-"},
            { "data": "memberName" },
            { "data": "memberPhone" },
            { "data": "memberEmail" },
            { "data": "positionCnt" },
            { "data": "resumeCnt" },
            {
                render: function(data, type, row) {
                    return "-";
                }
            }
        ]
        targets = [7];
        table = $("#datatable2")
    } else {
        url = "/bo/management/complaint/wefirm/recruit/detail?id=" + wefirmId
        params = {
            searchStart: start,
            searchEnd: end,
            id: $("input[data-role=wefirmId]").val()
        }
        columns = [
            { "data": "id" },
            { "data": "createdAt" },
            { "data": "title" },
            { "data": "memberName" },
            { "data": "positionCd" },
            { "data": "endDate" },
        ]
        targets = [5]
        table = $("#datatable3")
    }
    table.DataTable().clear().destroy();
    table.DataTable({
        ajax: {
            url: url,
            data: params,
            dataSrc: function(data) {
                return data.data;
            }
        },
        searching : false,
        order: [[1, 'desc']],
        language: {
            emptyTable: "데이터가 없습니다.",
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            info: "현재 _START_ - _END_ / _TOTAL_건",
            infoEmpty: "데이터 없음",
            infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
            search: "",
            zeroRecords: "일치하는 데이터가 없습니다.",
            loadingRecords: "로딩중...",
            processing: "잠시만 기다려 주세요.",
            paginate: {
                next: "다음",
                previous: "이전",
            },
        },
        columns: columns,
        columnDefs: [
            {
                "targets": targets,
            }
        ]
    });

})

$(".styleRemove").on("click", function () {
    $("#datatable2").attr("style", "");
    $("#datatable3").attr("style", "");
})
/** [] 검색 클릭시 */
// $("button[data-role=search]").click(function() {
//
//
//     let writer = $("form[name=searchRecruit] input[data-role=writer] ").val();
//     let searchKey = $("form[name=searchRecruit] input[data-role=searchKey]").val();
//     let radioButtons = $("input[type=radio]:checked").val();
//     let memberPersonalInfo =   document.getElementById("personalInfo").value;
//     let writerPhone;
//     let writerName;
//     let writerEmail;
//     let del;
//
//
//     if(radioButtons == "ALL"){
//         radioButtons = null;
//
//     }if(radioButtons == "DEL"){
//         del="Y";
//     }
//
//     if (memberPersonalInfo == 'phone') {
//         writerPhone = writer;
//     }else if (memberPersonalInfo == 'name') {
//         writerName = writer;
//     }else if (memberPersonalInfo == 'email') {
//         writerEmail = writer;
//     }
//
//     let params = {
//         delFlag : del,
//         word : word,
//         phone : writerPhone,
//         email : writerEmail,
//         name : writerName,
//         keywords : searchKey,
//         status : radioButtons,
//         searchStart: start,
//         searchEnd: end,
//     };
//
//
//     $("#datatable").DataTable().clear().destroy();
//     $("#datatable").DataTable({
//
//         ajax: {
//             type: "post",
//             url: "/bo/management/operation/recruit/search",
//             data: params,
//             dataSrc: function(data) {
//                 return data.data;
//             }
//         },
//         columns: [
//             { "data": "id" },
//             { "data": "name" },
//             { "data": "createdAt" },
//             {
//                 render: function(data, type, row) {
//                     return "<a style='font-weight: bold;' href=" + row.id + "'/bo/management/operation/recruit/detail?id='>" + row.title + "</a>";
//                 }
//             },
//             { "data": "industry" },
//             { "data": "endDate" },
//             { "data": "isCowork" },
//
//         ],
//         columnDefs: [
//             {
//                 "targets": [6],
//             }
//         ]
//     })
//
// })


// /* [취소] 버튼 클릭시 */
// $("button[data-role=list]").click(function () {
//     window.location.href = '/bo/management/operation/recruit'
// });



//
// let coWork= $('#coWork').val();
//
// if(coWork == '0'){
//     $('#coWorkDetail').hide();
// }
//
// let status = $("#status").val();
//
// let defaultOption;
// if(status == "END"){
//     $('#selectType').val("END");
// }
// if(status == "CLOSE"){
//     $('#selectType').val("CLOSE");
// }
// if(status == "DEL"){
//     $('#selectType').val("DEL");
// }
// if(status == "DOING"){
//     $('#selectType').val("DOING");
// }
//
//
// /* [상태] 버튼 클릭시 */
// $('#selectType').change(function(){
//     console.info($(this).val());
//     if (!confirm("포지션 상태를 변경하시겠습니까?")) {
//         return false;
//     } else {
//         $.ajax({
//             type: "post",
//             url: "/bo/management/operation/recruit/update",
//             data: {
//                 status : $("#selectType").val(),
//                 id : $("#recruitId").val()
//             },
//             success: function (data) {
//                 alert("변경되었습니다.");
//                 window.location.href = "/bo/management/operation/recruit/detail?id=" + $("#recruitId").val() ;
//             },
//             error: function () {
//                 alert("*****ajax fail*****");
//             }
//         });
//     }
// })
//
// $("button[data-role=delete]").click(function () {
//     if (!confirm("삭제하시겠습니까?")) {
//         return false;
//     } else {
//         $.ajax({
//             type: "post",
//             url: "/bo/management/operation/recruit/delete",
//             data: {recruitSeq: $("#recruitDetailSeq").val()},
//             success: function (data) {
//                 alert("삭제되었습니다.");
//                 window.location.href = "../recruit";
//             },
//             error: function () {
//                 alert("*****ajax fail*****");
//             }
//         });
//     }
// })


