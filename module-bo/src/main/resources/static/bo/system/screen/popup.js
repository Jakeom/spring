$("#datatable").DataTable({
    ajax: {
        url: "/bo/system/screen/popup/list",
    },
    language: {
	            emptyTable: "데이터가 없습니다.",
	            lengthMenu: "페이지당 _MENU_ 개씩 보기",
	            info: "현재 _START_ - _END_ / _TOTAL_건",
	            infoEmpty: "",
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
        {"data": "popupSeq"},
        {"data": "title"},
        {"data": "content"},
        {"data": "popupOrder"},
        {"data": "useFlag"},
        {"data": "popupStartDt"},
        {"data": "popupEndDt"},
        {"data": "linkUrl"},
        {"data": "offsetX"},
        {"data": "offsetY"},
        {"data": "createDt"},
        {
            render: function (data, type, row) {
                return "<a style='font-weight: bold;' href='/bo/system/screen/popup/detail?popupSeq=" + row.popupSeq + "'>" + "상세보기" + "</a>";
            }
        }
    ],
    columnDefs: [
		{
			"targets": [11],
		}
	]
});

$("button[data-role=form]").click(function () {
    window.location.href = "/bo/system/screen/popup/form";
})


