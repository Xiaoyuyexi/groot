/**
 * 系统展示控制
 * @param obj
 */
function systemFilter(obj){
	var flag="false";
		$.ajax({
 		url : 'getSystemConfig.jhtml',
 		async : false,
		 	type : "POST",
		 	dataType : 'json',
 		data : {"code": obj},
 		success : function(result){
 			flag = result;
 		}
 	});
    return flag;
}