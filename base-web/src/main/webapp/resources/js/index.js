var menuArray = [];
var menuItemArray=[];
function initMenu(){
	  
	$.ajax({
		  url: contextPath +"/getMenuListByUserId.jhtml?r="+Math.random(),
		  async:false,
		  data:{parentId:'0'},
		  type:"POST",
		  success: function(result) {
			  menuArray=result.menuContent;
			  menuItemArray = result.menuItemContent;
		  }
		 });

	var mainMenu =$("<div class='od' >");
 	$.each(menuArray,function(k,v){
 		var mainMenuContent=$("<div class='ni'>");
 		var menuText=$("<div class='hK nj'>");
 		var menuItemsText=$("<a hidefocus='hidefocus' class='jM' href='javascript:void(0)' id="+v.id+" menuId="+v.menuCode+">" +
 				"<span class='tree-item-symbol'><b class='icon-fold'></b></span><span style='margin-left:5px;'>"+v.menuName+"</span>&nbsp;</a>");
 		menuText.append(menuItemsText);
 		mainMenuContent.append(menuText);
 		mainMenu.append(mainMenuContent);
 		$("#navtree").append(mainMenu);
 		var menuItem=$("<ul class='ni' style='display:none;position:relative;'>");
 		
 		
 		menuItemArrays=menuItemContentByParentId(menuItemArray,v.id);
 		 $.each(menuItemArrays,function(k,v){
			menuItemContent=$("<li class='nj' id="+v.id+"></li>");
			menuItemContentLink=$("<a hidefocus='hidefocus' class='nh' href='javascript:void(0)' menuAction="+v.menuAction+" " +
					"id="+v.id+" title="+v.menuName+" menuId="+v.menuCode+" menuPermission="+v.permissionKey+"></a>");
			menuItemText=$("<span style='margin-left:10px;'>"+v.menuName+"</span>");
			menuItemContentLink.append(menuItemText);
			menuItemContent.append(menuItemContentLink);
			menuItem.append(menuItemContent);
			menuItem.insertAfter(menuText);
		});
	});
 	$(".jM").toggle(
 			function(){$(this).find("span").find("b").removeClass("icon-fold").addClass("icon-unfold");$(this).parent().next().toggle(300);},
 			function(){$(this).find("span").find("b").removeClass("icon-unfold").addClass("icon-fold");$(this).parent().next().toggle(300);}
 			);
}

function menuItemContentByParentId(menuItemArray , menuCode){
	var menuItemByParentId=[];
	 $.each(menuItemArray,function(k,v){
			if(v.parentId==menuCode){
				menuItemByParentId.push(v);
			}
		});
	 return menuItemByParentId;
}
$(function (){
	$("#navtree").hover(function(){$("#navtree").addClass('Y');},function(){$("#navtree").removeClass('Y');});
	initMenu();
	/*$("#pending_audit_merchant").click(function() {
		parent.menuClickFun(null, getRandomString(32), "商户入网审核","network_audit_manager.jhtml?home=a&&sheetCode=100006");
	});*/
	/*$("#channel_fail_merchant").click(function() {
		parent.menuClickFun(null, getRandomString(32), "商户同步管理","channel_fail_merchant.jhtml");
	});*/
	/*if (checkPermission("HomePage:pendingAudit") == true) {
		getpendingAuditSum();
	}*/
	/*if (checkPermission("HomePage:channelFail") == true) {
		getChannelFailSum();
	}*/
});
//pending_audit_sum
function getpendingAuditSum() {
	$.ajax({
		url:contextPath + "/getWaitAuditMerchantNum.jhtml",
		type:"POST",
		async:false,
		dataType: "json",
		success: function(result) {
			$("#pending_audit_sum").text(result);
		}
	});
}
//channel_fail_sum
/*function getChannelFailSum() {
	$.ajax({
		url:contextPath + "/getWaitSynchroMerchantNum.jhtml",
		type:"POST",
		async:false,
		dataType: "json",
		success: function(result) {
			$("#channel_fail_sum").text(result);
		}
	});
}*/

function checkPermission(permission) {
	var flag = false;
	$.ajax({
		url: contextPath+"/isPermitted.jhtml?permissionName=" + permission + "&r="+Math.random(),
		async:false,
		success: function(data) {
			flag = data;
		}
	});
	return flag;
}