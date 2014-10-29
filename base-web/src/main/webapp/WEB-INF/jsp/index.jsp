<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:message code="view.index.title" /></title>
<!-- xx zfx -->
<!-- css -->

<link type="text/css"	rel="stylesheet" href="resources/css/style.css" />
<!-- JS -->
<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="resources/js/index.js"></script>
<script type="text/javascript" src="resources/js/main.js"></script>
<script src="resources/js/highcharts/highcharts.js"></script>
<script src="resources/js/highcharts/modules/exporting.js"></script>
<script type="text/javascript" src="resources/js/message_box.js"></script>
<script type="text/javascript" src="resources/js/Map.js"></script>
<script type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/pos_order_highcharts.js"></script>
<script type="text/javascript" src="resources/js/rotate/jQueryRotate.2.2.js"></script>
</head>

<body class="skin-main skin_sakura ua-firefox ua-animation" style="min-height:100%;overflow: hidden;">
<script type="text/javascript">
	function validate(){
		var logoff=false;
		$.ajax({
			url:"validateLogoff.jhtml",
			async:false,
			type:"POST",
			success:function(result){
				logoff=result.value;
			}
		});
		return logoff;
	}
	var a = validate();
	if(a){
	}else{
		window.location.href='/groot/welcome.jhtml';
	}
	var contextPath = "${ctx}";
   	$(function() {
         $("#changePassword").bind("click",function(){
     		$("#password").toggle(); 
        }); 
    	$("#update").click(function(){
    		var oldPassword = $("input[name=oldPassword]").val();
    		var newPassword = $("input[name=newPassword]").val();
    		if(oldPassword==''||newPassword==''){
    			showMessage("新旧密码不能为空！");
    			setTimeout("hideMessage()", 5000);
    			return;
    		}
    		
    		if(/[^\a-\z\A-\Z0-9\!\@\#\$\%\^\&\*\(\)\_\-\+\=\>\?\<]/g.test(newPassword)){
				showMessage("密码格式错误，只允许输入字母、数字及以下特殊符号!@#$%^&*()_-=+<>?");
    			setTimeout("hideMessage()", 5000);
				return;
			}
    		
    		if(newPassword.length<6 || newPassword.length>16){
    			showMessage("密码长度限制6-16位");
    			setTimeout("hideMessage()", 5000);
				return;
    		}
    		
    		
    		$.ajax({
    			url:contextPath + "/updateUserPassword.jhtml",
    			type:"POST",
    			data:{"oldPassword":oldPassword,"newPassword":newPassword},
    			success: function(result){
    				showMessage(result.content);
    				setTimeout("hideMessage()", 5000);
    				if(result.isSuccess){
    					$("#password").css("display","none");
    					$("input[name=oldPassword]").val("");
        				$("input[name=newPassword]").val("");
    				}else{
    					
    				}
    			}
    		});
    	});
    	$("#cancel").click(function(){
    		$("#password").css("display","none");
    	});
	});    

</script>
    	<div class="frame-top">
        	<div id="dvTop" class="WB3-gTop">
            	<div class="nV mP skin-logo"  style="width: 156px; height: 38px; border: 1px solid black;" >
                	<!-- <h1 class="skin-logo-img">
                    	<a hidefocus="hidefocus" id="lnkHome"  href="javascript:void(0)">
                        	<img border="0" id="imgLogo" width="180px" class="gLogo" alt="手机收单内部管理系统" src="resources/images/logo.png" />
                        </a>                   
                    </h1> -->
                </div>
                
                <div class="mO">
                	<div class="jH g-addr">
                    	<em class="g-addr-email">${systemUser.roleId}:${systemUser.loginName}</em>                   
                    </div>
                    <div class="jG">
                    	<span id="_js4_component_63_63" style="z-index: 140;">
                        	<span class="fS"> | </span>
                            <a class="gy" href="logout.jhtml" id="logout" >退出</a>
                            <span class="fS"> | </span>
                            <a class="gy" id="changePassword"  style="cursor: pointer;">修改密码</a>
                            <div class="change-password" id="password">
								<ul>
									<li>旧密码:<span style="margin-left:3px"><input type="password" name="oldPassword" size="22" class="input-border-color" style="padding: 4px"/></span></li>
									<li>新密码:<span style="margin-left:3px"><input type="password" name="newPassword" size="22" class="input-border-color"  style="padding: 4px"/></span></li>
									<li  style="text-align:center"><a class="bootstrap-button" id="update" >修改</a><a class="bootstrap-button" id="cancel" style="margin-left: 20px;">取消</a></li>
								</ul>
							</div>
                        </span>
                    </div>
                </div>
            </div>
            <div id="tab_top_vector" class="WB3-gTop-tabs" style="min-width:600px;">
            	<ul id="tab_top_vector_ul">
                	<li style="width: 35px;" class="WB3-gTop-tabs-item fn-animation-slideIn fn-animation-slideIn-lr 
                    			fn-animation-showing WB3-gTop-tabs-item-on" id="home_li">
						<a title="首页" href="javascript:void(0)" class="WB3-gTop-tabs-link iZ">
							<b class="WB3-gTop-tabs-rc WB3-gTop-tabs-rc-1"></b>
							<b class="WB3-gTop-tabs-rc WB3-gTop-tabs-rc-2"></b>
							<span id="text">首页</span>
						</a>							
					</li>                
                </ul>           
            </div>
        </div>
        <div style="position: absolute;width:100%;top:72px;bottom: 0;">
    	<div id="navtree" class="frame-side">
			
        </div>
        <div style="position: absolute;border-left: 1px solid #8BAABF;top:0;bottom: 0;left:190px;"></div>
        <div class="frame-main">
        	<div id="dvContainer" class="frame-main-inner">
        	    <div id ="home_content">
		        	<div>
		        		<div style="background: #DBEAF9;font-size: 14px;width: 98%;padding: 3px 5px;font-family: 'Microsoft Yahei',verdana;float:left;">
		        			<span style="float:left;display:inline;">
		        				&nbsp;&nbsp;系统版本号:&nbsp;<span id="systemVersion" style="color:#AA4643"></span>
		        			</span>
		        			<span id="closeTop" style="float:right;cursor:pointer;display:inline;margin-right: 6px;font-weight: bold;">x</span>
		        		</div>
		        		<%-- <shiro:hasPermission name="HomePage:posOrderChar"> --%>
		        			<div style="width: 98%;z-index:500;" id="highchartPosOrder">
		        			<div id="closeChartPosOrder" style="float: right;"><span style="cursor:pointer;font-weight: bold;font-size: 14px;">x</span></div>
		        			<div id="posOrderChar" style="clear:right;min-height: 500px;"></div>
		        			</div>
		        		<%-- </shiro:hasPermission> --%>
		        		
		        	</div>         
 				</div>
				<div id ="main_content" class="oi" style='display:none;'>
 					
 				</div>
        		
        </div>
        </div>  
        </div>  
        <div id="mask" class="nui-mask" style="display: none;"></div>
  <!--   </div> -->
    
</body>
</html>
