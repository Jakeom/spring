let memberTypeCd="AP";
let option="";
let subOption="";
modal(memberTypeCd);
selectChange(option)
displayTable();
hHDisplayTable();
loadTable();
function modal(memberTypeCd) {
	$.ajax({
		url: "/bo/system/homepage/faq/modal",
	    method: "get",
        data:{memberTypeCd: memberTypeCd},
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
		var tablenum = data.data;
	    var tr_table = '';
	    for (var i = 0; i < tablenum.length; i++) {
	    	tr_table = '<tr><td>'+tablenum[i].categoryNm
	        +'</td><td><button class="btn" id="deletebtn" name="'
	        +tablenum[i].faqCategorySeq+'">&times;</button></td></tr>'
	        $("#modaltable").append(tr_table);
	    }
	})
	.fail(function (xhr, status, errorThrown) {
		alert("*****ajax fail*****");
	})  
}


$("button[data-role=AP]").click(function () {
	memberTypeCd="AP";
	$( '#modaltable > tbody').empty();
	modal(memberTypeCd);
});

$("button[data-role=HH]").click(function () {
	memberTypeCd="HH";
	$( '#modaltable > tbody').empty();
	modal(memberTypeCd);
});

$(document).on('click', '#deletebtn',function(e) {
	if (!confirm("삭제하시겠습니까? 카테고리와 관련된 모든 FAQ가 삭제됩니다.")) {
        return false;
    } else {
        $.ajax({
            type: "post",
            url: "/bo/system/homepage/faq/modal/delete",
            data: {faqCategorySeq: $(this).attr('name')},
            success: function (data) {
                alert("삭제되었습니다.");
                $( '#modaltable > tbody').empty();
                modal(memberTypeCd);
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
        $("#datatable").DataTable().clear().destroy();
        loadTable();
      }
}); 


$("button[data-role=save]").click(function () {
	let categoryNm = $("input[name=categoryNm]").val();
	if (categoryNm == "") {
        alert("카테고리 이름을 입력해주세요.");
        $("input[name=categoryNm]").focus();
        return false;
    }
	let params = {
		categoryNm: categoryNm,
		memberTypeCd: memberTypeCd 
	}
	
	if (!confirm("등록하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/system/homepage/modal/insert",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function (data) {
                alert("등록되었습니다.");
                $( '#modaltable > tbody').empty();
                modal(memberTypeCd);
                $('#categoryNm').val('');
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    }
	
});
function loadTable() {
    let datatable = $("#datatable").DataTable({
        destroy: true,
        ordering: true,
        searching: false,
        info: false,
        ajax:{
            url:"/bo/system/homepage/faq/list",
            dataSrc: function(data){
                return data.data;
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
            {
                "data": "faqSeq",
                "render": function (data, type, row) {
                    if(row.memberTypeCd == 'HH') {
                        if(row.displayFlag) {
                            return '<input class="checkDisplay hh" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        } else {
                            return '<input class="hh" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        }
                    } else if(row.memberTypeCd == 'AP') {
                        if(row.displayFlag) {
                            return '<input class="checkDisplay ap" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        } else {
                            return '<input class="ap" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        }
                    } else {
                        return "-"
                    }
                }
            },
            {"data": "faqSeq"},
            {"data": "memberTypeCd"},
            {"data": "categoryNm"},
            {
                "data" : "title",
                "render" : function(data, type, row){
                    return "<a style='font-weight: bold;' href='/bo/system/homepage/faq/detail?faqSeq=" + row.faqSeq + "'>" + data + "</a>";
                }
            },
            {"data": "createDt"},
            {"data": "writer", "defaultContent": "-"},
            {"data": "updateDt", "defaultContent": "-"},
            {"data": "editor", "defaultContent": "-"},
        ],
        columnDefs: [
            {
                "targets": [8],
            }
        ]

    })
}

$("button[data-role=faqCreate]").click(function () {
    window.location.href = "/bo/system/homepage/faq/create";
});

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
    $('#faqStartDt').datepicker('setDate', start);
    $('#faqEndDt').datepicker('setDate', end);
})

$("button[data-role=week]").click(function() {
    const current = new Date();
    current.setDate(current.getDate() - 7);
    let lastWeek = toStringByFormatting(current, '-');
    start = lastWeek;
    end = toStringByFormatting(new Date(), '-');
    $('#faqStartDt').datepicker('setDate', start);
    $('#faqEndDt').datepicker('setDate', end);
})

$("button[data-role=month]").click(function() {
    const current = new Date();
    current.setMonth(current.getMonth() - 1);
    let lastMonth = toStringByFormatting(current, '-');
    start = lastMonth;
    end = toStringByFormatting(new Date(), '-');
    $('#faqStartDt').datepicker('setDate', start);
    $('#faqEndDt').datepicker('setDate', end);
})

$("button[data-role=all]").click(function() {
    start="";
    end=toStringByFormatting(new Date(), '-');
    $('#faqStartDt').datepicker('setDate', null);
    $('#faqEndDt').datepicker('setDate', null);
})

$('#faqStartDt').change(function() {
    start = $(this).val();
    $('button[name=colorChange]').removeClass('btn-primary');
    $('button[name=colorChange]').addClass('btn-secondary');
})

$('#faqEndDt').change(function() {
    end = $(this).val();
    $('button[name=colorChange]').removeClass('btn-primary');
    $('button[name=colorChange]').addClass('btn-secondary');
})

$("button[data-role=search]").click(function() {
    let params = {
        startDate: start,
        endDate: end,
        memberTypeCd: option,
        categoryNm: subOption
    };
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        $("#datatable").DataTable().clear().destroy();
        $("#datatable").DataTable({
            destroy: true,
            ordering: true,
            searching: false,
            info: false,
            ajax: {
                type: "post",
                url: "/bo/system/homepage/faq/search",
                data: params,
                dataSrc: function (data) {
                    return data.data;
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
                {
                    "data": "faqSeq",
                    "render": function (data, type, row) {
                        if(row.memberTypeCd == 'HH') {
                            if(row.displayFlag) {
                                return '<input class="checkDisplay hh" type="checkbox" value="' + data + '" data-role=checkSeq>';
                            } else {
                                return '<input class="hh" type="checkbox" value="' + data + '" data-role=checkSeq>';
                            }
                        } else if(row.memberTypeCd == 'AP') {
                            if(row.displayFlag) {
                                return '<input class="checkDisplay ap" type="checkbox" value="' + data + '" data-role=checkSeq>';
                            } else {
                                return '<input class="ap" type="checkbox" value="' + data + '" data-role=checkSeq>';
                            }
                        } else {
                            return "-"
                        }
                    }
                },
                {"data": "faqSeq"},
                {"data": "memberTypeCd"},
                {"data": "categoryNm"},
                {
                    "data": "title",
                    "render": function (data, type, row) {
                        return "<a style='font-weight: bold;' href='/bo/system/homepage/faq/detail?faqSeq=" + row.faqSeq + "'>" + data + "</a>";
                    }
                },
                {"data": "createDt"},
                {"data": "writer", "defaultContent": "-"},
                {"data": "updateDt", "defaultContent": "-"},
                {"data": "editor", "defaultContent": "-"},
            ],
            columnDefs: [
                {
                    "targets": [8],
                }
            ]
        })
    }
})
function selectChange(option) {
    $.ajax({
        url: "/bo/system/homepage/faq/modal",
        method: "get",
        data: {memberTypeCd: option},
        datatype: "data",
    })
        .done(function(data){
            var selectnum = data.data;
            for (var i = 0; i < selectnum.length; i++) {
                var selectBox = '<option name="resetOption" value="'+ selectnum[i].categoryNm + '">' + selectnum[i].categoryNm + '</option>';
                $("#subMemberTypeSelect").append(selectBox);
            }
        })
        .fail(function (xhr, status, errorThrown) {
            alert("*****ajax fail*****");
        })
}

function selectChangeDataTable() {
    let params = {
        startDate: start,
        endDate: end,
        memberTypeCd: option,
        categoryNm: subOption
    };

    $("#datatable").DataTable().clear().destroy();
    $("#datatable").DataTable({
        destroy: true,
        ordering: true,
        searching: false,
        info: false,
        ajax: {
            type: "post",
            url: "/bo/system/homepage/faq/search",
            data: params,
            dataSrc: function(data) {
                return data.data;
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
            {
                "data": "faqSeq",
                "render": function (data, type, row) {
                    if(row.memberTypeCd == 'HH') {
                        if(row.displayFlag) {
                            return '<input class="checkDisplay hh" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        } else {
                            return '<input class="hh" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        }
                    } else if(row.memberTypeCd == 'AP') {
                        if(row.displayFlag) {
                            return '<input class="checkDisplay ap" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        } else {
                            return '<input class="ap" type="checkbox" value="' + data + '" data-role=checkSeq>';
                        }
                    } else {
                        return "-"
                    }
                }
            },
            {"data": "faqSeq"},
            {"data": "memberTypeCd"},
            {"data": "categoryNm"},
            {
                "data" : "title",
                "render" : function(data, type, row){
                    return "<a style='font-weight: bold;' href='/bo/system/homepage/faq/detail?faqSeq=" + row.faqSeq + "'>" + data + "</a>";
                }
            },
            {"data": "createDt"},
            {"data": "writer", "defaultContent": "-"},
            {"data": "updateDt", "defaultContent": "-"},
            {"data": "editor", "defaultContent": "-"},

        ],
        columnDefs: [
            {
                "targets": [8],
            }
        ]
    })
}

$(document).on("change", "select[id='memberTypeSelect']", function(){
    subOption = ""
    option = $('#memberTypeSelect').val();
    $("option[name=resetOption]").remove();
    if(start > end) {
        alert("시작 날짜가 종료 날짜보다 이후에 있습니다.")
    } else {
        selectChange(option)
        selectChangeDataTable();
    }
});

$(document).on("change", "select[id='subMemberTypeSelect']", function(){
    subOption = $('#subMemberTypeSelect').val();
    selectChangeDataTable();
});

let btnId = 'all';
$('button[name=colorChange]').click(function() {
    if(btnId != null || btnId == ""){
        document.getElementById(btnId).classList.remove('btn-primary');
        document.getElementById(btnId).classList.add('btn-secondary');
    }
    $(this).removeClass('btn-secondary');
    $(this).addClass('btn-primary');
    btnId = $(this).attr('id');
});

$('button[data-role=saveDisplay]').click(function() {
    let APdisplayCount = 0;
    let HHdisplayCount = 0;
    let displayFlag = [];
    let j = 0;
    for(let i = 1; i < 6; i++) {
        if($('#displayTable' + i + ' td').length > 5) {
            APdisplayCount++;
        } else {
            displayFlag[j] = i
            j++;
        }
    }
    for(let i = 6; i < 11; i++) {
        if($('#HHdisplayTable' + i + ' td').length > 5) {
            HHdisplayCount++;
        } else {
            displayFlag[j] = i
            j++;
        }
    }
    var select_obj = [];
    let i = 0;
    let maindisplayCheck = 1;
    let apHasMainCnt = 0;
    let hhHasMainCnt = 0;
    $('input[data-role="checkSeq"]:checked').each(function (index) {
        select_obj[i] = $(this).val();
        i++;
        if($(this).hasClass("ap") === true) {
            apHasMainCnt++
        } else {
            hhHasMainCnt++
        }
        if($(this).hasClass("checkDisplay") === true) {
            alert("이미 등록된 항목입니다.")
            maindisplayCheck = 0;
            return false;
        }
    });
    let params = {
        "checkSeq": select_obj,
        "displayFlagCheck": displayFlag
    };
    if(!maindisplayCheck) {
        return false;
    }
    if(select_obj.length == 0) {
        alert("메인에 노출 등록할 공지사항을 체크해주세요")
        return false;
    }
    if(apHasMainCnt + APdisplayCount > 5) {
        alert("5개 이상 등록하실수 없습니다.")
        return false;
    }
    if(hhHasMainCnt + HHdisplayCount > 5) {
        alert("5개 이상 등록하실수 없습니다.")
        return false;
    }

    if(!confirm("메인으로 등록하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/system/homepage/faq/updatedisplayflag",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("등록되었습니다.");
                location.reload();
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }

})

function displayTable() {
    $.ajax({
        url: "/bo/system/homepage/faq/display",
        type: "get",
        contentType: "application/json",
        success: function (data) {
            let i;
            let displayObject = data.data
            let size = displayObject.length
            for(i=0; i < size; i++) {
                let editor
                if(displayObject[i].updateSeq) {
                    editor = displayObject[i].editor
                } else {
                    editor = ''
                }
                let displayAppender = '<td>' + displayObject[i].memberTypeCd + '</td><td>' + displayObject[i].categoryNm + '</td><td>' + displayObject[i].title + '</td><td>' + displayObject[i].updateDt + '</td><td>' + editor + '</td>'
                        + '<td><button type="button" name="' + displayObject[i].displayFlag + '" data-role="deleteDisplay">삭제</button></td>'
                $('#displayTable' + displayObject[i].displayFlag).append(displayAppender);
            }

        },
        error: function () {
            alert("메인 노출 테이블 불러오기 오류")
        }
    });
}



function hHDisplayTable() {
    $.ajax({
        url: "/bo/system/homepage/faq/display/hh",
        type: "get",
        contentType: "application/json",
        success: function (data) {
            let i =0;
            let displayObject = data.data
            let size = displayObject.length
            for(i=0; i < size; i++) {
                let editor
                if(displayObject[i].updateSeq) {
                    editor = displayObject[i].editor
                } else {
                    editor = ''
                }
                let displayAppender = '<td>' + displayObject[i].memberTypeCd + '</td><td>' + displayObject[i].categoryNm + '</td><td>' + displayObject[i].title + '</td><td>' + displayObject[i].updateDt + '</td><td>' + editor + '</td>'
                    + '<td><button type="button" name="' + displayObject[i].displayFlag + '" data-role="deleteDisplay">삭제</button></td>'
                $('#HHdisplayTable' + (displayObject[i].displayFlag)).append(displayAppender);
            }

        },
        error: function () {
            alert("메인 노출 테이블 불러오기 오류")
        }
    });
}

$(document).on("click", "button[data-role=deleteDisplay]", function ()   {
    let params = {
        "displayFlag": $(this).attr('name')
    };
    if(!confirm("메인에서 삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/system/homepage/faq/deleteDisplayflag",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
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