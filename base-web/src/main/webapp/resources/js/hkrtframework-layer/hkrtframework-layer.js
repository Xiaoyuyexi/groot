/**
 * @license
 * (c) 2013-2014 
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
			MessageBox: HkrtFrameworkBox,
			MessageConfirm:HkrtFrameworkConfirm,
			MessageIframe:HkrtFrameworkIframe
		}
	});

	 function HkrtFrameworkBox(container, options) {
		 options = $.extend({
				width:"484px",
				height:"120px"
			},options||{});
		 var offsetWidth = document.body.offsetWidth;
		 var offsetHeight = document.body.offsetHeight;
		 var divWidth = (options.width).substring(0,options.width.length-2);
		 var divHeight = (options.height).substring(0,options.height.length-2);
		 var divLeft = (offsetWidth-divWidth)/2+"px";
		 var divTop = (offsetHeight-divHeight)/2+"px";
		function init() {
			$div = $("<div class='nui-mask' id='"+container+"'></div>");
			$div.css("width",$(document).width());
			$div.css("height",$(document).height());
			$(document.body).append($div);
			$contentDiv=$("<div id='"+container+"_contentDiv' class='message-box' style='left:"+divLeft+";top:"+divTop+";width:"+options.width+";height:118px;'>");
			$titleDiv=$("<div class='titleDiv message-top' style='width:"+options.width+";position:relative;' id='titleId'>").appendTo($contentDiv);
			$closeButton=$("<button class='message-close-button' id='"+container+"_close' type='button'>x</button>").appendTo($titleDiv);
			$middleDiv=$("<div style='color: #555555;width:484px;height:93px;background:none repeat scroll 0 0 #FAFAFA'>").appendTo($contentDiv);
			$image=$("<span style='float: left;margin:20px;'><img src='resources/js/hkrtframework-layer/images/warn.png'/></span>").appendTo($middleDiv);
			$warnContent=$("<div id='"+container+"_warn' style='width: 390px;overflow:auto;height: 70px;padding-top:20px;font-family: Microsoft Yahei,verdana;'><div>").appendTo($middleDiv);
			
			$("#"+container).after($contentDiv);
			$("#"+container+"_close").bind("click",function(){
				$("#"+container).remove();
				$("#"+container+"_contentDiv").remove();
			});
			$("#"+container+"_warn").html(options.content);
		};
		$.extend(this, {
			"HkrtFramework": "1.0"
		});
		init();
		drag("titleId",container+'_contentDiv');
	} 

	 function HkrtFrameworkConfirm(container, options) {
		 options = $.extend({
				width:"484px",
				height:"120px"
			},options||{});
		 var offsetWidth = document.body.offsetWidth+200;
		 var offsetHeight = document.body.offsetHeight;
		 var divWidth = (options.width).substring(0,options.width.length-2);
		 var divHeight = (options.height).substring(0,options.height.length-2);
		 var divLeft = (offsetWidth-divWidth)/2+"px";
		 var divTop = (offsetHeight-divHeight)/2+"px";
		function init() {
			$("#warnDiv", window.parent.document).show();
			$contentDiv=$("<div id='"+container+"_contentDiv' class='message-box' style='left:"+divLeft+";top:"+divTop+";width:"+options.width+";height:auto;'>");
			$titleDiv=$("<div class='titleDiv message-top' style='width:"+options.width+";text-align:right;'> id='titleId'").appendTo($contentDiv);
			$closeButton=$("<button class='message-close-button' id='"+container+"_close' type='button'>x</button>").appendTo($titleDiv);
			$middleDiv=$("<div style='color: #555555;width:"+options.width+";height:"+options.height+";background:none repeat scroll 0 0 #FAFAFA'>").appendTo($contentDiv);
			$warnContent=$("<div id='"+container+"_warn' style='width:"+options.width+";'><div>").appendTo($middleDiv);
			
			$("#"+container, window.parent.document).after($contentDiv);
			$("#"+container+"_close", window.parent.document).bind("click",function(){
				$("#"+container, window.parent.document).hide();
				$("#"+container+"_contentDiv",window.parent.document).remove();
			});
			$("#"+container+"_warn", window.parent.document).html(options.content);
		};
		$.extend(this, {
			"HkrtFramework": "1.0"
		});
		init();
		drag("titleId",container+'_contentDiv');
	}
	function HkrtFrameworkIframe(container, options) {
		options = $.extend({
			width:"684px",
			height:"400px",
			url:null,
			callback:function(){return false;}
		},options||{});
		function method() {
			var continues = options.callback();
			return continues;
		}
		var offsetWidth = document.body.offsetWidth;
		 var divWidth = (options.width).substring(0,options.width.length-2);
		 var divLeft = (offsetWidth-divWidth)/2+"px";
		function init() {
			$div = $("<div class='nui-mask' id='"+container+"'></div>");
			$div.css("width",$(document).width());
			$div.css("height",$(document).height());
			$(document.body).append($div);
			$iframeDiv=$("<div id='"+container+"_contentDiv' class='message-box' style='width:"+options.width+";height:auto;left:"+divLeft+";top:150px;'>");
			$topDiv=$("<div class='titleDiv message-top' style='width:"+options.width+";text-align:right;' id='titleId'>").appendTo($iframeDiv);
			$closeButton=$("<button class='message-close-button' id='"+container+"_close' type='button'>x</button>").appendTo($topDiv);
			$iframe=$("<iframe frameborder='0' class='ss' style='width:"+options.width+";height:"+options.height+";' src="+options.url+"  cellspacing='0'></iframe>").appendTo($iframeDiv);
			$("#"+container).after($iframeDiv);
			$("#"+container+"_close").bind("click",function(){
				$("#"+container).remove();
				$("#"+container+"_contentDiv").remove();
				method();
			});

		};
		
		$.extend(this, {
			"HkrtFramework": "1.0"
		});
		init();
		drag("titleId",container+'_contentDiv');
	}
	
	
	function drag(titleId,parentId){
		var div = document.getElementById(titleId);
		var pDiv = document.getElementById(parentId);
		div.onmousedown=function(e){
			div.style.cursor="move";
			var oEvent = e||event;
			var clientX = oEvent.clientX-div.parentElement.offsetLeft;
			var clientY = oEvent.clientY-div.parentElement.offsetTop;
			if(div.setCapture){
				div.onmousemove=fnMove;
				div.onmouseup=fnUp;
				div.setCapture();
			}else{
				document.onmousemove=fnMove;
				document.onmouseup=fnUp;
			}
			function fnMove(ev){
				var oEvent = ev||event;
				var l = oEvent.clientX-clientX;
				var t = oEvent.clientY-clientY;
				if(l<0){
					l=0;
				}else if(l>document.documentElement.clientWidth-pDiv.offsetWidth){
					l=document.documentElement.clientWidth-pDiv.offsetWidth;
				}
				if(t<0){
					t=0;
				}else if(t>document.documentElement.clientHeight-pDiv.offsetHeight){
					t=document.documentElement.clientHeight-pDiv.offsetHeight;
				}
				div.parentElement.style.left = l+"px";
				div.parentElement.style.top = t+"px";
			}
			function fnUp(){
				div.style.cursor="default";
				this.onmousemove=null;
				this.onmouseup=null;
				if(this.releaseCapture){
					div.releaseCapture();
				}
			}
			return false;
		};
	}
	
}(jQuery));
