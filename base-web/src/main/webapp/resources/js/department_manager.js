var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "parentId",
			rootpId : "0"
		},
		key : {
			name : "departmentName"
		}
	},
	edit : {
		enable : false,
		showRemoveBtn : true,
		showRenameBtn : true
	},
//	async : {
//		enable : false,
//		url : "getadminareas.jhtml",
//		autoParam : [ "id=parentId" ],
//		// otherParam:{"otherParam":"zTreeAsyncTest"}
//		dataFilter : ajaxDataFilter
//	},
	callback : {
		onClick : zTreeOnClick,
		beforeRemove : zTreeBeforeRemove,
		onRemove : zTreeOnRemove,
		onRename : zTreeOnRename
	}
};

$(function() {
	
	//复制组织代码
	$("#copyCode").bind("click", function() {
		var a = $("#codeprefix").text();
		var b = $("#code").val();
		var c = $("#codesuffix").text();
		a = a + b + c;
		
		layerOptions={width:"484px",height:"60px"};
		layerOptions.content="<div style='margin-top:11px;'>组织代码为:"+a+"</div>";
		layer = new HkrtFramework.MessageBox("warnDiv",layerOptions);
		
		
	/*	
		if(window.clipboardData) {
			window.clipboardData.clearData();
		    window.clipboardData.setData("Text", a);         //IE浏览
		    alert("复制组织代码已复制到剪切板");
		}else if(window.netscape){
			try {
		        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		   } catch (e) {
		        alert("你使用的火狐浏览器无法自动复制！请手动复制："+a);
		   }
		}*/
	});
});

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

// 判断是否可以删除
function zTreeBeforeRemove(treeId, treeNode) {
	if(null !=treeNode.children){
		if(treeNode.children.length!=0){
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
			name : treeNode.departmentName
		},
		dataType : "json",
		success : function(data) {
			// ok
		}
	});
}

//创建parentId节点下面最大的子节点编码
function getNextCode(treeNodeLevel, parentId){
	var codeArray = [];
	$.ajax({
		  url: contextPath +"/getDepartmentByParentId.jhtml?parentId="+parentId,
		  async:false,
		  type:"POST",
		  success: function(result) {
			  $.each(result, function(k, v) {
				  var a = v.departmentCode;
				  var b = treeNodeLevel * 2 + 2;
				  a=a.substring(b, b+2);
				  codeArray.push(a);
			  });
		  }
	});
	var c = Math.max.apply(null,codeArray);
	if(codeArray.length < 1){
		c = 0;
	}
	c = c + 1;
	if(c < 10){
		c = "0" + c;
	}
	return c;
}
// 节点点击事件函数
function zTreeOnClick(event, treeId, treeNode) {
	var length = treeNode.departmentCode.length;
	var treeNodeLevel = treeNode.level;
	var codeprefix = 2 * treeNodeLevel;
	$("#code").show();
	$("#addnode").show();
	$("#systemUser").val(treeNode.userId);
	getRoleNameByUser();
	$("#codeprefix").text("");
	$("#codesuffix").text("");
	if (0 == treeNodeLevel) {
		$("#pcode").val("无");
		$("#pname").val("无");
		$("#ppk").val("");
		$("#codesuffixLi").hide();
//		$("#codeprefix").text("00");
		$("#code").hide();
		$("#codesuffix").text(treeNode.departmentCode);
	}else{
		$("#codesuffixLi").show();
		$("#pcode").val(treeNode.getParentNode().departmentCode);
		$("#ppk").val(treeNode.getParentNode().id);
		$("#pname").val(treeNode.getParentNode().departmentName);
		$("#codeprefix").text(treeNode.departmentCode.substring(0,codeprefix));
		$("#codesuffix").text(treeNode.departmentCode.substring(codeprefix + 2,length));
		$("#code").val(treeNode.departmentCode.substring(codeprefix,codeprefix + 2));
	} 
	
	$("#name").val(treeNode.departmentName);
	$("#pk").val(treeNode.id);

	changeParentDepCode = "";
	changeParentId = "";
	
	$("#showview").show();
	$("#removenode").show();
	$("#savenode").attr("style","margin-left: 0px");
	// 初始化为更新状态
	isaddnode = "false";
	
};

var isaddnode = "false";


