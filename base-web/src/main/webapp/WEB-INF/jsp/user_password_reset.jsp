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

<script  type="text/javascript" src="resources/js/slick/lib/jquery-1.7.min.js"></script>
<script  type="text/javascript" src="resources/js/util.js"></script>
<script  type="text/javascript" src="resources/js/message_box.js"></script>
<script  type="text/javascript" src="resources/js/user_password_reset.js"></script>
<style type="text/css">
label{
	margin-top: 15px;
	display: inline-block;
	width:100px;
	text-align: right;
}
span{
	display: inline-block;
	width:200px;
	text-align: left;
	margin-left:10px;
}
</style>
	</head>
	<body class="overflow">
	<script type="text/javascript">
	 var contextPath = "${ctx}";
    </script>
    	<shiro:hasPermission name="UserPassword:update">
    	<div>
    		<font size="3"><b>运营后台登陆密码重置</b></font>
    		<div style="border-top: 1px solid #DEDEDE;margin: 7px 0;"></div>
			<div class='bootstrap'>用户名：
				<input type="text" class="bootstrap" style="width: 150px" id="userName" name="userName" />
				<input id="userSearch" type="button" class="bootstrap-button" value="查询"/>
				<a id="reset" href="javascript:void(0);">清除条件</a>
			</div>
			<div id="userInfo" style="display: none;">
				<label>用户名称:</label><span id="userNameSpan"></span><br/>
				<label>用户状态:</label><span id="userStatusSpan"></span><br/>
				<label>用户角色:</label><span id="userRoleSpan"></span><br/>
				<label>用户密码:</label><input type='hidden' id='userId' />
				<span><input name='userPassword' class="bootstrap-text" style="margin-left:-4px;" type='text' id='userPassword'/></span><br/>
				<label style="margin-left:84px;margin-top: 5px;"><a onclick='passwordReset();' class='bootstrap-button' href='javascript:void(0);'>密码重置</a></label>
			</div>
		</div>
		</shiro:hasPermission>
		
		<%-- <shiro:hasPermission name="AgentPassword:update">
		<div style="margin-top: 15px;">
    		<font size="3"><b>代理商密码重置</b></font>
    		<div style="border-top: 1px solid #DEDEDE;margin: 7px 0;"></div>
			<div class='bootstrap'>代理商简称：
				<input type="text" class="bootstrap" style="width: 150px" id="agentShortName" name="agentShortName" />
				<input id="agentSearch" type="button" class="bootstrap-button" value="查询"/>
				<a id="agentReset" href="javascript:void(0);">清除条件</a>
			</div>
			<div id="agentInfo" style="display: none;">
				<label>代理商简称:</label><span id="agentShortNameSpan"></span><br/>
				<label>联系人:</label><span id="agentLinkPersonSpan"></span><br/>
				<label>联系人手机号:</label><span id="agentLinkPhoneSpan"></span><br/>
				<input type='hidden' id='id'/> <input type='hidden' id='email'  /> <input type='hidden' id='linkPhone'  />
				<label style="margin-left:110px;"><a onclick='agentReset();' class='bootstrap-button' href='javascript:void(0);'>密码重置</a></label>
			</div>
		</div>
		</shiro:hasPermission> --%>
		
		<%-- <shiro:hasPermission name="MerchantPassword:update">
		<div style="margin-top: 15px;">
    		<font size="3"><b>商户操作员密码重置</b></font>
    		<div style="border-top: 1px solid #DEDEDE;margin: 7px 0;"></div>
			<div class='bootstrap'>商户手机号： <input type="text" class="bootstrap" style="width: 150px" id="merchantPhone" name="merchantPhone" />
								         操作员编号： <input type="text" class="bootstrap" style="width: 150px" id="opratorNo" name="opratorNo" />
				<input id="merchantSearch"  type="button" class="bootstrap-button" value="查询"/>
				<a id="merchantReset" href="javascript:void(0);">清除条件</a>
			</div>
			<div id="merchantInfo" style="display: none">
				<label>商户名称:</label><span id="merchantNameSpan"></span><br/>
				<label>商户手机号:</label><span id="merchantPhoneSpan"></span><br/>
				<label>商户邮箱:</label><span id="merchantEmailSpan"></span><br/>
				<label>操作员名称:</label><span id="opratorNameSpan"></span><br/>
				<input type="hidden" id="opratorId"/>
				<label style="margin-left:110px;"><a href="javascript:void(0);" class="bootstrap-button" id="merchantPasswordReset">密码重置</a></label>
			</div>
		</div>
		</shiro:hasPermission> --%>
	
		<%-- <shiro:hasPermission name="SalemanPassword:update">
		<div style="margin-top: 15px;">
    		<font size="3"><b>业务员密码重置</b></font>
    		<div style="border-top: 1px solid #DEDEDE;margin: 7px 0;"></div>
			<div class='bootstrap'>手机号：
				<input type="text" class="bootstrap" style="width: 150px" id="phone" name="phone" />
				<input id="salesmanSearch" type="button" class="bootstrap-button" value="查询"/>
				<a id="salesmanReset" href="javascript:void(0);">清除条件</a>
			</div>
			<div id="salesmanInfo" style="display: none;">
				<label>名称:</label><span id="salemanNameSpan"></span><br/>
				<label>手机号:</label><span id="salemanPhoneSpan"></span><br/>
				<label>邮箱:</label><span id="salemanEmailSpan"></span><br/>
				<input type='hidden' id='salemanId' />
				<input type='hidden' id='salemanName'/>
				<input type='hidden' id='salemanEmail'/>
				<input type='hidden' id='salemanPhone'/>
				<label style="margin-left:110px;"><a onclick='salesmanReset();' class='bootstrap-button' href='javascript:void(0);'>密码重置</a></label>
			</div>
		</div>
		</shiro:hasPermission> --%>
    </body>    
</html>