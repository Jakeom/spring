$("#datatable").DataTable({
    ajax: {
        url: "/bo/system/org/admin/list",
        dataSrc: function(data){
            return data.data;
        }
    },
    columns: [
        {"data": "adminSeq"},
        {"data": "orgId"},
        {"data": "adminId"},
        {"data": "adminNm"},
        {"data": "email"},
        {"data": "adminStatusNm"},
        {
            "data": "useStartDt", "data": "useEndDt",
            "render": function (data, type, row) {
                return row.useStartDt + '~' + row.useEndDt;
            }
        },
        {"data": "createDt"},
        {
            render: function (data, type, row) {
                return "<a style='font-weight: bold;' href='/bo/system/org/admin/detail?adminSeq=" + row.adminSeq + "'>" + "상세보기" + "</a>";
            }
        }
    ],
    columnDefs: [
        {
            "targets": [7],
           }
     ]
});

/* [관리자등록] 버튼 클릭 시 */
$("button[data-role=form]").click(function() {
   window.location.href = "/bo/system/org/admin/form"
});