// 新增子节点
function addnode() {
	var treeObj = $.fn.zTree.getZTreeObj("areaTree");
	var nodes = treeObj.getSelectedNodes();
	$("#systemUser").get(0).selectedIndex=0;
	$("#codesuffixLi").show();
	// 当前节点变为父节点，清空子节点信息
	$("#pcode").val($("#code").val());
	$("#pname").val($("#name").val());
	$("#ppk").val($("#pk").val());
	var treeNodeLevel=0;
	for ( var i = 0, l = nodes.length; i < l; i++) {
		var code = nodes[i].departmentCode;
		var length = code.length;
		treeNodeLevel = nodes[i].level;
		var codeprefix = 2 * treeNodeLevel + 2;
		if(0 == nodes[i].level){
			$("#codeprefix").text(code.substring(0,2));
			$("#code").hide();
			$("#codesuffix").text(code.substring(4,length));
		}else{
			$("#codeprefix").text(code.substring(0,codeprefix));
			$("#code").show();
			$("#codesuffix").text(code.substring(codeprefix+2,length));
		}
	}
	var pk = $("#pk").val();
	$("#code").val(getNextCode(treeNodeLevel, pk));
	$("#name").val("");
	$("#pk").val("");

	changeParentDepCode = "";
	changeParentId = "";
	
	// 设置新增状态
	isaddnode = "true";
	// 隐藏新增按钮和删除按钮
	$("#addnode").hide();
	$("#removenode").hide();
	$("#savenode").attr("style","margin-left: 150px");
}

// 保存节点信息
function savenode() {
	if("00"==$("#code").val()){
		alert("部门代码与上级重复!");
		return;
	}else if($("#code").val().length==0){
		alert("部门代码不能为空!");
		return;
	}else if($("#code").val().length != 2){
		alert("部门代码必须为两位!");
		return;
	}
	
	var pk = $("#pk").val();
	var departmentName = $("#name").val();
	var systemUser = $("#systemUser").val();
	
	//更换父节点后，ppk、departmentCode需要重新获取
	var ppk = "";
	var departmentCode = "";
	if(changeParentId == ""){
		ppk = $("#ppk").val();
		departmentCode = $("#codeprefix").text()+$("#code").val()+$("#codesuffix").text();
	}else{
		ppk = changeParentId;
		var changeParentNode = getTreeNodeById(changeParentId);
		treeNodeLevel = changeParentNode.level;
		departmentCode = getNextCode(treeNodeLevel, changeParentId);
		var codeprefix = changeParentDepCode.substring(0, (treeNodeLevel+1)*2);
		var codesuffix = changeParentDepCode.substring((treeNodeLevel+2)*2, changeParentDepCode.length);
		departmentCode = codeprefix + departmentCode + codesuffix;
	}
	
	if(pk == ppk){
		alert("修改上级部门有误，不能选择本部门");
		return;
	}
	
	$.ajax({
		url : "saveDepartment.jhtml",
		type : "post",
		data : {
			id : pk,
			parentId : ppk,
			departmentName : departmentName,
			departmentCode : departmentCode,
			userId : systemUser
		},
		dataType : "json",
		success : function(msg) {
			showMessageOfParent(msg.content);	
			setTimeout("hideMessageOfParent()",13000);
			if(msg.isSuccess){
				var data = msg.obj;
				if(data !=null){
					var treeObj = $.fn.zTree.getZTreeObj("areaTree");
					if (isaddnode == "true") {
						isaddnode = "";
						$("#pk").val(data.id);
						var parentNode = getTreeNodeById(ppk);
						treeObj.addNodes(parentNode, data);
					} else {
						var selectNode = getTreeNodeById(pk);
						//更换父节点
						if(ppk != selectNode.parentId){
							var parentNode = getTreeNodeById(ppk);
							treeObj.removeNode(selectNode);
							treeObj.addNodes(parentNode, data);
						}else{
							selectNode.id = data.id;
							selectNode.departmentName = data.departmentName;
							selectNode.departmentCode = data.departmentCode;
							selectNode.userId = data.userId;
							treeObj.updateNode(selectNode);
						}
					}
				}
				pname();
			}
		}
	});
}

function getTreeNodeById(id){
	var nodeArray = treeObj.getNodesByParam("id", id, null);
	var node = nodeArray[0];
	return node;
}


// 删除节点
function removenode() {
	if(!confirm("确定删除?")){
		return false;
	}
	var treeObj = $.fn.zTree.getZTreeObj("areaTree");
	var nodes = treeObj.getSelectedNodes();
	for ( var i = 0, l = nodes.length; i < l; i++) {
		if(null != nodes[i].children){
			if(nodes[i].children.length!=0){
				alert("请先清空下属节点!");
				return false;
			}
		}
	}
	var pk = $("#pk").val();
	$.ajax({
		url : "removeDepartment.jhtml",
		type : "post",
		data : {
			id : pk
		},
		dataType : "json",
		success : function(data) {
			if(data>0){
				$("#showview").hide();
				showMessageOfParent('删除成功!');	
				setTimeout("hideMessageOfParent()",1000);
				pname();
			}else{
				showMessageOfParent('删除失败!');	
				setTimeout("hideMessageOfParent()",1000);
			}
			
			for ( var i = 0, l = nodes.length; i < l; i++) {
				treeObj.removeNode(nodes[i]);
			}
		}
	});
}

