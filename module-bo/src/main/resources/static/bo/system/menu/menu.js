let deleteMenuMasterList = [];

$.ajax({
    type: 'GET',
    url: '/bo/system/menu/menu-list',
    success: function (data) {
        let company = [];
        $.each(data.data, function (idx, item) {
            company[idx] = {
                "id": item.menuCd,
                "text": item.menuNm,
                parent: item.upperMenuCd,
                data: {
                    "menuCd": item.menuCd,
                    "menuNm": item.menuNm,
                    "upperMenuCd": item.upperMenuCd,
                    "menuUrl": item.menuUrl,
                    "menuOpenTypeCd": item.menuOpenTypeCd,
                    "menuOrder": item.menuOrder,
                    "menuLevel": item.menuLevel,
                    "accessAbleStartDt": item.accessAbleStartDt,
                    "accessAbleEndDt": item.accessAbleEndDt,
                    "blockStartDt": item.blockStartDt,
                    "blockEndDt": item.blockEndDt,
                    "useFlag": item.useFlag,
                    "sql": "U"
                }
            }
        });

        console.log(company);

        $(".menu_tree").jstree({
            core: {
                "force_text": true,
                "animation": 200,
                "check_callback": function (op, node, par, pos, more) {
                    if (op == "move_node") {
                        return par.id == node.parent;
                    }
                    return true;
                },
                data: company    //데이터 연결
            },
            types: {
                "#": {
                    "max_children": 1,
                    "max_depth": 4,
                    "valid_children": ["root"]
                },
                "root": {
                    "icon": "glyphicon glyphicon-home text-muted",
                    "valid_children": ["default"]
                },
                "default": {
                    "icon": "glyphicon glyphicon-folder-open text-muted",
                    "valid_children": ["default"]
                }
            },
            plugins: ["dnd", "search", "types", "wholerow"]
        })
            .bind('loaded.jstree', function (event, data) {
                //트리 로딩 롼료 이벤트
            })
            .bind('select_node.jstree', function (event, data) {
                //노드 선택 이벤트
            }).on('ready.jstree', function () {
            $(this).jstree(true).open_all();
        }).on('move_node.jstree', function (event, data) {
            if (data.parent != "1") {
                data.node.data.upperMenuCd = data.parent;
            }
            data.node.data.menuLevel = data.instance.get_path(data.node.id).length - 1;
        }).on('select_node.jstree', function (event, data) {
            var frm = $("form[name=menuInfoFrm]");
            if (data.node.data) {
                frm.find("input[name=upperMenuCd]").val(data.node.data.upperMenuCd);
                frm.find("input[name=menuCd]").val(data.node.data.menuCd);
                var url = data.node.data.menuUrl;
                //url = url.replace(/&amp;/g, "&");
                frm.find("input[name=menuUrl]").val(url);
                frm.find("input[name=accessAbleStartDt]").val(data.node.data.accessAbleStartDt)
                frm.find("input[name=accessAbleEndDt]").val(data.node.data.accessAbleEndDt)
                frm.find("input[name=blockStartDt]").val(data.node.data.blockStartDt)
                frm.find("input[name=blockEndDt]").val(data.node.data.blockEndDt)
                frm.find("select[name=useYn]").val(data.node.data.useYn);

            } else frm[0].reset();

        }).on('create_node.jstree', function (event, data) {
            var parent = data.parent == "1" ? "" : data.parent;
            if (data.parent != "1") {
                var parentNode = data.instance.get_node(data.parent);
                if (!parentNode.data.menuCd) {
                    alert('새로 만든 메뉴에 하위 메뉴를 적용 할 수 없습니다.<br/>저장 후 생성 하시기 바랍니다.');
                    data.instance.delete_node(data.node);
                    return;
                }
            }
            var dom = data.instance.get_node(data.node, true);
            data.node.data = {
                upperMenuCd: parent,
                menuLevel: data.instance.get_path(data.node).length - 1,
                menuOrder: dom.index() + 1
            };
        }).on('rename_node.jstree', function (event, data) {
            data.node.data.menuNm = data.text;
        });

    },
    error: function (data) {
        alert("에러");
    }
});

