<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="lookup" uri="/lookup-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="resources/css/style.css" />
<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script  type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/rotate/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="resources/js/hkrtframework-image/hkrtframework-image.js"></script>
<style type="text/css">
#baseInfoContent label{
width: 120px;
text-align: right;
display: inline-block;
vertical-align:top;
}
#baseInfoContent span{
width: 200px;
margin-left:20px;
display: inline-block;
vertical-align:top;
}
</style>
</head>
<body  class="overflow">
	<script type="text/javascript">
	 var contextPath = "${ctx}";
    </script>
	<div>
		<div class="details change-li-background" id="baseInfoContent" style="padding:10px 0 0 10px;line-height: 30px;float: left;">

			<label>交易订单号:</label><span>${pos.orderNo }</span>
			<label>交易类型:</label><span><lookup:key typeCode="10311001" cdeKey="${pos.orderType }"/> </span><br>
			<label>交易金额:</label><span>
				<c:if test="${pos.trxAmt >= 0 }">
					<fmt:formatNumber value="${pos.trxAmt}" pattern="#,##0.00#" />
				</c:if>
				<c:if test="${pos.trxAmt < 0 }">
					<font color='red'><fmt:formatNumber value="${pos.trxAmt * -1}" pattern="#,##0.00#" /></font>
				</c:if>
			</span>
			<label>银行卡号:</label><span>${pos.cardNo }</span><br>
			<label>交易状态:</label><span><lookup:code typeCode="10661601" cdeCode="${pos.status }"/></span><br>
			<label>系统交易完成时间:</label><span>${pos.completeTime }</span>
			<label>创建时间:</label><span>${pos.createTime }</span><br>
			<label>备注:</label><span>${pos.note }</span><br/>
			<input type="hidden" name="id" id="primaryId" class="posInfo" value="${pos.id}"/>
		</div>
	</div>
</body>
</html>