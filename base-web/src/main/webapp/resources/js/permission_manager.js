var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "parentId",
			rootpId : "0"
		},
		key:{
			name: "permissionName"
		}
	},
	edit : {
		enable : false,
		showRemoveBtn : true,
		showRenameBtn : true
	},
	callback : {
		onClick : zTreeOnClick,
		beforeRemove : zTreeBeforeRemove,
		onRemove : zTreeOnRemove,
		onRename : zTreeOnRename
	}
};
var setting1 = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootpId : "0"
			},
			key:{
				name: "permissionName"
			}
		},
		edit : {
			enable : false,
			showRemoveBtn : true,
			showRenameBtn : true
		},
		callback : {
			onClick : updateOnClick
		}
	};
// 处理异步加载返回的节点属性信息
function ajaxDataFilter(treeId, parentNode, responseData) {
	if (responseData) {
		for ( var i = 0; i < responseData.length; i++) {
			if (responseData[i].parentId == 0) {
				responseData[i].isParent = "true";
			}
		}
	}
	return responseData;
};
function updateOnClick(event, treeId, treeNode) {
	var length=treeNode.permissionCode.length;
	var treeNodeLevel=treeNode.level+1;
	var codeprefix=2*treeNodeLevel;
	$("#codeprefix").text("");
	$("#codesuffix").text("");
	$("#status").val("");
	fu=treeNodeLevel;
		$("#ppk").val(treeNode.id);//父ID
		$("#pname").val(treeNode.permissionName);
		$("#codeprefix").text(treeNode.permissionCode.substring(0,codeprefix));
		$("#codesuffix").text(treeNode.permissionCode.substring(codeprefix+2,length));
		$("#code").val("");
	$("#status").val(treeNode.status);
};

// 判断是否可以删除
function zTreeBeforeRemove(treeId, treeNode) {
	if (null != treeNode.children) {
		if (treeNode.children.length != 0) {
			alert("请先清空下属节点!");
			return false;
		}
	}
	return true;
}
function zTreeOnRemove(event, treeId, treeNode) {
	$.ajax({
		url : "removeadminarea.jhtml",
		type : "post",
		data : {
			id : treeNode.id
		},
		dataType : "json",
		success : function() {
			//
		}
	});
}

function zTreeOnRename(event, treeId, treeNode) {
	$.ajax({
		url : "saveadminarea.jhtml",
		type : "post",
		data : {
			id : treeNode.id,
			name : treeNode.permissionName
		},
		dataType : "json",
		success : function(data) {
			// ok
		}
	});
}
var series=0;
//获取支持的级数
function getMenuNo(){
	$.ajax({
		  url: contextPath +'/getConfigByCode.jhtml?r='+Math.random(),
		  async:false,
		  type:"POST",
		  data:{"code":"10000004"},
		  success: function(result) {
			 series=result.configValue;
		  }
		});
};
// 节点点击事件函数
function zTreeOnClick(event, treeId, treeNode) {
	var length=treeNode.permissionCode.length;
	var treeNodeLevel=treeNode.level;
	var codeprefix=2*treeNodeLevel;
	$("#code").show();
	$("#codeprefix").text("");
	$("#codesuffix").text("");
	$("#menuAction").val("");
	$("#menuActionLi").hide();
	$("#menuAction").val("");//请求
	$("#status").val("");
	$("#permissionKey").val("");
	$("#requestMappingValueLi").hide();
	fu=treeNodeLevel;
	$("#requestMappingValue").val(treeNode.requestMappingValue);
	if (0 == treeNodeLevel) {
		$("#codesuffixLi").hide();
		$("#pname").val("无");
		$("#ppk").val("");
	}else{
		$("#codesuffixLi").show();
		$("#ppk").val(treeNode.getParentNode().id);//父ID
		$("#pname").val(treeNode.getParentNode().permissionName);
		$("#codeprefix").text(treeNode.permissionCode.substring(0,codeprefix));
		$("#codesuffix").text(treeNode.permissionCode.substring(codeprefix+2,length));
		$("#code").val(treeNode.permissionCode.substring(codeprefix,codeprefix+2));
	} 
	$("#Tree").hide();
	$("#TreeClose").hide();
	zTreeLevel=true;
	$("#permissionKey").val(treeNode.permissionKey);
	$("#status").val(treeNode.status);
	$("#name").val(treeNode.permissionName);
	$("#pk").val(treeNode.id);//主键

	$("#savenode").attr("style","margin-left: 0px");
	if(treeNodeLevel<series){
		$("#addnode").show();
	}else{
		$("#addnode").hide();
		$("#savenode").attr("style","margin-left: 65px");
	}
	if(treeNodeLevel==series){
		$("#requestMappingValueLi").show();
	}
	$("#showview").show();
	$("#removenode").show();

	// 初始化为更新状态
	isaddnode = "false";
};
var zTreeLevel=false;
var isaddnode = "false";
function menuCodeId(treeNodeLevel){
	var menuCodeId=[];
	$.ajax({
		  url: contextPath +"/getPermissionByParentId.jhtml?ParentId="+$("#pk").val(),
		  async:false,
		  type:"POST",
		  success: function(result) {
			  $.each(result, function(k, v) {
				  var a = v.permissionCode;
				  var b = treeNodeLevel*2+2;
				  a=a.substring(b,b+2);
				  menuCodeId.push(a);
			  });
		  }
	});
	var c = Math.max.apply(null,menuCodeId);
	if(menuCodeId.length<1){
		c=0;
	}
	c=c+1;
	if(c<10){
		c="0"+c;
	}
	return c;
}
// 新增子节点
function addnode() {
	zTreeLevel=false;
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	var nodes = treeObj.getSelectedNodes();
	
	$("#codesuffixLi").show();
	$("#codeprefix").text("");
	$("#codesuffix").text("");
	// 当前节点变为父节点，清空子节点信息
	$("#pname").val($("#name").val());
	$("#ppk").val($("#pk").val());
	var treeNodeLevel=0;
	for ( var i = 0, l = nodes.length; i < l; i++) {
		var menuCode=nodes[i].permissionCode;
		var length=menuCode.length;
		 treeNodeLevel=nodes[i].level;
		var codeprefix=2*treeNodeLevel+2;
		if(0==nodes[i].level){
			$("#codeprefix").text(menuCode.substring(0,2));
			$("#menuCode").hide();
			$("#codesuffix").text(menuCode.substring(4,length));
		}else{
			$("#codeprefix").text(menuCode.substring(0,codeprefix));
			$("#menuCode").show();
			$("#codesuffix").text(menuCode.substring(codeprefix+2,length));
		}
	}
	$("#code").val(menuCodeId(treeNodeLevel));
	$("#name").val("");
	$("#pk").val("");
	$("#permissionKey").val("");
	
	if (treeNodeLevel==2) {
		$("#requestMappingValueLi").show();
	}
	
	// 设置新增状态
	isaddnode = "true";
	// 隐藏新增按钮和删除按钮
	$("#addnode").hide();
	$("#removenode").hide();
}

