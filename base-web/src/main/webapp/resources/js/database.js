var columns;
var gridOptions;
var gridDatabase=null;
var currentPage=1;
var view=0;
$(function() {
	$("#database_view").hide();
	$("#process").bind("click", function() {
		$("#database_grid").empty();
		processList();
	});
	$("#processAll").bind("click", function() {
		$("#database_grid").empty();
		processListAll();
	});
	
});
function processListAll() {
	var sql = $("#querysql").val();
	if(sql==''){
		showMessageOfParent("查询不能为空!");
		setTimeout("hideMessageOfParent()",2000);
		return;
	}
	$.ajax({
		url : "databaseQuery.jhtml",
		type : "POST",
		data : {
			"sql" : sql,
			"currentPage" : 1,
			"pageSize" : 0 //0显示全部
		},
		success : function(result) {
			view=0;
			if("00"==result.code){
				columns = result.colums;
				$("#database_view").show();
				gridOptions = {};
				gridOptions.column = columns;
				gridDatabase = new HkrtFramework.Grid("#database_grid", null, null, gridOptions);
				gridDatabase.setColumns(columns);
				gridDatabase.setData(result.list);
				view = view + result.list.length;
				displayFooter(result.count);
				$("#next").hide();
			}else{
				$("#database_view").hide();
				showMessageOfParent("SQL查询有误,请检查!");
				$("#database_grid").html(result.list);
			}
		}
	});
	setTimeout("hideMessageOfParent()",2000);
}
//
function processList() {
	var sql = $("#querysql").val();
	if(sql==''){
		showMessageOfParent("查询不能为空!");
		setTimeout("hideMessageOfParent()",2000);
		return;
	}
	currentPage=1;
	view=0;
	$.ajax({
		url : "databaseQuery.jhtml",
		type : "POST",
		data : {
			"sql" : sql,
			"currentPage" : currentPage,
			"pageSize" : 50
		},
		success : function(result) {
			if("00"==result.code){
				columns = result.colums;
				$("#database_view").show();
				gridOptions = {};
				gridOptions.column = columns;
				gridDatabase = new HkrtFramework.Grid("#database_grid", null, null, gridOptions);
				gridDatabase.setColumns(columns);
				gridDatabase.setData(result.list);
				view = view + result.list.length;
				displayFooter(result.count);
				if(result.count == result.list.length){
					$("#next").hide();
				}else{
					$("#next").show();
				}
			}else{
				$("#database_view").hide();
				showMessageOfParent("SQL查询有误,请检查!");
				$("#database_grid").html(result.list);
			}
		}
	});
	setTimeout("hideMessageOfParent()",2000);
}

function displayFooter(total){
	$("#view").empty();
	$("#total").empty();
	$("#view").html("显示 " + view +" 行;");
	$("#total").html("总共 " + total +" 行;");
}

function nextView() {
	currentPage = currentPage + 1;
	var sql = $("#querysql").val();
	if(sql==''){
		showMessageOfParent("查询不能为空!");
		setTimeout("hideMessageOfParent()",2000);
		return;
	}
	$.ajax({
		url : "databaseQuery.jhtml",
		type : "POST",
		data : {
			"sql" : sql,
			"currentPage" : currentPage,
			"pageSize" : 50
		},
		success : function(result) {
			if("00"==result.code){
				$("#database_view").show();
				view = view + result.list.length;
				gridDatabase.addData(result.list);
				displayFooter(result.count);
			}else{
				$("#database_view").hide();
				showMessageOfParent("SQL查询有误,请检查!");
				$("#database_grid").html(result.list);
			}
		}
	});
	setTimeout("hideMessageOfParent()",2000);
}