$(document).ready(function () {
    // 전체 펼치기
    $("button[data-role=treeOpen]").click(function () {
        $("div.menu_tree").jstree(true).open_all();
        return false;
    });
// 전체 접기
    $("button[data-role=treeClose]").click(function () {
        $("div.menu_tree").jstree(true).close_all();
        return false;
    });

    $("button[data-type=apply]").click(function () {
        var ref = $(".menu_tree").jstree(true), sel = ref.get_selected(), node = ref.get_node(sel),
            frm = $("form[name=menuInfoFrm]");
        node.data.menuCd = frm.find("input[name=menuCd]").val();
        node.data.menuUrl = frm.find("input[name=menuUrl]").val();
        node.data.menuOpenTypeCd = frm.find("select[name=menuOpenTypeCd]").val();
        node.data.useFlag = frm.find("select[name=useFlag]").val();
        node.data.accessAbleStartDt = frm.find("input[name=accessAbleStartDt]").val();
        node.data.accessAbleEndDt = frm.find("input[name=accessAbleEndDt]").val();
        node.data.blockStartDt = frm.find("input[name=blockStartDt]").val();
        node.data.blockEndDt = frm.find("input[name=blockEndDt]").val();
        node.data.sql = 'U';
        alert('적용되었습니다.<br/>변경사항은 저장을 하셔야 반영됩니다.');
        $("#" + sel[0]).trigger("click");
    });

    $("button[data-type=add]").click(function () {
        var ref = $(".menu_tree").jstree(true), sel = ref.get_selected(), level = ref.get_path(sel).length;
        if (!sel.length) {
            alert("대상을 선택 해주세요.");
            return false;
        }
        sel = sel[0];
        if (level > 3) {
            alert("최대 3개의 Depth 까지 구성이 가능합니다.");
            return false;
        }
        sel = ref.create_node(sel, {"type": "default"});
        ref.edit(sel);
        return false;
    });

    $("button[data-type=modify]").click(function () {
        var ref = $(".menu_tree").jstree(true), sel = ref.get_selected();
        if (!sel.length) {
            alert("수정할 대상을 선택 해주세요.");
            return false;
        }
        sel = sel[0];
        ref.edit(sel);
        return false;
    });

    $("button[data-type=delete]").click(function () {
        var ref = $(".menu_tree").jstree(true), sel = ref.get_selected(), level = ref.get_path(sel).length;
        if (!sel.length) {
            alert("삭제할 대상을 선택 해주세요.");
            return false;
        }
        if (level < 3) {
            alert("1 Depth 는 삭제가 불가능합니다.");
            return false;
        }
        sel = sel[0];
        ref.delete_node(sel);
        deleteMenuMasterList.push(sel);
        return false;
    });

    $("input[name=searchText]").keyup(function (e) {
        if (e.keyCode == 13) $("button[data-type=search]").trigger("click");
        return false;
    });
    $("button[data-type=search]").click(function () {
        var v = $("input[name=searchText]").val();
        $(".menu_tree").jstree(true).search(v);
    });

    $("button[data-type=send]").click(function () {
        var ref = $(".menu_tree").jstree(true);
        ref.open_all();
        var jsonData = ref.get_json('#', {no_state: true, flat: true});
        jsonData.splice(0, 1);
        var resultData = [], deleteResultData = [];
        for (var d in jsonData) {
            var dom = ref.get_node(jsonData[d].id, true);
            jsonData[d].data.menuOrder = dom.index() + 1;
            if (jsonData[d].id.includes("j1")) {
                jsonData[d].data.sql = 'I';
            }
            resultData.push(jsonData[d].data);
        }
        for (var i in deleteMenuMasterList) {
            deleteResultData.push({"menuCd": deleteMenuMasterList[i]});
        }
        if (confirm('저장하시겠습니까?')) {
            $.ajax({
                url: "/bo/system/menu/insert-menu",
                type: "POST",
                data: JSON.stringify({"menuMasterList": resultData, "deleteMenuMasterList": deleteResultData}),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if(data.status==200){
                        alert(data.message)
                        location.reload();
                    }

                }
            })
        }
    });

    $("button[data-type=manage]").click(function () {
        var ref = $(".menu_tree").jstree(true), sel = ref.get_selected();
        if (!sel.length) {
            toastr.info("대상을 선택 해주세요.");
            return false;
        }

        alert(JSON.stringify(sel));
    });

    $("#manageModal").on("shown.bs.modal", function (e) {
        var modal = $(this);
        var button = $(e.relatedTarget);

        var ref = $(".menu_tree").jstree(true), sel = ref.get_selected(true);
        if (!sel.length) {
            alert("대상을 선택 해주세요.");
            modal.modal('hide');
            return false;
        }
        modal.find("small.subTitle").text(sel[0].text);

        if (sel[0].id.includes("j1")) {
            alert("메뉴 저장 후 사용하시기 바랍니다.")
            modal.modal('hide');
        } else {
            $(".modal a[data-toggle=tab]").first().trigger('shown.bs.tab');
            if (!$(".modal a[data-toggle=tab]").first().hasClass('active')) {
                $(".modal a[data-toggle=tab]").first().trigger('click');
            }
        }
    });

    $("button[data-role=programAdd]").click(function () {
        var ref = $(".menu_tree").jstree(true), sel = ref.get_selected();
        $.ajax({
            url: "/bo/system/menu/program",
            type: "post",
            data: {
                menuCd: sel[0],
                programNm: $("#programNm").val(),
                programUrl: $("#programUrl").val(),
                programAttrCd: $("#programAttrCd").val()
            },
            dataType: 'json',
            success: function (data) {
                alert(data.message)
                $(".modal a[data-toggle=tab]").first().tab('show');
            }
        });
        return false;
    });

    $(".modal a[data-toggle=tab]").on('shown.bs.tab', function (e) {
        //e.target // newly activated tab
        //e.relatedTarget // previous active tab
        if ($(this).attr("aria-controls") == "menu-master-list") {
            var ref = $(".menu_tree").jstree(true), sel = ref.get_selected();
            $.ajax({
                url: "/bo/system/menu/program",
                data: {"menuCd": sel[0]},
                success: function (data) {
                    var tbody = $(".modal table tbody");
                    var trs = [];
                    for (var i in data) {
                        var tds = [], d = data[i];
                        var updDttm = '';
                        if (d.updDttm) updDttm = moment(d.updDttm, 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm:ss');
                        tds.push('<td>' + d.programUrl);
                        tds.push(d.programNm);
                        tds.push(d.programAttrCd);
                        tds.push(d.updateDt);
                        tds.push(d.createDt);
                        // tds.push(updDttm);
                        //tds.push(moment(d.regDttm, 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm:ss'));
                        tds.push('<button type="button" class="btn btn-danger" data-menu-cd="' + d.menuCd + '" data-program-cd="' + d.programCd + '"><span class="glyphicon glyphicon-trash"></span></button></td>');
                        trs.push(tds.join('</td><td class="text-center">'));
                    }
                    tbody.empty().append('<tr>' + trs.join('</tr><tr>') + '</tr>');
                    tbody.find('button').click(function () {
                        // ToDo : 삭제
                        var d = $(this).data();
                        if (confirm('프로그램을 제거 하시겠습니까?')) {
                            $.ajax({
                                url: "/bo/system/menu/delete-program?programCd=" + d.programCd,
                                type: "post",
                                success: function (data) {
                                    alert(data.message)
                                    $(".modal a[data-toggle=tab]").first().trigger('shown.bs.tab');
                                }
                            })
                        }
                        return false;
                    });
                }
            });
        } else {
            $(".modal form")[0].reset();
            //$(".modal form select").selectpicker('refresh');
        }
    });
});

$(document).on('focus', '.form-datepicker', function () {
    $(this).datepicker({
        defaultDate: +7,
        changeMonth: true,
        changeYear: true,
        monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
        showMonthAfterYear: true,
        showOtherMonths: true,
        dateFormat: "yy-mm-dd",
        gotoCurrent: true,
        beforeShow: function (input, inst) {
            $('#ui-datepicker-div').addClass('datepicker-box');
        },
    });
});

