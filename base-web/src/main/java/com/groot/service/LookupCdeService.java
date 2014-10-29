package com.groot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootSystemLookupCdeDAO;
import com.groot.module.dao.GrootSystemLookupTypeDAO;
import com.groot.module.domain.GrootSystemLookupCde;
import com.groot.module.domain.GrootSystemLookupCdeExample;
import com.groot.module.domain.GrootSystemLookupType;
import com.groot.module.domain.GrootSystemLookupTypeExample;
import com.web.utils.lang.StringUtils;

@Transactional(readOnly = true)
@Service
public class LookupCdeService {

	@Autowired
	public SelectDAO selectDao;
	@Autowired
	public GrootSystemLookupTypeDAO lookupTypeDao;
	@Autowired
	public GrootSystemLookupCdeDAO lookupCdeDao;
	


	@Transactional
	@TriggersRemove(cacheName = "lookupCdeService", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public int insert(GrootSystemLookupCde lookupCde) {
		lookupCde.setId(StringUtils.randomUUID());
		lookupCdeDao.insert(lookupCde);
		return 1;
	}

	@Transactional
	@TriggersRemove(cacheName = "lookupCdeService", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public int update(GrootSystemLookupCde lookupCde) {
		lookupCdeDao.updateByPrimaryKeySelective(lookupCde);
		return 1;
	}

	@Transactional
	@TriggersRemove(cacheName = "lookupCdeService", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public int removeByPrimaryKey(String id) {
		return lookupCdeDao.deleteByPrimaryKey(id);
	}

	public String getLookupType(String LookupId){
		return lookupTypeDao.selectByPrimaryKey(LookupId).getTypeCode();
	}
	/**
	 * 查询字典域的子级是否存在
	 * 
	 * @param lookuptype
	 * @return
	 */
	@Transactional
	public int selectByExample(GrootSystemLookupType lookuptype) {
		GrootSystemLookupCdeExample example = new GrootSystemLookupCdeExample();
		example.createCriteria().andTypeIdEqualTo(lookuptype.getId());
		@SuppressWarnings("unchecked")
		List<GrootSystemLookupCdeExample> list = lookupCdeDao.selectByExample(example);
		if (list.size() > 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@Transactional
	public Integer getMaxLookUpCdeIntegerKeyBytypeId(String TypeId) {
		return (Integer) selectDao.object("SYSTEM.getMaxLookUpCdeIntegerKeyBytypeId", TypeId);
	}
	
	@Transactional
	public String getMaxLookUpCdeCodeBytypeId(String TypeId) {
		return (String) selectDao.object("SYSTEM.getMaxLookUpCdeCodeBytypeId", TypeId);
	}
	
	/**
	 * 获取某个域的字典
	 * 
	 * @return
	 */
	public List<GrootSystemLookupCde> getLookupCdeList(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<GrootSystemLookupCde> list = selectDao.list("SYSTEM.getLookUpCdeList", map);
		return list;
	}
	
	/**
	 * 获取某个域的字典
	 * 
	 * @return
	 */
	public GrootSystemLookupType getLookupCdeByCode(String typeCode) {
		GrootSystemLookupTypeExample exa = new GrootSystemLookupTypeExample();
		exa.createCriteria().andTypeCodeEqualTo(typeCode);
		@SuppressWarnings("unchecked")
		List<GrootSystemLookupType> list = lookupTypeDao.selectByExample(exa);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取某个域的数字字典
	 * 
	 * @return
	 */
	@Transactional
	@Cacheable(cacheName = "lookupCdeService")
	public List<GrootSystemLookupCde> getLookUpCdeIntegerKeyList( Map<String, Object> map) {
		return selectDao.list("SYSTEM.getLookUpCdeList", map);
	}
	
}
