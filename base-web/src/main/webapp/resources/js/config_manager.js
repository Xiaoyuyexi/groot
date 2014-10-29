//加载
function settleManage() {
	showMessageOfParent("正在载入...");
	$('#settle_date').datepicker({
		showButtonPanel: true,
		dateFormat: 'yy-mm-dd'
	});
	
	for(var i=0;i<=23;i++){
		var value =i;
		if(i<10){
			value="0"+value;
		}
		 $("#settle_time").append("<option value='"+value+"'>"+value+"</option>");
	}
	
	$("#setConfig").bind("click",function(){
		save();
	});
	
	hideMessageOfParent();
}

function configManage() {
	showMessageOfParent("正在载入...");
	var beginPage = 1;
	var data = [];
	var columns = [];
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});

	columns.push(checkboxSelector.getColumnDefinition());
	columns.push({
		id : "configCode",
		name : "变量代码",
		field : "configCode",
		width : 120,
		editor : Slick.Editors.Text
	},{
		id : "configName",
		name : "变量名称",
		field : "configName",
		width : 140,
		editor : Slick.Editors.Text
	}, {
		id : "configValue",
		name : "变量值",
		field : "configValue",
		width : 150,
		editor : Slick.Editors.Text
	}, {
		id : "remark",
		name : "备注",
		field : "remark",
		width : 230,
		editor : Slick.Editors.Text
	});
	var options = {
		editable : true,
		enableAddRow : true,
		enableCellNavigation : true,
		asyncEditorLoading : false,
		autoEdit : false,
		showTopPanel : true,
		callback : selectConfigForPage,
		showPagePanel : true,
		topPanelButtonAttr : [ {
			permission : "ConfigManager:save",
			name : "saveClick",
			onclick : "saveConfig()",
			title : "保存",
			alt : "save",
			src : "resources/images/save.png",
			className : "hover-icon-save"
		}, {
			permission : "ConfigManager:remove",
			name : "removeClick",
			onclick : "removeConfig()",
			title : "删除",
			alt : "cancel",
			src : "resources/images/delete.png",
			className : "hover-icon-delete"
		} ]

	};
	$.ajax({
		url : contextPath + "/getConfigListForPage.jhtml?r=" + Math.random(),
		async : false,
		type : "POST",
		data : {
			"currentPage" : beginPage,
			"name" : ''
		},
		success : function(result) {
			totalPage = result.pages;
			$.each(result.list, function(i, v) {
				var d = (data[i] = {});
				d["id"] = v.id;
				d["configName"] = v.configName;
				d["configCode"] = v.configCode;
				d["configValue"] = v.configValue;
				d["remark"] = v.remark;
			});
			var toolbarModel = new Slick.Plugins.ToolbarModel({
				queryText : "请输入配置名",
				action : "getConfigForPage()"
			});
			configGrid = new Slick.Grid("#systemConfigGrid", data, columns, options);
			configGrid.setSelectionModel(new Slick.RowSelectionModel({
				selectActiveRow : false
			}));
			configGrid.registerPlugin(checkboxSelector);
			configGrid.setTotalPages(totalPage);
			configGrid.setTotalCount(result.count);
			configGrid.registerPlugin(toolbarModel);

			configGrid.onAddNewRow.subscribe(function(e, args) {
				var item = args.item;
				item.bitFlag = 0;
				configGrid.invalidateRow(data.length);
				data.push(item);
				configGrid.updateRowCount();
				configGrid.render();
			});
			hideMessageOfParent();
		}
	});
}
//删除
function removeConfig() {
	var delRowNums = configGrid.getSelectedRows();
	if (delRowNums.length != 0) {
		if (confirm("确定要删除所选记录吗？")) {
			for ( var i = 0; i < delRowNums.length; i++) {
				var params = validateRemove(configGrid,delRowNums,i);
				if(params==false) continue;
				$.post(contextPath + "/removeConfig.jhtml", params, function(data) {
					showMessageOfParent(data.content);
					setTimeout("hideMessageOfParent()", 2000);
					if (data.code == "00") {
						selectConfigForPage();
					}
				});
			}
		}
	} else {
		alert("请勾选至少一条需要删除的数据!");
	}
}

