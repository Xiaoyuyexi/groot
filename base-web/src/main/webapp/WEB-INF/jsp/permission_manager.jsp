<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="resources/css/style.css" />
<link rel="stylesheet" href="resources/js/ztree/css/area.css" type="text/css" />
<link rel="stylesheet" href="resources/js/ztree/css/zTreeStyle.css" type="text/css" />

<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script  type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.exedit-3.5.js"></script>
<script  type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/permission_manager.js"></script>
<script type="text/javascript">
	 	var contextPath = "${ctx}";
</script>
<style >
label{
width: 80px;
display:inline-block;
text-align: right;
}
#Tree li{
line-height: 14px;
} 
#showview li{
width: 310px;
}
</style>
</head>
<body class="overflow">
	<div class="content_wrap" style="width: 932px;">
		<div class="content_wrap_title" style="width: 932px;">
				<span>权限管理</span>
		</div>
			<div class="zTreeDemoBackground left" style="width: 300px;">
				<ul id="permissionTree" class="ztree" style="width: 300px;"></ul>
			</div>
			<div id="showview" style="width: 310px;margin-right: 10px;float: left;" class="right" >
				<ul >
					<li>
						<label >上级名称:</label>
						<input type="text" readonly="readonly" name="pname" id="pname" style="margin-left: 5px;"/>
					</li>
					<li id="codesuffixLi">
						<label>CODE:</label>
						<span id="codeprefix" style="text-align:right;margin-left: 6px;"></span>
						<input style="width:20px;margin-left: 1px;margin-right: 1px;" type="text" name="code" size="1" maxlength="2" id="code" />
						<span id="codesuffix" style="text-align:left;display: none"></span>
					</li>
					<li>
						<label>名称:</label>
						<input type="text" name="name" id="name" style="margin-left: 5px;" />
					</li>
					<li>
						<label>KEY:</label>
						<input type="text" name="permissionKey" id="permissionKey" style="margin-left: 5px;" />
					</li>
					<li id="requestMappingValueLi" style="display: none">
						<label>请求接口:</label>
						<input  type="text"  style="margin-left: 5px;"  id="requestMappingValue" /> 
					</li>
					<li>
						<div style="margin-left:111px;">
							<input type="button" value="新增" name="addnode" id="addnode" class="bootstrap-button insert"/>
							<shiro:hasPermission name="PermissionManager:save">
								<input type="button" value="保存" name="savenode" id="savenode" class="bootstrap-button save"/>	
							</shiro:hasPermission>
							<shiro:hasPermission name="PermissionManager:remove">
								<input type="button" value="删除" name="removenode" id="removenode" class="bootstrap-button"/>	
							</shiro:hasPermission>
						</div>
					</li>
					<li><input type="hidden" name="pk" id="pk" /> 
						<input type="hidden" name="ppk" id="ppk" /> 
						<input type="hidden" name="pcode" id="pcode" />
					</li>
				</ul>
			</div>
			<div style="float: right;">
				<ul id="Tree" class="ztree" style="width: 300px;display: none;" >
				</ul>
				<input type="button" id="TreeClose" class="bootstrap-button" value="取消" style="margin-top: 20x;margin-left: 257px;display: none"/>
			</div>
		</div>
</body>
</html>