var busheetGrid;
var totalPage;
var lookupArray=[];
var busheetId;
function BusheetManage() {
	showMessageOfParent("正在载入...");
	var beginPage =1;
	  var data = [];
	  var columns = [];
	  var checkboxSelector = new Slick.CheckboxSelectColumn({
		    cssClass: "slick-cell-checkboxsel"
		  });
	  
	  columns.push(checkboxSelector.getColumnDefinition());
	  columns.push(
	    {id: "busheetCode", name: "单据编码", field: "busheetCode", width: 105,},
	    {id: "busheetName", name: "单据名称", field: "busheetName", width: 105, editor: Slick.Editors.Text},
	    {id: "csvTemplet", name: "csv模板", field: "csvTemplet", width: 180, editor: Slick.Editors.LongText},
	    {id: "xlsTemplet", name: "xls模板", field: "xlsTemplet", width: 320, editor: Slick.Editors.InputFile},
	    {id: "tableName", name: "主表别名", field: "tableName", width: 105, editor: Slick.Editors.Text}
	  );
	  var options = {
			    editable: true,
			    enableAddRow: true,
			    enableCellNavigation: true,
			    asyncEditorLoading: false,
			    autoEdit: false,
			    showTopPanel: true,
			    callback:getBusheetListForPage,
  			    showPagePanel:true,
			    topPanelButtonAttr:[
			      			       {permission:"BusheetManager:save",name:"saveClick",onclick:"saveBusheet()",title:"保存",alt:"save",src:"resources/images/save.png",className:"hover-icon-save"},
			      			       {permission:"BusheetManager:remove",name:"removeClick",onclick:"removeBusheet()",title:"删除",alt:"cancel",src:"resources/images/delete.png",className:"hover-icon-delete"}
			      			       ]
		        
			  };
	  
		  $.ajax({
			  url: contextPath +"/getBusheetListForPage.jhtml?r="+Math.random(),
			  async:false,
			  type:"POST",
			  data:{"currentPage":beginPage},
			  success: function(result) {
				     totalPage = result.pages;
			$.each(result.list, function(i,v) {
				var d = (data[i] = {});
			  d["id"] = v.id;
			  d["busheetName"] = v.busheetName;
			  d["busheetCode"] = v.busheetCode;
			  d["csvTemplet"] = v.csvTemplet;
			  d["xlsTemplet"] = v.xlsTemplet;
			  d["tableName"] = v.tableName;
			});
		  var toolbarModel = new Slick.Plugins.ToolbarModel({queryText:"单据名称",action:"selectBusheetList()"});
		  busheetGrid = new Slick.Grid("#busheet_grid", data, columns, options);
		  busheetGrid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow:true}));
		  busheetGrid.registerPlugin(checkboxSelector);
		  busheetGrid.setTotalPages(totalPage);
		  busheetGrid.setTotalCount(result.count);
		  busheetGrid.registerPlugin(toolbarModel); 
		  
		  
		  busheetGrid.onAddNewRow.subscribe(function (e, args) {
		    var item = args.item;
		    item.bitFlag=0;
		    busheetGrid.invalidateRow(data.length);
		    data.push(item);
		    busheetGrid.updateRowCount();
		    busheetGrid.render();
		  });
		  
		  busheetGrid.onClick.subscribe(function (e) {
				var cells = busheetGrid.getCellFromEvent(e);
				var row = busheetGrid.getSelectedRows();
				if(cells.row==row[0]){
					return;
				}
				if(busheetGrid.getData()[cells.row] == undefined){
					return;
				}
				 busheetId = busheetGrid.getData()[cells.row].id;
				selectpermissionList(busheetId);
				getBusheetWorkflowListForPage(busheetId);
			});
		  
		  hideMessageOfParent();
			  }
	 });
	 $("#1_tab,#2_tab").click(function(){
		$(this).addClass("nowtag").siblings().removeClass("nowtag");
		var id = $(this).attr("id").replace("_tab","_container");
		$("#"+id).show().siblings().hide();
	 });
}

function selectBusheetList(){
	$("#busheet_grid_pageUIId_currentPageId").text("1");
	getBusheetListForPage();
}
function getBusheetListForPage() {
	var beginPage = busheetGrid.getCurrentPage();
	var name=$("#text_busheet_grid").val();
	var data = [];
	$.ajax({
		  url: contextPath +"/getBusheetListForPage.jhtml?r="+Math.random(),
		  async:false,
		  type:"POST",
		  data:{"name":name,"currentPage":beginPage},
		  success: function(result) {
			  totalPage = result.pages;
			  $.each(result.list, function(i,v) {
					var d = (data[i] = {});
					  d["id"] = v.id;
					  d["busheetName"] = v.busheetName;
					  d["busheetCode"] = v.busheetCode;
					  d["csvTemplet"] = v.csvTemplet;
					  d["xlsTemplet"] = v.xlsTemplet;
					  d["tableName"] = v.tableName;
				});
			  busheetGrid.setTotalCount(result.count);
		  }
		});
	busheetGrid.setTotalPages(totalPage);
	busheetGrid.onAddNewRow.subscribe(function (e, args) {
		var item = args.item;
		item.bitFlag=0;
		busheetGrid.invalidateRow(data.length);
		data.push(item);
		busheetGrid.updateRowCount();
		busheetGrid.render();
	});
	busheetGrid.setData(data,true);	
	busheetGrid.render();
}

