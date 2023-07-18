let start="";
let end=toStringByFormatting(new Date(), '-');

$(".form-datepicker").datepicker({
    minDate: 0,
    monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
    showMonthAfterYear: true,
    showOtherMonths: true,
    dateFormat: "yy-mm-dd",
    gotoCurrent: true,
    beforeShow: function beforeShow(input, inst) {
        $("#ui-datepicker-div").addClass("datepicker-box");
    }
})

function toStringByFormatting(source, delimiter = '-') {
    var year = source.getFullYear();
    var month = source.getMonth() + 1 ;
    var day = source.getDate() ;
    return [year, month >= 10  ? month : '0' + month, day >= 10 ? day : '0' + day].join(delimiter);
}

$("button[data-role=today]").click(function() {
    let current = toStringByFormatting(new Date(), '-');
    start = current;
    end = current;
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})

$("button[data-role=week]").click(function() {
    const current = new Date();
    current.setDate(current.getDate() - 7);
    let lastWeek = toStringByFormatting(current, '-');
    start = lastWeek;
    end = toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})

$("button[data-role=month]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})

$("button[data-role=months]").click(function() {
    const startCurrent = new Date();
    startCurrent.setMonth(startCurrent.getMonth() - 3);
    let startMonth = toStringByFormatting(startCurrent, '-');
    start = startMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', start);
    $('#endDate').datepicker('setDate', end);
})

$("button[data-role=all]").click(function() {
    start="";
    end=toStringByFormatting(new Date(), '-');
    $('#startDate').datepicker('setDate', null);
    $('#endDate').datepicker('setDate', null);
})

$(document).on("change", "select[name='memberTypeSelect']", function(){
    let memberTypeOption = $("#memberTypeSelect").val()
    if(memberTypeOption == "AP") {
        let appendOption = "<option value=''>전체</option><option value='name'>이름</option><option value='phone'>휴대번호</option><option value='email'>이메일주소</option><option value='id'>회원ID</option>"
        $("#searchSelect").empty();
        $("#searchSelect").append(appendOption);
    } else {
        let appendOption = "<option value=''>전체</option><option value='name'>이름</optio><option value='phone'>휴대번호</option><option value='email'>이메일주소</option><option value='id'>회원ID</option><option value='referralCode'>추천코드</option>"
        $("#searchSelect").empty();
        $("#searchSelect").append(appendOption);
    }
});

$('#DTYPE').change(function() {
    $("input[name=page]").val("1");
    $('form[name=searchMember]').submit();
});

$("#inputSelect").keyup(function(e) {
    if (e.keyCode === 13) {
        document.getElementById('search').click();
    }
});

$("button[data-role=search]").click(function() {
    $("input[name=page]").val("1");
    $('form[name=searchMember]').submit();
})

$("select[name=isTemp]").on("change", function () {
    $("input[name=page]").val("1");
    $('form[name=searchMember]').submit();
})

$("select[name=deleted]").on("change", function () {
    $("input[name=page]").val("1");
    $('form[name=searchMember]').submit();
})

$("select[name=isStop]").on("change", function () {
    $("input[name=page]").val("1");
    $('form[name=searchMember]').submit();
})

// paging
$("a[data-role=btnGoPage]").click(function() {
    let page = $(this).data("page");
    $("form[name=searchMember] input[name=page]").val(page);
    $("form[name=searchMember]").submit();
});

function loadTable() {

    /*let selectOption = $("#searchSelect").val()
    let name = "";
    let phone = "";
    let email = "";
    let id = "";
    let referralCode = "";
    let dtype = $("#DTYPE").val();
    let deleted = $("select[name=deletedSelected]").val()
    let isTemp = $("select[name=isTempSelect]").val()
    let memberTypeOption = $("#memberTypeSelect").val();
    let isStop = $("select[name=isStopSelect]").val();
    if(selectOption == "name") {
        name = $("#inputSelect").val();
    } else if(selectOption == "phone") {
        phone = $("#inputSelect").val();
    } else if(selectOption == "email") {
        email = $("#inputSelect").val();
    } else if(selectOption == "id") {
        id = $("#inputSelect").val();
    } else if(selectOption == "referralCode") {
        referralCode =  $("#inputSelect").val();
    }

    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        let params = {
            startDate: start,
            endDate: end,
            nameOption: name,
            phoneOption: phone,
            emailOption: email,
            idOption: id,
            referralCodeOption: referralCode,
            memberTypeOption: memberTypeOption,
            DTYPE: dtype,
            deleted: deleted,
            isTemp: isTemp,
            isStop: isStop
        };

        $("#datatable").DataTable().clear().destroy();
        $("#datatable").DataTable({
            searching: false,
            ordering: false,
            info: false,
            serverSide: true,
            ajax: {
                type: "get",
                url: "/bo/management/complaint/member/search",
                data: params,
                dataSrc: function(data) {
                    return data.data.data;
                }
            },
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
                {"data": "id"},
                {"data": 'dtype'},
                {
                    "data": "name",
                    "render": function (data, type, row) {
                        if (row.blacklistType) {
                            return data + "<span class='text-danger'> (BL)</span>"
                        } else {
                            return data
                        }
                    }
                },
                {"data": "loginId"},
                {"data": "email"},
                {"data": "phone"},
                {
                    "data": "isTemp",
                    "render": function (data, type, row) {
                        if (data == '1') {
                            return "Y"
                        } else {
                            return "N"
                        }
                    }
                },
                {
                    "data": "deleted",
                    "render": function (data, type, row) {
                        if (data == '1') {
                            return "Y"
                        } else {
                            return "N"
                        }
                    }
                },
                {
                    "data": "isStop",
                    "render": function (data, type, row) {
                        if (data == '1') {
                            return "Y"
                        } else {
                            return "N"
                        }
                    }
                },
                {
                    "data" : "id",
                    "render" : function(data, type, row){
                        return "<a style='font-weight: bold;' href='/bo/management/complaint/member/detail?id=" + row.id + "'>" + "조회" + "</a>";
                    }
                }
            ],
            columnDefs: [
                {
                    "targets": [9],
                }
            ]
        })
    }*/
}