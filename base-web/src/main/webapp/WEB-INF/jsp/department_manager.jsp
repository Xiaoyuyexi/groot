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
<link type="text/css" rel="stylesheet" href="resources/js/hkrtframework-layer/css/hkrtframework-layer.css" />
<link rel="stylesheet" href="resources/js/jquery-ui-1.11.0.custom/jquery-ui.min.css" type="text/css" />

<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui-1.11.0.custom/jquery-ui.js"></script>
<script type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/Map.js"></script>
<script type="text/javascript" src="resources/js/hkrtframework-layer/hkrtframework-layer.js"></script>
<script type="text/javascript" src="resources/js/department_manager.js"></script>
<script type="text/javascript">
	var contextPath = "${ctx}";
</script>
<style >
label{
width: 80px;
display:inline-block;
text-align: right;
}

</style>
</head>
<body>
	<div class="content_wrap" style="width: 750px;">
		<div class="content_wrap_title" style="width: 750px;">
				<span>组织结构管理</span>
		</div>
			<div class="zTreeDemoBackground left" style="width: 300px;">
				<ul id="areaTree" class="ztree" style="width: 300px;"></ul>
			</div>
			<div id="showview" style="width: 400px;margin-right: 10px;" class="right" >
				<ul >
					<li>
						<label >上级名称:</label>
						<input type="text" name="pname" id="pname" style="margin-left: 5px;"/><p id="pname-description"style="margin-left: 50px;"></p>
					</li>
					<li id="codesuffixLi">
						<label>组织代码:</label>
						<span id="codeprefix" style="text-align:right; margin-left: 6px;"></span>
						<input style="width:20px;margin-left: 1px;margin-right: 1px;" type="text" name="code" size="1" maxlength="2" id="code" />
						<span id="codesuffix" style="text-align:left; display: none "></span>
						<a id="copyCode" href="javascript:void(0);" style="margin-left: 10px;">查看组织代码</a>
					</li>
					<li >
						<label>用户名:</label>
						<select id="systemUser" style="margin-left: 5px;width: 130px;" class="bootstrap-select"></select>
					</li>
					<li>
						<label>角色名称:</label>
						<span style="margin-left: 8px;" id="roleName"></span>
					</li>
					<li>
						<label>名称:</label>
						<input type="text" name="name" id="name" style="margin-left: 5px;" />
					</li>
					<li >
						<input type="button" style="margin-left: 111px;" value="新增" name="addnode" id="addnode" class="bootstrap-button insert"/> 
						<input type="button" value="保存" name="savenode" id="savenode" class="bootstrap-button save"/>
						<input type="button" value="删除" name="removenode" id="removenode" class="bootstrap-button"/>
					</li>
					<li><input type="hidden" name="pk" id="pk" /> 
						<input type="hidden" name="ppk" id="ppk" /> 
						<input type="hidden" name="pcode" id="pcode" />
					</li>
				</ul>
			</div>
		</div>
</body>
</html>