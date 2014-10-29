<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="resources/css/style.css" />
<link type="text/css" rel="stylesheet" href="resources/js/hkrtframework-pagination/css/hkrtframework-pagination.css" />
<link type="text/css" rel="stylesheet" href="resources/js/hkrtframework-grid/css/hkrtframework-grid.css" />
<link type="text/css" rel="stylesheet" href="resources/js/jQuery-Timepicker-Addon-master/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" href="resources/js/jquery-ui-1.10.3.custom/css/ui-lightness/jquery-ui-1.10.3.custom.min.css" type="text/css" />
<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui-1.10.3.custom/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="resources/js/jQuery-Timepicker-Addon-master/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript" src="resources/js/jQuery-Timepicker-Addon-master/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="resources/js/jQuery-Timepicker-Addon-master/i18n/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/Map.js"></script>
<script type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/order_payment.js"></script>
<script type="text/javascript" src="resources/js/rotate/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="resources/js/hkrtframework-pagination/hkrtframework-pagination.js"></script>
<script type="text/javascript" src="resources/js/hkrtframework-grid/hkrtframework-grid.js"></script>
<script type="text/javascript" src="resources/js/hkrtframework-layer/hkrtframework-layer.js"></script>
<style type="text/css">
.bootstrap label {
	width: 100px;
	display: inline-block;
	text-align: right;
}
.ui-timepicker-div dl,.ui-timepicker-div dd,.ui-timepicker-div dt{
/* 	float: none; */
	margin-top:0px;
}
</style>
</head>
<body>
	<script type="text/javascript">
		var contextPath = "${ctx}";
	</script>
	<div>
		<div style="clear:left">
			<dl >
				<dt>订单号:</dt>
				<dd>
					<input  id="orderNo"  name="orderNo"  class="pop bootstrap-text"  style="width: 100px"/>
				</dd>
			</dl>
			<dl style="margin-left: 5px;">
				<dt>订单创建时间从:</dt>
				<dd>
					<input id="createStartTime" name="createStartTime" class="pop bootstrap-text"
						readonly style="width: 125px"  />
				</dd>
			</dl>
			<dl>
				<dt>到:</dt>
				<dd>
					<input id="createEndTime" name="createEndTime"  class="pop bootstrap-text"
						readonly style="width: 125px"/>
				</dd>
			</dl>
			
		</div>
		<div style="clear: left;">
				<input id="search" type="button" class="bootstrap-button" value="查询" />
				<a id="reset" href="javascript:void(0);">清除条件</a>
			</div>
		<div style=" overflow: auto; margin-top: 5px;" id="pos_id">
			<div id="order_payment_grid" style="width: 1205px;"></div>
			<div id="order_payment_pagination" style="width: 1203px;"></div>
		</div>
	</div>
</body>
</html>