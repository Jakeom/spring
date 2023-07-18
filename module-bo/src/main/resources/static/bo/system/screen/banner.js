$("#datatable").DataTable({
	ordering: false,
	ajax: {
		url: "/bo/system/screen/banner/list",
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
		{ "data": "bannerSeq"},
		{ "data" : "bannerType" , "defaultContent" : "-"},
		{ "data": "bannerNm"},
		{ "data": "bannerOrder"},
		{ "data": "useFlag"},
		{ "data": "bannerStartDt"},
		{ "data": "bannerEndDt"},
		{ "data": "linkUrl" , "defaultContent" : "-" },
		{ "data": "corrector" , "defaultContent" : "-"},
		{ "data": "updateDt"  , "defaultContent" : "-"},
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/system/screen/banner/detail?bannerSeq=" + row.bannerSeq + "'>" + "상세보기" + "</a>";
			}
		}
	],
	columnDefs: [
		{
			"targets": [10],
		}
	]
});
/** [배너등록] 버튼 클릭시 */
$("button[data-role=form]").click(function () {
    window.location.href = "/bo/system/screen/banner/form";
})