//保存按钮
function saveBusheet() {
	busheetGrid.getEditorLock().commitCurrentEdit();
	var rowNums = busheetGrid.getSelectedRows();
	if (rowNums.length != 0) {
		for ( var i = 0; i < rowNums.length; i++){
			var row = rowNums[i];
			var tempData = busheetGrid.getData();
			var params = tempData[row];
			$.post(contextPath + "/saveBusheet.jhtml", params, function(data) {
				if (data > 0) {
					showMessageOfParent('保存成功 ！');
				} else {
					showMessageOfParent('保存失败 ！');
				}
				setTimeout("hideMessageOfParent()", 2000);
				getBusheetListForPage();
				//BusheetWorkflowManage();
			});
		}
	} else {
		alert("请勾选至少一条需要保存的数据!");
		return;
	}
};


//删除
function removeBusheet() {
	var delRowNums = busheetGrid.getSelectedRows();
    if(delRowNums.length!=0){
    	 if (confirm("确定要删除所选记录吗？")) {
			 for(var i= 0;i<delRowNums.length;i++){
				 var params = validateRemove(busheetGrid,delRowNums,i);
				 if(params==false) continue;
				 $.post(contextPath + "/removeBusheet.jhtml", params,function(data) {
					 if(data==-1){
						 showMessageOfParent('请先删除该单据下的所有子单据 ！');
					 }else{
						 if(data>0){
							 showMessageOfParent('删除成功 ！');	
							 getBusheetListForPage();
						 }else{
							 showMessageOfParent('删除失败 ！');
						 }
					 }
					 setTimeout("hideMessageOfParent()", 2000);
				 });
			 }
		 }
    }else{
    	alert("请勾选至少一条需要删除的数据!");
    }
}



function getUserByState(){
	lookupArray=[];
	$.ajax({
		  url: contextPath +'/getUserByState.jhtml?r='+Math.random(),
		  async:false,
		  type:"POST",
		  success: function(result) {
			  $.each(result, function(k, v) {
				  v.name=v.loginName;
				  lookupArray.push(v);
			  });
		  }
		});
	return lookupArray;
};

var permissionWorkflowGrid;
var permissiontotalPageWorkflow;

function permissionManage() {
	showMessageOfParent("正在载入...");
	var beginPage =1;
	  var data = [];
	  var columns = [];
	  var checkboxSelector = new Slick.CheckboxSelectColumn({
		    cssClass: "slick-cell-checkboxsel"
		  });
	  
	  columns.push(checkboxSelector.getColumnDefinition());
	  columns.push(
	   // {id: "userId", name: "用户", field: "userId", width: 130, editor: Slick.Editors.Text,maxLength:3},
	    {id: "userId", name: "用户", lookupArray:getUserByState(), field: "userId", width: 160,formatter: Slick.Formatters.Lookup, editor: Slick.Editors.LookupSelect},
	    {id: "departmentCode", name: "组织机构代码", field: "departmentCode", width: 340, editor: Slick.Editors.Text}
	  );
	  var options = {
			    editable: true,
			    enableAddRow: true,
			    enableCellNavigation: true,
			    asyncEditorLoading: false,
			    autoEdit: false,
			    showTopPanel: true,
			    callback:getpermissionListForPage,
  			    showPagePanel:true,
			    topPanelButtonAttr:[
			      			       {permission:"BusheetPermissionManager:save",name:"saveClick",onclick:"saveBusheetPermission()",title:"保存",alt:"save",src:"resources/images/save.png",className:"hover-icon-save"},
			      			       {permission:"BusheetPermissionManager:remove",name:"removeClick",onclick:"removeBusheetPermission()",title:"删除",alt:"cancel",src:"resources/images/delete.png",className:"hover-icon-delete"}
			      			       ]
		        
			  };
	  
		  $.ajax({
			  url: contextPath +"/getBusheetPermissionListForPage.jhtml?r="+Math.random(),
			  async:false,
			  type:"POST",
			  data:{"currentPage":beginPage},
			  success: function(result) {
				  permissiontotalPageWorkflow = result.pages;
			$.each(result.list, function(i,v) {
				var d = (data[i] = {});
			  d["id"] = v.id;
			  d["busheetId"] = v.busheetId;
			  d["userId"] = v.userId;
			  d["departmentCode"] = v.departmentCode;
			});
		  var toolbarModel = new Slick.Plugins.ToolbarModel({queryText:"组织机构代码",action:"selectpermissionList()"});
		  permissionWorkflowGrid = new Slick.Grid("#busheet_permission_grid", data, columns, options);
		  permissionWorkflowGrid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow:false}));
		  permissionWorkflowGrid.registerPlugin(checkboxSelector);
		  permissionWorkflowGrid.setTotalPages(permissiontotalPageWorkflow);
		  permissionWorkflowGrid.setTotalCount(result.count);
		  permissionWorkflowGrid.registerPlugin(toolbarModel); 
		  
		  
		  permissionWorkflowGrid.onAddNewRow.subscribe(function (e, args) {
		    var item = args.item;
		    item.bitFlag=0;
		    permissionWorkflowGrid.invalidateRow(data.length);
		    data.push(item);
		    permissionWorkflowGrid.updateRowCount();
		    permissionWorkflowGrid.render();
		  });
		  hideMessageOfParent();
			  }
	 });
};
function selectpermissionList(){
	$("#busheet_permission_grid_pageUIId_currentPageId").text("1");
	getpermissionListForPage();
};

