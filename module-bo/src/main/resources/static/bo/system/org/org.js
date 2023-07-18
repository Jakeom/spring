$("#datatable").DataTable({
	ajax:{
		url:"/bo/system/org/org/list",
		dataSrc: function(data){
            return data.data;
        }
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
		{"data": "orgId"},
        {"data": "orgNm"},
        {
			render: function(data, type, row) {
				if(row.useFlag == 'Y'){
					return '사용';
				}else if(row.useFlag == 'N'){
					return '미사용';
				}
			}
		},
        { "data": "writer" , "defaultContent" : "-"},
        {"data": "createDt"},
        { "data": "corrector" , "defaultContent" : "-"},
        {"data": "updateDt"},
        {
			"data" : "orgId",
			"render" : function(row){
				return "<a style='font-weight: bold;' href='/bo/system/org/org/detail?orgId=" + row + "'>" + "상세보기" + "</a>";
			}
		}
    ],
    columnDefs: [
        {
            "targets": [7],
        }
    ]
})
/** [조직등록] 버튼 클릭시 */
$("button[data-role=form]").click(function () {
    window.location.href = "/bo/system/org/org/form";
})