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
<script type="text/javascript" src="resources/js/menu_manager.js"></script>
<script type="text/javascript">
	 	var contextPath = "${ctx}";
    </script>
    <style >
select {
    border: 1px solid #CCCCCC;
    border-radius: 3px 3px 3px 3px;
    color: #555555;
    display: inline-block;
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 13px;
    height: 28px;
    line-height: 18px;
    margin-bottom: 9px;
    padding: 4px;
    width: 160px;
    margin-left: 13px;
}

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
				<span>菜单结构管理</span>
		</div>
			<div class="zTreeDemoBackground left" style="width: 300px;">
				<ul id="areaTree" class="ztree" style="width: 300px;"></ul>
			</div>
			<div id="showview" style="width: 310px;margin-right: 10px;display: none;float: left;" class="right" >
				<ul >
					<li>
						<label >上级名称:</label>
						<input type="text" readonly="readonly" name="pname" id="pname" style="margin-left: 12px;"/>
					</li>
					<li id="codesuffixLi">
						<label>菜单代码:</label>
						<span id="codeprefix" style="text-align:right;margin-left: 15px;"></span>
						<input style="width:20px;margin-left: 1px;margin-right: 1px;" type="text" name="menuCode" size="1" maxlength="2" id="menuCode" />
						<span id="codesuffix" style="text-align:left;display: none"></span>
					</li>
					<li>
						<label>名称:</label>
						<input type="text" name="name" id="name" style="margin-left: 12px;" />
					</li>
					<li id="menuActionLi">
						<label>请求:</label>
						<input type="text" name="menuAction" id="menuAction" style="margin-left: 12px;" />
						<label>菜单权限:</label>
						<select id="permissionKey" name="permissionKey" class="input-border-color select-parent-id"></select>
					</li>
					<li>
						<label>状态:</label>
						<select  class="input-border-color select-parent-id"  name="status" id="status"> </select>
					</li>
					<li id="tableNameLi" style="display: none">
						<label>表名描述:</label>
						<input  type="text"  style="margin-left: 12px;"  id="tableNameDepict" /> 
					</li>
					<li  id="operating">
						<input type="button" value="新增" name="addnode" id="addnode" class="bootstrap-button insert" style="margin-left: 120px;"/> 
						<input type="button" value="保存" name="savenode" id="savenode" class="bootstrap-button save"/>
						<input type="button" value="删除" name="removenode" id="removenode" class="bootstrap-button"/>
					</li>
					<li><input type="hidden" name="pk" id="pk" />
						<input type="hidden" name="ppk" id="ppk" /> 
						<input type="hidden" name="pcode" id="pcode" /></li>
				</ul>
			</div>
			<div style="float: right;">
				<ul id="Tree" class="ztree" style="width: 300px;display: none;" ></ul>
			</div>
		</div>
</body>
</html>