package com.groot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootBusheetWorkflowDAO;
import com.groot.module.domain.GrootBusheetWorkflow;
import com.groot.module.domain.GrootBusheetWorkflowExample;

@Transactional(readOnly=true)
@Service
public class BusheetWorkflowService {
	@Autowired
	public SelectDAO selectDao;
	@Autowired
	public GrootBusheetWorkflowDAO busheetWorkflowDao;
	
	@Transactional
	public List<GrootBusheetWorkflow> getBusheetWorkflowList(Map<String, Object> mapParam) {
		return selectDao.list("SYSTEM.getBusheetWorkflowList", mapParam);
	}
	@Transactional
	public int insert(GrootBusheetWorkflow busheetWorkflow) {
		busheetWorkflowDao.insert(busheetWorkflow);
		return 1;
	}
	
	@Transactional
	public int update(GrootBusheetWorkflow busheetWorkflow) {
		return busheetWorkflowDao.updateByPrimaryKeySelective(busheetWorkflow);
	}
	
	@Transactional
	public int removeByPrimaryKey(String id) {
		return busheetWorkflowDao.deleteByPrimaryKey(id);
	}
	/**
	 * 查询某个单据是否存在子单据
	 * @param sheetId
	 * @return
	 */
	public int selectBySheetId(String sheetId){
		GrootBusheetWorkflowExample example=new GrootBusheetWorkflowExample();
		example.createCriteria().andBusheetIdEqualTo(sheetId);
		return busheetWorkflowDao.selectByExample(example).size();
		
	}
}
