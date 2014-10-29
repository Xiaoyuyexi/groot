package com.groot.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.service.ExcelExportUtils;
import com.groot.service.OrderPaymentService;
import com.groot.service.SelectService;
import com.groot.vo.Constant;
import com.groot.vo.DataSet;
import com.groot.vo.OrderPaymentVO;

@Controller
public class OrderPaymentController {
	
	protected static Logger logger4J = Logger.getLogger(OrderPaymentController.class.getSimpleName());
	@Autowired
	public SelectService<OrderPaymentVO> selectService;
	@Autowired
	public OrderPaymentService orderPaymentService;
	@Autowired
	public ExcelExportUtils  excelExportUtils;
	
	public static String sheetCode = "10000001";
	
	
	/**
	 *  订单查询页面
	 * 
	 * @return
	 */
	@RequiresPermissions("OrderPayment:menu")
	@RequestMapping(value = "/order_payment")
	public ModelAndView orderPayment() {
		return new ModelAndView("jsp/order_payment");
	}
	
	/**
	 * 
	 * @param mapParam
	 * @param name
	 * @param config
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrderPaymentListForPage", method = RequestMethod.POST)
	public DataSet<OrderPaymentVO> getOrderPaymentListForPage(@RequestParam Map<String, Object> mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		mapParam = this.organizeParam(mapParam);
		String countSqlId = "SYSTEM.getOrderPaymentCount";
		String listSqlId = "SYSTEM.getOrderPaymentListForPage";
		DataSet<OrderPaymentVO> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, pageNumber, Constant.DEFAULT_PAGE_SIZE);
		return dataSet;
	}
	
	private Map<String, Object> organizeParam(Map<String, Object> mapParam){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (!"".equals(mapParam.get("createStartTime")) && null != mapParam.get("createStartTime")) {
				mapParam.put("createStartTime", sdf.parse(mapParam.get("createStartTime").toString()));
			}
			if (!"".equals(mapParam.get("createEndTime")) && null != mapParam.get("createEndTime")) {
				mapParam.put("createEndTime", sdf.parse(mapParam.get("createEndTime").toString()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mapParam;
	}
	
	
	@RequestMapping(value = "/order_info")
	public ModelAndView orderInfo(String id) {
		OrderPaymentVO order = orderPaymentService.getOrderById(id);
		return new ModelAndView("jsp/order_info","pos",order);
	}
	
	/**
	 * 导出
	 * @param amsAgent
	 * @param result
	 * @param session
	 * @return
	 */
	@RequiresPermissions("OrderPayment:export")
	@RequestMapping("exportOrderPayment")
	public void exportOrderPayment(@RequestParam Map<String, Object> mapParam, HttpServletRequest request,HttpServletResponse response){
//		String merchantName = (String)mapParam.get("merchantName");
//		try {
//			merchantName = URLDecoder.decode(merchantName, "UTF-8");
//			mapParam.put("merchantName", merchantName);	
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		mapParam = this.organizeParam(mapParam);
		List<OrderPaymentVO> list = orderPaymentService.getExportPosOrder(mapParam);
		
		if(StringUtils.isEmpty(sheetCode))return;
		String fileName = "order_"+DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd")+ ".xls";
		
		excelExportUtils.export(list, fileName, sheetCode, request, response);
	}
}
