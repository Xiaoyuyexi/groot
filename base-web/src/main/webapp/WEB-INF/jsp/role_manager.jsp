<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="resources/css/style.css" />
<link rel="stylesheet" href="resources/js/slick/slick.grid.css"
	type="text/css" />
<link rel="stylesheet"
	href="resources/js/slick/css/smoothness/jquery-ui-1.8.16.custom.css"
	type="text/css" />
<link rel="stylesheet" href="resources/js/slick/css/examples.css"
	type="text/css" />
<link rel="stylesheet"
	href="resources/js/slick/controls/slick.pager.css" type="text/css" />
	<link rel="stylesheet" href="resources/js/ztree/css/area.css" type="text/css" />
<link rel="stylesheet" href="resources/js/ztree/css/zTreeStyle.css" type="text/css" />

<script type="text/javascript"
	src="resources/js/slick/lib/jquery-1.7.min.js"></script>
<script type="text/javascript"
	src="resources/js/slick/lib/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript"
	src="resources/js/slick/lib/jquery.event.drag-2.0.min.js"></script>

<script type="text/javascript"
	src="resources/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="resources/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="resources/js/ztree/jquery.ztree.exedit-3.5.js"></script>

<script type="text/javascript"
	src="resources/js/slick/plugins/slick.toolbarmodel.js"></script>
<script type="text/javascript" src="resources/js/slick/slick.core.js"></script>
<script type="text/javascript"
	src="resources/js/slick/plugins/slick.cellrangedecorator.js"></script>
<script type="text/javascript"
	src="resources/js/slick/plugins/slick.cellrangeselector.js"></script>
<script type="text/javascript"
	src="resources/js/slick/plugins/slick.cellselectionmodel.js"></script>
<script type="text/javascript"
	src="resources/js/slick/plugins/slick.checkboxselectcolumn.js"></script>
<script type="text/javascript"
	src="resources/js/slick/plugins/slick.rowselectionmodel.js"></script>
<script type="text/javascript"
	src="resources/js/slick/slick.formatters.js"></script>
<script type="text/javascript" src="resources/js/slick/slick.editors.js"></script>
<script type="text/javascript" src="resources/js/slick/slick.grid.js"></script>
<script type="text/javascript"
	src="resources/js/slick/slick.dataview.js"></script>
<script type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/role_manager.js"></script>
</head>
<body>
	<script type="text/javascript">
		var contextPath = "${ctx}";
	</script>

	<div class="role-manager" style="width: 750px; overflow: auto;">
		<div style="float: left;">
			<div id="roleManagerGrid"
				style="width: 312px; height: 450px; border: 1px solid silver; border-top: 0;"></div>
		</div>

		<div class="zTreeDemoBackground left" style="width: 400px;float: left;">
				<ul id="permissionTree" class="ztree" style="width: 400px;height: 466px;"></ul>
			</div>
			<!-- <div>
			<input type="button" value="保存" id="rp_save" class="bootstrap-button" style="margin-top:-5px;margin-left:300px;"/>
			</div> -->
	</div>

</body>
</html>