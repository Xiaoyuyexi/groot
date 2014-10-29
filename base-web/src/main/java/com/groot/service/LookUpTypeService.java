package com.groot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootSystemLookupTypeDAO;
import com.groot.module.domain.GrootSystemLookupType;
import com.groot.module.domain.GrootSystemLookupTypeExample;


@Transactional(readOnly=true)
@Service
public class LookUpTypeService {
	@Autowired
	public GrootSystemLookupTypeDAO lookUpTypeDao;
	@Autowired
	public SelectDAO selectDao;
	
	/**
	 * 插入新数据
	 * @param lookUpType
	 * @return
	 */
	@Transactional
	public int insert(GrootSystemLookupType lookUpType) {
		lookUpType.setStatus("10A");
		lookUpTypeDao.insert(lookUpType);
		return 1;
	}
	/**
	 * 更新数据
	 * @param lookUpType
	 * @return
	 */
	@Transactional
	public int update(GrootSystemLookupType lookUpType) {
		return lookUpTypeDao.updateByPrimaryKeySelective(lookUpType);
	}

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	@Transactional
	public int removeByPrimaryKey(String id) {
		return lookUpTypeDao.deleteByPrimaryKey(id);
	}
	
	
	/**
	 * 根据类型获得字典域
	 * @return
	 */
	@Transactional
	public List<GrootSystemLookupType>  getLookUpTypeList(String status){
		GrootSystemLookupTypeExample example=new GrootSystemLookupTypeExample();
		example.createCriteria().andStatusEqualTo(status);
		return lookUpTypeDao.selectByExample(example);
	}
	@Transactional
	public List<GrootSystemLookupType>  getAllLookUpTypeList(){
		return lookUpTypeDao.selectByExample(null);
	}
}
