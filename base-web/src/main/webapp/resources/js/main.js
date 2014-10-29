var menuClickFun;
$(document).ready(function() {
	$(".nj ul").hide();
	tabVector=new Map(); 
	tabVector.removeAndPut("home_li","home_li");

$("ul.ni li").click(function() {
	menuClickFun(this);
});

menuClickFun = function menuClick(event,itemId,tabTitle,menuAction){
	
	$("#home_content").hide();
	var $treeItemId;
	if(event == null){
		$treeItemId = itemId;
	}else{
		$treeItemId = $(event).attr("id");
	}
	tabHasFocus($treeItemId,tabTitle,menuAction);	
	tabVector.removeAndPut($treeItemId,$treeItemId);
};
function dynamicTabTopWidth(){
	//����ǩ��� ����ı��ǩ��width
	var leftRightWidth=25;
	var tabNum=$(".WB3-gTop-tabs-item:not(#home_li)").length+1;  //�ſ�
	var homeWidth=$('#home_li').width();
	
	var mainWidth=$(".frame-main-inner").width()-homeWidth-leftRightWidth;  
	var tabWidth=mainWidth/tabNum;

	if(tabWidth< (100+leftRightWidth)){
		$(".WB3-gTop-tabs-item:not(#home_li)").css('width',tabWidth-leftRightWidth);
	}
};

$("#home_li").bind('click',function(){
	homeTabHasFocus();
});


function homeTabHasFocus(){
	$("#home_content").show();
	$("#main_content").hide();
	$("#tab_top_vector ul li").removeClass("WB3-gTop-tabs-item-on");
	$("#home_li").addClass("WB3-gTop-tabs-item-on");
	$(".ni li").removeClass("leftside-white");
}
function tabHasFocus ($treeItemId,tabTitle,menuAction){
//	var $tabTopId = $treeItemId+"_top_tab";
	
	var $action=$("#"+$treeItemId).find("a").attr("menuAction");
		if(menuAction != null){
			$action = menuAction;
		}
	if(tabVector.containsKey($treeItemId)==false){
		createIframe($treeItemId);
	//��ȡ�������
	var $tabTitle=$("#"+$treeItemId).find("a").attr("title");
	
	if(tabTitle != null){
		$tabTitle = tabTitle;
	}
	addTab($tabTitle,$treeItemId);
		//���ǩ����࣬���С��ǩ��width
		dynamicTabTopWidth();
	}
		$("#home_content").hide();
		$("#main_content").find("iframe").hide();
		$("#main_content").show();
		$("#"+$treeItemId+"_iframe").attr("src",$action).show();
	
		changeStyle($treeItemId);
		
		/*$("#tab_top_vector ul li").removeClass("WB3-gTop-tabs-item-on");
		$("#"+$treeItemId+"_top_tab").addClass("WB3-gTop-tabs-item-on");
		$(".ni li").removeClass("leftside-white");
		$("#"+$treeItemId).addClass("leftside-white");*/


}
//�����Ӧ��top��ǩ8
function addTab($tabTitle,$treeItemId) {
	var $tabTopId = $treeItemId+"_top_tab";
	var $closeIconId = $treeItemId + '_close_icon';
//	var $tabContentId = $treeItemId + '_tap_content';
	
	var li=$('<li></li>').attr('id',$tabTopId).attr('style','width: 100px;').addClass('WB3-gTop-tabs-item').addClass('fn-animation-slideIn')
		.addClass('fn-animation-slideIn-lr').addClass('fn-animation-showing');

	var close=$('<a href="javascript:void(0)"></a>').attr('id',$closeIconId).addClass('WB3-gTop-tabs-close');
	
	var tabMiddle=$('<a href="javascript:void(0)"></a>').attr('title',$tabTitle).attr('id',$tabTopId).addClass('WB3-gTop-tabs-link').addClass('iZ');
	var tabLeft=$('<b></b>').addClass('WB3-gTop-tabs-rc').addClass('WB3-gTop-tabs-rc-1');
	var	tabRight=$('<b></b>').addClass('WB3-gTop-tabs-rc').addClass('WB3-gTop-tabs-rc-2');
	var tabContant=$('<span id="text"></span>' ).text($tabTitle);
	tabMiddle.append(tabLeft);
	tabMiddle.append(tabRight);
	tabMiddle.append(tabContant);
	li.append(tabMiddle);
	close.hover(function(){close.addClass('WB3-tab-close-mouseover');},function(){close.removeClass('WB3-tab-close-mouseover');}); 
	li.append(close);
	$("#tab_top_vector ul").append(li);

	tabVector.removeAndPut($treeItemId,$treeItemId);
	li.bind("click",function(){
		$("#home_content").hide();
		$("#main_content").find("iframe").hide();
		$("#main_content").show();
		$("#"+$treeItemId+"_iframe").show();
	
		changeStyle($treeItemId);
		tabVector.removeAndPut($treeItemId,$treeItemId);
		/*$("#tab_top_vector ul li").removeClass("WB3-gTop-tabs-item-on");
		$("#"+$treeItemId+"_top_tab").addClass("WB3-gTop-tabs-item-on");
		$(".ni li").removeClass("leftside-white");
		$("#"+$treeItemId).addClass("leftside-white");*/
	});
	li.bind("dblclick",function(){
		tabHasFocus($treeItemId);
		tabVector.removeAndPut($treeItemId,$treeItemId);
	});
	close.bind("click",function(){
		var closeId = $(this).attr("id");
		closeId=closeId.replace("_close_icon",'');
		$("#"+closeId+"_iframe").remove();
		var removeCurrent=($(this).parent()).attr("id");
		($(this).parent()).remove();
		var treeItemId = removeCurrent.replace("_top_tab",'');
		tabVector.remove(treeItemId);
		var lastTabId=tabVector.last().key();
		if(lastTabId == "home_li"){
			homeTabHasFocus();
		}else{
			$("#home_content").hide();
			$("#main_content").find("iframe").hide();
			$("#main_content").show();
			$("#"+lastTabId+"_iframe").show();
		
			changeStyle(lastTabId);
		}
		dynamicTabTopWidth();
	});
};

function changeStyle(treeItemId){
	$("#tab_top_vector ul li").removeClass("WB3-gTop-tabs-item-on");
	$("#"+treeItemId+"_top_tab").addClass("WB3-gTop-tabs-item-on");
	$(".ni li").removeClass("leftside-white");
	$("#"+treeItemId).addClass("leftside-white");
}

function createIframe($treeItemId){
/*	var offsetHeight = document.body.offsetHeight;
	offsetHeight=offsetHeight-90;*/
	$("#main_content").append(" <iframe width='100%' height='99%' frameborder='0' src='' style='border: 0px solid #92A4BF;border-left:none;display: none;overflow:auto;position:absolute;' cellspacing='0' id='"+$treeItemId+"_iframe'></iframe>");
};
});