var treeObj;
function loadTree() {
	showMessageOfParent("正在载入...");
	$.ajax({
		url : "getAllDepartment.jhtml",
		type : "post",
		dataType : "json",
		success : function(date) {
			treeObj = $.fn.zTree.init($("#areaTree"), setting, date);
			hideMessageOfParent();
			/*//初始化下拉一级节点区域
			var nodes = treeObj.getNodes();
			var s_item
			for(var i = 0, l = nodes.length; i < l; i++){
				if(1 == nodes[i].level){
					s_item=$("<option value="+nodes[i].id+">"+nodes[i].name+"</option>");
					$("#fid").append(s_item);
				}
			};*/
		}
	});
	// $.fn.zTree.init($("#areaTree"), setting);
	// var zTree = $("#areaTree").zTree(setting, treeNodes);
}
function getAllSystemUser(){
	 $("#systemUser").append("<option value=''  selected='selected'>请选择</option>");
	 $.getJSON(contextPath+"/getUserByState.jhtml",function(data){
		 $.each(data, function(k, v) {
				 $("#systemUser").append("<option value='"+v.id+"'>"+v.loginName+"</option>");
		 });
	 });
}
function getRoleNameByUser() {
	$.ajax({
		url : contextPath + "/getRoleNameByUser.jhtml?r=" + Math.random(),
		async : false,
		type : "POST",
		data : {"id" : $("#systemUser").val()},
		success : function(result) {
			if (result.code == '00') {
				$("#roleName").text(result.value);
			} else {
				$("#roleName").text("没有对应角色");
			}
		}
	});
}

var changeParentDepCode = "";
var changeParentId = "";
function pname(){
	var array=[];
	$.ajax({
		url: contextPath + "/getAllDepartment.jhtml",
		async:false,
		type:"POST",
		success: function(result) {
			$.each(result, function(i,v) {
				var aa = {};
				aa.value = v.departmentName;
    			aa.id = v.id;
    			aa.departmentCode = v.departmentCode;
				array.push(aa);
			});
		}
	});
	$("#pname").autocomplete({
		source: array,
		select:function(e, ui) {//选中某项的事件
			changeParentId = ui.item.id;
			changeParentDepCode = ui.item.departmentCode;
		}
	});
	//---------------------------------
//	var projects = [
//	                {
//	                  value: "jquery",
//	                  label: "jQuery",
//	                  desc: "the write less, do more, JavaScript library",
//	                  icon: "jquery_32x32.png"
//	                },
//	                {
//	                  value: "jquery-ui",
//	                  label: "jQuery UI",
//	                  desc: "the official user interface library for jQuery",
//	                  icon: "jqueryui_32x32.png"
//	                },
//	                {
//	                  value: "sizzlejs",
//	                  label: "Sizzle JS",
//	                  desc: "a pure-JavaScript CSS selector engine",
//	                  icon: "sizzlejs_32x32.png"
//	                }
//	              ];
//	$( "#pname" ).autocomplete({
//	      minLength: 0,
//	      source: projects,
//	      focus: function( event, ui ) {
//	        $( "#pname" ).val( ui.item.label );
//	        return false;
//	      },
//	      select: function( event, ui ) {
//	        $( "#pname" ).val( ui.item.label );
////	        $( "#project-id" ).val( ui.item.value );
//	        $( "#pname-description" ).html( ui.item.desc );
////	        $( "#project-icon" ).attr( "src", "images/" + ui.item.icon );
//	 
//	        return false;
//	      }
//	    })
//	    .autocomplete( "instance" )._renderItem = function( ul, item ) {
//	      return $( "<li>" )
//	        .append( "<a>" + item.label + "<br>" + item.desc + "</a>" )
//	        .appendTo( ul );
//	    };
}


$(document).ready(function() {
	// 预输入
	pname();
	
	loadTree();
	$("#showview").hide();
	getAllSystemUser();
	
	$("#systemUser").bind("change",function(){
		getRoleNameByUser();
	});
	
	// 绑定点击事件
	$("#addnode").click(addnode);
	$("#savenode").click(savenode);
	$("#removenode").click(removenode);
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
			// || "TableName:view" == null
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