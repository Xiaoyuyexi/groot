/***
 * Contains basic SlickGrid editors.
 * @module Editors
 * @namespace Slick
 */

(function ($) {
  // register namespace
  $.extend(true, window, {
    "Slick": {
      "Editors": {
        "Text": TextEditor,
        "Password":PasswordEditor,
        "Integer": IntegerEditor,
        "Double": DoubleEditor,
        "Date": DateEditor,
        "YesNoSelect": YesNoSelectEditor,
        "Checkbox": CheckboxEditor,
        "PercentComplete": PercentCompleteEditor,
        "LongText": LongTextEditor,
        "LookupSelect":LookupSelectEditor,
        "InputFile":InputFileEditor,
        "SelectMul":SelectMulEditor,
        "AutoIncrease":AutoIncreaseEditor
      }
    }
  });

  function TextEditor(args) {
	var maxLength = args.column.maxLength;
    var $input;
    var defaultValue;
    var scope = this;
    var $inputText;
    if(null==maxLength||undefined==maxLength){
    	 $inputText = $("<INPUT type=text class='editor-text'/>");
    }else{
    	$inputText = $("<INPUT type=text class='editor-text' maxlength='"+maxLength+"'/>");
    }
    this.init = function () {
      $input = $inputText
          .appendTo(args.container)
          .bind("keydown.nav", function (e) {
            if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
              e.stopImmediatePropagation();
            }
          })
          .focus()
          .select();
    };

    this.destroy = function () {
      $input.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.getValue = function () {
      return $input.val();
    };

    this.setValue = function (val) {
      $input.val(val);
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field] || "";
      $input.val(defaultValue);
      $input[0].defaultValue = defaultValue;
      $input.select();
    };

    this.serializeValue = function () {
      return $input.val();
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };

    this.validate = function () {
      if (args.column.validator) {
        var validationResults = args.column.validator($input.val());
        if (!validationResults.valid) {
          return validationResults;
        }
      }

      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  
  function AutoIncreaseEditor(args) {
		var maxLength = args.column.maxLength;
	    var $input;
	    var defaultValue;
	    var scope = this;
	    var $inputText;
	    var value = "";
	    $.ajax({
			url : contextPath + '/'+args.column.valueRequest+'.jhtml?r=' + Math.random(),
			async : false,
			type : "POST",
			success : function(result) {
				value = result;
			}
		});
	    
	    
	    if(null==maxLength||undefined==maxLength){
	    	 $inputText = $("<INPUT type=text class='editor-text' value='"+value+"' readonly/>");
	    }else{
	    	$inputText = $("<INPUT type=text class='editor-text' value='"+value+"' readonly maxlength='"+maxLength+"'/>");
	    }
	    this.init = function () {
	      $input = $inputText
	          .appendTo(args.container)
	          .bind("keydown.nav", function (e) {
	            if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
	              e.stopImmediatePropagation();
	            }
	          })
	          .focus()
	          .select();
	    };

	    this.destroy = function () {
	      $input.remove();
	    };

	    this.focus = function () {
	      $input.focus();
	    };

	    this.getValue = function () {
	      return $input.val();
	    };

	    this.setValue = function (val) {
	      $input.val(val);
	    };

	    this.loadValue = function (item) {
	      defaultValue = item[args.column.field] || "";
	      $input.val(defaultValue);
	      $input[0].defaultValue = defaultValue;
	      $input.select();
	    };

	    this.serializeValue = function () {
	      return $input.val();
	    };

	    this.applyValue = function (item, state) {
	      item[args.column.field] = state;
	    };

	    this.isValueChanged = function () {
	      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
	    };

	    this.validate = function () {
	      if (args.column.validator) {
	        var validationResults = args.column.validator($input.val());
	        if (!validationResults.valid) {
	          return validationResults;
	        }
	      }

	      return {
	        valid: true,
	        msg: null
	      };
	    };

	    this.init();
	  }
  
  
  
  
function PasswordEditor(args) {
    var $input;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $input = $("<INPUT type=password class='editor-text' />")
          .appendTo(args.container)
          .bind("keydown.nav", function (e) {
            if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
              e.stopImmediatePropagation();
            }
          })
          .focus()
          .select();
    };

    this.destroy = function () {
      $input.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.getValue = function () {
      return $input.val();
    };

    this.setValue = function (val) {
      $input.val(val);
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field] || "";
      $input.val(defaultValue);
      $input[0].defaultValue = defaultValue;
      $input.select();
    };

    this.serializeValue = function () {
     	return $input.val();
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };

    this.validate = function () {
      if (args.column.validator) {
        var validationResults = args.column.validator($input.val());
        if (!validationResults.valid) {
          return validationResults;
        }
      }
      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  function IntegerEditor(args) {
    var $input;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $input = $("<INPUT type=text class='editor-text' />");

      $input.bind("keydown.nav", function (e) {
        if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
          e.stopImmediatePropagation();
        }
      });

      $input.appendTo(args.container);
      $input.focus().select();
    };

    this.destroy = function () {
      $input.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field];
      $input.val(defaultValue);
      $input[0].defaultValue = defaultValue;
      $input.select();
    };

    this.serializeValue = function () {
      return parseInt($input.val(), 10) || 0;
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };

    this.validate = function () {
      if (isNaN($input.val())) {
        return {
          valid: false,
          msg: "Please enter a valid integer"
        };
      }

      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  function DoubleEditor(args) {
	    var $input;
	    var defaultValue;
	    var scope = this;

	    this.init = function () {
	      $input = $("<INPUT type=text class='editor-text' />");

	      $input.bind("keydown.nav", function (e) {
	        if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
	          e.stopImmediatePropagation();
	        }
	      });

	      $input.appendTo(args.container);
	      $input.focus().select();
	    };

	    this.destroy = function () {
	      $input.remove();
	    };

	    this.focus = function () {
	      $input.focus();
	    };

	    this.loadValue = function (item) {
	      defaultValue = item[args.column.field];
	      $input.val(defaultValue);
	      $input[0].defaultValue = defaultValue;
	      $input.select();
	    };

	    this.serializeValue = function () {
	      return parseFloat($input.val(), 10) || 0;
	    };

	    this.applyValue = function (item, state) {
	      item[args.column.field] = state;
	    };

	    this.isValueChanged = function () {
	      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
	    };

	    this.validate = function () {
	      if (isNaN($input.val())) {
	        return {
	          valid: false,
	          msg: "Please enter a valid double"
	        };
	      }

	      return {
	        valid: true,
	        msg: null
	      };
	    };

	    this.init();
	  }
  
  function DateEditor(args) {
    var $input;
    var defaultValue;
    var scope = this;
    var calendarOpen = false;

    this.init = function () {
      $input = $("<INPUT type=text class='editor-text' />");
      $input.appendTo(args.container);
      $input.focus().select();
      $input.datepicker({
        showOn: "button",
        dateFormat: 'yy-mm-dd',
        buttonImageOnly: true,
        buttonImage: "resources/images/calendar.gif",
        beforeShow: function () {
          calendarOpen = true;
        },
        onClose: function () {
          calendarOpen = false;
        }
      });
      $input.width($input.width() - 18);
    };

    this.destroy = function () {
      $.datepicker.dpDiv.stop(true, true);
      $input.datepicker("hide");
      $input.datepicker("destroy");
      $input.remove();
    };

    this.show = function () {
      if (calendarOpen) {
        $.datepicker.dpDiv.stop(true, true).show();
      }
    };

    this.hide = function () {
      if (calendarOpen) {
        $.datepicker.dpDiv.stop(true, true).hide();
      }
    };

    this.position = function (position) {
      if (!calendarOpen) {
        return;
      }
      $.datepicker.dpDiv
          .css("top", position.top + 30)
          .css("left", position.left);
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field];
      $input.val(defaultValue);
      $input[0].defaultValue = defaultValue;
      $input.select();
    };

    this.serializeValue = function () {
      return $input.val();
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };

    this.validate = function () {
      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  function YesNoSelectEditor(args) {
	    var $select;
	    var defaultValue;
	    var scope = this;

	    this.init = function () {
	      $select = $("<SELECT tabIndex='0' class='editor-yesno'><OPTION value='yes'>Yes</OPTION><OPTION value='no'>No</OPTION></SELECT>");
	      $select.appendTo(args.container);
	      $select.focus();
	    };

	    this.destroy = function () {
	      $select.remove();
	    };

	    this.focus = function () {
	      $select.focus();
	    };

	    this.loadValue = function (item) {
	      $select.val((defaultValue = item[args.column.field]) ? "yes" : "no");
	      $select.select();
	    };

	    this.serializeValue = function () {
	      return ($select.val() == "yes");
	    };

	    this.applyValue = function (item, state) {
	      item[args.column.field] = state;
	    };

	    this.isValueChanged = function () {
	      return ($select.val() != defaultValue);
	    };

	    this.validate = function () {
	      return {
	        valid: true,
	        msg: null
	      };
	    };

	    this.init();
	  }

  function CheckboxEditor(args) {
    var $select;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $select = $("<INPUT type=checkbox value='true' class='editor-checkbox' >角色1<INPUT type=checkbox value='true' class='editor-checkbox' >角色2<INPUT type=checkbox value='true' class='editor-checkbox' >角色3");
      $select.appendTo(args.container);
      $select.focus();
    };

    this.destroy = function () {
      $select.remove();
    };

    this.focus = function () {
      $select.focus();
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field];
      if (defaultValue) {
        $select.attr("checked", "checked");
      } else {
        $select.removeAttr("checked");
      }
    };

    this.serializeValue = function () {
      return $select.attr("checked");
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return ($select.attr("checked") != defaultValue);
    };

    this.validate = function () {
      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  function PercentCompleteEditor(args) {
    var $input, $picker;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $input = $("<INPUT type=text class='editor-percentcomplete' />");
      $input.width($(args.container).innerWidth() - 25);
      $input.appendTo(args.container);

      $picker = $("<div class='editor-percentcomplete-picker' />").appendTo(args.container);
      $picker.append("<div class='editor-percentcomplete-helper'><div class='editor-percentcomplete-wrapper'><div class='editor-percentcomplete-slider' /><div class='editor-percentcomplete-buttons' /></div></div>");

      $picker.find(".editor-percentcomplete-buttons").append("<button val=0>Not started</button><br/><button val=50>In Progress</button><br/><button val=100>Complete</button>");

      $input.focus().select();

      $picker.find(".editor-percentcomplete-slider").slider({
        orientation: "vertical",
        range: "min",
        value: defaultValue,
        slide: function (event, ui) {
          $input.val(ui.value)
        }
      });

      $picker.find(".editor-percentcomplete-buttons button").bind("click", function (e) {
        $input.val($(this).attr("val"));
        $picker.find(".editor-percentcomplete-slider").slider("value", $(this).attr("val"));
      })
    };

    this.destroy = function () {
      $input.remove();
      $picker.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      $input.val(defaultValue = item[args.column.field]);
      $input.select();
    };

    this.serializeValue = function () {
      return parseInt($input.val(), 10) || 0;
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ((parseInt($input.val(), 10) || 0) != defaultValue);
    };

    this.validate = function () {
      if (isNaN(parseInt($input.val(), 10))) {
        return {
          valid: false,
          msg: "Please enter a valid positive number"
        };
      }

      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  /*
   * An example of a "detached" editor.
   * The UI is added onto document BODY and .position(), .show() and .hide() are implemented.
   * KeyDown events are also handled to provide handling for Tab, Shift-Tab, Esc and Ctrl-Enter.
   */
  function LongTextEditor(args) {
    var $input, $wrapper;
    var defaultValue;
    var scope = this;

    this.init = function () {
      var $container = $("body");

      $wrapper = $("<DIV style='z-index:10000;position:absolute;background:white;padding:5px;border:3px solid gray; -moz-border-radius:10px; border-radius:10px;'/>")
          .appendTo($container);

      $input = $("<TEXTAREA hidefocus rows=5 style='backround:white;width:250px;height:80px;border:0;outline:0'>")
          .appendTo($wrapper);

      $("<DIV style='text-align:right'><BUTTON>Save</BUTTON><BUTTON>Cancel</BUTTON></DIV>")
          .appendTo($wrapper);

      $wrapper.find("button:first").bind("click", this.save);
      $wrapper.find("button:last").bind("click", this.cancel);
      $input.bind("keydown", this.handleKeyDown);

      scope.position(args.position);
      $input.focus().select();
    };

    this.handleKeyDown = function (e) {
      if (e.which == $.ui.keyCode.ENTER && e.ctrlKey) {
        scope.save();
      } else if (e.which == $.ui.keyCode.ESCAPE) {
        e.preventDefault();
        scope.cancel();
      } else if (e.which == $.ui.keyCode.TAB && e.shiftKey) {
        e.preventDefault();
        grid.navigatePrev();
      } else if (e.which == $.ui.keyCode.TAB) {
        e.preventDefault();
        grid.navigateNext();
      }
    };

    this.save = function () {
      args.commitChanges();
    };

    this.cancel = function () {
      $input.val(defaultValue);
      args.cancelChanges();
    };

    this.hide = function () {
      $wrapper.hide();
    };

    this.show = function () {
      $wrapper.show();
    };

    this.position = function (position) {
      $wrapper
          .css("top", position.top - 5)
          .css("left", position.left - 5)
    };

    this.destroy = function () {
      $wrapper.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      $input.val(defaultValue = item[args.column.field]);
      $input.select();
    };

    this.serializeValue = function () {
      return $input.val();
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };

    this.validate = function () {
      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }
  function LookupSelectEditor(args) {
	    var $select;
	    var defaultValue;
	    var lookupIdArray=[];
	    var lookupNameArray=[];
	    
	    loopupArray=args.column.lookupArray;
	    $.each(loopupArray, function(k, v) {
	    	lookupIdArray.push(v.id);
	    	lookupNameArray.push(v.name);
		  });
	    
	    var scope = this;
	    this.init = function () {
	    	$select = $("<SELECT class='editor-yesno'></SELECT>");
	    	$select.empty();
	    	for(var i=0;i<lookupIdArray.length;i++){
	    		$option = $("<OPTION value="+lookupIdArray[i]+">"+lookupNameArray[i]+"</OPTION>");
	    		$select.append($option);
	    	}
	      $select.appendTo(args.container);
	      $select.focus();
	    };
	    this.destroy = function () {
	      $select.remove();
	    };

	    this.focus = function () {
	      $select.focus();
	    };

	    this.loadValue = function (item) {
	    	for(var i=0;i<lookupIdArray.length;i++){
	    		if(item[args.column.field]==lookupIdArray[i]){
		    		$select.val(lookupIdArray[i]);
	    		}
	    	}
		     $select.select();
	    };

	    this.serializeValue = function () {
	    	for(var i=0;i<lookupIdArray.length;i++){
			      if($select.val() == lookupIdArray[i]){
			    	  return lookupIdArray[i];
			      }
	    	}
	    };

	    this.applyValue = function (item, state) {
	      item[args.column.field] = state;
	    };

	    this.isValueChanged = function () {
	      return ($select.val() != defaultValue);
	    };

	    this.validate = function () {
	      return {
	        valid: true,
	        msg: null
	      };
	    };

	    this.init();
	  };
  function InputFileEditor(args) {
	    var $inputfile;
	    var $submit;
	    var defaultValue;
	    var id;
	    var scope = this;
	    this.init = function () {
	    	$inputfile = $("<input type='file' style='width:"+(args.column.width-110)+"px' id='file' name='file'></input>");
	    	$submit = $("<input type='button' id='submit' value='提交'></input>");
	    	$download = $("<input type='button' id='download' value='下载'></input>");
	    	$submit.bind("click",function(){
	    		upload();
	    	});
	    	$download.bind("click",function(){
	    		download();
	    	});
	    	$inputfile.appendTo(args.container);
	    	$submit.appendTo(args.container);
	    	$download.appendTo(args.container);
	    };
	    
	    var urlMap;
	    if(args.column.url == undefined){
	    	urlMap = "uploadHkrtxls";
	    }else{
	    	urlMap = args.column.url;
	    }
	    function download(){
	    	if(defaultValue== undefined){
	    		showMessageOfParent("不能下载");
	    		setTimeout("hideMessageOfParent()", 4000);
	    		return;
	    	}
	    	showMessageOfParent("正在下载......");
	    	var url = contextPath + '/downloadxls.jhtml?id='+id;
	    	window.location.href=url;
	    	setTimeout("hideMessageOfParent()", 4000);
	    }
	    function upload(){
	    	$.ajaxFileUpload({
				url : contextPath + '/'+urlMap+'.jhtml?id='+id,
				secureuri : false,
				fileElementId : 'file',
				dataType : 'json',
				success : function(data) {
					var message = jQuery.parseJSON(data);
					if(null==message){
						message = jQuery.parseJSON(data.responseText);
					}
					alert(message.value);
					defaultValue=message.src;
				},
				error : function(data) {
					var message = jQuery.parseJSON(data);
					if(null==message){
						message = jQuery.parseJSON(data.responseText);
					}
					alert(message.value);
				}
	    	});
	    }
	    
	    this.destroy = function () {
	    	$inputfile.remove();
	    	$submit.remove();
	    };

	    this.loadValue = function (item) {
	    	defaultValue=item[args.column.field];
	    	id=item.id;
	      };

	      this.serializeValue = function () {
	    	 // alert("serializeValue");
	      };

	    this.applyValue = function (item, state) {
	      item[args.column.field] = defaultValue;
	    };

	    this.isValueChanged = function () {
	      return true;
	    };

	    this.validate = function () {
	      return {
	        valid: true,
	        msg: null
	      };
	    };

	    this.init();
	  }
  function SelectMulEditor(args) {
	    var $select;
	    var defaultValue;
	    var firstIdArray=[];
	    var firstNameArray=[];
	    
	    var secondIdArray=[];
	    
	    loopupArray=args.column.firstArray;
	    $.each(loopupArray, function(k, v) {
	    	firstIdArray.push(v.id);
	    	firstNameArray.push(v.name);
		  });
	    
	    secondArray=args.column.secondArray;
	    $.each(secondArray, function(k, v) {
	    	secondIdArray.push(v.bankMerchantId);
		  });
	    var scope = this;
	    this.init = function () {
	    	$select = $("<SELECT class='editor-yesno' style='width:100px;'></SELECT>");
	    	$selectSecond = $("<SELECT class='editor-yesno' style='width:180px;margin-left: 10px;'></SELECT>");
	    	$select.empty();
	    	for(var i=0;i<firstIdArray.length;i++){
	    		$option = $("<OPTION value="+firstIdArray[i]+">"+firstNameArray[i]+"</OPTION>");
	    		$select.append($option);
	    	}
	    	
	    	
	    	$select.appendTo(args.container);
	    	$select.bind("change",function(){
	    		firstId = $select.val();
	    		$selectSecond.empty();
		    	$.ajax({
		    		url : contextPath + '/getBankMerchantCode.jhtml?r=' + Math.random(),
		    		async : false,
		    		type : "POST",
		    		data : {"id" : firstId},
		    		success : function(result) {
		    			$.each(result, function(k, v) {
		    				$option = $("<OPTION value="+v.bankMerchantId+">"+v.bankMerchantId+"</OPTION>");
		    				$selectSecond.append($option);
		    			});
		    		}
		    	});
	    	});
	    	if(args.item.id==undefined){
	    		$select.trigger("change");
	    	}
	    		
	      $selectSecond.appendTo(args.container);
	      $select.focus();
	    };
	    this.destroy = function () {
	      $select.remove();
	    };

	    this.focus = function () {
	      $select.focus();
	    };

	    this.loadValue = function (item) {
	    	value= item[args.column.field];
	    	var index = value.indexOf("/");
		  	  firstValue = value.substring(0,index);
		  	  secondValue = value.substring(index+1,value.length);
	    	for(var i=0;i<firstIdArray.length;i++){
	    		if(firstValue==firstIdArray[i]){
		    		$select.val(firstIdArray[i]);
	    		}
	    	}
		     $select.select();
	    	 $.ajax({
	    		 url : contextPath + '/getBankMerchantCode.jhtml?r=' + Math.random(),
	    		 async : false,
	    		 type : "POST",
	    		 data : {"id" : $select.val()},
	    		 success : function(result) {
	    			 $.each(result, function(k, v) {
	    				 $option = $("<OPTION value="+v.bankMerchantId+">"+v.bankMerchantId+"</OPTION>");
	    				 $selectSecond.append($option);
	    			 });
	    		 }
	    	 });
		     
		     for(var i=0;i<secondIdArray.length;i++){
		    		if(secondValue==secondIdArray[i]){
		    			$selectSecond.val(secondIdArray[i]);
		    		}
		    	}
		     $selectSecond.select();
	    };
	    this.serializeValue = function () {
	    	var first,second;
	    	for(var i=0;i<firstIdArray.length;i++){
			      if($select.val() == firstIdArray[i]){
			    	  first = firstIdArray[i];
			      }
	    	}
	    	for(var i=0;i<secondIdArray.length;i++){
			      if($selectSecond.val() == secondIdArray[i]){
			    	  second = secondIdArray[i];
			      }
	    	}
	    	return first+"/"+second;
	    };

	    this.applyValue = function (item, state) {
	      item[args.column.field] = state;
	    };

	    this.isValueChanged = function () {
	      return ($select.val()+"/"+ $selectSecond.val()!= defaultValue);
	    };

	    this.validate = function () {
	      return {
	        valid: true,
	        msg: null
	      };
	    };

	    this.init();
	  };
})(jQuery);
