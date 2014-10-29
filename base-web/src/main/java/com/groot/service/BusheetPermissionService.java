package com.groot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootBusheetPermissionDAO;
import com.groot.module.domain.GrootBusheetPermission;
import com.groot.module.domain.GrootBusheetPermissionExample;
import com.groot.module.domain.GrootSystemUser;


@Transactional(readOnly=true)
@Service
public class BusheetPermissionService {
	@Autowired
	public GrootBusheetPermissionDAO busheetPermissionDAO;
	@Autowired
	public SelectDAO selectDao;
	
	@Transactional
	public int insert(GrootBusheetPermission busheet) {
		busheetPermissionDAO.insert(busheet);
		return 1;
	}
	
	@Transactional
	public int update(GrootBusheetPermission busheet) {
		return busheetPermissionDAO.updateByPrimaryKeySelective(busheet);
	}
	
	@Transactional
	public int removeByPrimaryKey(String id) {
		return busheetPermissionDAO.deleteByPrimaryKey(id);
	}
	/**
	 * 校验用户是否重复
	 * @param busheet
	 * @return
	 */
	public boolean getBusheetPermissionByuserId(GrootBusheetPermission busheet){
		GrootBusheetPermissionExample example = new GrootBusheetPermissionExample();
		if(busheet.getId()==null){
			example.createCriteria().andUserIdEqualTo(busheet.getUserId()).andBusheetIdEqualTo(busheet.getBusheetId());
		}else{
			example.createCriteria().andUserIdEqualTo(busheet.getUserId()).andBusheetIdEqualTo(busheet.getBusheetId()).andIdNotEqualTo(busheet.getId());
		}
		List<GrootBusheetPermission> ls = busheetPermissionDAO.selectByExample(example);
		if(ls!=null&&ls.size()>0){
			return true;
		}
		return false;
	}
	
	public GrootBusheetPermission getPermissionByUserIdOrBesheetId(String sheetCode, HttpSession session) {
		GrootSystemUser systemUser = (GrootSystemUser)session.getAttribute("USER_SESSION_KEY");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sheetCode", sheetCode);
		paramMap.put("userId", systemUser.getId());
		GrootBusheetPermission bp = (GrootBusheetPermission) selectDao.object("SYSTEM.getPermissionByUserIdOrBesheetId", paramMap);
		return bp;
	}
}
