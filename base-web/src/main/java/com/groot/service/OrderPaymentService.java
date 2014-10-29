package com.groot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groot.busidao.SelectDAO;
import com.groot.module.domain.GrootSystemLookupCde;
import com.groot.vo.OrderPaymentVO;

@Service
public class OrderPaymentService {
	
	@Autowired
	public SelectDAO selectDao;
	@Autowired
	public LookupCdeService lookupCdeService;
	
	public OrderPaymentVO getOrderById(String id){
		return (OrderPaymentVO) selectDao.object("SYSTEM.getOrderById", id);
	}
	
	
	public List<OrderPaymentVO> getExportPosOrder(Map<String, Object> mapParam){
		List<OrderPaymentVO> list = selectDao.list("SYSTEM.getOrderPaymentListForPage", mapParam);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("code", "10311001");
		List<GrootSystemLookupCde> orderTypeList= lookupCdeService.getLookUpCdeIntegerKeyList(map2);//交易类型
		for(OrderPaymentVO vo : list){
			for(GrootSystemLookupCde orderType : orderTypeList){
				if(orderType.getCdeCode().toString().equals(vo.getOrderType())){
					vo.setOrderType(orderType.getCdeName());
				}
			}
		}
		map2.put("code", "10661601");
		List<GrootSystemLookupCde> statusList= lookupCdeService.getLookUpCdeIntegerKeyList(map2);//交易状态
		for(OrderPaymentVO vo : list){
			for(GrootSystemLookupCde status : statusList){
				if(status.getCdeCode().equals(vo.getStatus())){
					vo.setStatus(status.getCdeName());
				}
			}
		}
		
		return list;
	}
	
	
	
}
