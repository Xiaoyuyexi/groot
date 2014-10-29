package com.groot.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.module.dao.GrootSystemUserDAO;
import com.groot.module.domain.GrootSystemUser;
import com.groot.module.domain.GrootSystemUserExample;
import com.groot.vo.UserVO;
import com.web.bo.AjaxMessage;
import com.web.utils.lang.SecurityUtils;


@Transactional(readOnly=true)
@Service
public class UserManageService {

	@Autowired
	public GrootSystemUserDAO systemUserDAO;
	@Autowired
	public SelectDAO selectDao;

	@Transactional
	public int insert(GrootSystemUser systemUser, GrootSystemUser currentUser) {
		systemUser.setId(com.web.utils.lang.StringUtils.randomUUID());
		//新增用户默认密码123456
		systemUser.setPassword(SecurityUtils.EncoderByMd5("123456"));
		systemUser.setCreateUserId(currentUser.getId());
		systemUser.setCreateTime(new Date());
		systemUser.setStatus("10A");
		systemUserDAO.insert(systemUser);
		return 1;
	}

	@Transactional
	public int update(GrootSystemUser user, GrootSystemUser currentUser) {
		user.setUpdateTime(new Date());
		user.setUpdateUserId(currentUser.getId());
		return systemUserDAO.updateByPrimaryKeySelective(user);
	}

	@Transactional
	public int removeByPrimaryKey(String id) {
		int i = 0;
		try{
			i = systemUserDAO.deleteByPrimaryKey(id);
		}catch(DataIntegrityViolationException e){
			i=-2;
		}
		return i;
	}
	
	public GrootSystemUser selectUserById(String userId){
		return systemUserDAO.selectByPrimaryKey(userId);
	}

	
	/**
	 * 获取全部用户
	 * @return
	 */
	public List<GrootSystemUser>  getAllSystemUser(){
		GrootSystemUserExample example=new GrootSystemUserExample();
		return systemUserDAO.selectByExample(example);
	}
	
	/**
	 * 获取所有启用用户
	 * @return
	 */
	public List<GrootSystemUser> getUserByState() {
		GrootSystemUserExample ex = new GrootSystemUserExample();
		ex.createCriteria().andStatusEqualTo("10A");
		ex.setOrderByClause("login_name");
		return systemUserDAO.selectByExample(ex);
	}
	
	/**
	 * 校验用户信息
	 * @param GrootSystemUser
	 * @return
	 */
	public boolean validateUser(AjaxMessage msg, GrootSystemUser systemUser){
		if(StringUtils.isBlank(systemUser.getLoginName())){
			msg.setContent("保存失败,用户名不能为空！");
			return false;
		}
		
		//校验用户重复
		GrootSystemUserExample example=new GrootSystemUserExample();
		example.createCriteria().andLoginNameEqualTo(systemUser.getLoginName());
		List<GrootSystemUser> userList = systemUserDAO.selectByExample(example);
		
		if(StringUtils.isBlank(systemUser.getId())){
			if(userList.size() > 0){
				msg.setContent("保存失败,用户名重复!");
				return false;
			}
		}else{
			if(userList.size() > 1){
				msg.setContent("保存失败,用户名重复!");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 通过角色ID查询该角色对应的用户信息
	 * @param roleId 角色ID
	 * @return
	 */
	public List<GrootSystemUser> selectSystemUserByRoleId(String roleId) throws Exception{
		GrootSystemUserExample exa = new GrootSystemUserExample();
		exa.createCriteria().andRoleIdEqualTo(roleId);
		@SuppressWarnings("unchecked")
		List<GrootSystemUser> list = systemUserDAO.selectByExample(exa);
		return list;
	}
	
	public List<UserVO> getUser(String userName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("status", "10A");
		return selectDao.list("SYSTEM.getUserInfo", map);
	}
}
