let searchParams
$("button[data-role=search]").click(function() {
    let nullCheck = 0;
    let idCheck = 0;
    if($("#inputName").val()) {
        nullCheck++;
    }
    if($("#inputEmail").val()) {
        nullCheck++;
    }
    if($("#inputPhone").val()) {
        nullCheck++;
    }
    if($("#inputId").val()) {
        idCheck = 1;
        nullCheck++;
    }
    let name = $("#inputName").val();
    let phone = $("#inputPhone").val();
    let email = $("#inputEmail").val();
    let id = $("#inputId").val();
    searchParams = {
        nameOption: name,
        phoneOption: phone,
        emailOption: email,
        idOption: id,
    };
    if(nullCheck >= 2) {
        if(nullCheck == 3) {
            if(idCheck == 0) {
                searchMemberCnt(searchParams, phone, name, email)
            } else {
                searchTable()
            }
        } else {
            searchTable()
        }
    } else {
        alert("검색조건을 정확히 입력해주세요.")
    }
})

$("input[data-role=enter]").keyup(function(e) {
    if (e.keyCode === 13) {
        document.getElementById('search').click();
    }
});

$(document).on('click', 'button[data-role=blacklistSave]', function () {
    var select_obj = [];
    let i = 0;
    let params
    $('input[data-role="check"]:checked').each(function (index) {
        select_obj[i] = $(this).val();
        i++;
    });
    if(select_obj.length == 1) {
        if(select_obj[0] == 'GUEST') {
            params = {
                name: $("p[name=blackName]").text(),
                phone: $("p[name=blackPhone]").text(),
                email: $("p[name=blackEmail]").text(),
                blacklistType: 'GUEST'
            }
        } else {
            params = {
                "checkSeq": select_obj,
                "blacklistType": 'MEMBER'
            };
        }
    } else {
        params = {
            "checkSeq": select_obj,
            "blacklistType": 'MEMBER'
        };
    }
    if(select_obj.length == 0) {
        alert("등록할 블랙리스트를 체크해주세요.")
        return false;
    }
    if(!confirm("블랙리스트로 등록하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            url: "/bo/management/complaint/blacklist/form/insert",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(params),
            success: function () {
                alert("등록되었습니다.");
            },
            error: function () {
                alert("ajax fail")
            }
        });
    }
})

function searchTable() {
    $("#datatable").DataTable().clear().destroy();
    $("#datatable").DataTable({
        searching: false,
        ordering: false,
        info: false,
        ajax: {
            type: "get",
            url: "/bo/management/complaint/blacklist/form/search",
            data: searchParams,
            dataSrc: function(data) {
                return data.data;
            }
        },
        columns: [
            {
                "data" : "id",
                "render" : function(data){
                    return "<input type='checkbox' data-role='check' value='" + data + "'>";
                }
            },
            {"data": "id"},
            {"data": "name"},
            {"data": "phone"},
            {"data": "email"}
        ],
        columnDefs: [
            {
                "targets": [4],
            }
        ]
    })
}

$("button[data-role=blacklist]").click(function () {
    window.location.href = "/bo/management/complaint/blacklist"
});


$(document).on("click", "#allCheck", function(){
    if($("#allCheck").is(":checked")) $("input[data-role=check]").prop("checked", true);
    else $("input[data-role=check]").prop("checked", false);
});

$(document).on("click", "input[data-role='check']", function(){
    var total = $("input[data-role=check]").length;
    var checked = $("input[data-role=check]:checked").length;

    if(total != checked) $("#allCheck").prop("checked", false);
    else $("#allCheck").prop("checked", true);
});

function searchMemberCnt(params, phone, name, email) {

    $.ajax({
        url: "/bo/management/complaint/blacklist/form/member/cnt",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (data) {
            if(data.data == 0) {
                $("#datatable > tbody").remove()
                $("#datatable_paginate").remove()
                let appendNonMember = "<tbody><tr><td><input type='checkbox' data-role='check' value='GUEST'></td><td>비회원</td><td><p name='blackName'>" + name + "</p></td><td><p name='blackPhone'>" + phone + "</p></td><td><p name='blackEmail'>" + email + "</p></td></tr></tbody>"
                $("#datatable").append(appendNonMember)
            } else {
                searchTable()
            }
        },
        error: function () {
            alert("ajax fail")
        }
    });
}