/***
 * Contains basic SlickGrid formatters.
 * @module Formatters
 * @namespace Slick
 */

(function ($) {
  // register namespace
  $.extend(true, window, {
    "Slick": {
      "Formatters": {
        "PercentComplete": PercentCompleteFormatter,
        "PercentCompleteBar": PercentCompleteBarFormatter,
        "YesNo": YesNoFormatter,
        "Checkmark": CheckmarkFormatter,
        "Password":PasswordFormatter,
        "Lookup":LookupFormatter,
        "Date": DateFormatter,
        "Select":SelectFormatter,
        "HiddenText":HiddenTextFormatter
      }
    }
  });
  
  function DateFormatter(row, cell, value, columnDef, dataContext) {
	    return value.getFullYear() +"-"+(value.getMonth()+1)+"-"+value.getDate();
	  }
  
  function PasswordFormatter(row, cell, value, columnDef, dataContext) {
  	var s="●";
  	for(var i=1;i<value.length;i++){
  		var s=s+"●";
  	}
 		return s;
  }
  function PercentCompleteFormatter(row, cell, value, columnDef, dataContext) {
    if (value == null || value === "") {
      return "-";
    } else if (value < 50) {
      return "<span style='color:red;font-weight:bold;'>" + value + "%</span>";
    } else {
      return "<span style='color:green'>" + value + "%</span>";
    }
  }

  function PercentCompleteBarFormatter(row, cell, value, columnDef, dataContext) {
    if (value == null || value === "") {
      return "";
    }

    var color;

    if (value < 30) {
      color = "red";
    } else if (value < 70) {
      color = "silver";
    } else {
      color = "green";
    }

    return "<span class='percent-complete-bar' style='background:" + color + ";width:" + value + "%'></span>";
  }

  function YesNoFormatter(row, cell, value, columnDef, dataContext) {
    return value ? "Yes" : "No";
  }

  function CheckmarkFormatter(row, cell, value, columnDef, dataContext) {
    return value ? "<img src='../images/tick.png'>" : "";
  }
  function LookupFormatter(row, cell, value, columnDef, dataContext){
	  if (value == null || value === ""||value=="undefined") {
	      return "";
	    }
	  var name='';
	  var lookupArray=columnDef.lookupArray;
	  $.each(lookupArray, function(k, v) {
		  if(value===v.id){
			  name=v.name;
		  }
	  });
	  return name;
  }
  function SelectFormatter(row, cell, value, columnDef, dataContext){
	  if (value == null || value === ""||value=="undefined") {
	      return "";
	    }
	  var firsName='';
	  var secondName='';
	  var firstArray=columnDef.firstArray;
	  var secondArray=columnDef.secondArray;
	  var index = value.indexOf("/");
	  firstValue = value.substring(0,index);
	  secondValue = value.substring(index+1,value.length);
	  $.each(firstArray, function(k, v) {
		  if(firstValue===v.id){
			  firsName=v.name;
		  }
	  });
	  $.each(secondArray, function(k, v) {
		  if(secondValue===v.bankMerchantId){
			  secondName=v.bankMerchantId;
		  }
	  });
	  return firsName+"/"+secondName;
  }
  function HiddenTextFormatter(row, cell, value, columnDef, dataContext){
	  var name = "";
	  $.ajax({
		  url: contextPath +"/getDepartmentByCode.jhtml?r="+Math.random(),
		  async:false,
		  type:"POST",
		  data:{"code":dataContext.code},
		  success: function(result) {
			  if (result.name != undefined) {
				  name = result.name;
			  } else {
				  name = "组织结构不存在";
			  }
		  }
	  });
	  return name;
  }
})(jQuery);