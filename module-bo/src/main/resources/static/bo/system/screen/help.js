/** 등록 */
$("button[data-role=form]").click(function() {
	location.href = "/bo/system/screen/help/form";
});

function setDataTable(data){
//	 let table =	$("#datatable").DataTable({
//		ajax: {
//			url: "/bo/system/help/list",
//			dataSrc : "",
//		},
//		columns : [
//			{"data": "menuCd"},
//			{"data": "content"},
//			{
//				"data" : "menuCd",
//				"render" : function(data, type, row){
//					return "<a style='font-weight: bold;' href='/bo/system/help/detail?menuCd=" + row.menuCd + "'>" + "상세보기" + "</a>";
//				}
//			}
//		],
//		columnDefs: [
//	        {
//	            "targets": [2],
//	        }
//	    ]
//	});
	
	var tag = "";
	data.forEach(function(a,b){
		tag += "<tr><td>" + a.menuCd + "</td><td>"+a.content + "</td></tr>";
	});
	$("#datatable tbody").html(tag);
}
 


/** 버튼 클릭시 */
$("tr[data-role=detailList]").click(function(){
	let menuCd =$(this).data("menuCd");
	// alert("test  :" + menuCd)
	$.ajax({ 
	    type:"get",
	    url:"/bo/system/screen/help/list", 
		success: function(data) {
			setDataTable(data.data);
		}
	});
});
