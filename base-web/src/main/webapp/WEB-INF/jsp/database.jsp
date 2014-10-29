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
<link type="text/css"	rel="stylesheet" href="resources/css/style.css"/>
<link type="text/css"	rel="stylesheet" href="resources/js/hkrtframework-grid/css/hkrtframework-grid.css"/>

<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script  type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/Map.js"></script>
<script type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/hkrtframework-grid/hkrtframework-grid.js"></script>
<script type="text/javascript" src="resources/js/database.js"></script>
</head>
<body class="overflow">
<script type="text/javascript">
	 var contextPath = "${ctx}";
    </script>
<div class="details"  id="databaseContent">
				<div id="database_query">
				<textarea class="bootstrap-textarea" rows="5" cols="100" id="querysql" style="width:700px;height:100px;"></textarea>
				<br/>
					<input type="button" value="分页执行" id="process" style="margin-left: 500px; width:105px;" class="bootstrap-button"/>
					<input type="button" value="查询全部执行" id="processAll" style="margin-left: 5px;" class="bootstrap-button"/>
				</div>
				
				<div id="database_process">
				</div>
		 		<div id="database_grid"></div>
		 		<div id="database_view"><span id='view'></span><span id="total"></span><a id='next' href='javascript:nextView()'>next</a></div>
		</div>
</body>
</html>