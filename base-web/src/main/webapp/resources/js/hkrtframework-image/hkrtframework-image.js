/**
 * @license (c) 2013-2014 ZhaoDaping
 * 
 * Distributed under MIT license. All rights reserved.
 * 
 * HkrtFramework v1.0
 * 
 */
(function($) {
	document.createElement("rvml");
	$.extend(true, window, {
		HkrtFramework : {
			Image : HkrtFrameworkImage
		}
	});

	function HkrtFrameworkImage(obj, iImageSetting) {
		// 设置图片参数
		var angle = 0, setting = {
			id : '',
			code:'101',
			imageid : '',
			status:'',
			upload : {
				open : true,
				url : 'uploadHkrtImage.jhtml',
				argName : ''
			},
			load : {
				url : 'loadHkrtImage.jhtml',
				argName : ''
			},
			audit:{
				open:false,
				url:'auditMerchantImage.jhtml',
				argName:''
			},
			rotate : {
				open : true,
				angle : 90
			},
			view : {
				status : false,
				division : false,
				title:true,
				auditnote:false,
				history:true
			},
			image : {
				width : 200,
				height : 200
			}
		},imageArray = [],imageStatus=[],defaultImage='resources/images/default.gif';

		function createImageDiv(result) {
			if (setting.view.division) {
				$h2Ydiv = $("<div></div>");
				
				$h2Y = $("<div class='meta'></div>");
				$h2Ydiv.html("<b>以下证件为必填项</b>");
				$h2Ydiv.append($h2Y);

				$h2Ndiv = $("<div style='clear:left;'></div>");
				$h2N = $("<div class='meta'></div>");
				$h2Ndiv.html("<b>以下证件为选填项</b>");
				$h2Ndiv.append($h2N);

				$imageA = $("<div id='imageA'>");
			//	$imageA.css("width",setting.image.width);
				$imageB = $("<div id='imageB'>");
			//	$imageB.css("width",setting.image.width);

				obj.append($h2Ydiv).append($imageA).append($h2Ndiv).append(
						$imageB);
			}
			$.each(result,function(k, v) {
								var node = (imageArray[k] = {});
								node.id = v.id;
								node.rotate = 0;
								//计算高度和宽度
								var $divImage = $("<div id='divImage"+k+"' style='float:left;width: 100%;'></div>");
								createImage(k,v,$divImage);
								if (setting.view.division) {
									if ('Y'==v.required) {
										$('#imageA').append($divImage);
									} else {
										$('#imageB').append($divImage);
									}
								} else {
									obj.append($divImage);
								}
							});
		}
		
		function createImage(k,v,divImage){
			divImage.empty();
			
			$divone = $("<div id='divone"+k+"' class='grid' style='float: left;border:1px solid #8BAABF;float: left;margin-left: 10px;margin-top:10px;'></div>");
			$divhistory = $("<div id='divhistory"+k+"' style='overflow: auto;float: left;border:1px solid #8BAABF;float: left;margin-left: 10px;margin-top:10px;width:600px;'></div>");
			$divhistory.hide();
			$divhistoryContent = $("<div id='history"+k+"' style='margin-top:20px;'></div>");
			$historyTitle = $("<div style='float:left;margin-left:10px'></div>");
			$historyA = $("<a href='javascript:void(0)'>图片历史</a>");
			$historyTitle.append($historyA);
			divImage.append($divone);
			if(setting.audit.open){
				divImage.append($historyTitle);
			}else{
				divImage.append("<div></div>");
			}
		/*	$historyTitle.append($historyA);
			if(setting.audit.open){
				divImage.append($divhistory);
			}
			
			$divhistory.append($divhistoryContent);*/
			
			var count=0;
			$divimage = $("<div style='position:absolute;clear:left;'>");
			$divimage.css("width", setting.image.width).css("height", setting.image.height+20);
			$img = $("<img></img>");
			$img.attr("id", "image" + k);
			if(!validateImage(v.path)){
				v.path=defaultImage;
			}else{
				v.path=v.path+'?'+ new Date().getTime();
			}
			$img.attr("src", v.path);
			$img.bind("mouseover", function() {
				imgonmouseover(k);
			}).bind("mouseout", function() {
				imgonmouseout(k);
			}).bind("click", function() {
				openImgSrc("image",k);
			}).bind("error", function() {
				imageUploadError(k, setting);
			});

			$leftspan = $("<div class='button gray fileinput-button'  style='width:30px;position:absolute;left:0px;top:5px;background:#FFF;'>左旋</div>");
			$leftspan.attr("id", "leftspan" + k);
			$leftspan.bind("mouseover", function() {
				imgonmouseover(k);
			}).bind("mouseout", function() {
				imgonmouseout(k);
			}).bind("click", function() {
				trunRotate(k, setting);
			});
			//class='button gray fileinput-button' 
			$rightspan = $("<div class='button gray fileinput-button' style='width:30px;position:absolute;right:5px;top:5px;background:#FFF;'>右旋</div>");
			$rightspan.attr("id", "rightspan" + k);
			$rightspan.bind("mouseover", function() {
				imgonmouseover(k);
			}).bind("mouseout", function() {
				imgonmouseout(k);
			}).bind("click", function() {
				trunRightRotate(k, setting);
			});
			$leftspan.hide();
			
			$rightspan.hide();
			$span_title = $("<div style='height: 20px;'></div>");
			$span_tilte_name = $("<span title='"+v.name+"' style='height:20px;float:left;overflow: hidden;text-overflow: ellipsis;white-space: pre;'>"+v.name+"</span>");
			$span_tilte_name.width(setting.image.width-40);
			$span_title_close = $("<a href='javascript:void(0)' style='height:20px;float:right;cursor:pointer;'>收起</a>");
			$span_title.append($span_tilte_name).append($span_title_close);
			$spanStatusTitle=$("<span style='display:inline-block;width:35px'>状态: </span>");
			$spanStatus = $("<span style='display:inline-block;width:60px' id='status" + k
					+ "'></span>");
			$spanStatus.text(imageStatus[v.status]);
			if(!validateImageIncludeDefault(v.path)){
				$spanStatus.text(imageStatus['10A']);
			}
			$span_title_close.bind("click",
					function(){
						if($(this).parent().siblings().is(":hidden")){
							$(this).text("收起");
							$(this).parent().siblings().slideDown(100);
			 				$(this).parent().parent().next().slideDown(100);
			 				$(this).parent().parent().css("height",setting.image.height +count*20);
						}else{
							$(this).text("展开");
							$(this).parent().siblings().slideUp(100);
			 				$(this).parent().parent().next().slideUp(100);
			 				$(this).parent().parent().css("height","auto");
						}
					}
		 			);
			
			$divhistory.append($divhistoryContent);
			divImage.append($divhistory);
			
			$historyA.bind("click",function(){
				$(this).parent().remove();
				loadImageHistory(k);
				/*if($(this).parent().siblings().is(":hidden")){
					loadImageHistory(k);
					$(this).parent().siblings().show(300);
					$(this).parent().parent().css("height",setting.image.height +count*20);
				}else{
					$(this).parent().siblings().hide(300);
					$(this).parent().parent().css("height","auto");
				}*/
			});
			/*if(!validateImageIncludeDefault(v.path)){
				divImage.children("div:eq(1)").hide();
				$span_title_close.text("展开");
				$divimage.hide();
				$divimage.parent().css("height","auto");
			}*/
			$divimage.append($img);
			$rotate = $("<div id='rotate"+k+"'></div>");
			$rotate.append($leftspan).append($rightspan);
			$divimage.append($rotate);
			
			//待解决空图片问题
			if(setting.rotate.open){
				if(!validateImageIncludeDefault(v.path)){
					$rotate.hide();
				}
			}
			
			$labelOperate=$("<div style='position:relative;'></div>");
			if (setting.view.status) {
				$ImageStatusArea=$("<div style='float:left;'></div>");
				$ImageStatusArea.append($spanStatusTitle).append($spanStatus);
				$labelOperate.append($ImageStatusArea);
				count++;
			};
			if(setting.audit.open && validateImageIncludeDefault(v.path)){
				$auditArea=$("<div style='float:right;'></div>");
				$auditTitle=$("<span>操作:</span>");
				$spanAuditY=$("<span>通过</span>");
				$radioAuditY=$("<input type='radio' name='audit"+k+"' value='10D' />");
				$radioAuditY.bind('click',function(){
					auditImageStatus(this,v.id,k);
				});
				$spanAuditN=$("<span>拒绝</span>");
				$radioAuditN=$("<input type='radio' name='audit"+k+"' value='10E' />");
				$radioAuditN.bind('click',function(){
					auditImageStatus(this,v.id,k);
				});
				//.append($auditTitle)
				$auditArea.append($radioAuditY).append($spanAuditY).append($radioAuditN).append($spanAuditN);
				$labelOperate.append($auditArea);
				//$("input[type=radio][value="+v.status+"]").attr("checked",'checked');
				if(v.status==$radioAuditY.attr("value")){
					$radioAuditY.attr("checked",'checked');
				}
				if(v.status==$radioAuditN.attr("value")){
					$radioAuditN.attr("checked",'checked');
				}
				if (!setting.view.status) {
					count++;
				}
			}
			$divimage.append($labelOperate);
			if(setting.view.auditnote || setting.audit.open){
				//if(validateImageIncludeDefault(v.path)){
					$auditNoteArea=$("<div style='float:left;'></div>");
					$auditNoteArea.css("width",setting.image.width);
					$auditTitle = $("<span style='display:inline-block;width:60px;overflow: hidden;text-overflow: ellipsis;white-space: pre;'>审核备注:</span>");
					$auditInput = $("<input type='text' id='audit"+k+"' maxlength='30'></input>");
					$auditInput.css("width",setting.image.width*0.6);
					/*$auditInput.bind("change", function() {
						updateAuditNote(v.id);
					});*/
					$auditSave = $("<a style='margin-left:5px;' href='javascript:void(0)'>保存</a>");
					$auditSave.bind("click", function() {
						updateAuditNote(v.id,k);
					});
					if(setting.audit.open){
						$auditInput.attr("value",v.auditNote);
						$auditNoteArea.append($auditTitle).append($auditInput).append($auditSave);
						count+=2;
					}else{
						var value = v.auditNote;
						if(value==null)value='';
						$auditTitle.attr("title",value);
						$auditTitle.text("审核备注:"+value);
						$auditTitle.css("width",setting.image.width);
						$auditNoteArea.append($auditTitle);
						count++;
					}
					$divimage.append($auditNoteArea);
				//}
			}
			if(setting.view.title){
				$divone.append($span_title);
				count++;
			}
			
			$divone.append($divimage);
			if (setting.upload.open) {
				$span_tilte_name.text(v.name+"(上传最大限制"+v.size+"M)");
				
	    	 	var $bindA	 =	$("<a  href='javascript:void(0)' style='cursor: pointer;'>添加图片</a>");
	    	 	$fileHome=$("<div style='float:left;position: relative;'></div>");
	    	 	$divComposeAttachBrowse =$("<div style='width:50px;position: absolute;overflow: hidden;height: 24px;'></div>");
	    	 	$fileHome.append($divComposeAttachBrowse);
	    	 	
	    	 	var $removeI =	$("<div style='float:left;'><a href='javascript:void(0)' style='cursor: pointer;'>删除</a></div>");
	    	 	if(setting.image.width<200){
	    	 		$divComposeAttachBrowse.css("margin-left", setting.image.width/2+20);
	    	 	}else{
	    	 		$divComposeAttachBrowse.css("margin-left", setting.image.width*0.1);
	    	 		$removeI.css("margin-left", setting.image.width*0.3);
	    	 	}	
	    	 	$file=$("<input id='businessLicenseImage"+k+"' style='font-size:20px;opacity: 0;cursor: pointer;position: absolute;top:-10px;right:-10px;filter:alpha(opacity=0)' name='file' type='file' size='1'>")
	    	 	.bind("change",function(){
					imageUpload(k);
				});
	    	 	$divComposeAttachBrowse.append($bindA).append($file);
	    	 	
	    	 	$removeI.bind("click",function(){
	    	 		removeImage(k);
				});
	    	 	$labelOperate.append($fileHome);
	    	 	$labelOperate.append($removeI);
			}
			
			//设置大小
			$divone.css("width", setting.image.width);
			$divone.css("height",setting.image.height +count*20);
			
			$img.css("width", setting.image.width);
			$img.css("height", setting.image.height);
			
			$divhistory.css("height",$divone.css("height"));
			$historyTitle.css("margin-top",setting.image.height +count*20-10);
			if(!validateImageIncludeDefault(v.path)){
				$historyTitle.hide();
				$span_title_close.text("展开");
				$divimage.hide();
				$divimage.parent().css("height","auto");
			}
		}
		
		// 加载信息
		function loadImageCategory() {
			var url;
			if ('' == setting.load.argName) {
				url = setting.load.url + '?id=' + setting.id;
			} else {
				url = setting.load.url + '?' + setting.load.argName + '='
						+ setting.id;
			}
			$.ajax({
				url : url+'&code='+setting.code+'&r='+Math.random(),
				async : false,
				type : "GET",
				success : function(result) {
					createImageDiv(result);
				}
			});
		}
		
		// 加载信息
		function loadImageHistory(k) {
			var divhistoryContent = $("#history"+k);
			$.ajax({
				url : 'loadImageHistory.jhtml?r='+Math.random(),
				async : false,
				type : "GET",
				data:{"id":imageArray[k].id},
				success : function(result) {
					var count=0;
					$.each(result,function(k, v) {
						$historyImage = $("<div style='float: left;padding: 0 3px;'></div>");
						$image = $("<img id='historyImage"+k+"'></img>");
						$image.attr("src", v);
						$image.css("width", setting.image.width);
						$image.css("height", setting.image.height);
						$image.bind("click", function() {
							openImgSrc("historyImage",k);
						});
						if(validateImageIncludeDefault(v)){
							$historyImage.append($image);
							divhistoryContent.append($historyImage);
						count++;
						}
					});
					divhistoryContent.css("height",setting.image.height);
					divhistoryContent.css("width",count*(setting.image.width+6));
					$("#divhistory"+k).show();
				}
			});
		}
		
		//删除图片
		function removeImage(k){
			var imgid = '#image' + k;
			var path = $(imgid).attr("src");
			if(validateImageIncludeDefault(path)){
				if(confirm("是否删除")){
					var status = '#status' + k;
					var rotate = '#rotate' + k;
					$.ajax({
						url: contextPath +'/removeImage.jhtml?r='+Math.random(),
						async:false,
						type:"POST",
						data:{"id":imageArray[k].id},
						success: function(message) {
							if('00'==message.code){
								showMessageOfParent(message.value);
								$(imgid).attr("src", defaultImage);
								/*$(status).text(imageStatus[message.obj.status]);
								$(rotate).hide();*/
								createImage(k,message.obj,$("#divImage"+k));
								setTimeout("hideMessageOfParent()",3000);
							}
						}
					});
				}
			}
		}
		// 上传图片
		function imageUpload(k) {
			var imgid = '#image' + k;
			var status = '#status' + k;
			var rotate = '#rotate' + k;
			var randomId = new Date().getTime();
			var arg;
			if ('' == setting.load.argName) {
				arg = 'id';
			} else {
				arg = setting.upload.argName;
			}
			;
			var url = setting.upload.url + '?' + arg + '=' + imageArray[k].id+'&code='+setting.code+'&status='+setting.status+'&r='+Math.random();
			$.ajaxFileUpload({
				url : contextPath + '/' + url,
				secureuri : false,
				fileElementId : 'businessLicenseImage' + k,
				dataType : 'json',
				success : function(data) {
					var message = jQuery.parseJSON(data);
					if(null==message){
						message = jQuery.parseJSON(data.responseText);
						showMessageOfParent(message.value);
						setTimeout("hideMessageOfParent()",3000);
					}else{
						showWarnMessage(message.value);
					}
					if('00'==message.code){
						$(imgid).attr("src", message.src + '?' + randomId);
						/*$(status).text(imageStatus[message.status]);
						$(rotate).show();*/
						createImage(k,message.obj,$("#divImage"+k));
					}
				},
				error : function(data) {
					var message = jQuery.parseJSON(data);
					if(null==message){
						message = jQuery.parseJSON(data.responseText);
						showMessageOfParent(message.value);
						setTimeout("hideMessageOfParent()",3000);
					}else{
						showWarnMessage(message.value);
					}
					if('00'==message.code){
						$(imgid).attr("src", message.src + '?' + randomId);
						/*$(status).text(imageStatus[message.status]);
						$(rotate).show();*/
						createImage(k,message.obj,$("#divImage"+k));
					}
					showMessageOfParent(message.value);
					setTimeout("hideMessageOfParent()",3000);
				}
			});
		}
		//获取某个域的字典
		function getLookupCdeList(){
			$.ajax({
				  url:'getLookupCdeList.jhtml?r='+Math.random(),
				  async:false,
				  type:"POST",
				  data:{"typeCode":"10251011"},
				  success: function(result) {
					  $.each(result, function(k, v) {
						  imageStatus[v.id]=v.name;
					  });
				  }
				});
		}
		//更新审核结果
		function auditImageStatus(obj,imageId,k){
			var status=obj.value;
			$.ajax({
				url:setting.audit.url+'?id='+imageId+'&status='+status+'&r='+Math.random(),
				async:false,
				type:"POST",
				success: function(result) {
					$('#status'+k).text(imageStatus[status]);
				}
			});
		}
		//更新审核状态
		function updateAuditNote(imageId,k){
			var text = $('#audit'+k).val();
			if(''==text||null==text){
				alert("内容不能为空!");
				return;
			}
			$.ajax({
				url:setting.audit.url,
				async:false,
				type:'POST',
				data:{id:imageId,text:text},
				success: function(result) {
					alert("保存成功!");
				}
			});
		}
		function imageBind(k){
			$("#image" + k).bind("mouseover", function() {
				imgonmouseover(k);
			}).bind("mouseout", function() {
				imgonmouseout(k);
			}).bind("click", function() {
				openImgSrc("image",k);
			}).bind("error", function() {
				imageUploadError(k, setting);
			});
		}
		function imageUnbind(k){
			$("#image" + k).unbind("mouseover")
			.unbind("mouseout").unbind("click").unbind("error");
		}
		// 逆时针旋转图片
		function trunRotate(k) {
			imageArray[k].rotate -= setting.rotate.angle;
			imageUnbind(k);
			$("#image" + k).rotate(imageArray[k].rotate);
			//解决IE rotate 重绘问题
			imageBind(k);
			
		}// 顺时针旋转图片
		function trunRightRotate(k) {
			imageArray[k].rotate += setting.rotate.angle;
			imageUnbind(k);
			$("#image" + k).rotate(imageArray[k].rotate);
			imageBind(k);
		}
		// 鼠标滑过图片
		function imgonmouseover(k) {
			$('#leftspan' + k).show();
			$('#rightspan' + k).show();
		}
		// 鼠标离开图片
		function imgonmouseout(k) {
			$('#leftspan' + k).hide();
			$('#rightspan' + k).hide();
		}
		// 加载图片失败事件
		function imageUploadError(k) {
			var imgid = '#image' + k;
			$(imgid).attr("src", defaultImage);
		}
		function validateImage(path){
			if(null==path||''==path){
				return false;
			}
			return true;
		}
		function validateImageIncludeDefault(path){
			if(!validateImage(path)){
				return false;
			}
			if(defaultImage==path){
				return false;
			}
			return true;
		}
		
		//打开图片
		function openImgSrc(id,k) {
			var path = $("#"+id + k).attr("src");
			var index=0;
			if(validateImageIncludeDefault(path)){
			//	window.open(path,"_blank");
				imageArray[k].rotate = 0;
				$("#mask", window.parent.document).show();
				$imageIframe = $("<div style='padding:10px;'></div>");
				$imageBorder = $("<div style='width:660px;height:450px;overflow:hidden;text-align: center;' id='imageBorer'></div>");
				$table = $("<table width='100%' height='97%' id='table'></table>");
				$tr = $("<tr></tr>");
				$td = $("<td align='center' style='position:relative;'></td>");
				$images = $("<img src='"+path+"' id='img'></img>");
				$td.append($images);
				$tr.append($td);
				$table.append($tr);
				$div = $("<div id='cursorWheel' style='position:absolute;z-index:1;background-color:#666;cursor:url(http://maps.gstatic.cn/intl/zh-CN_cn/mapfiles/closedhand_8_8.cur), auto;' title='滚动鼠标可以改变图片大小'></div>");
				$images.load(function(){
					$div.width("660px").height("450px").css({top:"30px",left:"10px"}).fadeTo(100, 0.05);
				});
				dragpic=-1;
				dragpicy=-1;
				$div.bind({
					mousedown: function(e){
						//e=event?event:window.event;
						dragpic=e.pageX || (e.clientX);
						dragpicy=e.pageY || (e.clientY);
						if (e.stopPropagation){ 
							// this code is for Mozilla and Opera 
							e.stopPropagation(); 
							e.preventDefault();
						};
					},
					mouseup: function(){
						dragpic=-1;
					},mousemove: function(e){
						if(dragpic>=0){
							//e=event?event:window.event;
							dqsb=e.pageX || (e.clientX );
							dqsby=e.pageY || (e.clientY );
							$imageBorder.scrollLeft($imageBorder.scrollLeft()+(dragpic-dqsb));
							$imageBorder.scrollTop($imageBorder.scrollTop()+(dragpicy-dqsby));
							dragpic=dqsb;
							dragpicy=dqsby;
						}
					},mouseout: function(){
						dragpic=-1;
					}
				});
				
				
				$imageBorder.append($table).append($div);
				$rotate = $("<div style='text-align: center;font-family: Microsoft YaHei;font-size: 14px;'></div>");
				$rotateSave = $("<a id='saver' href='javascript:void(0)' style='padding:0 5px;color:#FFF;'>保存</a>");
				$rotateLeft = $("<a id='left' href='javascript:void(0)' style='padding:0 5px;color:#FFF;'>左转</a>");
				$rotateRight = $("<a id='right' href='javascript:void(0)' style='padding:0 5px;color:#FFF;'>右转</a>");
				$close = $("<a href='javascript:void(0)' class='clsoe-image' style='color:#FFF;'>x</a>");
				$close.bind("click",function(){
					$(this).parent().parent().remove();
					$("#mask", window.parent.document).hide();
				});
				$(document).keyup(function(e){
					if (e.keyCode == 27) { $close.trigger("click");}
				});
				$("#mask", window.parent.document).bind("click",function(){
					$(this).hide();
					$divImage.remove();
				});
				$zoom = $("<a id='zoom' href='javascript:void(0)' style='padding:0 5px;color:#FFF;'>放大</a>");
				
				$narrow = $("<a id='narrow' href='javascript:void(0)' style='padding:0 5px;color:#FFF;'>缩小</a>");
			
				$rotate.append($zoom).append($narrow).append($rotateLeft).append($rotateRight).append($rotateSave);
				$divImage = $("<div style='position:absolute;z-index:999;background:rgba(0, 0, 0, 0.4);width:680px;height:500px;top:110px;filter:progid:DXImageTransform.Microsoft.Gradient(GradientType=0,StartColorStr=#66000000,EndColorStr=#66000000);'></div>");
				$divImage.css("left",(parent.document.body.clientWidth-680)/2);
				$imageIframe.append($rotate).append($imageBorder).append($close);
				$divImage.append($imageIframe);
				$("body", window.parent.document).append($divImage);
				$("#left", window.parent.document).bind("click",function(){
					imageArray[k].rotate -= setting.rotate.angle;
					index -=setting.rotate.angle;
					$("#img", window.parent.document).rotate(imageArray[k].rotate);
				});
				$("#saver", window.parent.document).bind("click",function(){
					var imgid = '#image' + k;
					$.ajax({
						url: contextPath +'/saveImage.jhtml?r='+Math.random(),
						async:false,
						type:"POST",
						data:{"id":imageArray[k].id,"degree":index},
						success: function(message) {
							if('00'==message.code){
								showMessageOfParent("保存成功!");
								$(imgid).attr("src", message.src+'?r='+Math.random());
								//createImage(k,message.obj,$("#divImage"+k));
								index=0;
								setTimeout("hideMessageOfParent()",3000);
							}else{
								showMessageOfParent("保存失败!");
								setTimeout("hideMessageOfParent()",3000);
							}
						}
					});
				});
				$("#right", window.parent.document).bind("click",function(){
					imageArray[k].rotate += setting.rotate.angle;
					index +=setting.rotate.angle;
					$("#img", window.parent.document).rotate(imageArray[k].rotate);
				});
				$("#zoom", window.parent.document).bind("click",function(){
					var img = parent.document.getElementById('img');
					img.width=img.width+50;
					if(img.width>660){
			    		parent.document.getElementById('imageBorer').scrollLeft=img.width/2-340;
			    	}
			    	if(img.height>450){
			    		parent.document.getElementById('imageBorer').scrollTop=img.height/2-225;
			    	}
				});
				$("#narrow", window.parent.document).bind("click",function(){
					var img = parent.document.getElementById('img');
					if(img.width<=50){
		    			img.width=50;
		    			return;
		    		}
					img.width=img.width-50;
				});
			//	width = $($images).css("width").replace("px","");
			//	height = $($images).css("height").replace("px","");
			//	if(width/height>66/48){
				//	$($images).css("width","655px");
					$($images).attr("width","400");
			//	}else{
				//	$($images).css("height","475px");
			//	}
				/*if(width<650&&height<480){
					$($images).css("width","auto");
					$($images).css("height","auto");
				}
*/

				 var oDiv = parent.document.getElementById('cursorWheel');
				 myAddEvent(oDiv,'mousewheel',onMouseWheel);
				 myAddEvent(oDiv,'DOMMouseScroll',onMouseWheel);
				
			}
		}
		function onMouseWheel(ev){
	    	var oEvent = ev||event;
	    	var bDown = true;
	    	bDown = oEvent.wheelDelta?oEvent.wheelDelta<0:oEvent.detail>0;
    		var img = parent.document.getElementById('img');
	    	if(bDown){
	    		img.width=img.width+50;
	    	}else{
	    		if(img.width<=50){
	    			img.width=50;
	    			return;
	    		}
	    		img.width=img.width-50;
	    	}
	    	
	    	if(img.width>660){
	    		parent.document.getElementById('imageBorer').scrollLeft=img.width/2-340;
	    	}
	    	if(img.height>450){
	    		parent.document.getElementById('imageBorer').scrollTop=img.height/2-225;
	    	}
	    }
		function myAddEvent(obj,sEvent,fn){
	    	if(obj.attachEvent){
	    		obj.attachEvent('on'+sEvent,fn);
	    	}else{
	    		obj.addEventListener(sEvent,fn,false);
	    	}
	    }
		function clone(obj) {
			if (obj === null)
				return null;
			var o = obj.constructor === Array ? [] : {};
			for ( var i in obj) {
				o[i] = (obj[i] instanceof Date) ? new Date(obj[i].getTime())
						: (typeof obj[i] === "object" ? arguments
								.callee(obj[i]) : obj[i]);
			}
			return o;
		}
		function refresh(){
			init();
		}
		function init() {
			obj.empty();
			// var setting = clone(setting);
			$.extend(true, setting, iImageSetting);
			//加载图片状态
			getLookupCdeList();
			// 加载图片
			loadImageCategory(obj, setting);
		}
		$.extend(this, {
			"HkrtFramework" : "1.0"
		});
		init();
	}
})(jQuery);
