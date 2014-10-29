<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css"	rel="stylesheet" href="resources/css/style.css"/>
<link rel="stylesheet" href="resources/js/slick/slick.grid.css" type="text/css"/>
<link rel="stylesheet" href="resources/js/slick/css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css"/>
<link type="text/css" href="resources/js/slick/css/examples.css" rel="stylesheet"/>
<link rel="stylesheet" href="resources/js/slick/controls/slick.pager.css" type="text/css"/>
<script  type="text/javascript" src="resources/js/slick/lib/jquery-1.7.min.js"></script>
<script  type="text/javascript" src="resources/js/util.js"></script>
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
<script  type="text/javascript" src="resources/js/slick/slick.dataview.js"></script>
<script  type="text/javascript" src="resources/js/message_box.js"></script>
<script  type="text/javascript" src="resources/js/lookup_type_manager.js"></script>
<script  type="text/javascript" src="resources/js/lookup_cde_manager.js"></script>
	</head>
	<body>
	<script type="text/javascript">
	 var contextPath = "${ctx}";
    </script>
     <div style="overflow: auto;width:98%;position: relative;height: 406px;">
    <div style="width: 1195px;">
    <input type="hidden" id="cdeId"/>
		<div style="float: left;">
			<div id="lookUpTypeManagerGrid" style="width:310px;height:360px;border:1px solid silver;border-top:0;"></div>
		</div>
		<div style="float: left;margin-left: 10px;visibility: hidden" id="lookUpCdeManagerGridDiv">
			<div id="lookUpCdeManagerGrid" style="width:750px;height:360px;border:1px solid silver;border-top:0;"></div>
		</div>
	</div>
	</div>
    </body>    
</html>