// 保存节点信息
function savenode() {
	if ($("#code").val().length == 0) {
		alert("CODE不能为空!");
	} else if ($("#code").val().length < 2) {
		alert("CODE必须为两位!");
	} else {
		var name = $("#name").val();
		var pk = $("#pk").val();
		var ppk = $("#ppk").val();
		var permissionKey = $("#permissionKey").val();
		var code = $("#codeprefix").text()+$("#code").val()+$("#codesuffix").text();
		var requestMappingValue = $("#requestMappingValue").val();
		$.ajax({
			url : "savePermission.jhtml",
			type : "post",
			data : {
				id : pk,
				parentId : ppk,
				permissionName : name,
				permissionKey : permissionKey,
				permissionCode : code,
				requestMappingValue:requestMappingValue
			},
			dataType : "json",
			success : function(data) {
				if(data.id=="2000"){
					showMessageOfParent('code已存在!');
				}else if(data.id=="1000"){
					showMessageOfParent('超出最大级别!');
				}else{
					if (data != null) {
						var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
						var nodes = treeObj.getSelectedNodes();
						for ( var i = 0, l = nodes.length; i < l; i++) {
							if (isaddnode == "true") {
								treeObj.addNodes(nodes[i], data);
							} else {
								nodes[i].name = data.permissionName;
								nodes[i].permissionKey = data.permissionKey;
								nodes[i].requestMappingValue = data.requestMappingValue;
								treeObj.updateNode(nodes[i]);
							}
						}
						showMessageOfParent('保存成功!');
					} else {
						showMessageOfParent('保存失败!');
					}
				}
				setTimeout("hideMessageOfParent()", 1000);
			}
		});
	}
}

// 删除节点
function removenode() {
	if (!confirm("确定删除?")) {
		return false;
	}
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	var nodes = treeObj.getSelectedNodes();
	for ( var i = 0, l = nodes.length; i < l; i++) {
		if (null != nodes[i].children) {
			if (nodes[i].children.length != 0) {
				alert("请先清空下属节点!");
				return false;
			}
		}
	}
	var pk = $("#pk").val();
	$.ajax({
		url : "removePermission.jhtml",
		type : "post",
		data : {
			id : pk
		},
		dataType : "json",
		success : function(data) {
			if (data == '0' ) {
				$("#showview").hide();
				showMessageOfParent('删除成功 ！');	
				for ( var i = 0, l = nodes.length; i < l; i++) {
					treeObj.removeNode(nodes[i]);
				}
			} else if (data == '-1') { 
				showMessageOfParent('删除失败 ！');
			} else {	
				showMessageOfParent('请先移除'+data+'的该权限!');
			}
			setTimeout("hideMessageOfParent()", 1000);
		}
	});
}

