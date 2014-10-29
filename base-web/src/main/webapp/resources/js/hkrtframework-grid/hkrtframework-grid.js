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
if (typeof Map === "undefined") {
	  throw "hkrtframework requires map module to be loaded";
	}
(function ($) {
	
	$.extend(true, window, {
		HkrtFramework: {
			Grid: HkrtFrameworkGrid
		}
	});

	 function HkrtFrameworkGrid(container, data, columns, options) {
		 var defaults = {
			      showTopPanel: false,
			   //   width:"1000px",
			      topPanelHeight: "25px",
			   //   topPanelWeight: "1370px",
			      column:[]
			    };
		var $topPanelScroller=0;
		var $container = $(container);
		var $table;
		var $thead;
		var map = new Map();
		var permissionMap = new Map();
		var cdeMap = new Map();
		var bitComputMap = new Map();
		function addData(objData){
			for(var i = 0; i < objData.length; i++){
				var d = objData[i];
				$tr = $("<tr style='white-space:nowrap;'></tr>");
				if(options.column!=null){
					for(var j = 0; j < options.column.length; j++){
						var m = options.column[j];
						
						if(m.id=='operating'){
							var $object = [] ;
							$object = d[m.id];
							$td = $("<td></td>");
								$.each($object,function(k,v){
									var permissionName = v.attr("permission");
									if (permissionName) {
										if(permissionMap.containsKey(permissionName)==false){
											$.ajax({
												url: contextPath+"/isPermitted.jhtml?permissionName="+permissionName+"&r="+Math.random(),
												async:false,
												success: function(data) {
													permission = data |false;
													if(permission){
														permissionMap.put(permissionName,permissionName);
															$td.append(v);
													}
												}
											});
										}else{	
											$td.append(v);
										}
									}else{
										$td.append(v);
									}
									$tr.append($td);
								});
								//解决ie和chrome数据为空时的td不添加的bug
								if($object.length==0){
									$tr.append("<td></td>");
								}
						}else{
							
							var value = d[m.id];
							if(value!=undefined||value!=null){
								if(m.formatter!=undefined||m.formatter!=null){
									cdeMap = m.formatter;
									if(cdeMap.containsKey(d[m.id])){
										value = cdeMap.get(d[m.id]);
									}
								}
								
								if(m.bitComput!=undefined||m.bitComput!=null){
									bitComputMap = m.bitComput;
									var showMessage = "";
									for(var n=0; n<bitComputMap.size(); n++){  
										var key = bitComputMap.key();
										if( (d[m.id] & key) == key){
											showMessage = showMessage + bitComputMap.get(key) + ",";
										}
										bitComputMap.next();
									}
									showMessage = showMessage.substring(0, (showMessage.length - 1));
									value = showMessage;
								}
							}
							if(m.dateFormat!=undefined||m.dateFormat!=null){
								if(!isEmpty(value)){
									value = value.replace(/\-/g,"/");
									var date = new Date(value);
									value = date.pattern(m.dateFormat);
								}
							}
							var value_1 = value;
							if(m.moneyFormat!=undefined||m.moneyFormat!=null){
								if(!isEmpty(value)  && true==m.moneyFormat){
									value = cfmoney(value,2);
								}
								if(!isEmpty(value)  && "ABS"==m.moneyFormat){
									value = cfmoney(Math.abs(value),2);
								}
								if(!isEmpty(value_1)  && true==m.moneyFormat){
									value_1 = cfmoneyNotRed(value_1,2);
								}
								if(!isEmpty(value_1)  && "ABS"==m.moneyFormat){
									value_1 = cfmoneyNotRed(Math.abs(value_1),2);
								}
							}
							
							if(m.width!=undefined||m.width!=null){
								if((typeof value)=="string"){
									$div = $("<div title='"+value_1+"' style='white-space: nowrap;overflow:hidden;text-overflow:ellipsis'></div>").append(value).css('width',m.width);
								}else{
									$div = $("<div style='white-space: nowrap;overflow:hidden;text-overflow:ellipsis'></div>").append(value).css('width',m.width);
								}
								$td = $("<td></td>").append($div);
							}else{
								$td = $("<td></td>").append(value);
							}
							if(m.addClass!=undefined||m.addClass!=null){
								$td.addClass(m.addClass);
							}
							$tr.append($td);
						}
						
					}
				}
				$tr.bind("mouseover",function(){
					$(this).addClass("hkrtframework-grid-tr");
				}).bind("mouseout",function(){
					$(this).removeClass("hkrtframework-grid-tr");
				});
				$table.append($tr);
			 }
			$divTable.append($table);
			$container.append($divTable);
		}
		function crossTable(dataArray,crossThArray,index){
			var tr = $("<tr class='eEven1'>");
			
			for(var j = 0; j < options.column.length; j++){
				var m = options.column[j];
				var columnCode = $("<td>").append(m.name).attr('nowrap','nowrap');
				tr.append(columnCode);
			}
			for(var i = 0; i < crossThArray.length; i++){
				tr.append($("<td align='center' id='th-hidden" + i + "'>")
						.append(crossThArray[i].name));
			}
			tr.append($("<td align='center' id='th-hidden" + i + "'>")
					.append("汇总"));
			$table.append(tr);
			var alf=dataArray.length/crossThArray.length;
			for(var i = 0 ; i< alf; i++){
				var groupTr = $("<tr style='background-color:#FFFFFF'></tr>");
				for(var j = 0; j < options.column.length; j++){
					var m=options.column[j];
					var value = dataArray[i*crossThArray.length][m.id];
					groupTr.append($("<td></td>").append(value));
				}
				var sum=0.00;
				for(var j = 0 ; j < crossThArray.length;j++){
					var priceTd = $("<td class='control-tdHight" + j + "'></td>");
					var value = dataArray[j*alf+i][index];
					priceTd.append(value);
					sum+=value;
				groupTr.append(priceTd);
				}
				var sumTd = $("<td class='control-tdHight" + i + "'></td>");
				sumTd.append(sum);
				groupTr.append(sumTd);
				$table.append(groupTr);
			}

			$container.append($table);
		}
		
		function setCrossData(objData,objColumns,index){
			gridData = objData;
			$(container).find("table tr:gt(0)").remove();
			$table.empty();
		//	$table.append($thead);
			crossTable(objData,objColumns,index);
		}
		
		function setData(objData){
			gridData = objData;
			$(container).find("table tr:gt(0)").remove();
		//	$table.empty();
		//	$table.append($thead);
			addData(objData);
		}
		function setColumns(columns){
			if(columns!=null){
				for (var i = 0; i < columns.length; i++) {
						var m = columns[i];
						$td = $("<td></td>").css('width',m.width).append(m.name);
						$thead.append($td);
				}
				$table.append($thead);
			}
		}
		function setTopButton(id,object,permissionName){
			  var permission = false;
			  if (permissionName) {
				  $.ajax({
					  url: contextPath+"/isPermitted.jhtml?permissionName="+permissionName+"&r="+Math.random(),
					  async:false,
					  success: function(data) {
						  permission = data;
						  if (permission || permissionName == null) {
							  	object.attr("id",id);
								map.put(id,object);
								$topPanelScroller.append(object);
						  }
					  }
					});
			  }
		}
		function getTopButton(id){
			return map.get(id);
		}
		function showTopPanel() {
		      options.showTopPanel = true;
		}
		function init() {
			
			
			if ($(container).length < 1) {
				throw new Error("HkrtFrameworkPagination requires a valid container, " + container + " does not exist in the DOM.");
			}
			if (options.showTopPanel){
				$topPanelScroller = $("<div style='color:#000;border:1px solid #8BAABF;border-bottom:none;height:25px;line-height:25px;background: #D6E8F6;'></div>");
				$container.append($topPanelScroller);
			}
//			$container.css('width',options.width);
//			$container.css('height',options.height);
			$container.addClass('hkrtframework-grid');
			$divTable=$("<div style='width:100%;'></div>");
			$table = $("<table style='width:100%;background:#FFF;' ></table>");
	//		$table.css('width',options.width);
			$table.css('width',options.height);
			$thead = $("<tr style='background-color: #E9F1F5;height:35px;font-weight: bold;white-space:nowrap;'></tr>");
/*			if(columns!=null){
				for (var i = 0; i < columns.length; i++) {
						var m = columns[i];
						$td = $("<td></td>").css('width',m.width).append(m.name);
						$thead.append($td);
				}
			}
			$table.append($thead);
			 if(data!=null){
				 for(var i = 0; i < data.length; i++){
						var d = data[i];
						$tr = $("<tr></tr>");
						for(var j = 0; j < columns.length; j++){
							var m = columns[j];
								$td = $("<td></td>").append(d[m.id]);
							$tr.append($td);
						}
						$table.append($tr);
				 }
			}
			$container.append($table);*/
	//			if(options.permission){
					var id = $(".leftside-white",window.parent.document).attr("id");
					if(id==undefined){
						return;
					}
					
					var permission = false;
					$.ajax({
						url: contextPath+"/isPermitted.jhtml?permissionName=TableName:view&r="+Math.random(),
						async:false,
						success: function(data) {
							permission = data;
							// || "TableName:view" == null
							if (permission) {
								var tableName="";
								$.ajax({
									url : contextPath + "/getTableNameByMenuId.jhtml?r=" + Math.random(),
									async : false,
									type : "POST",
									data : {"id" : id},
									success : function(result) {
										if(null==result.tableNameDepict){
											tableName="没有对应的表";
										}else{
											tableName = result.tableNameDepict;
										}
									}
								});
								var $div = $("<div id='cursorMenu' style='position:absolute;width:150px;background:#FFF;border:1px solid #8BAABF;box-shadow:0 0 10px rgba(0, 0, 0, 0.5);padding:4px 0;z-index:100;'></div>");
								var $cursorMenu = $("<div style='cursor: pointer;padding:3px 30px;'>查看表名</div>");
								$div.append($cursorMenu);
								$("body").click(function(){$div.remove();});
								$("body").bind("contextmenu",function(){
									if($("#cursorMenu")!=undefined){
										$("#cursorMenu").remove();
									}
								});
								$($table).bind("contextmenu",function(e){
									x = e.clientX+document.documentElement.scrollLeft || document.body.scrollLeft;
									y = e.clientY+document.documentElement.scrollTop || document.body.scrollTop;
									$div.css("left",x);
									$div.css("top",y);
									if($("#cursorMenu")!=undefined){
										$("#cursorMenu").remove();
									}
									$("body").append($div);
									$div.children().hover(function(){$(this).css("color","#FFF").css("background","#1664B4");},function(){$(this).css("color","#000").css("background","#FFF");});
									$cursorMenu.bind("click",function(e){
										$(this).parent().remove();
										$(this).css("color","#000").css("background","#FFF");
										 var offsetWidth = document.body.offsetWidth;
										 var offsetHeight = document.body.offsetHeight;
										 var divLeft = (offsetWidth/2-200)+"px";
										 var divTop = (offsetHeight/2);
										 if(divTop>300){
											 divTop=300+"px"; 
										 }else{
											 divTop=divTop+"px";
										 }
										$divTableName = $('<div style="left:'+divLeft+';top:'+divTop+';width:350px;height: auto;" class="message-box" id="warnDiv_contentDiv"></div>');
										$divTop = $('<div class="message-top"></div>');
										$WarnDiv = $('<div style="float:left;font-weight: bold;margin-left: 10px;">提示</div>').appendTo($divTop);
										$closeBtn = $('<button type="button" class="message-close-button">x</button>)').appendTo($divTop);
										$divContent = $('<div style="padding: 30px 0;text-align: center;color: #555555;background:none repeat scroll 0 0 #FAFAFA;">'+tableName+'</div>');
										$divTableName.append($divTop).append($divContent);
										$mask = $('<div id="warnDiv" class="nui-mask"></div>');
										$mask.css("width",$(document).width());
										$mask.css("height",$(document).height());
										$("body").append($mask).append($divTableName);
										$closeBtn.bind("click",function(){
											$(this).parent().parent().remove();
											$mask.remove();
										});
									});
									return false;
								});
							}
							
						}
					});
	//			}
		};
		$.extend(this, {
			"HkrtFramework": "1.0",
			"setData" : setData,
			"setCrossData" : setCrossData,
			"addData" : addData,
			"showTopPanel": showTopPanel,
			"setColumns": setColumns,
			"setTopButton": setTopButton,
			"getTopButton": getTopButton
		});
		init();
	}//HkrtFrameworkGrid end 
}(jQuery));