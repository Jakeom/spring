$("#datatable").DataTable({
	ajax: {
		url: "/bo/system/screen/banner/list",
		dataSrc: ''
	},
	columns: [
		{ "data": "bannerSeq"},
		{ "data": "bannerTypeCd"},
		{ "data": "bannerNm"},
		{ "data": "bannerOrder"},
		{ "data": "useFlag"},
		{ "data": "bannerStartDt"},
		{ "data": "bannerEndDt"},
		{ "data": "linkUrl"},
		{ "data": "createNo"},
		{ "data": "createDt"},
		{ "data": "updateNo"},
		{ "data": "updateDt"},
		{
			render: function(data, type, row) {
				return "<a style='font-weight: bold;' href='/bo/system/screen/banner/detail?bannerSeq=" + row.bannerSeq + "'>" + "상세보기" + "</a>";
			}
		}
	],
	columnDefs: [
		{
			"targets": [12],
		}
	]
});
/** [배너등록] 버튼 클릭시 */
$("button[data-role=form]").click(function () {
    window.location.href = "/bo/system/screen/banner/form";
})