var treeObj;
var tree;
function loadTree() {
	showMessageOfParent("正在载入...");
	$.ajax({
		url : "getAllPermission.jhtml",
		type : "post",
		dataType : "json",
		success : function(date) {
			treeObj = $.fn.zTree.init($("#permissionTree"), setting, date);
			 hideMessageOfParent();
		}
	});
}
function loadTree1(id) {
	$.ajax({
		url : "getotherPermissionList.jhtml",
		type : "post",
		dataType : "json",
		 data:{"id":id},
		success : function(date) {
			tree = $.fn.zTree.init($("#Tree"), setting1, date);
		}
	});
	$("#Tree").show();
	$("#TreeClose").show();
};
function updateFather(){
	if(zTreeLevel){
		loadTree1($("#pk").val());
	}
}
function TreeClose(){
	$("#Tree").hide();
	$("#TreeClose").hide();
}
$(document).ready(function() {
	loadTree();
	getMenuNo();
	$("#showview").hide();
	// 绑定点击事件
	$("#addnode").click(addnode);
	$("#savenode").click(savenode);
	$("#removenode").click(removenode);
	$("#pname").click(updateFather);
	$("#TreeClose").click(TreeClose);
	var id = $(".leftside-white",window.parent.document).attr("id");
	if(id==undefined){
		return;
	}
	var permission = false;
	$.ajax({
		url: contextPath+"/isPermitted.jhtml?permissionName=TableName:view&r="+Math.random(),
		async:false,
		success: function(data) {
			permission = data;
			if (permission) {
			var tableName="";
			$.ajax({
				url : contextPath + "/getTableNameByMenuId.jhtml?r=" + Math.random(),
				async : false,
				type : "POST",
				data : {"id" : id},
				success : function(result) {
					if(null==result.tableNameDepict){
						tableName="没有对应的表";
					}else{
						tableName = result.tableNameDepict;
					}
				}
			});
				var $div = $("<div id='cursorMenu' style='position:absolute;width:150px;background:#FFF;border:1px solid #8BAABF;box-shadow:0 0 10px rgba(0, 0, 0, 0.5);padding:4px 0;z-index:100;'></div>");
				var $cursorMenu = $("<div style='cursor: pointer;padding:3px 30px;'>查看表名</div>");
				$div.append($cursorMenu);
				$("body").click(function(){$div.remove();});
				$("body").bind("contextmenu",function(){
					if($("#cursorMenu")!=undefined){
						$("#cursorMenu").remove();
					}
				});
				$(".zTreeDemoBackground").bind("contextmenu",function(e){
					x = e.clientX;
					y = e.clientY;
					$div.css("left",x);
					$div.css("top",y);
					if($("#cursorMenu")!=undefined){
						$("#cursorMenu").remove();
					}
					$("body").append($div);
					$div.children().hover(function(){$(this).css("color","#FFF").css("background","#1664B4");},function(){$(this).css("color","#000").css("background","#FFF");});
					$cursorMenu.bind("click",function(e){
						$(this).parent().remove();
						$(this).css("color","#000").css("background","#FFF");
						 var offsetWidth = document.body.offsetWidth;
						 var offsetHeight = document.body.offsetHeight;
						 var divLeft = (offsetWidth/2-200)+"px";
						 var divTop = (offsetHeight/2-200)+"px";
						$divTableName = $('<div style="left:'+divLeft+';top:'+divTop+';width:350px;height: auto;" class="message-box" id="warnDiv_contentDiv"></div>');
						$divTop = $('<div class="message-top"></div>');
						$WarnDiv = $('<div style="float:left;font-weight: bold;margin-left: 10px;">提示</div>').appendTo($divTop);
						$closeBtn = $('<button type="button" class="message-close-button">x</button>)').appendTo($divTop);
						$divContent = $('<div style="padding: 30px 0;text-align: center;color: #555555;background:none repeat scroll 0 0 #FAFAFA;">'+tableName+'</div>');
						$divTableName.append($divTop).append($divContent);
						$mask = $('<div id="warnDiv" class="nui-mask"></div>');
						$("body").append($mask).append($divTableName);
						$closeBtn.bind("click",function(){
							$(this).parent().parent().remove();
							$mask.remove();
						});
					});
					return false;
				});
			}
			
		}
	});
});