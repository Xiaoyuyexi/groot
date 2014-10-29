<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css"	rel="stylesheet" href="resources/css/style.css"/>
<link rel="stylesheet" href="resources/js/slick/slick.grid.css" type="text/css"/>
<link rel="stylesheet" href="resources/js/slick/css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css"/>
<link type="text/css" href="resources/js/slick/css/examples.css" rel="stylesheet"/>
<link rel="stylesheet" href="resources/js/slick/controls/slick.pager.css" type="text/css"/>
<script  type="text/javascript" src="resources/js/slick/lib/jquery-1.7.min.js"></script>
<script  type="text/javascript" src="resources/js/upload/ajaxfileupload.js"></script>
<script  type="text/javascript" src="resources/js/slick/lib/jquery-ui-1.8.16.custom.min.js"></script>
<script  type="text/javascript" src="resources/js/slick/lib/jquery.event.drag-2.0.min.js"></script>
<script  type="text/javascript" src="resources/js/slick/plugins/slick.toolbarmodel.js"></script>
<script  type="text/javascript" src="resources/js/slick/slick.core.js"></script>
<script  type="text/javascript" src="resources/js/slick/plugins/slick.cellrangedecorator.js"></script>
<script  type="text/javascript" src="resources/js/slick/plugins/slick.cellrangeselector.js"></script>
<script  type="text/javascript" src="resources/js/slick/plugins/slick.cellselectionmodel.js"></script>
<script  type="text/javascript" src="resources/js/slick/plugins/slick.checkboxselectcolumn.js"></script>
<script  type="text/javascript" src="resources/js/slick/plugins/slick.rowselectionmodel.js"></script>
<script  type="text/javascript" src="resources/js/slick/slick.formatters.js"></script>
<script  type="text/javascript" src="resources/js/slick/slick.editors.js"></script>
<script  type="text/javascript" src="resources/js/slick/slick.grid.js"></script>
<script  type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/busheet_workflow_manager.js"></script>
<script type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/busheet_manager.js"></script>
</head>
<body class="overflow">
<script type="text/javascript">
	 var contextPath = "${ctx}";
    </script>
	<div id="busheet_grid"  style="width:845px;height:350px;border:1px solid silver;border-top:0;"></div>
	<div style="border: 1px solid silver;margin-top:3px;clear: left;width:845px;min-height: 100px;">
	<div class="mode_menu_tag" style="width:845px;clear: left;margin-top: 0px;">
			<ul>
				<li id="1_tab" class="nowtag"><a href="javascript:void(0);" id="tab1_link" style="border-left: none;">单据状态</a></li>
				<li id="2_tab" class=""><a href="javascript:void(0);" id="tab2_link" style="border-left: none;">单据数据权限</a></li>
			</ul>
	</div>
	<div style="margin-left: 10px;margin-top: 5px; padding-bottom: 7px;">
		<div id="1_container">
			<div class="hide"  id="busheet_workflow_gridDiv">
				<div id="busheet_workflow_grid" style="width: 320px;height: 350px;border:1px solid silver;border-top:0;"></div>
			</div>
		</div>
		<div class="hide" id="2_container">
			<div class="hide" id="busheet_permission_gridDiv">
				<div id="busheet_permission_grid" style="width: 530px;height: 350px;border:1px solid silver;border-top:0;"></div>
			</div>
		</div>
	</div>
	
	</div>
	
</body>
</html>