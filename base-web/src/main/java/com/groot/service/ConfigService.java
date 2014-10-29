package com.groot.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.groot.module.dao.GrootSystemConfigDAO;
import com.groot.module.domain.GrootSystemConfig;
import com.groot.module.domain.GrootSystemConfigExample;
import com.web.utils.lang.StringUtils;

@Service
public class ConfigService {

	@Autowired
	public GrootSystemConfigDAO systemConfigDAO;
	
	
	/**
	 * 保存或更新
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveOrUpdate(GrootSystemConfig config){
		config.setCreateTime(new Date());
		if(StringUtils.isEmpty(config.getId())){
			config.setId(StringUtils.randomUUID());
			systemConfigDAO.insert(config);
		}else{
			systemConfigDAO.updateByPrimaryKeySelective(config);
		}
	}
	
	
	/**
	 * 根据CODE更新系统配置
	 * @param config
	 * @return
	 */
	@Transactional
	public int updateByCode(GrootSystemConfig config) {
		GrootSystemConfigExample ex = new GrootSystemConfigExample();
		ex.createCriteria().andConfigCodeEqualTo(config.getConfigCode());
		return systemConfigDAO.updateByExampleSelective(config, ex);
	}
	
	
	/**
	 * 获取批次配置
	 * @return
	 */
	public GrootSystemConfig getSystemConfig(){
		@SuppressWarnings("unchecked")
		List<GrootSystemConfig> list = systemConfigDAO.selectByExample(new GrootSystemConfigExample());
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public int removeByPrimaryKey(String id) {
		return systemConfigDAO.deleteByPrimaryKey(id);

	}
	
	/**
	 * 根据code获取配置变量
	 * @param code
	 * @return
	 */
	public GrootSystemConfig getSystemConfig(String code){
		GrootSystemConfigExample example = new GrootSystemConfigExample();
		example.createCriteria().andConfigCodeEqualTo(code);
		@SuppressWarnings("unchecked")
		List<GrootSystemConfig> list = systemConfigDAO.selectByExample(example);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	
	
}
