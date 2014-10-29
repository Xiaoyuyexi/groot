$(function () {
	$("#pending_audit_merchant,#channel_fail_merchant").hover(function(){$(this).css("color","red");},function(){$(this).css("color","#005590");});
//	if (checkPermission("HomePage:posOrderChar") == true) {
		getPosOrderHighCharts();
//	}
	getSystemVersion();
	$("#home_li").dblclick(function() {
		$("#closeTop").parent().show();
		$("#highchartPosOrder").show();
//		if (checkPermission("HomePage:posOrderChar") == true) {
			getPosOrderHighCharts();
//		}
	});
	$("#closeTop").click(function(){
		$(this).parent().hide();
	});
	$("#closeChartPosOrder").click(function(){
		$("#highchartPosOrder").hide();
	});
});

function getSystemVersion(){
	$.ajax({
		url:contextPath + "/getVersionNumber.jhtml",
		type:"POST",
		async:false,
		data:{"code":"10000011"},
		success: function(result){
			$("#systemVersion").text(result.configValue);
		}
	});
}
function getPosOrderHighCharts(){
    var dataDate = [];
	var dataVolume = [];
	var dataAmount = [];
	
	var tempData1 = {};
	tempData1.systemTrxTime = '2014-05-15';
	tempData1.orderSum = 1;
	tempData1.trxAmtSum = 0.01;
	var tempData2 = {};
	tempData2.systemTrxTime = '2014-05-20';
	tempData2.orderSum = 4;
	tempData2.trxAmtSum = 0.07;
	var tempArray = [];
	tempArray.push(tempData1);
	tempArray.push(tempData2);
	
    $(document).ready(function() {
    	showMessageOfParent("正在载入...");
//    	$.ajax({
//    		url:contextPath + "/getPosOrderChar.jhtml",
//    		type:"POST",
//    		async:false,
//    		dataType: "json",
//    		success: function(result){
//    			$.each(result, function(k, v) {
//    				dataDate[k] = Date.parse(parseDate(v.systemTrxTime));
//    				dataVolume[k] = v.orderSum;
//    				dataAmount[k] = fmoneyDecimal(v.trxAmtSum/10000,6);
//    			});
//    		}
//    	});
    	$.each(tempArray, function(k, v) {
			dataDate[k] = Date.parse(parseDate(v.systemTrxTime));
			dataVolume[k] = v.orderSum;
			dataAmount[k] = fmoneyDecimal(v.trxAmtSum/10000,6);
		});
    	hideMessageOfParent();
    	
    	
        new Highcharts.Chart({
            chart: {
                renderTo: 'posOrderChar',
                fontFamily: "'Microsoft Yahei',verdana"
            },
            exporting: {
            	enabled : false
            },
            title: {
                text: '数据分析',//POS交易订单分析
                style: {
                	fontSize: '18px',
                	fontFamily: "'Microsoft Yahei',verdana"
                }
            },
            xAxis: [{
                categories: dataDate,
                type: 'datetime',
		        labels : {
					formatter : function() {
					  return Highcharts.dateFormat('%Y-%m-%d', this.value);
					}
		        }
            }],
            yAxis: [{ // Primary yAxis
            	 title: {
                     text: '交易量',
                     style: {
                         color: '#89A54E'
                     }
                 },
                 labels: {
                    formatter: function() {
                        return this.value;
                    },
                    style: {
                        color: '#89A54E'
                    }
                 },
                 min:0 // 定义最小值 
            }, { // Secondary yAxis
                title: {
                    text: '交易额',
                    style: {
                        color: '#4572A7'
                    }
                },
                labels: {
                    formatter: function() {
                        return this.value;
                    },
                    style: {
                        color: '#4572A7'
                    }
                },
                opposite: true
            }],
            tooltip: {
                formatter: function() {
                    return ''+
                         Highcharts.dateFormat('%Y-%m-%d', this.x) +': ' +
                        (this.series.name == '交易量' ? this.y +' 笔' : fmoney(this.y*10000,2) +'元');
                }
            },
            series: [{
                name: '交易额',
                color: '#4572A7',
                type: 'column',
                yAxis: 1,
                data: dataAmount
            },{
                name: '交易量',
                color: '#89A54E',
                type: 'spline',
                data: dataVolume
            }]
        });
    });
}




//日期格式转换
function parseDate(str) {
	str = str.split('-');
	var date = new Date();
	date.setUTCFullYear(str[0], str[1] - 1, str[2]);
	date.setUTCHours(0, 0, 0, 0);
	return date;
}