function getpermissionListForPage() {
	$("#busheet_permission_gridDiv").show();
	showMessageOfParent("正在载入...");
	var beginPage = permissionWorkflowGrid.getCurrentPage();
	var name=$("#text_busheet_permission_grid").val();
	var data = [];
	$.ajax({
		  url: contextPath +"/getBusheetPermissionListForPage.jhtml?r="+Math.random(),
		  async:false,
		  type:"POST",
		  data:{"currentPage":beginPage,"busheetId":busheetId,"departmentCode":name},
		  success: function(result) {
			  totalPageWorkflow = result.pages;
			  $.each(result.list, function(i,v) {
					var d = (data[i] = {});
					 d["id"] = v.id;
					  d["busheetId"] = v.busheetId;
					  d["userId"] = v.userId;
					  d["departmentCode"] = v.departmentCode;
				});
			  permissionWorkflowGrid.setTotalCount(result.count);
		  }
		});
	hideMessageOfParent();
	permissionWorkflowGrid.setTotalPages(totalPageWorkflow);
	permissionWorkflowGrid.onAddNewRow.subscribe(function (e, args) {
		var item = args.item;
		item.bitFlag=0;
		permissionWorkflowGrid.invalidateRow(data.length);
		data.push(item);
		permissionWorkflowGrid.updateRowCount();
		permissionWorkflowGrid.render();
	});
	permissionWorkflowGrid.setData(data,true);	
	permissionWorkflowGrid.render();
}
//保存按钮
function saveBusheetPermission() {
	permissionWorkflowGrid.getEditorLock().commitCurrentEdit();
	var rowNums = permissionWorkflowGrid.getSelectedRows();
	if (rowNums.length != 0) {
		for ( var i = 0; i < rowNums.length; i++){
			var row = rowNums[i];
			var tempData = permissionWorkflowGrid.getData();
			var params = tempData[row];
			params.busheetId=busheetId;
			$.post(contextPath + "/saveBusheetPermission.jhtml", params, function(data) {
				selectpermissionList(busheetId);
				if(data==-1){
					showMessageOfParent('数据为空 ！');
					setTimeout("hideMessageOfParent()", "3000");
				}else if(data==-2){
					showMessageOfParent('用户不能重复 ！');
					setTimeout("hideMessageOfParent()", "3000");
				}else if (data > 0) {
					showMessageOfParent('保存成功 ！');
					setTimeout("hideMessageOfParent()", "3000");
				} else {
					showMessageOfParent('保存失败 ！');
					setTimeout("hideMessageOfParent()", "3000");
				}
			});
		}
	} else {
		alert("请勾选至少一条需要保存的数据!");
		return;
	}
};


//删除
function removeBusheetPermission() {
	var delRowNums = permissionWorkflowGrid.getSelectedRows();
    if(delRowNums.length!=0){
    	 if (confirm("确定要删除所选记录吗？")) {
			 for(var i= 0;i<delRowNums.length;i++){
				 var row = delRowNums[i];
				 var selData = permissionWorkflowGrid.getData();
				 var params=selData[row];
				 $.post(contextPath + "/removeBusheetPermission.jhtml?id="+params.id, null,function(data) {
					 selectpermissionList(busheetId);
					 if(data>0){
						 showMessageOfParent('删除成功 ！');
						 setTimeout("hideMessageOfParent()", "3000");
					 }else{
						 showMessageOfParent('删除失败 ！');
						 setTimeout("hideMessageOfParent()", "3000");
					 }
				 });
			 }
		 }
    }else{
    	alert("请勾选至少一条需要删除的数据!");
    }
}
$(function(){
	BusheetManage();
	permissionManage();
});