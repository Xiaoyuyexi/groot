var busheetWorkflowGrid;
var totalPageWorkflow;
var lookupArray=[];
var busheet;
function BusheetWorkflowManage() {
	showMessageOfParent("正在载入...");
	  var data = [];
	  var columns = [];
	  var checkboxSelector = new Slick.CheckboxSelectColumn({
		    cssClass: "slick-cell-checkboxsel"
		  });
	  
	  columns.push(checkboxSelector.getColumnDefinition());
	  columns.push(
	    //{id: "code", name: "工作流编号", field: "code", width: 160, editor: Slick.Editors.Text},
	    {id: "statusCode", name: "编号", field: "statusCode", width: 130, editor: Slick.Editors.Text,maxLength:3},
	    {id: "workflowName", name: "名称", field: "workflowName", width: 160, editor: Slick.Editors.Text}
	    //{id: "status", name: "状态", lookupArray:getLookupCdeList(), field: "status", width: 160,formatter: Slick.Formatters.Lookup, editor: Slick.Editors.LookupSelect},
	    //{id: "busheetId", name: "所属单据", lookupArray:getBusheetAll(), field: "busheetId", width: 160,formatter: Slick.Formatters.Lookup, editor: Slick.Editors.LookupSelect}
	  );
	  var options = {
			    editable: true,
			    enableAddRow: true,
			    enableCellNavigation: true,
			    asyncEditorLoading: false,
			    autoEdit: false,
			    showTopPanel: true,
  			    showPagePanel:false,
			    topPanelButtonAttr:[
			      			       {permission:"BusheetWorkflow:save",name:"saveClick",onclick:"saveBusheetWorkflow()",title:"保存",alt:"save",src:"resources/images/save.png",className:"hover-icon-save"},
			      			       {permission:"BusheetWorkflow:remove",name:"removeClick",onclick:"removeBusheetWorkflow()",title:"删除",alt:"cancel",src:"resources/images/delete.png",className:"hover-icon-delete"}
			      			       ]
		        
			  };
	  
		  $.ajax({
			  url: contextPath +"/getBusheetWorkflowList.jhtml?r="+Math.random(),
			  async:false,
			  type:"POST",
			  success: function(result) {
			$.each(result, function(i,v) {
				var d = (data[i] = {});
			  d["id"] = v.id;
			  d["workflowName"] = v.workflowName;
			  d["statusCode"] = v.statusCode;
//			  d["busheetId"] = v.busheetId;
//			  d["status"] = v.status;
			});
	//	  var toolbarModel = new Slick.Plugins.ToolbarModel({queryText:"单据名称",action:"selectBusheetWorkflowList()"});
		  busheetWorkflowGrid = new Slick.Grid("#busheet_workflow_grid", data, columns, options);
		  busheetWorkflowGrid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow:false}));
		  busheetWorkflowGrid.registerPlugin(checkboxSelector);
	//	  busheetWorkflowGrid.registerPlugin(toolbarModel); 
		  
		  
		  busheetWorkflowGrid.onAddNewRow.subscribe(function (e, args) {
		    var item = args.item;
		    item.bitFlag=0;
		    busheetWorkflowGrid.invalidateRow(data.length);
		    data.push(item);
		    busheetWorkflowGrid.updateRowCount();
		    busheetWorkflowGrid.render();
		  });
		  hideMessageOfParent();
			  }
	 });
}
function getBusheetWorkflowListForPage(busheetId) {
	$("#busheet_workflow_gridDiv").show();
	if(busheetId!=undefined){
		busheet=busheetId;
		beginPage=1;
	}
	showMessageOfParent("正在载入...");
	var name=$("#text_busheet_workflow_grid").val();
	var data = [];
	$.ajax({
		  url: contextPath +"/getBusheetWorkflowList.jhtml?r="+Math.random(),
		  async:false,
		  type:"POST",
		  data:{"name":name,"busheetId":busheet},
		  success: function(result) {
			  $.each(result, function(i,v) {
					var d = (data[i] = {});
					 d["id"] = v.id;
					  d["workflowName"] = v.workflowName;
					  d["statusCode"] = v.statusCode;
					  //d["busheetId"] = v.busheetId;
//					  d["status"] = v.status;
				});
			  hideMessageOfParent();
		  }
		});
	busheetWorkflowGrid.onAddNewRow.subscribe(function (e, args) {
		var item = args.item;
		item.bitFlag=0;
		busheetWorkflowGrid.invalidateRow(data.length);
		data.push(item);
		busheetWorkflowGrid.updateRowCount();
		busheetWorkflowGrid.render();
	});
	busheetWorkflowGrid.setData(data,true);	
	busheetWorkflowGrid.render();
}

//保存按钮
function saveBusheetWorkflow() {
	busheetWorkflowGrid.getEditorLock().commitCurrentEdit();
	var rowNums = busheetWorkflowGrid.getSelectedRows();
	if (rowNums.length != 0) {
		for ( var i = 0; i < rowNums.length; i++){
			var row = rowNums[i];
			var tempData = busheetWorkflowGrid.getData();
			var params = tempData[row];
			params.busheetId=busheet;
			$.post(contextPath + "/saveBusheetWorkflow.jhtml", params, function(data) {
				if (data > 0) {
					showMessageOfParent('保存成功 ！');
				} else {
					showMessageOfParent('保存失败 ！');
				}
				setTimeout("hideMessageOfParent()", "5000");
				getBusheetWorkflowListForPage();
			});
		}
	} else {
		alert("请勾选至少一条需要保存的数据!");
		return;
	}
};


//删除
function removeBusheetWorkflow() {
	var delRowNums = busheetWorkflowGrid.getSelectedRows();
    if(delRowNums.length!=0){
    	 if (confirm("确定要删除所选记录吗？")) {
			 for(var i= 0;i<delRowNums.length;i++){
				 var row = delRowNums[i];
				 var selData = busheetWorkflowGrid.getData();
				 var params=selData[row];
				 $.post(contextPath + "/removeBusheetWorkflow.jhtml", params,function(data) {
					 if(data>0){
						 showMessageOfParent('删除成功 ！');	
						 getBusheetWorkflowListForPage();
					 }else{
						 showMessageOfParent('删除失败 ！');
					 }
				 });
			 }
		 }
    }else{
    	alert("请勾选至少一条需要删除的数据!");
    }
}
//获取某个域的字典
function getLookupCdeList(){
	lookupArray=[];
	$.ajax({
		  url: contextPath +'/getLookupCdeList.jhtml?r='+Math.random(),
		  async:false,
		  type:"POST",
		  data:{"code":"10261001"},
		  success: function(result) {
			  $.each(result, function(k, v) {
				  lookupArray.push(v);
			  });
		  }
		});
	return lookupArray;
};
function getBusheetAll(){
	lookupArray=[];
	$.ajax({
		  url: contextPath +'/getBusheetAll.jhtml?r='+Math.random(),
		  async:false,
		  type:"POST",
		  success: function(result) {
			  $.each(result, function(k, v) {
				  lookupArray.push(v);
			  });
		  }
		});
	return lookupArray;
};
$(function(){
	BusheetWorkflowManage();
});