// 保存按钮
function saveConfig() {
	configGrid.getEditorLock().commitCurrentEdit();
	var rowNums = configGrid.getSelectedRows();
	if (rowNums.length != 0) {
		for ( var i = 0; i < rowNums.length; i++) {
			var row = rowNums[i];
			var tempData = configGrid.getData();
			var params = tempData[row];
			$.post(contextPath + "/saveConfig.jhtml", params, function(data) {
				showMessageOfParent(data.content);
				setTimeout("hideMessageOfParent()", 2000);
				if(data.code == '00'){
					selectConfigForPage();
				}
			});
		}
	} else {
		alert("请勾选至少一条需要保存的数据!");
		return;
	}
};

function getConfigForPage() {
	$("#systemConfigGrid_pageUIId_currentPageId").text("1");
	selectConfigForPage();
}
// 分页
function selectConfigForPage() {
	var currentPage = configGrid.getCurrentPage();
	var data = [];
	datas=[];
	var name = $("#text_systemConfigGrid").val();
	/*
	 * if (name.length > 0) { beginPage = 1; }
	 */
	$.ajax({
		url : contextPath + '/getConfigListForPage.jhtml',
		data : {
			"currentPage" : currentPage,
			"name" : name
		},
		async : false,
		type : "POST",
		success : function(result) {
			totalPage = result.pages;
			count = result.count;
			$.each(result.list, function(k, v) {
				var d = (data[k] = {});
				d["id"] = v.id;
				d["configName"] = v.configName;
				d["configCode"] = v.configCode;
				d["configValue"] = v.configValue;
				d["remark"] = v.remark;
			});
			configGrid.setTotalCount(result.count);
		}
	});
	configGrid.setTotalPages(totalPage);
	configGrid.setTotalCount(count);
	configGrid.onAddNewRow.subscribe(function(e, args) {
		var item = args.item;
		item.bitFlag = 0;
		configGrid.invalidateRow(data.length);
		data.push(item);
		configGrid.updateRowCount();
		configGrid.render();
	});
	configGrid.setData(data, true);
	configGrid.render();
}

//保存按钮
function save(){
	var date = $('#settle_date').val();
	var time = $("#settle_time").val();
	var id = $("#settle_id").val();
	var settleDate = date+" "+time+":00:00";
	$.ajax({
		url : "set_settle_config.jhtml",
		async : false,
		type : "POST",
		data : {
			"id" : id,
			"settleDate" : settleDate
		},
		success : function(data) {
			if("00"==data){
				showMessageOfParent("操作成功");
			}else{
				showMessageOfParent("操作失败");
			}
			setTimeout("hideMessageOfParent()", 2000);
		}
	});
}
//解析日期格式
function parseSettleDate(settleDate){
	var date = settleDate.substr(0,10);
	$("#settle_date").attr("value",date);
	var time = settleDate.substr(11,2);
	$("#settle_time").attr("value",time);
}

//初始化页面日期
function init(){
	$.ajax({
		url : "getConfigByCode.jhtml",
		async : false,
		type : "POST",
		data : {
			"code" : '10000001'
		},
		success : function(data) {
			$("#settle_id").attr("value",data.id);
			parseSettleDate(data.value);
		}
	});
}
//组装日期格式
function packageSettleDate(){
	
}
//解析日期格式
function parseSettleDate(settleDate){
	var date = settleDate.substr(0,10);
	$("#settle_date").attr("value",date);
	var time = settleDate.substr(11,2);
	$("#settle_time").attr("value",time);
}

//加载初始化
$(function() {
	settleManage();
	configManage();
	init();
});