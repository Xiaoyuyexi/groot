/**
 * @license
 * (c) 2013-2014 Chenliguo
 *
 * Distributed under MIT license.
 * All rights reserved.
 *
 * HkrtFramework v1.0
 *
 */
if (typeof jQuery === "undefined") {
  throw "hkrtframework requires jquery module to be loaded";
}
(function ($) {
	
	$.extend(true, window, {
		HkrtFramework: {
			Pagination: HkrtFrameworkPagination
		}
	});

	 function HkrtFrameworkPagination(container, options) {
		var $container = $(container);
		var id=$container.attr("id");
		
		var defaults = {
			width:"1100px",
			pageCount:10,
			totalShow:"",
			callback:function(){return false;}
		}
		
		function setCurrentPageNumber(currentPage) {
			$("#"+id+"_page_no").text(currentPage);
		};
		function getCurrentPageNumber() {
			return parseInt($("#"+id+"_page_no").text());
		};
		function setPageCount(pageCount) {
			$("#"+id+"_page_count").text(pageCount);
		};
		function setCount(Count) {
			$("#"+id+"_count").text(Count);
		};
		function init() {
			
			options = $.extend({}, defaults, options);
			if ($(container).length < 1) {
				throw new Error("HkrtFrameworkPagination requires a valid container, " + container + " does not exist in the DOM.");
			}
		//	$container.css('width',options.width);
		//	$container.css('height',options.height);
			$container.addClass("pagination-background");

		//	var $pageNum=$("<input type='hidden' name='"+id+"_hkrtFrameworkPageNum' id='"+id+"_pageNum' value='1'/>").appendTo($container);
			//每页数据量
		//	var $pageSize=$("<input type='hidden' name='"+id+"_hkrtFrameworkPageSize' id='"+id+"_pageSize' value="+options.pageSize+">").appendTo($container);
			//当前页和总页数
			var $curAndCountPage=$("<span style='float:left;'>第<span id='"+id+"_page_no'>1</span>页/共<span id='"+id+"_page_count'>"+options.pageCount+"</span>页&nbsp;</span>").appendTo($container);
			//第一页
			var $firstPage=$("<span style='float:left;'><a href='javascript:void(0)' id='"+id+"_firstPage'>首页</a>&nbsp;</span>").appendTo($container);
			//前一页
			var $prePage=$("<span style='float:left;'><a href='javascript:void(0)' id='"+id+"_prev'>上一页</a>&nbsp;</span>").appendTo($container);
			//下一页
			var $nextPage=$("<span style='float:left;'><a href='javascript:void(0)' id='"+id+"_next'>下一页</a>&nbsp;</span>").appendTo($container);
			//末页
			var $lastPage=$("<span style='float:left;'><a href='javascript:void(0)' id='"+id+"_lastPage'>末页</a></span>").appendTo($container);
			//总数
			var $count=$("<span style='margin-left:10px;float:left;'>共有<span id='"+id+"_count'></span>条数据</span>").appendTo($container);
			var $totalDiv=$("<div id='totalDiv'></div>");
			//实际到账总金额
			if(options.showChannelAmount){
				$("<span style='float:right;margin-right:10px;'>实际到账总金额:<span id='"+id+"_totalSettleAmount'></span></span>").appendTo($totalDiv);
			}
			//通道手续费统计
			if(options.showChannelFee){
				$("<span style='float:right;margin-right:10px;'>通道手续费统计:<span id='"+id+"_totalAllFee'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalChannelAmount){
				$("<span style='float:right;margin-right:10px;'>银行交易总金额:<span id='"+id+"_totalTradeAmount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalFeedbackFee){
				$("<span style='float:right;margin-right:10px;'>退货总手续费:<span id='"+id+"_totalFeedbackFee'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalFeedbackAmount){
				$("<span style='float:right;margin-right:10px;'>退货总金额:<span id='"+id+"_totalFeedbackAmount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalFeedbackCount){
				$("<span style='float:right;margin-right:10px;'>退货总笔数:<span id='"+id+"_totalFeedbackCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalSettleAmount){
				$("<span style='float:right;margin-right:10px;'>结算总金额:<span id='"+id+"_totalSettleAmount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalReversAmount){
				$("<span style='float:right;margin-right:10px;'>撤销总金额:<span id='"+id+"_totalReversAmount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalReversCount){
				$("<span style='float:right;margin-right:10px;'>撤销总笔数:<span id='"+id+"_totalReversCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalFee){
				//（消费手续费-退货手续费）
				$("<span style='float:right;margin-right:10px;'>消费总手续费:<span id='"+id+"_totalFee'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalAmount){
				$("<span style='float:right;margin-right:10px;'>消费总金额:<span id='"+id+"_totalAmonut'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalCount){
				$("<span style='float:right;margin-right:10px;'>消费总笔数:<span id='"+id+"_totalCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalAllFee){
				$("<span style='float:right;margin-right:10px;'>手续费总计:<span id='"+id+"_totalAllFee'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalTradeAmount){
				$("<span style='float:right;margin-right:10px;'>交易总金额:<span id='"+id+"_totalTradeAmount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalTradeAmountSum){
				$("<span style='float:right;margin-right:10px;'>退货总金额:<span id='"+id+"_totalTradeAmountSum'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalTradeCountSum){
				$("<span style='float:right;margin-right:10px;'>交易笔数总计:<span id='"+id+"_totalTradeCountSum'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalMerchantBindCount){
				$("<span style='float:right;margin-right:10px;'>商户绑定数:<span id='"+id+"_totalMerchantBindCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalTerminalBindCount){
				$("<span style='float:right;margin-right:10px;'>终端绑定数:<span id='"+id+"_totalTerminalBindCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalSalesmanCount){
				$("<span style='float:right;margin-right:10px;'>业务员数:<span id='"+id+"_totalSalesmanCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalMerchantCount){
				$("<span style='float:right;margin-right:10px;'>商户数:<span id='"+id+"_totalMerchantCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalAgentCount){
				$("<span style='float:right;margin-right:10px;'>代理商数:<span id='"+id+"_totalAgentCount'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalFreezeAmt){
				$("<span style='float:right;margin-right:10px;'>冻结金额:<span id='"+id+"_totalFreezeAmt'></span></span>").appendTo($totalDiv);
			}
			if(options.showTotalOverage){
				$("<span style='float:right;margin-right:10px;'>余额:<span id='"+id+"_totalOverage'></span></span>").appendTo($totalDiv);
			}
			if(options.totalShow!=''){
				$totalShow = $(options.totalShow);
				$totalShow.addClass("pagination-background");
				$totalDiv.appendTo($totalShow);
			}else{
				$totalDiv.appendTo($container);
			}
			/*var $totalDivShow=$("<div id='totalDivShow' class='box'></div>");
			$totalDivShow.appendTo($container);*/
//			$("#"+id+"_prev").bind("click",function(){
//				var curPage = parseInt($("#"+id+"_page_no").text());
//				if(curPage ==1){
//					return ;
//				}
//				$("#"+id+"_page_no").text(curPage-1);
//				var continues = options.callback();
//				return continues;
//			 });
//			$("#"+id+"_next").bind("click",function(){
//				var curPage = parseInt($("#"+id+"_page_no").text());
//				if(curPage == $("#"+id+"_page_count").text()){
//					return ;
//				}
//				$("#"+id+"_page_no").text(curPage+1);
//				var continues = options.callback();
//				return continues;
//			});
//	
//			$("#"+id+"_firstPage").bind("click",function(){
//				$("#"+id+"_page_no").text(1);
//				var continues = options.callback();
//				return continues;
//			});
//			$("#"+id+"_lastPage").bind("click",function(){
//				$("#"+id+"_page_no").text($("#"+id+"_page_count").text());
//				var continues = options.callback();
//				return continues;
//			});
			$("#"+id+"_prev").bind("click",function(){
				var curPage = parseInt($("#"+id+"_page_no").text());
				if(curPage ==1){
					return ;
				}
				if(parseInt($("#"+id+"_page_count").text())<1){
					return ;
				}
				$("#"+id+"_page_no").text(curPage-1);
				var continues = options.callback();
				return continues;
			 });
			$("#"+id+"_next").bind("click",function(){
				var curPage = parseInt($("#"+id+"_page_no").text());
				if(parseInt($("#"+id+"_page_count").text())<1){
					return ;
				}
				if(curPage == $("#"+id+"_page_count").text()){
					return ;
				}
				$("#"+id+"_page_no").text(curPage+1);
				var continues = options.callback();
				return continues;
			});
	
			$("#"+id+"_firstPage").bind("click",function(){
				var curPage = parseInt($("#"+id+"_page_no").text());
				if(curPage ==1){
					return ;
				}
				$("#"+id+"_page_no").text(1);
				var continues = options.callback();
				return continues;
			});
			$("#"+id+"_lastPage").bind("click",function(){
				var curPage = parseInt($("#"+id+"_page_no").text());
				var page_count=$("#"+id+"_page_count").text();
				if(parseInt(page_count)<1){
					return ;
				}
				if(curPage ==page_count){
					return ;
				}
				$("#"+id+"_page_no").text(page_count);
				var continues = options.callback();
				return continues;
			});
		};
		$.extend(this, {
			"HkrtFramework": "1.0",
			"getCurrentPageNumber": getCurrentPageNumber,
			"setCurrentPageNumber": setCurrentPageNumber,
			"setPageCount" : setPageCount,
			"setCount" : setCount
		});
		init();
	}//HkrtFrameworkPagination end 
}(jQuery));