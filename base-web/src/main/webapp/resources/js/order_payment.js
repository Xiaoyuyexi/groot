var pagination;
var columns = [];
var grid;

var paginationOptions;
var gridOptions;
var currentPageNumber = 1;
var maxsize = 5000;
var dataSize = 0;
var status_cde_map = new Map();
var value;
$(function(){
	// 时间插件
	$('#createStartTime').datetimepicker({
		controlType: 'select',
		timeFormat: 'HH:mm:ss'
	});
	$('#createEndTime').datetimepicker({
		controlType: 'select',
		timeFormat: 'HH:mm:ss'
	});
	defaultSystemTrxTime();
	// 分页查询
	$("#search").bind("click", function() {
		currentPageNumber = 1;
		getOrderPaymentListForPage();
	});
	$("#reset").bind("click",function(){
		$(':input').not(':button, :submit, :reset, :hidden, :checkbox').val('');
		$.each($("select"),function(i,k){
			$(k).get(0).selectedIndex=0;
		});
		$("input[type='checkbox']").removeAttr("checked");
	});
	columns.push( 
			{id: "orderNo", name: "订单号", width:"140px"},
			{id: "orderType", name: "订单类型", width:"100px"},
			{id: "cardNo", name: "银行卡号", width:"150px"},
			{id: "trxAmt", name: "交易金额", moneyFormat:true, width:"100px"},
			{id: "mobile", name: "电话号", width:"120px"},
			{id: "status", name: "交易状态", formatter: status_cde_map,width:"85px"},
			{id: "createTime", name: "创建时间", width:"150px"},
			{id: "completeTime", name: "完成时间", width:"150px"},
			{id: "note", name: "备注"}
	);
	//分页
	gridOptions = {};
	gridOptions.showTopPanel=true;
	gridOptions.column=columns;
	grid = new HkrtFramework.Grid("#order_payment_grid",null,null,gridOptions);
	grid.setColumns(columns);
	
});

function getOrderPaymentListForPage() {
	showMessageOfParent("正在载入...");
	var orderNo = $("#orderNo").val();
	var createStartTime = $("#createStartTime").val();
	var createEndTime = $("#createEndTime").val();
	$.ajax({
		url : contextPath + "/getOrderPaymentListForPage.jhtml",
		type : "POST",
		data: {
			"currentPage":currentPageNumber,
			"orderNo":orderNo.trim(),
			"createStartTime":createStartTime.trim(),
			"createEndTime":createEndTime.trim(),
		},
		success : function(result) {
			
			$.each(result.list, function(k, v) {
				 result.list[k].orderNo=$("<a  href='javascript:void(0)'>"+v.orderNo+"</a>").bind("click",function(){
					 parent.menuClickFun(null, v.id+"pos", "查看详情","order_info.jhtml?id="+v.id);
				 });
				
				 if(v.orderType=='10A'){
					 result.list[k].orderType = "交易订单";
				 }else{	
					 result.list[k].orderType = "其他订单";
				 }
				 
				 if(v.status=='10A'){
					 result.list[k].status = "正常状态";
				 }else{	
					 result.list[k].status = "其他状态";
				 }
				 
			});
			
			$exportA=$("<a style='cursor:pointer;margin-left:10px;float:left;'>导出</a>").bind("click",function(){
				exportOrderPayment();
			});
			if(grid.getTopButton("export")==null){
				grid.setTopButton("export",$exportA,'OrderPayment:export');
			}
		    grid.setData(result.list);
		     
		    if (pagination == null||pagination==undefined) {
		    	var options = {callback:paginationCallBack};
		    	pagination = new HkrtFramework.Pagination("#order_payment_pagination",options);
		    } else {
		    	pagination.setCurrentPageNumber(currentPageNumber);
		    }

	    	pagination.setPageCount(result.pages);
		    pagination.setCount(result.count);
		    dataSize = result.count;
		    hideMessageOfParent();
		}
	});
}
function getMaxSize(){
	$.ajax({
		url : contextPath + "/getPosOrderExportMaxSize.jhtml",
		type : "POST",
		success : function(result) {
			maxsize = result;
		}
	});
}
function exportOrderPayment(){
	if(dataSize > 5000){
		 showMessageOfParent("导出失败,导出数据最多"+maxsize+"条!");
		 setTimeout("hideMessageOfParent()", 4000);
		 return;
	}
	var params = $(".pop").serialize();
	showMessageOfParent("正在导出......");
	window.location.href=contextPath + "/exportOrderPayment.jhtml?"+params;
	setTimeout("hideMessageOfParent()", 4000);
}


function channelName(){
	$("#bankCode").empty();
	$("#bankCode").append("<option value=''>请选择</option>");
	$.ajax({
		url : contextPath + "/getAllHkrtBankChannel.jhtml",
		type : "POST",
		success : function(result) {
			$.each(result, function(k, v) {
				var str = "<option value='"+v.bankCode+"'>"+v.channelName+"</option>";
				$("#bankCode").append(str);
			});
		}
	});
}
//风控标示
function paginationCallBack() {
	if (pagination != null || pagination != undefined) {
		currentPageNumber = pagination.getCurrentPageNumber();
	}
	getOrderPaymentListForPage();
}
String.prototype.trim= function(){  
    // 用正则表达式将前后空格  
    // 用空字符串替代。  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
};


//设置默认时间
function defaultSystemTrxTime(){
	var start= new Date();
	var end = new Date(); 
	start.setDate(start.getDate()-2); 
	$('#createStartTime').val(start.getFullYear()+"-"+(start.getMonth()+1)+"-"+start.getDate()+" 00:00:00");
	$('#createEndTime').val(end.getFullYear()+"-"+(end.getMonth()+1)+"-"+end.getDate()+" 23:59:59");
}

function allCheckbox(check){
	if(check){ 
		$("input[name='checkBoxStatusA']").each(function(){
			$(this).parent().parent().parent().addClass("change-li-background");
			this.checked=true;
		//	$("table").find("tr:gt(0)").addClass("change-li-background");
			}); 
	}else{ 
		$("input[name='checkBoxStatusA']").each(function(){this.checked=false;$("table").find("tr:gt(0)").removeClass("change-li-background");}); 
	} 
};
function allCheckboxfalse(check,obj){
	if(check){
		$(obj).parent().parent().parent().addClass("change-li-background");
	}else{
		$(obj).parent().parent().parent().removeClass("change-li-background");
	}
	if(!check){ 
		$("input[id='allCheckbox']").each(function(){this.checked=false;}); 
	} 
};
