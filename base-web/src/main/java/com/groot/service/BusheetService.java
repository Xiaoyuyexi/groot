package com.groot.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootBusheetDAO;
import com.groot.module.domain.GrootBusheet;
import com.groot.module.domain.GrootBusheetExample;
import com.groot.module.domain.GrootSystemUser;


@Transactional(readOnly=true)
@Service
public class BusheetService {
	
	@Autowired
	public GrootBusheetDAO busheetDao;
	@Autowired
	public SelectDAO selectDao;
	
	
	@Transactional
	public List<GrootBusheet> getBusheetAll() {
		return busheetDao.selectByExample(null);
	}
	
	@Transactional
	public int insert(GrootBusheet busheet, GrootSystemUser currentUser) {
		String maxCode = (String)selectDao.object("SYSTEM.getMaxBusheetCode");
		if(StringUtils.isEmpty(maxCode)){
			maxCode = "10000001";
		}else{
			int a = Integer.valueOf(maxCode).intValue() + 1;
			maxCode = String.valueOf(a);
		}
		busheet.setBusheetCode(maxCode);
		busheet.setCreateTime(new Date());
		busheet.setCreateUserId(currentUser.getId());
		busheetDao.insert(busheet);
		return 1;
	}
	
	public GrootBusheet getBusheetByCode(String code){
		GrootBusheetExample example = new GrootBusheetExample();
		example.createCriteria().andBusheetCodeEqualTo(code);
		List<GrootBusheet> list = busheetDao.selectByExample(example);
		if(0!=list.size()){
			return list.get(0);
		}
		return null;
		
	}
	
	@Transactional
	public int update(GrootBusheet busheet, GrootSystemUser currentUser) {
		busheet.setUpdateTime(new Date());
		busheet.setUpdateUserId(currentUser.getId());
		return busheetDao.updateByPrimaryKeySelective(busheet);
	}
	
	@Transactional
	public int removeByPrimaryKey(String id) {
		return busheetDao.deleteByPrimaryKey(id);
	}
	
	@Transactional
	public GrootBusheet getBusheet(String id) {
		return busheetDao.selectByPrimaryKey(id);
	}
	
}
