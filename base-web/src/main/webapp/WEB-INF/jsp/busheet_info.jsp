<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="resources/css/style.css" />
<link rel="stylesheet" href="resources/js/ztree/css/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="resources/js/upload/ajaxfileupload.js"></script>
<script type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/busheet_manager.js"></script>
<script type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="resources/js/ztree/jquery.ztree.exedit-3.5.js"></script>

</head>
<body class="overflow">
	<script type="text/javascript">
	 var contextPath = "${ctx}";
    </script>
	<div class="bootstrap" id="new-agent">
	
		<ul class="agent-details-change" style="height: 30px;width: 400px;">
		<li id="baseInfo" class="change-li-background">基本信息</li>
		<!-- <li id="teminalInfo">设备信息</li> -->
		<li id="imageInfo">图片信息</li>
		<!-- <li id="SalesmanInfo">业务员</li> -->
		</ul>
		<div class="details change-li-background" id="baseInfoContent">
			<input type="hidden" name="id" id="sheetId" class="agentInfo" value="${sheet.id}"/>
			<label style="width:40px;">名称:</label>
			<input type= "text" name="name" id="sheetName" class="agentInfo input-medium" value="${sheet.name}"/>
			<label style="width:60px;">CODE:</label>
			<input type= "text" name="sheetCode" class="agentInfo input-medium" value="${sheet.sheetCode}"/>
			<div style="margin-left: 100px;margin-top: 5px;">
				<input type ="button" class="btn btn-primary" id="save" value="保存" /> 
				<input type ="button" class="btn btn-primary" id="reset" value="清空" />
				<span class="warn"></span>
			</div>
			<br>
		</div>
		<div class="details"  id="teminalInfoContent"style="display: none;">
			<div class="searchDetails" style="display: block;">
						<div id="agent_manager_grid"></div>
						<div id="agent_manager_pagination"></div>
               </div>
		</div>
		<div class="details" id="imageInfoContent" style="display: none;height: 620px;width:1260px;">
			<div style="width:400px;height: 300px;border:1px solid #8BAABF;float: left;margin-left: 10px;">
				<div style="width:400px;height: 270px;">
					<img alt="" width="400px" height="270px" src="<c:out value="${amsAgent.businessLicenseImage==null ? 'resources/images/default.gif' : amsAgent.businessLicenseImage}"></c:out>"/>
				</div>
				<div>
					<label style="width:150px">营业执照正本或副本正面照</label>
					<a id=""  onclick="document.getElementById('businessLicenseImage').click();" style="cursor: pointer">编辑图片</a><br>
					<input style="display:none" id="businessLicenseImage" type="file" size="20" name="file" onchange="imageUpload(this)"/>
				</div>
			</div>
			<div style="width:400px;height: 300px;border:1px solid #8BAABF;float: left;margin-left: 10px;">
				<div style="width:400px;height: 270px;">
					<img alt="" width="400px" height="270px" src="<c:out value="${amsAgent.officeEnvironmentImage1==null ? 'resources/images/default.gif' : amsAgent.officeEnvironmentImage1}"></c:out>"/>
				</div>
				<div>
					<label>内部办公环境照片</label>
					<a id=""  onclick="document.getElementById('officeEnvironmentImage1').click();" style="cursor: pointer">编辑图片</a><br>
					<input style="display:none" id="officeEnvironmentImage1" type="file" size="20" name="file" onchange="imageUpload(this)"/>
				</div>
			</div>
			<div style="width:400px;height: 300px;border:1px solid #8BAABF;float: left;margin-left: 10px;">
				<div style="width:400px;height: 270px;">
					<img alt="" width="400px" height="270px" src="<c:out value="${amsAgent.officeEnvironmentImage2==null ? 'resources/images/default.gif' : amsAgent.officeEnvironmentImage2}"></c:out>"/>
				</div>
				<div>
					<label>内部办公环境照片</label>
					<a id=""  onclick="document.getElementById('officeEnvironmentImage2').click();" style="cursor: pointer">编辑图片</a><br>
					<input style="display:none" id="officeEnvironmentImage2" type="file" size="20" name="file" onchange="imageUpload(this)"/>
				</div>
			</div>
			<div style="width:400px;height: 300px;border:1px solid #8BAABF;float: left;margin-left: 10px;margin-top: 5px">
				<div style="width:400px;height: 270px;">
					<img alt="" width="380px" height="270px" style="margin-left: 2px;margin-top: 2px" src="<c:out value="${amsAgent.officeEnvironmentImage3==null ? 'resources/images/default.gif' : amsAgent.officeEnvironmentImage3}"></c:out>"/>
				</div>
				<div>
					<label>内部办公环境照片</label>
					<a id=""  onclick="document.getElementById('officeEnvironmentImage3').click();" style="cursor: pointer">编辑图片</a><br>
					<input style="display:none" id="officeEnvironmentImage3" type="file" size="20" name="file" onchange="imageUpload(this)"/>
				</div>
			</div>
			<div style="width:400px;height: 300px;border:1px solid #8BAABF;float: left;margin-left: 10px;margin-top: 5px">
				<div style="width:400px;height: 270px;">
					<img alt="" width="400px" height="270px" src="<c:out value="${amsAgent.corporateIdentityFtImage==null ? 'resources/images/default.gif' : amsAgent.corporateIdentityFtImage}"></c:out>"/>
				</div>
				<div>
					<label>法人身份证正面</label>
					<a id=""  onclick="document.getElementById('corporateIdentityFtImage').click();" style="cursor: pointer">编辑图片</a><br>
					<input style="display:none" id="corporateIdentityFtImage" type="file" size="20" name="file" onchange="imageUpload(this)"/>
				</div>
			</div>
			<div style="width:400px;height: 300px;border:1px solid #8BAABF;float: left;margin-left: 10px;margin-top: 5px">
				<div style="width:400px;height: 270px;">
					<img alt="" width="400px" height="270px" src="<c:out value="${amsAgent.corporateIdentityBdImage==null ? 'resources/images/default.gif' : amsAgent.corporateIdentityBdImage}"></c:out>"/>
				</div>
				<div>
					<label>法人身份证反面</label>
					<a id=""  onclick="document.getElementById('corporateIdentityBdImage').click();" style="cursor: pointer">编辑图片</a><br>
					<input style="display:none" id="corporateIdentityBdImage" type="file" size="20" name="file" onchange="imageUpload(this)"/>
				</div>
			</div> 
		</div>
		<div class="details"  id="salesmanContent" style="display: none;">
		 		<div id="agent_Salesman_manager_grid"></div>
		</div>
	</div>
</body>
</html>